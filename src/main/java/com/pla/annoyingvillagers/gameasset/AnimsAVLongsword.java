package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsAVLongsword {
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_LONGSWORD_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_LONGSWORD_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_LONGSWORD_DUAL_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_LONGSWORD_DUAL_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_LONGSWORD_DUAL_DASH;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        AV_LONGSWORD_DUAL_AUTO2 = builder.nextAccessor("biped/av_longsword/av_longsword_dual_auto2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        AV_LONGSWORD_DUAL_AUTO3 = builder.nextAccessor("biped/av_longsword/av_longsword_dual_auto3",
                accessor -> new BasicAttackAnimation(0.16F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        AV_LONGSWORD_DUAL_AUTO4 = builder.nextAccessor("biped/av_longsword/av_longsword_dual_auto4",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));

        AV_LONGSWORD_DUAL_AUTO5 = builder.nextAccessor("biped/av_longsword/av_longsword_dual_auto5",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));

        AV_LONGSWORD_DUAL_DASH = builder.nextAccessor("biped/av_longsword/av_longsword_dual_dash",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.7F, 0.8F, 1.0F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.25F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)));
    }
}
