package com.pla.annoyingvillagers.compat.epicfightx;

import com.asanginxst.epicfightx.gameassets.animations.AnimationsX;
import com.asanginxst.epicfightx.gameassets.animations.ExtraAnimations;
import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcXLongsword {
        public static final Builder<MobPatch<?>> X_LONGSWORD_SHIELD = AvNpcCombatBehaviorBuilder.weapon(
                CombatCommon.animations(
                        AnimationsX.LONGSWORD_AUTO1,
                        AnimationsX.LONGSWORD_AUTO2,
                        AnimationsX.LONGSWORD_AUTO3,
                        ExtraAnimations.LONGSWORD_AUTO4,
                        ExtraAnimations.LONGSWORD_AUTO5
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                ),
                CombatCommon.animations(
                        AnimationsX.LONGSWORD_DASH,
                        AnimationsX.LONGSWORD_AIR_SLASH,
                        AnimationsX.BATTOJUTSU,
                        AnimationsX.SHARP_STAB
                )
        );

        public static final Builder<MobPatch<?>> X_LONGSWORD = AvNpcCombatBehaviorBuilder.weapon(
                CombatCommon.animations(
                        AnimationsX.LONGSWORD_AUTO1,
                        AnimationsX.LONGSWORD_AUTO2,
                        AnimationsX.LONGSWORD_AUTO3,
                        ExtraAnimations.LONGSWORD_AUTO4,
                        ExtraAnimations.LONGSWORD_AUTO5
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                ),
                CombatCommon.animations(
                        ExtraAnimations.LONGSWORD_TWOHAND_DASH,
                        ExtraAnimations.LONGSWORD_TWOHAND_AIR_SLASH,
                        AnimationsX.LONGSWORD_LIECHTENAUER_AUTO1,
                        AnimationsX.LONGSWORD_LIECHTENAUER_AUTO2,
                        AnimationsX.LONGSWORD_LIECHTENAUER_AUTO3,
                        ExtraAnimations.LONGSWORD_LIECHTENAUER_AUTO4,
                        ExtraAnimations.LONGSWORD_LIECHTENAUER_AUTO5
                )
        );
}
