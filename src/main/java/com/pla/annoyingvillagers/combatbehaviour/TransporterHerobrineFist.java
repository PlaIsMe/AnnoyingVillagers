package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsSculkSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class TransporterHerobrineFist {
    public static final Builder<MobPatch<?>> TRANSPORTER_HEROBRINE_FIST = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .custom(HerobrineCommon::canPerformPortalEscapeStepBack)
                                            .withinDistance(0.0D, 48.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::performPortalEscapeStepBack)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .guard(20)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(45.0D)
                            .maxCooldown(10)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::isSupportingHerobrineEscaping)
                                            .animationBehavior(AnimsSculkSteve.PORTAL_SUMMON, 0.0F)
                                            .addExBehavior(HerobrineCommon::summonSupportingHerobrineEscapePortal)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(35.0D)
                            .maxCooldown(10)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::isSupportingHerobrineGettingShot)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::summonSupportCounterPortal)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30.0D)
                            .maxCooldown(10)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::canSummonLowCloneSupport)
                                            .animationBehavior(AnimsSculkSteve.PORTAL_SUMMON, 0.0F)
                                            .addExBehavior(HerobrineCommon::summonLowCloneSupport)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(25.0D)
                            .maxCooldown(10)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::canSummon2Portal)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::summon2Portal)
                            )
            );
}
