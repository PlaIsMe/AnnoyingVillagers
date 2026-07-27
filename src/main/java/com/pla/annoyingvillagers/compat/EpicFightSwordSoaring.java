package com.pla.annoyingvillagers.compat;

import net.p1nero.ss.gameassets.animations.BabylonAnimations;
import net.p1nero.ss.gameassets.animations.ScreenSwordAnimations;
import net.p1nero.ss.gameassets.animations.WanAnimations;

import java.util.HashSet;
import java.util.Set;

public class EpicFightSwordSoaring {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                ScreenSwordAnimations.PLAYER_SUMMON_KILL_AURA_1.get().getRegistryName().toString(),
                ScreenSwordAnimations.PLAYER_SUMMON_KILL_AURA_2.get().getRegistryName().toString(),
                ScreenSwordAnimations.PLAYER_SUMMON_SCREEN_SWORD.get().getRegistryName().toString(),
                ScreenSwordAnimations.PLAYER_SUMMON_RAIN_SWORD.get().getRegistryName().toString(),
                BabylonAnimations.BABYLON_SUMMON_PLAYER.get().getRegistryName().toString(),
                WanAnimations.WAN1_PLAYER.get().getRegistryName().toString()
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }
}
