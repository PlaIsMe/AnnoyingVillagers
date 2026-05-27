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

public class NpcFist {
    public static final Builder<MobPatch<?>> FIST = CECombatBehaviors.builder()
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
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
            )
//            .newBehaviorRoot(
//                    BehaviorRoot.builder()
//                            .priority(3.0D)
//                            .weight(1000.0D)
//                            .maxCooldown (0)
//                            .addFirstBehavior(
//                                    Behavior.builder()
//                                            .custom(CombatCommon::canPerformNormalAttackLogic)
//                                            .custom(CombatCommon::canAttackWhileNotHealing)
//                                            .custom(CombatCommon::isWrongWeapon)
//                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
//                                            .addExBehavior(CombatCommon::swapToMelee)
//                            )
//            )
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
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .custom(CombatCommon::canAttackWhileNotHealing)
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
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    addFistRandomCombatChains(
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
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlAway)
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

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addFistRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        Animations.FIST_AUTO1,
                        Animations.FIST_AUTO2,
                        Animations.FIST_AUTO3
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.FIST_LEFT,
                        Animations.FIST_DASH,
                        AnimsPugilistSteve.FIST_UP,
                        AnimsPugilistSteve.WHIRLWIND_KICK_LEFT,
                        AnimsPugilistSteve.FIST_DASH,
                        AnimsPugilistSteve.WHIRLWIND_KICK
                ),
                CombatCommon.animations(
                        Animations.FIST_AIR_SLASH,
                        Animations.RELENTLESS_COMBO
                ),
                CombatCommon.animations(
                        EFKickAnimations.KICK_1,
                        EFKickAnimations.KICK_C,
                        EFKickAnimations.KICK_2,
                        EFKickAnimations.KICK_RUSH,
                        EFKickAnimations.KICK_COMBO,
                        EFKickAnimations.KICK_3,
                        EFKickAnimations.KICK_H,
                        EFKickAnimations.KICK_4
                ),
                CombatCommon.stepAnimations()
        );
    }

}
