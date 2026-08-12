package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.gameasset.EFNAnimations;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.RushSwordAnimation;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DiamondAttractorSwordItem;
import com.pla.annoyingvillagers.item.ThunderDiamondBladeItem;
import com.pla.annoyingvillagers.network.ClientboundDiamondAttractorFx;
import com.pla.annoyingvillagers.network.ClientboundMuteExplosionAtPos;
import com.pla.annoyingvillagers.network.ClientboundWoopieSwordWindFx;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMDamageType;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsAVSword {
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> WOOPIE_INNATE;
    public static AnimationManager.AnimationAccessor<RushSwordAnimation> WOOPIE_INNATE_SPECIAL;
    public static AnimationManager.AnimationAccessor<RushSwordAnimation> WOOPIE_INNATE_SPECIAL_LEGENDARY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> WOOPIE_FLY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> GREAT_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THUNDER_DIAMOND_BLADE_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THUNDER_DIAMOND_BLADE_DUAL_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLACK_FIRE_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_FLAME_SWORD_SPECIAL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> DIAMOND_ATTRACTOR_INNATE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DIAMOND_BLASTER_INNATE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> HACKER_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_SWORD_INNATE1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_SWORD_INNATE2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HOOK_SWORD_DUAL_INNATE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> FLANKER_HOOK_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DNAX_HOOK_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DNAX_HOOK_SWORD_DUAL_INNATE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_DAGGER_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> AV_DAGGER_AUTO2;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> AV_DAGGER_INNATE;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        WOOPIE_INNATE = builder.nextAccessor("biped/av_sword/woopie_innate", (animationaccessor) -> (BasicAttackAnimation) (new BasicAttackAnimation(0.05F, 0.01F, 0.1F, 0.6F, null, humanoidArmature.get().toolR, animationaccessor, humanoidArmature))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.2F, (livingEntityPatch, self, params) -> {
                            LivingEntity entity = livingEntityPatch.getOriginal();
                            if (entity.level().isClientSide()) return;
                            Vec3 windPos = EpicfightUtil.getJointWithTranslation(
                                    entity,
                                    new Vec3f(0.0F, 0.0F, 0.0F),
                                    Armatures.BIPED.get().toolR,
                                    4.3F,
                                    0.5F
                            );
                            if (windPos != null) {
                                BlockPos mutePos = BlockPos.containing(windPos);
                                AnnoyingVillagers.PACKET_HANDLER.send(
                                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                                        new ClientboundMuteExplosionAtPos(mutePos, 4)
                                );
                                entity.level().explode(entity, windPos.x, windPos.y, windPos.z,
                                        2.0F, false, Level.ExplosionInteraction.NONE);
                                AnnoyingVillagers.PACKET_HANDLER.send(
                                        PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
                                        new ClientboundWoopieSwordWindFx(windPos)
                                );
                            }
                        }, AnimationEvent.Side.SERVER)
                )
        );

        WOOPIE_INNATE_SPECIAL = builder.nextAccessor("biped/av_sword/woopie_innate_special",
                accessor -> new RushSwordAnimation(
                        0.15F, 0.0F, 0.1F, 0.26F, 0.75F,
                        ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));

        WOOPIE_INNATE_SPECIAL_LEGENDARY = builder.nextAccessor("biped/av_sword/woopie_innate_special_legendary",
                accessor -> new RushSwordAnimation(
                        0.15F, 0.0F, 0.1F, 0.26F, 0.75F,
                        ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));

        WOOPIE_FLY = builder.nextAccessor("biped/av_sword/woopie_fly",
                accessor -> new ActionAnimation(0.05F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.REMOVE_DELTA_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (animation, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                        .newTimePair(0.0F, 0.3F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.3F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, params) -> {
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    if (entity.level().isClientSide() || !entity.getOffhandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) return;

                                    Vec3 offHandPos = EpicfightUtil.getJointWithTranslation(entity, new Vec3f(0.0F, 0.0F, 0.0F), Armatures.BIPED.get().toolL, 0.0F, 0.0F);
                                    Vec3 windPos = offHandPos == null ? entity.position().add(0.0D, 0.05D, 0.0D) : new Vec3(offHandPos.x, entity.getY() + 0.05D, offHandPos.z);

                                    AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClientboundMuteExplosionAtPos(BlockPos.containing(windPos), 4));
                                    entity.level().explode(entity, windPos.x, windPos.y, windPos.z, 2.0F, false, Level.ExplosionInteraction.NONE);
                                    AnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClientboundWoopieSwordWindFx(windPos));
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, (livingEntityPatch, self, params) -> livingEntityPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_INNATE, 0.0F), AnimationEvent.Side.SERVER)
                        ));

        GREAT_SWORD_INNATE = builder.nextAccessor("biped/av_sword/great_sword_innate",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));

        THUNDER_DIAMOND_BLADE_INNATE = builder.nextAccessor("biped/av_sword/thunder_diamond_blade_innate",
                (accessor) -> (new AttackAnimation(0.1F, 0.0F, 0.15F, 0.3F, 0.8F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, assetAccessor, objects) -> {
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, livingEntity);
                                    }

                                }, AnimationEvent.Side.BOTH)
                        ));

        THUNDER_DIAMOND_BLADE_DUAL_INNATE = builder.nextAccessor("biped/c/thunder_diamond_blade_dual_innate",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new AttackAnimation.Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, assetAccessor, objects) -> {
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, livingEntity);
                                        if (livingEntity.getOffhandItem().getItem() instanceof ThunderDiamondBladeItem) {
                                            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, livingEntity, true);
                                        }
                                    }

                                }, AnimationEvent.Side.BOTH)
                        ));

        BLACK_FIRE_SWORD_INNATE = builder.nextAccessor("biped/av_sword/black_fire_sword_innate",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, (livingEntityPatch, self, p) -> BlackFireEntity.shootFromOwnerLook(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal()), AnimationEvent.Side.SERVER)
                        ));

        BLUE_FLAME_SWORD_SPECIAL = builder.nextAccessor("biped/av_sword/blue_flame_sword_special",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));

        DIAMOND_ATTRACTOR_INNATE = builder.nextAccessor("biped/av_sword/diamond_attractor_innate",
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

        DIAMOND_BLASTER_INNATE = builder.nextAccessor("biped/av_sword/diamond_blaster_innate",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));

        HACKER_SWORD_INNATE = builder.nextAccessor("biped/av_sword/hacker_sword_innate", accessor -> (BasicMultipleAttackAnimation) new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.45F, 0.5F, 0.55F, 0.55F, humanoidArmature.get().toolR, ColliderPreset.SWORD),
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

        HOOK_SWORD_INNATE1 = builder.nextAccessor("biped/av_sword/hook_sword_innate1",
                (accessor) -> new BasicAttackAnimation(0.15F, 0.05F, 0.15F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED));

        HOOK_SWORD_INNATE2 = builder.nextAccessor("biped/av_sword/hook_sword_innate2", (accessor) -> new BasicAttackAnimation(0.15F, 0.05F, 0.15F, 0.85F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED));

        HOOK_SWORD_DUAL_INNATE = builder.nextAccessor("biped/av_sword/hook_sword_dual_innate",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new AttackAnimation.Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true));

        FLANKER_HOOK_SWORD_INNATE = builder.nextAccessor("biped/av_sword/flanker_hook_sword_innate",
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

        DNAX_HOOK_SWORD_INNATE = builder.nextAccessor("biped/av_sword/dnax_hook_sword_innate",
                (accessor) -> (new AttackAnimation(0.1F, 0.0F, 0.15F, 0.3F, 0.8F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER));

        DNAX_HOOK_SWORD_DUAL_INNATE = builder.nextAccessor("biped/av_sword/dnax_hook_sword_dual_innate",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new AttackAnimation.Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true));

        AV_DAGGER_AUTO1 = builder.nextAccessor("biped/av_sword/av_dagger_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));

        AV_DAGGER_AUTO2 = builder.nextAccessor("biped/av_sword/av_dagger_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.0F, 0.1F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature));

        AV_DAGGER_INNATE = builder.nextAccessor("biped/av_sword/av_dagger_innate",
                (accessor) -> (AvalonAttackAnimation) (new AvalonAttackAnimation(
                        0.01F,
                        accessor,
                        Armatures.BIPED,
                        0.8F,
                        1.0F,
                        AvalonAnimationUtils.createSimplePhase(13, 18, 19, InteractionHand.MAIN_HAND, 0.5F, 0.7F, Armatures.BIPED.get().toolL, ColliderPreset.TACHI),
                        AvalonAnimationUtils.createSimplePhase(20, 26, 30, InteractionHand.MAIN_HAND, 0.5F, 0.7F, Armatures.BIPED.get().toolR, ColliderPreset.TACHI)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_DUAL)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFunctions.TRACE_TARGET_LOCATION_ROTATION)
                        .addProperty(AnimationProperty.ActionAnimationProperty.ENTITY_YROT_PROVIDER, MoveCoordFunctions.LOOK_DEST)
                        .newTimePair(0.0F, 0.2F)
                        .addState(EntityState.ATTACK_RESULT, DodgeAnimation.DODGEABLE_SOURCE_VALIDATOR));
    }
}
