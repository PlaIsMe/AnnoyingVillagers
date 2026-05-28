package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcAxe {
    public static final Builder<MobPatch<?>> AXE = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot(Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_ROLL_BACKWARD, Animations.BIPED_ROLL_FORWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    Animations.AXE_AUTO1,
                                    Animations.AXE_AUTO2
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.AXE_HEAVY_AUTO_1,
                                    AnimsPugilistSteve.AXE_HEAVY_AUTO_2,
                                    AnimsPugilistSteve.AXE_FUN_SKILL
                            ),
                            CombatCommon.animations(
                                    Animations.AXE_DASH,
                                    Animations.AXE_AIRSLASH,
                                    Animations.THE_GUILLOTINE
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(false))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
