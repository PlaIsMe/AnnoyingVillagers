package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.client.animation.rig_animation.living.LivingAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.living.RunAnimations;
import com.pla.annoyingvillagers.entity.AlexEntity;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.Vec3;

public class AnimationUtil {
    private static final double MIN_FAST_HORIZONTAL_SPEED = 0.09D;
    private static final double FAST_SPEED_ATTRIBUTE_RATIO = 0.38D;
    private static final double MIN_HORIZONTAL_MOVEMENT_SPEED = 0.01D;

    public static boolean shouldUseRunAnimation(Mob mob) {
        if (mob.isInWaterOrBubble()) return false;
        return mob.isSprinting() || isMovingFasterThanRegularSpeed(mob);
    }

    private static boolean isMovingFasterThanRegularSpeed(Mob mob) {
        Vec3 motion = mob.getDeltaMovement();
        double velocitySpeed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        double tickX = mob.getX() - mob.xo;
        double tickZ = mob.getZ() - mob.zo;
        double positionSpeed = Math.sqrt(tickX * tickX + tickZ * tickZ);
        double movementAttribute = mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        double fastSpeed = Math.max(MIN_FAST_HORIZONTAL_SPEED, movementAttribute * FAST_SPEED_ATTRIBUTE_RATIO);
        return Math.max(velocitySpeed, positionSpeed) > fastSpeed;
    }

    public static boolean isMovingHorizontally(Mob mob) {
        Vec3 motion = mob.getDeltaMovement();

        double velocitySpeed = Math.sqrt(
                motion.x * motion.x +
                        motion.z * motion.z
        );

        double tickX = mob.getX() - mob.xo;
        double tickZ = mob.getZ() - mob.zo;

        double positionSpeed = Math.sqrt(
                tickX * tickX +
                        tickZ * tickZ
        );

        return Math.max(velocitySpeed, positionSpeed)
                > MIN_HORIZONTAL_MOVEMENT_SPEED;
    }

    public static AnimationDefinition getRunAnimation(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        if (holdingItem.getItem() instanceof SwordItem) {
           if (offHandItem.getItem() instanceof SwordItem) {
               return RunAnimations.RUN_HOLDING_DUAL_WEAPON;
           } else {
               return RunAnimations.RUN_HOLDING_WEAPON;
           }
        } else {
            return RunAnimations.RUN;
        }
    }

    public static AnimationDefinition getWalkAnimation(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        return LivingAnimations.WALK;
    }

    public static AnimationDefinition getIdleAnimation(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        return LivingAnimations.IDLE;
    }

    public static void moreSweepingEdgeLogic(Mob mob) {
        if (mob instanceof AlexEntity alexEntity && alexEntity.level() instanceof ServerLevel serverLevel) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, alexEntity);
        }
    }

    public static void moreDancingEdgeLogic(Mob mob) {
        if (mob instanceof AlexEntity alexEntity && alexEntity.level() instanceof ServerLevel serverLevel) {
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, alexEntity);
            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, alexEntity, true);
        }
    }
}
