/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: MIT
 *
 * Upstream: Epic Sanji - reascer
 * Source: https://www.curseforge.com/minecraft/mc-mods/epic-sanji
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
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightSanji {
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SANJI_DIABLE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SANJI_CONCASSER;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        SANJI_DIABLE = builder.nextAccessor("biped/epicsanji/sanji_disable",
                accessor -> (new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 1.95F, 2.15F, 3.0F, Float.MAX_VALUE,  humanoidArmature.get().rootJoint, AVCollider.SANJI_SPIN)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.BLAZE_SHOOT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.55F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.65F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.75F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.85F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.95F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.05F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.25F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.35F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.45F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.55F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(1.65F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get())
                        ));
        SANJI_CONCASSER = builder.nextAccessor("biped/epicsanji/sanji_concasser",
                accessor -> (new BasicMultipleAttackAnimation(0.1F, 1.15F, 1.9F, 2.35F, AVCollider.SANJI_SPIN, humanoidArmature.get().rootJoint, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(8.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.05F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.05F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.25F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.35F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.45F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()),
                                AnimationEvent.InTimeEvent.create(0.55F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(AVSounds.SWORD_WHOOSH.get()))
        );
    }
}
