package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.shelmarow.combat_evolution.ai.condition.HealthCheck;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcCraftingTable {
    public static final Builder<MobPatch<?>> CRAFTING_TABLE = CECombatBehaviors.builder()
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
                            .priority(3.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBlockToEscape)
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
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(100.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwapToBow)
                                            .withinDistance(7.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBow)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(80.0D)
                            .maxCooldown (120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .withinDistance(7.0D, 48.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlToTarget)
                            )
            )
            .newBehaviorRoot(
                    addCraftingTableRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20)
                    )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(10.0D)
                            .maxCooldown(40)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.0D, 3.0D)
                                            .custom(CombatCommon::canThrowEnderPearl)
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(CombatCommon::performEnderPearlAway)
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
                                            .custom(CombatCommon::canPerformGuarding)
                                            .guard(40)
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

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addCraftingTableRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
                CombatCommon.animations(
                        Animations.TACHI_AUTO2,
                        Animations.TACHI_AUTO3,
                        AnimsRuine.RUINE_AUTO_1,
                        AnimsRuine.RUINE_AUTO_2
                ),
                CombatCommon.animations(
                        WOMAnimations.TORMENT_AIRSLAM,
                        Animations.LONGSWORD_AIR_SLASH,
                        AnimsRuine.RUINE_CHATIMENT,
                        AnimsSolar.SOLAR_AUTO_2
                ),
                CombatCommon.kickAnimations(),
                CombatCommon.rollAnimations()
        );
    }

}
