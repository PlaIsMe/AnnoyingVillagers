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
    ARMS_GUARD_FINISH(SpecialAnimationFamily.GOLEM_ARMS, "GUARD_FINISH");

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
