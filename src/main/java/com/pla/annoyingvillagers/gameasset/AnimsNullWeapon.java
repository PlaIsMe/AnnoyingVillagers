package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.attacks.AntitheusShootAttackAnimation;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.WOMSounds;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.MovementAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsNullWeapon {
    public static AnimationManager.AnimationAccessor<StaticAnimation> NULL_WEAPON_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> NULL_WEAPON_RUN;
    public static AnimationManager.AnimationAccessor<MovementAnimation> NULL_WEAPON_WALK;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_WEAPON_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_WEAPON_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_WEAPON_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_WEAPON_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_WEAPON_AUTO5;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_WEAPON_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> NULL_WEAPON_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AntitheusShootAttackAnimation> NULL_WEAPON_SPECIAL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_WEAPON_INNATE_SPECIAL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> NULL_WEAPON_SKELETON_SPAWN;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        NULL_WEAPON_IDLE = builder.nextAccessor("biped/null_weapon/null_weapon_idle",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature));

        NULL_WEAPON_RUN = builder.nextAccessor("biped/null_weapon/null_weapon_run", (accessor) -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));

        NULL_WEAPON_WALK = builder.nextAccessor("biped/null_weapon/null_weapon_walk", (accessor) -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));

        NULL_WEAPON_AUTO1 = builder.nextAccessor("biped/null_weapon/null_weapon_auto1",
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

        NULL_WEAPON_AUTO2 = builder.nextAccessor("biped/null_weapon/null_weapon_auto2",
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

        NULL_WEAPON_AUTO3 = builder.nextAccessor("biped/null_weapon/null_weapon_auto3",
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

        NULL_WEAPON_AUTO4 = builder.nextAccessor("biped/null_weapon/null_weapon_auto4",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.267F, 0.267F, 0.267F, InteractionHand.MAIN_HAND, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL),
                        (new AttackAnimation.Phase(0.267F, 0.9F, 0.9F, 1.0F, 1.0F, 1.0F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F)),
                        (new AttackAnimation.Phase(1.0F, 1.0F, 1.0F, 1.1F, 1.1F, 1.1F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F)),
                        (new AttackAnimation.Phase(1.1F, 1.1F, 1.1F, 1.2F, 1.2F, 1.2F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F)),
                        (new AttackAnimation.Phase(1.2F, 1.2F, 1.2F, 1.33F, 2.67F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES)))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(40.0F))))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(DamageTypeTags.NO_IMPACT))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.1F, 0.9F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.MOVE_VERTICAL, true)
                        .newTimePair(0.0F, 1.8F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 2.0F)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .newTimePair(0.0F, Float.MAX_VALUE).addState(EntityState.ATTACK_RESULT, (damagesource) -> {
                            if (damagesource instanceof EpicFightDamageSource epicfightdamagesource) {
                                if (epicfightdamagesource.getStunType() != StunType.NEUTRALIZE) {
                                    epicfightdamagesource.setStunType(StunType.NONE);
                                }
                            }
                            return damagesource.is(DamageTypes.FALL) ? AttackResult.ResultType.MISSED : AttackResult.ResultType.SUCCESS;
                        }));

        NULL_WEAPON_AUTO5 = builder.nextAccessor("biped/null_weapon/null_weapon_auto5", accessor -> new BasicMultipleAttackAnimation(0.05F, 1.45F, 1.5F, 1.7F, WOMWeaponColliders.PLUNDER_PERDITION, humanoidArmature.get().rootJoint, accessor, humanoidArmature)
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

        NULL_WEAPON_DASH = builder.nextAccessor("biped/null_weapon/null_weapon_dash",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        (new AttackAnimation.Phase(0.0F, 0.267F, 0.267F, 0.43F, 0.43F, 0.43F, InteractionHand.MAIN_HAND, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        (new AttackAnimation.Phase(0.43F, 0.43F, 0.467F, 0.63F, 1.33F, Float.MAX_VALUE, InteractionHand.OFF_HAND, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_ASCENDED_PUNCHES))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.4F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.REACH, 0.3F).newTimePair(0.0F, 0.63F)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .newTimePair(0.0F, 0.63F).addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false));

        NULL_WEAPON_AIRSLASH = builder.nextAccessor("biped/null_weapon/null_weapon_airslash",
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

        NULL_WEAPON_SPECIAL = builder.nextAccessor("biped/null_weapon/null_weapon_special",
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

        NULL_WEAPON_INNATE_SPECIAL = builder.nextAccessor("biped/null_weapon/null_weapon_innate_special",
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

                                        Vec3 forward = new Vec3(owner.getLookAngle().x, 0.0D, owner.getLookAngle().z);
                                        if (forward.lengthSqr() < 1.0E-6D) {
                                            float yawRad = (float) Math.toRadians(owner.getYRot());
                                            forward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
                                        }
                                        forward = forward.normalize();

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
                                            nullSkeletonPatch.playAnimationSynchronized(NULL_WEAPON_SKELETON_SPAWN, 0.0F);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER)
                        }));

        NULL_WEAPON_SKELETON_SPAWN = builder.nextAccessor("biped/null_weapon/null_weapon_skeleton_spawn",
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
    }
}
