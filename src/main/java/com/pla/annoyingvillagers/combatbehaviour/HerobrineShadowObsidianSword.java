package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.*;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineShadowObsidianSword {
    public static final Builder<MobPatch<?>> SHADOW_OBSIDIAN_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(5.0D)
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
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
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
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canSummonDarkOb)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F)
                                            .addExBehavior(HerobrineCommon::performSummonDarkOb)
                            )
            )
            .newBehaviorRoot(
                    addShadowObsidianSwordRandomCombatChains(
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
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(AnimsWom.SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 6.0D)
                                                            .animationBehavior(AnimsWom.SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH, 0.0F)
                                            )
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canShootDarkOb)
                                            .withinDistance(5.0D, 10.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::performShootDarkOb)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canPlayObsidianMachine)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::performObsidianMachine)
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
                                            .custom(HerobrineCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(160)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(HerobrineCommon::jump)
                            )
            );

    public static final Builder<MobPatch<?>> SHADOW_OBSIDIAN_DUAL_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(5.0D)
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
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
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
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(HerobrineCommon::canPerformHealing)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F)
                                            .addExBehavior(HerobrineCommon::performHealingAnimation)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canSummonDarkOb)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_BUFF, 0.0F)
                                            .addExBehavior(HerobrineCommon::performSummonDarkOb)
                            )
            )
            .newBehaviorRoot(
                    addShadowObsidianDualSwordRandomCombatChains(
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
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canShootDarkOb)
                                            .withinDistance(5.0D, 10.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::performShootDarkOb)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .maxCooldown(200)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canPlayObsidianMachine)
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(HerobrineCommon::performObsidianMachine)
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
                                            .custom(HerobrineCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown(160)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(HerobrineCommon::jump)
                            )
            );

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addShadowObsidianSwordRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        AnimsEpicFightDualGreatsword.SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1,
                        AnimsEpicFightDualGreatsword.SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2,
                        AnimsPugilistSteve.SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG,
                        AnimsEpicFight.SHADOW_OBSIDIAN_FIST_AIR_SLASH
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.OBSIDIAN_FIST_DASH,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                        AnimsWom.SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH,
                        AnimsWom.SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM,
                        AnimsEpicFightInfernalGainer.OBSIDIAN_INFERNAL_AUTO_2
                ),
                CombatCommon.rollStepAnimations()
        );
    }


    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addShadowObsidianDualSwordRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        AnimsWom.SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2,
                        AnimsPugilistSteve.SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4,
                        AnimsPugilistSteve.SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5,
                        AnimsEpicFightDualGreatsword.GREATSWORD_DUAL_AUTO_2,
                        AnimsEpicFightDualGreatsword.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3
                ),
                CombatCommon.animations(
                        WOMAnimations.TORMENT_DASH,
                        AnimsWom.SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_3,
                        AnimsEpicFightDualGreatsword.SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH
                ),
                CombatCommon.rollStepAnimations()
        );
    }

}
