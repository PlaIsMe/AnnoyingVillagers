package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class SteveFist {
    public static final Builder<MobPatch<?>> FIST = CECombatBehaviors.builder()
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
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::swapToBlockAndPerformEscapeRunAway)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canAttackWhileNotHealing)
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                            .addExBehavior(CombatCommon::swapToMelee)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic).custom(CombatCommon::canAttackWhileNotHealing)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic).custom(CombatCommon::canAttackWhileNotHealing)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .withinDistance(1.0D, 14.0D)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_ROLL_BACKWARD, Animations.BIPED_ROLL_FORWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot(true))
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
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
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
