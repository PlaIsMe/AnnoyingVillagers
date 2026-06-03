package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXGreatsword {
    public static final Builder<MobPatch<?>> X_GREATSWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.GREATSWORD_AUTO1,
                    AnimationsX.GREATSWORD_AUTO2,
                    ExtraAnimations.GREATSWORD_AUTO3,
                    ExtraAnimations.GREATSWORD_AUTO4,
                    ExtraAnimations.GREATSWORD_AUTO5
            ),
            CombatCommon.animations(
                    AnimationsX.GREATSWORD_DASH,
                    AnimationsX.GREATSWORD_AIR_SLASH
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.GIANT_WHIRLWIND,
                    AnimationsX.STEEL_WHIRLWIND_CHARGING,
                    AnimationsX.STEEL_WHIRLWIND
            )
    );
}
