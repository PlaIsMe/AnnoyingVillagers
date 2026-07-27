/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * Upstream: Epic Fight - Guandao Moveset - namelesslk
 * Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-guandao-moveset
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
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightGuandao {
    public static AnimationManager.AnimationAccessor<StaticAnimation> FALCHION_IDLE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> FALCHION_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> FALCHION_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> FALCHION_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FALCHION_FORWARD;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FALCHION_BACKWARD;
    public static AnimationManager.AnimationAccessor<AttackAnimation> FALCHION_SIDE;
    
    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        FALCHION_IDLE = builder.nextAccessor("biped/falchion/falchion_idle",
                (animationaccessor) -> new StaticAnimation(true, animationaccessor, humanoidArmature));
        FALCHION_AUTO1 = builder.nextAccessor("biped/falchion/falchion_auto1",
                (animationaccessor) -> (new BasicAttackAnimation(0.1F, 0.25F, 0.317F, 0.5F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        FALCHION_AUTO2 = builder.nextAccessor("biped/falchion/falchion_auto2",
                (animationaccessor) -> (new BasicAttackAnimation(0.05F, 0.167F, 0.233F, 0.5F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        FALCHION_AUTO3 = builder.nextAccessor("biped/falchion/falchion_auto3",
                (animationaccessor) -> (new BasicAttackAnimation(0.05F, 0.333F, 0.383F, 0.95F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        FALCHION_FORWARD = builder.nextAccessor("biped/falchion/falchion_forward", (animationaccessor) ->
                (new AttackAnimation(0.05F, animationaccessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.6F, 0.667F, 0.75F, 0.75F, humanoidArmature.get().toolR, null),
                        (new AttackAnimation.Phase(0.75F, 0.85F, 0.9F, 1.35F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.95F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        FALCHION_BACKWARD = builder.nextAccessor("biped/falchion/falchion_backward",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, humanoidArmature,
                        (new AttackAnimation.Phase(0.0F, 0.5F, 0.6F, 1.0F, 1.0F, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F)),
                        (new AttackAnimation.Phase(1.0F, 1.63F, 1.7F, 2.33F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.85F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
        FALCHION_SIDE = builder.nextAccessor("biped/falchion/falchion_side",
                (animationaccessor) -> (new AttackAnimation(0.1F, 0.25F, 0.58F, 0.667F, 1.0F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
    }
}
