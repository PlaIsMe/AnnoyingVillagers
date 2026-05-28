package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightIronSpell;
import com.pla.annoyingvillagers.gameasset.AnimsWom;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class HerobrineNullWeapon {
    public static final Builder<MobPatch<?>> NULL_WEAPON = CECombatBehaviors.builder()
            .newBehaviorRoot(CombatBehaviourTemplates.executionRoot(5.0D))
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(4.0D)
                            .weight(1000.0D)
                            .maxCooldown(0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 8.0D)
                                            .animationBehavior(WOMAnimations.SHADOWSTEP_BACKWARD, 0.0F)
                                            .addExBehavior(CombatCommon::performEscapeRunAway)
                            )
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canEscape)
                                            .withinDistance(0.0D, 48.0D)
                                            .guard(40)
                                            .addExBehavior(HerobrineCommon::performGuardWeaponSpinning)
                            )
            )
            .newBehaviorRoot(CombatBehaviourTemplates.herobrineHealingRoot())
            .newBehaviorRoot(
                    CombatCommon.addRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(100),
                            CombatCommon.conditions(HerobrineCommon::canPlaySecondFormAnimation),
                            CombatCommon.animations(
                                    AnimsAgony.AGONY_AIR_ATTACK_1,
                                    AnimsAgony.AGONY_AIR_ATTACK_2,
                                    AnimsAgony.AGONY_AIR_ATTACK_3,
                                    AnimsAgony.AGONY_AIR_ATTACK_4
                            ),
                            CombatCommon.animations(
                                    AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_1,
                                    AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_2,
                                    AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_3,
                                    AnimsWom.CLONE_ANTITHEUS_ASCENDED_BLACKHOLE
                            ),
                            CombatCommon.animations(
                                    AnimsAgony.AGONY_AIR_ATTACK_1,
                                    AnimsAgony.AGONY_AIR_ATTACK_2,
                                    AnimsAgony.AGONY_AIR_ATTACK_3,
                                    AnimsAgony.AGONY_AIR_ATTACK_4
                            ),
                            CombatCommon.animations(
                                    AnimsWom.CLONE_ANTITHEUS_SHOOT,
                                    AnimsWom.CLONE_ANTITHEUS_ASCENDED_BLINK,
                                    AnimsWom.CLONE_ANTITHEUS_ASCENDED_DEATHFALL,
                                    AnimsWom.CLONE_ANTITHEUS_ASCENDED_BLACKHOLE,
                                    AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_1,
                                    AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_2,
                                    AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_3
                            ),
                            CombatCommon.shadowStepAnimations())
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(40)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 15.0D)
                                            .animationBehavior(AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_1, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(2.0D, 15.0D)
                                                            .animationBehavior(AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_2, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(2.0D, 15.0D)
                                                                            .animationBehavior(AnimsWom.NULL_ANTITHEUS_ASCENDED_AUTO_3, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(30)
                            .maxCooldown(80)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 15.0D)
                                            .animationBehavior(AnimsWom.CLONE_ANTITHEUS_SHOOT, 0.0F)
                                            .addNextBehavior(
                                                    Behavior.builder()
                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                            .withinDistance(2.0D, 15.0D)
                                                            .animationBehavior(AnimsWom.CLONE_ANTITHEUS_SHOOT, 0.0F)
                                                            .addNextBehavior(
                                                                    Behavior.builder()
                                                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                                                            .withinDistance(2.0D, 15.0D)
                                                                            .animationBehavior(AnimsWom.CLONE_ANTITHEUS_SHOOT, 0.0F)
                                                            )
                                            )
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .maxCooldown(600)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 15.0D)
                                            .animationBehavior(AnimsEpicFightIronSpell.CASTING_ONE_HAND_TOP, 0.0F)
                                            .addExBehavior(HerobrineCommon::releaseWeapon)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(25)
                            .maxCooldown(600)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(2.0D, 15.0D)
                                            .custom(HerobrineCommon::canSummonNullSkeleton)
                                            .animationBehavior(AnimsWom.NULL_SKELETON_ANTITHEUS_ASCENSION, 0.0F)
                            )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .maxCooldown(100)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .withinDistance(0.0D, 4.0D)
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .custom(HerobrineCommon::canPerformGuarding)
                                            .guard(40)
                                            .addExBehavior(HerobrineCommon::performGuardWeaponSpinning)
                            )
            )
            .newBehaviorRoot(
                    addNullWeaponMovementAnimations(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(10.0D)
                    )
            );


    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addNullWeaponMovementAnimations(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        root = CombatCommon.addAnimationBehaviors(
                root,
                2.0D,
                15.0D,
                CombatCommon.shadowStepAnimations()
        );
        root = CombatCommon.addAnimationBehaviors(
                root,
                0.0D,
                5.0D,
                CombatCommon.conditions(CombatCommon::isNotRiding),
                CombatCommon.enderStepAnimations()
        );
        return CombatCommon.addAnimationBehaviors(
                root,
                0.0D,
                5.0D,
                CombatCommon.rollStepAnimations()
        );
    }
}
