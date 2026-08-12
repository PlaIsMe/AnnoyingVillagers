package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import com.pla.annoyingvillagers.entity.TridentLightningBolt;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsBlueDemonTrident {
    public static AnimationManager.AnimationAccessor<MovementAnimation> BLUE_DEMON_TRIDENT_TWOHAND_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLUE_DEMON_TRIDENT_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLUE_DEMON_TRIDENT_AUTO2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_AUTO4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_AUTO5;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_AUTO6;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_DASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLUE_DEMON_TRIDENT_THROW_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLUE_DEMON_TRIDENT_THROW_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> BLUE_DEMON_TRIDENT_THROW_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_THROW_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_THROW_5;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_THROW_DASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLUE_DEMON_TRIDENT_THROW_AIRSLASH;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_TRIDENT_SPECIAL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLUE_DEMON_TRIDENT_SPECIAL_LEGENDARY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_DEMON_TRIDENT_FESTIVAL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_DEMON_TRIDENT_THUNDER_ATTACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_DEMON_TRIDENT_ELECTRIC_FIELD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_STATE_TRANSFORM;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_STATE_TRANSFORM_END;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE_LEGENDARY_SWORD_START;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ZAP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ZAP_LONG;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        BLUE_DEMON_TRIDENT_TWOHAND_RUN = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_twohand_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));

        BLUE_DEMON_TRIDENT_AUTO1 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_auto1", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.2f, 0.3f, 0.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));

        BLUE_DEMON_TRIDENT_AUTO2 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_auto2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));

        BLUE_DEMON_TRIDENT_AUTO3 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_auto3", access ->
                new AttackAnimation(0.2f, 0.6f, 0.5f, 0.6f, 1.9f, ColliderPreset.BATTOJUTSU_DASH, Armatures.BIPED.get().rootJoint, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.5f))
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, speed, prevElapsedTime, elapsedTime) ->
                        {
                            if (elapsedTime >= 0.5F && elapsedTime < 0.6F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float)Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -1.5F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate(-(float)Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                if (distanceToGround > 0.5F) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.025F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.3F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.5f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT)
                                        .params(SoundEvents.TRIDENT_HIT_GROUND),
                                AnimationEvent.InTimeEvent.create(0.5F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                        .params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().rootJoint, 1.2, 1F)));

        BLUE_DEMON_TRIDENT_AUTO4 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_aut4",
                accessor -> new AttackAnimation(0.05F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.167F, 0.167F, 0.38F, 1.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolL, null))
                        .addProperty(AnimationProperty.AttackAnimationProperty.REMOVE_DELTA_MOVEMENT, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.5F)
                        .newTimePair(0.0F, 0.3F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.3F, 10.0F));

        BLUE_DEMON_TRIDENT_AUTO5 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_auto5", access ->
                new AttackAnimation(0.2f, 0.0f, 0.75f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.2f, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.35f, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        Vec3 tridentTip = EpicfightUtil.getJointWithTranslation(
                                                livingEntityPatch.getOriginal(), new Vec3f(0,0,0), Armatures.BIPED.get().toolR, 1.2F, 0.0F
                                        );
                                        if (tridentTip != null) {
                                            BlockPos.MutableBlockPos checkPos = BlockPos.containing(tridentTip).mutable();
                                            while (checkPos.getY() > serverLevel.getMinBuildHeight()
                                                    && !serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) {
                                                checkPos.move(0, -1, 0);
                                            }
                                            if (serverLevel.getBlockState(checkPos).isSolidRender(serverLevel, checkPos)) {
                                                TridentLightningBolt tridentLightningBolt = new TridentLightningBolt(AnnoyingVillagersModEntities.TRIDENT_LIGHTNING_BOLT.get(), serverLevel);
                                                tridentLightningBolt.setOwner(livingEntityPatch.getOriginal());
                                                tridentLightningBolt.moveTo(
                                                        checkPos.getX() + 0.5D,
                                                        checkPos.getY() + 1.0D,
                                                        checkPos.getZ() + 0.5D
                                                );
                                                serverLevel.addFreshEntity(tridentLightningBolt);
                                            }
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));

        BLUE_DEMON_TRIDENT_AUTO6 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_auto6", access ->
                new AttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.3f, 0.3f, 0.4f, 0.4f, 0.4f, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f)),
                        new AttackAnimation.Phase(0.4f, 0.0f, 0.4f, 0.5f, 0.5f, 0.5f, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.15f)),
                        new AttackAnimation.Phase(0.5f, 0.0f, 0.5f, 0.6f, 0.6f, 0.6f, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2f)),
                        new AttackAnimation.Phase(0.6f, 0.0f, 0.6f, 0.7f, 0.7f, 0.7f, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.7f, 0.0f, 0.7f, 0.8f, 0.8f, 0.8f, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f)),
                        new AttackAnimation.Phase(0.8f, 0.0f, 0.8f, 0.9f, 0.9f, 0.9f, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35f)),
                        new AttackAnimation.Phase(0.9f, 0.0f, 1.25f, 1.35f, 2f, 2f, InteractionHand.MAIN_HAND, humanoidArmature.get().rootJoint, ColliderPreset.BATTOJUTSU_DASH)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.7F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.7F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)));

        BLUE_DEMON_TRIDENT_DASH = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_dash", accessor -> new AttackAnimation(0.1F, 0.2F, 0.35F, 0.45F, 0.7F, ColliderPreset.BIPED_BODY_COLLIDER, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(2.0F))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.RAW_COORD_WITH_X_ROT)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.85F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.ROOT_X_MODIFIER)
                .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(Animations.ReusableSources.RESTORE_BOUNDING_BOX, AnimationEvent.Side.BOTH))
                .addEvents(AnimationProperty.StaticAnimationProperty.TICK_EVENTS, AnimationEvent.SimpleEvent.create(Animations.ReusableSources.RESIZE_BOUNDING_BOX, AnimationEvent.Side.BOTH)
                        .params(EntityDimensions.scalable(0.6F, 1.0F)))
                .addEvents(AnimationEvent.InPeriodEvent.create(0.35F, 1.0F, (entitypatch, animation, params) -> {
                    Vec3 pos = entitypatch.getOriginal().position();

                    for(int x = -1; x <= 1; x += 2) {
                        for(int z = -1; z <= 1; z += 2) {
                            Vec3 rand = new Vec3(Math.random() * (double)x, Math.random(), Math.random() * (double)z).normalize().scale(2.0F);
                            entitypatch.getOriginal().level().addParticle(EpicFightParticles.TSUNAMI_SPLASH.get(), pos.x + rand.x, pos.y + rand.y - (double)1.0F, pos.z + rand.z, rand.x * 0.1, rand.y * 0.1, rand.z * 0.1);
                        }
                    }
                }, AnimationEvent.Side.CLIENT)));

        BLUE_DEMON_TRIDENT_AIRSLASH = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_airslash",
                accessor -> new AttackAnimation(0.15F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.0F, 0.8F, 0.9F, 0.9F, 0.9F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                        new AttackAnimation.Phase(0.9F, 0.9F, 0.95F, 1.05F, 1.05F, 1.05F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                        new AttackAnimation.Phase(1.05F, 1.05F, 1.15F, 1.25F, 10.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.STOP_MOVEMENT, false)
                        .newTimePair(0.0F, 1.45F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.95F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        BLUE_DEMON_TRIDENT_THROW_1 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_1", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.2f, 0.3f, 0.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0f, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.05f, THROW_TRIDENT_HAND_RIGHT, AnimationEvent.Side.SERVER)));

        BLUE_DEMON_TRIDENT_THROW_2 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, THROW_TRIDENT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        BLUE_DEMON_TRIDENT_THROW_3 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_3", accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.5F, 0.3F, 0.3F, InteractionHand.OFF_HAND, humanoidArmature.get().handR, WOMWeaponColliders.PUNCH),
                new AttackAnimation.Phase(0.3F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.PUNCH))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.TRIDENT_RETURN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.1F, PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.3F, PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                            if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                BlueDemonThunderBeamEntity beam = new BlueDemonThunderBeamEntity(
                                        AnnoyingVillagersModEntities.BLUE_DEMON_THUNDER_BEAM.get(),
                                        serverLevel,
                                        livingEntityPatch.getOriginal(),
                                        10,
                                        6,
                                        7.5F
                                );
                                beam.initSpawnState();
                                serverLevel.addFreshEntity(beam);
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.5F, PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, AnimationEvent.Side.SERVER)
                ));

        BLUE_DEMON_TRIDENT_THROW_4 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_4",
                accessor -> new AttackAnimation(0.15F, 0.53F, 0.53F, 1.2F, 1.2F, ColliderPreset.FIST, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.43F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.43F, THROW_TRIDENT_HAND_LEFT_LIGHTNING, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.43F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.43F, THROW_TRIDENT_HAND_RIGHT_LIGHTNING, AnimationEvent.Side.SERVER))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.5F)
                        .newTimePair(0.0F, 0.76F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));
        
        BLUE_DEMON_TRIDENT_THROW_5 = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_5", access ->
                new AttackAnimation(0.2f, 0.0f, 0.75f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.2f, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.35f, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4f, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4f, THROW_TRIDENT_HAND_RIGHT_EXPLODE, AnimationEvent.Side.SERVER)));
        
        BLUE_DEMON_TRIDENT_THROW_DASH = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_dash",
                accessor -> new AttackAnimation(0.15F, 0.33F, 0.33F, 1.33F, 1.33F, ColliderPreset.FIST, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.15F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.15F, THROW_TRIDENT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.23F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.23F, THROW_TRIDENT_HAND_RIGHT, AnimationEvent.Side.SERVER))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .newTimePair(0.0F, 0.6F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.83F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        BLUE_DEMON_TRIDENT_THROW_AIRSLASH = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_throw_airslash",
                accessor -> new AttackAnimation(0.15F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.0F, 0.33F, 0.46F, 0.46F, 0.46F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F)),
                        new AttackAnimation.Phase(0.46F, 0.46F, 0.47F, 0.6F, 10.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED, true).addProperty(AnimationProperty.AttackAnimationProperty.STOP_MOVEMENT, false)
                        .newTimePair(0.0F, 0.85F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 1.35F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, THROW_TRIDENT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, THROW_TRIDENT_HAND_RIGHT, AnimationEvent.Side.SERVER)
                        ));

        BLUE_DEMON_TRIDENT_SPECIAL = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_special",
                accessor -> new StaticAnimation(0.1F, false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.25F, TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, TRIDENT_SPINNING, AnimationEvent.Side.CLIENT)));

        BLUE_DEMON_TRIDENT_SPECIAL_LEGENDARY = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_special_legendary",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, THROW_TRIDENT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        BLUE_DEMON_TRIDENT_FESTIVAL = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_festival",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel) {
                                        if (livingEntityPatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity) {
                                            blueDemonEntity.setState(1);
                                            blueDemonEntity.playSound(AnnoyingVillagersModSounds.BLUE_DEMON_SAY_TRIDENT_FESTIVAL.get(), 1.0F, 1.0F);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        if (livingEntityPatch.getOriginal() instanceof BlueDemonEntity) {
                                            BlueDemonTridentItem.summonMissingTridentAndAnimate(serverLevel, livingEntityPatch.getOriginal());
                                        }
                                        ScreenShakeUtil.applyScreenShake(serverLevel, livingEntityPatch.getOriginal().blockPosition().getCenter(), 12.0, 80, 8);
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.spawnDamageZones(serverLevel, livingEntityPatch.getOriginal());
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal(), true);
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal(), true);
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.summonSuperLightningAtGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                        BlueDemonTridentItem.setStormEnergy(livingEntityPatch.getOriginal().getMainHandItem(), 0);
                                        BlueDemonTridentItem.setStormEnergy(livingEntityPatch.getOriginal().getOffhandItem(), 0);
                                        if (livingEntityPatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity) {
                                            blueDemonEntity.beginStateTwoTransform();
                                            livingEntityPatch.playAnimationSynchronized(BLUE_DEMON_STATE_TRANSFORM, 0.0F);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        BLUE_DEMON_TRIDENT_THUNDER_ATTACK = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_thunder_attack",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(4.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.summonLightningAtGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER)
                        ));

        BLUE_DEMON_TRIDENT_ELECTRIC_FIELD = builder.nextAccessor("biped/blue_demon_trident/blue_demon_trident_electric_field",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.spawnDamageZones(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        BLUE_DEMON_STATE_TRANSFORM = builder.nextAccessor("biped/blue_demon_trident/blue_demon_state_transform",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        BLUE_DEMON_STATE_TRANSFORM_END = builder.nextAccessor("biped/blue_demon_trident/blue_demon_state_transform_end",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));

        BLUE_DEMON_DIE = builder.nextAccessor("biped/blue_demon_trident/blue_demon_die",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));

        BLUE_DEMON_DIE_LEGENDARY_SWORD_START = builder.nextAccessor("biped/blue_demon_trident/blue_demon_die_legendary_sword_start",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));

        BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK = builder.nextAccessor("biped/blue_demon_trident/blue_demon_die_legendary_sword_tick",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        ZAP = builder.nextAccessor("biped/blue_demon_trident/zap",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
        );

        ZAP_LONG = builder.nextAccessor("biped/blue_demon_trident/zap_long",
                accessor -> (LongHitAnimation) new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
        );
    }

    private static final AnimationEvent.E0 TRIDENT_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), SoundEvents.TRIDENT_RETURN, SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);

    private static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_LEFT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handL, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                        );
                        if (jointVec == null) return;

                        BlueDemonUtil.spawnBlueDemonEffect(serverLevel, livingEntity, jointVec, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                        float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                        float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                        serverLevel.playSound(
                                null,
                                BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                SoundSource.NEUTRAL,
                                volume,
                                pitch
                        );
                    }
                }
            };

    private static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_WEAPON_RIGHT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().toolR, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                        );
                        if (jointVec == null) return;

                        BlueDemonUtil.spawnBlueDemonEffect(serverLevel, livingEntity, jointVec, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                        float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                        float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                        serverLevel.playSound(
                                null,
                                BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                SoundSource.NEUTRAL,
                                volume,
                                pitch
                        );
                    }
                }
            };

    private static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_RIGHT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handR, new Random().nextFloat(-1.0F, 1.0F), 0.0F
                        );
                        if (jointVec == null) return;

                        BlueDemonUtil.spawnBlueDemonEffect(serverLevel, livingEntity, jointVec, 1, 0.0D, 0.0D, 0.0D, 0.0D);

                        float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                        float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                        serverLevel.playSound(
                                null,
                                BlockPos.containing(jointVec.x, jointVec.y, jointVec.z),
                                AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                SoundSource.NEUTRAL,
                                volume,
                                pitch
                        );
                    }
                }
            };

    private static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack stack =  livingEntity.getOffhandItem();
                    Item weapon = stack.getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        if (livingEntity instanceof Player player) {
                            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                        }
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handL, 0.0F, 0.0F
                        );
                        if (jointVec == null) return;

                        Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                        if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                        trident.assignSpawnSequence(livingEntity);
                        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                        trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                        trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                        trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                        float speed = 2.5F;
                        float inaccuracy = 1.0F;

                        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                        trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                        serverLevel.addFreshEntity(trident);
                    }
                }
            };

    private static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack stack = livingEntity.getMainHandItem();
                    Item weapon = stack.getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        if (livingEntity instanceof Player player) {
                            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        }
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handR, 0.0F, 0.0F
                        );
                        if (jointVec == null) return;

                        Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                        if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                        trident.assignSpawnSequence(livingEntity);
                        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                        trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                        trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                        trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                        float speed = 2.5F;
                        float inaccuracy = 1.0F;

                        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                        trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                        serverLevel.addFreshEntity(trident);
                    }
                }
            };

    private static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT_LIGHTNING =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack stack =  livingEntity.getOffhandItem();
                    Item weapon = stack.getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        if (livingEntity instanceof Player player) {
                            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                        }
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handL, 0.0F, 0.0F
                        );
                        if (jointVec == null) return;

                        Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                        if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                        trident.assignSpawnSequence(livingEntity);
                        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                        trident.setMode(TridentMode.LIGHTNING);
                        trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                        trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                        trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                        float speed = 2.5F;
                        float inaccuracy = 1.0F;

                        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                        trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                        serverLevel.addFreshEntity(trident);
                    }
                }
            };

    private static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_LIGHTNING =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack stack = livingEntity.getMainHandItem();
                    Item weapon = stack.getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        if (livingEntity instanceof Player player) {
                            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        }
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handR, 0.0F, 0.0F
                        );
                        if (jointVec == null) return;

                        Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                        if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                        trident.assignSpawnSequence(livingEntity);
                        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                        trident.setMode(TridentMode.LIGHTNING);
                        trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                        trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                        trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                        float speed = 2.5F;
                        float inaccuracy = 1.0F;

                        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                        trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                        serverLevel.addFreshEntity(trident);
                    }
                }
            };

    private static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_EXPLODE =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    ItemStack stack = livingEntity.getMainHandItem();
                    Item weapon = stack.getItem();
                    if (weapon instanceof BlueDemonTridentItem) {
                        if (livingEntity instanceof Player player) {
                            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                        }
                        Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                                livingEntity, new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().handR, 0.0F, 0.0F
                        );
                        if (jointVec == null) return;

                        Vec3 direction = BlueDemonTridentItem.getTridentThrowDirection(livingEntity, jointVec);
                        if (direction == null || direction.lengthSqr() < 1.0E-7) return;
                        BlueDemonThrownTridentEntity trident = new BlueDemonThrownTridentEntity(serverLevel, livingEntity, stack.copy());
                        trident.assignSpawnSequence(livingEntity);
                        trident.trimOldGroundedTridentsAroundOwnerOnSpawn();
                        trident.setMode(TridentMode.EXPLOSION);
                        trident.setPos(jointVec.x, jointVec.y, jointVec.z);

                        trident.setYRot((float)(Mth.atan2(direction.x, direction.z) * (180F / Math.PI)));
                        trident.setXRot((float)(Mth.atan2(direction.y, Math.sqrt(direction.x * direction.x + direction.z * direction.z)) * (180F / Math.PI)));

                        float speed = 2.5F;
                        float inaccuracy = 1.0F;

                        trident.pickup = AbstractArrow.Pickup.DISALLOWED;
                        trident.shoot(direction.x, direction.y, direction.z, speed, inaccuracy);
                        serverLevel.addFreshEntity(trident);
                    }
                }
            };
}
