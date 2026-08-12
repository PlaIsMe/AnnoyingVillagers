package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.gameasset.animations.EFNGreatSwordAnimations;
import com.hm.efn.registries.EFNMobEffectRegistry;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.merlin204.avalon.util.AvalonEventUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.animations.HeavyAttackAnimation;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.skill.LegendarySwordSkill;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.animation.attacks.UltimateAttackAnimation;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsLegendarySword {
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> LEGENDARY_SWORD_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LEGENDARY_SWORD_GUARD;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> LEGENDARY_SWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> LEGENDARY_SWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> LEGENDARY_SWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> LEGENDARY_SWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> LEGENDARY_SWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> LEGENDARY_SWORD_DASH;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> LEGENDARY_SWORD_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> LEGENDARY_SWORD_WOOPIE_AUTO1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> LEGENDARY_SWORD_SPECIAL;
    public static AnimationManager.AnimationAccessor<HeavyAttackAnimation> LEGENDARY_SWORD_INNATE;
    public static AnimationManager.AnimationAccessor<UltimateAttackAnimation> LEGENDARY_SWORD_INNATE_SPECIAL;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> LEGENDARY_SWORD_KNOCKDOWN;

    private static final float AWAKENED_PLAY_SPEED_MULTIPLIER = 1.5F;

    private static float legendarySwordAttackSpeed(DynamicAnimation self, LivingEntityPatch<?> livingEntityPatch, float speed, float prevElapsedTime, float elapsedTime) {
        float playSpeed = EFNAnimations.ATTACK_SPEED_CAP_RUIN.modify(self, livingEntityPatch, speed, prevElapsedTime, elapsedTime);
        if (livingEntityPatch.getOriginal() instanceof Player player
                && LegendarySwordSkill.isAwakened(player.getMainHandItem(), player.level())) {
            return playSpeed * AWAKENED_PLAY_SPEED_MULTIPLIER;
        }

        return playSpeed;
    }

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        LEGENDARY_SWORD_IDLE = builder.nextAccessor("biped/legendary_sword/legendary_sword_idle",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        LEGENDARY_SWORD_RUN = builder.nextAccessor("biped/legendary_sword/legendary_sword_run",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));

        LEGENDARY_SWORD_GUARD = builder.nextAccessor("biped/legendary_sword/legendary_sword_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));

        LEGENDARY_SWORD_AUTO1 = builder.nextAccessor("biped/legendary_sword/legendary_sword_auto1", (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.6F, 0.8F, 0.9F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5.0F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, AnimsLegendarySword::legendarySwordAttackSpeed));

        LEGENDARY_SWORD_AUTO2 = builder.nextAccessor("biped/legendary_sword/legendary_sword_auto2", (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.5F, 0.83F, 0.93F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5.0F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, AnimsLegendarySword::legendarySwordAttackSpeed));

        LEGENDARY_SWORD_AUTO3 = builder.nextAccessor("biped/legendary_sword/legendary_sword_auto3", (accessor) -> (BasicAttackAnimation)(new BasicAttackAnimation(0.1F, 0.45F, 0.75F, 1.2F, null, Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(5.0F))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, AnimsLegendarySword::legendarySwordAttackSpeed));

        LEGENDARY_SWORD_AUTO4 = builder.nextAccessor("biped/legendary_sword/legendary_sword_auto4", (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, 0.25F, 0.4F, 1.0F, null, humanoidArmature.get().toolR, accessor, humanoidArmature))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.15F, 0.65F))
                .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));

        LEGENDARY_SWORD_AUTO5 = builder.nextAccessor("biped/legendary_sword/legendary_sword_auto5", (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.1F, accessor, Armatures.BIPED, 0.5F, 2.0F, AvalonAnimationUtils.createSimplePhase(7, 15, 40, InteractionHand.MAIN_HAND, 2.0F, 2.0F, Armatures.BIPED.get().toolR, EFNGreatSwordAnimations.GREATSWORD_AIRSLASH_SECOND)))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN)
                .addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) ->
                                entitypatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2, false, false, false)), AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) ->
                                entitypatch.getOriginal().addEffect(new MobEffectInstance(EFNMobEffectRegistry.SIN_STUN_IMMUNITY.get(), 30, 10, false, false, false)), AnimationEvent.Side.BOTH),
                        AvalonEventUtils.simpleCameraShake(7, 20, 2.0F, 2.0F, 2.0F), AvalonEventUtils.simpleGroundSplit(7, 0.0F, 0.0F, 0.0F, 0.0F, 2.5F, true)})
        );

        LEGENDARY_SWORD_DASH = builder.nextAccessor("biped/legendary_sword/legendary_sword_dash",
        accessor -> new SpecialAttackAnimation(0.05F, accessor, humanoidArmature,
                new AttackAnimation.Phase(0.0F, 0.15F, 0.4F, 0.41F, 0.41F, humanoidArmature.get().toolR, null),
                new AttackAnimation.Phase(0.41F, 0.5F, 0.55F, 0.6F, 0.65F, humanoidArmature.get().toolR, null))
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
                                }, AnimationEvent.Side.CLIENT)
                        })
        );

        LEGENDARY_SWORD_AIRSLASH = builder.nextAccessor("biped/legendary_sword/legendary_sword_airslash",
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
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, true)
                        .newTimePair(0.0F, 0.35F)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.55F, 1.1F)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false));

        LEGENDARY_SWORD_WOOPIE_AUTO1 = builder.nextAccessor("biped/legendary_sword/legendary_sword_woopie_auto1",
                accessor -> new BasicAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, humanoidArmature.get().toolL, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));

        LEGENDARY_SWORD_SPECIAL = builder.nextAccessor("biped/legendary_sword/legendary_sword_special", (animationaccessor) -> (AttackAnimation) (new AttackAnimation(0.1F, 0.0F, 0.5F, 0.76F, 1.36F, null, Armatures.BIPED.get().toolR, animationaccessor, Armatures.BIPED))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,
                        (self, livingEntityPatch, speed, prevElapsedTime, elapsedTime) -> 1.0F)
                .newTimePair(0.0F, 0.3F)
                .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                .newTimePair(0.53F, Float.MAX_VALUE)
                .addState(EntityState.TURNING_LOCKED, true)
                .newTimePair(0.0F, 1.16F)
                .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                .newTimePair(0.0F, 0.0F)
                .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        LEGENDARY_SWORD_INNATE = builder.nextAccessor("biped/legendary_sword/legendary_sword_innate",
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

                                float distance = (float) java.lang.Math.max(java.lang.Math.abs(livingEntityPatch.getOriginal().getY() - y) - 1.0D, 0.0D);
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

        LEGENDARY_SWORD_INNATE_SPECIAL = builder.nextAccessor("biped/legendary_sword/legendary_sword_innate_special", (accessor) -> (UltimateAttackAnimation)(new UltimateAttackAnimation(0.2F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.55F, 0.65F, 0.75F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.SOLAR_INFIERNO)))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, WOMParticles.SOLAR_POLVORA_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, WOMSounds.SOLAR_HIT.get())
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.55F, (entitypatch, self, params) -> {
                    if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.LEGENDARY_SWORD);
                        if (skillContainer == null || skillContainer.getStack() < 1
                                || !(skillContainer.getSkill() instanceof LegendarySwordSkill legendarySwordSkill)
                                || LegendarySwordSkill.isAwakened(skillContainer)) {
                            return;
                        }
                        legendarySwordSkill.getResourceType().consumer
                                .consume(skillContainer, serverPlayerPatch, legendarySwordSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                        legendarySwordSkill.startAwakening(skillContainer);
                    }
                }, AnimationEvent.Side.SERVER)}));

        LEGENDARY_SWORD_KNOCKDOWN = builder.nextAccessor("biped/legendary_sword/legendary_sword_knockdown",
                accessor -> new KnockdownAnimation(0.2F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
    }
}
