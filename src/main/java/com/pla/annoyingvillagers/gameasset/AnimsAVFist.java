package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsAVFist {
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_LEFT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_UP;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_DASH;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        FIST_DASH = builder.nextAccessor("biped/av_fist/fist_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));

        FIST_LEFT = builder.nextAccessor("biped/av_fist/fist_left",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 1.1F, ColliderPreset.FIST, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));

        FIST_UP = builder.nextAccessor("biped/av_fist/fist_up",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 0.95F, WOMWeaponColliders.KICK, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));

        WHIRLWIND_KICK = builder.nextAccessor("biped/av_fist/whirlwind_kick",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.29F, 0.45F, 0.85F, 1.8F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legR, accessor, humanoidArmature).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.5F));
    }
}
