package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class SteveGreatsword {
    public static final Builder<MobPatch<?>> GREATSWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(CombatBehaviourTemplates.escapeWithGuardRoot(4.0D, Animations.BIPED_ROLL_BACKWARD, false))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(3.0D)
                            .weight(100.0D)
                            .maxCooldown(120)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(CombatCommon::canSwitchWeapon)
                                            .animationBehavior(Animations.BIPED_ROLL_FORWARD, 0.0F)
                                            .addExBehavior(CombatCommon::switchWeapon)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.eatingRoot(Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.swapToBowRoot(Animations.BIPED_ROLL_FORWARD, Animations.BIPED_ROLL_BACKWARD))
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlToTargetRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.combatFishingRodRoot())
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
                                    WOMAnimations.TORMENT_AUTO_3,
                                    AnimsSolar.SOLAR_HORNO
                            ),
                            CombatCommon.animations(
                                    AnimsPugilistSteve.GIANT_WHIRLWIND,
                                    AnimsPugilistSteve.GREATSWORD_SKILL
                            ),
                            CombatCommon.animations(
                                    Animations.GREATSWORD_DASH,
                                    Animations.GREATSWORD_AIR_SLASH
                            ),
                            CombatCommon.kickAnimations(),
                            CombatCommon.rollAnimations())
            )
            .newBehaviorRoot(CombatBehaviourTemplates.combatFishingRodEscapeRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.enderPearlAwayRoot(true))
            .newBehaviorRoot(CombatBehaviourTemplates.guardRoot())
            .newBehaviorRoot(CombatBehaviourTemplates.jumpRoot());
}
