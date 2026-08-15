package com.pla.annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import org.joml.Math;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.BasicMultipleAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.utils.math.Vec3f;

import java.util.Random;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsEnderAegis {
    public static AnimationManager.AnimationAccessor<StaticAnimation> ENDER_AEGIS_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ENDER_AEGIS_GUARD;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_AUTO5;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_DASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicMultipleAttackAnimation> ENDER_AEGIS_SPECIAL;
    public static AnimationManager.AnimationAccessor<ActionAnimation> ENDER_AEGIS_INNATE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> ENDER_AEGIS_INNATE_OFFHAND;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        ENDER_AEGIS_IDLE = builder.nextAccessor("biped/ender_aegis/ender_aegis_idle",
                accessor -> new StaticAnimation(0.35F, true, accessor, humanoidArmature));

        ENDER_AEGIS_GUARD = builder.nextAccessor("biped/ender_aegis/ender_aegis_guard", (accessor) -> new StaticAnimation(0.3F, true, accessor, humanoidArmature));

        ENDER_AEGIS_AUTO1 = builder.nextAccessor("biped/ender_aegis/ender_aegis_auto1",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.GESETZ)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.0F));

        ENDER_AEGIS_AUTO2 = builder.nextAccessor("biped/ender_aegis/ender_aegis_auto2",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.2F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.GESETZ_INSET_LARGE)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F)
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.3F, (entitypatch, self, params) -> entitypatch.getOriginal().level().playSound(null, entitypatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - ((new Random()).nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT)}));

        ENDER_AEGIS_AUTO3 = builder.nextAccessor("biped/ender_aegis/ender_aegis_auto3",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.2F, 0.45F, 0.6F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.GESETZ_KRUMMEN)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.33F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(5.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F));

        ENDER_AEGIS_AUTO4 = builder.nextAccessor("biped/ender_aegis/ender_aegis_auto4",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.15F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.5F, 0.55F, 0.55F, InteractionHand.MAIN_HAND, humanoidArmature.get().handR, WOMWeaponColliders.PUNCH), new AttackAnimation.Phase(0.55F, 0.7F, 0.85F, 1.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, WOMWeaponColliders.GESETZ)))
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
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.8F,
                                (livingEntityPatch, self, params) ->
                                        livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - ((new Random()).nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT)
                        }));

        ENDER_AEGIS_AUTO5 = builder.nextAccessor("biped/ender_aegis/ender_aegis_auto5",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 1.05F, 1.15F, 2.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_GUILLOTINE)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(20.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(1.05F, ReuseableEvents.BODY_BIG_GROUNDSLAM, AnimationEvent.Side.CLIENT), 
                                AnimationEvent.InTimeEvent.create(0.55F, 
                                        (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.MASTER, 1.0F, 0.6F - ((new Random()).nextFloat() - 0.5F) * 0.1F), AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.95F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.MASTER, 1.0F, 0.9F - ((new Random()).nextFloat() - 0.5F) * 0.1F), AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(1.1F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.4F, 0.7F - ((new Random()).nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT)}));

        ENDER_AEGIS_DASH = builder.nextAccessor("biped/ender_aegis/ender_aegis_dash",
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
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
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

        ENDER_AEGIS_AIRSLASH = builder.nextAccessor("biped/ender_aegis/ender_aegis_airslash",
                (accessor) -> (BasicMultipleAttackAnimation)(new BasicMultipleAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.65F, 0.75F, 1.65F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, humanoidArmature.get().rootJoint, WOMWeaponColliders.ANTITHEUS_GUILLOTINE)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(20.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_BIG.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT_HARD.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0.0F, 0.35F))
                        .newTimePair(0.0F, 1.25F)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                            if (elapsedTime >= 0.55F && elapsedTime < 0.65F) {
                                float dpx = (float) entitypatch.getOriginal().getX();
                                float dpy = (float) entitypatch.getOriginal().getY();
                                float dpz = (float) entitypatch.getOriginal().getZ();

                                for(BlockState block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz)); (block.getBlock() instanceof BushBlock || block.isAir()) && !block.is(Blocks.VOID_AIR); block = entitypatch.getOriginal().level().getBlockState(new BlockPos.MutableBlockPos(dpx, dpy, dpz))) {
                                    --dpy;
                                }

                                float distanceToGround = (float) Math.max(Math.abs(entitypatch.getOriginal().getY() - (double)dpy) - (double)1.0F, 0.0F);
                                return 1.0F - (1.0F / (-distanceToGround - 1.0F) + 1.0F);
                            } else {
                                return speed;
                            }
                        })
                        .addEvents(new AnimationEvent[]{
                                AnimationEvent.InTimeEvent.create(0.65F, ReuseableEvents.BODY_BIG_GROUNDSLAM, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.35F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), EpicFightSounds.CLASH.get(), SoundSource.MASTER, 0.3F, 1.2F - ((new Random()).nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(2.65F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.MASTER, 1.0F, 0.6F - ((new Random()).nextFloat() - 0.5F) * 0.1F), AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(3.25F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), EpicFightSounds.WHOOSH.get(), SoundSource.MASTER, 1.0F, 0.9F - ((new Random()).nextFloat() - 0.5F) * 0.1F), AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(3.45F, (livingEntityPatch, self, params) -> livingEntityPatch.getOriginal().level().playSound(null, livingEntityPatch.getOriginal(), SoundEvents.ANVIL_LAND, SoundSource.MASTER, 0.3F, 1.2F - ((new Random()).nextFloat() - 0.5F) * 0.2F), AnimationEvent.Side.CLIENT)
                        }));

        ENDER_AEGIS_SPECIAL = builder.nextAccessor("biped/ender_aegis/ender_aegis_special",
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


        ENDER_AEGIS_INNATE = builder.nextAccessor("biped/ender_aegis/ender_aegis_innate",
                accessor -> new ActionAnimation(0.35F, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    EnderAegisItem.shieldShoot(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal());
                                }, AnimationEvent.Side.SERVER)
                        ));

        ENDER_AEGIS_INNATE_OFFHAND = builder.nextAccessor("biped/ender_aegis/ender_aegis_innate_offhand",
                accessor -> new ActionAnimation(0.35F, accessor, humanoidArmature)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.1F, (livingEntityPatch, self, p) -> {
                                    EnderAegisItem.shieldShoot(livingEntityPatch.getOriginal().level(), livingEntityPatch.getOriginal());
                                }, AnimationEvent.Side.SERVER)
                        ));
    }
}