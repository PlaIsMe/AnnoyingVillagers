package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.registries.EFNMobEffectRegistry;
import com.hm.efn.util.EffectConditionParticleTrail;
import com.hm.efn.util.EffectEntityInvoker;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.EnderGlaiveItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEnderGlaive {
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ENDER_GLAIVE_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ENDER_GLAIVE_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ENDER_GLAIVE_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_AUTO5;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> ENDER_GLAIVE_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ENDER_GLAIVE_SPECIAL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ENDER_GLAIVE_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ENDER_GLAIVE_INNATE_SPECIAL;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        ENDER_GLAIVE_AUTO1 = builder.nextAccessor("biped/ender_glaive/ender_glaive_auto1",
                (accessor) -> (BasicMultipleAttackAnimation) (new BasicMultipleAttackAnimation(
                        0.1F,
                        accessor,
                        humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.3F, 0.55F, 0.65F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.29F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));

        ENDER_GLAIVE_AUTO2 = builder.nextAccessor("biped/ender_glaive/ender_glaive_auto2",
                (accessor) -> (AvalonAttackAnimation) (new AvalonAttackAnimation(
                        0.1F,
                        accessor,
                        Armatures.BIPED,
                        1.0F,
                        1.0F,
                        AvalonAnimationUtils.createSimplePhase(37, 44, 60, InteractionHand.MAIN_HAND, 0.8F, 1.0F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_TARGET_LOCATION_ROTATION)
                        .addProperty(AnimationProperty.ActionAnimationProperty.ENTITY_YROT_PROVIDER, MoveCoordFunctions.LOOK_DEST)
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN)
                        .addEvents(
                                EffectConditionParticleTrail.buffedParticleTrail(33, 44, InteractionHand.MAIN_HAND, new Vec3(0.0F, 0.0F, -1.5F), new Vec3(0.0F, 0.0F, -1.8F), 4.0F, 3, ParticleTypes.LAVA, 0.2F, EFNMobEffectRegistry.MEEN_LANCE.get())
                        ));

        ENDER_GLAIVE_AUTO3 = builder.nextAccessor("biped/ender_glaive/ender_glaive_auto3",
                (accessor) -> (AvalonAttackAnimation) (new AvalonAttackAnimation(
                        0.1F,
                        accessor,
                        Armatures.BIPED,
                        1.0F,
                        1.0F,
                        AvalonAnimationUtils.createSimplePhase(40, 46, 55, InteractionHand.MAIN_HAND, 0.5F, 0.5F, Armatures.BIPED.get().toolR, null),
                        AvalonAnimationUtils.createSimplePhase(61, 70, 72, InteractionHand.MAIN_HAND, 0.5F, 0.5F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE)
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN)
                        .addEvents(
                                EffectEntityInvoker.clearFireWind(20),
                                EffectConditionParticleTrail.buffedParticleTrail(30, 43, InteractionHand.MAIN_HAND, new Vec3(0.0F, 0.0F, -1.7F), new Vec3(0.0F, 0.0F, -2.15F), 5.0F, 1, ParticleTypes.FLAME, 0.3F, EFNMobEffectRegistry.MEEN_LANCE.get()),
                                EffectConditionParticleTrail.buffedParticleTrail(50, 69, InteractionHand.MAIN_HAND, new Vec3(0.0F, 0.0F, -1.7F), new Vec3(0.0F, 0.0F, -2.15F), 5.0F, 1, ParticleTypes.LAVA, 0.3F, EFNMobEffectRegistry.MEEN_LANCE.get())
                        ));

        ENDER_GLAIVE_AUTO4 = builder.nextAccessor("biped/ender_glaive/ender_glaive_auto4",
                (accessor) -> (AvalonAttackAnimation) (new AvalonAttackAnimation(
                        0.1F,
                        accessor,
                        Armatures.BIPED,
                        1.0F,
                        1.0F,
                        AvalonAnimationUtils.createSimplePhase(30, 36, 50, InteractionHand.MAIN_HAND, 0.5F, 0.6F, Armatures.BIPED.get().toolR, null),
                        AvalonAnimationUtils.createSimplePhase(64, 70, 80, InteractionHand.MAIN_HAND, 0.5F, 0.6F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN)
                        .addEvents(
                                EffectConditionParticleTrail.buffedParticleTrail(25, 40, InteractionHand.MAIN_HAND, new Vec3(0.0F, 0.0F, -2.0F), new Vec3(0.0F, 0.0F, -2.15F), 8.0F, 3, ParticleTypes.FLAME, 0.5F, EFNMobEffectRegistry.MEEN_LANCE.get()),
                                EffectConditionParticleTrail.buffedParticleTrail(58, 72, InteractionHand.MAIN_HAND, new Vec3(0.0F, 0.0F, -2.0F), new Vec3(0.0F, 0.0F, -2.15F), 8.0F, 2, ParticleTypes.LAVA, 0.1F, EFNMobEffectRegistry.MEEN_LANCE.get())
                        ));

        ENDER_GLAIVE_AUTO5 = builder.nextAccessor("biped/ender_glaive/ender_glaive_auto5",
                (accessor) -> (BasicMultipleAttackAnimation) (new BasicMultipleAttackAnimation(
                        0.05F,
                        accessor,
                        humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.25F, 0.25F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.25F, 0.25F, 0.35F, 0.55F, 0.55F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.55F, 0.55F, 0.7F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));

        ENDER_GLAIVE_DASH = builder.nextAccessor("biped/ender_glaive/ender_glaive_dash",
                (accessor) -> (AvalonAttackAnimation) (new AvalonAttackAnimation(
                        0.1F,
                        accessor,
                        Armatures.BIPED,
                        1.0F,
                        1.0F,
                        AvalonAnimationUtils.createSimplePhase(30, 36, 42, InteractionHand.MAIN_HAND, 0.7F, 0.7F, Armatures.BIPED.get().toolR, null),
                        AvalonAnimationUtils.createSimplePhase(42, 48, 65, InteractionHand.MAIN_HAND, 0.7F, 0.7F, Armatures.BIPED.get().toolR, null),
                        AvalonAnimationUtils.createSimplePhase(65, 74, 90, InteractionHand.MAIN_HAND, 1.0F, 1.0F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .newTimePair(0.0F, 0.85F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN));

        ENDER_GLAIVE_AIRSLASH = builder.nextAccessor("biped/ender_glaive/ender_glaive_airslash",
                (accessor) -> (BasicMultipleAttackAnimation) (new BasicMultipleAttackAnimation(
                        0.15F,
                        accessor,
                        humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.05F, 0.2F, 0.25F, 0.25F, humanoidArmature.get().toolR, WOMWeaponColliders.AGONY_AIRSLASH),
                        new AttackAnimation.Phase(0.25F, 0.3F, 0.45F, 0.49F, 0.49F, humanoidArmature.get().toolR, WOMWeaponColliders.AGONY_AIRSLASH),
                        new AttackAnimation.Phase(0.49F, 0.5F, 0.75F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, WOMWeaponColliders.AGONY_AIRSLASH)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(
                                AnimationEvent.InPeriodEvent.create(0.2F, 0.4F, ReuseableEvents.LOOPED_FALLING_MOVE_FRONT, AnimationEvent.Side.BOTH)
                        )
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, ReuseableEvents.LOOPED_FALLING, AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(0.65F, ReuseableEvents.AGONY_GROUNDSLAM, AnimationEvent.Side.CLIENT)
                        ));

        ENDER_GLAIVE_SPECIAL = builder.nextAccessor("biped/ender_glaive/ender_glaive_special",
                (accessor) -> (AttackAnimation) (new AttackAnimation(0.1F, 0.06F, 0.06F, 0.2F, 1.16F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.5F)
                        .newTimePair(0.0F, 0.83F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, 0.33F));

        ENDER_GLAIVE_INNATE = builder.nextAccessor("biped/ender_glaive/ender_glaive_innate",
                (accessor) -> (AttackAnimation) (new AttackAnimation(0.15F, 0.0F, 0.85F, 1.1F, 1.63F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                EpicfightUtil.cameraZoomInEvent(0.05F, -0.35F, 30),
                                AnimationEvent.InTimeEvent.create(1.15F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        EnderGlaiveItem.spawnVacumSlise(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, AnimationEvent.Side.SERVER),
                                EpicfightUtil.cameraZoomOutBlurEvent(1.15F, 10.0F, 20)
                        )
                        .newTimePair(0.0F, 0.66F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.86F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 1.16F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.33F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, 0.86F));

        ENDER_GLAIVE_INNATE_SPECIAL = builder.nextAccessor("biped/ender_glaive/ender_glaive_innate_special",
                (accessor) -> (AttackAnimation) (new AttackAnimation(0.15F, 0.0F, 1.15F, 1.3F, 2.0F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                EpicfightUtil.cameraZoomInEvent(0.05F, -0.35F, 30),
                                AnimationEvent.InTimeEvent.create(1.35F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        EnderGlaiveItem.spawnVacumSlise(serverLevel, livingEntityPatch.getOriginal(), EnderGlaiveItem.DEFAULT_DAMAGE * 2);
                                    }
                                }, AnimationEvent.Side.SERVER),
                                EpicfightUtil.cameraZoomOutBlurEvent(1.35F, 10.0F, 20)
                        )
                        .newTimePair(0.0F, 0.9F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(1.0F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 1.5F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.66F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, 1.0F));
    }
}