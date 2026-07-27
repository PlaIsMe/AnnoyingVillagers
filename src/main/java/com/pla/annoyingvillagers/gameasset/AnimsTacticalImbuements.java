/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: MIT
 *
 * Upstream: Tactical Imbuements - M3tte
 * Source: https://www.curseforge.com/minecraft/mc-mods/tactical-imbuements
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/MIT.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsTacticalImbuements {
    Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ZAP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ZAP_LONG;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        ZAP = builder.nextAccessor("biped/tactical_imbuements/zap",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
        );
        ZAP_LONG = builder.nextAccessor("biped/tactical_imbuements/zap_long",
                accessor -> (LongHitAnimation) new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
        );
    }
}
