package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXTachi {
        public static final Builder<MobPatch<?>> X_TACHI = AvNpcCombatBehaviorBuilder.weapon(
                CombatCommon.animations(
                        AnimationsX.TACHI_AUTO1,
                        AnimationsX.TACHI_AUTO2,
                        AnimationsX.TACHI_AUTO3,
                        ExtraAnimations.TACHI_AUTO4,
                        ExtraAnimations.TACHI_AUTO5
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                ),
                CombatCommon.animations(
                        AnimationsX.TACHI_DASH,
                        ExtraAnimations.TACHI_AIR_SLASH,
                        AnimationsX.RUSHING_TEMPO1,
                        AnimationsX.RUSHING_TEMPO2,
                        AnimationsX.RUSHING_TEMPO3,
                        ExtraAnimations.RUSHING_TEMPO4,
                        ExtraAnimations.RUSHING_TEMPO5
                )
        );
}
