package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class RigCombatProfiles {
    private static final Map<RigCombatStyle, RigCombatProfile> PROFILES = new EnumMap<>(RigCombatStyle.class);
    private static final double SPECIAL_ATTACK_CHANCE = 0.15D;
    private static final double ROLLING_CHANCE = 0.15D;
    private static final double ULTIMATE_CHANCE = 0.10D;

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
                List.of(RigAnimationId.FIST_DASH_ATTACK, RigAnimationId.FIST_JUMP_ATTACK, RigAnimationId.FIST_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.FIST_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.UNARMED
        ));

        PROFILES.put(RigCombatStyle.BASIC, new RigCombatProfile(
                List.of(RigAnimationId.BASIC_ATTACK1, RigAnimationId.BASIC_ATTACK2, RigAnimationId.BASIC_ATTACK3, RigAnimationId.BASIC_ATTACK4),
                List.of(RigAnimationId.BASIC_DASH_ATTACK, RigAnimationId.BASIC_JUMP_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.BASIC_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_BASIC, new RigCombatProfile(
                List.of(RigAnimationId.DUAL_BASIC_ATTACK1, RigAnimationId.DUAL_BASIC_ATTACK2, RigAnimationId.DUAL_BASIC_ATTACK3),
                List.of(RigAnimationId.DUAL_BASIC_DASH_ATTACK, RigAnimationId.DUAL_BASIC_JUMP_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DUAL_BASIC_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.AXE, new RigCombatProfile(
                List.of(RigAnimationId.AXE_ATTACK1, RigAnimationId.AXE_ATTACK2, RigAnimationId.AXE_ATTACK3,
                        RigAnimationId.AXE_ATTACK4, RigAnimationId.AXE_ATTACK5),
                List.of(RigAnimationId.AXE_DASH_ATTACK, RigAnimationId.AXE_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.AXE_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.EARTH_AXE, new RigCombatProfile(
                List.of(RigAnimationId.AXE_ATTACK1, RigAnimationId.AXE_ATTACK2, RigAnimationId.AXE_ATTACK3,
                        RigAnimationId.AXE_ATTACK4, RigAnimationId.AXE_ATTACK5),
                List.of(RigAnimationId.AXE_DASH_ATTACK, RigAnimationId.AXE_JUMP_ATTACK, RigAnimationId.EARTH_AXE_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.EARTH_AXE_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.RED_AXE, new RigCombatProfile(
                List.of(RigAnimationId.AXE_ATTACK1, RigAnimationId.AXE_ATTACK2, RigAnimationId.AXE_ATTACK3,
                        RigAnimationId.AXE_ATTACK4, RigAnimationId.AXE_ATTACK5),
                List.of(RigAnimationId.AXE_DASH_ATTACK, RigAnimationId.AXE_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.GREATAXE_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_AXE, new RigCombatProfile(
                List.of(RigAnimationId.DUAL_SWORD_ATTACK1, RigAnimationId.DUAL_SWORD_ATTACK2, RigAnimationId.DUAL_SWORD_ATTACK3,
                        RigAnimationId.DUAL_SWORD_ATTACK4, RigAnimationId.DUAL_SWORD_ATTACK5),
                List.of(RigAnimationId.DUAL_SWORD_DASH_ATTACK, RigAnimationId.DUAL_SWORD_JUMP_ATTACK, RigAnimationId.DUAL_SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DUAL_AXE_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.GREATSWORD, new RigCombatProfile(
                List.of(RigAnimationId.GREATSWORD_ATTACK1, RigAnimationId.GREATSWORD_ATTACK2, RigAnimationId.GREATSWORD_ATTACK3,
                        RigAnimationId.GREATSWORD_ATTACK4, RigAnimationId.GREATSWORD_ATTACK5),
                List.of(RigAnimationId.GREATSWORD_DASH_ATTACK, RigAnimationId.GREATSWORD_JUMP_ATTACK, RigAnimationId.GREATSWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.GREATSWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.GREATSWORD
        ));

        PROFILES.put(RigCombatStyle.GREATAXE, new RigCombatProfile(
                List.of(RigAnimationId.GREATSWORD_ATTACK1, RigAnimationId.GREATSWORD_ATTACK2, RigAnimationId.GREATSWORD_ATTACK3,
                        RigAnimationId.GREATAXE_ATTACK4, RigAnimationId.GREATAXE_ATTACK5),
                List.of(RigAnimationId.GREATAXE_DASH_ATTACK, RigAnimationId.GREATAXE_JUMP_ATTACK, RigAnimationId.GREATSWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.GREATAXE_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.GREATSWORD
        ));

        PROFILES.put(RigCombatStyle.CRAFTING_TABLE, new RigCombatProfile(
                List.of(RigAnimationId.TACHI_ATTACK1, RigAnimationId.TACHI_ATTACK2, RigAnimationId.TACHI_ATTACK3,
                        RigAnimationId.TACHI_ATTACK4, RigAnimationId.TACHI_ATTACK5),
                List.of(RigAnimationId.GREATSWORD_DASH_ATTACK, RigAnimationId.GREATSWORD_JUMP_ATTACK, RigAnimationId.GREATSWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.TACHI_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.CRAFTING_TABLE
        ));

        PROFILES.put(RigCombatStyle.LONGSWORD, new RigCombatProfile(
                List.of(RigAnimationId.LONGSWORD_ATTACK1, RigAnimationId.LONGSWORD_ATTACK2, RigAnimationId.LONGSWORD_ATTACK3,
                        RigAnimationId.LONGSWORD_ATTACK4, RigAnimationId.LONGSWORD_ATTACK5),
                List.of(RigAnimationId.LONGSWORD_DASH_ATTACK, RigAnimationId.LONGSWORD_JUMP_ATTACK, RigAnimationId.LONGSWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.LONGSWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_LONGSWORD, new RigCombatProfile(
                List.of(RigAnimationId.DUAL_LONGSWORD_ATTACK1, RigAnimationId.DUAL_LONGSWORD_ATTACK2, RigAnimationId.DUAL_LONGSWORD_ATTACK3,
                        RigAnimationId.DUAL_LONGSWORD_ATTACK4, RigAnimationId.DUAL_LONGSWORD_ATTACK5),
                List.of(RigAnimationId.DUAL_LONGSWORD_DASH_ATTACK, RigAnimationId.DUAL_LONGSWORD_JUMP_ATTACK, RigAnimationId.DUAL_LONGSWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DUAL_LONGSWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.SPEAR, new RigCombatProfile(
                List.of(RigAnimationId.SPEAR_ATTACK1, RigAnimationId.SPEAR_ATTACK2, RigAnimationId.SPEAR_ATTACK3,
                        RigAnimationId.SPEAR_ATTACK4, RigAnimationId.SPEAR_ATTACK5),
                List.of(RigAnimationId.SPEAR_DASH_ATTACK, RigAnimationId.SPEAR_JUMP_ATTACK, RigAnimationId.SPEAR_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.SPEAR_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.SPEAR
        ));

        PROFILES.put(RigCombatStyle.STAFF, new RigCombatProfile(
                List.of(RigAnimationId.SPEAR_ATTACK1, RigAnimationId.SPEAR_ATTACK2, RigAnimationId.SPEAR_ATTACK3,
                        RigAnimationId.SPEAR_ATTACK4, RigAnimationId.SPEAR_ATTACK5),
                List.of(RigAnimationId.SPEAR_DASH_ATTACK, RigAnimationId.SPEAR_JUMP_ATTACK, RigAnimationId.SPEAR_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.STAFF_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.SPEAR
        ));

        PROFILES.put(RigCombatStyle.SICKLE, new RigCombatProfile(
                List.of(RigAnimationId.SPEAR_ATTACK1, RigAnimationId.SPEAR_ATTACK2, RigAnimationId.SPEAR_ATTACK3,
                        RigAnimationId.SPEAR_ATTACK4, RigAnimationId.SPEAR_ATTACK5),
                List.of(RigAnimationId.SPEAR_DASH_ATTACK, RigAnimationId.SPEAR_JUMP_ATTACK, RigAnimationId.SPEAR_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.SICKLE_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.SPEAR
        ));

        PROFILES.put(RigCombatStyle.DAGGER, new RigCombatProfile(
                List.of(RigAnimationId.DAGGER_ATTACK1, RigAnimationId.DAGGER_ATTACK2, RigAnimationId.DAGGER_ATTACK3,
                        RigAnimationId.DAGGER_ATTACK4, RigAnimationId.DAGGER_ATTACK5),
                List.of(RigAnimationId.DAGGER_DASH_ATTACK, RigAnimationId.DAGGER_JUMP_ATTACK, RigAnimationId.DAGGER_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DAGGER_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.TACHI, new RigCombatProfile(
                List.of(RigAnimationId.TACHI_ATTACK1, RigAnimationId.TACHI_ATTACK2, RigAnimationId.TACHI_ATTACK3,
                        RigAnimationId.TACHI_ATTACK4, RigAnimationId.TACHI_ATTACK5),
                List.of(RigAnimationId.TACHI_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.TACHI_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.TACHI_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.TACHI
        ));

        PROFILES.put(RigCombatStyle.SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.DUAL_SWORD_ATTACK1, RigAnimationId.DUAL_SWORD_ATTACK2, RigAnimationId.DUAL_SWORD_ATTACK3,
                        RigAnimationId.DUAL_SWORD_ATTACK4, RigAnimationId.DUAL_SWORD_ATTACK5),
                List.of(RigAnimationId.DUAL_SWORD_DASH_ATTACK, RigAnimationId.DUAL_SWORD_JUMP_ATTACK, RigAnimationId.DUAL_SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DUAL_SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.SPECIAL_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.BASIC_ULT, RigAnimationId.SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_SPECIAL_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.DUAL_SWORD_ATTACK1, RigAnimationId.DUAL_SWORD_ATTACK2, RigAnimationId.DUAL_SWORD_ATTACK3,
                        RigAnimationId.DUAL_SWORD_ATTACK4, RigAnimationId.DUAL_SWORD_ATTACK5),
                List.of(RigAnimationId.DUAL_SWORD_DASH_ATTACK, RigAnimationId.DUAL_SWORD_JUMP_ATTACK, RigAnimationId.DUAL_SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DUAL_BASIC_ULT, RigAnimationId.DUAL_SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.BLACK_FIRE_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.BLACK_FIRE_SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DIAMOND_ATTRACTOR, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DIAMOND_ATTRACTOR_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DIAMOND_BLASTER, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.DIAMOND_BLASTER_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.HACKER_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.HACKER_SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.HOOKED_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.HOOK_SWORD_ULT1, RigAnimationId.HOOK_SWORD_ULT2, RigAnimationId.SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.FLANKER_HOOKED_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.FLANKER_HOOK_SWORD_ULT, RigAnimationId.SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.DUAL_HOOKED_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.DUAL_SWORD_ATTACK1, RigAnimationId.DUAL_SWORD_ATTACK2, RigAnimationId.DUAL_SWORD_ATTACK3,
                        RigAnimationId.DUAL_SWORD_ATTACK4, RigAnimationId.DUAL_SWORD_ATTACK5),
                List.of(RigAnimationId.DUAL_SWORD_DASH_ATTACK, RigAnimationId.DUAL_SWORD_JUMP_ATTACK, RigAnimationId.DUAL_SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.HOOK_SWORD_DUAL_ULT, RigAnimationId.DUAL_SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.GREAT_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.GREAT_SWORD_ULT, RigAnimationId.SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.WOOPIE_THE_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.SWORD_ATTACK1, RigAnimationId.SWORD_ATTACK2, RigAnimationId.SWORD_ATTACK3,
                        RigAnimationId.SWORD_ATTACK4, RigAnimationId.SWORD_ATTACK5),
                List.of(RigAnimationId.SWORD_DASH_ATTACK, RigAnimationId.SWORD_JUMP_ATTACK, RigAnimationId.SWORD_EXTRA_ATTACK, RigAnimationId.KICK_ATTACK1, RigAnimationId.KICK_ATTACK2, RigAnimationId.KICK_ATTACK3, RigAnimationId.KICK_ATTACK4, RigAnimationId.KICK_DASH_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT, RigAnimationId.WOOPIE_THE_SWORD_ULT, RigAnimationId.SWORD_ULT, RigAnimationId.KICK_COMBO_ATTACK, RigAnimationId.SWORD_ULT),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.BLUE_DEMON, new RigCombatProfile(
                List.of(RigAnimationId.BLUE_DEMON_ATTACK1, RigAnimationId.BLUE_DEMON_ATTACK2, RigAnimationId.BLUE_DEMON_ATTACK3,
                        RigAnimationId.BLUE_DEMON_ATTACK4, RigAnimationId.BLUE_DEMON_ATTACK5, RigAnimationId.BLUE_DEMON_ATTACK6),
                List.of(RigAnimationId.BLUE_DEMON_DASH_ATTACK, RigAnimationId.BLUE_DEMON_JUMP_ATTACK, RigAnimationId.BLUE_DEMON_EXTRA_ATTACK,
                        RigAnimationId.BLUE_DEMON_THROW_ATTACK1, RigAnimationId.BLUE_DEMON_THROW_ATTACK2, RigAnimationId.BLUE_DEMON_THROW_ATTACK3,
                        RigAnimationId.BLUE_DEMON_THROW_ATTACK4, RigAnimationId.BLUE_DEMON_THROW_ATTACK5, RigAnimationId.BLUE_DEMON_THROW_DASH_ATTACK, RigAnimationId.BLUE_DEMON_THROW_JUMP_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.BLUE_DEMON_ULT, RigAnimationId.BLUE_DEMON_THROW_ULT),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.5,
                RigLocomotionStyle.DEFAULT
        ));

        PROFILES.put(RigCombatStyle.BLUE_DEMON_LEGENDARY_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.LEGENDARY_SWORD_ATTACK1, RigAnimationId.LEGENDARY_SWORD_ATTACK2, RigAnimationId.LEGENDARY_SWORD_ATTACK3,
                        RigAnimationId.LEGENDARY_SWORD_ATTACK4, RigAnimationId.LEGENDARY_SWORD_ATTACK5),
                List.of(RigAnimationId.LEGENDARY_SWORD_DASH_ATTACK, RigAnimationId.LEGENDARY_SWORD_JUMP_ATTACK,
                        RigAnimationId.LEGENDARY_SWORD_EXTRA_ATTACK, RigAnimationId.BLUE_DEMON_EXTRA_ATTACK_LEGENDARY),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.LEGENDARY_SWORD_ULT, RigAnimationId.LEGENDARY_SWORD_EXTRA_ULT, RigAnimationId.BLUE_DEMON_ULT),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.5,
                RigLocomotionStyle.LEGENDARY_SWORD
        ));

        PROFILES.put(RigCombatStyle.LEGENDARY_SWORD, new RigCombatProfile(
                List.of(RigAnimationId.LEGENDARY_SWORD_ATTACK1, RigAnimationId.LEGENDARY_SWORD_ATTACK2, RigAnimationId.LEGENDARY_SWORD_ATTACK3,
                        RigAnimationId.LEGENDARY_SWORD_ATTACK4, RigAnimationId.LEGENDARY_SWORD_ATTACK5),
                List.of(RigAnimationId.LEGENDARY_SWORD_DASH_ATTACK, RigAnimationId.LEGENDARY_SWORD_JUMP_ATTACK,
                        RigAnimationId.LEGENDARY_SWORD_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.LEGENDARY_SWORD_ULT),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.5,
                RigLocomotionStyle.LEGENDARY_SWORD
        ));

        PROFILES.put(RigCombatStyle.LEGENDARY_SWORD_WOOPIE, new RigCombatProfile(
                List.of(RigAnimationId.LEGENDARY_SWORD_DUAL_AUTO1, RigAnimationId.LEGENDARY_SWORD_DUAL_AUTO2, RigAnimationId.LEGENDARY_SWORD_DUAL_AUTO3,
                        RigAnimationId.LEGENDARY_SWORD_ATTACK4, RigAnimationId.LEGENDARY_SWORD_ATTACK5),
                List.of(RigAnimationId.LEGENDARY_SWORD_DASH_ATTACK, RigAnimationId.LEGENDARY_SWORD_JUMP_ATTACK,
                        RigAnimationId.LEGENDARY_SWORD_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.WOOPIE_THE_SWORD_FLY, RigAnimationId.WOOPIE_THE_SWORD_EXTRA_ULT,
                        RigAnimationId.LEGENDARY_SWORD_EXTRA_ULT),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.5,
                RigLocomotionStyle.LEGENDARY_SWORD
        ));

        PROFILES.put(RigCombatStyle.LEGENDARY_SWORD_ANGRY_STEVE, new RigCombatProfile(
                List.of(RigAnimationId.LEGENDARY_SWORD_ATTACK1, RigAnimationId.LEGENDARY_SWORD_ATTACK2, RigAnimationId.LEGENDARY_SWORD_ATTACK3,
                        RigAnimationId.LEGENDARY_SWORD_ATTACK4, RigAnimationId.LEGENDARY_SWORD_ATTACK5),
                List.of(RigAnimationId.LEGENDARY_SWORD_DASH_ATTACK, RigAnimationId.LEGENDARY_SWORD_JUMP_ATTACK,
                        RigAnimationId.LEGENDARY_SWORD_EXTRA_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.LEGENDARY_SWORD_ULT, RigAnimationId.LEGENDARY_SWORD_EXTRA_ULT),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.5,
                RigLocomotionStyle.LEGENDARY_SWORD
        ));

        PROFILES.put(RigCombatStyle.AEGIS_HEROBRINE, new RigCombatProfile(
                List.of(RigAnimationId.AEGIS_HEROBRINE_ATTACK1, RigAnimationId.AEGIS_HEROBRINE_ATTACK2, RigAnimationId.AEGIS_HEROBRINE_ATTACK3,
                        RigAnimationId.AEGIS_HEROBRINE_ATTACK4, RigAnimationId.AEGIS_HEROBRINE_ATTACK5),
                List.of(RigAnimationId.AEGIS_HEROBRINE_DASH_ATTACK, RigAnimationId.AEGIS_HEROBRINE_JUMP_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT
                ),
                List.of(RigAnimationId.AEGIS_HEROBRINE_EXTRA_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.2,
                RigLocomotionStyle.AEGIS_HEROBRINE
        ));

        PROFILES.put(RigCombatStyle.GLAIVE_HEROBRINE,new RigCombatProfile(
                List.of(RigAnimationId.GLAIVE_HEROBRINE_ATTACK1, RigAnimationId.GLAIVE_HEROBRINE_ATTACK2, RigAnimationId.GLAIVE_HEROBRINE_ATTACK3,
                        RigAnimationId.GLAIVE_HEROBRINE_ATTACK4, RigAnimationId.GLAIVE_HEROBRINE_ATTACK5),
                List.of(RigAnimationId.GLAIVE_HEROBRINE_DASH_ATTACK, RigAnimationId.GLAIVE_HEROBRINE_JUMP_ATTACK),
            List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT),
                List.of(RigAnimationId.GLAIVE_HEROBRINE_EXTRA_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.2,
                RigLocomotionStyle.ELITE_HEROBRINE
        ));

        PROFILES.put(RigCombatStyle.REAPER_HEROBRINE,new RigCombatProfile(
                List.of(RigAnimationId.REAPER_HEROBRINE_ATTACK1, RigAnimationId.REAPER_HEROBRINE_ATTACK2, RigAnimationId.REAPER_HEROBRINE_ATTACK3,
                        RigAnimationId.REAPER_HEROBRINE_ATTACK4, RigAnimationId.REAPER_HEROBRINE_ATTACK5),
                List.of(RigAnimationId.REAPER_HEROBRINE_DASH_ATTACK, RigAnimationId.REAPER_HEROBRINE_JUMP_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT),
                List.of(RigAnimationId.REAPER_HEROBRINE_EXTRA_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.2,
                RigLocomotionStyle.REAPER_HEROBRINE
        ));

        PROFILES.put(RigCombatStyle.SLEDGEHAMMER_HEROBRINE,new RigCombatProfile(
                List.of(RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK1, RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK2, RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK3,
                        RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK4, RigAnimationId.SLEDGEHAMMER_HEROBRINE_ATTACK5),
                List.of(RigAnimationId.SLEDGEHAMMER_HEROBRINE_DASH_ATTACK, RigAnimationId.SLEDGEHAMMER_HEROBRINE_JUMP_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT),
                List.of(RigAnimationId.SLEDGEHAMMER_HEROBRINE_EXTRA_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.2,
                RigLocomotionStyle.ELITE_HEROBRINE
        ));

        PROFILES.put(RigCombatStyle.SWORDSMAN_HEROBRINE,new RigCombatProfile(
                List.of(RigAnimationId.SWORDSMAN_HEROBRINE_ATTACK1, RigAnimationId.SWORDSMAN_HEROBRINE_ATTACK2, RigAnimationId.SWORDSMAN_HEROBRINE_ATTACK3,
                        RigAnimationId.SWORDSMAN_HEROBRINE_ATTACK4, RigAnimationId.SWORDSMAN_HEROBRINE_ATTACK5),
                List.of(RigAnimationId.SWORDSMAN_HEROBRINE_DASH_ATTACK, RigAnimationId.SWORDSMAN_HEROBRINE_JUMP_ATTACK),
                List.of(
                        RigAnimationId.ROLL_FORWARD,
                        RigAnimationId.ROLL_BACKWARD,
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT),
                List.of(RigAnimationId.SWORDSMAN_HEROBRINE_EXTRA_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.2,
                RigLocomotionStyle.ELITE_HEROBRINE
        ));

        PROFILES.put(RigCombatStyle.NULL_HEROBRINE, new RigCombatProfile(
                List.of(RigAnimationId.NULL_ATTACK1, RigAnimationId.NULL_ATTACK2, RigAnimationId.NULL_ATTACK3, RigAnimationId.NULL_ATTACK4),
                List.of(RigAnimationId.NULL_DASH_ATTACK, RigAnimationId.NULL_JUMP_ATTACK),
                List.of(
                        RigAnimationId.STEP_FORWARD,
                        RigAnimationId.STEP_BACKWARD,
                        RigAnimationId.STEP_LEFT,
                        RigAnimationId.STEP_RIGHT),
                List.of(RigAnimationId.NULL_ATTACK5, RigAnimationId.NULL_EXTRA_ATTACK),
                SPECIAL_ATTACK_CHANCE,
                ROLLING_CHANCE,
                ULTIMATE_CHANCE * 1.5D,
                RigLocomotionStyle.NULL_HEROBRINE
        ));
    }

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

            RigCombatStyle mainStyle = mainProvider.getRigCombatStyle(mainHand);
            if (mob instanceof AngrySteveEntity && mainStyle == RigCombatStyle.LEGENDARY_SWORD) {
                return getProfile(RigCombatStyle.LEGENDARY_SWORD_ANGRY_STEVE);
            }
            return getProfile(mainStyle);
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

