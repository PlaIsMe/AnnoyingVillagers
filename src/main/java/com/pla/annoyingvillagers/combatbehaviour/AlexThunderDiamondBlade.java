package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFight;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AlexThunderDiamondBlade {
    public static final Builder<MobPatch<?>> THUNDER_DIAMOND_BLADE = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_STEP_FORWARD, Animations.BIPED_STEP_BACKWARD, Animations.BIPED_STEP_LEFT, Animations.BIPED_STEP_RIGHT))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    AnimsEpicFightAwaken.CUT_LEFT_DP_AUTO_3,
                                    AnimsPugilistSteve.SWORD_DASH,
                                    AnimsPugilistSteve.DAGGER_AUTO1,
                                    AnimsHerrscher.HERRSCHER_AUTO_1,
                                    AnimsHerrscher.HERRSCHER_AUTO_2
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                            ),
                            CombatCommon.animations(
                                    AnimsEpicFightAwaken.CUT_LEFT_DP_DASH,
                                    AnimsEpicFightAwaken.HOOK_SLASH_AIR,
                                    AnimsEpicFight.THUNDER_SWEEPING_EDGE
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.stepAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());

    public static final Builder<MobPatch<?>> DUAL_THUNDER_DIAMOND_BLADE = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_STEP_FORWARD, Animations.BIPED_STEP_BACKWARD, Animations.BIPED_STEP_FORWARD, Animations.BIPED_STEP_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(20),
                            CombatCommon.animations(
                                    AnimsEpicFightAwaken.DP_AUTO_1,
                                    AnimsEpicFightAwaken.DP_AUTO_2,
                                    AnimsEpicFightAwaken.DP_AUTO_3,
                                    AnimsEpicFightAwaken.DP_AUTO_4,
                                    AnimsPugilistSteve.DUAL_SWORD_AUTO2
                            ),
                            CombatCommon.animations(
                                    Animations.DAGGER_DUAL_DASH,
                                    Animations.LONGSWORD_AUTO2,
                                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
                            ),
                            CombatCommon.animations(
                                    AnimsEpicFightAwaken.DP_DASH,
                                    AnimsEpicFightAwaken.DP_NIGHT_FALL,
                                    AnimsEpicFight.THUNDER_DANCING_EDGE
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.stepAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(100, true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
