package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.client.sound.EFNSounds;
import com.hm.efn.gameasset.EFNAnimations;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsAVSpear {
    public static AnimationManager.AnimationAccessor<StaticAnimation> AV_SPEAR_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> AV_SPEAR_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> AV_SPEAR_RUN;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> AV_SPEAR_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_SPEAR_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AV_SPEAR_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AV_SPEAR_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_SPEAR_AUTO5;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> AV_SPEAR_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AV_SPEAR_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> AV_SPEAR_SPECIAL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> AV_SPEAR_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STAFF_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_INNATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> AV_SPEAR_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLACKSCRATCHER_IDLE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLACKSCRATCHER_ATTACK;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        AV_SPEAR_IDLE = builder.nextAccessor("biped/av_spear/av_spear_idle",
                (accessor) -> new StaticAnimation(0.2F, true, accessor, humanoidArmature));

        AV_SPEAR_WALK = builder.nextAccessor("biped/av_spear/av_spear_walk",
                (accessor) -> new MovementAnimation(0.2F, true, accessor, humanoidArmature));

        AV_SPEAR_RUN = builder.nextAccessor("biped/av_spear/av_spear_run",
                (accessor) -> new MovementAnimation(0.2F, true, accessor, humanoidArmature));

        AV_SPEAR_AUTO1 = builder.nextAccessor("biped/av_spear/av_spear_auto1",
                (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.15F, accessor, Armatures.BIPED, 1.0F, 1.0F, AvalonAnimationUtils.createSimplePhase(32, 40, 50, InteractionHand.MAIN_HAND, 0.8F, 1.0F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN));

        AV_SPEAR_AUTO2 = builder.nextAccessor("biped/av_spear/av_spear_auto2",
                (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.25F, 0.11666667F, 0.575F, 0.71F, 1.0F, ColliderPreset.SPEAR, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_1.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .newTimePair(0.0F, 0.735F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false));

        AV_SPEAR_AUTO3 = builder.nextAccessor("biped/av_spear/av_spear_auto3",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.35F, 0.45F, Float.MAX_VALUE, humanoidArmature.get().toolR, WOMWeaponColliders.STAFF_TAIL)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        AV_SPEAR_AUTO4 = builder.nextAccessor("biped/av_spear/av_spear_auto4",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.25F, 0.35F, 0.4F, 0.4F, humanoidArmature.get().toolR, WOMWeaponColliders.STAFF_TAIL), new AttackAnimation.Phase(0.4F, 0.55F, 0.65F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, WOMWeaponColliders.STAFF_TAIL)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        AV_SPEAR_AUTO5 = builder.nextAccessor("biped/av_spear/av_spear_auto5",
                (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.01F, 0.33333334F, 0.75F, 0.86F, 1.42F, ColliderPreset.SPEAR, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_2.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .newTimePair(0.0F, 0.885F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false));

        AV_SPEAR_DASH = builder.nextAccessor("biped/av_spear/av_spear_dash",
                (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.1F, accessor, Armatures.BIPED, 1.0F, 1.0F, AvalonAnimationUtils.createSimplePhase(30, 40, 48, InteractionHand.MAIN_HAND, 1.0F, 1.0F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.01F, 0.2F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN));

        AV_SPEAR_AIRSLASH = builder.nextAccessor("biped/av_spear/av_spear_airslash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.75F, 0.79F, 0.79F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.79F, 0.8F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 6)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null));

        AV_SPEAR_SPECIAL = builder.nextAccessor("biped/av_spear/av_spear_special",
                accessor -> new AttackAnimation(0.11F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, humanoidArmature.get().toolR, ColliderPreset.SPEAR),
                        new AttackAnimation.Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, humanoidArmature.get().toolR, ColliderPreset.SPEAR),
                        new AttackAnimation.Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.SPEAR))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));

        AV_SPEAR_INNATE = builder.nextAccessor("biped/falchion/av_spear_innate",
                (animationaccessor) -> (new AttackAnimation(0.1F, 0.25F, 0.58F, 0.667F, 1.0F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));

        STAFF_INNATE = builder.nextAccessor("biped/av_spear/staff_innate",
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

        SICKLE_INNATE = builder.nextAccessor("biped/av_spear/sickle_innate",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, humanoidArmature, (new AttackAnimation.Phase(0.0F, 0.66F, 0.81F, 0.81F, 0.81F, humanoidArmature.get().toolR, ColliderPreset.SPEAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_4.get()), (new AttackAnimation.Phase(0.81F, 0.81F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, ColliderPreset.SPEAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_4.get()), (new AttackAnimation.Phase(1.0F, 1.05F, 1.21F, 2.13F, 2.13F, humanoidArmature.get().toolR, ColliderPreset.SPEAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_2.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .newTimePair(0.0F, 1.63F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.83F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        AV_SPEAR_GUARD = builder.nextAccessor("biped/av_spear/av_spear_guard",
                accessor -> new StaticAnimation(0.05F, true, accessor, humanoidArmature)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.0F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.7F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT)));

        BLACKSCRATCHER_IDLE = builder.nextAccessor("biped/pla/blackscratcher_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        BLACKSCRATCHER_ATTACK = builder.nextAccessor("biped/pla/blackscratcher_attack.",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));
    }
}