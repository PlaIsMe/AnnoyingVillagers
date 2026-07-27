package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXSword {
    public static final Builder<MobPatch<?>> X_SWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.SWORD_AUTO1,
                    AnimationsX.SWORD_AUTO2,
                    AnimationsX.SWORD_AUTO3,
                    ExtraAnimations.SWORD_AUTO4,
                    ExtraAnimations.SWORD_AUTO5
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimationsX.SWORD_DASH,
                    AnimationsX.SWORD_AIR_SLASH,
                    AnimationsX.SWEEPING_EDGE
            )
    );

    public static final Builder<MobPatch<?>> X_DUAL_SWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimationsX.SWORD_DUAL_AUTO1,
                    AnimationsX.SWORD_DUAL_AUTO2,
                    AnimationsX.SWORD_DUAL_AUTO3,
                    ExtraAnimations.SWORD_DUAL_AUTO4,
                    ExtraAnimations.SWORD_DUAL_AUTO5
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimationsX.SWORD_DUAL_DASH,
                    AnimationsX.SWORD_DUAL_AIR_SLASH,
                    AnimationsX.DANCING_EDGE
            )
    );
}
