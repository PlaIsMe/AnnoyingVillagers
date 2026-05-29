package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcLongsword {
    public static final Builder<MobPatch<?>> LONGSWORD_SHIELD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.LONGSWORD_AUTO1,
                    Animations.LONGSWORD_AUTO2,
                    Animations.LONGSWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.LONGSWORD_DASH,
                    Animations.LONGSWORD_AIR_SLASH,
                    Animations.BATTOJUTSU,
                    Animations.SHARP_STAB
            )
    );

    public static final Builder<MobPatch<?>> LONGSWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.LONGSWORD_AUTO1,
                    Animations.LONGSWORD_AUTO2,
                    Animations.LONGSWORD_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.LONGSWORD_DASH,
                    Animations.LONGSWORD_AIR_SLASH,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO3
            )
    );

    public static final Builder<MobPatch<?>> AV_LONGSWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_AUTO_2,
                    AnimsRuine.RUINE_AUTO_3,
                    AnimsRuine.RUINE_CHATIMENT
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.HOOK_GROUND,
                    AnimsRuine.RUINE_COMET,
                    AnimsEpicFightAwaken.DP_FALLING_SHADOW
            )
    );

    public static final Builder<MobPatch<?>> DUAL_AV_LONGSWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_3,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO3,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO5
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.HOOK_GROUND,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsEpicFightAwaken.DP_FALLING_SHADOW
            )
    );

    public static final Builder<MobPatch<?>> CHIPPED_LONGSWORD = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_AUTO_2,
                    AnimsRuine.RUINE_AUTO_3,
                    AnimsRuine.RUINE_EXPIATION_1,
                    AnimsRuine.RUINE_EXPIATION_2
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsRuine.RUINE_EXPIATION,
                    AnimsRuine.RUINE_REDEMPTION
            )
    );

    public static final Builder<MobPatch<?>> DIAMOND_SABRE = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.LONGSWORD_LIECHTENAUER_AUTO1,
                    Animations.LONGSWORD_LIECHTENAUER_AUTO2,
                    AnimsRuine.RUINE_AUTO_1,
                    AnimsRuine.RUINE_AUTO_2,
                    AnimsEpicFightBattleArts.SABRE_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    AnimsEpicFightBattleArts.SABRE_DASH_ATTACK,
                    AnimsEpicFightBattleArts.SABRE_AIR_ATTACK,
                    AnimsEpicFightBattleArts.SABRE_QUAD_STING
            )
    );
}
