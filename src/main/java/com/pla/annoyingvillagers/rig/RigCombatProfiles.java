package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.client.animation.rig_animation.RigRunAnimations;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.List;

public final class RigCombatProfiles {
//    private static final RigCombatProfile DIAMOND_SWORD = new RigCombatProfile(
//            List.of(RigAnimationId.NORMAL_ATTACK1, RigAnimationId.NORMAL_ATTACK2),
//            List.of(RigAnimationId.DASH_ATTACK1, RigAnimationId.JUMP_ATTACK1, RigAnimationId.JUMP_TOWARD_ATTACK1),
//            List.of(RigAnimationId.ROLL_TOWARD, RigAnimationId.ROLL_BACKWARD, RigAnimationId.ROLL_RIGHT, RigAnimationId.ROLL_LEFT),
//            List.of(RigAnimationId.SWORD_ULTIMATE_ATTACK1),
//            0.22D,
//            0.08D,
//            0.035D
//    );
//
//    private static final RigCombatProfile IRON_SWORD = new RigCombatProfile(
//            List.of(RigAnimationId.NORMAL_ATTACK3, RigAnimationId.NORMAL_ATTACK4),
//            List.of(RigAnimationId.DASH_ATTACK2, RigAnimationId.JUMP_ATTACK2, RigAnimationId.JUMP_TOWARD_ATTACK2),
//            List.of(RigAnimationId.ROLL_TOWARD, RigAnimationId.ROLL_BACKWARD, RigAnimationId.ROLL_RIGHT, RigAnimationId.ROLL_LEFT),
//            List.of(RigAnimationId.SWORD_ULTIMATE_ATTACK2),
//            0.20D,
//            0.08D,
//            0.03D
//    );
//
//    private static final RigCombatProfile HEAVY_WEAPON = new RigCombatProfile(
//            List.of(RigAnimationId.NORMAL_ATTACK3, RigAnimationId.NORMAL_ATTACK4),
//            List.of(RigAnimationId.DASH_ATTACK2, RigAnimationId.JUMP_TOWARD_ATTACK2),
//            List.of(RigAnimationId.ROLL_BACKWARD, RigAnimationId.ROLL_LEFT, RigAnimationId.ROLL_RIGHT),
//            List.of(RigAnimationId.SWORD_ULTIMATE_ATTACK2),
//            0.18D,
//            0.06D,
//            0.025D
//    );

    private static final RigCombatProfile DEFAULT_SWORD = new RigCombatProfile(
            List.of(RigAnimationId.SWORD_AUTO1, RigAnimationId.SWORD_AUTO2, RigAnimationId.SWORD_AUTO3, RigAnimationId.SWORD_AUTO4),
            List.of(RigAnimationId.SWORD_DASH, RigAnimationId.SWORD_AIRSLASH),
            List.of(RigAnimationId.ROLL_FORWARD, RigAnimationId.ROLL_BACKWARD, RigAnimationId.STEP_FORWARD, RigAnimationId.STEP_BACKWARD, RigAnimationId.STEP_LEFT, RigAnimationId.STEP_RIGHT),
            List.of(RigAnimationId.SWEEPING_EDGE),
            0.18D,
            0.06D,
            0.02D
    );

    private static final RigCombatProfile DUAL_SWORD = new RigCombatProfile(
            List.of(RigAnimationId.SWORD_DUAL_AUTO1, RigAnimationId.SWORD_DUAL_AUTO2, RigAnimationId.SWORD_DUAL_AUTO3),
            List.of(RigAnimationId.SWORD_DUAL_DASH, RigAnimationId.SWORD_DUAL_AIRSLASH),
            List.of(RigAnimationId.ROLL_FORWARD, RigAnimationId.ROLL_BACKWARD, RigAnimationId.STEP_FORWARD, RigAnimationId.STEP_BACKWARD, RigAnimationId.STEP_LEFT, RigAnimationId.STEP_RIGHT),
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
