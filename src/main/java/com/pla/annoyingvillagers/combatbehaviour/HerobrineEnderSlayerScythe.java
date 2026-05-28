package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsEnderblaster;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineEnderSlayerScythe {
    public static final Builder<MobPatch<?>> ENDER_SLAYER_SCYTHE = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::performEscapeRunAwayWithLowClone)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.herobrineHealingRoot())
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(AnimsWom.HEROBRINE_MOB_ENDERSTEP_OBSCURIS, 0.0F)
                                            .addExBehavior(HerobrineCommon::giveSlowFalling)
                            )
            )
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.conditions(CombatCommon::isNotRiding),
                            CombatCommon.animations(
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_1,
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_2,
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_3,
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_4,
                                    AnimsWom.CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK
                            ),
                            CombatCommon.enderStepAnimations(),
                            CombatCommon.animations(
                                    WOMAnimations.KICK_AUTO_1,
                                    WOMAnimations.KICK_AUTO_2,
                                    WOMAnimations.KICK_AUTO_3
                            ),
                            CombatCommon.enderStepAnimations(),
                            CombatCommon.animations(
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_1,
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_2,
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_3,
                                    AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_4,
                                    AnimsWom.CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK
                            ))
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 6.0D)
                                            .animationBehavior(AnimsEnderblaster.ENDERBLASTER_TWOHAND_AUTO_1, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 4.0D)
                                            .animationBehavior(AnimsWom.CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::isNotRiding)
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 4.0D)
                                            .animationBehavior(WOMAnimations.KICK_AUTO_2, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(600)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canChangeToSecondForm)
                                            .withinDistance(2.0D, 90.0D)
                                            .animationBehavior(AnimsWom.AGONY_GUARD_HIT_1, 0.0F)
                                            .addExBehavior(HerobrineCommon::changeToSecondForm)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(25)
                            .maxCooldown(600)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canMountOrDismountDragon)
                                            .withinDistance(2.0D, 90.0D)
                                            .animationBehavior(AnimsPugilistSteve.POSE_UP, 0.0F)
                                            .addExBehavior(HerobrineCommon::mountOrDismountDragon)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(25)
                            .maxCooldown(300)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 90.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .custom(HerobrineCommon::canCastThunder)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(25)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 90.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .custom(HerobrineCommon::canCastMeteorite)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormSpecialAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(25)
                            .maxCooldown(900)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 90.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .custom(HerobrineCommon::canRespawnCrystal)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_INWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::respawnCrystal)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot(0.0D, 5.0D, HerobrineCommon::canPerformGuarding))
            .newBehaviorRoot(
                    CombatCommon.addAnimationBehaviors(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(10.0D),
                            0.0D,
                            5.0D,
                            CombatCommon.conditions(CombatCommon::isNotRiding),
                            CombatCommon.enderStepRollAnimations()
                    )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.herobrineJumpRoot());
}
