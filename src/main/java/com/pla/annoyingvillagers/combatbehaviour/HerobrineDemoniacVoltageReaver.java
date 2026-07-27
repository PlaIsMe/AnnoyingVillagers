package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineDemoniacVoltageReaver {
    public static final Builder<MobPatch<?>> DEMONIAC_VOLTAGE_REAVER = CECombatBehaviors.builder()
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
                                    AnimsWom.DEMONIAC_RUINE_AUTO_1,
                                    WOMAnimations.TORMENT_BERSERK_AUTO_2,
                                    WOMAnimations.TORMENT_BERSERK_AUTO_1,
                                    AnimsWom.DEMONIAC_RUINE_AUTO_2,
                                    AnimsWom.DEMONIAC_RUINE_AUTO_4
                            ),
                            CombatCommon.animations(
                                    AnimsWom.CLONE_ENDERBLASTER_ONEHAND_DASH,
                                    WOMAnimations.TORMENT_AUTO_1,
                                    WOMAnimations.TORMENT_DASH,
                                    AnimsWom.DEMONIAC_RUINE_COMET,
                                    AnimsRuine.RUINE_CHATIMENT,
                                    WOMAnimations.TORMENT_AUTO_2,
                                    WOMAnimations.TORMENT_AUTO_3,
                                    WOMAnimations.TORMENT_CHARGED_ATTACK_3,
                                    AnimsWom.DEMONIAC_TORMENT_CHARGED_ATTACK_2,
                                    WOMAnimations.TORMENT_AIRSLAM,
                                    WOMAnimations.TORMENT_BERSERK_DASH,
                                    WOMAnimations.TORMENT_BERSERK_AIRSLAM,
                                    Animations.GREATSWORD_AUTO1,
                                    Animations.GREATSWORD_AUTO2,
                                    Animations.THE_GUILLOTINE,
                                    AnimsSolar.SOLAR_OBSCURIDAD_AUTO_4,
                                    AnimsSolar.SOLAR_AUTO_2,
                                    WOMAnimations.TORMENT_AUTO_4,
                                    AnimsPugilistSteve.LEGENDARY_SWORD_AUTO_4,
                                    AnimsAgony.AGONY_RIPPING_FANGS,
                                    AnimsAgony.AGONY_AUTO_3,
                                    WOMAnimations.TORMENT_CHARGED_ATTACK_1,
                                    AnimsSolar.SOLAR_AUTO_4
                            ),
                            CombatCommon.enderStepRollAnimations())
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
                                            .animationBehavior(WOMAnimations.TORMENT_BERSERK_CONVERT, 0.0F)
                                            .addExBehavior(HerobrineCommon::changeToSecondForm)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(12.5D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 8.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .custom(HerobrineCommon::hasNearbySixPortalSupport)
                                            .animationBehavior(AVAnimations.SNAKE_BLADE, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(12.5D)
                            .maxCooldown(300)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 8.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormAnimation)
                                            .animationBehavior(AVAnimations.SNAKE_BLADE, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15)
                            .maxCooldown(300)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 8.0D)
                                            .custom(HerobrineCommon::canPlaySecondFormGuardAnimation)
                                            .animationBehavior(AVAnimations.SNAKE_BLADE_GUARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::playSecondFormGuardAnimation)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot(0.0D, 5.0D, HerobrineCommon::canPerformGuarding))
            .newBehaviorRoot(CombatBehaviourTemplates.herobrineJumpRoot());
}
