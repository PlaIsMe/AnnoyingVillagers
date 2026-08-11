/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: AFL-3.0
 *
 * Upstream: Annoying Villagers - Pugilist Steve
 * Source: https://space.bilibili.com/1337039598/dynamic
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/AFL-3.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.HeavyAttackAnimation;
import com.pla.annoyingvillagers.animations.RushSwordAnimation;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.ScreenShakeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.MultiCollider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsPugilistSteve {
    public static AnimationManager.AnimationAccessor<StaticAnimation> LAYING_DEATH;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> LAYING_DEATH_DEAD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_STATE_TRANSFORM;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_STATE_TRANSFORM_END;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> TRIDENT_FESTIVAL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> COUNTER;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FIST_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK;
    public static AnimationManager.AnimationAccessor<HeavyAttackAnimation> LEGENDARY_SWORD_HEAVY_ATTACK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HACKER_SWORD_SKILL_TWOHAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_UP;
    public static AnimationManager.AnimationAccessor<RushSwordAnimation> RUSH_SWORD;
    public static AnimationManager.AnimationAccessor<RushSwordAnimation> RUSH_SWORD_OFFHAND;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DUAL_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_BACKWARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> SPEAR_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<GuardAnimation> LEGENDARY_SWORD_GUARD_PARRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> POSE_UP;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DAGGER_DUAL_AUTO4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CHECK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> BIPED_RUN_ESWORD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNIFE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> KNIFE_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> KNIFE_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNIFE_CHECK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HOOK_GUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CARRY;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FIST_LEFT;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_FORWARD;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_RIGHT;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_LEFT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> AXE_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SWORD_HEAVY_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HARD_KICK;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HARD_KICK_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> RUN_START;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LONGSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_DUAL_BIG;
    public static AnimationManager.AnimationAccessor<MovementAnimation> RUN_HOLD;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> LONGEST_HIT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HARD_GREATSWORD_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> HARD_GREATSWORD_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HARD_GREATSWORD_GUARD_SKILL;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_LEFT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_RIGHT;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SHAKE_HAND_TRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SHAKE_HAND;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FIST_TRY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> FISTING;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GIANT_WHIRLWIND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SPEAR_THRUST;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_TACHI_GUARD;
    public static AnimationManager.AnimationAccessor<GuardAnimation> DUAL_TACHI_GUARD_HIT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WHIRLWIND_KICK_LEFT;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SUPER_PUNCH;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> GUARD_BREAK_ATTACK;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SWORD_DASH;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> TACHI_DASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_END;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> TRIED;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATSWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_WAKE_UP_ATTACK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DUAL_E_END;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AXE_FUN_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_FIST_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_THROW_2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> TRIDENT_THROW_LEGENDARY;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> STRANGLE_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> STRANGLE_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> WRESTLING_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> WRESTLING_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> WRESTLING_BACK_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> WRESTLING_BACK_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> STAB_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> DUAL_STAB_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> STAB_EXECUTE_HIT;
    public static AnimationManager.AnimationAccessor<ExecutionAttackAnimation> SHIELD_EXECUTE;
    public static AnimationManager.AnimationAccessor<ExecutionHitAnimation> SHIELD_EXECUTE_HIT;

    private static final ExtraDamageInstance.ExtraDamage TARGET_MAX_HEALTH = new ExtraDamageInstance.ExtraDamage((attacker, itemstack, target, baseDamage, params) -> params[0] + target.getMaxHealth() * params[1], (itemstack, tooltips, baseDamage, params) -> {
    });

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        MultiCollider<OBBCollider> executionCollider = new MultiOBBCollider(3, 1.25F, 1.5F, 1.5F, 0.0F, 1.5F, -1.5F);
        MultiCollider<OBBCollider> executionColliderBack = new MultiOBBCollider(3, 1.25F, 1.5F, 1.5F, 0.0F, 1.5F, 1.5F);

        LAYING_DEATH = builder.nextAccessor("biped/pugilist_steve/death_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true));
        LAYING_DEATH_DEAD = builder.nextAccessor("biped/pugilist_steve/death_emote_dead", (accessor) -> new LongHitAnimation(0.16F, accessor, Armatures.BIPED));
        BLUE_DEMON_STATE_TRANSFORM = builder.nextAccessor("biped/pugilist_steve/blue_demon_state_transform",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        BLUE_DEMON_STATE_TRANSFORM_END = builder.nextAccessor("biped/pugilist_steve/blue_demon_state_transform_end",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        BLUE_DEMON_DIE = builder.nextAccessor("biped/pugilist_steve/blue_demon_die",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        TRIDENT_FESTIVAL = builder.nextAccessor("biped/pugilist_steve/trident_festival",
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
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
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
                                AnimationEvent.InTimeEvent.create(0.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal(), true);
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
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
                                AnimationEvent.InTimeEvent.create(3.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));
        COUNTER = builder.nextAccessor("biped/pugilist_steve/counter",
                accessor -> new BasicMultipleAttackAnimation(0.3F, 0.08F, 0.1F, 0.15F, 0.525F, ColliderPreset.FIST, humanoidArmature.get().legR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.COUNTER))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F)));
        FIST_GUARD = builder.nextAccessor("biped/pugilist_steve/fist_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        FIST_DASH = builder.nextAccessor("biped/pugilist_steve/fist_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));
        WHIRLWIND_KICK = builder.nextAccessor("biped/pugilist_steve/whirlwind_kick",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.29F, 0.45F, 0.85F, 1.8F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legR, accessor, humanoidArmature).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER)
                                        .params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.5F));
        LEGENDARY_SWORD_HEAVY_ATTACK = builder.nextAccessor("biped/pugilist_steve/legendary_sword_heavy_attack",
                accessor -> new HeavyAttackAnimation(0.05F, 0.05F, 0.5F, 0.7F, 1.2F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.5F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (dynamicAnimation, livingEntityPatch, f, f1, pose) -> {
                            if (f1 >= 0.3F && f1 < 0.35F) {
                                float x = (float)livingEntityPatch.getOriginal().getX();
                                float y = (float)livingEntityPatch.getOriginal().getY();
                                float z = (float)livingEntityPatch.getOriginal().getZ();

                                for (BlockState blockState = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int)x, (int)y, (int)z))); (blockState.getBlock() instanceof BushBlock || blockState.isAir()) && !blockState.is(Blocks.VOID_AIR); blockState = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos(new Vec3i((int)x, (int)y, (int)z)))) --y;

                                float distance = (float)Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - y) - 1.0D, 0.0D);
                                return 1.0F - (1.0F / (-distance - 1.0F) + 1.0F);
                            }

                            return 1.0F;
                        })
                        .addEvents(
                                EpicfightUtil.cameraZoomInEvent(0.05F, -0.35F, 30),
                                AnimationEvent.InTimeEvent.create(0.0F, ((livingEntityPatch, assetAccessor, animationParameters) -> {
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    if (!(entity.level() instanceof ServerLevel serverLevel)) return;

                                    serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), AnnoyingVillagersModSounds.HEAVY_ATTACK_START.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                    serverLevel.playSound(null, entity.getX(), entity.getY(), entity.getZ(), AnnoyingVillagersModSounds.HEAVY_ATTACK_LEGENDARY_SWORD_2.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getY(), entity.getZ(), 15, 0.0D, 0.0D, 0.0D, 0.2D);
                                    serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entity.getX(), entity.getEyeY(), entity.getZ(), 100, 0.0D, 0.0D, 0.0D, 0.5D);
                                }), AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.5F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.get().toolR, 2.5D, 0.6F),
                                AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.SHOCK_WAVE, AnimationEvent.Side.SERVER),
                                EpicfightUtil.cameraZoomOutBlurEvent(0.5F, 10.0F, 20)
                        ));
        HACKER_SWORD_SKILL_TWOHAND = builder.nextAccessor("biped/pugilist_steve/hacker_sword_skill",
                accessor -> new AttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.016F, 0.066F, 0.133F, 0.133F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new AttackAnimation.Phase(0.133F, 0.133F, 0.183F, 0.25F, 0.25F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new AttackAnimation.Phase(0.25F, 0.25F, 0.3F, 0.366F, 0.366F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new AttackAnimation.Phase(0.366F, 0.366F, 0.416F, 0.483F, 0.483F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new AttackAnimation.Phase(0.483F, 0.483F, 0.533F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new AttackAnimation.Phase(0.6F, 0.6F, 0.65F, 0.716F, 0.716F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new AttackAnimation.Phase(0.716F, 0.716F, 0.766F, 0.833F, 0.833F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, ColliderPreset.SWORD), new AttackAnimation.Phase(0.833F, 0.833F, 0.883F, 1.1F, 1.1F, humanoidArmature.get().toolR, ColliderPreset.SWORD), new AttackAnimation.Phase(0.933F, 1.133F, 1.183F, 1.6F, 1.6F, humanoidArmature.get().toolL, ColliderPreset.SWORD)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        DUAL_SWORD_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto1",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_SWORD_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        TRIDENT_DUAL_AUTO2 = builder.nextAccessor("biped/pugilist_steve/trident_dual_auto2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        DUAL_SWORD_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto3",
                accessor -> new BasicAttackAnimation(0.16F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto4",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/pugilist_steve/dual_sword_auto5",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        DUAL_SWORD1 = builder.nextAccessor("biped/pugilist_steve/dual_auto1",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolL, null), new AttackAnimation.Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        DUAL_SWORD2 = builder.nextAccessor("biped/pugilist_steve/dual_auto2",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.05F, 0.4F, 0.8F, 1.167F, 2.5F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.1F, 1.2F, 1.3F, 1.5F, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.1F, 1.4F, 1.5F, 2.1F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F));
        DUAL_SWORD3 = builder.nextAccessor("biped/pugilist_steve/dual_auto3",
                accessor -> new BasicAttackAnimation(0.1F, 0.0F, 0.0F, 0.06F, 0.3F, ColliderPreset.SWORD, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        FIST_UP = builder.nextAccessor("biped/pugilist_steve/fist_up",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 0.95F, WOMWeaponColliders.KICK, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, AVSounds.KICK.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));
        RUSH_SWORD = builder.nextAccessor("biped/pugilist_steve/rush_sword",
                accessor -> new RushSwordAnimation(
                        0.15F, 0.0F, 0.1F, 0.26F, 0.75F,
                        ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        RUSH_SWORD_OFFHAND = builder.nextAccessor("biped/pugilist_steve/rush_sword_offhand",
                accessor -> new RushSwordAnimation(
                        0.15F, 0.0F, 0.1F, 0.26F, 0.75F,
                        ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        DUAL_DANCING_EDGE = builder.nextAccessor("biped/pugilist_steve/dancing_edge",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null), new AttackAnimation.Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        SWEEPING_EDGE = builder.nextAccessor("biped/pugilist_steve/sweeping_edge",
                accessor -> new AttackAnimation(0.2F, 0.1F, 0.35F, 0.46F, 0.79F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        HIT_BACKWARD = builder.nextAccessor("biped/pugilist_steve/hit_backward",
                accessor -> new LongHitAnimation(0.08F, accessor, humanoidArmature));
        SPEAR_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/spear_guard_hit",
                accessor -> new GuardAnimation(0.05F, 0.2F, accessor, humanoidArmature)
                        .addEvents(new AnimationEvent.InTimeEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.FAST_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.FAST_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, AVAnimations.ReuseableEvents.FAST_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.FAST_SPINNING, AnimationEvent.Side.CLIENT)}));
        LEGENDARY_SWORD_GUARD = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        LEGENDARY_SWORD_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        LEGENDARY_SWORD_GUARD_PARRY = builder.nextAccessor("biped/pugilist_steve/legendary_sword_guard_parry",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        POSE_UP = builder.nextAccessor("biped/pugilist_steve/pose_up",
                accessor -> new ActionAnimation(0.0F, 1.85F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true));
        DAGGER_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dagger_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dagger_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.1F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dagger_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.15F, 0.26F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO1 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.16F, 0.25F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO2 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.16F, InteractionHand.OFF_HAND, null, humanoidArmature.get().toolL, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO3 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.11F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));
        DAGGER_DUAL_AUTO4 = builder.nextAccessor("biped/pugilist_steve/dagger_dual_auto4",
                accessor -> new BasicAttackAnimation(0.13F, 0.1F, 0.21F, 0.4F, ColliderPreset.DUAL_DAGGER_DASH, humanoidArmature.get().toolR, accessor, humanoidArmature));
        CHECK = builder.nextAccessor("biped/pugilist_steve/check",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        BIPED_RUN_ESWORD = builder.nextAccessor("biped/pugilist_steve/run_esword",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        KNIFE_IDLE = builder.nextAccessor("biped/pugilist_steve/knife_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        KNIFE_RUN = builder.nextAccessor("biped/pugilist_steve/knife_run",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        KNIFE_ATTACK = builder.nextAccessor("biped/pugilist_steve/knife_attack",
                accessor -> new BasicAttackAnimation(0.15F, 0.01F, 0.2F, 0.5F, 0.6F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addState(EntityState.MOVEMENT_LOCKED, false)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        KNIFE_CHECK = builder.nextAccessor("biped/pugilist_steve/knife_check",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        HOOK_GUN = builder.nextAccessor("biped/pugilist_steve/hook_gun",
                accessor -> new ActionAnimation(0.0F, 1.85F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        CARRY = builder.nextAccessor("biped/pugilist_steve/carry",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        FIST_LEFT = builder.nextAccessor("biped/pugilist_steve/fist_left",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.85F, 1.1F, ColliderPreset.FIST, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.2F));
        KNOCKDOWN_FORWARD = builder.nextAccessor("biped/pugilist_steve/knockdown_forward",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        KNOCKDOWN_RIGHT = builder.nextAccessor("biped/pugilist_steve/knockdown_right",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        KNOCKDOWN_LEFT = builder.nextAccessor("biped/pugilist_steve/knockdown_left",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        AXE_HEAVY_AUTO_1 = builder.nextAccessor("biped/pugilist_steve/axe_heavy_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.3F, 0.6F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F));
        AXE_HEAVY_AUTO_2 = builder.nextAccessor("biped/pugilist_steve/axe_heavy_auto2",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.8F, 1.2F, 1.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F));
        SWORD_HEAVY_AUTO_1 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.11F, 0.27F, 0.5F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        SWORD_HEAVY_AUTO_2 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto2",
                accessor -> new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.12F, 0.22F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));
        SWORD_HEAVY_AUTO_3 = builder.nextAccessor("biped/pugilist_steve/sword_heavy_auto3",
                accessor -> new BasicMultipleAttackAnimation(0.01F, 0.1F, 0.21F, 0.32F, 1.2F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.5F));
        HARD_KICK = builder.nextAccessor("biped/pugilist_steve/hard_kick",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.29F, 1.1F, 1.2F, 3.1F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().legR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        HARD_KICK_HIT = builder.nextAccessor("biped/pugilist_steve/hard_kick_hit",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        RUN_START = builder.nextAccessor("biped/pugilist_steve/run_start",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        LONGSWORD_AUTO1 = builder.nextAccessor("biped/pugilist_steve/tachi_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        RUN_HOLD = builder.nextAccessor("biped/pugilist_steve/run_hold",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        RUN_DUAL_BIG = builder.nextAccessor("biped/pugilist_steve/run_dual_big",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        LONGEST_HIT = builder.nextAccessor("biped/pugilist_steve/longest_hit",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        HARD_GREATSWORD_GUARD = builder.nextAccessor("biped/pugilist_steve/hard_greatsword",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HARD_GREATSWORD_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/hard_greatsword_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        HARD_GREATSWORD_GUARD_SKILL = builder.nextAccessor("biped/pugilist_steve/hard_greatsword_skill",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        HIT_LEFT = builder.nextAccessor("biped/pugilist_steve/hit_left",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature));
        HIT_RIGHT = builder.nextAccessor("biped/pugilist_steve/hit_right",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature));
        SHAKE_HAND_TRY = builder.nextAccessor("biped/pugilist_steve/shake_hand_try",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SHAKE_HAND = builder.nextAccessor("biped/pugilist_steve/shake_hand",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        FIST_TRY = builder.nextAccessor("biped/pugilist_steve/fist_try",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        FISTING = builder.nextAccessor("biped/pugilist_steve/fisting",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.15F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        GIANT_WHIRLWIND = builder.nextAccessor("biped/pugilist_steve/giant_whirlwind",
                accessor -> new BasicAttackAnimation(0.41F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.35F, 0.55F, 0.9F, 0.9F, humanoidArmature.get().toolL, null), new AttackAnimation.Phase(0.9F, 0.95F, 1.05F, 1.2F, 1.5F, 1.5F, humanoidArmature.get().toolL, null), new AttackAnimation.Phase(1.5F, 1.65F, 1.75F, 1.95F, 2.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, ColliderPreset.GREATSWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        DUAL_SWORD_DANCING_EDGE = builder.nextAccessor("biped/pugilist_steve/dual_sword_dancing_edge",
                accessor -> new BasicAttackAnimation(0.25F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.31F, 0.4F, 0.4F, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.4F, 0.5F, 0.61F, 0.65F, 0.65F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null), new AttackAnimation.Phase(0.65F, 0.76F, 0.85F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.DUAL_SWORD))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 2)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        SPEAR_THRUST = builder.nextAccessor("biped/pugilist_steve/spear_thrust",
                accessor -> new AttackAnimation(0.11F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.3F, 0.36F, 0.5F, 0.5F, humanoidArmature.get().toolR, ColliderPreset.SPEAR),
                        new AttackAnimation.Phase(0.5F, 0.5F, 0.56F, 0.75F, 0.75F, humanoidArmature.get().toolR, ColliderPreset.SPEAR),
                        new AttackAnimation.Phase(0.75F, 0.75F, 0.81F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.SPEAR))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        DUAL_TACHI_GUARD = builder.nextAccessor("biped/pugilist_steve/dual_tachi_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        DUAL_TACHI_GUARD_HIT = builder.nextAccessor("biped/pugilist_steve/dual_tachi_guard_hit",
                accessor -> new GuardAnimation(0.05F, accessor, humanoidArmature));
        WHIRLWIND_KICK_LEFT = builder.nextAccessor("biped/pugilist_steve/whirlwind_kick_left",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.7F, 0.9F, Float.MAX_VALUE, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().legL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(10.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.23F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(EpicFightSounds.WHOOSH.get()))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.5F));
        SUPER_PUNCH = builder.nextAccessor("biped/pugilist_steve/super_punch",
                accessor -> new AttackAnimation(0.05F, 1.0F, 1.25F, 1.4F, Float.MAX_VALUE, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        GUARD_BREAK_ATTACK = builder.nextAccessor("biped/pugilist_steve/guard_break_attack",
                accessor -> new KnockdownAnimation(0.05F, accessor, humanoidArmature));
        SWORD_DASH = builder.nextAccessor("biped/pugilist_steve/sword_dash",
                accessor -> new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        TACHI_DASH = builder.nextAccessor("biped/pugilist_steve/tachi_dash",
                accessor -> new DashAttackAnimation(0.15F, 0.1F, 0.2F, 0.45F, 0.7F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG));
        DUAL_SWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/dual_sword_skill",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.7F, 1.7F, 1.8F, 1.8F, 1.8F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.8F, 1.8F, 1.9F, 1.9F, 1.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.9F, 2.0F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.5F, AVAnimations.ReuseableEvents.END_ATTACK, AnimationEvent.Side.BOTH))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        DUAL_END = builder.nextAccessor("biped/pugilist_steve/dual_back_end",
                accessor -> new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        TRIED = builder.nextAccessor("biped/pugilist_steve/tried",
                accessor -> new KnockdownAnimation(0.2F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        GREATSWORD_SKILL = builder.nextAccessor("biped/pugilist_steve/greatsword_skill",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.7F, 1.7F, 1.8F, 1.85F, 1.85F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.85F, 1.85F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F));
        LEGENDARY_SWORD_WAKE_UP_ATTACK = builder.nextAccessor("biped/pugilist_steve/legendary_sword_wake_up_attack",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.15F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.45F, 0.5F, 0.8F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false));
        DUAL_E_END = builder.nextAccessor("biped/pugilist_steve/dual_e_end",
                accessor -> new ActionAnimation(0.2F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        AXE_FUN_SKILL = builder.nextAccessor("biped/pugilist_steve/axe_fun_skill",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.1F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, ColliderPreset.SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.5F, 1.5F, 1.55F, 1.55F, 1.55F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F)), new AttackAnimation.Phase(1.55F, 1.6F, 1.7F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, false).addState(EntityState.LOCKON_ROTATE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        LEGENDARY_SWORD_AUTO_4 = builder.nextAccessor("biped/pugilist_steve/legendary_sword_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));
        OBSIDIAN_FIST_DASH = builder.nextAccessor("biped/pugilist_steve/obsidian_fist_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        SHADOW_OBSIDIAN_SWORD_ONEHAND_LONG = builder.nextAccessor("biped/pugilist_steve/shadow_obsidian_sword_onehand_long",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));
        SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/pugilist_steve/shadow_obsidian_sword_dual_sword_auto4",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        SHADOW_OBSIDIAN_SWORD_DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/pugilist_steve/shadow_obsidian_sword_dual_sword_auto5",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        TRIDENT_THROW_2 = builder.nextAccessor("biped/pugilist_steve/trident_throw_2",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, AVAnimations.ReuseableEvents.THROW_TRIDENT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));
        TRIDENT_THROW_LEGENDARY = builder.nextAccessor("biped/pugilist_steve/trident_throw_legendary",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.THROW_TRIDENT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));
        STRANGLE_EXECUTE = builder.nextAccessor("biped/pugilist_steve/strangle_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.01F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.1F, 0.29F, 1.0F, 1.2F, 1.2F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.2F, 0.0F, 3.36F, 1.9F, 1.9F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        STRANGLE_EXECUTE_HIT = builder.nextAccessor("biped/pugilist_steve/strangle_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.01F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 0.8333333F));

        WRESTLING_EXECUTE = builder.nextAccessor("biped/pugilist_steve/wrestling_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.0F, 0.05F, 1.85F, 2.0F, 2.0F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 2.0F, 0.0F, 3.36F, 2.5F, 2.5F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        WRESTLING_EXECUTE_HIT = builder.nextAccessor("biped/pugilist_steve/wrestling_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.01F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 0.8333333F));

        WRESTLING_BACK_EXECUTE = builder.nextAccessor("biped/pugilist_steve/wrestling_back_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.0F, 0.05F, 1.85F, 2.0F, 2.0F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 2.0F, 0.0F, 3.36F, 2.5F, 2.5F, Armatures.BIPED.get().rootJoint, executionColliderBack))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        WRESTLING_BACK_EXECUTE_HIT = builder.nextAccessor("biped/pugilist_steve/wrestling_back_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.01F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 0.8333333F));

        STAB_EXECUTE = builder.nextAccessor("biped/pugilist_steve/stab_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.05F, 0.05F, 1.85F, 2.0F, 0.4F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.4F,  0.4F, 0.6F, 0.6F, 1.1F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.0F, 1.2F, 1.4F, Float.MAX_VALUE, Float.MAX_VALUE, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));

        DUAL_STAB_EXECUTE = builder.nextAccessor("biped/pugilist_steve/dual_stab_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.05F, 0.05F, 1.85F, 2.0F, 0.4F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.OLD_FALL.get()),
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.4F,  0.4F, 0.6F, 0.6F, 1.1F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.0F, 1.2F, 1.4F, Float.MAX_VALUE, Float.MAX_VALUE, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        STAB_EXECUTE_HIT = builder.nextAccessor("biped/pugilist_steve/stab_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.1F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));

        SHIELD_EXECUTE = builder.nextAccessor("biped/pugilist_steve/shield_execute",
                (accessor) -> (new ExecutionAttackAnimation(0.05F, accessor, Armatures.BIPED,
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 0.1F, 0.65F, 0.8F, 1.2F, 1.2F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(false, 1.2F, 1.45F, 1.6F, 1.6F, 1.6F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.01F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F)),
                        (new ExecutionAttackAnimation.ExecutionPhase(true, 1.6F, 2.05F, 2.3F, 2.3F, 2.3F, Armatures.BIPED.get().rootJoint, executionCollider))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(TARGET_MAX_HEALTH.create(15.0F, 0.08F)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, assetAccessor, animationParameters) -> livingEntityPatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 9, false, false)),
                                        AnimationEvent.Side.BOTH)}
                        ));
        SHIELD_EXECUTE_HIT = builder.nextAccessor("biped/pugilist_steve/shield_execute_hit", (accessor) -> (new ExecutionHitAnimation(0.1F, accessor, Armatures.BIPED)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
    }

    public static final AnimationEvent.E0 LEGENDARY_SWORD_HEAVY_START = (livingEntityPatch, animation, params) -> {

    };
}
