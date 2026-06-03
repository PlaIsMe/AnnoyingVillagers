package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXSpear {
        public static final Builder<MobPatch<?>> X_SPEAR_SHIELD = AvNpcCombatBehaviorBuilder.weapon(
                CombatCommon.animations(
                        AnimationsX.SPEAR_ONEHAND_AUTO,
                        ExtraAnimations.SPEAR_ONEHAND_AUTO1,
                        ExtraAnimations.SPEAR_ONEHAND_AUTO2
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SPEAR_THRUST
                ),
                CombatCommon.animations(
                        AnimationsX.SPEAR_DASH,
                        AnimationsX.SPEAR_ONEHAND_AIR_SLASH,
                        AnimationsX.HEARTPIERCER
                )
        );

        public static final Builder<MobPatch<?>> X_SPEAR = AvNpcCombatBehaviorBuilder.weapon(
                CombatCommon.animations(
                        AnimationsX.SPEAR_TWOHAND_AUTO1,
                        AnimationsX.SPEAR_TWOHAND_AUTO2,
                        ExtraAnimations.SPEAR_TWOHAND_AUTO3,
                        ExtraAnimations.SPEAR_TWOHAND_AUTO4,
                        ExtraAnimations.SPEAR_TWOHAND_AUTO5
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SPEAR_THRUST
                ),
                CombatCommon.animations(
                        ExtraAnimations.SPEAR_TWOHAND_DASH,
                        AnimationsX.SPEAR_TWOHAND_AIR_SLASH,
                        AnimationsX.GRASPING_SPIRAL_FIRST,
                        AnimationsX.GRASPING_SPIRAL_SECOND
                )
        );
}
