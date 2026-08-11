/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *
 * Upstream: Epic Fight - Infernal Gainer - reascer
 * Source: https://www.curseforge.com/minecraft/mc-mods/epic-fight-infernal-gainer
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightInfernalGainer {
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> INFERNAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> INFERNAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> INFERNAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_INFERNAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_INFERNAL_AUTO_2;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        INFERNAL_AUTO_1 = builder.nextAccessor("biped/epicfight_infernal_gainer/infernal_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, ColliderPreset.FIST, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
        INFERNAL_AUTO_2 = builder.nextAccessor("biped/epicfight_infernal_gainer/infernal_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.1F, 0.2F, 0.25F, ColliderPreset.FIST, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
        INFERNAL_AUTO_3 = builder.nextAccessor("biped/epicfight_infernal_gainer/infernal_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.35F, 0.45F, 0.5F, ColliderPreset.FIST, humanoidArmature.get().legL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));
        OBSIDIAN_INFERNAL_AUTO_1 = builder.nextAccessor("biped/epicfight_infernal_gainer/obsidian_infernal_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.4F));
        OBSIDIAN_INFERNAL_AUTO_2 = builder.nextAccessor("biped/epicfight_infernal_gainer/obsidian_infernal_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.1F, 0.2F, 0.25F, ColliderPreset.FIST, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.4F));
    }
}
