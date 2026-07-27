/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * Upstream: EpicACG - dfdyz
 * Source: https://github.com/dfdyz/epicacg-1.20
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/GPL-3.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.BowAttackAnimation;
import com.pla.annoyingvillagers.util.BowFunction;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightACG {
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_1;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_2;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_3;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_4;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_5;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        BOW_AUTO_1 = builder.nextAccessor("biped/epic_agc/bow_auto1",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.62F, 0.8333F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        )
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_2 = builder.nextAccessor("biped/epic_agc/bow_auto2",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.7F, 0.98F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        )
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_3 = builder.nextAccessor("biped/epic_agc/bow_auto3",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.88F, 1.03F, 1.3F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.84F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        ));

        BOW_AUTO_4 = builder.nextAccessor("biped/epic_agc/bow_auto4",
                accessor -> new BowAttackAnimation(0.05F, 0, 2.12F, 2.733F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.2083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.7916F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(2.0416F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_5 = builder.nextAccessor("biped/epic_agc/bow_auto5",
                accessor -> new BowAttackAnimation(0.02F, 0, 0.2F, 1.51F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.7083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        ));
    }
}
