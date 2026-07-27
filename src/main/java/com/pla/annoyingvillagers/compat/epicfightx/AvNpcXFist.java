package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXFist {
    public static final Builder<MobPatch<?>> X_FIST = AvNpcCombatBehaviorBuilder.fist(
            CombatCommon.animations(
                    AnimationsX.FIST_AUTO1,
                    AnimationsX.FIST_AUTO2,
                    AnimationsX.FIST_AUTO3,
                    ExtraAnimations.FIST_AUTO4,
                    ExtraAnimations.FIST_AUTO5
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
                    AnimationsX.FIST_DASH,
                    AnimationsX.FIST_AIR_SLASH,
                    AnimationsX.RELENTLESS_COMBO
            )
    );
}
