package com.pla.annoyingvillagers.specialanimation;

import java.util.HashMap;
import java.util.Map;

public enum SpecialAnimationId {
    WARDEN_ATK_1_1(SpecialAnimationFamily.AV_WARDEN, "ATK_1_1"),
    WARDEN_ATK_1_2(SpecialAnimationFamily.AV_WARDEN, "ATK_1_2"),
    WARDEN_ATK_2_1(SpecialAnimationFamily.AV_WARDEN, "ATK_2_1"),
    WARDEN_ATK_2_2(SpecialAnimationFamily.AV_WARDEN, "ATK_2_2"),
    WARDEN_ATK_2_3(SpecialAnimationFamily.AV_WARDEN, "ATK_2_3"),
    WARDEN_ATK_3_1(SpecialAnimationFamily.AV_WARDEN, "ATK_3_1"),
    WARDEN_ATK_3_2(SpecialAnimationFamily.AV_WARDEN, "ATK_3_2"),
    WARDEN_ATK_3_3(SpecialAnimationFamily.AV_WARDEN, "ATK_3_3"),
    WARDEN_ATK_4_1(SpecialAnimationFamily.AV_WARDEN, "ATK_4_1"),
    WARDEN_ATK_4_2(SpecialAnimationFamily.AV_WARDEN, "ATK_4_2"),
    WARDEN_SKILL_1(SpecialAnimationFamily.AV_WARDEN, "SKILL_1"),
    WARDEN_SKILL_2(SpecialAnimationFamily.AV_WARDEN, "SKILL_2"),
    WARDEN_SONIC_BOOM(SpecialAnimationFamily.AV_WARDEN, "SONIC_BOOM"),

    GOLEM_ATK_1_1(SpecialAnimationFamily.AV_GOLEM, "ATK_1_1"),
    GOLEM_ATK_1_2(SpecialAnimationFamily.AV_GOLEM, "ATK_1_2"),
    GOLEM_ATK_2_1(SpecialAnimationFamily.AV_GOLEM, "ATK_2_1"),
    GOLEM_ATK_2_2(SpecialAnimationFamily.AV_GOLEM, "ATK_2_2"),
    GOLEM_ATK_2_3(SpecialAnimationFamily.AV_GOLEM, "ATK_2_3"),
    GOLEM_ATK_3_1(SpecialAnimationFamily.AV_GOLEM, "ATK_3_1"),
    GOLEM_ATK_3_2(SpecialAnimationFamily.AV_GOLEM, "ATK_3_2"),
    GOLEM_ATK_3_3(SpecialAnimationFamily.AV_GOLEM, "ATK_3_3"),
    GOLEM_SKILL_1(SpecialAnimationFamily.AV_GOLEM, "SKILL_1"),
    GOLEM_SKILL_2(SpecialAnimationFamily.AV_GOLEM, "SKILL_2"),
    GOLEM_SKILL_3(SpecialAnimationFamily.AV_GOLEM, "SKILL_3"),
    GOLEM_SWORD_ATK_1_1(SpecialAnimationFamily.AV_GOLEM, "SWORD_ATK_1_1"),
    GOLEM_SWORD_ATK_1_2(SpecialAnimationFamily.AV_GOLEM, "SWORD_ATK_1_2"),
    GOLEM_SWORD_SKILL_1(SpecialAnimationFamily.AV_GOLEM, "SWORD_SKILL_1"),
    GOLEM_DUAL_SWORD_ATK_1_1(SpecialAnimationFamily.AV_GOLEM, "DUAL_SWORD_ATK_1_1"),
    GOLEM_DUAL_SWORD_ATK_1_2(SpecialAnimationFamily.AV_GOLEM, "DUAL_SWORD_ATK_1_2"),
    GOLEM_DUAL_SWORD_SKILL_1(SpecialAnimationFamily.AV_GOLEM, "DUAL_SWORD_SKILL_1"),
    GOLEM_AXE_ATK_1_1(SpecialAnimationFamily.AV_GOLEM, "AXE_ATK_1_1"),
    GOLEM_AXE_ATK_1_2(SpecialAnimationFamily.AV_GOLEM, "AXE_ATK_1_2"),
    GOLEM_AXE_SKILL_1(SpecialAnimationFamily.AV_GOLEM, "AXE_SKILL_1"),
    GOLEM_DUAL_AXE_ATK_1_1(SpecialAnimationFamily.AV_GOLEM, "DUAL_AXE_ATK_1_1"),
    GOLEM_DUAL_AXE_ATK_1_2(SpecialAnimationFamily.AV_GOLEM, "DUAL_AXE_ATK_1_2"),
    GOLEM_DUAL_AXE_ATK_1_3(SpecialAnimationFamily.AV_GOLEM, "DUAL_AXE_ATK_1_3"),
    GOLEM_DUAL_AXE_SKILL_1(SpecialAnimationFamily.AV_GOLEM, "DUAL_AXE_SKILL_1"),
    GOLEM_SPEAR_ATK_1_1(SpecialAnimationFamily.AV_GOLEM, "SPEAR_ATK_1_1"),
    GOLEM_SPEAR_ATK_1_2(SpecialAnimationFamily.AV_GOLEM, "SPEAR_ATK_1_2"),
    GOLEM_SPEAR_SKILL_1(SpecialAnimationFamily.AV_GOLEM, "SPEAR_SKILL_1"),

    ARMS_ATK_1(SpecialAnimationFamily.GOLEM_ARMS, "ATK_1"),
    ARMS_ATK_2(SpecialAnimationFamily.GOLEM_ARMS, "ATK_2"),
    ARMS_ATK_3(SpecialAnimationFamily.GOLEM_ARMS, "ATK_3"),
    ARMS_RUN_ATK(SpecialAnimationFamily.GOLEM_ARMS, "RUN_ATK"),
    ARMS_AIR_ATK(SpecialAnimationFamily.GOLEM_ARMS, "AIR_ATK"),
    ARMS_GUARD_TRANSFORM(SpecialAnimationFamily.GOLEM_ARMS, "GUARD_TRANSFORM"),
    ARMS_GUARD(SpecialAnimationFamily.GOLEM_ARMS, "GUARD"),
    ARMS_GUARD_FINISH(SpecialAnimationFamily.GOLEM_ARMS, "GUARD_FINISH"),

    OBSIDIAN_HELMET_EXTENDED_1(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_1"),
    OBSIDIAN_HELMET_EXTENDED_2(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_2"),
    OBSIDIAN_HELMET_EXTENDED_3(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_3"),
    OBSIDIAN_HELMET_EXTENDED_4(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_4"),
    OBSIDIAN_HELMET_EXTENDED_5(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_5"),
    OBSIDIAN_HELMET_EXTENDED_6(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_6"),
    OBSIDIAN_HELMET_EXTENDED_7(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_7"),
    OBSIDIAN_HELMET_EXTENDED_8(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_8"),
    OBSIDIAN_HELMET_EXTENDED_9(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_9"),
    OBSIDIAN_HELMET_EXTENDED_10(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_10"),
    OBSIDIAN_HELMET_EXTENDED_11(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_11"),
    OBSIDIAN_HELMET_EXTENDED_12(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_12"),
    OBSIDIAN_HELMET_EXTENDED_13(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_13"),
    OBSIDIAN_HELMET_EXTENDED_14(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_14"),
    OBSIDIAN_HELMET_EXTENDED_15(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_15"),
    OBSIDIAN_HELMET_EXTENDED_16(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_16"),
    OBSIDIAN_HELMET_EXTENDED_17(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_17"),
    OBSIDIAN_HELMET_EXTENDED_18(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_18"),
    OBSIDIAN_HELMET_EXTENDED_19(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_19"),
    OBSIDIAN_HELMET_EXTENDED_20(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_20"),
    OBSIDIAN_HELMET_EXTENDED_21(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_21"),
    OBSIDIAN_HELMET_EXTENDED_22(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_22"),
    OBSIDIAN_HELMET_EXTENDED_23(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_23"),
    OBSIDIAN_HELMET_EXTENDED_24(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_24"),
    OBSIDIAN_HELMET_EXTENDED_25(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_25"),
    OBSIDIAN_HELMET_EXTENDED_26(SpecialAnimationFamily.OBSIDIAN_DIAMOND_HELMET, "EXTENDED_26"),
    OBSIDIAN_CHESTPLATE_EXTENDED_1(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_1"),
    OBSIDIAN_CHESTPLATE_EXTENDED_2(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_2"),
    OBSIDIAN_CHESTPLATE_EXTENDED_3(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_3"),
    OBSIDIAN_CHESTPLATE_EXTENDED_4(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_4"),
    OBSIDIAN_CHESTPLATE_EXTENDED_5(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_5"),
    OBSIDIAN_CHESTPLATE_EXTENDED_6(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_6"),
    OBSIDIAN_CHESTPLATE_EXTENDED_7(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_7"),
    OBSIDIAN_CHESTPLATE_EXTENDED_8(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_8"),
    OBSIDIAN_CHESTPLATE_EXTENDED_9(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_9"),
    OBSIDIAN_CHESTPLATE_EXTENDED_10(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_10"),
    OBSIDIAN_CHESTPLATE_EXTENDED_11(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_11"),
    OBSIDIAN_CHESTPLATE_EXTENDED_12(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_12"),
    OBSIDIAN_CHESTPLATE_EXTENDED_13(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_13"),
    OBSIDIAN_CHESTPLATE_EXTENDED_14(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_14"),
    OBSIDIAN_CHESTPLATE_EXTENDED_15(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_15"),
    OBSIDIAN_CHESTPLATE_EXTENDED_16(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_16"),
    OBSIDIAN_CHESTPLATE_EXTENDED_17(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_17"),
    OBSIDIAN_CHESTPLATE_EXTENDED_18(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_18"),
    OBSIDIAN_CHESTPLATE_EXTENDED_19(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_19"),
    OBSIDIAN_CHESTPLATE_EXTENDED_20(SpecialAnimationFamily.OBSIDIAN_DIAMOND_CHESTPLATE, "EXTENDED_20");

    private static final Map<Integer, SpecialAnimationId> BY_NETWORK_ID = new HashMap<>();

    static {
        for (SpecialAnimationId id : values()) BY_NETWORK_ID.put(id.ordinal(), id);
    }

    private final SpecialAnimationFamily family;
    private final String fieldName;

    SpecialAnimationId(SpecialAnimationFamily family, String fieldName) {
        this.family = family;
        this.fieldName = fieldName;
    }

    public int networkId() {
        return this.ordinal();
    }

    public SpecialAnimationFamily family() {
        return this.family;
    }

    public String fieldName() {
        return this.fieldName;
    }


    public static SpecialAnimationId fromNetworkId(int id) {
        SpecialAnimationId animation = BY_NETWORK_ID.get(id);
        if (animation == null) throw new IllegalArgumentException("Unknown special animation network id " + id);
        return animation;
    }
}
