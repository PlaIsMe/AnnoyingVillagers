package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.efkick.gameasset.EFKickAnimations;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NpcFist {
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
                                            .animationBehavior(Animations.BIPED_STEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
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
//            .newBehaviorRoot(
//                    BehaviorRoot.builder()
//                            .priority(3.0D)
//                            .weight(1000.0D)
//                            .maxCooldown (0)
//                            .addFirstBehavior(
//                                    Behavior.builder()
//                                            .custom(CombatCommon::canPerformNormalAttackLogic)
//                                            .custom(CombatCommon::canAttackWhileNotHealing)
//                                            .custom(CombatCommon::isWrongWeapon)
//                                            .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
//                                            .addExBehavior(CombatCommon::swapToMelee)
//                            )
//            )
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_STEP_RIGHT, Animations.BIPED_STEP_LEFT, Animations.BIPED_STEP_BACKWARD, Animations.BIPED_STEP_FORWARD))
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
                                    AnimsPugilistSteve.FIST_UP,
                                    AnimsPugilistSteve.WHIRLWIND_KICK_LEFT,
                                    AnimsPugilistSteve.FIST_DASH,
                                    AnimsPugilistSteve.WHIRLWIND_KICK
                            ),
                            CombatCommon.animations(
                                    Animations.FIST_AIR_SLASH,
                                    Animations.RELENTLESS_COMBO
                            ),
                            CombatCommon.animations(
                                    EFKickAnimations.KICK_1,
                                    EFKickAnimations.KICK_C,
                                    EFKickAnimations.KICK_2,
                                    EFKickAnimations.KICK_RUSH,
                                    EFKickAnimations.KICK_COMBO,
                                    EFKickAnimations.KICK_3,
                                    EFKickAnimations.KICK_H,
                                    EFKickAnimations.KICK_4
                            ),
                            CombatCommon.stepAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
