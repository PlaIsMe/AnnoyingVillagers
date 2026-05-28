package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NpcTrapdoor {
    public static final Builder<MobPatch<?>> TRAPDOOR = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot())
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
                                    Animations.SWORD_AUTO1,
                                    Animations.SWORD_AUTO3,
                                    Animations.SWORD_AUTO2,
                                    AnimsHerrscher.HERRSCHER_AUTO_3
                            ),
                            CombatCommon.animations(
                                    Animations.SWORD_DUAL_AUTO1,
                                    Animations.LONGSWORD_AUTO2,
                                    Animations.SWORD_DUAL_AUTO2,
                                    Animations.SWORD_DUAL_DASH,
                                    Animations.SWORD_AIR_SLASH,
                                    AnimsPugilistSteve.GIANT_WHIRLWIND,
                                    AnimsHerrscher.HERRSCHER_VERDAMMNIS
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.stepAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
