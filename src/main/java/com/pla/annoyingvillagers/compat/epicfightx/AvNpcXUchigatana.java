package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXUchigatana {
    public static final CECombatBehaviors.Builder<MobPatch<?>> X_UCHIGATANA = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.UCHIGATANA_AUTO1,
                    AnimationsX.UCHIGATANA_AUTO2,
                    AnimationsX.UCHIGATANA_AUTO3,
                    ExtraAnimations.UCHIGATANA_AUTO4,
                    ExtraAnimations.UCHIGATANA_AUTO5
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimationsX.UCHIGATANA_DASH,
                    AnimationsX.UCHIGATANA_SHEATHING_AUTO,
                    AnimationsX.BATTOJUTSU,
                    AnimationsX.BATTOJUTSU_DASH,
                    AnimationsX.UCHIGATANA_AIR_SLASH,
                    AnimationsX.UCHIGATANA_SHEATHING_DASH
            )
    );
}
