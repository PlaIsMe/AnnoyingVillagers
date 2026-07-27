package com.pla.annoyingvillagers.compat.dualgreatsword;

import M6FGR.dualgreatswords.gameassets.DualGreatSwordsAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcDualGreatsword {
    public static final CECombatBehaviors.Builder<MobPatch<?>> DUAL_GREATSWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_1,
                    DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_2,
                    DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_3,
                    DualGreatSwordsAnimations.GREATSWORD_DUAL_AUTO_4
            ),
            CombatCommon.animations(
                    DualGreatSwordsAnimations.GREATSWORD_DUAL_DASH,
                    DualGreatSwordsAnimations.GREATSWORD_DUAL_AIRSLASH
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.GIANT_WHIRLWIND,
                    DualGreatSwordsAnimations.GREATSWORD_EARTH_QUAKE
            )
    );
}
