package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcStaff {
    public static final Builder<MobPatch<?>> STAFF = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_ROLL_FORWARD, Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    WOMAnimations.STAFF_AUTO_1,
                                    WOMAnimations.STAFF_AUTO_2,
                                    WOMAnimations.STAFF_AUTO_3
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.SPEAR_THRUST,
                                    WOMAnimations.STAFF_SQUALL,
                                    WOMAnimations.STAFF_KINKONG,
                                    WOMAnimations.STAFF_CHARYBDIS
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot(0.5D))
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
