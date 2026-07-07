package com.pla.annoyingvillagers.compat.refm;

import com.pla.annoyingvillagers.combatbehaviour.AvNpcCombatBehaviorBuilder;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.gameasset.AnimsEpicFightBattleArts;
import net.shelmarow.combat_evolution.ai.CECombatBehaviors.Builder;
import net.yonchi.refm.gameasset.RapierAnimations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class AvNpcRapier {
    public static final Builder<MobPatch<?>> RAPIER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    RapierAnimations.RAPIER_AUTO1,
                    RapierAnimations.RAPIER_AUTO2,
                    RapierAnimations.RAPIER_AUTO3
            ),
            CombatCommon.animations(
                    RapierAnimations.RAPIER_DASH,
                    RapierAnimations.RAPIER_AIR_SLASH,
                    AnimsEpicFightBattleArts.SABRE_QUAD_STING
            ),
            CombatCommon.animations(
                    RapierAnimations.DEADLYBACKFLIP_FIRST,
                    RapierAnimations.DEADLYBACKFLIP_SECOND,
                    RapierAnimations.DEADLYBACKFLIP_FAIL
            )
    );

    public static final Builder<MobPatch<?>> ENDER_RAPIER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    RapierAnimations.RAPIER_AUTO1,
                    RapierAnimations.RAPIER_AUTO2_ENDER,
                    RapierAnimations.RAPIER_AUTO3_ENDER
            ),
            CombatCommon.animations(
                    RapierAnimations.RAPIER_DASH_ENDER,
                    RapierAnimations.RAPIER_AIR_SLASH_ENDER,
                    AnimsEpicFightBattleArts.SABRE_QUAD_STING
            ),
            CombatCommon.animations(
                    RapierAnimations.DEADLYBACKFLIP_FIRST,
                    RapierAnimations.DEADLYBACKFLIP_SECOND_ENDER,
                    RapierAnimations.DEADLYBACKFLIP_FAIL
            )
    );

    public static final Builder<MobPatch<?>> OCEAN_RAPIER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    RapierAnimations.RAPIER_AUTO1,
                    RapierAnimations.RAPIER_AUTO2_OCEAN,
                    RapierAnimations.RAPIER_AUTO3_OCEAN
            ),
            CombatCommon.animations(
                    RapierAnimations.RAPIER_DASH_OCEAN,
                    RapierAnimations.RAPIER_AIR_SLASH_OCEAN,
                    AnimsEpicFightBattleArts.SABRE_QUAD_STING
            ),
            CombatCommon.animations(
                    RapierAnimations.DEADLYBACKFLIP_FIRST,
                    RapierAnimations.DEADLYBACKFLIP_SECOND_OCEAN,
                    RapierAnimations.DEADLYBACKFLIP_FAIL
            )
    );

    public static final Builder<MobPatch<?>> WITHER_RAPIER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    RapierAnimations.RAPIER_AUTO1,
                    RapierAnimations.RAPIER_AUTO2_WITHER,
                    RapierAnimations.RAPIER_AUTO3_WITHER
            ),
            CombatCommon.animations(
                    RapierAnimations.RAPIER_DASH_WITHER,
                    RapierAnimations.RAPIER_AIR_SLASH_WITHER,
                    AnimsEpicFightBattleArts.SABRE_QUAD_STING
            ),
            CombatCommon.animations(
                    RapierAnimations.DEADLYBACKFLIP_FIRST,
                    RapierAnimations.DEADLYBACKFLIP_SECOND_WITHER,
                    RapierAnimations.DEADLYBACKFLIP_FAIL
            )
    );

    public static final Builder<MobPatch<?>> AMETHYST_RAPIER = AvNpcCombatBehaviorBuilder.weapon(
            CombatCommon.animations(
                    RapierAnimations.RAPIER_AUTO1,
                    RapierAnimations.RAPIER_AUTO2_AMETHYST,
                    RapierAnimations.RAPIER_AUTO3_AMETHYST
            ),
            CombatCommon.animations(
                    RapierAnimations.RAPIER_DASH_AMETHYST,
                    RapierAnimations.RAPIER_AIR_SLASH_AMETHYST,
                    AnimsEpicFightBattleArts.SABRE_QUAD_STING
            ),
            CombatCommon.animations(
                    RapierAnimations.DEADLYBACKFLIP_FIRST,
                    RapierAnimations.DEADLYBACKFLIP_SECOND_AMETHYST,
                    RapierAnimations.DEADLYBACKFLIP_FAIL
            )
    );
}
