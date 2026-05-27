package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.efkick.gameasset.EFKickAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NpcAxe {
    public static final Builder<MobPatch<?>> AXE = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canExecute)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                            .addExBehavior(CombatCommon::performExecute)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBlockToEscape)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(80.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    addAxeRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20)
                    )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AnimsPugilistSteve.AXE_HEAVY_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.AXE_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 3.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.AXE_FUN_SKILL, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.AXE_DASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.AXE_AIRSLASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.THE_GUILLOTINE, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlAway)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(2.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_1, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_2, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_3, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_4, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_C, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_RUSH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(EFKickAnimations.KICK_H, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
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
                            )
            );

    public static final Builder<MobPatch<?>> AV_AXE = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canExecute)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                            .addExBehavior(CombatCommon::performExecute)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBlockToEscape)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(80.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    addAvAxeRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20)
                    )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlAway)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
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
                            )
            );

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addAxeRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        Animations.AXE_AUTO1,
                        Animations.AXE_AUTO2
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                        AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                        AnimsPugilistSteve.AXE_FUN_SKILL
                ),
                CombatCommon.animations(
                        Animations.AXE_DASH,
                        Animations.AXE_AIRSLASH,
                        Animations.THE_GUILLOTINE
                ),
                CombatCommon.kickAnimations(),
                CombatCommon.stepAnimations()
        );
    }


    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addAvAxeRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        Animations.AXE_AUTO1,
                        Animations.AXE_AUTO2,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                        AnimsPugilistSteve.AXE_FUN_SKILL,
                        AnimsPugilistSteve.AXE_HEAVY_AUTO_2
                ),
                CombatCommon.animations(
                        Animations.AXE_DASH,
                        Animations.THE_GUILLOTINE,
                        Animations.AXE_AIRSLASH
                ),
                CombatCommon.kickAnimations(),
                CombatCommon.stepAnimations()
        );
    }

}
