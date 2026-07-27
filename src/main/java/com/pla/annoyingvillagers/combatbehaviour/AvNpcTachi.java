package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcTachi {
    public static final Builder<MobPatch<?>> TACHI = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    Animations.TACHI_AUTO1,
                    Animations.TACHI_AUTO2,
                    Animations.TACHI_AUTO3
            ),
            CombatCommon.animations(
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
            ),
            CombatCommon.animations(
                    Animations.TACHI_DASH,
                    Animations.RUSHING_TEMPO1,
                    Animations.RUSHING_TEMPO2,
                    Animations.LONGSWORD_AIR_SLASH,
                    Animations.RUSHING_TEMPO3
            )
    );

    public static final Builder<MobPatch<?>> DIAMOND_WARBLADE = avTachiMoveset(
            AnimsWom.WARBLADE_SATSUJIN_TSUKUYOMI
    );

    public static final Builder<MobPatch<?>> DIAMOND_LAEVATEINN = avTachiMoveset(
            AnimsEpicFightBattleArts.TACHI_BLOSSOM_SLASH
    );

    public static final Builder<MobPatch<?>> FALCHION = avTachiMoveset(
            AnimsEpicFightAwaken.CUT_LEFT_DP_PHANTOM_DANCE_END_1_ENHANCED
    );

    public static final Builder<MobPatch<?>> DUAL_FALCHION = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_1,
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_2,
                    AnimsEpicFightAwaken.DP_SHADOW_LUNGE_1,
                    AnimsEpicFightAwaken.DP_SHADOW_LUNGE_2,
                    AnimsEpicFightAwaken.DP_SHADOW_LUNGE_3
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_HEAVY_AUTO_4_SPC,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsEpicFightAwaken.DP_PHANTOM_DANCE_END_2_ENHANCED
            )
    );

    private static Builder<MobPatch<?>> avTachiMoveset(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> innate
    ) {
        return AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                        AnimsEpicFightAwaken.CUT_LEFT_DP_SHADOW_LUNGE_2,
                        AnimsEpicFightAwaken.CUT_LEFT_DP_SHADOW_LUNGE_1,
                        AnimsRuine.RUINE_AUTO_1,
                        AnimsRuine.RUINE_CHATIMENT,
                        AnimsEpicFightAwaken.CUT_LEFT_DP_SHADOW_LUNGE_3
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                ),
                CombatCommon.animations(
                        AnimsEpicFightAwaken.DP_HEAVY_AUTO_4_SPC,
                        AnimsRuine.RUINE_COMET,
                        innate
                )
        );
    }
}
