package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.client.animation.rig_animation.RigIdleAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigRunAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigWalkAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class AnimationUtil {
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
