package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineEnderGlaive {
    public static final Builder<MobPatch<?>> ENDER_GLAIVE = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(WOMAnimations.ENDERSTEP_BACKWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::performEscapeRunAwayWithLowClone)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
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
                            CombatCommon.animations(
                                    AnimsWom.ENDER_GLAIVE_NAPOLEON_AUTO_1,
                                    AnimsWom.ENDER_GLAIVE_NAPOLEON_AUTO_2,
                                    AnimsAgony.AGONY_AUTO_4,
                                    AnimsAgony.AGONY_AUTO_2,
                                    AnimsAgony.AGONY_AUTO_3
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.SPEAR_THRUST
                            ),
                            CombatCommon.animations(
                                    AnimsWom.ENDER_GLAIVE_NAPOLEON_AUSTERLITZ,
                                    Animations.SPEAR_DASH,
                                    AnimsWom.CLONE_ANTITHEUS_AUTO_1,
                                    AnimsWom.CLONE_ANTITHEUS_AUTO_2,
                                    AnimsWom.CLONE_ANTITHEUS_GUILLOTINE,
                                    Animations.SPEAR_TWOHAND_AUTO1,
                                    Animations.SPEAR_TWOHAND_AUTO2,
                                    Animations.SPEAR_TWOHAND_AIR_SLASH,
                                    AnimsAgony.AGONY_AIR_ATTACK_4,
                                    AnimsWom.CLONE_ANTITHEUS_AGRESSION,
                                    WOMAnimations.STAFF_AUTO_2,
                                    WOMAnimations.STAFF_AUTO_3,
                                    AnimsWom.CLONE_ANTITHEUS_AUTO_3,
                                    AnimsWom.CLONE_ANTITHEUS_AUTO_4
                            ),
                            CombatCommon.enderStepRollAnimations())
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(4.0D, 10.0D)
                                            .animationBehavior(AnimsAgony.AGONY_RISING_EAGLE, 0.0F)
                                            .addExBehavior(HerobrineCommon::performAgonySpecialAttack)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .maxCooldown(600)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canChangeToSecondForm)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(AnimsWom.AGONY_GUARD_HIT_1, 0.0F)
                                            .addExBehavior(HerobrineCommon::changeToSecondForm)
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
                                            .withinDistance(2.0D, 8.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AnimsWom.ENDER_GLAIVE_AGONY_AUTO_1, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
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
                                            .withinDistance(2.0D, 8.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AnimsWom.ENDER_GLAIVE_NAPOLEON_SHOOT_3, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormSpecialAnimation)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot(0.0D, 5.0D, HerobrineCommon::canPerformGuarding))
            .newBehaviorRoot(CombatBehaviourTemplates.herobrineJumpRoot());
}
