package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.ai.RecoveryAi;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.rig.pose.RigPoseLibrary;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

/** Uses an owned sauce to lift Blue Demon out of water and toward dry land or a water target. */
public final class BlueDemonWaterCarryGoal extends Goal {
    private static final int RENDEZVOUS_TIMEOUT_TICKS = 100;
    private static final int SEARCH_RADIUS = 16;
    private static final double CARRY_SPEED = 0.30D;
    private final BlueDemonEntity mob;
    private BbqEntity carrier;
    private Vec3 destination;
    private boolean dryDestination;
    private double cruiseY;
    private int phase;
    private int elapsed;
    private int nextUseTick;
    private boolean ownsLock;
    private boolean previousNoGravity;
    private RigAnimationId exitRoll;
    private int exitRollStartTick;

    public BlueDemonWaterCarryGoal(BlueDemonEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    public boolean ownsEscape() { return this.ownsLock; }

    public boolean isCarrying() { return this.ownsLock && this.phase > 0 && this.exitRoll == null; }

    public boolean shouldForceCancel() {
        return this.ownsLock && (!this.canAct() || !this.mob.canUseBbqHoleEscape()
                || this.exitRoll == null && (this.carrier == null || !this.carrier.isAlive()
                || this.carrier.isRemoved() || this.carrier.getHoleCarryLeader() != this.mob));
    }

    public void forceCancel() { if (this.ownsLock) this.stop(); }

    @Override
    public boolean canUse() {
        if (this.mob.tickCount < this.nextUseTick || !this.canAct() || this.mob.isLocked()
                || !this.mob.isInWater() || !this.mob.canUseBbqHoleEscape()
                || RigAnimationController.hasActiveAnimation(this.mob) && !RigAnimationController.hasActiveProfileAttack(this.mob)
                || (this.carrier = this.mob.findAvailableHoleEscapeSauce()) == null) return false;
        this.nextUseTick = this.mob.tickCount + 20 + this.mob.getRandom().nextInt(11);
        this.destination = this.findDestination((ServerLevel) this.mob.level());
        return this.destination != null;
    }

    @Override
    public void start() {
        this.mob.lock();
        this.ownsLock = true;
        this.elapsed = 0;
        this.phase = 0;
        this.exitRoll = null;
        this.previousNoGravity = this.mob.isNoGravity();
        this.mob.setBbqHoleEscapeActive(true);
        RigShieldGuardController.stop(this.mob);
        if (RigAnimationController.hasActiveProfileAttack(this.mob)) {
            RigAnimationController.stop(this.mob, RigAnimationController.getActiveAnimationId(this.mob));
        }
        this.mob.getNavigation().stop();
        if (!this.carrier.beginHoleCarry(this.mob)) {
            this.stop();
            return;
        }
        this.cruiseY = this.computeCruiseY((ServerLevel) this.mob.level(), this.destination);
        if (!Double.isFinite(this.cruiseY)) this.stop();
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.ownsLock || this.elapsed >= 400 || !this.canAct() || !this.mob.canUseBbqHoleEscape()) return false;
        if (this.exitRoll != null) return true;
        return this.carrier != null && this.carrier.isAlive() && this.carrier.getHoleCarryLeader() == this.mob;
    }

    @Override
    public void tick() {
        this.elapsed++;
        this.mob.getNavigation().stop();
        if (this.exitRoll != null) {
            if (RigAnimationController.getActiveAnimationId(this.mob) != this.exitRoll
                    || this.mob.tickCount - this.exitRollStartTick > RigAnimationSpecs.get(this.exitRoll).durationTicks()) this.elapsed = 400;
            return;
        }
        if (this.carrier == null || !this.carrier.isAlive() || this.carrier.isRemoved()
                || this.carrier.getHoleCarryLeader() != this.mob || this.destination == null) {
            this.stop();
            return;
        }

        Vec3 head = carrierPosition();
        if (this.phase == 0) {
            if (this.elapsed >= RENDEZVOUS_TIMEOUT_TICKS) {
                this.stop();
                return;
            }
            this.mob.getLookControl().setLookAt(this.carrier, 60.0F, 60.0F);
            if (this.carrier.distanceToSqr(head) > 0.35D * 0.35D) return;
            this.mob.setNoGravity(true);
            this.mob.setDeltaMovement(Vec3.ZERO);
            this.carrier.setHoleCarryLifting(true);
            RigAnimationController.playHeldPose(this.mob, RigAnimationId.BLUE_DEMON_ZIPLINE);
            this.phase = 1;
            return;
        }
        if (RigAnimationController.getActiveAnimationId(this.mob) != RigAnimationId.BLUE_DEMON_ZIPLINE) {
            this.elapsed = 400;
            return;
        }

        Vec3 wanted;
        if (this.phase == 1) {
            wanted = new Vec3(this.mob.getX(), this.cruiseY, this.mob.getZ());
            if (Math.abs(this.mob.getY() - this.cruiseY) < 0.15D) this.phase = 2;
        } else if (this.phase == 2) {
            wanted = new Vec3(this.destination.x, this.cruiseY, this.destination.z);
            if (horizontalDistanceSqr(this.mob.position(), wanted) < 0.18D) this.phase = 3;
        } else {
            wanted = this.destination;
            if (this.mob.position().distanceToSqr(wanted) < 0.12D) {
                this.finishCarry();
                return;
            }
        }
        if (!moveToward(wanted)) return;
        head = carrierPosition();
        this.carrier.move(MoverType.SELF, head.subtract(this.carrier.position()));
        this.carrier.setDeltaMovement(Vec3.ZERO);
        this.carrier.flapForHoleCarry();
        if (this.carrier.distanceToSqr(head) > 0.08D * 0.08D) this.elapsed = 400;
    }

    private void finishCarry() {
        if (!this.validDestinationNow()) {
            this.elapsed = 400;
            return;
        }
        RigAnimationController.stop(this.mob, RigAnimationId.BLUE_DEMON_ZIPLINE);
        this.mob.setNoGravity(this.previousNoGravity);
        this.carrier.endHoleCarry(true);
        this.exitRoll = this.randomExitRoll();
        this.exitRollStartTick = this.mob.tickCount;
        RigAnimationController.play(this.mob, this.exitRoll);
        this.nextUseTick = this.mob.tickCount + 120 + this.mob.getRandom().nextInt(81);
    }

    private boolean moveToward(Vec3 wanted) {
        Vec3 delta = wanted.subtract(this.mob.position());
        if (delta.lengthSqr() > CARRY_SPEED * CARRY_SPEED) delta = delta.normalize().scale(CARRY_SPEED);
        AABB swept = this.mob.getBoundingBox().expandTowards(delta);
        Vec3 nextMob = this.mob.position().add(delta);
        Vec3 nextCarrier = new Vec3(nextMob.x, nextMob.y + this.mob.getBbHeight() + 0.3D, nextMob.z);
        Vec3 carrierDelta = nextCarrier.subtract(this.carrier.position());
        BlockPos nextCell = BlockPos.containing(nextMob);
        BlockPos nextCarrierCell = BlockPos.containing(nextCarrier);
        if (!this.mob.level().hasChunkAt(nextCell) || !this.mob.level().isInWorldBounds(nextCell)
                || !this.mob.level().getWorldBorder().isWithinBounds(nextCell)
                || !this.carrier.level().hasChunkAt(nextCarrierCell)
                || !this.carrier.level().isInWorldBounds(nextCarrierCell)
                || !this.carrier.level().getWorldBorder().isWithinBounds(nextCarrierCell)
                || !this.mob.level().noCollision(this.mob, swept)
                || !this.carrier.level().noCollision(this.carrier, this.carrier.getBoundingBox().expandTowards(carrierDelta))) {
            this.elapsed = 400;
            return false;
        }
        this.mob.move(MoverType.SELF, delta);
        this.mob.setDeltaMovement(Vec3.ZERO);
        this.mob.fallDistance = 0.0F;
        if (this.mob.position().distanceToSqr(nextMob) > 0.02D * 0.02D) {
            this.elapsed = 400;
            return false;
        }
        return true;
    }

    private Vec3 carrierPosition() {
        return new Vec3(this.mob.getX(), this.mob.getBoundingBox().maxY + 0.3D, this.mob.getZ());
    }

    @Override
    public void stop() {
        if (!this.ownsLock) return;
        RigAnimationId active = RigAnimationController.getActiveAnimationId(this.mob);
        if (active == RigAnimationId.BLUE_DEMON_ZIPLINE || active == this.exitRoll) RigAnimationController.stop(this.mob, active);
        this.mob.setNoGravity(this.previousNoGravity);
        if (this.carrier != null && this.carrier.getHoleCarryLeader() == this.mob) this.carrier.endHoleCarry(true);
        this.mob.setBbqHoleEscapeActive(false);
        this.mob.unlock();
        this.ownsLock = false;
        this.nextUseTick = Math.max(this.nextUseTick, this.mob.tickCount + 120);
        this.carrier = null;
        this.destination = null;
        this.exitRoll = null;
    }

    @Override public boolean requiresUpdateEveryTick() { return true; }

    private boolean canAct() {
        return this.mob.level() instanceof ServerLevel && this.mob.isAlive() && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying() && !this.mob.isNoAi() && !this.mob.isPassenger()
                && !RigStunController.isStunned(this.mob);
    }

    @Nullable
    private Vec3 findDestination(ServerLevel level) {
        BlockPos origin = this.mob.blockPosition();
        LivingEntity target = this.mob.getTarget();
        BlockPos best = null;
        double bestScore = Double.MAX_VALUE;
        for (int radius = 2; radius <= SEARCH_RADIUS; radius += 2) {
            for (int dx = -radius; dx <= radius; dx++) for (int dz = -radius; dz <= radius; dz++) {
                if (Math.max(Math.abs(dx), Math.abs(dz)) != radius) continue;
                BlockPos column = origin.offset(dx, 0, dz);
                if (!level.hasChunkAt(column)) continue;
                BlockPos stand = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column);
                if (!safeDryLanding(level, stand)) continue;
                double score = dx * dx + dz * dz;
                if (target != null && target.isAlive()) score += stand.distSqr(target.blockPosition()) * 0.2D;
                if (score < bestScore) {
                    bestScore = score;
                    best = stand;
                }
            }
            if (best != null) break;
        }
        if (best != null) {
            this.dryDestination = true;
            return Vec3.atBottomCenterOf(best);
        }

        if (RecoveryAi.validTarget(this.mob, target) && target.isInWater()
                && this.mob.distanceToSqr(target) > 9.0D) {
            Vec3 toward = target.position().subtract(this.mob.position());
            Vec3 horizontal = new Vec3(toward.x, 0.0D, toward.z);
            if (horizontal.lengthSqr() > 1.0E-6D) {
                Vec3 point = target.position().subtract(horizontal.normalize().scale(2.0D));
                BlockPos column = BlockPos.containing(point);
                if (level.hasChunkAt(column)) {
                    int surface = waterSurfaceAirY(level, column, this.mob.getBlockY());
                    if (surface != Integer.MIN_VALUE) {
                        this.dryDestination = false;
                        return new Vec3(point.x, surface + 0.2D, point.z);
                    }
                }
            }
        }
        return null;
    }

    private boolean safeDryLanding(ServerLevel level, BlockPos stand) {
        if (!level.hasChunkAt(stand) || !level.getWorldBorder().isWithinBounds(stand)
                || !level.getFluidState(stand).isEmpty() || !level.getFluidState(stand.above()).isEmpty()
                || !level.getBlockState(stand).getCollisionShape(level, stand).isEmpty()
                || !level.getBlockState(stand.above()).getCollisionShape(level, stand.above()).isEmpty()
                || !level.getBlockState(stand.below()).isFaceSturdy(level, stand.below(), Direction.UP)) return false;
        int requiredRollCells = requiredRollClearance();
        for (Direction side : Direction.Plane.HORIZONTAL) {
            if (countDryRollCells(level, stand, side, requiredRollCells) >= requiredRollCells) {
                return level.noCollision(this.mob, this.mob.getBoundingBox().move(
                        stand.getX() + 0.5D - this.mob.getX(), stand.getY() - this.mob.getY(),
                        stand.getZ() + 0.5D - this.mob.getZ()));
            }
        }
        return false;
    }

    private boolean validDestinationNow() {
        if (!(this.mob.level() instanceof ServerLevel level) || this.destination == null) return false;
        if (!this.dryDestination) {
            return this.mob.getTarget() != null && this.mob.getTarget().isAlive()
                    && this.mob.getTarget().isInWater() && level.noCollision(this.mob);
        }
        return safeDryLanding(level, BlockPos.containing(this.destination));
    }

    private double computeCruiseY(ServerLevel level, Vec3 end) {
        int steps = Mth.clamp(Mth.ceil(this.mob.position().distanceTo(end)), 1, 64);
        double y = Math.max(this.mob.getY(), end.y) + 2.5D;
        for (int i = 0; i <= steps; i++) {
            BlockPos column = BlockPos.containing(this.mob.position().lerp(end, i / (double) steps));
            if (!level.hasChunkAt(column) || !level.getWorldBorder().isWithinBounds(column)
                    || !level.isInWorldBounds(column)) return Double.NaN;
            int waterSurface = waterSurfaceAirY(level, column, this.mob.getBlockY());
            int terrain = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, column).getY();
            double obstacleTop = Math.max(terrain, waterSurface == Integer.MIN_VALUE ? level.getMinY() : waterSurface);
            if (obstacleTop - this.mob.getY() > 32.0D) return Double.NaN;
            y = Math.max(y, obstacleTop + 2.5D);
        }
        if (y + this.mob.getBbHeight() + 0.3D + this.carrier.getBbHeight() >= level.getMaxY()) return Double.NaN;
        return y;
    }

    private RigAnimationId randomExitRoll() {
        RigAnimationId roll = this.mob.getRandom().nextBoolean() ? RigAnimationId.ROLL_FORWARD : RigAnimationId.ROLL_BACKWARD;
        Vec3 facePoint = null;
        if (this.dryDestination && this.mob.level() instanceof ServerLevel level) {
            BlockPos feet = this.mob.blockPosition();
            int bestDry = -1;
            int requiredRollCells = requiredRollClearance();
            for (Direction side : Direction.Plane.HORIZONTAL) {
                int dry = countDryRollCells(level, feet, side, requiredRollCells);
                if (dry > bestDry) {
                    bestDry = dry;
                    facePoint = Vec3.atCenterOf(feet.relative(side, requiredRollCells));
                }
            }
        } else if (this.mob.getTarget() != null) {
            facePoint = this.mob.getTarget().position();
        }
        if (facePoint != null) {
            double dx = facePoint.x - this.mob.getX();
            double dz = facePoint.z - this.mob.getZ();
            if (roll == RigAnimationId.ROLL_BACKWARD) { dx = -dx; dz = -dz; }
            if (dx * dx + dz * dz > 1.0E-6D) {
                float yaw = (float) (Math.atan2(-dx, dz) * 180.0D / Math.PI);
                this.mob.setYRot(yaw);
                this.mob.yBodyRot = yaw;
            }
        }
        return roll;
    }

    private static double horizontalDistanceSqr(Vec3 a, Vec3 b) {
        double dx = a.x - b.x;
        double dz = a.z - b.z;
        return dx * dx + dz * dz;
    }

    private static int requiredRollClearance() {
        return Mth.ceil(Math.max(RigPoseLibrary.maxHorizontalMotionBlocks(RigAnimationId.ROLL_FORWARD),
                RigPoseLibrary.maxHorizontalMotionBlocks(RigAnimationId.ROLL_BACKWARD))) + 1;
    }

    private int countDryRollCells(ServerLevel level, BlockPos start, Direction direction, int limit) {
        int clear = 0;
        for (int step = 1; step <= limit; step++) {
            BlockPos pos = start.relative(direction, step);
            if (!level.hasChunkAt(pos) || !level.isInWorldBounds(pos)
                    || !level.getWorldBorder().isWithinBounds(pos) || !level.getFluidState(pos).isEmpty()
                    || !level.getFluidState(pos.above()).isEmpty()
                    || !level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()
                    || !level.getBlockState(pos.above()).getCollisionShape(level, pos.above()).isEmpty()
                    || !level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)
                    || !level.noCollision(this.mob, this.mob.getBoundingBox().move(
                    pos.getX() + 0.5D - this.mob.getX(), pos.getY() - this.mob.getY(),
                    pos.getZ() + 0.5D - this.mob.getZ()))) break;
            clear++;
        }
        return clear;
    }

    private static int waterSurfaceAirY(ServerLevel level, BlockPos column, int aroundY) {
        int min = Math.max(level.getMinY(), aroundY - 4);
        int max = Math.min(level.getMaxY() - 2, aroundY + 32);
        boolean sawWater = false;
        for (int y = min; y <= max; y++) {
            BlockPos pos = new BlockPos(column.getX(), y, column.getZ());
            if (!level.hasChunkAt(pos)) break;
            if (level.getFluidState(pos).is(FluidTags.WATER)) sawWater = true;
            else if (sawWater) return y;
        }
        return Integer.MIN_VALUE;
    }
}
