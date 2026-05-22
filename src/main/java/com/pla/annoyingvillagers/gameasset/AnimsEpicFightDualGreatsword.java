/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: MIT
 *
 * Upstream: Epic Fight - Dual GreatSword - reascer
 * Source: https://www.curseforge.com/minecraft/mc-mods/epicfight-dual-greatsword
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
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightDualGreatsword {
    // Animation from EpicFight Dual GreatSword
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_TWOHAND_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_TWOHAND_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> GREATSWORD_DUAL_AIRSLASH;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> GREATSWORD_DUAL_EARTHQUAKE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE_PILLAR;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        GREATSWORD_TWOHAND_AUTO_1 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_twohand_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new AttackAnimation.Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F));
        GREATSWORD_TWOHAND_AUTO_2 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_twohand_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        GREATSWORD_DUAL_AUTO_1 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new AttackAnimation.Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        GREATSWORD_DUAL_AUTO_2 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.85F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        GREATSWORD_DUAL_AUTO_3 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        GREATSWORD_DUAL_AUTO_4 = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.8F, 1.0F, 1.25F, InteractionHand.OFF_HAND, ColliderPreset.DUAL_SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.75F));
        GREATSWORD_DUAL_DASH = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_dash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.1F, 0.4F, 0.4F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        GREATSWORD_DUAL_AIRSLASH = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_airslash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, WOMWeaponColliders.TORMENT_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        GREATSWORD_DUAL_EARTHQUAKE = builder.nextAccessor("biped/epicfight_dual_greatsword/greatsword_dual_earthquake",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD),
                        new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, ColliderPreset.DUAL_SWORD))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_1 = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_twohand_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new AttackAnimation.Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_TWOHAND_AUTO_2 = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_twohand_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AUTO_3 = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_AIRSLASH = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_airslash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_earthquake",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.25F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_CIRCLE, AnimationEvent.Side.SERVER)
                        ));

        SHADOW_OBSIDIAN_SWORD_GREATSWORD_DUAL_EARTHQUAKE_PILLAR = builder.nextAccessor("biped/epicfight_dual_greatsword/shadow_obsidian_sword_greatsword_dual_earthquake_pillar",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.25F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_CIRCLE, AnimationEvent.Side.SERVER)
                        ));
    }
}
