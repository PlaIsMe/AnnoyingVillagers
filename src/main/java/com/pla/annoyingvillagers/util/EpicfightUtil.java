package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.FlyingShockwaveProjectile;
import com.pla.annoyingvillagers.gameasset.AnimsPugilistSteve;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;

public class EpicfightUtil {
    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    public static boolean isLongHitAnimationNotExecutedAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return !(dynamicAnimation.get() instanceof ExecutionHitAnimation)
                && (dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFall.isEFNStun(dynamicAnimation))
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch));
    }

    public static boolean isLongHitAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return dynamicAnimation.get() instanceof ExecutionHitAnimation
                || dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFall.isEFNStun(dynamicAnimation))
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch);
    }

    public static boolean isDamagableHitAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return dynamicAnimation.get() instanceof ExecutionHitAnimation
                || dynamicAnimation.get() instanceof KnockdownAnimation
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch);
    }

    public static void stopAnimationSynchronized(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (entity == null || animation == null) {
            return;
        }

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) {
            return;
        }

        if (livingEntityPatch.isLogicalClient()) {
            livingEntityPatch.getAnimator().stopPlaying(animation);
        } else {
            livingEntityPatch.stopPlaying(animation);
        }
    }

    public static boolean isPlaying(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (entity == null || animation == null) {
            return false;
        }

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null || livingEntityPatch.getAnimator().getPlayerFor(null) == null) {
            return false;
        }

        return livingEntityPatch.getAnimator().getPlayerFor(null).getRealAnimation() == animation;
    }

    public static void cancel(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (isPlaying(entity, animation)) {
            stopAnimationSynchronized(entity, animation);
        }
    }

    public static void cancelLater(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation, int delayTicks) {
        if (entity == null || animation == null) {
            return;
        }

        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (entity.isRemoved() || !entity.isAlive()) {
                    return;
                }
                EpicfightUtil.cancel(entity, animation);
            }
        };
    }

    public static void dealStaminaDamageByPercentage(DamageSource damageSource, LivingEntityPatch<?> livingEntityPatch, double percentage, boolean playStunAnimation) {
        float decrease = 0.0F;
        if (livingEntityPatch instanceof CEHumanoidPatch) {
            float currentStamina = CEPatchUtils.getStamina(livingEntityPatch);
            float maxStamina = CEPatchUtils.getMaxStamina(livingEntityPatch);
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        } else if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            float currentStamina = playerPatch.getStamina();
            float maxStamina = playerPatch.getMaxStamina();
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        }
        dealStaminaDamage(damageSource, decrease, livingEntityPatch, playStunAnimation);
    }

    public static void dealStaminaDamage(DamageSource damageSource, float amount, LivingEntityPatch<?> livingEntityPatch, boolean playStunAnimation) {
        if (livingEntityPatch instanceof CEHumanoidPatch<?> ceHumanoidPatch) {
            if (!ceHumanoidPatch.dealStaminaDamage(damageSource, amount) && playStunAnimation) {
                livingEntityPatch.playAnimationSynchronized(AnimsPugilistSteve.GUARD_BREAK_ATTACK, 0.0F);
            }
        } else if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            float stamina = playerPatch.getStamina();
            playerPatch.setStamina(stamina - amount);
            if (amount >= stamina) {
                EpicFightDamageSource efSource = damageSource instanceof EpicFightDamageSource ? (EpicFightDamageSource)damageSource : null;
                if (efSource != null) {
                    efSource.setStunType(StunType.NONE);
                    Vec3 sourcePosition = efSource.getInitialPosition();
                    if (sourcePosition != null) {
                        playerPatch.getOriginal().lookAt(EntityAnchorArgument.Anchor.FEET, sourcePosition);
                    }
                }

                if (playerPatch.applyStun(StunType.NEUTRALIZE, 0.0F)) {
                    (playerPatch.getOriginal()).forceAddEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 100), playerPatch.getOriginal());
                    Vec3 eyePosition = (playerPatch.getOriginal()).getEyePosition();
                    Vec3 viewVec = (playerPatch.getOriginal()).getLookAngle().scale(2.0F);
                    Vec3 pos = new Vec3(eyePosition.x + viewVec.x, eyePosition.y + viewVec.y, eyePosition.z + viewVec.z);
                    (playerPatch.getOriginal()).level().addParticle(EpicFightParticles.NEUTRALIZE.get(), pos.x, pos.y, pos.z, (double)0.0F, (double)0.0F, (double)0.0F);
                    playerPatch.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 1.0F, 1.0F);
                }
            }
        }
    }

    public static void breakWeaponOnParryOpAttack(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (EscapeUtil.isAnimationDangerous(dynamicAnimation)) {
                    int breakValue = AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get();
                    if (ModList.get().isLoaded("efn")) {
                        if (EpicFightNightFall.isEfnWeapons(player.getMainHandItem())) {
                            breakValue = AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get() * EpicFightNightFall.MULTIPLIER_DAMAGE_VALUE;
                        }
                    }
                    player.getMainHandItem().hurtAndBreak(breakValue, player, (livingEntity) -> {
                        livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }
            }
        }
    }

    public static void damageBlocked(DamageSource damagesource, Entity livingentity, ServerLevel level) {
        if (livingentity == null) return;
        if (!damagesource.is(DamageTypes.IN_WALL) && !damagesource.is(DamageTypes.IN_FIRE) && !damagesource.is(DamageTypes.ON_FIRE)) {
            livingentity.playSound(EpicFightSounds.CLASH.get(), 1.0F, 1.0F);
        }
        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO,
                livingentity, damagesource.getEntity());
        if (damagesource.getEntity() instanceof Player player) {
            ScreenShakeUtil.applyScreenShake(level, player.getOnPos().getCenter(), 1.0, 20, 4);
        }
    }

    public static void damageBlockedForce(Entity defender, Entity attacker, ServerLevel level) {
        defender.playSound(EpicFightSounds.CLASH.get(), 1.0F, 1.0F);
        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO,
                defender, attacker);
        if (attacker instanceof Player player) {
            ScreenShakeUtil.applyScreenShake(level, player.getOnPos().getCenter(), 1.0, 20, 4);
        }
    }

    public static void shootFlyingShockwave(LivingEntityPatch<?> livingEntityPatch) {
        float ang = (float) ((livingEntityPatch.getYRot()+90)/180 * Math.PI);
        Vec3 shootVec = new Vec3(Math.cos(ang), 0 , Math.sin(ang));
        Vec3 shootPos = livingEntityPatch.getOriginal().position().add(shootVec.x, 0, shootVec.z);

        FlyingShockwaveProjectile projectile = AnnoyingVillagersModEntities.FLYING_SHOCKWAVE.get().create(livingEntityPatch.getOriginal().level());
        float multiplier = 1.5f;
        if (projectile != null)
        {
            projectile.setDamage((float) livingEntityPatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE) * multiplier);

            projectile.setPos(shootPos);
            projectile.setMaxStrikes(3);
            projectile.setOwner(livingEntityPatch.getOriginal());
            projectile.shoot(shootVec.x(), 0, shootVec.z(), 4.2f, 0);
            livingEntityPatch.getOriginal().level().addFreshEntity(projectile);
        }
    }
}
