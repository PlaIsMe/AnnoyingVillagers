/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: LicenseRef-WOM-Proprietary
 *
 * Upstream: Weapons of Miracles - reacer
 * Source: https://www.curseforge.com/minecraft/mc-mods/weapons-of-miracles-epicfight
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/LicenseRef-WOM-Proprietary.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonChestplateItem;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.item.EarthAxeItem;
import com.pla.annoyingvillagers.network.ClientboundGlaiveExplosionFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.AntitheusShootAttackAnimation;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMDamageType;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Random;
import java.util.Set;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsWom {
    public static AnimationManager.AnimationAccessor<ActionAnimation> CUT_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<MovementAnimation> TORMENT_BERSERK_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TRIDENT_GUARD_HIT_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> TRIDENT_GUARD_HIT_2;
    public static AnimationManager.AnimationAccessor<ActionAnimation> ELECTRIC_FIELD;
    public static AnimationManager.AnimationAccessor<AttackAnimation> EARTH_AXE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GLOWING_AGONY_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_BULL_CHARGE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_MOONLESS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_SOLAR_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_NAPOLEON_AUTO_3;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> YELLOW_NAPOLEON_AUSTERLITZ_SHOOT;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_AEGIS_NAPOLEON_RELOAD_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUSTERLITZ;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_TORMENT_CHARGED_ATTACK_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> DEMONIAC_RUINE_COMET;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AGONY_GUARD_HIT_1;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> ENDER_GLAIVE_NAPOLEON_SHOOT_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_AGONY_AUTO_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CLONE_ANTITHEUS_IDLE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_AGRESSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_GUILLOTINE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CLONE_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<AttackAnimation> CLONE_ANTITHEUS_LAPSE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_DEATHFALL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_BLINK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ANTITHEUS_ASCENDED_BLACKHOLE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_GLAIVE_NAPOLEON_WATERLOW;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> YELLOW_TORMENT_CHARGED_ATTACK_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> CLONE_ENDERBLASTER_ONEHAND_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_TORMENT_BERSERK_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_TORMENT_BERSERK_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SLEDGEHAMMER_SOLAR_AUTO_3;
    public static AnimationManager.AnimationAccessor<AntitheusShootAttackAnimation> CLONE_ANTITHEUS_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CLONE_ANTITHEUS_ASCENDED_IDLE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_SKELETON_ANTITHEUS_ASCENSION;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_ANTITHEUS_ASCENDED_AUTO_3;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> HEROBRINE_MOB_ENDERSTEP_OBSCURIS;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL;
    public static AnimationManager.AnimationAccessor<MovementAnimation> OLD_MOONLESS_RUN;
    public static AnimationManager.AnimationAccessor<MovementAnimation> TRIDENT_TWO_HAND_RUN;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_STRONG_PUNCH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> CLONE_NAPOLEON_WATERLOW_SHOOT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> CUT_ENDERBLASTER_TWOHAND_RELOAD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HACKER_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> WARBLADE_SATSUJIN_TSUKUYOMI;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HOOK_HERRSCHER_UP;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        CUT_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/cut_antitheus_ascension",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level()
                                            instanceof ServerLevel
                                            && livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof BlueDemonChestplateItem) {
                                        BlueDemonChestplateItem.activateBuff(livingEntity.getItemBySlot(EquipmentSlot.CHEST));
                                    }
                                }, AnimationEvent.Side.SERVER)));
        TORMENT_BERSERK_WALK = builder.nextAccessor("biped/wom_clone/torment_berserk_walk", accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        TRIDENT_GUARD_HIT_1 = builder.nextAccessor("biped/wom_clone/trident_guard_hit1",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT)));
        TRIDENT_GUARD_HIT_2 = builder.nextAccessor("biped/wom_clone/trident_guard_hit2",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT)));
        ELECTRIC_FIELD = builder.nextAccessor("biped/wom_clone/electric_field",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.spawnDamageZones(serverLevel, livingEntityPatch.getOriginal());
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
                                AnimationEvent.InTimeEvent.create(3.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));
        EARTH_AXE = builder.nextAccessor("biped/wom_clone/earth_axe",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        EarthAxeItem.summonEarthWall(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));
        GLOWING_AGONY_GUARD = builder.nextAccessor("biped/wom_clone/glowing_agony_guard",
                accessor -> new StaticAnimation(0.05F, true, accessor, humanoidArmature)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.0F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.6F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.7F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING_AGONY, AnimationEvent.Side.CLIENT)));
        ENDER_AEGIS_BULL_CHARGE = builder.nextAccessor("biped/wom_clone/ender_aegis_bull_charge",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.25F, 0.29F, 0.29F,
                        humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.29F, 0.3F, 0.35F, 0.39F, 0.39F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.39F, 0.4F, 0.45F, 0.49F, 0.49F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.79F, 0.8F, 0.85F, 0.89F, 0.89F,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP),
                        new AttackAnimation.Phase(0.89F, 1.0F, 1.1F, 1.3F, Float.MAX_VALUE,
                                humanoidArmature.get().rootJoint, WOMWeaponColliders.SHOULDER_BUMP))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 5)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 6)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(2.0F), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 7)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 7)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null));
        CLONE_ANTITHEUS_AGRESSION = builder.nextAccessor("biped/wom_clone/clone_antitheus_agression",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.35F, 0.59F, 0.59F, humanoidArmature.get().toolR, WOMWeaponColliders.ANTITHEUS_AGRESSION),
                        new AttackAnimation.Phase(0.59F, 0.6F, 0.65F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_AGRESSION_REAP)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_DOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.0F)), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.EVISCERATE.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_UP, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(new AnimationEvent[] {
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        CLONE_ANTITHEUS_GUILLOTINE = builder.nextAccessor("biped/wom_clone/clone_antitheus_guillotine",
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
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        CLONE_ANTITHEUS_IDLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_idle", (accessor) -> (new StaticAnimation(0.2F, true, accessor, humanoidArmature)));
        CLONE_ANTITHEUS_AUTO_1 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.35F, 0.55F, 0.69F, 0.69F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.69F, 0.7F, 0.9F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.55F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.75F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false));
        CLONE_ANTITHEUS_AUTO_2 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.15F, 0.45F, 0.45F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        CLONE_ANTITHEUS_AUTO_3 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.35F, 0.5F, 0.5F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.5F, 0.55F, 0.7F, 0.75F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 5)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F));
        CLONE_ANTITHEUS_AUTO_4 = builder.nextAccessor("biped/wom_clone/clone_antitheus_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.5F, 0.75F, 0.9F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.2F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        CLONE_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascension",
                accessor -> new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new AttackAnimation.Phase(0.65F, 1.75F, 2.05F, 2.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F));
        NULL_SKELETON_ANTITHEUS_ASCENSION = builder.nextAccessor("biped/wom_clone/null_skeleton_antitheus_ascension",
                accessor -> new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new AttackAnimation.Phase(0.65F, 1.75F, 2.05F, 2.8F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        NullSkeletonEntity nullSkeletonEntity = new NullSkeletonEntity(AnnoyingVillagersModEntities.NULL_SKELETON.get(), serverLevel);
                                        LivingEntity owner = livingEntityPatch.getOriginal();
                                        Vec3 forward = getVec3(owner);

                                        double dist = 2.0D;
                                        Vec3 spawnPos = owner.position().add(forward.scale(dist));

                                        nullSkeletonEntity.moveTo(
                                                spawnPos.x,
                                                spawnPos.y,
                                                spawnPos.z,
                                                owner.getYRot(),
                                                owner.getXRot()
                                        );
                                        if (owner instanceof Player player) {
                                            nullSkeletonEntity.setPlayer(player);
                                        } else if (owner instanceof NullEntity nullEntity) {
                                            nullSkeletonEntity.setNullEntity(nullEntity);
                                        }

                                        nullSkeletonEntity.finalizeSpawn(serverLevel,
                                                serverLevel.getCurrentDifficultyAt(nullSkeletonEntity.blockPosition()),
                                                MobSpawnType.MOB_SUMMONED,
                                                null, null
                                        );
                                        serverLevel.addFreshEntity(nullSkeletonEntity);
                                        if (owner instanceof NullEntity nullEntity) {
                                            nullEntity.claimWitherSkeletonSlot(nullSkeletonEntity);
                                        }
                                        LivingEntityPatch<?> nullSkeletonPatch = EpicFightCapabilities.getEntityPatch(nullSkeletonEntity, LivingEntityPatch.class);
                                        if (nullSkeletonPatch != null) {
                                            nullSkeletonPatch.playAnimationSynchronized(CLONE_ANTITHEUS_LAPSE, 0.0F);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        CLONE_ANTITHEUS_LAPSE = builder.nextAccessor("biped/wom_clone/clone_antitheus_lapse",
                accessor -> new AttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.65F, 0.75F, 0.8F, 0.8F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new AttackAnimation.Phase(0.8F, 1.3F, 1.4F, 1.45F, 1.45F, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION),
                        new AttackAnimation.Phase(1.45F, 1.75F, 1.85F, 2.3F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.PLUNDER_PERDITION))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_HURT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(4.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_HURT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(20.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F));
        CLONE_ANTITHEUS_ASCENDED_DEATHFALL = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_deathfall",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.5F, 0.55F, 0.75F, WOMWeaponColliders.ANTITHEUS_ASCENDED_DEATHFALL, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.75F))
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            for (int i = 0; i < 24; ++i) {
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) (new Random().nextFloat() - 0.5F), livingEntityPatch.getOriginal().getY() + 2.200000047683716D, livingEntityPatch.getOriginal().getZ() + (double) (new Random().nextFloat() - 0.5F), (new Random().nextFloat() - 0.5F) * 0.05F, -((double) new Random().nextFloat() * (livingEntityPatch.getOriginal().getY() - (double) f1) * 0.4000000059604645D), (new Random().nextFloat() - 0.5F) * 0.05F);
                            }

                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.7F, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.BLUNT_HIT_HARD.get(), SoundSource.NEUTRAL, 0.7F, 0.7F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            Vec3 vec3 = new Vec3(0.0D, (double) (f1 - 2.0F) - livingEntityPatch.getOriginal().getY(), 0.0D);

                            livingEntityPatch.getOriginal().move(MoverType.SELF, vec3);
                            byte b0 = 80;
                            double d0 = 0.6D;
                            double d1 = 0.01D;

                            for (int i = 0; i < b0; ++i) {
                                double d2 = 6.283185307179586D * new Random().nextDouble();
                                double d3 = (new Random().nextDouble() - 0.5D) * 3.141592653589793D * d1 / d0;
                                double d4 = d0 * Math.cos(d3) * Math.cos(d2);
                                double d5 = d0 * Math.cos(d3) * Math.sin(d2);
                                double d6 = d0 * Math.sin(d3);
                                float f3 = new Random().nextFloat() + 0.4F;
                                Vec3f vec3f = new Vec3f((float) d4 * f3, (float) d5 * f3, (float) d6 * f3);
                                OpenMatrix4f openmatrix4f = new OpenMatrix4f().rotate((float) Math.toRadians(90.0F), new Vec3f(1.0F, 0.0F, 0.0F));

                                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) vec3f.x, (float) (int) livingEntityPatch.getOriginal().getY() + vec3f.y + 0.02F, livingEntityPatch.getOriginal().getZ() + (double) vec3f.z, vec3f.x, vec3f.y, vec3f.z);
                            }

                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER)})
        );
        CLONE_ANTITHEUS_ASCENDED_BLINK = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_blink", accessor -> new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.4F, 0.4F, WOMWeaponColliders.ANTITHEUS_ASCENDED_BLINK, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_HIT_REVERSE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (entitypatch, self, params) -> {
                    entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                    entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                }, AnimationEvent.Side.CLIENT)));
        CLONE_ANTITHEUS_ASCENDED_BLACKHOLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_blackhole", accessor -> new BasicMultipleAttackAnimation(0.05F, 1.45F, 1.5F, 1.7F, WOMWeaponColliders.PLUNDER_PERDITION, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.7F))
                .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), WOMSounds.ANTITHEUS_BLACKKHOLE_CHARGEUP.get(), SoundSource.PLAYERS, 2.0F, 1.0F), AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().toolL);
                    transformMatrix.translate(new Vec3f(0.0F, 0.0F, 0.0F));
                    OpenMatrix4f.mul(new OpenMatrix4f().rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                    int n = 70;
                    double r = 5.0F;

                    for (int i = 0; i < n; ++i) {
                        double theta = Math.PI * 2D * new Random().nextDouble();
                        double phi = Math.acos((double) 2.0F * new Random().nextDouble() - (double) 1.0F);
                        double x = r * Math.sin(phi) * Math.cos(theta);
                        double y = r * Math.sin(phi) * Math.sin(theta);
                        double z = r * Math.cos(phi);
                        livingEntityPatch.getOriginal().level().addParticle(AnnoyingVillagersModParticleTypes.NULL.get(), (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX() + x, (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY() + y, (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ() + z, (float) (-x * (double) 0.15F), (float) (-y * (double) 0.15F), (float) (-z * (double) 0.15F));
                    }

                }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(1.05F, (livingEntityPatch, self, params) -> {
                    livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                    livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(1.45F, (livingEntityPatch, self, params) -> {
                    if (!(livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel)) return;

                    livingEntityPatch.getOriginal().level().playSound(
                            null,
                            livingEntityPatch.getOriginal(),
                            SoundEvents.WITHER_BREAK_BLOCK,
                            SoundSource.PLAYERS,
                            1.0F, 0.5F
                    );

                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature()
                            .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().handR);

                    OpenMatrix4f CORRECTION = new OpenMatrix4f()
                            .rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                    CORRECTION.translate(new Vec3f(0.0F, 0.0F, -3.5F));
                    OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);

                    serverLevel.sendParticles(WOMParticles.ANTITHEUS_BLACKHOLE_START.get(), (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY(), (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ(), 1, 0.0F, 0.0F, 0.0F, 0.0F);
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, (double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY(), (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ(), 48, 0.0F, 0.0F, 0.0F, 0.5F);
                }, AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(1.45F, (livingEntityPatch, self, params) -> {
                    OpenMatrix4f transformMatrix = livingEntityPatch.getArmature().getBoundTransformFor(livingEntityPatch.getAnimator().getPose(0.0F), Armatures.BIPED.get().handR);
                    OpenMatrix4f CORRECTION = new OpenMatrix4f().rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yRotO + 180.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                    CORRECTION.translate(new Vec3f(0.0F, 0.0F, -3.5F));
                    OpenMatrix4f.mul(CORRECTION, transformMatrix, transformMatrix);
                    Level level = livingEntityPatch.getOriginal().level();
                    Vec3 FractureCenter = new Vec3((double) transformMatrix.m30 + livingEntityPatch.getOriginal().getX(), (double) transformMatrix.m31 + livingEntityPatch.getOriginal().getY() - (double) 2.0F, (double) transformMatrix.m32 + livingEntityPatch.getOriginal().getZ());
                    LevelUtil.circleSlamFracture(livingEntityPatch.getOriginal(), level, FractureCenter, 4.0F, true, true);
                }, AnimationEvent.Side.CLIENT)));
        ENDER_AEGIS_MOONLESS_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_aegis_moonless_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.25F, 0.45F, 0.5F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        ENDER_AEGIS_MOONLESS_AUTO_2 = builder.nextAccessor("biped/wom_clone/ender_aegis_moonless_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.8F, 1.0F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, WOMWeaponColliders.MOONLESS_BYPASS))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_ANGLED_DOWN_LEFT_SLASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F));

        YELLOW_SOLAR_AUTO_2 = builder.nextAccessor("biped/wom_clone/yellow_solar_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.65F, 0.8F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SOLAR_HIT_UP)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        YELLOW_NAPOLEON_AUTO_3 = builder.nextAccessor("biped/wom_clone/yellow_napoleon_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.39F, 0.5F, 0.7F, 0.74F, 0.74F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.74F, 0.75F, 0.85F, 1.19F, 1.19F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(1.19F, 1.2F, 2.2F, 2.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(1.2F, 2.25F)).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true).newTimePair(0.0F, 0.85F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.4F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        YELLOW_NAPOLEON_AUSTERLITZ_SHOOT = builder.nextAccessor("biped/wom_clone/yellow_napoleon_austerlitz_shoot",
                accessor -> new SpecialAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.4F, 0.41F, 0.41F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.41F, 0.85F, 1.05F, 1.15F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.1F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.4F, (entityPatch, self, params) -> {
                                    Level level = entityPatch.getOriginal().level();
                                    LivingEntity entity = entityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(WOMAnimations.TORMENT_DASH, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        })
        );
        ENDER_AEGIS_NAPOLEON_RELOAD_1 = builder.nextAccessor("biped/wom_clone/ender_aegis_napoleon_reload_1",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.7F, 0.75F, 0.95F, 1.0F, 1.0F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(1.0F, 1.05F, 1.2F, 1.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 4)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));

        ENDER_GLAIVE_NAPOLEON_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.1F, 0.45F, 0.79F, 0.79F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.79F, 0.8F, 1.0F, 1.05F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_AUTO_2 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.6F, 0.64F, 0.64F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.64F, 0.65F, 0.95F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_AUTO_4 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.6F, 1.0F, 1.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 1.2F))
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_AUSTERLITZ = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_austerlitz",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.05F, 0.1F, 0.14F, 0.14F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.14F, 0.15F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.35F, 0.45F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.59F, 0.6F, 0.8F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        DEMONIAC_RUINE_AUTO_1 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, 0.2F, 0.55F, 0.55F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.75F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        DEMONIAC_RUINE_AUTO_2 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.59F, 0.6F, 0.85F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.95F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        DEMONIAC_RUINE_AUTO_4 = builder.nextAccessor("biped/wom_clone/demoniac_ruine_auto_4",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.6F, 0.65F, 0.65F, humanoidArmature.get().toolR, WOMWeaponColliders.RUINE_COMET),
                        new AttackAnimation.Phase(0.65F, 0.8F, 1.05F, 1.45F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(1.0F)), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        DEMONIAC_RUINE_COMET = builder.nextAccessor("biped/wom_clone/demoniac_ruine_comet",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.55F, 0.75F, WOMWeaponColliders.RUINE_COMET, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_TARGET_CURRENT_HEALTH.create(0.5F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 20)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for (BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double) dpy) - (double) 1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -0.25F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate((float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                AABB box = AABB.ofSize(livingEntityPatch.getOriginal().getPosition(1.0F), 3.0F, 3.0F, 3.0F);
                                List<Entity> list = livingEntityPatch.getOriginal().level().getEntities(livingEntityPatch.getOriginal(), box);
                                if (distanceToGround > 0.5F && list.isEmpty()) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.05F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(AnimationEvent.InTimeEvent.create(0.25F, reascer.wom.gameasset.ReuseableEvents.RUINE_COMET_AIRBURST, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.RUINE_COMET_GROUNDTHRUST, AnimationEvent.Side.CLIENT))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        DEMONIAC_TORMENT_CHARGED_ATTACK_2 = builder.nextAccessor("biped/wom_clone/demoniac_torment_charged_attack_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.65F))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));

        AGONY_GUARD_HIT_1 = builder.nextAccessor("biped/wom_clone/agony_guard_hit1",
                accessor -> new ActionAnimation(0.05F, 0.5F, accessor, humanoidArmature)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.2F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.3F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.FAST_SPINING, AnimationEvent.Side.CLIENT)
                                }));
        ENDER_GLAIVE_NAPOLEON_SHOOT_3 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_shoot_3",
                accessor -> new SpecialAttackAnimation(0.2F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.4F, 0.44F, 0.44F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.44F, 0.45F, 0.5F, 0.95F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                            livingEntityPatch.getOriginal(),
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    if (tipPos != null) {
                                        BlockPos mutePos = BlockPos.containing(tipPos);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                                        );
                                        livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                        if (explosionPos != null) {
                                            livingEntityPatch.getOriginal().level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER))
        );
        ENDER_GLAIVE_AGONY_AUTO_1 = builder.nextAccessor("biped/wom_clone/ender_glaive_agony_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.3F, 0.55F, 0.65F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.29F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, (self, livingEntityPatch, transformSheet) -> {
                            LivingEntity attackTarget = livingEntityPatch.getTarget();
                            if (!(Boolean) self.getRealAnimation().get().getProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                                TransformSheet transform = self.getTransfroms().get("Root").copyAll();
                                Keyframe[] keyframes = transform.getKeyframes();
                                int startFrame = 0;
                                int endFrame = transform.getKeyframes().length - 1;
                                Vec3f keyLast = keyframes[endFrame].transform().translation();
                                Vec3 pos = livingEntityPatch.getOriginal().getEyePosition();
                                Vec3 targetPos = attackTarget.position().add(attackTarget.getDeltaMovement().scale(8.0F));
                                float horizontalDistance = Math.max((float) targetPos.subtract(pos).horizontalDistance() - (attackTarget.getBbWidth() + livingEntityPatch.getOriginal().getBbWidth()), 0.0F);
                                Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                                float scale = Math.min(worldPosition.length() / keyLast.length(), 2.0F);

                                for (int i = startFrame; i <= endFrame; ++i) {
                                    Vec3f translation = keyframes[i].transform().translation();
                                    translation.z *= scale;
                                }

                                transformSheet.readFrom(transform);
                            } else {
                                transformSheet.readFrom(self.getTransfroms().get("Root"));
                            }

                        }).addEvents(
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, p) -> {
                                    Vec3 tipPos = EpicfightUtil.getJointWithTranslation(
                                            livingEntityPatch.getOriginal(),
                                            new Vec3f(0.0F, 0.0F, 0.0F),
                                            Armatures.BIPED.get().toolR,
                                            4.3F,
                                            2.3F
                                    );
                                    if (tipPos != null) {
                                        BlockPos mutePos = BlockPos.containing(tipPos);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundMuteExplosionAtPos(mutePos, 4)
                                        );
                                        livingEntityPatch.getOriginal().level().explode(livingEntityPatch.getOriginal(), tipPos.x, tipPos.y, tipPos.z,
                                                2.0F, true, Level.ExplosionInteraction.TNT);
                                        Vec3 glaivePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 1.3F, 2.3F);
                                        Vec3 explosionPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                                Armatures.BIPED.get().toolR, 10.3F, 2.3F);
                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundGlaiveExplosionFx(glaivePos, explosionPos)
                                        );
                                        if (explosionPos != null) {
                                            livingEntityPatch.getOriginal().level().playSound(null, new BlockPos((int) explosionPos.x, (int) explosionPos.y, (int) explosionPos.z), AnnoyingVillagersModSounds.ENDER_SHOT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER))
        );
        ENDER_GLAIVE_NAPOLEON_AUTO_3 = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.39F, 0.5F, 0.7F, 0.74F, 0.74F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.74F, 0.75F, 0.85F, 1.19F, 1.19F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(1.19F, 1.2F, 2.2F, 2.25F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.1F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F), 3)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 3)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.15F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(1.2F, 2.25F)).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true).newTimePair(0.0F, 0.85F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(2.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));
        ENDER_GLAIVE_NAPOLEON_WATERLOW = builder.nextAccessor("biped/wom_clone/ender_glaive_napoleon_waterlow",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.35F, 0.39F, 0.39F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.39F, 0.4F, 0.6F, 0.64F, 0.64F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.64F, 0.65F, 1.0F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.4F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));

        CLONE_ENDERBLASTER_TWOHAND_TOMAHAWK = builder.nextAccessor("biped/wom_clone/clone_enderblaster_twohand_dash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.44F, 0.45F, 0.45F, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE),
                        new AttackAnimation.Phase(0.45F, 0.5F, 0.6F, 0.65F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.TORMENT_AIRSLAM))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.GROUND_BODYSCRAPE_LAND, AnimationEvent.Side.CLIENT)
                        }));
        YELLOW_TORMENT_CHARGED_ATTACK_3 = builder.nextAccessor("biped/wom_clone/yellow_torment_charged_attack_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.2F, 1.5F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.BYPASS_DODGE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.35F, 0.9F))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.9F && elapsedTime < 1.15F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for (BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(livingEntityPatch.getOriginal().getY() - (double) dpy) - (double) 1.0F, 0.0F);
                                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                            } else {
                                return speed;
                            }
                        }).addEvents(
                                AnimationEvent.InTimeEvent.create(0.35F, reascer.wom.gameasset.ReuseableEvents.AIRBURST_JUMP, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.15F, AVAnimations.ReuseableEvents.SHOCK_WAVE, AnimationEvent.Side.SERVER)
                        )
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, params) -> {
                            LivingEntity target = livingEntityPatch.getOriginal().getLastHurtMob();
                            if (target != null && target.distanceTo(livingEntityPatch.getOriginal()) < 30.0F) {
                                double offset = 4.0F;
                                double referenceX = target.getX();
                                double referenceY = target.getY();
                                double referenceZ = target.getZ();
                                float referenceYaw = livingEntityPatch.getOriginal().yHeadRot;
                                double newX = referenceX + offset * Math.sin(Math.toRadians(referenceYaw));
                                double newZ = referenceZ - offset * Math.cos(Math.toRadians(referenceYaw));
                                BlockPos blockPos = new BlockPos((int) newX, (int) referenceY, (int) newZ);
                                BlockState block = livingEntityPatch.getOriginal().level().getBlockState(blockPos);
                                if (!block.isCollisionShapeFullBlock(livingEntityPatch.getOriginal().level(), blockPos)) {
                                    livingEntityPatch.getOriginal().teleportTo(newX, referenceY, newZ);
                                } else {
                                    livingEntityPatch.getOriginal().teleportTo(referenceX, referenceY, referenceZ);
                                }

                                livingEntityPatch.getOriginal().setDeltaMovement(target.getDeltaMovement());
                            }

                            ((ServerLevel) livingEntityPatch.getOriginal().level())
                                    .sendParticles(ParticleTypes.REVERSE_PORTAL,
                                            livingEntityPatch.getOriginal().getX(),
                                            livingEntityPatch.getOriginal().getY() + (double) 1.0F,
                                            livingEntityPatch.getOriginal().getZ(),
                                            60, 0.05, 0.05, 0.05, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(
                                    null,
                                    livingEntityPatch.getOriginal().xo,
                                    livingEntityPatch.getOriginal().yo + (double) 1.0F,
                                    livingEntityPatch.getOriginal().zo,
                                    SoundEvents.ENDERMAN_TELEPORT,
                                    livingEntityPatch.getOriginal().getSoundSource(),
                                    2.0F, 1.0F - (new Random().nextFloat() - 0.5F) * 0.2F
                            );

                        }, AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> {
                            LivingEntity entity = livingEntityPatch.getOriginal();
                            livingEntityPatch.getOriginal().level()
                                    .addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(),
                                            entity.getX(), entity.getY(), entity.getZ(),
                                            Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F
                                    );
                        }, AnimationEvent.Side.CLIENT)}));
        CLONE_ENDERBLASTER_ONEHAND_DASH = builder.nextAccessor("biped/wom_clone/clone_enderblaster_onehand_dash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE),
                        new AttackAnimation.Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().legL, WOMWeaponColliders.KICK_HUGE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK,
                                (self, entitypatch, transformSheet) -> {
                                    LivingEntity attackTarget = entitypatch.getTarget();
                                    if (!(Boolean) self.getRealAnimation().get().getProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                                        TransformSheet transform = self.getTransfroms().get("Root").copyAll();
                                        Keyframe[] keyframes = transform.getKeyframes();
                                        int startFrame = 0;
                                        int endFrame = transform.getKeyframes().length - 1;
                                        Vec3f keyLast = keyframes[endFrame].transform().translation();
                                        Vec3 pos = entitypatch.getOriginal().getEyePosition();
                                        Vec3 targetpos = attackTarget.position().add(attackTarget.getDeltaMovement().scale(1.5F));
                                        float horizontalDistance = Math.max((float) targetpos.subtract(pos).horizontalDistance() - (attackTarget.getBbWidth() + entitypatch.getOriginal().getBbWidth()), 0.0F);
                                        Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                                        float scale = Math.min(worldPosition.length() / keyLast.length(), 1.5F);

                                        for (int i = startFrame; i <= endFrame; ++i) {
                                            Vec3f translation = keyframes[i].transform().translation();
                                            translation.z *= scale;
                                        }

                                        transformSheet.readFrom(transform);
                                    } else if (transformSheet != null) {
                                        transformSheet.readFrom(self.getTransfroms().get("Root"));
                                    }
                                }));
        SLEDGEHAMMER_TORMENT_BERSERK_AUTO_1 = builder.nextAccessor("biped/wom_clone/sledgehammer_torment_berserk_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.OVERBLOOD_HIT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.SLEDGEHAMMER_SHOOT, AnimationEvent.Side.SERVER)
                        )
        );
        SLEDGEHAMMER_TORMENT_BERSERK_AUTO_2 = builder.nextAccessor("biped/wom_clone/sledgehammer_torment_berserk_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.4F, 0.15F, 0.5F, 0.5F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(9.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.OVERBLOOD_HIT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.31F, AVAnimations.ReuseableEvents.SLEDGEHAMMER_SHOOT, AnimationEvent.Side.SERVER)
                        )
        );
        SLEDGEHAMMER_SOLAR_AUTO_3 = builder.nextAccessor("biped/wom_clone/sledgehammer_solar_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.4F, 0.75F, 0.85F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.3F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, AVAnimations.ReuseableEvents.SLEDGEHAMMER_SHOOT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.5F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
        );
        CLONE_ANTITHEUS_SHOOT = builder.nextAccessor("biped/wom_clone/clone_antitheus_shoot",
                accessor -> new AntitheusShootAttackAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ANTITHEUS_SHOOT, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(1.0F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, SoundEvents.WITHER_SHOOT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.WITHER_BREAK_BLOCK)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));
        CLONE_ANTITHEUS_ASCENDED_IDLE = builder.nextAccessor("biped/wom_clone/clone_antitheus_ascended_idle",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature));
        NULL_ANTITHEUS_ASCENDED_AUTO_1 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_1",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.4F, 0.4F, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), AnimationEvent.Side.CLIENT)
                        }));
        NULL_ANTITHEUS_ASCENDED_AUTO_2 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.3F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new AttackAnimation.Phase(0.5F, 0.6F, 0.7F, 0.7F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), AnimationEvent.Side.CLIENT)}));
        NULL_ANTITHEUS_ASCENDED_AUTO_3 = builder.nextAccessor("biped/wom_clone/null_antitheus_ascended_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new AttackAnimation.Phase(0.35F, 0.4F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES),
                        new AttackAnimation.Phase(0.55F, 0.7F, 0.8F, 0.85F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get(), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 0)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.9F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F), AnimationEvent.Side.CLIENT)}));
        HEROBRINE_MOB_ENDERSTEP_OBSCURIS = builder.nextAccessor("biped/wom_clone/herobrine_mob_ender_obscuris",
                accessor -> new DodgeAnimation(0.05F, accessor, 0.6F, 1.65F, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.15F))
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.1F, reascer.wom.gameasset.ReuseableEvents.ENDER_STEP, AnimationEvent.Side.BOTH),
                                AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        Entity entity = livingEntityPatch.getOriginal();
                                        if (entity instanceof HerobrineMob herobrineMob && herobrineMob.getTarget() != null) {
                                            LivingEntity target = herobrineMob.getTarget();
                                            if (target != null) {
                                                double offset = 2.0D;

                                                double referenceX = target.getX();
                                                double referenceY = target.getY();
                                                double referenceZ = target.getZ();
                                                float referenceYaw = target.yHeadRot;

                                                double sin = Math.sin(Math.toRadians(referenceYaw));
                                                double cos = Math.cos(Math.toRadians(referenceYaw));
                                                double newX = referenceX + offset * sin;
                                                double newZ = referenceZ - offset * cos;
                                                double newY = referenceY;

                                                ServerLevel serverLevel = (ServerLevel) entity.level();
                                                int baseY = target.blockPosition().getY();
                                                int minY = serverLevel.getMinBuildHeight() + 1;
                                                int maxY = serverLevel.getMaxBuildHeight() - 2;
                                                baseY = Mth.clamp(baseY, minY, maxY);

                                                BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
                                                boolean found = false;
                                                for (int tries = 0; tries < 10 && offset > 0.25D; tries++) {
                                                    newX = referenceX + offset * sin;
                                                    newZ = referenceZ - offset * cos;

                                                    mpos.set(Mth.floor(newX), baseY, Mth.floor(newZ));
                                                    if (!serverLevel.isLoaded(mpos)) {
                                                        offset -= 0.25D;
                                                        continue;
                                                    }
                                                    int scan = 0;
                                                    while (scan++ < 12 && mpos.getY() > minY) {
                                                        BlockPos belowPos = mpos.below();
                                                        BlockState below = serverLevel.getBlockState(belowPos);

                                                        if (below.isFaceSturdy(serverLevel, belowPos, net.minecraft.core.Direction.UP) && !below.is(Blocks.VOID_AIR)) {
                                                            break;
                                                        }
                                                        mpos.move(0, -1, 0);
                                                    }

                                                    BlockPos belowPos = mpos.below();
                                                    BlockState below = serverLevel.getBlockState(belowPos);
                                                    BlockState feet  = serverLevel.getBlockState(mpos);
                                                    BlockState head  = serverLevel.getBlockState(mpos.above());

                                                    boolean solidBelow = below.isFaceSturdy(serverLevel, belowPos, net.minecraft.core.Direction.UP) && !below.is(Blocks.VOID_AIR);
                                                    boolean freeFeet = feet.isAir() || feet.getBlock() instanceof BushBlock;
                                                    boolean freeHead = head.isAir() || head.getBlock() instanceof BushBlock;

                                                    if (solidBelow && freeFeet && freeHead) {
                                                        newX = mpos.getX() + 0.5D;
                                                        newY = mpos.getY();
                                                        newZ = mpos.getZ() + 0.5D;
                                                        if (serverLevel.noCollision(entity,
                                                                entity.getBoundingBox().move(newX - entity.getX(), newY - entity.getY(), newZ - entity.getZ()))) {
                                                            found = true;
                                                            break;
                                                        }
                                                    }

                                                    offset -= 0.25D;
                                                }

                                                if (found) {
                                                    entity.teleportTo(newX, newY, newZ);
                                                    entity.setDeltaMovement(target.getDeltaMovement());
                                                    entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
                                                }
                                            }
                                        }

                                        ((ServerLevel) entity.level()).sendParticles(ParticleTypes.REVERSE_PORTAL, entity.getX(), entity.getY() + (double) 1.0F, entity.getZ(), 60, 0.05, 0.05, 0.05, 0.5F);
                                        entity.level().playSound(null, entity.xo, entity.yo + (double) 1.0F, entity.zo, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 2.0F, 1.0F - (new Random().nextFloat() - 0.5F) * 0.2F);
                                    }
                                }, AnimationEvent.Side.BOTH)
                        }));
        OBSIDIAN_ANTITHEUS_ASCENDED_DEATHFALL = builder.nextAccessor("biped/wom_clone/obsidian_antitheus_ascended_deathfall",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.5F, 0.55F, 0.75F, WOMWeaponColliders.ANTITHEUS_ASCENDED_DEATHFALL, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(10.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.ANTITHEUS_PUNCH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.8F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.75F))
                        .addEvents(AnimationEvent.InTimeEvent.create(0.05F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                            Vec3 position = new Vec3(0.0F, 3.0F, 0.0F);
                            livingEntityPatch.getOriginal().move(MoverType.SELF, position);
                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.7F, 0.7F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.WHOOSH_BIG.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            for (int i = 0; i < 24; ++i) {
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) (new Random().nextFloat() - 0.5F), livingEntityPatch.getOriginal().getY() + 2.200000047683716D, livingEntityPatch.getOriginal().getZ() + (double) (new Random().nextFloat() - 0.5F), (new Random().nextFloat() - 0.5F) * 0.05F, -((double) new Random().nextFloat() * (livingEntityPatch.getOriginal().getY() - (double) f1) * 0.4000000059604645D), (new Random().nextFloat() - 0.5F) * 0.05F);
                            }

                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> {
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.NEUTRAL, 0.7F, 0.5F);
                            livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal().blockPosition(), EpicFightSounds.BLUNT_HIT_HARD.get(), SoundSource.NEUTRAL, 0.7F, 0.7F);

                            float f = (float) livingEntityPatch.getOriginal().getX();
                            float f1 = (float) livingEntityPatch.getOriginal().getY();

                            Vec3 vec3 = new Vec3(0.0D, (double) (f1 - 2.0F) - livingEntityPatch.getOriginal().getY(), 0.0D);

                            livingEntityPatch.getOriginal().move(MoverType.SELF, vec3);
                            byte b0 = 80;
                            double d0 = 0.6D;
                            double d1 = 0.01D;

                            for (int i = 0; i < b0; ++i) {
                                double d2 = 6.283185307179586D * new Random().nextDouble();
                                double d3 = (new Random().nextDouble() - 0.5D) * 3.141592653589793D * d1 / d0;
                                double d4 = d0 * Math.cos(d3) * Math.cos(d2);
                                double d5 = d0 * Math.cos(d3) * Math.sin(d2);
                                double d6 = d0 * Math.sin(d3);
                                float f3 = new Random().nextFloat() + 0.4F;
                                Vec3f vec3f = new Vec3f((float) d4 * f3, (float) d5 * f3, (float) d6 * f3);
                                OpenMatrix4f openmatrix4f = new OpenMatrix4f().rotate((float) Math.toRadians(90.0F), new Vec3f(1.0F, 0.0F, 0.0F));

                                OpenMatrix4f.transform3v(openmatrix4f, vec3f, vec3f);
                                livingEntityPatch.getOriginal().level().addParticle(ParticleTypes.LARGE_SMOKE, livingEntityPatch.getOriginal().getX() + (double) vec3f.x, (float) (int) livingEntityPatch.getOriginal().getY() + vec3f.y + 0.02F, livingEntityPatch.getOriginal().getZ() + (double) vec3f.z, vec3f.x, vec3f.y, vec3f.z);
                            }

                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.55F, (livingEntityPatch, assetaccessor, animationparameters) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_CROSS, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_CROSS_FIX_DELAY_SHADOW_HEROBRINE, AnimationEvent.Side.SERVER)
                        )
        );
        OLD_MOONLESS_RUN = builder.nextAccessor("biped/wom_clone/old_moonless_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        TRIDENT_TWO_HAND_RUN = builder.nextAccessor("biped/wom_clone/trident_two_hand_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        OBSIDIAN_STRONG_PUNCH = builder.nextAccessor("biped/wom_clone/obsidian_strong_punch",
                accessor -> new BasicMultipleAttackAnimation(0.3F, 0.1F, 0.15F, 0.35F, WOMWeaponColliders.PUNCH, humanoidArmature.get().handL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SMALL.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.0F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, AVAnimations.ReuseableEvents.SUMMON_3_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));
        OBSIDIAN_ENDERBLASTER_TWOHAND_TISHNAW = builder.nextAccessor("biped/wom_clone/obsidian_enderblaster_twohand_tishnaw",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.3F, 0.5F, 0.65F, WOMWeaponColliders.KICK_HUGE, humanoidArmature.get().legR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.65F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 20)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.3F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.35F && elapsedTime < 0.45F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(2.5F, -0.25F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                AABB box = AABB.ofSize(livingEntityPatch.getOriginal().getPosition(1.0F), 3.0F, 3.0F, 3.0F);
                                List<Entity> list = livingEntityPatch.getOriginal().level().getEntities(livingEntityPatch.getOriginal(), box);
                                if (distanceToGround > 0.5F && list.isEmpty()) {
                                    livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                    return 0.05F;
                                } else {
                                    return speed;
                                }
                            } else {
                                return 1.0F;
                            }
                        })
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> {
                                            LivingEntity entity = livingEntityPatch.getOriginal();
                                            livingEntityPatch.getOriginal().level()
                                                    .addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(),
                                                            entity.getX(), entity.getY(), entity.getZ(),
                                                            Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F
                                                    );
                                        }, AnimationEvent.Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.GROUND_BODYSCRAPE_LAND, AnimationEvent.Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_SMALL_CROSS, AnimationEvent.Side.SERVER)
                                })
        );
        SHADOW_OBSIDIAN_SWORD_TORMENT_AIRSLAM = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_torment_airslam",
                accessor -> new BasicMultipleAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.45F, 0.55F, 0.6F, 0.6F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(0.6F, 0.5F, 0.65F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.0F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                                (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                                    if (elapsedTime >= 0.3F && elapsedTime < 0.55F) {
                                        float dpx = (float) livingEntityPatch.getOriginal().getX();
                                        float dpy = (float) livingEntityPatch.getOriginal().getY();
                                        float dpz = (float) livingEntityPatch.getOriginal().getZ();
                                        for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                            --dpy;
                                        }
                                        float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                        return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                                    } else {
                                        return speed;
                                    }
                                }).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.55F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)}));
        SHADOW_OBSIDIAN_SWORD_TORMENT_BERSERK_DASH = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_torment_berserk_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(0.55F, 0.8F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(0.9F, 1.35F, 1.4F, 1.4F, Float.MAX_VALUE, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get()).
                        addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get(), 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.5F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.85F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.4F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.4F, AVAnimations.ReuseableEvents.SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER)
                        }));
        SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_3 = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_gezets_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.5F, 0.55F, 0.55F, InteractionHand.OFF_HAND, humanoidArmature.get().handL, WOMWeaponColliders.PUNCH), new AttackAnimation.Phase(0.55F, 0.7F, 0.85F, 1.0F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, WOMWeaponColliders.GESETZ))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.33F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.84F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get(), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addEvents(
                                new AnimationEvent[] {
                                        AnimationEvent.InTimeEvent.create(0.8F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - (new Random().nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT),
                                        AnimationEvent.InTimeEvent.create(0.8F, AVAnimations.ReuseableEvents.THROW_OBSIDIAN_OFFHAND, AnimationEvent.Side.SERVER)
                                }
                        )
        );
        SHADOW_OBSIDIAN_SWORD_GESETZ_AUTO_2 = builder.nextAccessor("biped/wom_clone/shadow_obsidian_sword_gezets_auto_2",
                accessor -> new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.3F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, WOMWeaponColliders.GESETZ_INSET_LARGE))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F)
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.3F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - (new Random().nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT)}));
        CLONE_NAPOLEON_WATERLOW_SHOOT = builder.nextAccessor("biped/wom_clone/clone_napoleon_waterlow_shoot",
                accessor -> new SpecialAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.1F, 0.3F, 0.35F, 0.35F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.35F, 0.8F, 0.9F, 0.94F, 0.94F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.94F, 0.95F, 1.1F, 1.1F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.NAPOLEON_WATERLOW_SHOOT))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(6.0F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.58F), 2)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 2)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.8F))
                        .addProperty(WomAnimationProperty.CAN_SPAM, true)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 1.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                                (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                                    if (elapsedTime > 0.8F && elapsedTime < 0.9F) {
                                        float dpx = (float) livingEntityPatch.getOriginal().getX();
                                        float dpy = (float) livingEntityPatch.getOriginal().getY() - 1.0F;
                                        float dpz = (float) livingEntityPatch.getOriginal().getZ();
                                        BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz));
                                        livingEntityPatch.getOriginal().setDeltaMovement(0.0F, -2.0F, 0.0F);
                                        LivingEntity entity = livingEntityPatch.getOriginal();
                                        if ((block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR) && dpy > -64.0F && !block.is(Blocks.WATER) && !entity.onGround()) {
                                            return (elapsedTime - 0.8F) / 0.1F;
                                        }
                                        return 2.0F;
                                    }

                                    return 1.0F;
                                })
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.15F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.25F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.45F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.75F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.95F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.05F, (livingEntityPatch, self, params) -> {
                                    Level level = livingEntityPatch.getOriginal().level();
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InPeriodEvent.create(0.0F, 1.05F, (livingEntityPatch, self, params) -> {
                                    livingEntityPatch.getOriginal().resetFallDistance();
                                    Entity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity instanceof Player player) {
                                        player.yCloak = 0.0F;
                                        player.yCloakO = 0.0F;
                                    }
                                }, AnimationEvent.Side.BOTH))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.9F, reascer.wom.gameasset.ReuseableEvents.BODY_BIG_GROUNDSLAM, AnimationEvent.Side.CLIENT))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.LEGENDARY_SWORD_WAKE_UP_ATTACK, 0.0F);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true)
                        .newTimePair(0.0F, 0.35F).addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.55F, 1.1F).addState(EntityState.CAN_SKILL_EXECUTION, false));
        CUT_ENDERBLASTER_TWOHAND_RELOAD = builder.nextAccessor("biped/wom_clone/cut_enderblaster_twohand_reload",
                accessor -> new StaticAnimation(0.1F, false, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.25F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.3F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.35F, AVAnimations.ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.4F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.5F, AVAnimations.ReuseableEvents.TRIDENT_SPINNING, AnimationEvent.Side.CLIENT)));
        HACKER_SWORD_SKILL = builder.nextAccessor("biped/wom_clone/hacker_sword_skill", accessor -> (BasicMultipleAttackAnimation) new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(0.55F, 0.8F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(0.9F, 1.35F, 1.4F, 1.4F, 1.4F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(1.55F, 1.8F, 1.85F, 1.9F, 1.9F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
                new AttackAnimation.Phase(1.9F, 2.35F, 2.4F, 2.4F, Float.MAX_VALUE, humanoidArmature.get().toolR, ColliderPreset.SWORD))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                .addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(0.0F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.35F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.85F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.35F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.85F, (entityPatch, self, params) -> {
                            Level level = entityPatch.getOriginal().level();
                            LivingEntity entity = entityPatch.getOriginal();
                            level.addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                        }, AnimationEvent.Side.CLIENT)
                })
        );
        WARBLADE_SATSUJIN_TSUKUYOMI = builder.nextAccessor("biped/wom_clone/warblade_katana_tsukuyomi",
                accessor -> new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.6F, 0.75F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_UP_SLASH)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME,
                                TimePairList.create(0.0F, 0.6F)
                        )
                        .newTimePair(0.0F, 0.9F)
                        .addStateRemoveOld(EntityState.INACTION, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime > 0.65F && elapsedTime < 0.75F) {
                                float dpx = (float) livingEntityPatch.getOriginal().getX();
                                float dpy = (float) livingEntityPatch.getOriginal().getY();
                                float dpz = (float) livingEntityPatch.getOriginal().getZ();

                                for(BlockState block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = livingEntityPatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(livingEntityPatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                LivingEntity livingentity = livingEntityPatch.getOriginal();
                                Vec3f direction = new Vec3f(0.0F, -0.75F, 0.0F);
                                OpenMatrix4f rotation = new OpenMatrix4f().rotate(-org.joml.Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 90.0F), new Vec3f(0.0F, 1.0F, 0.0F));
                                OpenMatrix4f.transform3v(rotation, direction, direction);
                                livingentity.move(MoverType.SELF, direction.toDoubleVector());
                                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(
                                new AnimationEvent[]{
                                        AnimationEvent.InTimeEvent.create(0.25F, (livingEntityPatch, self, params) -> EpicfightUtil.shootFlyingShockwave(livingEntityPatch), AnimationEvent.Side.SERVER),
                                        AnimationEvent.InTimeEvent.create(0.7F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().resetFallDistance(), AnimationEvent.Side.SERVER)
                                })
        );
        HOOK_HERRSCHER_UP = builder.nextAccessor("biped/wom_clone/hook_herrscher_up",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.45F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SHARPCUT_UP_SLASH)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE, WOMDamageType.BLACKOUT))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.25F, (entitypatch, self, params) -> {
                            if (entitypatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                serverLevel.playSound(null, entitypatch.getOriginal().getX(), entitypatch.getOriginal().getY(), entitypatch.getOriginal().getZ(), EpicFightSounds.WHOOSH_ROD.get(), SoundSource.MASTER, 0.5F, 1.3F - ((new Random()).nextFloat() - 0.5F) * 0.1F);
                            }

                        }, AnimationEvent.Side.SERVER)}));
    }

    private static @NotNull Vec3 getVec3(LivingEntity owner) {
        Vec3 look = owner.getLookAngle();
        Vec3 forward = new Vec3(look.x, 0.0D, look.z);
        if (forward.lengthSqr() < 1.0E-6D) {
            float yawRad = (float) Math.toRadians(owner.getYRot());
            forward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        }
        forward = forward.normalize();
        return forward;
    }
}
