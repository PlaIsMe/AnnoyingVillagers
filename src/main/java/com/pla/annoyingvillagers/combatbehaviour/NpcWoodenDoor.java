package com.pla.annoyingvillagers.combatbehaviour;

import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NpcWoodenDoor {
    public static final Builder<MobPatch<?>> WOODEN_DOOR = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot(Animations.BIPED_STEP_BACKWARD, true))
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_STEP_FORWARD, Animations.BIPED_STEP_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    Animations.GREATSWORD_AUTO1,
                                    Animations.GREATSWORD_AUTO2,
                                    WOMAnimations.TORMENT_AUTO_2,
                                    WOMAnimations.TORMENT_AUTO_3
                            ),
                            CombatCommon.animations(
                                    Animations.GREATSWORD_DASH,
                                    WOMAnimations.TORMENT_CHARGED_ATTACK_2,
                                    WOMAnimations.TORMENT_BERSERK_DASH,
                                    WOMAnimations.TORMENT_AIRSLAM
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.stepAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
