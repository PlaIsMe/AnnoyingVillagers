package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModBlocks;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.animation.AnimationManager;
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
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsObsidianWeapon {
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_WEAPON_RIGHT_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_WEAPON_RIGHT_2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_WEAPON_RIGHT_3;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> OBSIDIAN_WEAPON_RIGHT_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_WEAPON_TWOHAND_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_WEAPON_TWOHAND_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> OBSIDIAN_MACHINE_GUN;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_WEAPON_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_WEAPON_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_WEAPON_LEFT_1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> OBSIDIAN_WEAPON_LEFT_2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_WEAPON_LEFT_3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_WEAPON_SPECIAL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> OBSIDIAN_WEAPON_INNATE_SPECIAL;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_PILLAR_SPECIAL;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SHADOW_OBSIDIAN_PILLAR_DUAL_INNATE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SHADOW_OBSIDIAN_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_SPECIAL;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> SHADOW_OBSIDIAN_SWORD_DUAL_INNATE;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        OBSIDIAN_WEAPON_RIGHT_1 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_right_1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_2_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_RIGHT_2 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_right_2",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_2_OBSIDIAN_HAND_RIGHT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_RIGHT_3 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_right_3",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 3.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_3_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_RIGHT_4 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_right_4",
                accessor -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, SUMMON_6_OBSIDIAN_HAND_RIGHT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_TWOHAND_1 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_twohand_1",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_OBSIDIAN_PILLAR, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_TWOHAND_2 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_twohand_2",
                accessor -> new AttackAnimation(0.1F, 0.5F, 0.5F, 0.6F, 1.15F, ColliderPreset.HEAD, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.65F, SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_MACHINE_GUN = builder.nextAccessor("biped/obsidian_weapon/obsidian_machine_gun",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, ((livingEntityPatch, assetAccessor, animationParameters) -> {
                                    if (livingEntityPatch.getOriginal() instanceof Player player) {
                                        HerobrineEnderEyeItem.startShadowObsidianMachineGun((ServerLevel) player.level(), player);
                                    } else if (livingEntityPatch.getOriginal() instanceof ShadowHerobrineEntity shadowHerobrineEntity) {
                                        shadowHerobrineEntity.setObsidianMachineGunTick();
                                    }
                                }), AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_DASH = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_dash",
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
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_3_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_AIRSLASH = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_airslash",
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
                                        AnimationEvent.InTimeEvent.create(0.5F, SUMMON_OBSIDIAN_SMALL_CROSS, AnimationEvent.Side.SERVER)
                                })
        );

        OBSIDIAN_WEAPON_LEFT_1 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_left_1",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.15F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_2_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_LEFT_2 = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_left_2",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.5F, InteractionHand.OFF_HAND, null, Armatures.BIPED.get().toolL, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_3_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_LEFT_3 = builder.nextAccessor("biped/obsidian_weapon/obsidian_infernal_auto_3",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolL, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.4F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, SUMMON_6_OBSIDIAN_HAND_LEFT, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_SPECIAL = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_special",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.25F, 0.45F, 0.7F, 0.95F, ColliderPreset.BIPED_BODY_COLLIDER, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.AIR_BURST)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.6F, THROW_OBSIDIAN, AnimationEvent.Side.SERVER)
                        ));

        OBSIDIAN_WEAPON_INNATE_SPECIAL = builder.nextAccessor("biped/obsidian_weapon/obsidian_weapon_innate_special",
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
                                AnimationEvent.InTimeEvent.create(0.5F, SUMMON_OBSIDIAN_CROSS, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.6F, SUMMON_OBSIDIAN_CROSS_FIX_DELAY_SHADOW_HEROBRINE, AnimationEvent.Side.SERVER)
                        )
        );

        SHADOW_OBSIDIAN_PILLAR_DUAL_INNATE = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_pillar_dual_innate",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.25F, SUMMON_OBSIDIAN_CIRCLE, AnimationEvent.Side.SERVER)
                        ));

        SHADOW_OBSIDIAN_SWORD_AUTO1 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_auto1",
                accessor -> new BasicMultipleAttackAnimation(0.25F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                        new AttackAnimation.Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.55F));

        SHADOW_OBSIDIAN_SWORD_AUTO2 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_auto2",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F));

        SHADOW_OBSIDIAN_SWORD_AUTO3 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_auto3",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.15F, 0.2F, 0.3F, 0.75F, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true));

        SHADOW_OBSIDIAN_SWORD_AUTO4 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_auto4",
                accessor -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.4F, AVCollider.SHADOW_OBSIDIAN_PILLAR, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 4.0F));

        SHADOW_OBSIDIAN_SWORD_DASH = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dash",
                accessor -> new BasicMultipleAttackAnimation(0.1F, 0.11F, 0.27F, 0.5F, 0.95F, ColliderPreset.SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 2.0F));

        SHADOW_OBSIDIAN_SWORD_AIRSLASH = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_airslash",
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

        SHADOW_OBSIDIAN_SWORD_INNATE = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_innate",
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
                                AnimationEvent.InTimeEvent.create(0.5F, SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.85F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85F, SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.4F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.4F, SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER)
                        }));

        SHADOW_OBSIDIAN_SWORD_DUAL_AUTO1 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_auto1",
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

        SHADOW_OBSIDIAN_SWORD_DUAL_AUTO2 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_auto2",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));

        SHADOW_OBSIDIAN_SWORD_DUAL_AUTO3 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_auto3",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F));

        SHADOW_OBSIDIAN_SWORD_DUAL_AUTO4 = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_auto4",
                accessor -> new BasicMultipleAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, ColliderPreset.DUAL_SWORD, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.85F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));

        SHADOW_OBSIDIAN_SWORD_DUAL_DASH = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_dash",
                accessor -> new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.7F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.45F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));

        SHADOW_OBSIDIAN_SWORD_DUAL_AIRSLASH = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_airslash",
                accessor -> new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, AVCollider.SHADOW_OBSIDIAN_PILLAR, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.2F))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT)
                        ));

        SHADOW_OBSIDIAN_SWORD_DUAL_INNATE = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_innate",
                accessor -> new SpecialAttackAnimation(0.15F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, humanoidArmature.get().toolR, AVCollider.SHADOW_OBSIDIAN_PILLAR),
                        new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, humanoidArmature.get().toolL, AVCollider.SHADOW_OBSIDIAN_PILLAR))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT, 1)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.25F, reascer.wom.gameasset.ReuseableEvents.SOLAR_GROUNDSLAM_SMALL, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.25F, SUMMON_OBSIDIAN_CIRCLE, AnimationEvent.Side.SERVER)
                        ));

        SHADOW_OBSIDIAN_PILLAR_SPECIAL = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_pillar_special",
                accessor -> new BasicMultipleAttackAnimation(0.2F, 0.1F, 0.2F, 0.25F, ColliderPreset.FIST, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 5.4F)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, SUMMON_OBSIDIAN_WALL, AnimationEvent.Side.SERVER)
                        ));

        SHADOW_OBSIDIAN_SWORD_DUAL_SPECIAL = builder.nextAccessor("biped/obsidian_weapon/shadow_obsidian_sword_dual_special",
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
                                        AnimationEvent.InTimeEvent.create(0.8F, THROW_OBSIDIAN_OFFHAND, AnimationEvent.Side.SERVER)
                                }
                        )
        );
    }

    private static boolean isShadowObsidianMob(LivingEntity livingEntity) {
        return livingEntity instanceof ShadowHerobrineEntity
                || livingEntity instanceof ShadowHerobrineCloneEntity
                || livingEntity instanceof LowShadowHerobrineCloneEntity
                || livingEntity instanceof Herobrine7Entity
                || livingEntity instanceof ArmoredHerobrineEntity;
    }

    private static BlockState shadowObsidianBlock(LivingEntity livingEntity) {
        return AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                .defaultBlockState()
                .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
    }

    private static BlockState shadowObsidianMiddlePillar(LivingEntity livingEntity) {
        return AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                .defaultBlockState()
                .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player)
                .setValue(BlockStateProperties.HORIZONTAL_FACING, livingEntity.getDirection());
    }

    private static BlockState obsidianBlock(LivingEntity livingEntity) {
        return AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                .defaultBlockState()
                .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
    }

    public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_HAND_RIGHT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().toolR);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolR);
                    } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                        HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolR);
                    }
                }
            };
    public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_HAND_LEFT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().toolL);
                    } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                        HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolL);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().toolL);
                    }
                }
            };
    public static final AnimationEvent.E0 SUMMON_3_OBSIDIAN_HAND_LEFT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 3, Armatures.BIPED.get().toolL);
                    } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                        HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 4, Armatures.BIPED.get().toolL);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 4, Armatures.BIPED.get().toolL);
                    }
                }
            };
    public static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_HAND_LEFT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().toolL);
                    } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                        HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolL);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolL);
                    }
                }
            };
    public static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_HAND_RIGHT =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().toolR);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolR);
                    } else if (livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                        HerobrineUtil.summonShadowObsidianMiddlePillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().toolR);
                    }
                }
            };
    public static final AnimationEvent.E0 SUMMON_OBSIDIAN_PILLAR =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianPillar(serverLevel, livingEntity, obsidian);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, livingEntity);
                    }
                }
            };
    public static final AnimationEvent.E0 SUMMON_OBSIDIAN_WALL =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianWall(serverLevel, livingEntity, obsidian);
                    } else if (weapon instanceof ShadowObsidianPillarItem || weapon instanceof ShadowObsidianSwordItem) {
                        HerobrineUtil.summonShadowObsidianLongPillarDefense(serverLevel, livingEntity);
                    }
                }
            };

    public static final AnimationEvent.E0 SUMMON_OBSIDIAN_CIRCLE =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    HerobrineUtil.summonShadowObsidianLongPillarCircle(serverLevel, livingEntity, livingEntity.getOnPos());
                    if (livingEntity.getMainHandItem().getItem() instanceof ShadowObsidianPillarItem) {
                        HerobrineUtil.summonShadowObsidianLongPillarShootToward(serverLevel, livingEntity);
                    }
                }
            };

    public static final AnimationEvent.E0 SUMMON_OBSIDIAN_CROSS =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    if (weapon instanceof ShadowObsidianWeaponItem || weapon instanceof ObsidianWeaponItem) {
                        BlockState obsidian;
                        if (weapon instanceof ShadowObsidianWeaponItem) {
                            obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                        }
                        HerobrineUtil.summonObsidianCross(serverLevel, livingEntity, obsidian);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        // has delay with mob
                        if (livingEntity instanceof Player) {
                            HerobrineUtil.summonShadowObsidianLongPillarDefenseWide(serverLevel, livingEntity);
                        }
                    }
                }
            };

    public static final AnimationEvent.E0 SUMMON_OBSIDIAN_CROSS_FIX_DELAY_SHADOW_HEROBRINE =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                Item weapon = livingEntity.getMainHandItem().getItem();
                if (livingEntity.level() instanceof ServerLevel serverLevel
                        && !(livingEntity instanceof Player)
                        && weapon instanceof ShadowObsidianPillarItem) {
                    HerobrineUtil.summonShadowObsidianLongPillarDefenseWide(serverLevel, livingEntity);
                }
            };

    public static final AnimationEvent.E0 SUMMON_OBSIDIAN_SMALL_CROSS =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    BlockState obsidian;
                    if (weapon instanceof ShadowObsidianWeaponItem) {
                        obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                .defaultBlockState()
                                .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                    } else if (weapon instanceof ShadowObsidianPillarItem) {
                        obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR.get()
                                .defaultBlockState()
                                .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player)
                                .setValue(BlockStateProperties.HORIZONTAL_FACING, livingEntity.getDirection());
                    } else if (weapon instanceof ShadowObsidianSwordItem
                            || livingEntity.getOffhandItem().getItem() instanceof ShadowObsidianSwordItem) {
                        obsidian = shadowObsidianMiddlePillar(livingEntity);
                    } else if (isShadowObsidianMob(livingEntity)) {
                        obsidian = shadowObsidianBlock(livingEntity);
                    } else {
                        obsidian = obsidianBlock(livingEntity);
                    }
                    HerobrineUtil.summonObsidianSmallCross(serverLevel, livingEntity, obsidian);
                }
            };

    public static final AnimationEvent.E0 THROW_OBSIDIAN =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    BlockState obsidian;
                    if (weapon instanceof ShadowObsidianWeaponItem) {
                        obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                .defaultBlockState()
                                .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                    } else if (weapon instanceof ShadowObsidianSwordItem) {
                        obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                                .defaultBlockState()
                                .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                    } else if (isShadowObsidianMob(livingEntity)) {
                        obsidian = shadowObsidianBlock(livingEntity);
                    } else {
                        obsidian = obsidianBlock(livingEntity);
                    }
                    LivingEntity attacker = livingEntityPatch.getOriginal();
                    Vec3 to = attacker.getEyePosition().add(attacker.getLookAngle().scale(16.0));
                    if (attacker instanceof Mob mob && mob.getTarget() != null) {
                        to = mob.getTarget().getEyePosition(1.0F);
                    }
                    BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                            attacker.level(),
                            attacker,
                            obsidian
                    );
                    serverLevel.addFreshEntity(throwingObsidian);
                    Vec3 dir = to.subtract(throwingObsidian.position());
                    if (dir.lengthSqr() < 1.0e-6) dir = attacker.getLookAngle();
                    Vec3 vel = dir.normalize().scale(2.0F);
                    throwingObsidian.setDeltaMovement(vel);
                }
            };

    public static final AnimationEvent.E0 THROW_OBSIDIAN_OFFHAND =
            (livingEntityPatch, staticAnimation, object) -> {
                LivingEntity livingEntity = livingEntityPatch.getOriginal();
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                            livingEntity, new Vec3f(0, 0, 0),
                            Armatures.BIPED.get().toolL, 2.0F, 0.0F
                    );
                    Item weapon = livingEntity.getMainHandItem().getItem();
                    BlockState obsidian;
                    if (weapon instanceof ShadowObsidianWeaponItem) {
                        obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK.get()
                                .defaultBlockState()
                                .setValue(ShadowObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                    } else if (weapon instanceof ShadowObsidianSwordItem) {
                        obsidian = AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR.get()
                                .defaultBlockState()
                                .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
                    } else if (isShadowObsidianMob(livingEntity)) {
                        obsidian = shadowObsidianBlock(livingEntity);
                    } else {
                        obsidian = obsidianBlock(livingEntity);
                    }
                    LivingEntity attacker = livingEntityPatch.getOriginal();
                    Vec3 to = attacker.getEyePosition().add(attacker.getLookAngle().scale(16.0));
                    if (attacker instanceof Mob mob && mob.getTarget() != null) {
                        to = mob.getTarget().getEyePosition(1.0F);
                    }
                    BlockProjectileEntity throwingObsidian = new BlockProjectileEntity(
                            attacker.level(),
                            attacker,
                            obsidian
                    );
                    serverLevel.addFreshEntity(throwingObsidian);
                    if (jointVec != null) {
                        throwingObsidian.moveTo(jointVec);
                    }
                    Vec3 dir = to.subtract(throwingObsidian.position());
                    if (dir.lengthSqr() < 1.0e-6) dir = attacker.getLookAngle();
                    Vec3 vel = dir.normalize().scale(2.0F);
                    throwingObsidian.setDeltaMovement(vel);
                }
            };
}
