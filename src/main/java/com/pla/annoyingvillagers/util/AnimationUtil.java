package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.client.animation.rig_animation.RigIdleAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigRunAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigWalkAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.Vec3;

public class AnimationUtil {
    private static final float RUN_LIMB_SWING_AMOUNT = 0.52F;
    private static final double MIN_FAST_HORIZONTAL_SPEED = 0.09D;
    private static final double FAST_SPEED_ATTRIBUTE_RATIO = 0.38D;

    public static boolean shouldUseRunAnimation(Mob mob, float limbSwingAmount) {
        if (mob.isInWaterOrBubble()) {
            return false;
        }

        return mob.isSprinting()
                || mob.isAggressive()
                || isMovingFasterThanRegularSpeed(mob, limbSwingAmount);
    }

    private static boolean isMovingFasterThanRegularSpeed(Mob mob, float limbSwingAmount) {
        if (limbSwingAmount >= RUN_LIMB_SWING_AMOUNT) {
            return true;
        }

        Vec3 motion = mob.getDeltaMovement();
        double horizontalSpeed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        double movementAttribute = mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
        double fastSpeed = Math.max(MIN_FAST_HORIZONTAL_SPEED, movementAttribute * FAST_SPEED_ATTRIBUTE_RATIO);
        return horizontalSpeed > fastSpeed;
    }

    public static AnimationDefinition getRunAnimation(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        if (holdingItem.getItem() instanceof SwordItem) {
           if (offHandItem.getItem() instanceof SwordItem) {
               return RigRunAnimations.RUN_HOLDING_DUAL_WEAPON;
           } else {
               return RigRunAnimations.RUN_HOLDING_WEAPON;
           }
        } else {
            return RigRunAnimations.RUN;
        }
    }

    public static AnimationDefinition getWalkAnimation(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        return RigWalkAnimations.WALK;
    }

    public static AnimationDefinition getIdleAnimation(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        return RigIdleAnimations.IDLE;
    }
}
