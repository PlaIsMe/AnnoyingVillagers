package com.pla.annoyingvillagers.combatbehaviour;

import com.hm.efn.gameasset.animations.EFNSwordAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.annoyingvillagers.advancedmobpatch.AdvancedMobPatch.AdditionalAttackGroup;
import net.shelmarow.ef_awaken.efassets.animations.StraightSwordAnimations;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.List;

public class AlexThunderDiamondBlade {
    private static final List<AdditionalAttackGroup> THUNDER_DIAMOND_BLADE = List.of(
            AdditionalAttackGroup.forced(CombatCommon.stepAnimations()),
            AdditionalAttackGroup.random(
                    0.35F,
                    EFNSwordAnimations.NF_SWORD_SKILL,
                    AnimsAVSword.THUNDER_DIAMOND_BLADE_INNATE
            )
    );

    private static final List<AdditionalAttackGroup> DUAL_THUNDER_DIAMOND_BLADE = List.of(
            AdditionalAttackGroup.forced(CombatCommon.stepAnimations()),
            AdditionalAttackGroup.random(
                    0.35F,
                    StraightSwordAnimations.STRAIGHTSWORD_DUAL_DODGE_SLASH,
                    AnimsAVSword.THUNDER_DIAMOND_BLADE_DUAL_INNATE
            )
    );

    public static List<AdditionalAttackGroup> additionalAttacks(Style style) {
        return style == Styles.TWO_HAND ? DUAL_THUNDER_DIAMOND_BLADE : THUNDER_DIAMOND_BLADE;
    }
}
