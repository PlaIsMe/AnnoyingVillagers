package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcUchigatana {
    public static final Builder<MobPatch<?>> UCHIGATANA = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.UCHIGATANA_AUTO1,
                    Animations.UCHIGATANA_AUTO2,
                    Animations.UCHIGATANA_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.UCHIGATANA_DASH,
                    Animations.UCHIGATANA_SHEATHING_AUTO,
                    Animations.BATTOJUTSU,
                    Animations.BATTOJUTSU_DASH,
                    Animations.UCHIGATANA_AIR_SLASH,
                    Animations.UCHIGATANA_SHEATHING_DASH
            )
    );
}
