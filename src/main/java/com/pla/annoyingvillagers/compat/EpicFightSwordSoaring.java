package com.pla.annoyingvillagers.compat;

import java.util.HashSet;
import java.util.Set;

public class EpicFightSwordSoaring {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                "ss:biped/player_summon_kill_aura_1",
                "ss:biped/player_summon_kill_aura_2",
                "ss:biped/player_summon_screen_sword",
                "ss:biped/player_summon_rain_sword",
                "ss:biped/babylon_summon_player",
                "ss:biped/wan1_player"
        ));
    }

    public static Set<String> getDangerousAnimations() {
        return DANGEROUS_ANIMATIONS;
    }
}
