package com.pla.annoyingvillagers.compat.dualaxes;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcDualAxe {
    public static final CECombatBehaviors.Builder<MobPatch<?>> AXE = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    DualAxesAnimations.AXE_AUTO_1,
                    DualAxesAnimations.AXE_AUTO_2,
                    DualAxesAnimations.AXE_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    Animations.BIPED_MOB_TACHI,
                    Animations.AXE_AIRSLASH,
                    Animations.THE_GUILLOTINE
            )
    );

    public static final CECombatBehaviors.Builder<MobPatch<?>> DUAL_AXE = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    DualAxesAnimations.AXE_DUAL_AUTO_1,
                    DualAxesAnimations.AXE_DUAL_AUTO_2,
                    DualAxesAnimations.AXE_DUAL_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    DualAxesAnimations.AXE_DUAL_DASH,
                    DualAxesAnimations.AXE_DUAL_AIRSLASH,
                    DualAxesAnimations.AXE_SPINNING_DEATH
            )
    );
}
