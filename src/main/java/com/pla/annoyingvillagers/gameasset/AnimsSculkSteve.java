/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: LicenseRef-Author-Permission
 *
 * Upstream: Sculk Steve - Original Author
 * Source: Authorized by original author; no public source URL recorded.
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/LicenseRef-Author-Permission.md
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
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsSculkSteve {
    public static AnimationManager.AnimationAccessor<StaticAnimation> PLAYER_HEROBRINE_POSSESSION;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ENDER_SLAYER_SCYTHE_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_SACRIFICING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ASSISTANCE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_STAGE_CHANGE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PORTAL_SUMMON;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        PLAYER_HEROBRINE_POSSESSION = builder.nextAccessor("biped/sculk_steve/player_herobrine_possession",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        LEGENDARY_SWORD_IDLE = builder.nextAccessor("biped/sculk_steve/legendary_sword_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        ENDER_SLAYER_SCYTHE_IDLE = builder.nextAccessor("biped/sculk_steve/ender_slayer_scythe_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_SACRIFICING = builder.nextAccessor("biped/sculk_steve/herobrine_sacrificing",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_ASSISTANCE = builder.nextAccessor("biped/sculk_steve/herobrine_assistance",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_STAGE_CHANGE = builder.nextAccessor("biped/sculk_steve/herobrine_stage_change",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        PORTAL_SUMMON = builder.nextAccessor("biped/sculk_steve/portal_summon",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));
    }
}
