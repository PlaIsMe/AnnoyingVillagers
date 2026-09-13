package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.MoverType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

import java.util.EnumSet;

/** Temporarily owns a sauce's movement while it rendezvouses with and carries Blue Demon. */
public final class BbqCarryBlueDemonEscapeGoal extends Goal {
    private final BbqEntity bbq;
    private double approachY;
    private int approachPhase;
    @Nullable private BlockPos breakingPos;
    @Nullable private BlockState breakingState;
    @Nullable private BlockPos vetoedPos;
    @Nullable private BlockState vetoedState;
    private int breakTicks;
    private int breakStage = -1;

    public BbqCarryBlueDemonEscapeGoal(BbqEntity bbq) {
        this.bbq = bbq;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override public boolean canUse() { return this.validCarry(); }
    @Override public boolean canContinueToUse() { return this.validCarry(); }
    @Override public boolean requiresUpdateEveryTick() { return true; }

    @Override
    public void start() {
        resetBreakingSession();
        this.bbq.getNavigation().stop();
        this.bbq.setNoGravity(true);
        this.approachPhase = 0;
        BlueDemonEntity leader = this.bbq.getHoleCarryLeader();
        this.approachY = this.bbq.getY();
        if (leader != null) {
            this.approachY = Math.max(this.approachY, leader.getBoundingBox().maxY + 0.3D);
            // Cross the rim above the terrain, then descend into the shaft. A direct
            // diagonal flight to the head would run into the side of a deep hole.
            int steps = Math.max(1, (int) Math.ceil(this.bbq.distanceTo(leader)));
            if (steps > 64) {
                this.bbq.endHoleCarry(false);
                return;
            }
            for (int step = 0; step <= steps; step++) {
                BlockPos column = BlockPos.containing(this.bbq.position().lerp(leader.position(), step / (double) steps));
                if (!this.bbq.level().hasChunkAt(column)) {
                    this.bbq.endHoleCarry(false);
                    return;
                }
                this.approachY = Math.max(this.approachY,
                        this.bbq.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column).getY() + 1.0D);
            }
        }
    }

    @Override
    public void tick() {
        BlueDemonEntity leader = this.bbq.getHoleCarryLeader();
        if (leader == null) return;
        double x = leader.getX();
        double y = leader.getBoundingBox().maxY + 0.3D;
        double z = leader.getZ();
        this.bbq.getNavigation().stop();
        this.bbq.setNoGravity(true);
        this.bbq.flapForHoleCarry();
        this.bbq.getLookControl().setLookAt(leader, 60.0F, 60.0F);
        if (this.bbq.isHoleCarryLifting()) {
            resetBreakingSession();
            this.bbq.move(MoverType.SELF, new Vec3(x - this.bbq.getX(), y - this.bbq.getY(), z - this.bbq.getZ()));
            this.bbq.setDeltaMovement(Vec3.ZERO);
        } else {
            if (this.approachPhase == 0) {
                if (this.bbq.getY() < this.approachY - 0.25D) {
                    if (breakFlightObstruction(new Vec3(this.bbq.getX(), this.approachY, this.bbq.getZ()))) return;
                    this.bbq.moveEscapeAerialTowards(this.bbq.getX(), this.approachY, this.bbq.getZ(), 0.28D, 0.72D);
                    return;
                }
                this.approachPhase = 1;
            }
            if (this.approachPhase == 1) {
                double dx = x - this.bbq.getX();
                double dz = z - this.bbq.getZ();
                if (dx * dx + dz * dz > 0.04D) {
                    if (breakFlightObstruction(new Vec3(x, this.approachY, z))) return;
                    this.bbq.moveEscapeAerialTowards(x, this.approachY, z, 0.28D, 0.72D);
                    return;
                }
                this.approachPhase = 2;
                this.bbq.setDeltaMovement(Vec3.ZERO);
            }
            if (breakFlightObstruction(new Vec3(x, y, z))) return;
            this.bbq.moveEscapeAerialTowards(x, y, z, 0.28D, 0.72D);
        }
    }

    @Override
    public void stop() {
        resetBreakingSession();
        if (this.bbq.isHoleCarryActive() && !this.validCarry()) this.bbq.endHoleCarry(false);
    }

    /** Progressively opens only the next body-sized collision cell on the rendezvous flight. */
    private boolean breakFlightObstruction(Vec3 wanted) {
        if (!(this.bbq.level() instanceof ServerLevel level)) return true;
        Vec3 delta = wanted.subtract(this.bbq.position());
        if (delta.lengthSqr() < 1.0E-6D) {
            clearBreaking();
            return false;
        }
        Vec3 step = delta.lengthSqr() > 0.8D * 0.8D ? delta.normalize().scale(0.8D) : delta;
        AABB swept = this.bbq.getBoundingBox().expandTowards(step).inflate(0.04D);
        for (BlockPos pos : BlockPos.betweenClosed(BlockPos.containing(swept.minX, swept.minY, swept.minZ),
                BlockPos.containing(swept.maxX, swept.maxY, swept.maxZ))) {
            if (!level.hasChunkAt(pos) || !level.isInWorldBounds(pos)
                    || !level.getWorldBorder().isWithinBounds(pos)) {
                clearBreaking();
                return true;
            }
        }
        BlockPos obstruction = nearestObstruction(level, swept);
        if (obstruction == null) {
            clearBreaking();
            return false;
        }
        BlockState state = level.getBlockState(obstruction);
        if (obstruction.equals(this.vetoedPos) && state == this.vetoedState) return true;
        if (obstruction.equals(this.vetoedPos)) {
            this.vetoedPos = null;
            this.vetoedState = null;
        }
        if (!canBreak(level, obstruction, state)) {
            clearBreaking();
            return true;
        }
        if (!obstruction.equals(this.breakingPos) || state != this.breakingState) {
            clearBreaking();
            this.breakingPos = obstruction.immutable();
            this.breakingState = state;
        }
        float hardness = state.getDestroySpeed(level, obstruction);
        int needed = Mth.clamp(Mth.ceil(Math.max(0.1F, hardness) * 2.0F), 1, 10);
        int stage = Math.min(9, ++this.breakTicks * 10 / needed);
        if (stage != this.breakStage) {
            level.destroyBlockProgress(this.bbq.getId(), obstruction, stage);
            this.breakStage = stage;
        }
        if (this.breakTicks < needed) return true;
        if (level.getBlockState(obstruction) == state && canBreak(level, obstruction, state)
                && ForgeEventFactory.onEntityDestroyBlock(this.bbq, obstruction, state)) {
            level.destroyBlock(obstruction, true, this.bbq);
        } else {
            this.vetoedPos = obstruction.immutable();
            this.vetoedState = state;
        }
        clearBreaking();
        return true;
    }

    @Nullable
    private BlockPos nearestObstruction(ServerLevel level, AABB swept) {
        BlockPos nearest = null;
        double best = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(BlockPos.containing(swept.minX, swept.minY, swept.minZ),
                BlockPos.containing(swept.maxX, swept.maxY, swept.maxZ))) {
            BlockState state = level.getBlockState(pos);
            var shape = state.getCollisionShape(level, pos);
            if (shape.isEmpty() || !Shapes.joinIsNotEmpty(Shapes.create(swept),
                    shape.move(pos.getX(), pos.getY(), pos.getZ()), BooleanOp.AND)) continue;
            double distance = this.bbq.position().distanceToSqr(Vec3.atCenterOf(pos));
            if (distance < best) {
                best = distance;
                nearest = pos.immutable();
            }
        }
        return nearest;
    }

    private boolean canBreak(ServerLevel level, BlockPos pos, BlockState state) {
        return level.hasChunkAt(pos) && level.isInWorldBounds(pos)
                && level.getWorldBorder().isWithinBounds(pos)
                && ForgeEventFactory.getMobGriefingEvent(level, this.bbq)
                && !state.isAir() && state.getFluidState().isEmpty() && !state.hasBlockEntity()
                && state.canEntityDestroy(level, pos, this.bbq)
                && state.getDestroySpeed(level, pos) >= 0.0F
                && !state.getCollisionShape(level, pos).isEmpty();
    }

    private void clearBreaking() {
        if (this.breakingPos != null && this.breakStage >= 0) {
            this.bbq.level().destroyBlockProgress(this.bbq.getId(), this.breakingPos, -1);
        }
        this.breakingPos = null;
        this.breakingState = null;
        this.breakTicks = 0;
        this.breakStage = -1;
    }

    private void resetBreakingSession() {
        clearBreaking();
        this.vetoedPos = null;
        this.vetoedState = null;
    }

    private boolean validCarry() {
        return this.bbq.isAlive() && !this.bbq.isRemoved() && !this.bbq.isNoAi()
                && this.bbq.getHoleCarryLeader() != null;
    }
}
