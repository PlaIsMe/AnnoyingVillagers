package com.pla.annoyingvillagers.combatbehaviour;

import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Arrays;

final class AvNpcCombatBehaviorBuilder {
    private static final int WEAPON_ENDER_PEARL_AWAY_COOLDOWN = 60;

    private AvNpcCombatBehaviorBuilder() {
    }

    @SafeVarargs
    static Builder<MobPatch<?>> weapon(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return CECombatBehaviors.builder()
                .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
                .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(CombatCommon::usesStepMoveset, Animations.BIPED_STEP_BACKWARD))
                .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(CombatCommon::usesRollMoveset, Animations.BIPED_ROLL_BACKWARD))
                .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(
                        CombatCommon::usesStepMoveset,
                        Animations.BIPED_STEP_BACKWARD,
                        Animations.BIPED_STEP_FORWARD
                ))
                .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(
                        CombatCommon::usesRollMoveset,
                        Animations.BIPED_ROLL_BACKWARD,
                        Animations.BIPED_ROLL_FORWARD
                ))
                .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
                .newBehaviorRoot(combatRoot(
                        CombatCommon::usesStepMoveset,
                        CombatCommon.stepAnimations(),
                        CombatCommon.kickAnimations(),
                        opener,
                        groups
                ))
                .newBehaviorRoot(combatRoot(
                        CombatCommon::usesRollMoveset,
                        CombatCommon.rollAnimations(),
                        CombatCommon.kickAnimations(),
                        opener,
                        groups
                ))
                .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(WEAPON_ENDER_PEARL_AWAY_COOLDOWN, false))
                .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
                .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
    }

    @SafeVarargs
    static Builder<MobPatch<?>> fist(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return CECombatBehaviors.builder()
                .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
                .newBehaviorRoot(escapeRunAwayRoot(CombatCommon::usesStepMoveset, Animations.BIPED_STEP_BACKWARD))
                .newBehaviorRoot(escapeRunAwayRoot(CombatCommon::usesRollMoveset, Animations.BIPED_ROLL_BACKWARD))
                .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(CombatCommon::usesStepMoveset, Animations.BIPED_STEP_BACKWARD))
                .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(CombatCommon::usesRollMoveset, Animations.BIPED_ROLL_BACKWARD))
                .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(
                        CombatCommon::usesStepMoveset,
                        Animations.BIPED_STEP_RIGHT,
                        Animations.BIPED_STEP_LEFT,
                        Animations.BIPED_STEP_BACKWARD,
                        Animations.BIPED_STEP_FORWARD
                ))
                .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(
                        CombatCommon::usesRollMoveset,
                        Animations.BIPED_ROLL_BACKWARD,
                        Animations.BIPED_ROLL_FORWARD
                ))
                .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot(true))
                .newBehaviorRoot(combatRoot(
                        CombatCommon::usesStepMoveset,
                        CombatCommon.stepAnimations(),
                        CombatCommon.fistKickAnimations(),
                        opener,
                        groups
                ))
                .newBehaviorRoot(combatRoot(
                        CombatCommon::usesRollMoveset,
                        CombatCommon.rollAnimations(),
                        CombatCommon.fistKickAnimations(),
                        opener,
                        groups
                ))
                .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
                .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
    }

    @SafeVarargs
    private static BehaviorRoot.Builder<MobPatch<?>> combatRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] movementAnimations,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] kickAnimations,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] opener,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[]... groups
    ) {
        return CombatCommon.addRandomCombatChains(
                BehaviorRoot.builder()
                        .priority(1.0D)
                        .weight(40.0D)
                        .maxCooldown(20),
                CombatCommon.conditions(movesetCondition),
                opener,
                appendMovement(groups, kickAnimations, movementAnimations)
        );
    }

    private static AnimationManager.AnimationAccessor<? extends StaticAnimation>[][] appendMovement(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[][] groups,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] kickAnimations,
            AnimationManager.AnimationAccessor<? extends StaticAnimation>[] movementAnimations
    ) {
        AnimationManager.AnimationAccessor<? extends StaticAnimation>[][] result = Arrays.copyOf(groups, groups.length + 2);
        result[groups.length] = kickAnimations;
        result[groups.length + 1] = movementAnimations;
        return result;
    }

    private static BehaviorRoot.Builder<MobPatch<?>> escapeRunAwayRoot(
            CombatCommon.MobPatchCondition movesetCondition,
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation
    ) {
        return BehaviorRoot.builder()
                .priority(3.0D)
                .weight(1000.0D)
                .maxCooldown(0)
                .addFirstBehavior(
                        CECombatBehaviors.Behavior.builder()
                                .custom(movesetCondition)
                                .custom(CombatCommon::canEscape)
                                .withinDistance(0.0D, 8.0D)
                                .animationBehavior(animation, 0.0F)
                                .addExBehavior(CombatCommon::performEscapeRunAway)
                )
                .addFirstBehavior(
                        CECombatBehaviors.Behavior.builder()
                                .custom(movesetCondition)
                                .custom(CombatCommon::canAttackWhileNotHealing)
                                .custom(CombatCommon::canEscape)
                                .withinDistance(0.0D, 48.0D)
                                .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                .addExBehavior(CombatCommon::swapToMelee)
                );
    }
}
