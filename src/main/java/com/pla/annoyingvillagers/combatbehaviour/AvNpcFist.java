package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcFist {
    public static final Builder<MobPatch<?>> FIST = AvNpcCombatBehaviorBuilder.fist(
            CombatCommon.animations(
                    Animations.FIST_AUTO1,
                    Animations.FIST_AUTO2,
                    Animations.FIST_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.FIST_LEFT,
                    Animations.FIST_DASH,
                    AnimsPugilistSteve.WHIRLWIND_KICK_LEFT,
                    AnimsPugilistSteve.FIST_UP,
                    AnimsPugilistSteve.FIST_DASH,
                    AnimsPugilistSteve.WHIRLWIND_KICK
            ),
            CombatCommon.animations(
                    Animations.RELENTLESS_COMBO,
                    Animations.FIST_AIR_SLASH
            )
    );
}
