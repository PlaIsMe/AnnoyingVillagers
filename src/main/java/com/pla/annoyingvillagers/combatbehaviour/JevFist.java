package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class JevFist {
    public static final Builder<MobPatch<?>> FIST = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
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
                            .priority(2.0D)
                            .weight(70.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performDrinkingAnimation)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .health(2.0F / 3.0F, HealthCheck.Comparator.LESS_RATIO_CONTAIN)
                                            .custom(CombatCommon::canPerformEating)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performDrinkingAnimation)
                            )
            )
            .newBehaviorRoot(
                    addFistRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20)
                    )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40.0D)
                            .maxCooldown(160)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canJump)
                                            .withinDistance(5.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_JUMP, 0.0F)
                                            .addExBehavior(CombatCommon::jump)
                            )
            );

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addFistRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        Animations.FIST_AUTO1,
                        Animations.FIST_AUTO2,
                        Animations.FIST_AUTO3
                ),
                CombatCommon.animations(
                        AnimsPugilistSteve.FIST_LEFT,
                        Animations.FIST_DASH,
                        AnimsPugilistSteve.WHIRLWIND_KICK_LEFT,
                        AnimsPugilistSteve.FIST_UP,
                        AnimsPugilistSteve.FIST_DASH,
                        AnimsPugilistSteve.WHIRLWIND_KICK
                ),
                CombatCommon.animations(
                        Animations.RELENTLESS_COMBO,
                        Animations.FIST_AIR_SLASH
                ),
                CombatCommon.fistKickAnimations(),
                CombatCommon.rollAnimations()
        );
    }

}
