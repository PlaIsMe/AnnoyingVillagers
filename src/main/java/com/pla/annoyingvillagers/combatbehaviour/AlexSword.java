package com.pla.annoyingvillagers.combatbehaviour;

import com.hm.efn.gameasset.animations.EFNSwordAnimations;
import com.pla.annoyingvillagers.gameasset.*;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.ef_awaken.efassets.animations.StraightSwordAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Arrays;

public class AlexSword {
    public static final Builder<MobPatch<?>> THUNDER_DIAMOND_BLADE = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    EFNSwordAnimations.NF_SWORD_AUTO1,
                    EFNSwordAnimations.NF_SWORD_AUTO2,
                    StraightSwordAnimations.STRAIGHTSWORD_AUTO4,
                    StraightSwordAnimations.STRAIGHTSWORD_AUTO3,
                    StraightSwordAnimations.STRAIGHTSWORD_AUTO5
            ),
            CombatCommon.animations(
                    StraightSwordAnimations.STRAIGHTSWORD_DODGE_PURSUIT,
                    EFNSwordAnimations.NF_SWORD_AIRSLASH,
                    EFNSwordAnimations.NF_SWORD_SKILL
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                    AnimsEpicFight.THUNDER_SWEEPING_EDGE
                )
    ).newBehaviorRoot(thunderRoot(AnimsEpicFight.THUNDER_SWEEPING_EDGE));

    public static final Builder<MobPatch<?>> DUAL_THUNDER_DIAMOND_BLADE = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_AUTO_1,
                    AnimsEpicFightAwaken.DP_AUTO_2,
                    AnimsEpicFightAwaken.DP_AUTO_3,
                    AnimsEpicFightAwaken.DP_AUTO_4,
                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
            ),
            CombatCommon.animations(
                    Animations.DAGGER_DUAL_DASH,
                    Animations.LONGSWORD_AUTO2,
                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
            ),
            CombatCommon.animations(
                    AnimsEpicFightAwaken.DP_DASH,
                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                    AnimsEpicFight.THUNDER_DANCING_EDGE
                )
    ).newBehaviorRoot(thunderRoot(AnimsEpicFight.THUNDER_DANCING_EDGE));

    private static BehaviorRoot.Builder<MobPatch<?>> thunderRoot(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation
    ) {
        return BehaviorRoot.builder()
                .priority(2.2D)
                .weight(150.0D)
                .maxCooldown(70)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .withinDistance(0.0D, 6.5D)
                                .animationBehavior(animation, 0.0F)
                );
    }
}
