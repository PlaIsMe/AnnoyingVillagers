package com.pla.annoyingvillagers.gameasset;

import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.registries.EFNMobEffectRegistry;
import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.avalon.util.AvalonAnimationUtils;
import com.merlin204.avalon.util.AvalonEventUtils;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.animation.WomAnimationProperty;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.ReuseableEvents;
import reascer.wom.gameasset.colliders.WOMWeaponColliders;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.Set;

import static com.hm.efn.gameasset.animations.EFNGreatSwordAnimations.GREATSWORD_CLASH_HIT_FIRST;
import static com.hm.efn.gameasset.animations.EFNGreatSwordAnimations.GREATSWORD_CLASH_HIT_SECOND;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsAVTachi {
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> AV_TACHI_AUTO4;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> AV_TACHI_AUTO5;
    public static AnimationManager.AnimationAccessor<SpecialAttackAnimation> AV_TACHI_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> AV_TACHI_SPECIAL;
    public static AnimationManager.AnimationAccessor<AvalonAttackAnimation> AV_TACHI_INNATE;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        AV_TACHI_AUTO4 = builder.nextAccessor("biped/av_tachi/av_tachi_auto4",
                (accessor) -> (SpecialAttackAnimation)(new SpecialAttackAnimation(0.25F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.75F, 0.75F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(0.5F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> speed * 1.35F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F));

        AV_TACHI_AUTO5 = builder.nextAccessor("biped/av_tachi/av_tachi_auto5",
                (accessor) -> (SpecialAttackAnimation)(new SpecialAttackAnimation(0.25F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.3F, 0.75F, 0.75F, Float.MAX_VALUE, humanoidArmature.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(3.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(0.5F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.1F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> speed * 1.35F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F));

        AV_TACHI_AIRSLASH = builder.nextAccessor("biped/av_tachi/av_tachi_airslash",
                (accessor) -> (SpecialAttackAnimation)(new SpecialAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.6F, 0.75F, 1.0F, Float.MAX_VALUE, humanoidArmature.get().rootJoint, WOMWeaponColliders.RUINE_REDEMPTION)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_HIT.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(0.5F)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, null)
                        .addProperty(WomAnimationProperty.ANTI_STUN_MULTIPLYER, 0.4F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .newTimePair(0.0F, 3.0F)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.INACTION, true)
                        .addEvents(AnimationEvent.InPeriodEvent.create(0.0F, 3.0F, (entitypatch, self, params) -> {
                            entitypatch.getOriginal().resetFallDistance();
                            if (entitypatch.getOriginal() instanceof Player player) {
                                player.yCloak = 0.0F;
                                player.yCloakO = 0.0F;
                            }

                        }, AnimationEvent.Side.BOTH))
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.05F, (entitypatch, self, params) -> {
                            LivingEntity entity = entitypatch.getOriginal();
                            entity.level().playSound(null, entity.xo, entity.yo + (double)1.0F, entity.zo, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 2.0F, 1.0F - ((new Random()).nextFloat() - 0.5F) * 0.2F);

                        }, AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.05F, (entitypatch, self, params) -> {
                            LivingEntity entity = entitypatch.getOriginal();
                            entity.level().addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);

                        }, AnimationEvent.Side.CLIENT), AnimationEvent.InTimeEvent.create(0.75F, (entitypatch, self, params) -> {
                            Vec3 bodyFloorPos = ReuseableEvents.getfloor(entitypatch, self.get(), new Vec3f(0.0F, 0.0F, 0.0F), Armatures.BIPED.get().rootJoint);
                            entitypatch.getOriginal().teleportTo(bodyFloorPos.x, (int)bodyFloorPos.y + 1, bodyFloorPos.z);
                        }, AnimationEvent.Side.SERVER)}));

        AV_TACHI_SPECIAL = builder.nextAccessor("biped/av_tachi/av_tachi_special",
                (accessor) -> (AvalonAttackAnimation) (new AvalonAttackAnimation(
                        0.1F, accessor, Armatures.BIPED, 1.0F, 1.0F, AvalonAnimationUtils.createSimplePhase(38, 48, 55, InteractionHand.MAIN_HAND, 1.7F, 1.7F, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH_SHARP.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_TACHI)
        );

        AV_TACHI_INNATE = builder.nextAccessor("biped/av_tachi/av_tachi_innate",
                (accessor) -> (AvalonAttackAnimation)(new AvalonAttackAnimation(0.1F, accessor, Armatures.BIPED, 1.0F, 1.0F, AvalonAnimationUtils.createSimplePhase(31, 39, 79, InteractionHand.MAIN_HAND, 0.8F, 0.8F, Armatures.BIPED.get().toolR, GREATSWORD_CLASH_HIT_FIRST), AvalonAnimationUtils.createSimplePhase(79, 85, 120, InteractionHand.MAIN_HAND, 0.8F, 0.8F, Armatures.BIPED.get().rootJoint, GREATSWORD_CLASH_HIT_SECOND)))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLADE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, EFNAnimations.ATTACK_SPEED_CAP_RUIN)
                        .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(0.6F, (entitypatch, self, params) -> {
                            if (!entitypatch.getOriginal().level().isClientSide()) {
                                LivingEntity attacker = entitypatch.getOriginal();
                                ServerLevel level = (ServerLevel)attacker.level();
                                double centerX = attacker.getX();
                                double centerY = attacker.getY();
                                double centerZ = attacker.getZ();
                                double baseRadius = 8.0F;
                                double maxRadius = 15.0F;
                                int waveCount = 3;
                                int particlesPerWave = 80;
                                double speed = 0.4;

                                for(int wave = 0; wave < waveCount; ++wave) {
                                    double radius = baseRadius + (maxRadius - baseRadius) * (double)wave / (double)(waveCount - 1);

                                    for(int i = 0; i < particlesPerWave; ++i) {
                                        double angle = (Math.PI * 2D) * (double)i / (double)particlesPerWave;
                                        double randomOffset = 0.3 * (level.random.nextDouble() - (double)0.5F);
                                        double xOffset = radius * Math.cos(angle) + randomOffset;
                                        double zOffset = radius * Math.sin(angle) + randomOffset;
                                        double motionX = xOffset * speed / radius;
                                        double motionZ = zOffset * speed / radius;
                                        double yOffset = (double)0.5F * Math.sin(angle * (double)2.0F + (double)wave * (double)0.5F);
                                        level.sendParticles(ParticleTypes.SMOKE, centerX + xOffset, centerY + 0.1 + yOffset, centerZ + zOffset, 1, motionX, 0.05, motionZ, 0.8);
                                    }
                                }

                                level.sendParticles(ParticleTypes.END_ROD, centerX, centerY + (double)0.5F, centerZ, 50, 1.5F, 0.5F, 1.5F, 0.7);
                            }

                        }, AnimationEvent.Side.SERVER), AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) -> entitypatch.getOriginal().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 50, 2, false, false, false)), AnimationEvent.Side.BOTH), AnimationEvent.InTimeEvent.create(0.0F, (entitypatch, self, params) -> entitypatch.getOriginal().addEffect(new MobEffectInstance(EFNMobEffectRegistry.SIN_STUN_IMMUNITY.get(), 50, 10, false, false, false)), AnimationEvent.Side.BOTH), AnimationEvent.InTimeEvent.create(0.3F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, 0.3F, 0.0F), Armatures.BIPED.get().toolR, (double)2.0F, 0.5F), AnimationEvent.InTimeEvent.create(1.4F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0F, 0.3F, 0.0F), Armatures.BIPED.get().rootJoint, (double)4.0F, 0.55F), AvalonEventUtils.simpleCameraShake(80, 40, 4.0F, 4.0F, 4.0F)}));
    }
}
