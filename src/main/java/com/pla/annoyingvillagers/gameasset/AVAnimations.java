package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.block.ObsidianBlock;
import com.pla.annoyingvillagers.block.ShadowObsidianBlock;
import com.pla.annoyingvillagers.clazz.TridentMode;
import com.pla.annoyingvillagers.compat.p1nero_bow.AnimsP1neroEpicBow;
import com.pla.annoyingvillagers.entity.*;
import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.item.*;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationEvent.Side;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.utils.HitEntityList.Priority;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AVAnimations {
    public static AnimationManager.AnimationAccessor<ActionAnimation> TRIDENT_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNOCKED_ELITE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ANIMATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_HEALING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LOW_CLONE_ESCAPE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SNAKE_BLADE_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE_BREAK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLACE_BLOCK;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BLACK_FIRE_SWORD_SKILL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> BLUE_FLAME_SWORD;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DIAMOND_BLASTER_SKILL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> EARTH_AXE_SHOOT;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> RED_AXE_ATTACK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> BLACKSCRATCHER_IDLE;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BLACKSCRATCHER_ATTACK;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(AnnoyingVillagers.MODID, AVAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        AnimsEpicFight.build(builder);
        AnimsEpicFightACG.build(builder);
        AnimsEpicFightAwaken.build(builder);
        AnimsEpicFightBattleArts.build(builder);
        AnimsEpicFightDualGreatsword.build(builder);
        AnimsEpicFightInfernalGainer.build(builder);
        AnimsEpicFightIronSpell.build(builder);
        AnimsEpicFightSanji.build(builder);
        AnimsEpicFightValourGuard.build(builder);
        AnimsPugilistSteve.build(builder);
        AnimsSculkSteve.build(builder);
        AnimsWom.build(builder);
        AnimsYonchiChikito.build(builder);
        AnimsEpicFightGuandao.build(builder);
        AnimsTacticalImbuements.build(builder);
        if (ModList.get().isLoaded("p1nero_bow")) {
            AnimsP1neroEpicBow.build(builder);
        }

        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        TRIDENT_ATTACK = builder.nextAccessor("biped/pla/trident_attack",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.relaunchGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(2.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.2F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_RIGHT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.8F, ReuseableEvents.PLAY_TRIDENT_EFFECT_HAND_LEFT, Side.SERVER),
                                AnimationEvent.InTimeEvent.create(4.0F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        BlueDemonTridentItem.summonLightningAtGroundedTridents(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, Side.SERVER)
                        ));
        HEROBRINE_ANIMATE = builder.nextAccessor("biped/pla/herobrine_animate",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        HEROBRINE_HEALING = builder.nextAccessor("biped/pla/herobrine_healing",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        LOW_CLONE_ESCAPE = builder.nextAccessor("biped/pla/low_clone_escape",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        KNOCKED_ELITE = builder.nextAccessor("biped/pla/knocked_elite",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_1 = builder.nextAccessor("biped/pla/eating_elite_1",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_2 = builder.nextAccessor("biped/pla/eating_elite_2",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_3 = builder.nextAccessor("biped/pla/eating_elite_3",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_4 = builder.nextAccessor("biped/pla/eating_elite_4",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        SNAKE_BLADE = builder.nextAccessor("biped/pla/snake_blade",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    DemoniacVoltageReaverItem.process(livingEntityPatch.getOriginal().getMainHandItem(), livingEntityPatch.getOriginal());
                                    livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                                }, Side.SERVER)
                        ));
        SNAKE_BLADE_GUARD = builder.nextAccessor("biped/pla/snake_blade_guard",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.0F, (livingEntityPatch, self, p) -> {
                                    DemoniacVoltageReaverItem.processGuard(livingEntityPatch.getOriginal().getMainHandItem(), livingEntityPatch.getOriginal());
                                    livingEntityPatch.getOriginal().getMainHandItem().getOrCreateTag().putBoolean("SnakeAnimation", true);
                                }, Side.SERVER)
                        ));
        IDLE_BREAK = builder.nextAccessor("biped/pla/idle_break",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        PLACE_BLOCK = builder.nextAccessor("biped/pla/place_block",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature));
        BLACK_FIRE_SWORD_SKILL = builder.nextAccessor("biped/pla/black_fire_sword_skill",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.8F, (livingEntityPatch, self, p) -> BlackFireEntity.shootFromOwnerLook(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal()), Side.SERVER)
                        ));
        BLUE_FLAME_SWORD = builder.nextAccessor("biped/pla/blue_flame_sword",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false));
        DIAMOND_BLASTER_SKILL = builder.nextAccessor("biped/pla/diamond_blaster_skill",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));
        EARTH_AXE_SHOOT = builder.nextAccessor("biped/pla/earth_axe_shoot",
                accessor -> new BasicAttackAnimation(0.1F, accessor, humanoidArmature,
                        new Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                        new Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AttackPhaseProperty.HIT_PRIORITY, Priority.TARGET)
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                        .addProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, staticAnimation, object) -> {
                                    Vec3 bladePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                            Armatures.BIPED.get().toolR, 0.5F, 0.0F);
                                    if (bladePos == null) return;
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        BlockPos liftPos = EarthAxeItem.findLiftableBlockUnderPoint(serverLevel, bladePos, 6, 1);
                                        if (liftPos != null) {
                                            EarthAxeItem.liftBlockAt(serverLevel, liftPos, livingEntity);
                                        }
                                    }
                                }, Side.SERVER)
                        )
        );
        RED_AXE_ATTACK = builder.nextAccessor("biped/pla/red_axe_attack",
                (accessor) -> (new BasicMultipleAttackAnimation(0.05F, 1.0F, 1.2F, 2.5F, WOMWeaponColliders.TORMENT_BERSERK_AIRSLAM, (humanoidArmature.get()).rootJoint, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.BYPASS_DODGE))
                        .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.GUARD_PUNCTURE))
                        .addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.35F, 0.9F))
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                        if (elapsedTime >= 0.9F && elapsedTime < 1.15F) {
                            float dpx = (float)(entitypatch.getOriginal()).getX();
                            float dpy = (float)(entitypatch.getOriginal()).getY();
                            float dpz = (float)(entitypatch.getOriginal()).getZ();

                            for(BlockState block = (entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                --dpy;
                            }

                            float distanceToGround = (float) org.joml.Math.max(org.joml.Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                            return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                        } else {
                            return speed;
                        }
                    }).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.35F, reascer.wom.gameasset.ReuseableEvents.AIRBURST_JUMP, Side.CLIENT), AnimationEvent.InTimeEvent.create(1.15F, reascer.wom.gameasset.ReuseableEvents.TORMENT_GROUNDSLAM, Side.CLIENT)
                    }));

        BLACKSCRATCHER_IDLE = builder.nextAccessor("biped/pla/blackscratcher_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        BLACKSCRATCHER_ATTACK = builder.nextAccessor("biped/pla/blackscratcher_attack.",
                accessor -> new BasicAttackAnimation(0.08F, 0.05F, 0.15F, 0.2F, null, humanoidArmature.get().toolR, accessor, humanoidArmature)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)));
    }

    static class ReuseableEvents {
        public static final AnimationEvent.E0 FAST_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);
        public static final AnimationEvent.E0 TRIDENT_SPINNING =
                (livingentitypatch, staticAnimation, aobject) -> livingentitypatch.getOriginal().level().playSound((Player) livingentitypatch.getOriginal(), livingentitypatch.getOriginal(), SoundEvents.TRIDENT_RETURN, SoundSource.NEUTRAL, 0.5F, 1.1F - (new Random().nextFloat() - 0.5F) * 0.2F);
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_LEFT =
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

                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    jointVec.x, jointVec.y, jointVec.z,
                                    1,
                                    0.0D, 0.0D, 0.0D,
                                    0.0D
                            );

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
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_WEAPON_RIGHT =
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

                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    jointVec.x, jointVec.y, jointVec.z,
                                    1,
                                    0.0D, 0.0D, 0.0D,
                                    0.0D
                            );

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
        public static final AnimationEvent.E0 PLAY_TRIDENT_EFFECT_HAND_RIGHT =
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

                            serverLevel.sendParticles(
                                    AnnoyingVillagersModParticleTypes.ELECTRIC_SPARK.get(),
                                    jointVec.x, jointVec.y, jointVec.z,
                                    1,
                                    0.0D, 0.0D, 0.0D,
                                    0.0D
                            );

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
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT =
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
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT =
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
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_LEFT_LIGHTNING =
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
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_LIGHTNING =
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
        public static final AnimationEvent.E0 THROW_TRIDENT_HAND_RIGHT_EXPLODE =
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
        public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_LEG_RIGHT =
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
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().legR);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().legR);
                        }
                    }
                };
        public static final AnimationEvent.E0 SUMMON_2_OBSIDIAN_LEG_LEFT =
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
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 2, Armatures.BIPED.get().legL);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 3, Armatures.BIPED.get().legL);
                        }
                    }
                };
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
        public static final AnimationEvent.E0 SUMMON_6_OBSIDIAN_LEG_RIGHT =
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
                            HerobrineUtil.summonObsidianBlocksInfrontOf(serverLevel, livingEntity, obsidian, 6, Armatures.BIPED.get().legR);
                        } else if (weapon instanceof ShadowObsidianPillarItem) {
                            HerobrineUtil.summonShadowObsidianShortPillarShootToward(serverLevel, livingEntity, 7, Armatures.BIPED.get().legR);
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
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
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
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
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
                        } else {
                            obsidian = AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK.get()
                                    .defaultBlockState()
                                    .setValue(ObsidianBlock.FROM_PLAYER, livingEntity instanceof Player);
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

        public static final AnimationEvent.E0 SLEDGEHAMMER_SHOOT =
                (livingEntityPatch, staticAnimation, object) -> {
                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                        LivingEntity shooterEntity = livingEntityPatch.getOriginal();

                        Vec3 aimPosition = null;

                        if (shooterEntity instanceof Mob mob && mob.getTarget() != null) {
                            aimPosition = mob.getTarget().getEyePosition(1.0F);
                        } else if (shooterEntity instanceof Player player) {
                            Vec3 playerEyePosition = player.getEyePosition(1.0F);
                            Vec3 playerLookDirection = player.getLookAngle();
                            double aimDistance = 64.0D;
                            aimPosition = playerEyePosition.add(playerLookDirection.scale(aimDistance));
                        }

                        ObsidianSledgehammerProjectileEntity obsidianSledgehammerProjectileEntity = new ObsidianSledgehammerProjectileEntity(AnnoyingVillagersModEntities.OBSIDIAN_SLEDGEHAMMER_PROJECTILE.get(), serverLevel);
                        Vec3 hammerPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                Armatures.BIPED.get().toolR, 1.0F, 0.0F);
                        if (hammerPos != null && aimPosition != null) {
                            obsidianSledgehammerProjectileEntity.moveTo(hammerPos.x, hammerPos.y, hammerPos.z, 0F, 0F);
                            obsidianSledgehammerProjectileEntity.setPosToAim(new Vec3(aimPosition.x, aimPosition.y, aimPosition.z));
                            obsidianSledgehammerProjectileEntity.setInvulnerable(true);
                            obsidianSledgehammerProjectileEntity.playSound(AnnoyingVillagersModSounds.METAL_HIT.get(), 1.0F, 1.0F);
                            obsidianSledgehammerProjectileEntity.setOwner(shooterEntity);
                            if (staticAnimation == AnimsWom.SLEDGEHAMMER_SOLAR_AUTO_3) {
                                obsidianSledgehammerProjectileEntity.setShouldStun(true);
                            }
                            serverLevel.addFreshEntity(obsidianSledgehammerProjectileEntity);
                        }
                    }
                };
        public static final AnimationEvent.E0 SHOCK_WAVE =
                (livingEntityPatch, staticAnimation, object) -> {

                    Vec3 legendarySwordPos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                            Armatures.BIPED.get().toolR, 1.5F, 0.0F);
                    final int MAX_SHOCKWAVE_RADIUS = 6;
                    final int TICKS_BETWEEN_LAYERS = 2;
                    for (int radius = 1; radius <= MAX_SHOCKWAVE_RADIUS; radius++) {
                        int delayTicks = (radius - 1) * TICKS_BETWEEN_LAYERS;
                        int ringRadius = radius;
                        if (legendarySwordPos == null) return;
                        BlockPos finalVec = BlockPos.containing(legendarySwordPos);
                        new DelayedTask(delayTicks) {
                            @Override
                            public void run() {
                                LegendarySwordItem.spawnCircleRing((ServerLevel) livingEntityPatch.getOriginal().level(), finalVec, ringRadius, livingEntityPatch.getOriginal());
                            }
                        };
                    }
                };

        public static final AnimationEvent.E0 END_ATTACK =
                (livingentitypatch, staticAnimation, object) -> {
                    if (livingentitypatch instanceof PlayerPatch) {
                        livingentitypatch.playAnimationSynchronized(AnimsPugilistSteve.DUAL_END, 0.1F);
                    }
                };

    }
}
