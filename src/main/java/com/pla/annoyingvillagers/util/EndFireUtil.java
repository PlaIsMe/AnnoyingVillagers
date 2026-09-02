package com.pla.annoyingvillagers.util;

import net.minecraft.world.entity.Entity;

public final class EndFireUtil {
    private EndFireUtil() {
    }

    public static boolean isEndFireBurning(Entity entity) {
        return entity instanceof EndFireEntity endFireEntity && endFireEntity.annoyingVillagers$isEndFireBurning();
    }

    public static void setEndFireBurning(Entity entity,boolean endFireBurning) {
        if (entity instanceof EndFireEntity endFireEntity) endFireEntity.annoyingVillagers$setEndFireBurning(endFireBurning);
    }
}
