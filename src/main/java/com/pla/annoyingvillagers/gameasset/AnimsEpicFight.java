package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.item.ThunderDiamondBladeItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsEpicFight {
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DRINK_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_MAINHAND;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AEGIS_SHIELD_SHOOT_MAINHAND;
    public static AnimationManager.AnimationAccessor<ActionAnimation> AEGIS_SHIELD_SHOOT_OFFHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_OFFHAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_FIST_AUTO3;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> OBSIDIAN_FIST_AIR_SLASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SHADOW_OBSIDIAN_FIST_AIR_SLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_BIPED_LANDING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_ZOMBIE_ATTACK3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_FIST_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_FIST_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SHADOW_HEROBRINE_BIPED_LANDING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NERF_TSUNAMI_REINFORCED;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE_LEGENDARY_SWORD_START;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_AXE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> HOOK_AXE_AUTO2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HOOK_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DNAX_HOOK_SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DNAX_HOOK_DANCING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THUNDER_SWEEPING_EDGE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THUNDER_DANCING_EDGE;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        EAT_OFFHAND = builder.nextAccessor("biped/epicfight_clone/eat_offhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        DRINK_OFFHAND = builder.nextAccessor("biped/epicfight_clone/drink_offhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        SHIELD_MAINHAND = builder.nextAccessor("biped/epicfight_clone/shield_mainhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        AEGIS_SHIELD_SHOOT_MAINHAND = builder.nextAccessor("biped/epicfight_clone/aegis_shield_shoot_mainhand",
                accessor -> new ActionAnimation(0.35F, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    EnderAegisItem.shieldShoot(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal());
                                }, AnimationEvent.Side.SERVER)
                        ));
        AEGIS_SHIELD_SHOOT_OFFHAND = builder.nextAccessor("biped/epicfight_clone/aegis_shield_shoot_offhand",
                accessor -> new ActionAnimation(0.35F, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.5F, (livingEntityPatch, self, p) -> {
                                    EnderAegisItem.shieldShoot(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal());
                                }, AnimationEvent.Side.SERVER)
                        ));
        SHIELD_OFFHAND = builder.nextAccessor("biped/epicfight_clone/shield_offhand",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));
        OBSIDIAN_FIST_AUTO1 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
        );
        OBSIDIAN_FIST_AUTO2 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto2",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));
        OBSIDIAN_FIST_AUTO3 = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F));
        SHADOW_OBSIDIAN_FIST_AUTO1 = builder.nextAccessor("biped/epicfight_clone/shadow_obsidian_fist_auto1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        SHADOW_OBSIDIAN_FIST_AUTO3 = builder.nextAccessor("biped/epicfight_clone/shadow_obsidian_fist_auto3",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));
        OBSIDIAN_FIST_AIR_SLASH = builder.nextAccessor("biped/epicfight_clone/obsidian_fist_airslash",
                accessor -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        SHADOW_OBSIDIAN_FIST_AIR_SLASH = builder.nextAccessor("biped/epicfight_clone/shadow_obsidian_fist_airslash",
                accessor -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, AVCollider.SHADOW_OBSIDIAN_PILLAR, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));
        OBSIDIAN_BIPED_LANDING = builder.nextAccessor("biped/epicfight_clone/obsidian_landing",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));
        OBSIDIAN_ZOMBIE_ATTACK3 = builder.nextAccessor("biped/epicfight_clone/obsidian_zombie_attack3",
                accessor -> new AttackAnimation(0.1F, 0.5F, 0.5F, 0.6F, 1.15F, ColliderPreset.HEAD, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F));
        SHADOW_HEROBRINE_BIPED_LANDING = builder.nextAccessor("biped/epicfight_clone/shadow_herobrine_landing",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    if (!livingEntityPatch.isLogicalClient()) {
                                        LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                        if (livingEntity instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
                                            shadowHerobrineEntity.setObsidianMachineGunTick();
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));
        NERF_TSUNAMI_REINFORCED = builder.nextAccessor("biped/epicfight_clone/tsunami_reinforced", accessor -> new AttackAnimation(0.1F, 0.2F, 0.35F, 0.45F, 0.7F, ColliderPreset.BIPED_BODY_COLLIDER, Armatures.BIPED.get().rootJoint, accessor, Armatures.BIPED)
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
        BLUE_DEMON_DIE_LEGENDARY_SWORD_START = builder.nextAccessor("biped/epicfight_clone/blue_demon_die_legendary_sword_start",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK = builder.nextAccessor("biped/epicfight_clone/blue_demon_die_legendary_sword_tick",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HOOK_AXE_AUTO1 = builder.nextAccessor("biped/epicfight_clone/hook_axe_auto1",
                (accessor) -> new BasicAttackAnimation(0.15F, 0.05F, 0.15F, 0.7F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED));
        HOOK_AXE_AUTO2 = builder.nextAccessor("biped/epicfight_clone/hook_axe_auto2", (accessor) -> new BasicAttackAnimation(0.15F, 0.05F, 0.15F, 0.85F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED));
        HOOK_DANCING_EDGE = builder.nextAccessor("biped/epicfight_clone/hook_dancing_edge",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new AttackAnimation.Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true));
        DNAX_HOOK_SWEEPING_EDGE = builder.nextAccessor("biped/epicfight_clone/dnax_hook_sweeping_edge",
                (accessor) -> (new AttackAnimation(0.1F, 0.0F, 0.15F, 0.3F, 0.8F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 1)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, Animations.ReusableSources.COMBO_ATTACK_DIRECTION_MODIFIER));
        DNAX_HOOK_DANCING_EDGE = builder.nextAccessor("biped/epicfight_clone/dnax_hook_dancing_edge",
                (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.25F, 0.4F, 0.4F, 0.4F, Armatures.BIPED.get().toolR, null),
                        new AttackAnimation.Phase(0.4F, 0.4F, 0.5F, 0.55F, 0.6F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.6F, 0.6F, 0.7F, 1.15F, Float.MAX_VALUE, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true));
        THUNDER_SWEEPING_EDGE = builder.nextAccessor("biped/epicfight_clone/thunder_sweeping_edge",
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
        THUNDER_DANCING_EDGE = builder.nextAccessor("biped/epicfight_clone/thunder_dancing_edge",
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
    }
}
