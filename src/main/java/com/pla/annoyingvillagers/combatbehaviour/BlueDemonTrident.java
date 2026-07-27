package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.*;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class BlueDemonTrident {
    public static final Builder<MobPatch<?>> TRIDENT = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
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
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canBlueDemonPerformHealing)
                                            .animationBehavior(AnimsWom.CUT_ANTITHEUS_ASCENSION, 0.0F)
                                            .addExBehavior(CombatCommon::performBlueDemonHealing)
                            )
            )
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    AnimsEpicFightBattleArts.ADVANCED_LANCER_AUTO1,
                                    AnimsPugilistSteve.TRIDENT_DUAL_AUTO2,
                                    AnimsEpicFightBattleArts.ADVANCED_DUELIST_SHOOTING_STAR,
                                    AnimsEpicFightAwaken.CUT_DP_AIR_ATTACK,
                                    AnimsEpicFightBattleArts.ADVANCED_LANCER_AUTO3,
                                    AnimsEpicFightBattleArts.ADVANCED_DUELIST_WHIRLEDGE
                            ),
                            CombatCommon.animations(
                                    AnimsEpicFightBattleArts.TRIDENT_THROW_1,
                                    AnimsPugilistSteve.TRIDENT_THROW_2,
                                    AnimsEpicFightBattleArts.TRIDENT_THROW_3,
                                    AnimsEpicFightAwaken.DP_THROW_BLADE_AUTO_2,
                                    AnimsEpicFightBattleArts.TRIDENT_THROW_5
                            ),
                            CombatCommon.animations(
                                    AnimsEpicFightAwaken.DP_THROW_BLADE_AUTO_1,
                                    AnimsEpicFight.NERF_TSUNAMI_REINFORCED,
                                    AnimsEpicFightAwaken.CUT_HOOK_SPIN_SLASH_AIR,
                                    AnimsEpicFightAwaken.THROW_HOOK_SLASH_AIR,
                                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
                            ),
                            CombatCommon.rollStepAnimations())
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .maxCooldown(20)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                            .withinDistance(10.0D, 80.0D)
                                            .animationBehavior(AnimsEpicFightBattleArts.TRIDENT_THROW_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                                            .withinDistance(10.0D, 80.0D)
                                                            .animationBehavior(AnimsPugilistSteve.TRIDENT_THROW_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                                                            .withinDistance(10.0D, 80.0D)
                                                                            .animationBehavior(AnimsEpicFightBattleArts.TRIDENT_THROW_3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                                                                            .withinDistance(10.0D, 80.0D)
                                                                                            .animationBehavior(AnimsEpicFightAwaken.DP_THROW_BLADE_AUTO_2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .custom(CombatCommon::isTargetingHerobrineDragon)
                                                                                                            .withinDistance(10.0D, 80.0D)
                                                                                                            .animationBehavior(AnimsEpicFightBattleArts.TRIDENT_THROW_5, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10)
                            .maxCooldown(600)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 10.0D)
                                            .animationBehavior(AnimsWom.ELECTRIC_FIELD, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canPerformTridentAttack)
                                            .withinDistance(0.0D, 10.0D)
                                            .animationBehavior(AVAnimations.TRIDENT_ATTACK, 0.0F)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot(0.5D))
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());


}
