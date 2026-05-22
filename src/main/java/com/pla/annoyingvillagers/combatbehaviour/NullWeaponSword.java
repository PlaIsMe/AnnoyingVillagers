package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NullWeaponSword {
    public static final Builder<MobPatch<?>> SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::canSpinning)
                                            .withinDistance(0.0D, 45.0D)
                                            .guard(40)
                                            .addExBehavior(HerobrineCommon::performSpinning)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .maxCooldown(80)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 3.0D)
                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                            )
                                            )
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                                                                                            .addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                                            .addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                                            .addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                                                                            .addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.BIPED_STEP_RIGHT, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                                                                                            .addNextBehavior(
                                                                                                                                    Behavior.builder()
                                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                                            .animationBehavior(Animations.SWORD_AUTO3, 0.0F)
                                                                                                                            )
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.BIPED_STEP_LEFT, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.SWORD_AUTO2, 0.0F)
                                                                                                            .addNextBehavior(
                                                                                                                    Behavior.builder()
                                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                                                                                            )
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )

                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AUTO1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(0.0D, 3.0D)
                                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(0.0D, 4.0D)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F)
                                                                            .addNextBehavior(
                                                                                    Behavior.builder()
                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                            .withinDistance(0.0D, 4.0D)
                                                                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                                                                                            .addNextBehavior(
                                                                                                    Behavior.builder()
                                                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                                                            .withinDistance(0.0D, 5.0D)
                                                                                                            .animationBehavior(Animations.BIPED_STEP_FORWARD, 0.0F)
                                                                                            )
                                                                            )
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(20.0D)
                            .maxCooldown (80)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .withinDistance(0.0D, 3.0D)
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .withinDistance(0.0D, 3.0D)
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .animationBehavior(AnimsPugilistSteve.SWORD_HEAVY_AUTO_3, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown (80)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_DASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWORD_AIR_SLASH, 0.0F)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .animationBehavior(Animations.SWEEPING_EDGE, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.5D, 3.0D)
                                            .custom(CombatCommon::canPerformGuarding)
                                            .guard(40)
                            )
            );
}
