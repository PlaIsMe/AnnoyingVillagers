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
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightACG {
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_1;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_2;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_3;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_4;
    public static AnimationManager.AnimationAccessor<BowAttackAnimation> BOW_AUTO_5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GS_LAODENG_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_DASH;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        BOW_AUTO_1 = builder.nextAccessor("biped/epic_acg/bow_auto1",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.62F, 0.8333F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        )
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_2 = builder.nextAccessor("biped/epic_acg/bow_auto2",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.7F, 0.98F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        )
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_3 = builder.nextAccessor("biped/epic_acg/bow_auto3",
                accessor -> new BowAttackAnimation(0.1F, 0.0F, 0.88F, 1.03F, 1.3F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.84F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        ));

        BOW_AUTO_4 = builder.nextAccessor("biped/epic_acg/bow_auto4",
                accessor -> new BowAttackAnimation(0.05F, 0, 2.12F, 2.733F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.2083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(1.7916F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(2.0416F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F));

        BOW_AUTO_5 = builder.nextAccessor("biped/epic_acg/bow_auto5",
                accessor -> new BowAttackAnimation(0.02F, 0, 0.2F, 1.51F, 1.2F, InteractionHand.MAIN_HAND, null, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (anim, entity, a, b, c) -> 3.0F)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.7083F, (livingEntityPatch, assetAccessor, objects) -> BowFunction.bowShoot(livingEntityPatch), AnimationEvent.Side.BOTH)
                        ));
        SAO_RAPIER_AUTO1 = builder.nextAccessor("biped/epic_acg/sao_rapier_auto1",
                (animationaccessor) -> (BasicAttackAnimation) (new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, (humanoidArmature.get()).toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        SAO_RAPIER_AUTO2 = builder.nextAccessor("biped/epic_acg/sao_rapier_auto2",
                (animationaccessor) -> (BasicAttackAnimation) (new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, (humanoidArmature.get()).toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        SAO_RAPIER_AUTO3 = builder.nextAccessor("biped/epic_acg/sao_rapier_auto3",
                (animationaccessor) -> (BasicAttackAnimation) (new BasicAttackAnimation(0.02F, 0.1F, 0.2F, 0.4F, null, (humanoidArmature.get()).toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        SAO_RAPIER_AUTO4 = builder.nextAccessor("biped/epic_acg/sao_rapier_auto4",
                (animationaccessor) -> (BasicAttackAnimation) (new BasicAttackAnimation(0.05F, animationaccessor, humanoidArmature, new AttackAnimation.Phase[]{
                        (new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, (humanoidArmature.get()).toolR, null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F)),
                        (new AttackAnimation.Phase(0.2F, 0.25F, 0.35F, 0.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, (humanoidArmature.get()).toolR, null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                })).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        SAO_RAPIER_AUTO5 = builder.nextAccessor("biped/epic_acg/sao_rapier_auto5",
                (animationaccessor) -> (BasicAttackAnimation) (new BasicAttackAnimation(0.02F, 0.2F, 0.3F, 0.65F, null, (humanoidArmature.get()).toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        GS_LAODENG_AUTO5 = builder.nextAccessor("biped/epic_acg/gs_laodeng_auto5",
                animationaccessor -> new BasicAttackAnimation(0.06F, animationaccessor, humanoidArmature, (new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.5F, 0.5F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.5F, 0.5F, 0.59F, 0.59F, 0.59F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.59F, 0.59F, 0.7F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));
        BATTLE_SCYTHE_AUTO1 = builder.nextAccessor("biped/epic_acg/battle_scythe_auto1",
                animationaccessor -> new BasicAttackAnimation(0.08F, 0.2F, 0.4F, 0.5F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        BATTLE_SCYTHE_AUTO2 = builder.nextAccessor("biped/epic_acg/battle_scythe_auto2",
                animationaccessor -> new BasicAttackAnimation(0.08F, 0.2F, 0.3F, 0.4F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        BATTLE_SCYTHE_AUTO3 = builder.nextAccessor("biped/epic_acg/battle_scythe_auto3",
                animationaccessor -> new BasicAttackAnimation(0.04F, 0.3F, 0.4F, 0.55F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        BATTLE_SCYTHE_AUTO4 = builder.nextAccessor("biped/epic_acg/battle_scythe_auto4",
                animationaccessor -> new BasicAttackAnimation(0.06F, animationaccessor, humanoidArmature, new AttackAnimation.Phase[]{
                        (new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.4F, 0.5F, 0.55F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.6F, 0.9F, 1.0F, 1.1F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                }).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        BATTLE_SCYTHE_AUTO5 = builder.nextAccessor("biped/epic_acg/battle_scythe_auto5",
                animationaccessor -> new BasicAttackAnimation(0.06F, 0.25F, 0.4F, 0.8F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        BATTLE_SCYTHE_DASH = builder.nextAccessor("biped/epic_acg/battle_scythe_dash",
                animationaccessor -> new BasicAttackAnimation(0.1F, animationaccessor, humanoidArmature, new AttackAnimation.Phase[]{
                        (new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.4F, 0.5F, 0.6F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                }).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
    }
}
