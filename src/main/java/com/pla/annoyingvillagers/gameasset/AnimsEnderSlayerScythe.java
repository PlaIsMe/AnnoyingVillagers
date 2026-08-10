package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.client.sound.EFNSounds;
import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.gameasset.EFNExtraDamageInstance;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.merlin204.avalon.util.AvalonEventUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.skill.EnderSlayerScytheSkill;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

import static com.hm.efn.gameasset.animations.EFNLanceAnimations.MEEN_LANCE_CHARGE3;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEnderSlayerScythe {
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ENDER_SLAYER_SCYTHE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_SCYTHE_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_SCYTHE_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ENDER_SLAYER_SCYTHE_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_SLAYER_SCYTHE_AUTO5;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> ENDER_SLAYER_SCYTHE_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> ENDER_SLAYER_SCYTHE_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ENDER_SLAYER_SCYTHE_SPECIAL;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> ENDER_SLAYER_SCYTHE_INNATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ENDER_SLAYER_SCYTHE_SPECIAL_INNATE;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        MultiOBBCollider SCYTHE_COLLIDER = new MultiOBBCollider(new OBBCollider(0.25F, 0.25F, 1.3, 0.0F, 0.0F, 1.0F), new OBBCollider(0.25F, 0.25F, 1.3, 0.0F, 0.0F, 1.0F), new OBBCollider(0.7, 0.9, 0.7, 0.0F, -0.5F, -0.45), new OBBCollider(0.7, 0.9, 0.7, 0.0F, -0.5F, -0.45));

        ENDER_SLAYER_SCYTHE_AUTO1 = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_auto1",
                (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.11666667F, 0.58F, 0.7F, 1.3F, SCYTHE_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_2.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .newTimePair(0.0F, 0.725F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false));

        ENDER_SLAYER_SCYTHE_AUTO2 = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_auto2",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.15F, 0.15F, 0.45F, 0.45F, SCYTHE_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));

        ENDER_SLAYER_SCYTHE_AUTO3 = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_auto3",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.35F, 0.59F, 0.59F, humanoidArmature.get().toolR, WOMWeaponColliders.ANTITHEUS_AGRESSION), new AttackAnimation.Phase(0.59F, 0.6F, 0.65F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_AGRESSION_REAP)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_DOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_MISSING_HEALTH.create(2.0F)), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_UP, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true));

        ENDER_SLAYER_SCYTHE_AUTO4 = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_auto4",
                (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.11666667F, 0.58F, 0.75F, 1.33F, SCYTHE_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_4.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .newTimePair(0.0F, 0.75F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false));

        ENDER_SLAYER_SCYTHE_AUTO5 = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_auto5",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.15F, 0.5F, 0.75F, 0.9F, SCYTHE_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));

        ENDER_SLAYER_SCYTHE_DASH = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_dash",
                (accessor) -> (DashAttackAnimation)(new DashAttackAnimation(0.1F, 0.61F, 0.61F, 0.71F, 1.35F, SCYTHE_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_2.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .newTimePair(0.0F, 0.81F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false));

        ENDER_SLAYER_SCYTHE_AIRSLASH = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_airslash",
                (accessor) -> (AirSlashAnimation)(new AirSlashAnimation(0.1F, 0.56F, 0.65F, 1.28F, SCYTHE_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EFNSounds.WHOOSH_HEAVY_3.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.65F))
                        .newTimePair(0.0F, 0.75F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false));

        ENDER_SLAYER_SCYTHE_SPECIAL = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_special",
                animationaccessor -> new BasicAttackAnimation(0.06F, animationaccessor, humanoidArmature, (new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.5F, 0.5F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, SCYTHE_COLLIDER))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.5F, 0.5F, 0.59F, 0.59F, 0.59F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)),
                        (new AttackAnimation.Phase(0.59F, 0.59F, 0.7F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));

        ENDER_SLAYER_SCYTHE_INNATE = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_innate",
                (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.1F, accessor, Armatures.BIPED, 1.0F, 1.0F, AvalonAnimationUtils.createSimplePhase(59, 70, 100, InteractionHand.MAIN_HAND, 1.0F, 2.0F, Armatures.BIPED.get().rootJoint, MEEN_LANCE_CHARGE3)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(100.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(EFNExtraDamageInstance.LOST_HEALTH_DAMAGE_WITH_SCALING_CAP.create(0.2F, 50.0F, 100.0F), ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE, EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_MEEN)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, animation, params) -> {
                                    LivingEntity entity = entitypatch.getOriginal();
                                    entity.level().addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) -> entitypatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 5, false, false, false)), AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(0.2F, (entitypatch, self, params) ->
                                        entitypatch.playSound(SoundEvents.RESPAWN_ANCHOR_DEPLETE.get(), 1.5F, 0.0F, 0.0F), AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.1F, (entitypatch, self, params) -> {
                                    entitypatch.playSound(SoundEvents.GENERIC_EXPLODE, 1.5F, 0.0F, 0.0F);
                                    if (entitypatch instanceof PlayerPatch<?> playerPatch) {
                                        EnderSlayerScytheSkill.activateFromInnateAnimation(playerPatch);
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AvalonEventUtils.simpleGroundSplit(57, 0.0F, 0.0F, 0.0F, 0.0F, 5.0F, true),
                                AvalonEventUtils.simpleCameraShake(59, 60, 4.0F, 4.0F, 4.0F))
                        .newTimePair(0.0F, Float.MAX_VALUE)
                        .addStateRemoveOld(EntityState.MOVEMENT_LOCKED, true)
                        .addStateRemoveOld(EntityState.CAN_SWITCH_HAND_ITEM, false)
                        .addStateRemoveOld(EntityState.INACTION, true));

        ENDER_SLAYER_SCYTHE_SPECIAL_INNATE = builder.nextAccessor("biped/ender_slayer_scythe/ender_slayer_scythe_special_innate",
                accessor -> new StaticAnimation(0.0F, false, accessor, humanoidArmature));
    }
}
