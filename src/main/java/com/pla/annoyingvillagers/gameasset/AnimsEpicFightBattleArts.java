/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: CC-BY-SA-4.0
 *
 * Upstream: EpicFight - Battle Arts - Forixaim
 * Source: https://github.com/Forixaim/Epic-Fight-Battle-Arts
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/CC-BY-SA.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import com.pla.annoyingvillagers.entity.TridentLightningBolt;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsEpicFightBattleArts {
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> ADVANCED_LANCER_AUTO1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ADVANCED_LANCER_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ADVANCED_DUELIST_WHIRLEDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> ADVANCED_DUELIST_SHOOTING_STAR;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_THROW_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TRIDENT_THROW_5;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SQUIRE_SWORD_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SQUIRE_SWORD_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SQUIRE_SWORD_RUN;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SQUIRE_SWORD_HOP_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SQUIRE_SWORD_HEAVY_BLOW;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SABRE_AUTO3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SABRE_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SABRE_AIR_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SABRE_QUAD_STING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TACHI_BLOSSOM_SLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_AUTO3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> AXE_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> AXE_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> AXE_INNATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BAXE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BAXE_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BAXE_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BAXE_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BAXE_AUTO_2;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> BAXE_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> BAXE_AIR_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> BAXE_SEISMIC_IMPACT;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> GREATSWORD_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATSWORD_AIRSLAM;
    public static AnimationManager.AnimationAccessor<AttackAnimation> GREATSWORD_POWER_GEYSER;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> THIEF_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> THIEF_AUTO3;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> THIEF_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> THIEF_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THIEF_STEAL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_BLADES_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_BLADES_AIRSLAM;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DUAL_BLADES_WHIRLEDGE;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SWORD_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> IRON_LOTUS_DASH_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> TRIDENT_THROW_3;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        ADVANCED_LANCER_AUTO1 = builder.nextAccessor("biped/battle_style/advanced_lancer_auto1", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.2f, 0.3f, 0.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        ADVANCED_LANCER_AUTO3 = builder.nextAccessor("biped/battle_style/advanced_lancer_auto3", access ->
                new AttackAnimation(0.2f, 0.0f, 0.75f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.2f, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.35f, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
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
        TRIDENT_THROW_1 = builder.nextAccessor("biped/battle_style/trident_throw_1", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.2f, 0.3f, 0.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0f, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.05f, AVAnimations.ReuseableEvents.THROW_TRIDENT_HAND_RIGHT, AnimationEvent.Side.SERVER)));
        TRIDENT_THROW_5 = builder.nextAccessor("biped/battle_style/trident_throw_5", access ->
                new AttackAnimation(0.2f, 0.0f, 0.75f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.2f, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT).params(SoundEvents.TRIDENT_RETURN),
                                AnimationEvent.InTimeEvent.create(0.35f, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4f, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4f, AVAnimations.ReuseableEvents.THROW_TRIDENT_HAND_RIGHT_EXPLODE, AnimationEvent.Side.SERVER)));
        ADVANCED_DUELIST_WHIRLEDGE = builder.nextAccessor("biped/battle_style/advanced_duelist_whirledge", access ->
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
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.7F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.7F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)));
        ADVANCED_DUELIST_SHOOTING_STAR = builder.nextAccessor("biped/battle_style/advanced_duelist_shooting_star", access ->
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
                                AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.3F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.5f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT)
                                        .params(SoundEvents.TRIDENT_HIT_GROUND),
                                AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                        .params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().rootJoint, 1.2, 1F)));

        SQUIRE_SWORD_IDLE = builder.nextAccessor("biped/battle_style/squire_sword_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        SQUIRE_SWORD_WALK = builder.nextAccessor("biped/battle_style/squire_sword_walk",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        SQUIRE_SWORD_RUN = builder.nextAccessor("biped/battle_style/squire_sword_run",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        SQUIRE_SWORD_AUTO_1 = builder.nextAccessor("biped/battle_style/squire_sword_auto1",
                accessor -> new AttackAnimation(0.1f, 0f, 0.2f, 0.35f, 0.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_AUTO_2 = builder.nextAccessor("biped/battle_style/squire_sword_auto2",
                accessor -> new AttackAnimation(0.2f, 0f, 0.2f, 0.35f, 0.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_AUTO_3 = builder.nextAccessor("biped/battle_style/squire_sword_auto3",
                accessor -> new AttackAnimation(0.2f, 0f, 0.2f, 0.35f, 2.0f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_DASH_ATTACK = builder.nextAccessor("biped/battle_style/squire_sword_dash_attack",
                accessor -> new AttackAnimation(0.2f, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.2f, 0.3f, 0.5f, 1.0f, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2f))));
        SQUIRE_SWORD_HOP_ATTACK = builder.nextAccessor("biped/battle_style/squire_sword_hop_attack",
                accessor ->
                        new AirSlashAnimation(0.1f, 0f, 0.2f, 0.35f, 2f, false, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SQUIRE_SWORD_HEAVY_BLOW = builder.nextAccessor("biped/battle_style/squire_sword_heavy_blow",
                accessor -> new AttackAnimation(0.1f, 0f, 0.7f, 0.8f, 1.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false));
        SABRE_AUTO3 = builder.nextAccessor("biped/battle_style/sabre_auto3", access ->
                new AttackAnimation(0.05f, 0.0f, 0.1f, 0.2f, 1.9f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SABRE_DASH_ATTACK = builder.nextAccessor("biped/battle_style/sabre_dash_attack", access ->
                new DashAttackAnimation(0.2f, 0.0f, 0.3f, 0.45f, 1.9f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SABRE_AIR_ATTACK = builder.nextAccessor("biped/battle_style/sabre_aerial", access ->
                new AirSlashAnimation(0.2f, 0.0f, 0.5f, 0.6f, 1.9f, false, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SABRE_QUAD_STING = builder.nextAccessor("biped/battle_style/sabre_quadsting", access ->
                new AttackAnimation(0.2f, access, Armatures.BIPED, new AttackAnimation.Phase(
                        0.0f, 0.0f, 0.5f, 0.6f, 0.65f, 0.65f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(
                        0.65f, 0.0f, 0.65f, 0.75f, 0.8f, 0.8f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(
                        0.8f, 0.0f, 0.8f, 0.9f, 1.1f, 1.1f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD), new AttackAnimation.Phase(
                        1.1f, 0.0f, 1.1f, 1.2f, 2f, 2f, Armatures.BIPED.get().toolR, null
                ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9f)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        TACHI_BLOSSOM_SLASH = builder.nextAccessor("biped/battle_style/tachi_blossom_slash", access ->
                new AttackAnimation(0.1f, access, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.0f, 0.1f, 0.1f, 0.1f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.15f, 0.0f, 0.15f, 0.25f, 0.25f, 0.25f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.25f, 0.0f, 0.3f, 0.4f, 0.4f, 0.4f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.4f, 0.0f, 0.45f, 0.55f, 0.6f, 0.6f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD),
                        new AttackAnimation.Phase(0.6f, 0.0f, 0.6f, 0.8f, 2f, 2f, Armatures.BIPED.get().rootJoint, ColliderPreset.LONGSWORD)
                )
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(12f), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 4)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        AXE_AUTO1 = builder.nextAccessor("biped/battle_style/axe_auto1",
                access -> new BasicAttackAnimation(0.2f, 0.0f, 0.7f, 0.8f, 1f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED));
        AXE_AUTO2 = builder.nextAccessor("biped/battle_style/axe_auto2",
                access -> new BasicAttackAnimation(0.2f, 0.0f, 0.5f, 0.6f, 1f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED));
        AXE_AUTO3 = builder.nextAccessor("biped/battle_style/axe_auto3",
                access -> new BasicAttackAnimation(0.2f, 0.0f, 0.35f, 0.55f, 1.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f));
        AXE_DASH = builder.nextAccessor("biped/battle_style/axe_dash",
                access -> new DashAttackAnimation(0.2f, 0.0f, 0.35f, 0.45f, 1.5f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED));
        AXE_AIRSLASH = builder.nextAccessor("biped/battle_style/axe_airslash",
                access -> new AirSlashAnimation(0.2f, 0.0f, 0.5f, 0.65f, 1.5f, false, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false));
        AXE_INNATE = builder.nextAccessor("biped/battle_style/axe_innate",
                access -> new AttackAnimation(0.2f, 0.0f, 0.9f, 1.5f, 3f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.25f));
        BAXE_IDLE = builder.nextAccessor("biped/battle_style/baxe_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        BAXE_WALK = builder.nextAccessor("biped/battle_style/baxe_walk",
                accessor -> new MovementAnimation(0.1f, true, accessor, Armatures.BIPED));
        BAXE_RUN = builder.nextAccessor("biped/battle_style/baxe_run",
                accessor -> new MovementAnimation(0.2f, true, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F));
        BAXE_AUTO_1 = builder.nextAccessor("biped/battle_style/baxe_auto1",
                accessor -> new BasicAttackAnimation(0.2f, 0.0f, 0.4f, 0.6f, 1.0f, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F));
        BAXE_AUTO_2 = builder.nextAccessor("biped/battle_style/baxe_auto2",
                accessor -> new BasicAttackAnimation(0.5f, 0.0f, 0.55f, 0.65f, 2.0f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2f))
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2));
        BAXE_DASH_ATTACK = builder.nextAccessor("biped/battle_style/baxe_dash_attack",
                accessor -> new AirSlashAnimation(0.2f, 0.0f, 1.15f, 1.25f, 3.0f, false, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.7f))
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5f)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().toolR, 2.0, 2F)));
        BAXE_AIR_ATTACK = builder.nextAccessor("biped/battle_style/baxe_airslash",
                accessor -> new DashAttackAnimation(0.5f, 0.0f, 0.4f, 0.6f, 2.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS,2)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false));
        BAXE_SEISMIC_IMPACT = builder.nextAccessor("biped/battle_style/baxe_seismic_impact",
                access -> new AirSlashAnimation(0.5f, access, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0f, 0.0f, 1.4f, 1.6f, 1.6f, 1.6f, Armatures.BIPED.get().toolR, null
                        ).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2f)),
                        new AttackAnimation.Phase(1.6f, 0.0f, 1.6f, 1.7f, 3.0f, 4.0f, Armatures.BIPED.get().rootJoint, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f))
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.GENERIC_EXPLODE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.9F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.6f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE,
                                                AnimationEvent.Side.CLIENT)
                                        .params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().toolR, 3.0, 2F
                                        ))
        );
        GREATSWORD_DASH_ATTACK = builder.nextAccessor("biped/battle_style/greatsword_dash_attack", access ->
                new DashAttackAnimation(0.2f, 0.0f, 0.5f, 0.65f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
        );
        GREATSWORD_POWER_GEYSER = builder.nextAccessor("biped/battle_style/greatsword_power_geyser", access ->
                new AttackAnimation(0.2f, 0.0f, 0.8f, 0.9f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1f))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.9f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                        .params(new Vec3f(0.0F, -0.3F, -5.0F), Armatures.BIPED.get().toolR, 1.1, 1.55F))
        );
        GREATSWORD_AIRSLAM = builder.nextAccessor("biped/battle_style/greatsword_airslam", access ->
                new BasicAttackAnimation(0.2f, 0.0f, 0.5f, 0.65f, 2f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3f))
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0f, 0.5f))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.65f, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                                        .params(new Vec3f(0.0F, -0.3F, -5.0F), Armatures.BIPED.get().toolR, 1.1, 1.55F)
                        )
        );
        THIEF_AUTO1 = builder.nextAccessor("biped/battle_style/thief_auto1",
                accessor -> new BasicAttackAnimation(0.2f, 0f, 0.2f, 0.35f, 0.5f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        THIEF_AUTO3 = builder.nextAccessor("biped/battle_style/thief_auto3",
                accessor -> new BasicAttackAnimation(0.2f, 0f, 0.55f, 0.65f, 1.7f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        THIEF_DASH_ATTACK = builder.nextAccessor("biped/battle_style/thief_dash_attack",
                accessor -> new DashAttackAnimation(0.2f, 0f, 0.3f, 0.4f, 1.7f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        THIEF_AIRSLASH = builder.nextAccessor("biped/battle_style/thief_airslash",
                accessor -> new AirSlashAnimation(0.2f, 0f, 0.2f, 0.3f, 1.7f, false, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        THIEF_STEAL = builder.nextAccessor("biped/battle_style/thief_steal",
                accessor -> new AttackAnimation(0.2f, 0f, 0.65f, 0.75f, 1.7f, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_BLADES_AIRSLAM = builder.nextAccessor("biped/battle_style/dual_blades_airslam", access ->
                new BasicAttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.4f, 0.5f, 0.6f, 0.6f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f)))
                        .addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.SimpleEvent.create((livingEntityPatch, assetAccessor, animationParameters) ->
                        {
                            if (assetAccessor.get() instanceof AttackAnimation animation && livingEntityPatch.getOriginal().getOffhandItem().getItem() instanceof SwordItem swordItem)
                            {
                                animation.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(swordItem.getDamage()));
                            }
                        }, AnimationEvent.Side.SERVER))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_BLADES_AUTO3 = builder.nextAccessor("biped/battle_style/dual_blades_auto3", access ->
                new BasicAttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.0f, 0.55f, 0.65f, 0.6f, 0.65f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.65f, 0.0f, 0.65f, 0.75f, 0.75f, 0.75f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.75f, 0.0f, 0.9f, 1f, 1f, 1f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(1f, 0.0f, 1f, 1.1f, 3f, 3.0f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f))
                )
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        DUAL_BLADES_WHIRLEDGE = builder.nextAccessor("biped/battle_style/dual_blades_whirledge", access ->
                new AttackAnimation(0.2f, access, humanoidArmature,
                        new AttackAnimation.Phase(0.0f, 0.3f, 0.3f, 0.4f, 0.4f, 0.4f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1f)),
                        new AttackAnimation.Phase(0.4f, 0.0f, 0.4f, 0.5f, 0.5f, 0.5f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.15f)),
                        new AttackAnimation.Phase(0.5f, 0.0f, 0.5f, 0.6f, 0.6f, 0.6f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2f)),
                        new AttackAnimation.Phase(0.6f, 0.0f, 0.6f, 0.7f, 0.7f, 0.7f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25f)),
                        new AttackAnimation.Phase(0.7f, 0.0f, 0.7f, 0.8f, 0.8f, 0.8f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3f)),
                        new AttackAnimation.Phase(0.8f, 0.0f, 0.8f, 0.9f, 0.9f, 0.9f, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.35f)),
                        new AttackAnimation.Phase(0.9f, 0.0f, 1.25f, 1.35f, 2f, 2f, InteractionHand.MAIN_HAND, Armatures.BIPED.get().rootJoint, ColliderPreset.BATTOJUTSU_DASH)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5f))
                )
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SWORD_DASH_ATTACK = builder.nextAccessor("biped/battle_style/sword_dash_attack", access ->
                new DashAttackAnimation(0.2f, 0.0f, 0.3f, 0.45f, 1.9f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        IRON_LOTUS_AUTO1 = builder.nextAccessor("biped/battle_style/iron_lotus_auto1", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 0.25f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));

        IRON_LOTUS_AUTO2 = builder.nextAccessor("biped/battle_style/iron_lotus_auto2", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 0.25f, null, Armatures.BIPED.get().toolL,  access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));

        IRON_LOTUS_AUTO3 = builder.nextAccessor("biped/battle_style/iron_lotus_auto3", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.1f, 0.2f, 1.0f, null, Armatures.BIPED.get().legR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));

        IRON_LOTUS_DASH_ATTACK = builder.nextAccessor("biped/battle_style/iron_lotus_dash_attack", access ->
                new BasicAttackAnimation(0.1f, 0.0f, 0.2f, 0.4f, 1.0f, ColliderPreset.BATTOJUTSU_DASH, Armatures.BIPED.get().rootJoint, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.0F));
        TRIDENT_THROW_3 = builder.nextAccessor("biped/battle_style/trident_throw_3", accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.5F, 0.3F, 0.3F, InteractionHand.OFF_HAND, humanoidArmature.get().handR, WOMWeaponColliders.PUNCH),
                new AttackAnimation.Phase(0.3F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.PUNCH))
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.TRIDENT_RETURN)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent.create(0.3F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, AnimationEvent.Side.SERVER),
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
                        AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_WEAPON_RIGHT, AnimationEvent.Side.SERVER)
                ));
    }
}
