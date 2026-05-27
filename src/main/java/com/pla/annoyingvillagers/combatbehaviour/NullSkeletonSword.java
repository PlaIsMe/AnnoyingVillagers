package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NullSkeletonSword {
    public static final CECombatBehaviors.Builder<MobPatch<?>> AV_SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::canExecute)
                                            .withinDistance(0.0D, 5.0D)
                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                            .addExBehavior(CombatCommon::performExecute)
                            )
            )
            .newBehaviorRoot(
                    addAvSwordRandomCombatChains(
                            CECombatBehaviors.BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20)
                    )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canPerformGuarding)
                                            .guard(40)
                            )
            )
            .newBehaviorRoot(
                    CECombatBehaviors.BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .maxCooldown(160)
                            .addFirstBehavior(
                                    CECombatBehaviors.Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(CombatCommon::jump)
                            )
            );

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addAvSwordRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2,
                        Animations.SWORD_AUTO3
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                        AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                ),
                CombatCommon.animations(
                        Animations.SWORD_DASH,
                        Animations.SWORD_AIR_SLASH,
                        Animations.SWEEPING_EDGE
                ),
                CombatCommon.kickAnimations(),
                CombatCommon.rollStepAnimations()
        );
    }
}
