package com.pla.annoyingvillagers.compat.p1nero_bow;

import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightACG;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NpcP1neroBow {
    public static final CECombatBehaviors.Builder<MobPatch<?>> BOW = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsP1neroEpicBow.P1NERO_MOB_BOW_JUMP_ATTACK, 0.0F)
                                            .addExBehavior(CombatCommon::shortPillarJump)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsPugilistSteve.KNIFE_CHECK, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsPugilistSteve.KNIFE_CHECK, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsP1neroEpicBow.P1NERO_MOB_BOW_AUTO1, 0.0F)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsP1neroEpicBow.P1NERO_MOB_BOW_AUTO2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(20.0D, 25.0D)
                                            .animationBehavior(AnimsP1neroEpicBow.P1NERO_MOB_BOW_DASH_ATTACK, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsP1neroEpicBow.P1NERO_MOB_BOW_AUTO1, 0.0F)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsP1neroEpicBow.P1NERO_MOB_BOW_AUTO2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::hasClearBowShot)
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(60.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
                            )
            );
}
