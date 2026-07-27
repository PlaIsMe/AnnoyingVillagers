package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineEnderAegis {
    public static final Builder<MobPatch<?>> ENDER_AEGIS = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
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
                            .maxCooldown(20)
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
                            CombatCommon.steps(
                                    AnimsHerrscher.HERRSCHER_AUTO_1,
                                    AnimsHerrscher.HERRSCHER_AUTO_2,
                                    AnimsHerrscher.HERRSCHER_AUTO_3,
                                    AnimsWom.ENDER_AEGIS_MOONLESS_AUTO_1,
                                    AnimsWom.ENDER_AEGIS_MOONLESS_AUTO_2
                            ),
                            CombatCommon.steps(
                                    AnimsWom.ENDER_AEGIS_BULL_CHARGE,
                                    AnimsSolar.SOLAR_QUEMADURA,
                                    AnimsSolar.SOLAR_HORNO,
                                    AnimsSolar.SOLAR_OBSCURIDAD_IMPACTO
                            ),
                            CombatCommon.steps(
                                    AnimsWom.ENDER_AEGIS_MOONLESS_AUTO_1,
                                    AnimsWom.ENDER_AEGIS_MOONLESS_AUTO_2,
                                    AnimsHerrscher.HERRSCHER_AUTO_2,
                                    AnimsHerrscher.HERRSCHER_AUTO_3,
                                    AnimsSolar.SOLAR_QUEMADURA
                            ),
                            CombatCommon.steps(
                                    CombatCommon.guard(40, 6.0D, HerobrineCommon::canPerformGuarding)
                            ))
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsWom.ENDER_AEGIS_BULL_CHARGE, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 5.0D)
                                                            .animationBehavior(AnimsWom.ENDER_AEGIS_BULL_CHARGE, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 5.0D)
                                                                            .animationBehavior(AnimsWom.ENDER_AEGIS_BULL_CHARGE, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsSolar.SOLAR_OBSCURIDAD_IMPACTO, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsSolar.SOLAR_HORNO, 0.0F)
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
                                            .animationBehavior(AnimsWom.ENDER_AEGIS_NAPOLEON_RELOAD_1, 0.0F)
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
                                            .withinDistance(0.0D, 8.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AnimsEpicFight.AEGIS_SHIELD_SHOOT, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot(HerobrineCommon::canPerformGuarding))
            .newBehaviorRoot(
                    CombatCommon.addAnimationBehaviors(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(10.0D),
                            0.0D,
                            5.0D,
                            CombatCommon.enderStepRollAnimations()
                    )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.herobrineJumpRoot());
}
