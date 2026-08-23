package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.client.animation.rig_animation.greatsword.GreatswordAnimations1;
import com.pla.annoyingvillagers.client.animation.rig_animation.living.LivingAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.living.RunAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.spear.SpearAnimations1;
import com.pla.annoyingvillagers.client.animation.rig_animation.tachi.TachiAnimations1;
import com.pla.annoyingvillagers.rig.RigCombatProfiles;
import com.pla.annoyingvillagers.rig.RigLocomotionStyle;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.Vec3;

public class AnimationUtil {
    private static final double MIN_FAST_HORIZONTAL_SPEED = 0.09D;
    private static final double FAST_SPEED_ATTRIBUTE_RATIO = 0.38D;

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
        Vec3 movement = mob.getDeltaMovement();
        return movement.x * movement.x + movement.z * movement.z > 0.0004D;
    }

    private static AnimationDefinition getCustomIdleAnimation(RigLocomotionStyle style) {
        return switch (style) {
            case SPEAR -> SpearAnimations1.SPEAR_IDLE;
            case GREATSWORD -> GreatswordAnimations1.GREATSWORD_IDLE;
            case CRAFTING_TABLE -> GreatswordAnimations1.CARRY;
            case TACHI -> TachiAnimations1.TACHI_IDLE;
            default -> LivingAnimations.IDLE;
        };
    }

    private static AnimationDefinition getCustomWalkAnimation(RigLocomotionStyle style) {
        return switch (style) {
            case SPEAR -> SpearAnimations1.SPEAR_WALK;
            case GREATSWORD -> GreatswordAnimations1.GREATSWORD_WALK;
            case TACHI -> TachiAnimations1.TACHI_WALK;
            default -> LivingAnimations.WALK;
        };
    }

    private static AnimationDefinition getCustomRunAnimation(RigLocomotionStyle style) {
        return switch (style) {
            case DEFAULT -> RunAnimations.RUN_HOLDING_WEAPON;
            case SPEAR -> SpearAnimations1.SPEAR_RUN;
            case GREATSWORD -> GreatswordAnimations1.GREATSWORD_RUN;
            case TACHI -> TachiAnimations1.TACHI_RUN;
            default -> RunAnimations.RUN;
        };
    }

    public static AnimationDefinition getRunAnimation(Mob mob) {
        ItemStack mainHand = mob.getMainHandItem();
        ItemStack offHand = mob.getOffhandItem();
        if (mainHand.getItem() instanceof SwordItem && offHand.getItem() instanceof SwordItem) {
            return RunAnimations.RUN_HOLDING_DUAL_WEAPON;
        }

        RigLocomotionStyle style = RigCombatProfiles.getLocomotionStyle(mob);
        AnimationDefinition customRun = getCustomRunAnimation(style);

        if (customRun != null) {
            return customRun;
        }

        if (mainHand.getItem() instanceof SwordItem) {
            return RunAnimations.RUN_HOLDING_WEAPON;
        }

        return RunAnimations.RUN;
    }

    public static AnimationDefinition getWalkAnimation(Mob mob) {
        RigLocomotionStyle style = RigCombatProfiles.getLocomotionStyle(mob);
        AnimationDefinition customWalk = getCustomWalkAnimation(style);

        if (customWalk != null) {
            return customWalk;
        }

        return LivingAnimations.WALK;
    }

    public static AnimationDefinition getIdleAnimation(Mob mob) {
        ItemStack mainHand = mob.getMainHandItem();
        ItemStack offHand = mob.getOffhandItem();
        if (mainHand.getItem() instanceof SwordItem && offHand.getItem() instanceof SwordItem) {
            return LivingAnimations.IDLE_DUAL;
        }

        RigLocomotionStyle style = RigCombatProfiles.getLocomotionStyle(mob);
        AnimationDefinition customIdle = getCustomIdleAnimation(style);

        if (customIdle != null) {
            return customIdle;
        }

        return LivingAnimations.IDLE;
    }
}
