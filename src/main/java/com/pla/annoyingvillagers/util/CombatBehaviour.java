package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.compat.SmartNpc;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

import java.util.Random;

public class CombatBehaviour {
    public record HealingFoodUse(ItemStack foodStack, InteractionHand hand, boolean fromInventory) {
    }

    public static boolean eatInventoryHealingFood(LivingEntity entity) {
        if (entity == null || entity.level().isClientSide() || isTrackedHealing(entity)) {
            return false;
        }

        HealingFoodUse foodUse = selectHealingFoodUse(entity);
        if (foodUse == null) {
            return false;
        }

        setTrackedHealing(entity, true);
        try {
            return useHealingFood(entity, foodUse);
        } finally {
            setTrackedHealing(entity, false);
        }
    }

    public static HealingFoodUse selectHealingFoodUse(LivingEntity entity) {
        if (entity == null) {
            return null;
        }

        ItemStack offhand = entity.getOffhandItem();
        if (InventoryUtils.isHealingFoodStack(offhand)) {
            return new HealingFoodUse(oneOf(offhand), InteractionHand.OFF_HAND, false);
        }

        ItemStack mainhand = entity.getMainHandItem();
        if (InventoryUtils.isHealingFoodStack(mainhand)) {
            return new HealingFoodUse(oneOf(mainhand), InteractionHand.MAIN_HAND, false);
        }

        ItemStack inventoryFood = InventoryUtils.selectHealingFood(entity, entity.getRandom()).orElse(ItemStack.EMPTY);
        if (inventoryFood.isEmpty()) {
            return null;
        }

        return new HealingFoodUse(inventoryFood.copy(), InteractionHand.MAIN_HAND, true);
    }

    public static boolean useHealingFood(LivingEntity entity, HealingFoodUse foodUse) {
        if (entity == null || foodUse == null || entity.level().isClientSide()) {
            return false;
        }

        ItemStack foodStack = foodUse.foodStack().copy();
        foodStack.setCount(1);
        if (foodStack.isEmpty()) {
            return false;
        }

        if (foodUse.fromInventory()) {
            if (!InventoryUtils.consumeHealingFood(entity, foodStack)) {
                return false;
            }
        } else if (!consumeHandFood(entity, foodUse.hand(), foodStack)) {
            return false;
        }

        return finishUsingHealingFood(entity, foodStack, foodUse.hand());
    }

    public static boolean finishUsingHealingFood(LivingEntity entity, ItemStack foodStack, InteractionHand hand) {
        if (entity == null || entity.level().isClientSide() || foodStack.isEmpty()) {
            return false;
        }

        ItemStack useStack = foodStack.copy();
        useStack.setCount(1);
        ItemStack remainder = useStack.getItem().finishUsingItem(useStack, entity.level(), entity);
        if (!remainder.isEmpty() && !InventoryUtils.addItem(entity, remainder.copy())) {
            entity.spawnAtLocation(remainder.copy());
        }
        healFromRegularFood(entity, foodStack);
        entity.swing(hand, true);
        entity.level().playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        entity.level().playSound(null, entity.blockPosition(), SoundEvents.PLAYER_BURP, SoundSource.NEUTRAL, 0.5F, 1.0F);
        return true;
    }

    private static boolean consumeHandFood(LivingEntity entity, InteractionHand hand, ItemStack foodStack) {
        ItemStack handStack = entity.getItemInHand(hand);
        if (handStack.isEmpty() || !ItemStack.isSameItemSameTags(handStack, foodStack)) {
            return false;
        }

        handStack.shrink(1);
        if (handStack.isEmpty()) {
            entity.setItemInHand(hand, ItemStack.EMPTY);
        }
        return true;
    }

    private static ItemStack oneOf(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1);
        return copy;
    }

    private static void healFromRegularFood(LivingEntity entity, ItemStack foodStack) {
        if (foodStack.is(Items.GOLDEN_APPLE) || foodStack.is(Items.ENCHANTED_GOLDEN_APPLE)) {
            return;
        }

        FoodProperties foodProperties = foodStack.getFoodProperties(entity);
        if (foodProperties != null) {
            entity.heal(Math.max(2.0F, foodProperties.getNutrition()));
        }
    }

    private static boolean isTrackedHealing(Entity entity) {
        if (entity instanceof AVNpc avNpc) {
            return avNpc.isHealing();
        }
        return ModList.get().isLoaded("smart_npc") && SmartNpc.isHealing(entity);
    }

    private static void setTrackedHealing(Entity entity, boolean healing) {
        if (entity instanceof AVNpc avNpc) {
            avNpc.setHealing(healing);
        } else if (ModList.get().isLoaded("smart_npc")) {
            SmartNpc.setHealing(entity, healing);
        }
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

    public static boolean throwEnderPearl(Entity entity, float xRot) {
        if (!consumeTrackedEnderPearl(entity)) {
            return false;
        }

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
                    if (!entity.isAlive() || entity.isRemoved()) {
                        return;
                    }

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

        return true;
    }

    public static boolean throwEnderPearlAt(Entity entity, Vec3 target) {
        if (!consumeTrackedEnderPearl(entity)) {
            return false;
        }

        facePosition(entity, target);

        if (entity.level() instanceof ServerLevel serverLevel) {
            new DelayedTask(5) {
                @Override
                public void run() {
                    if (!entity.isAlive() || entity.isRemoved()) {
                        return;
                    }

                    Vec3 handPos = getFrontLeftPos(entity);
                    Vec3 delta = target.subtract(handPos);
                    double horizontal = Math.sqrt(delta.x * delta.x + delta.z * delta.z);

                    Projectile projectile = new ThrownEnderpearl(EntityType.ENDER_PEARL, serverLevel);
                    projectile.setOwner(entity);
                    projectile.setPos(handPos.x, handPos.y, handPos.z);
                    projectile.shoot(delta.x, delta.y + horizontal * 0.08D, delta.z, 1.8F, 0.0F);
                    serverLevel.addFreshEntity(projectile);
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                }
            };
        }

        return true;
    }

    private static boolean consumeTrackedEnderPearl(Entity entity) {
        if (!(entity instanceof AVNpc)) {
            return true;
        }
        return InventoryUtils.consumeItem(entity, Items.ENDER_PEARL, 1).isPresent();
    }

    private static void facePosition(Entity entity, Vec3 target) {
        Vec3 origin = entity instanceof LivingEntity livingEntity
                ? livingEntity.getEyePosition(1.0F)
                : entity.position().add(0.0D, entity.getBbHeight() * 0.85D, 0.0D);
        double dx = target.x - origin.x;
        double dy = target.y - origin.y;
        double dz = target.z - origin.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Mth.atan2(dz, dx) * (180.0F / (float) Math.PI)) - 90.0F;
        float pitch = (float) (-(Mth.atan2(dy, horizontal) * (180.0F / (float) Math.PI)));

        entity.setYRot(yaw);
        entity.setXRot(pitch);
        entity.setYBodyRot(yaw);
        entity.setYHeadRot(yaw);
        entity.yRotO = yaw;
        entity.xRotO = pitch;

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.yBodyRotO = yaw;
            livingEntity.yHeadRotO = yaw;
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
}
