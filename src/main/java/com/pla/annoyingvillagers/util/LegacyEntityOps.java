package com.pla.annoyingvillagers.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Small server-bound adapters for entity operations whose 26.1 signatures now
 * require the authoritative level explicitly.
 */
public final class LegacyEntityOps {
    private LegacyEntityOps() {
    }

    public static ItemEntity spawnAtLocation(Entity entity, ItemStack stack) {
        return entity.level() instanceof ServerLevel serverLevel
                ? entity.spawnAtLocation(serverLevel, stack)
                : null;
    }

    public static ItemEntity spawnAtLocation(Entity entity, ItemStack stack, float yOffset) {
        return entity.level() instanceof ServerLevel serverLevel
                ? entity.spawnAtLocation(serverLevel, stack, yOffset)
                : null;
    }

    public static void kill(Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            entity.kill(serverLevel);
        }
    }
}
