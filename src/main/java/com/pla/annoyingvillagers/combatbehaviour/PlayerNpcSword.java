package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcSword {
    public static final Builder<MobPatch<?>> SWORD = CECombatBehaviors.builder()
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
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(100, true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());

    public static final Builder<MobPatch<?>> DUAL_SWORD = CECombatBehaviors.builder()
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
                                    Animations.SWORD_DUAL_AUTO1,
                                    Animations.SWORD_DUAL_AUTO2,
                                    Animations.SWORD_DUAL_AUTO3
                            ),
                            CombatCommon.animations(
                                    Animations.DAGGER_DUAL_DASH,
                                    Animations.LONGSWORD_AUTO2,
                                    AnimsPugilistSteve.DUAL_DANCING_EDGE,
                                    AnimsPugilistSteve.DUAL_SWORD_DANCING_EDGE
                            ),
                            CombatCommon.animations(
                                    Animations.SWORD_DUAL_DASH,
                                    Animations.SWORD_DUAL_AIR_SLASH,
                                    Animations.DANCING_EDGE
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(100, true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());

    public static final Builder<MobPatch<?>> AV_SWORD = CECombatBehaviors.builder()
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
                                    AnimsEpicFightAwaken.CUT_LEFT_DP_DUSK_REAVER_2
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());

    public static final Builder<MobPatch<?>> AV_DUAL_SWORD = CECombatBehaviors.builder()
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
                                    AnimsEpicFightAwaken.HOOK_SLASH_GROUND
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(100, true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
