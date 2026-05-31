package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.combatbehaviour.CombatCommon;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.PlayerNpcEntity;
import com.pla.annoyingvillagers.entity.SteveEntity;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.Objects;
import java.util.Random;

public class CombatBehaviour {
    public static final ItemStack HOSTILE_HEALING_POTION;
    public static final ItemStack HEALING_POTION;

    static {
        HOSTILE_HEALING_POTION = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HARMING);
        HOSTILE_HEALING_POTION.setCount(1);
        HEALING_POTION = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.STRONG_HEALING);
        HEALING_POTION.setCount(1);
    }

    private static Vec3 getFrontLeftPos(Entity entity) {
        Vec3 base = (entity instanceof LivingEntity le)
                ? le.getEyePosition(1.0F)
                : entity.position().add(0.0, entity.getBbHeight() * 0.85, 0.0);

        base = base.add(0.0, -0.1, 0.0);

        Vec3 forward = entity.getLookAngle();
        Vec3 forwardH = new Vec3(forward.x, 0.0, forward.z);
        if (forwardH.lengthSqr() < 1.0E-6) {
            forwardH = entity.getForward();
            forwardH = new Vec3(forwardH.x, 0.0, forwardH.z);
        }
        forwardH = forwardH.normalize();

        Vec3 left = new Vec3(0.0, 1.0, 0.0).cross(forwardH);
        if (left.lengthSqr() < 1.0E-6) {
            left = new Vec3(1.0, 0.0, 0.0);
        } else {
            left = left.normalize();
        }

        return base.add(forwardH.scale(0.35)).add(left.scale(0.25));
    }

    public static void throwEnderPearl(Entity entity, float xRot) {
        if (xRot != 0.0F) {
            entity.setYRot(0.0F);
            entity.setXRot(xRot);
            entity.setYBodyRot(entity.getYRot());
            entity.setYHeadRot(entity.getYRot());
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.yBodyRotO = livingEntity.getYRot();
            livingEntity.yHeadRotO = livingEntity.getYRot();
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            new DelayedTask(5) {
                @Override
                public void run() {
                    Vec3 handPos = getFrontLeftPos(entity);
                    Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, serverLevel);
                    projectile.setOwner(entity);
                    projectile.setPos(handPos.x, handPos.y, handPos.z);
                    projectile.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, new Random().nextBoolean() ? 1.0F : 2.0F, 0.0F);
                    serverLevel.addFreshEntity(projectile);
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                }
            };

        }
    }

    private static void recoverItemDueToFailure(Entity entity) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            playerNpcEntity.setItemInHand(InteractionHand.MAIN_HAND, playerNpcEntity.getMainWeaponItem().copy());
            playerNpcEntity.setHealing(false);
            playerNpcEntity.resetGapCooldown();
        }
        if (entity instanceof AVNpc AVNpc) {
            AVNpc.setItemInHand(InteractionHand.MAIN_HAND, AVNpc.getMainWeaponItem().copy());
            AVNpc.setHealing(false);
            AVNpc.resetGapCooldown();
        }
    }

    private static boolean isTrackedHealingCancelled(Entity entity) {
        if (entity instanceof PlayerNpcEntity playerNpcEntity) {
            return !playerNpcEntity.isHealing();
        }
        if (entity instanceof AVNpc AVNpc) {
            return !AVNpc.isHealing();
        }
        return false;
    }

    private static boolean trackedNpcHoldsMainHandItem(Entity entity, Item expectedItem) {
        if (!(entity instanceof PlayerNpcEntity || entity instanceof AVNpc)) {
            return true;
        }
        return entity instanceof LivingEntity livingEntity
                && livingEntity.getMainHandItem().getItem().equals(expectedItem);
    }

    private static void performEatingGoldenAppleActionMainHand(Entity entity,
                                                                LevelAccessor levelaccessor,
                                                                LivingEntityPatch<?> livingEntityPatch,
                                                                boolean isEnchanted) {
        if (isTrackedHealingCancelled(entity)) {
            return;
        }
        if (!trackedNpcHoldsMainHandItem(entity, isEnchanted ? Items.ENCHANTED_GOLDEN_APPLE : Items.GOLDEN_APPLE)) {
            recoverItemDueToFailure(entity);
            return;
        }

        AssetAccessor<? extends StaticAnimation> currentAnim = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (currentAnim.get() instanceof AttackAnimation
                || EpicfightUtil.isLongHitAnimation(currentAnim, livingEntityPatch)
                || CombatCommon.canEscape((MobPatch<?>) livingEntityPatch)) {
            recoverItemDueToFailure(entity);
            return;
        }

        if (!entity.level().isClientSide() && entity.getServer() != null && entity instanceof LivingEntity livingEntity) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_EAT, 0.0F);
        }

        if (levelaccessor instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null,
                    entity.blockPosition(),
                    SoundEvents.GENERIC_EAT,
                    SoundSource.NEUTRAL,
                    1.0F, 1.0F);
        }

        if (entity.level() instanceof ServerLevel serverLevel) {
            Vec3 forward = entity.getViewVector(1.0F);
            Vec3 up = entity.getUpVector(1.0F);
            Vec3 left = up.cross(forward).normalize();
            Vec3 spawnPos = entity.position()
                    .add(left.scale(0.0D))
                    .add(up.scale(1.5D))
                    .add(forward.scale(0.5D));

            serverLevel.sendParticles(
                    new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.GOLDEN_APPLE)),
                    spawnPos.x, spawnPos.y, spawnPos.z,
                    10,
                    0.0D, 0.0D, 0.0D,
                    0.01D
            );
        }
    }

    private static void performDrinkingHealingPotionActionMainhand(Entity entity,
                                                                    LevelAccessor levelaccessor,
                                                                    LivingEntityPatch<?> livingEntityPatch) {
        if (isTrackedHealingCancelled(entity)) {
            return;
        }
        if (!trackedNpcHoldsMainHandItem(entity, Items.POTION)) {
            recoverItemDueToFailure(entity);
            return;
        }

        AssetAccessor<? extends StaticAnimation> currentAnim = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (currentAnim.get() instanceof AttackAnimation
                || EpicfightUtil.isLongHitAnimation(currentAnim, livingEntityPatch)
                || CombatCommon.canEscape((MobPatch<?>) livingEntityPatch)) {
            recoverItemDueToFailure(entity);
            return;
        }

        if (!entity.level().isClientSide() && entity.getServer() != null && entity instanceof LivingEntity livingEntity) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_DRINK, 0.0F);
        }

        if (levelaccessor instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null,
                    entity.blockPosition(),
                    SoundEvents.GENERIC_DRINK,
                    SoundSource.NEUTRAL,
                    1.0F, 1.0F);
        }
    }

    public static void eatingGoldenApple(Entity entity, LevelAccessor levelaccessor, double amount, boolean isEnchanted) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

        if (livingEntityPatch != null && entity instanceof LivingEntity livingEntity) {
            if (entity instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isHealing()) {
                return;
            }
            if (entity instanceof AVNpc AVNpc && AVNpc.isHealing()) {
                return;
            }
            livingEntity.addEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (amount * 2.0D), 2, false, false)
            );
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                if (playerNpcEntity.isHealing()) {
                    return;
                } else {
                    playerNpcEntity.setHealing(true);
                }
            }
            if (entity instanceof AVNpc AVNpc) {
                if (AVNpc.isHealing()) {
                    return;
                } else {
                    AVNpc.setHealing(true);
                }
            }
            new DelayedTask(20) {
                @Override
                public void run() {
                    if (!entity.isAlive()) return;
                    if (isTrackedHealingCancelled(entity)) return;
                    if (!trackedNpcHoldsMainHandItem(entity, isEnchanted ? Items.ENCHANTED_GOLDEN_APPLE : Items.GOLDEN_APPLE)) {
                        recoverItemDueToFailure(entity);
                        return;
                    }

                    LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (patch == null) {
                        recoverItemDueToFailure(entity);
                        return;
                    }
                    AssetAccessor<? extends StaticAnimation> currentAnim = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
                    if (currentAnim.get() instanceof AttackAnimation
                            || EpicfightUtil.isLongHitAnimation(currentAnim, patch)
                            || CombatCommon.canEscape((MobPatch<?>) livingEntityPatch)) {
                        recoverItemDueToFailure(entity);
                        return;
                    }
                    Runnable bite = () -> performEatingGoldenAppleActionMainHand(entity, levelaccessor, patch, isEnchanted);
                    int biteDelay = 4;
                    int totalBites = 7;

                    for (int i = 0; i < totalBites; i++) {
                        int delay = 4 + i * biteDelay;
                        new DelayedTask(delay) {
                            @Override
                            public void run() {
                                if (entity.isAlive()) {
                                    bite.run();
                                }
                            }
                        };
                    }

                    new DelayedTask(4 + totalBites * biteDelay - 1) {
                        @Override
                        public void run() {
                            if (!entity.isAlive()) return;
                            if (isTrackedHealingCancelled(entity)) return;
                            if (!trackedNpcHoldsMainHandItem(entity, isEnchanted ? Items.ENCHANTED_GOLDEN_APPLE : Items.GOLDEN_APPLE)) {
                                recoverItemDueToFailure(entity);
                                return;
                            }
                            if (levelaccessor instanceof ServerLevel serverLevel) {
                                serverLevel.playSound(null,
                                        entity.blockPosition(),
                                        SoundEvents.PLAYER_BURP,
                                        SoundSource.NEUTRAL,
                                        1.5F, 1.0F);
                            }
                        }
                    };
                    new DelayedTask(4 + totalBites * biteDelay) {
                        @Override
                        public void run() {
                            if (!entity.isAlive()) return;
                            if (isTrackedHealingCancelled(entity)) return;
                            if (!trackedNpcHoldsMainHandItem(entity, isEnchanted ? Items.ENCHANTED_GOLDEN_APPLE : Items.GOLDEN_APPLE)) {
                                recoverItemDueToFailure(entity);
                                return;
                            }

                            LivingEntityPatch<?> livingEntityPatch1 = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                            if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch1 != null) {
                                livingEntityPatch1.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                            }

                            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, playerNpcEntity.getMainWeaponItem().copy());
                            }
                            if (entity instanceof AVNpc AVNpc) {
                                if (livingEntityPatch1 != null && AVNpc instanceof SteveEntity && CombatCommon.canSwitchWeapon((MobPatch<?>) livingEntityPatch1)) {
                                    CombatCommon.switchWeapon((MobPatch<?>) livingEntityPatch1);
                                } else {
                                    livingEntity.setItemInHand(InteractionHand.MAIN_HAND, AVNpc.getMainWeaponItem().copy());
                                }
                            }

                            if (!livingEntity.level().isClientSide()) {
                                if (isEnchanted) {
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0));
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0));
                                } else {
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0));
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
                                }
                            }

                            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                                playerNpcEntity.setHealing(false);
                            }
                            if (entity instanceof AVNpc AVNpc) {
                                AVNpc.setHealing(false);
                            }
                        }
                    };
                }
            };
        }
    }

    public static void drinkingHealingPotion(Entity entity, LevelAccessor levelaccessor, boolean isHostile, double amount) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

        if (livingEntityPatch != null && entity instanceof LivingEntity livingEntity) {
            if (entity instanceof PlayerNpcEntity playerNpcEntity && playerNpcEntity.isHealing()) {
                return;
            }
            if (entity instanceof AVNpc AVNpc && AVNpc.isHealing()) {
                return;
            }
            livingEntity.addEffect(
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (amount * 2.0D), 2, false, false)
            );
            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                if (playerNpcEntity.isHealing()) {
                    return;
                } else {
                    playerNpcEntity.setHealing(true);
                }
            }
            if (entity instanceof AVNpc AVNpc) {
                if (AVNpc.isHealing()) {
                    return;
                } else {
                    AVNpc.setHealing(true);
                }
            }
            new DelayedTask(20) {
                @Override
                public void run() {
                    if (!entity.isAlive()) return;
                    if (isTrackedHealingCancelled(entity)) return;
                    if (!trackedNpcHoldsMainHandItem(entity, Items.POTION)) {
                        recoverItemDueToFailure(entity);
                        return;
                    }

                    LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                    if (patch == null) {
                        recoverItemDueToFailure(entity);
                        return;
                    }
                    AssetAccessor<? extends StaticAnimation> currentAnim = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
                    if (currentAnim.get() instanceof AttackAnimation
                            || EpicfightUtil.isLongHitAnimation(currentAnim, patch)
                            || CombatCommon.canEscape((MobPatch<?>) livingEntityPatch)) {
                        recoverItemDueToFailure(entity);
                        return;
                    }
                    Runnable bite = () -> performDrinkingHealingPotionActionMainhand(entity, levelaccessor, patch);
                    int biteDelay = 4;
                    int totalBites = 7;

                    for (int i = 0; i < totalBites; i++) {
                        int delay = 4 + i * biteDelay;
                        new DelayedTask(delay) {
                            @Override
                            public void run() {
                                if (entity.isAlive()) {
                                    bite.run();
                                }
                            }
                        };
                    }

                    new DelayedTask(4 + totalBites * biteDelay - 1) {
                        @Override
                        public void run() {
                            if (!entity.isAlive()) return;
                            if (isTrackedHealingCancelled(entity)) return;
                            if (!trackedNpcHoldsMainHandItem(entity, Items.POTION)) {
                                recoverItemDueToFailure(entity);
                                return;
                            }
                            if (levelaccessor instanceof ServerLevel serverLevel) {
                                serverLevel.playSound(null,
                                        entity.blockPosition(),
                                        SoundEvents.PLAYER_BURP,
                                        SoundSource.NEUTRAL,
                                        1.5F, 1.0F);
                            }
                        }
                    };
                    new DelayedTask(4 + totalBites * biteDelay) {
                        @Override
                        public void run() {
                            if (!entity.isAlive()) return;
                            if (isTrackedHealingCancelled(entity)) return;
                            if (!trackedNpcHoldsMainHandItem(entity, Items.POTION)) {
                                recoverItemDueToFailure(entity);
                                return;
                            }

                            LivingEntityPatch<?> livingEntityPatch1 = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                            if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch1 != null) {
                                livingEntityPatch1.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                            }

                            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, playerNpcEntity.getMainWeaponItem().copy());
                            }
                            if (entity instanceof AVNpc AVNpc) {
                                if (livingEntityPatch1 != null && AVNpc instanceof SteveEntity && CombatCommon.canSwitchWeapon((MobPatch<?>) livingEntityPatch1)) {
                                    CombatCommon.switchWeapon((MobPatch<?>) livingEntityPatch1);
                                } else {
                                    livingEntity.setItemInHand(InteractionHand.MAIN_HAND, AVNpc.getMainWeaponItem().copy());
                                }
                            }

                            if (!livingEntity.level().isClientSide()) {
                                if (isHostile) {
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.HARM, 40, 0));
                                } else {
                                    livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
                                }
                            }

                            if (entity instanceof PlayerNpcEntity playerNpcEntity) {
                                playerNpcEntity.setHealing(false);
                            }
                            if (entity instanceof AVNpc AVNpc) {
                                AVNpc.setHealing(false);
                            }
                        }
                    };
                }
            };
        }
    }

    public static void forceLookAt(Entity self, Entity target, float maxYawChange, float maxPitchChange) {
        if (target == null) return;

        Vec3 eye = self.getEyePosition();
        Vec3 to = target.getEyePosition().subtract(eye);

        double dx = to.x;
        double dy = to.y;
        double dz = to.z;

        double flat = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
        float targetPitch = (float)(-(Mth.atan2(dy, flat) * (180F / Math.PI)));

        float yaw = Mth.approachDegrees(self.getYRot(), targetYaw, maxYawChange);
        float pitch = Mth.clamp(Mth.approachDegrees(self.getXRot(), targetPitch, maxPitchChange), -90F, 90F);

        self.setYRot(yaw);
        self.setXRot(pitch);
        self.yRotO = yaw;
        self.xRotO = pitch;

        if (self instanceof Mob mob) {
            mob.yBodyRot = yaw;
            mob.yBodyRotO = yaw;
            mob.yHeadRot = yaw;
            mob.yHeadRotO = yaw;
        }
    }

    public static double calculateGuardBreakWakeUpChance(LivingEntity entity) {
        float hpPct = entity.getHealth() / entity.getMaxHealth();

        double min = AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE.get();
        double max = AnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE.get();

        if (max < min) {
            double tmp = max;
            max = min;
            min = tmp;
        }

        double chance;
        if (max == min) {
            chance = max;
        } else {
            double t = (1.0D - hpPct) / 0.5D;
            t = Mth.clamp(t, 0.0D, 1.0D);
            chance = max - t * (max - min);
        }

        return chance;
    }

    public static void postGuardBreakWakeUp(LivingEntity entity, LivingEntityPatch<?> livingEntityPatch, ServerLevel serverLevel) {
        serverLevel.sendParticles(
                EpicFightParticles.WHITE_AFTERIMAGE.get(),
                entity.getX(), entity.getY(), entity.getZ(),
                1, 0.0D, 0.0D, 0.0D, Double.longBitsToDouble(entity.getId())
        );
        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false));

        double chooseAnimation = new Random().nextDouble(0.0D, 1.0D);
        if (chooseAnimation <= 0.4D) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
        } else if (chooseAnimation <= 0.8D) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
        } else {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
        }
    }
}
