package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public final class CombatBehaviourTemplates {
    private CombatBehaviourTemplates() {
    }

    private static Behavior.Builder<MobPatch<?>> withCondition(
            Behavior.Builder<MobPatch<?>> behavior,
            CombatCommon.MobPatchCondition condition
    ) {
        return condition == null ? behavior : behavior.custom(condition);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> executionRoot() {
        return executionRoot(4.0D);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> executionRoot(double priority) {
        return BehaviorRoot.builder()
                .priority(priority)
                .weight(1000.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canExecute)
                                .withinDistance(0.0D, 5.0D)
                                .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                .addExBehavior(CombatCommon::performExecute)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot() {
        return escapeWithGuardRoot(Animations.BIPED_STEP_BACKWARD);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation
    ) {
        return escapeWithGuardRoot(3.0D, escapeAnimation, false);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation
    ) {
        return escapeWithGuardRoot(3.0D, movesetCondition, escapeAnimation, false);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            boolean requireNormalAttackLogic
    ) {
        return escapeWithGuardRoot(3.0D, escapeAnimation, requireNormalAttackLogic);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            boolean requireNormalAttackLogic
    ) {
        return escapeWithGuardRoot(3.0D, movesetCondition, escapeAnimation, requireNormalAttackLogic);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot(
            double priority,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            boolean requireNormalAttackLogic
    ) {
        return escapeWithGuardRoot(priority, null, escapeAnimation, requireNormalAttackLogic);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithGuardRoot(
            double priority,
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            boolean requireNormalAttackLogic
    ) {
        Behavior.Builder<MobPatch<?>> escapeBehavior = withCondition(Behavior.builder(), movesetCondition);
        if (requireNormalAttackLogic) {
            escapeBehavior = escapeBehavior.custom(CombatCommon::canPerformNormalAttackLogic);
        }

        return BehaviorRoot.builder()
                .priority(priority)
                .weight(1000.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        escapeBehavior
                                .custom(CombatCommon::canEscape)
                                .withinDistance(0.0D, 8.0D)
                                .animationBehavior(escapeAnimation, 0.0F)
                                .addExBehavior(CombatCommon::swapToBlockToEscape)
                )
                .addFirstBehavior(
                        withCondition(Behavior.builder(), movesetCondition)
                                .custom(CombatCommon::canEscape)
                                .withinDistance(0.0D, 48.0D)
                                .guard(40)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithAnimationRoot(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> followUpAnimation
    ) {
        return escapeWithAnimationRoot(3.0D, escapeAnimation, followUpAnimation, false);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithAnimationRoot(
            double priority,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> followUpAnimation,
            boolean requireNormalAttackLogic
    ) {
        return escapeWithAnimationRoot(priority, null, escapeAnimation, followUpAnimation, requireNormalAttackLogic);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithAnimationRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> followUpAnimation
    ) {
        return escapeWithAnimationRoot(3.0D, movesetCondition, escapeAnimation, followUpAnimation, false);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> escapeWithAnimationRoot(
            double priority,
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> escapeAnimation,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> followUpAnimation,
            boolean requireNormalAttackLogic
    ) {
        Behavior.Builder<MobPatch<?>> escapeBehavior = withCondition(Behavior.builder(), movesetCondition);
        if (requireNormalAttackLogic) {
            escapeBehavior = escapeBehavior.custom(CombatCommon::canPerformNormalAttackLogic);
        }

        return BehaviorRoot.builder()
                .priority(priority)
                .weight(1000.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        escapeBehavior
                                .custom(CombatCommon::canEscape)
                                .withinDistance(0.0D, 8.0D)
                                .animationBehavior(escapeAnimation, 0.0F)
                                .addExBehavior(CombatCommon::swapToBlockToEscape)
                )
                .addFirstBehavior(
                        withCondition(Behavior.builder(), movesetCondition)
                                .custom(CombatCommon::canEscape)
                                .withinDistance(0.0D, 48.0D)
                                .animationBehavior(followUpAnimation, 0.0F)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> eatingRoot() {
        return eatingRoot(Animations.BIPED_STEP_BACKWARD);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> eatingRoot(
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation
    ) {
        return eatingRoot(null, animation);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> eatingRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation
    ) {
        return BehaviorRoot.builder()
                .priority(2.0D)
                .weight(70.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        withCondition(Behavior.builder(), movesetCondition)
                                .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                .custom(CombatCommon::canPerformEating)
                                .animationBehavior(animation, 0.0F)
                                .addExBehavior(CombatCommon::performEatingAnimation)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> swapToBowRoot() {
        return swapToBowRoot(Animations.BIPED_STEP_BACKWARD, Animations.BIPED_STEP_FORWARD);
    }

    @SafeVarargs
    public static BehaviorRoot.Builder<MobPatch<?>> swapToBowRoot(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... animations
    ) {
        return swapToBowRoot(null, animations);
    }

    @SafeVarargs
    public static BehaviorRoot.Builder<MobPatch<?>> swapToBowRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... animations
    ) {
        BehaviorRoot.Builder<MobPatch<?>> root = BehaviorRoot.builder()
                .priority(2.0D)
                .weight(100.0D)
                .maxCooldown(120);

        for (AnimationManager.AnimationAccessor<? extends StaticAnimation> animation : animations) {
            root = root.addFirstBehavior(
                    withCondition(Behavior.builder(), movesetCondition)
                            .custom(CombatCommon::canPerformNormalAttackLogic)
                            .custom(CombatCommon::canSwapToBow)
                            .withinDistance(7.0D, 14.0D)
                            .animationBehavior(animation, 0.0F)
                            .addExBehavior(CombatCommon::swapToBow)
            );
        }

        return root;
    }

    public static BehaviorRoot.Builder<MobPatch<?>> enderPearlToTargetRoot() {
        return enderPearlToTargetRoot(false);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> combatFishingRodRoot() {
        return BehaviorRoot.builder()
                .priority(2.0D)
                .weight(55.0D)
                .maxCooldown(35)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .custom(CombatCommon::canUseNpcCombatFishingRod)
                                .withinDistance(0.0D, 32.0D)
                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
                                .addExBehavior(CombatCommon::performNpcCombatFishingRod)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> combatFishingRodEscapeRoot() {
        return BehaviorRoot.builder()
                .priority(3.0D)
                .weight(1000.0D)
                .maxCooldown(35)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canEscape)
                                .custom(CombatCommon::canUseNpcCombatFishingRodEscape)
                                .withinDistance(0.0D, 32.0D)
                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
                                .addExBehavior(CombatCommon::performNpcCombatFishingRodEscape)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> villagerKnightLavaBucketRoot() {
        return BehaviorRoot.builder()
                .priority(2.0D)
                .weight(30.0D)
                .maxCooldown(120)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .custom(CombatCommon::isGeneral)
                                .custom(CombatCommon::canUseVillagerKnightLavaBucket)
                                .withinDistance(0.0D, 5.0D)
                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
                                .addExBehavior(CombatCommon::performVillagerKnightLavaBucket)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> enderPearlToTargetRoot(boolean requireAttackWhileNotHealing) {
        Behavior.Builder<MobPatch<?>> behavior = Behavior.builder()
                .custom(CombatCommon::canPerformNormalAttackLogic)
                .custom(CombatCommon::canThrowEnderPearl);

        if (requireAttackWhileNotHealing) {
            behavior = behavior.custom(CombatCommon::canAttackWhileNotHealing);
        }

        return BehaviorRoot.builder()
                .priority(2.0D)
                .weight(80.0D)
                .maxCooldown(120)
                .addFirstBehavior(
                        behavior
                                .withinDistance(7.0D, 48.0D)
                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
                                .addExBehavior(CombatCommon::performEnderPearlToTarget)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> enderPearlAwayRoot(boolean requireAttackWhileNotHealing) {
        return enderPearlAwayRoot(40, requireAttackWhileNotHealing);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> enderPearlAwayRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            boolean requireAttackWhileNotHealing
    ) {
        return enderPearlAwayRoot(40, movesetCondition, requireAttackWhileNotHealing);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> enderPearlAwayRoot(
            int maxCooldown,
            boolean requireAttackWhileNotHealing
    ) {
        return enderPearlAwayRoot(maxCooldown, null, requireAttackWhileNotHealing);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> enderPearlAwayRoot(
            int maxCooldown,
            CombatCommon.MobPatchCondition movesetCondition,
            boolean requireAttackWhileNotHealing
    ) {
        Behavior.Builder<MobPatch<?>> behavior = withCondition(Behavior.builder(), movesetCondition)
                .custom(CombatCommon::canPerformNormalAttackLogic)
                .withinDistance(0.0D, 3.0D)
                .custom(CombatCommon::canThrowEnderPearl);

        if (requireAttackWhileNotHealing) {
            behavior = behavior.custom(CombatCommon::canAttackWhileNotHealing);
        }

        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(10.0D)
                .maxCooldown(maxCooldown)
                .addFirstBehavior(
                        behavior
                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F)
                                .addExBehavior(CombatCommon::performEnderPearlAway)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> guardRoot() {
        return guardRoot(0.0D, CombatCommon::canPerformGuarding);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> guardRoot(double minDistance) {
        return guardRoot(minDistance, CombatCommon::canPerformGuarding);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> guardRoot(CombatCommon.MobPatchCondition guardCondition) {
        return guardRoot(0.0D, guardCondition);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> guardRoot(
            double minDistance,
            CombatCommon.MobPatchCondition guardCondition
    ) {
        return guardRoot(minDistance, 3.0D, guardCondition);
    }

    public static BehaviorRoot.Builder<MobPatch<?>> guardRoot(
            double minDistance,
            double maxDistance,
            CombatCommon.MobPatchCondition guardCondition
    ) {
        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(15.0D)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .withinDistance(minDistance, maxDistance)
                                .custom(guardCondition)
                                .guard(40)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> jumpRoot() {
        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(40.0D)
                .maxCooldown(160)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .custom(CombatCommon::canJump)
                                .withinDistance(5.0D, 14.0D)
                                .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                .addExBehavior(CombatCommon::jump)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> herobrineHealingRoot() {
        return BehaviorRoot.builder()
                .priority(2.0D)
                .weight(70.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                .custom(HerobrineCommon::canPerformHealing)
                                .animationBehavior(AVAnimations.POINT_LEFT_HAND_UP, 0.0F)
                                .addExBehavior(HerobrineCommon::performHealingAnimation)
                );
    }

    public static BehaviorRoot.Builder<MobPatch<?>> herobrineJumpRoot() {
        return BehaviorRoot.builder()
                .priority(1.0D)
                .weight(20.0D)
                .maxCooldown(160)
                .addFirstBehavior(
                        Behavior.builder()
                                .custom(CombatCommon::canPerformNormalAttackLogic)
                                .custom(HerobrineCommon::canJump)
                                .withinDistance(5.0D, 14.0D)
                                .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                .addExBehavior(HerobrineCommon::jump)
                );
    }
}
