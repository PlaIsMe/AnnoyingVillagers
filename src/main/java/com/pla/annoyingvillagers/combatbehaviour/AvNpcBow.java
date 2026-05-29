package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightACG;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcBow {
    public static final Builder<MobPatch<?>> BOW = CECombatBehaviors.builder()
            .newBehaviorRoot(swapToMeleeRoot(
                    CombatCommon::usesStepMoveset,
                    Animations.BIPED_STEP_FORWARD,
                    Animations.BIPED_STEP_BACKWARD
            ))
            .newBehaviorRoot(swapToMeleeRoot(
                    CombatCommon::usesRollMoveset,
                    Animations.BIPED_ROLL_BACKWARD,
                    Animations.BIPED_ROLL_FORWARD
            ))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsPugilistSteve.KNIFE_CHECK, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsPugilistSteve.KNIFE_CHECK, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(CombatCommon::usesStepMoveset, Animations.BIPED_STEP_RIGHT))
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(CombatCommon::usesRollMoveset, Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(bowAutoRoot(7.0D, 14.0D, false))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(bowPowerRoot(7.0D, 14.0D, false))
            .newBehaviorRoot(bowPowerRoot(7.0D, 14.0D, false))
            .newBehaviorRoot(bowAutoRoot(7.0D, 80.0D, true))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(bowPowerRoot(7.0D, 80.0D, true))
            .newBehaviorRoot(distanceMovementRoot(
                    CombatCommon::usesStepMoveset,
                    Animations.BIPED_STEP_FORWARD,
                    Animations.BIPED_STEP_BACKWARD
            ))
            .newBehaviorRoot(distanceMovementRoot(
                    CombatCommon::usesRollMoveset,
                    Animations.BIPED_ROLL_BACKWARD,
                    Animations.BIPED_ROLL_FORWARD
            ))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot());

    private static BehaviorRoot.Builder<MobPatch<?>> swapToMeleeRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            yesman.epicfight.api.animation.AnimationManager.AnimationAccessor<? extends yesman.epicfight.api.animation.types.StaticAnimation> firstAnimation,
            yesman.epicfight.api.animation.AnimationManager.AnimationAccessor<? extends yesman.epicfight.api.animation.types.StaticAnimation> secondAnimation
    ) {
        return BehaviorRoot.builder()
                .priority(2.0D)
                .weight(100.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(movesetCondition)
                                .custom(CombatCommon::isNotRiding)
                                .withinDistance(0.0D, 5.0D)
                                .animationBehavior(firstAnimation, 0.0F)
                                .addExBehavior(CombatCommon::swapToMelee)
                )
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(movesetCondition)
                                .custom(CombatCommon::isNotRiding)
                                .withinDistance(0.0D, 5.0D)
                                .animationBehavior(secondAnimation, 0.0F)
                                .addExBehavior(CombatCommon::swapToMelee)
                );
    }

    private static BehaviorRoot.Builder<MobPatch<?>> bowAutoRoot(
            double minDistance,
            double maxDistance,
            boolean requireDragonTarget
    ) {
        Behavior.Builder<MobPatch<?>> first = Behavior.builder();
        Behavior.Builder<MobPatch<?>> second = Behavior.builder();
        Behavior.Builder<MobPatch<?>> third = Behavior.builder();
        if (requireDragonTarget) {
            first = first.custom(CombatCommon::isTargetingHerobrineDragon);
            second = second.custom(CombatCommon::isTargetingHerobrineDragon);
            third = third.custom(CombatCommon::isTargetingHerobrineDragon);
        }

        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(40.0D)
                .addFirstBehavior(
                        first
                                .withinDistance(minDistance, maxDistance)
                                .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                .addNextBehavior(
                                        second
                                                .withinDistance(minDistance, maxDistance)
                                                .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                                .addNextBehavior(
                                                        third
                                                                .withinDistance(minDistance, maxDistance)
                                                                .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                                )
                                )
                );
    }

    private static BehaviorRoot.Builder<MobPatch<?>> bowPowerRoot(
            double minDistance,
            double maxDistance,
            boolean requireDragonTarget
    ) {
        Behavior.Builder<MobPatch<?>> first = Behavior.builder().custom(CombatCommon::isNotRiding);
        Behavior.Builder<MobPatch<?>> second = Behavior.builder().custom(CombatCommon::isNotRiding);
        if (requireDragonTarget) {
            first = first.custom(CombatCommon::isTargetingHerobrineDragon);
            second = second.custom(CombatCommon::isTargetingHerobrineDragon);
        }

        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(10.0D)
                .maxCooldown(100)
                .addFirstBehavior(
                        first
                                .withinDistance(minDistance, maxDistance)
                                .animationBehavior(AnimsEpicFightACG.BOW_AUTO_3, 0.0F)
                )
                .addFirstBehavior(
                        second
                                .withinDistance(minDistance, maxDistance)
                                .animationBehavior(AnimsEpicFightACG.BOW_AUTO_5, 0.0F)
                );
    }

    private static BehaviorRoot.Builder<MobPatch<?>> distanceMovementRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            yesman.epicfight.api.animation.AnimationManager.AnimationAccessor<? extends yesman.epicfight.api.animation.types.StaticAnimation> firstAnimation,
            yesman.epicfight.api.animation.AnimationManager.AnimationAccessor<? extends yesman.epicfight.api.animation.types.StaticAnimation> secondAnimation
    ) {
        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(10.0D)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(movesetCondition)
                                .custom(CombatCommon::isNotRiding)
                                .withinDistance(7.0D, 14.0D)
                                .animationBehavior(firstAnimation, 0.0F)
                )
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(movesetCondition)
                                .custom(CombatCommon::isNotRiding)
                                .withinDistance(7.0D, 14.0D)
                                .animationBehavior(secondAnimation, 0.0F)
                );
    }
}
