package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXAxe {
    public static final Builder<MobPatch<?>> X_AXE = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.AXE_AUTO1,
                    AnimationsX.AXE_AUTO2,
                    ExtraAnimations.AXE_AUTO3,
                    ExtraAnimations.AXE_AUTO4,
                    ExtraAnimations.AXE_AUTO5
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                    AnimsPugilistSteve.AXE_FUN_SKILL
            ),
            CombatCommon.animations(
                    AnimationsX.AXE_DASH,
                    AnimationsX.AXE_AIRSLASH,
                    AnimationsX.THE_GUILLOTINE
            )
    );
}
