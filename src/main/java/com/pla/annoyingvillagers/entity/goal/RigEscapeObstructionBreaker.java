package com.pla.annoyingvillagers.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;

/** Forge-safe progressive breaker for the exact swept volume of a scripted escape. */
final class RigEscapeObstructionBreaker {
    @Nullable private BlockPos pos;
    @Nullable private BlockState state;
    private int ticks;
    private int stage = -1;

    /** @return true while an obstruction exists and movement/animation must wait. */
    boolean clear(Mob breaker, AABB volume) {
        if (!(breaker.level() instanceof ServerLevel level)) return true;
        BlockPos obstruction = firstCollision(level, volume);
        if (obstruction == null) {
            reset(breaker);
            return false;
        }
        if (!level.hasChunkAt(obstruction) || !level.isInWorldBounds(obstruction)
                || !level.getWorldBorder().isWithinBounds(obstruction)) {
            reset(breaker);
            return true;
        }
        BlockState found = level.getBlockState(obstruction);
        if (!canBreak(breaker, obstruction, found)) {
            reset(breaker);
            return true;
        }
        if (!obstruction.equals(this.pos) || found != this.state) {
            reset(breaker);
            this.pos = obstruction.immutable();
            this.state = found;
        }
        int needed = Mth.clamp(Mth.ceil(Math.max(0.1F, found.getDestroySpeed(level, obstruction)) * 2.0F), 1, 10);
        int nextStage = Math.min(9, ++this.ticks * 10 / needed);
        if (nextStage != this.stage) {
            level.destroyBlockProgress(breaker.getId(), obstruction, nextStage);
            this.stage = nextStage;
        }
        if (this.ticks < needed) return true;
        if (level.getBlockState(obstruction) == found && canBreak(breaker, obstruction, found)
                && ForgeEventFactory.onEntityDestroyBlock(breaker, obstruction, found)) {
            level.destroyBlock(obstruction, true, breaker);
        }
        reset(breaker);
        return true;
    }

    void reset(Mob breaker) {
        if (this.pos != null && this.stage >= 0) breaker.level().destroyBlockProgress(breaker.getId(), this.pos, -1);
        this.pos = null;
        this.state = null;
        this.ticks = 0;
        this.stage = -1;
    }

    static boolean canBreak(Mob breaker, BlockPos pos, BlockState state) {
        return breaker.level() instanceof ServerLevel level && level.hasChunkAt(pos)
                && level.isInWorldBounds(pos) && level.getWorldBorder().isWithinBounds(pos)
                && ForgeEventFactory.getMobGriefingEvent(level, breaker)
                && !state.isAir() && state.getFluidState().isEmpty() && !state.hasBlockEntity()
                && state.getDestroySpeed(level, pos) >= 0.0F && state.canEntityDestroy(level, pos, breaker)
                && !state.getCollisionShape(level, pos).isEmpty();
    }

    @Nullable
    private static BlockPos firstCollision(ServerLevel level, AABB volume) {
        BlockPos best = null;
        double bestY = Double.MAX_VALUE;
        for (BlockPos candidate : BlockPos.betweenClosed(BlockPos.containing(volume.minX, volume.minY, volume.minZ),
                BlockPos.containing(volume.maxX, volume.maxY, volume.maxZ))) {
            if (!level.hasChunkAt(candidate) || !level.isInWorldBounds(candidate)
                    || !level.getWorldBorder().isWithinBounds(candidate)) return candidate.immutable();
            BlockState state = level.getBlockState(candidate);
            var shape = state.getCollisionShape(level, candidate);
            if (shape.isEmpty() || !Shapes.joinIsNotEmpty(Shapes.create(volume),
                    shape.move(candidate.getX(), candidate.getY(), candidate.getZ()), BooleanOp.AND)) continue;
            double y = Vec3.atCenterOf(candidate).y;
            if (y < bestY) {
                bestY = y;
                best = candidate.immutable();
            }
        }
        return best;
    }
}
