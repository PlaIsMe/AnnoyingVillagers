package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXDagger {
    public static final Builder<MobPatch<?>> X_DAGGER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.DAGGER_AUTO1,
                    AnimationsX.DAGGER_AUTO2,
                    AnimationsX.DAGGER_AUTO3,
                    ExtraAnimations.DAGGER_AUTO4,
                    ExtraAnimations.DAGGER_AUTO5
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimationsX.DAGGER_DASH,
                    AnimationsX.DAGGER_AIR_SLASH,
                    AnimationsX.EVISCERATE_FIRST,
                    AnimationsX.EVISCERATE_SECOND
            )
    );

    public static final Builder<MobPatch<?>> X_DUAL_DAGGER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.DAGGER_DUAL_AUTO1,
                    AnimationsX.DAGGER_DUAL_AUTO2,
                    AnimationsX.DAGGER_DUAL_AUTO3,
                    AnimationsX.DAGGER_DUAL_AUTO4,
                    ExtraAnimations.DAGGER_DUAL_AUTO5
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimationsX.DAGGER_DUAL_DASH,
                    AnimationsX.DAGGER_DUAL_AIR_SLASH,
                    AnimationsX.BLADE_RUSH_COMBO1,
                    AnimationsX.BLADE_RUSH_COMBO2,
                    AnimationsX.BLADE_RUSH_COMBO3
            )
    );
}
