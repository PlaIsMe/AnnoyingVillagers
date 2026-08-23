package com.pla.annoyingvillagers.rig;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class RigCombatProfiles {
    private static final Map<RigCombatStyle, RigCombatProfile> PROFILES =
            new EnumMap<>(RigCombatStyle.class);

    public static RigLocomotionStyle getLocomotionStyle(Mob mob) {
        RigCombatProfile profile = getCombatProfile(mob);

        if (profile == null) {
            return RigLocomotionStyle.DEFAULT;
        }

        return profile.locomotionStyle();
    }

    static {
        PROFILES.put(RigCombatStyle.UNARMED, new RigCombatProfile(
                List.of(RigAnimationId.FIST_ATTACK1, RigAnimationId.FIST_ATTACK2, RigAnimationId.FIST_ATTACK3, RigAnimationId.FIST_ATTACK4, RigAnimationId.FIST_ATTACK5),
                List.of(RigAnimationId.FIST_DASH_ATTACK, RigAnimationId.FIST_JUMP_ATTACK, RigAnimationId.FIST_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.FIST_ULT),
                0.18D,
                0.06D,
                0.02D,
                RigLocomotionStyle.UNARMED
        ));

        PROFILES.put(RigCombatStyle.BASIC, new RigCombatProfile(
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
                0.02D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_BASIC, new RigCombatProfile(
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
                0.025D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.AXE, new RigCombatProfile(
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
                0.02D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.EARTH_AXE, new RigCombatProfile(
                List.of(RigAnimationId.AXE_ATTACK1, RigAnimationId.AXE_ATTACK2, RigAnimationId.AXE_ATTACK3,
                        RigAnimationId.AXE_ATTACK4, RigAnimationId.AXE_ATTACK5),
                List.of(RigAnimationId.AXE_DASH_ATTACK, RigAnimationId.AXE_JUMP_ATTACK, RigAnimationId.EARTH_AXE_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.EARTH_AXE_ULT),
                0.18D,
                0.06D,
                0.02D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.RED_AXE, new RigCombatProfile(
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
                List.of(RigAnimationId.GREATAXE_ULT),
                0.18D,
                0.06D,
                0.02D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_AXE, new RigCombatProfile(
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
                List.of(RigAnimationId.DUAL_AXE_ULT),
                0.18D,
                0.06D,
                0.02D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.GREATSWORD, new RigCombatProfile(
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
                0.0D,
                RigLocomotionStyle.GREATSWORD
        ));

        PROFILES.put(RigCombatStyle.GREATAXE, new RigCombatProfile(
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
                0.0D,
                RigLocomotionStyle.GREATSWORD
        ));

        PROFILES.put(RigCombatStyle.CRAFTING_TABLE, new RigCombatProfile(
                List.of(RigAnimationId.TACHI_ATTACK1, RigAnimationId.TACHI_ATTACK2, RigAnimationId.TACHI_ATTACK3,
                        RigAnimationId.TACHI_ATTACK4, RigAnimationId.TACHI_ATTACK5),
                List.of(RigAnimationId.GREATSWORD_DASH_ATTACK, RigAnimationId.GREATSWORD_JUMP_ATTACK, RigAnimationId.GREATSWORD_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.TACHI_ULT),
                0.18D,
                0.06D,
                0.0D,
                RigLocomotionStyle.CRAFTING_TABLE
        ));

        PROFILES.put(RigCombatStyle.LONGSWORD, new RigCombatProfile(
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
                0.0D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_LONGSWORD, new RigCombatProfile(
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
                0.0D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.SPEAR, new RigCombatProfile(
                List.of(RigAnimationId.SPEAR_ATTACK1, RigAnimationId.SPEAR_ATTACK2, RigAnimationId.SPEAR_ATTACK3,
                        RigAnimationId.SPEAR_ATTACK4, RigAnimationId.SPEAR_ATTACK5),
                List.of(RigAnimationId.SPEAR_DASH_ATTACK, RigAnimationId.SPEAR_JUMP_ATTACK, RigAnimationId.SPEAR_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.SPEAR_ULT),
                0.18D,
                0.06D,
                0.0D,
                RigLocomotionStyle.SPEAR
        ));

        PROFILES.put(RigCombatStyle.STAFF, new RigCombatProfile(
                List.of(RigAnimationId.SPEAR_ATTACK1, RigAnimationId.SPEAR_ATTACK2, RigAnimationId.SPEAR_ATTACK3,
                        RigAnimationId.SPEAR_ATTACK4, RigAnimationId.SPEAR_ATTACK5),
                List.of(RigAnimationId.SPEAR_DASH_ATTACK, RigAnimationId.SPEAR_JUMP_ATTACK, RigAnimationId.SPEAR_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.STAFF_ULT),
                0.18D,
                0.06D,
                0.0D,
                RigLocomotionStyle.SPEAR
        ));

        PROFILES.put(RigCombatStyle.SICKLE, new RigCombatProfile(
                List.of(RigAnimationId.SPEAR_ATTACK1, RigAnimationId.SPEAR_ATTACK2, RigAnimationId.SPEAR_ATTACK3,
                        RigAnimationId.SPEAR_ATTACK4, RigAnimationId.SPEAR_ATTACK5),
                List.of(RigAnimationId.SPEAR_DASH_ATTACK, RigAnimationId.SPEAR_JUMP_ATTACK, RigAnimationId.SPEAR_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.SICKLE_ULT),
                0.18D,
                0.06D,
                0.0D,
                RigLocomotionStyle.SPEAR
        ));

        PROFILES.put(RigCombatStyle.DAGGER, new RigCombatProfile(
                List.of(RigAnimationId.DAGGER_ATTACK1, RigAnimationId.DAGGER_ATTACK2, RigAnimationId.DAGGER_ATTACK3,
                        RigAnimationId.DAGGER_ATTACK4, RigAnimationId.DAGGER_ATTACK5),
                List.of(RigAnimationId.DAGGER_DASH_ATTACK, RigAnimationId.DAGGER_JUMP_ATTACK, RigAnimationId.DAGGER_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DAGGER_ULT),
                0.18D,
                0.06D,
                0.0D,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.TACHI, new RigCombatProfile(
                List.of(RigAnimationId.TACHI_ATTACK1, RigAnimationId.TACHI_ATTACK2, RigAnimationId.TACHI_ATTACK3,
                        RigAnimationId.TACHI_ATTACK4, RigAnimationId.TACHI_ATTACK5),
                List.of(RigAnimationId.TACHI_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.TACHI_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.TACHI_ULT),
                0.18D,
                0.06D,
                0.0D,
                RigLocomotionStyle.TACHI
        ));
    }

//
//    private static final RigCombatProfile SWORD = new RigCombatProfile(
//            List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
//                    RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
//            List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK),
//            List.of(
//                    RigAnimationId.ROLL_FORWARD,
//                    RigAnimationId.ROLL_BACKWARD,
//                    RigAnimationId.STEP_FORWARD,
//                    RigAnimationId.STEP_BACKWARD,
//                    RigAnimationId.STEP_LEFT,
//                    RigAnimationId.STEP_RIGHT
//            ),
//            List.of(RigAnimationId.SWORD_ULT),
//            0.18D,
//            0.06D,
//            0.0D
//    );
//
//    private static final RigCombatProfile DUAL_SWORD = new RigCombatProfile(
//            List.of(RigAnimationId.DUAL_SWORD_ATTACK1, RigAnimationId.DUAL_SWORD_ATTACK2, RigAnimationId.DUAL_SWORD_ATTACK3,
//                    RigAnimationId.DUAL_SWORD_ATTACK4, RigAnimationId.DUAL_SWORD_ATTACK5),
//            List.of(RigAnimationId.DUAL_SWORD_DASH_ATTACK, RigAnimationId.DUAL_SWORD_JUMP_ATTACK, RigAnimationId.DUAL_SWORD_EXTRA_ATTACK),
//            List.of(
//                    RigAnimationId.ROLL_FORWARD,
//                    RigAnimationId.ROLL_BACKWARD,
//                    RigAnimationId.STEP_FORWARD,
//                    RigAnimationId.STEP_BACKWARD,
//                    RigAnimationId.STEP_LEFT,
//                    RigAnimationId.STEP_RIGHT
//            ),
//            List.of(RigAnimationId.DUAL_SWORD_ULT),
//            0.18D,
//            0.06D,
//            0.0D
//    );

    public static boolean isProfileAttack(RigAnimationId animationId) {
        return PROFILES.values().stream()
                .anyMatch(profile -> profile.containsAttack(animationId));
    }

    private static RigCombatProfile getProfile(RigCombatStyle style) {
        return PROFILES.get(style);
    }

    public static RigCombatProfile getCombatProfile(Mob mob) {
        ItemStack mainHand = mob.getMainHandItem();
        ItemStack offHand = mob.getOffhandItem();

        if (mainHand.getItem() instanceof RigCombatProfileProvider mainProvider) {

            if (offHand.getItem() instanceof RigCombatProfileProvider offProvider) {
                RigDualWieldGroup mainGroup = mainProvider.getDualWieldGroup(mainHand);
                RigDualWieldGroup offGroup = offProvider.getDualWieldGroup(offHand);
                if (mainGroup != RigDualWieldGroup.NONE && mainGroup == offGroup) {
                    return getProfile(mainProvider.getDualRigCombatStyle(mainHand, offHand));
                }
            }

            return getProfile(mainProvider.getRigCombatStyle(mainHand));
        }

        if (mainHand.getItem() instanceof SwordItem) {
            if (offHand.getItem() instanceof SwordItem) {
                return getProfile(RigCombatStyle.DUAL_BASIC);
            }
            return getProfile(RigCombatStyle.BASIC);
        }

        return getProfile(RigCombatStyle.UNARMED);
    }
}
