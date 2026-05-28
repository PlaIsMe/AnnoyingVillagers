package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class PlayerNpcTachi {
    public static final Builder<MobPatch<?>> TACHI = CECombatBehaviors.builder()
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
                                    Animations.TACHI_AUTO1,
                                    Animations.TACHI_AUTO2,
                                    Animations.TACHI_AUTO3
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_1,
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_2,
                                    AnimsPugilistSteve.SWORD_HEAVY_AUTO_3
                            ),
                            CombatCommon.animations(
                                    Animations.TACHI_DASH,
                                    Animations.RUSHING_TEMPO1,
                                    Animations.RUSHING_TEMPO2,
                                    Animations.LONGSWORD_AIR_SLASH,
                                    Animations.RUSHING_TEMPO3
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(false))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
