package com.pla.annoyingvillagers.rig;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.List;

public final class RigCombatProfiles {
    private static final RigCombatProfile DEFAULT_SWORD = new RigCombatProfile(
            List.of(RigAnimationId.SWORD_AUTO1, RigAnimationId.SWORD_AUTO2, RigAnimationId.SWORD_AUTO3, RigAnimationId.SWORD_AUTO4),
            List.of(RigAnimationId.SWORD_DASH, RigAnimationId.SWORD_AIRSLASH),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.SWEEPING_EDGE),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile DUAL_SWORD = new RigCombatProfile(
            List.of(RigAnimationId.SWORD_DUAL_AUTO1, RigAnimationId.SWORD_DUAL_AUTO2, RigAnimationId.SWORD_DUAL_AUTO3),
            List.of(RigAnimationId.SWORD_DUAL_DASH, RigAnimationId.SWORD_DUAL_AIRSLASH),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.DANCING_EDGE),
            0.20D,
            0.06D,
            0.025D
    );

//    private static final RigCombatProfile UNARMED = new RigCombatProfile(
//            List.of(RigAnimationId.SWORD_AUTO1, RigAnimationId.SWORD_AUTO2),
//            List.of(),
//            List.of(RigAnimationId.ROLL_BACKWARD),
//            List.of(),
//            0.0D,
//            0.05D,
//            0.0D
//    );

    private RigCombatProfiles() {
    }

    public static RigCombatProfile getCombatProfile(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        if (holdingItem.getItem() instanceof SwordItem) {
            if (offHandItem.getItem() instanceof SwordItem) {
                return DUAL_SWORD;
            } else {
                return DEFAULT_SWORD;
            }
        } else {
            return DEFAULT_SWORD;
        }
    }
}
