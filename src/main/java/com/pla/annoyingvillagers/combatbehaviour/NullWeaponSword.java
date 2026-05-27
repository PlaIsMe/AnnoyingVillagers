package com.pla.annoyingvillagers.combatbehaviour;

import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Behavior;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.BehaviorRoot;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class NullWeaponSword {
    public static final Builder<MobPatch<?>> SWORD = CECombatBehaviors.builder()
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(2.0D)
                            .weight(1000.0D)
                            .maxCooldown (0)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(HerobrineCommon::canSpinning)
                                            .withinDistance(0.0D, 45.0D)
                                            .guard(40)
                                            .addExBehavior(HerobrineCommon::performSpinning)
                            )
            )
            .newBehaviorRoot(
                    addSwordRandomCombatChains(
                            BehaviorRoot.builder()
                                    .priority(1.0D)
                                    .weight(40.0D)
                                    .maxCooldown(80)
                    )
            )
            .newBehaviorRoot(
                    BehaviorRoot.builder()
                            .priority(1.0D)
                            .weight(15.0D)
                            .addFirstBehavior(
                                    Behavior.builder()
                                            .custom(CombatCommon::canPerformNormalAttackLogic)
                                            .withinDistance(0.5D, 3.0D)
                                            .custom(CombatCommon::canPerformGuarding)
                                            .guard(40)
                            )
            );

    private static CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> addSwordRandomCombatChains(CECombatBehaviors.BehaviorRoot.Builder<MobPatch<?>> root) {
        return CombatCommon.addRandomCombatChains(
                root,
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
                CombatCommon.stepAnimations()
        );
    }

}
