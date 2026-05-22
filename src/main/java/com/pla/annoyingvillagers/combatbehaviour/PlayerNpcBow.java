package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightACG;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcBow {
    public static final Builder<MobPatch<?>> BOW = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
            )
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
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEatingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(7.0D, 14.0D)
                                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(7.0D, 14.0D)
                                                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_3, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_5, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_3, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_5, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                                            .withinDistance(7.0D, 80.0D)
                                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                                                            .withinDistance(7.0D, 80.0D)
                                                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_1, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_3, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightACG.BOW_AUTO_5, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(60.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
                            )
            );
}
