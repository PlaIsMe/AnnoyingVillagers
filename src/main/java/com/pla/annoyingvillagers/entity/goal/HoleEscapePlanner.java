package com.pla.annoyingvillagers.entity.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.PathfinderMob;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/** Bounded shaft/rim scan shared by physical and rig-based hole escapes. */
final class HoleEscapePlanner {
    private HoleEscapePlanner() {}

    static boolean safeFooting(PathfinderMob mob, BlockPos feet) {
        return mob.level().hasChunkAt(feet.below())
                && mob.level().getBlockState(feet.below()).isFaceSturdy(mob.level(), feet.below(), Direction.UP)
                && mob.level().getBlockState(feet).getFluidState().isEmpty();
    }

    static boolean open(PathfinderMob mob, BlockPos feet) {
        return mob.level().hasChunkAt(feet) && mob.level().hasChunkAt(feet.above())
                && mob.level().getBlockState(feet).getCollisionShape(mob.level(), feet).isEmpty()
                && mob.level().getBlockState(feet.above()).getCollisionShape(mob.level(), feet.above()).isEmpty()
                && mob.level().getBlockState(feet).getFluidState().isEmpty();
    }

    @Nullable
    static BlockPos findPassiveShaftExit(PathfinderMob mob, BlockPos column, int maxRimHeight,
                                         BiConsumer<String, BlockPos> rejection) {
        return findPassiveShaftExit(mob, column, maxRimHeight, true, rejection);
    }

    @Nullable
    static BlockPos findPassiveShaftExit(PathfinderMob mob, BlockPos column, int maxRimHeight,
                                         boolean requireSky, BiConsumer<String, BlockPos> rejection) {
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> seen = new HashSet<>();
        queue.add(column);
        seen.add(column);
        BlockPos best = null;
        while (!queue.isEmpty()) {
            BlockPos cell = queue.removeFirst();
            if (!open(mob, cell)) return reject(rejection, "body_not_open", cell);
            if (requireSky && !mob.level().canSeeSky(cell)) return reject(rejection, "roof_or_no_sky", cell);
            for (Direction side : Direction.Plane.HORIZONTAL) {
                BlockPos adjacent = cell.relative(side);
                if (!mob.level().hasChunkAt(adjacent)) return reject(rejection, "unloaded_edge", adjacent);
                if (open(mob, adjacent) || open(mob, adjacent.above()) && safeFooting(mob, adjacent.above())) {
                    if (!open(mob, adjacent)) return reject(rejection, "walkable_step_exit", adjacent);
                    if (!safeFooting(mob, adjacent) || Math.abs(adjacent.getX() - column.getX()) > 3
                            || Math.abs(adjacent.getZ() - column.getZ()) > 3) {
                        return reject(rejection, "open_drop_or_radius_exceeded", adjacent);
                    }
                    if (seen.add(adjacent)) {
                        if (seen.size() > 24) return reject(rejection, "footprint_limit", adjacent);
                        queue.addLast(adjacent);
                    }
                    continue;
                }
                BlockPos rim = null;
                for (int dy = 2; dy <= maxRimHeight; dy++) {
                    BlockPos stand = adjacent.above(dy);
                    if (open(mob, stand) && safeFooting(mob, stand)) {
                        rim = stand;
                        break;
                    }
                }
                if (rim == null) return reject(rejection, "no_rim_within_" + maxRimHeight + "_blocks", adjacent);
                if (best == null || rim.getY() < best.getY()) best = rim;
            }
        }
        return best;
    }

    private static BlockPos reject(BiConsumer<String, BlockPos> rejection, String reason, BlockPos pos) {
        rejection.accept(reason, pos);
        return null;
    }
}
