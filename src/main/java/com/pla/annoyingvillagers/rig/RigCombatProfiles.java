package com.pla.annoyingvillagers.rig;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.List;

public final class RigCombatProfiles {
    private static final RigCombatProfile AXE = new RigCombatProfile(
            List.of(RigAnimationId.AXE_ATTACK1, RigAnimationId.AXE_ATTACK2, RigAnimationId.AXE_ATTACK3,
                    RigAnimationId.AXE_ATTACK4, RigAnimationId.AXE_ATTACK5),
            List.of(RigAnimationId.AXE_DASH_ATTACK, RigAnimationId.AXE_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.AXE_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile GREATSWORD = new RigCombatProfile(
            List.of(RigAnimationId.GREATSWORD_ATTACK1, RigAnimationId.GREATSWORD_ATTACK2, RigAnimationId.GREATSWORD_ATTACK3,
                    RigAnimationId.GREATSWORD_ATTACK4, RigAnimationId.GREATSWORD_ATTACK5),
            List.of(RigAnimationId.GREATSWORD_DASH_ATTACK, RigAnimationId.GREATSWORD_JUMP_ATTACK, RigAnimationId.GREATSWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.GREATSWORD_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile GREATAXE = new RigCombatProfile(
            List.of(RigAnimationId.GREATSWORD_ATTACK1, RigAnimationId.GREATSWORD_ATTACK2, RigAnimationId.GREATSWORD_ATTACK3,
                    RigAnimationId.GREATAXE_ATTACK4, RigAnimationId.GREATAXE_ATTACK5),
            List.of(RigAnimationId.GREATAXE_DASH_ATTACK, RigAnimationId.GREATAXE_JUMP_ATTACK, RigAnimationId.GREATSWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.GREATAXE_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile LONGSWORD = new RigCombatProfile(
            List.of(RigAnimationId.LONGSWORD_ATTACK1, RigAnimationId.LONGSWORD_ATTACK2, RigAnimationId.LONGSWORD_ATTACK3,
                    RigAnimationId.LONGSWORD_ATTACK4, RigAnimationId.LONGSWORD_ATTACK5),
            List.of(RigAnimationId.LONGSWORD_DASH_ATTACK, RigAnimationId.LONGSWORD_JUMP_ATTACK, RigAnimationId.LONGSWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.LONGSWORD_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile DUAL_LONGSWORD = new RigCombatProfile(
            List.of(RigAnimationId.DUAL_LONGSWORD_ATTACK1, RigAnimationId.DUAL_LONGSWORD_ATTACK2, RigAnimationId.DUAL_LONGSWORD_ATTACK3,
                    RigAnimationId.DUAL_LONGSWORD_ATTACK4, RigAnimationId.DUAL_LONGSWORD_ATTACK5),
            List.of(RigAnimationId.DUAL_LONGSWORD_DASH_ATTACK, RigAnimationId.DUAL_LONGSWORD_JUMP_ATTACK, RigAnimationId.DUAL_LONGSWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.DUAL_LONGSWORD_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile SWORD = new RigCombatProfile(
            List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                    RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
            List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.SWORD_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile DUAL_SWORD = new RigCombatProfile(
            List.of(RigAnimationId.DUAL_SWORD_ATTACK1, RigAnimationId.DUAL_SWORD_ATTACK2, RigAnimationId.DUAL_SWORD_ATTACK3,
                    RigAnimationId.DUAL_SWORD_ATTACK4, RigAnimationId.DUAL_SWORD_ATTACK5),
            List.of(RigAnimationId.DUAL_SWORD_DASH_ATTACK, RigAnimationId.DUAL_SWORD_JUMP_ATTACK, RigAnimationId.DUAL_SWORD_EXTRA_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.DUAL_SWORD_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile BASIC_ATTACK = new RigCombatProfile(
            List.of(RigAnimationId.BASIC_ATTACK1, RigAnimationId.BASIC_ATTACK2, RigAnimationId.BASIC_ATTACK3, RigAnimationId.BASIC_ATTACK4),
            List.of(RigAnimationId.BASIC_DASH_ATTACK, RigAnimationId.BASIC_JUMP_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.BASIC_ULT),
            0.18D,
            0.06D,
            0.0D
    );

    private static final RigCombatProfile DUAL_BASIC_ATTACK = new RigCombatProfile(
            List.of(RigAnimationId.DUAL_BASIC_ATTACK1, RigAnimationId.DUAL_BASIC_ATTACK2, RigAnimationId.DUAL_BASIC_ATTACK3),
            List.of(RigAnimationId.DUAL_BASIC_DASH_ATTACK, RigAnimationId.DUAL_BASIC_JUMP_ATTACK),
            List.of(
                    RigAnimationId.ROLL_FORWARD,
                    RigAnimationId.ROLL_BACKWARD,
                    RigAnimationId.STEP_FORWARD,
                    RigAnimationId.STEP_BACKWARD,
                    RigAnimationId.STEP_LEFT,
                    RigAnimationId.STEP_RIGHT
            ),
            List.of(RigAnimationId.DUAL_BASIC_ULT),
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

    public static boolean isProfileAttack(RigAnimationId animationId) {
        return BASIC_ATTACK.containsAttack(animationId) || DUAL_SWORD.containsAttack(animationId);
    }

    public static RigCombatProfile getCombatProfile(Mob mob) {
        ItemStack holdingItem = mob.getMainHandItem();
        ItemStack offHandItem = mob.getOffhandItem();

        if (holdingItem.getItem() instanceof SwordItem) {
            if (offHandItem.getItem() instanceof SwordItem) {
                return DUAL_SWORD;
            } else {
                return BASIC_ATTACK;
            }
        } else {
            return BASIC_ATTACK;
        }
    }
}
