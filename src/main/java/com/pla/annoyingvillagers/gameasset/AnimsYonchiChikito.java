/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: LicenseRef-Author-Permission
 *
 * Upstream: Yonchi Chikito - Original Author
 * Source: Authorized by original author; no public source URL recorded.
 *
 * This file contains code and animation data adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/LicenseRef-Author-Permission.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DiamondAttractorSwordItem;
import com.pla.annoyingvillagers.network.ClientboundDiamondAttractorFx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.PacketDistributor;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.damagesource.StunType;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Bus.MOD)
public class AnimsYonchiChikito {
    // Animation from Yonchi Chikito
    public static AnimationManager.AnimationAccessor<ActionAnimation> DIAMOND_ATTRACTOR_SKILL;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GREATAXE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> GREATAXE_WALK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATAXE_SLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREATAXE_OFFHAND_ATTACK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SLAM_FIRST;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SLAM_SECOND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SLAM_THIRD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SAKURA_STAFF_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SAKURA_STAFF_WALK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAKURA_STAFF_AUTO_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAKURA_STAFF_AUTO_2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAKURA_STAFF_AUTO_3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAKURA_STAFF_AUTO_4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAKURA_STAFF_AUTO_5;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SAKURA_STAFF_DASH;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        DIAMOND_ATTRACTOR_SKILL = builder.nextAccessor("biped/yonchi_chikito/diamond_attractor",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    LivingEntity entity = livingEntityPatch.getOriginal();

                                    if (entity.level() instanceof ServerLevel serverLevel) {
                                        serverLevel.playSound(
                                                null,
                                                entity.getX(),
                                                entity.getY(),
                                                entity.getZ(),
                                                AnnoyingVillagersModSounds.DIAMOND_ATTRACTOR.get(),
                                                entity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE,
                                                1.0F,
                                                1.0F
                                        );

                                        AnnoyingVillagers.PACKET_HANDLER.send(
                                                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(livingEntityPatch::getOriginal),
                                                new ClientboundDiamondAttractorFx(entity)
                                        );

                                        DiamondAttractorSwordItem.pullWeapons(entity);
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));
        GREATAXE_IDLE = builder.nextAccessor("biped/yonchi_chikito/greataxe_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        GREATAXE_WALK = builder.nextAccessor("biped/yonchi_chikito/greataxe_walk",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        GREATAXE_SLASH = builder.nextAccessor("biped/yonchi_chikito/greataxe_slash",
                accessor -> (new BasicAttackAnimation(0.1F, 0.7F, 1.4F, 1.47F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)));
        GREATAXE_OFFHAND_ATTACK = builder.nextAccessor("biped/yonchi_chikito/greataxe_offhand_attack",
                accessor -> (new BasicAttackAnimation(0.1F, 0.7F, 1.4F, 1.47F, null, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)));
        SLAM_FIRST = builder.nextAccessor("biped/yonchi_chikito/slamfirst",
                accessor -> (new BasicAttackAnimation(0.1F, 0.9F, 1.4F, 1.47F, WOMWeaponColliders.SOLAR, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        )));
        SLAM_SECOND = builder.nextAccessor("biped/yonchi_chikito/slamsecond",
                accessor -> (new BasicAttackAnimation(0.1F, 2.1F, 3.0F, 3.0F, WOMWeaponColliders.SOLAR, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.0F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        )));
        SLAM_THIRD = builder.nextAccessor("biped/yonchi_chikito/slamthird",
                accessor -> (new BasicAttackAnimation(0.1F, 2.1F, 3.0F, 3.0F, WOMWeaponColliders.SOLAR, (Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.425F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        )));
        SLAM_THIRD = builder.nextAccessor("biped/yonchi_chikito/slamthird",
                accessor -> new BasicAttackAnimation(0.05F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(2.26F, 2.5F, 3.5F, 3.56F, 3.56F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.SOLAR),
                        new AttackAnimation.Phase(2.26F, 2.5F, 3.5F, 3.56F, 3.56F, humanoidArmature.get().toolR, WOMWeaponColliders.SOLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(2.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(3.4F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));
        SAKURA_STAFF_IDLE = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        SAKURA_STAFF_WALK = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_walk",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        SAKURA_STAFF_AUTO_1 = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_auto1",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SAKURA_STAFF_AUTO_2 = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_auto2",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SAKURA_STAFF_AUTO_3 = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_auto3",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SAKURA_STAFF_AUTO_4 = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_auto4",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SAKURA_STAFF_AUTO_5 = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_auto5",
                (accessor) -> (new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        SAKURA_STAFF_DASH = builder.nextAccessor("biped/yonchi_chikito/sakurastaff_dash",
                (accessor) -> (new DashAttackAnimation(0.1F, 0.25F, 0.3F, 0.4F, 0.8F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED, true))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
    }
}
