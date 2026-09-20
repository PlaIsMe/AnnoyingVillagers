package com.pla.annoyingvillagers.block;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/** Position-scoped handoff for fracture block-entity animation data. */
public final class FractureBlockState {
    private static final Map<BlockPos, Data> PENDING = new ConcurrentHashMap<>();

    private FractureBlockState() {}

    public static void prepare(BlockPos pos, BlockState originalState, Vector3f translate,
                               Quaternionf rotation, double bouncing, int maxLifeTime) {
        PENDING.put(pos.immutable(), new Data(originalState, new Vector3f(translate),
                new Quaternionf(rotation), bouncing, maxLifeTime));
    }

    public static Data take(BlockPos pos) {
        return PENDING.remove(pos);
    }

    public static void remove(BlockPos pos) {
        PENDING.remove(pos);
    }

    public static void reset() {
        PENDING.clear();
    }

    public record Data(BlockState originalState, Vector3f translate, Quaternionf rotation,
                       double bouncing, int maxLifeTime) {}
}
