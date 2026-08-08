/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: LGPL-2.1-only
 *
 * Upstream: Epic Fight - Valour Guard - namelesslk
 * Source: https://github.com/Cyber2049/valour-guard
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/LGPL-2.1.md
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
import yesman.epicfight.api.animation.types.GuardAnimation;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightValourGuard {
    public static AnimationManager.AnimationAccessor<StaticAnimation> VALOUR_HOLD_GREATSWORD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> VALOUR_WALK_GREATSWORD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> VALOUR_RUN_GREATSWORD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> VALOUR_FIST_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> VALOUR_FIST_GUARD_HIT;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        VALOUR_HOLD_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_hold_greatsword",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        VALOUR_RUN_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_run_greatsword",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        VALOUR_WALK_GREATSWORD = builder.nextAccessor("biped/epicfight_valour_guard/valour_walk_greatsword",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        VALOUR_FIST_GUARD = builder.nextAccessor("biped/epicfight_valour_guard/valour_fist_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        VALOUR_FIST_GUARD_HIT = builder.nextAccessor("biped/epicfight_valour_guard/valour_fist_guard_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
    }
}
