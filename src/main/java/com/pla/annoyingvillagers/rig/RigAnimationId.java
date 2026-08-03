package com.pla.annoyingvillagers.rig;

public enum RigAnimationId {
    SWORD_AUTO1,
    SWORD_AUTO2,
    SWORD_AUTO3,
    SWORD_AUTO4,
    SWORD_DASH,
    SWORD_AIRSLASH,
    SWEEPING_EDGE,
    SWORD_MOUNT_ATTACK,
    SWORD_DUAL_AUTO1,
    SWORD_DUAL_AUTO2,
    SWORD_DUAL_AUTO3,
    SWORD_DUAL_DASH,
    SWORD_DUAL_AIRSLASH,
    DANCING_EDGE,
    ROLL_BACKWARD,
    ROLL_FORWARD,
    STEP_FORWARD,
    STEP_BACKWARD,
    STEP_LEFT,
    STEP_RIGHT,
    JUMP,
    BOW_AIM_DOWN,
    BOW_AIM_MID,
    BOW_AIM_UP,
    BOW_SHOT_DOWN,
    BOW_SHOT_MID,
    BOW_SHOT_UP,
    KNOCKDOWN_WAKEUP_LEFT,
    KNOCKDOWN_WAKEUP_RIGHT,
    EAT_OFFHAND,
    EAT_MAINHAND,
    THROW_ENDER_PEARL,
    SHIELD_OFFHAND,
    HIT_SHIELD_OFFHAND;

    private static final RigAnimationId[] VALUES = values();

    public int networkId() {
        return this.ordinal();
    }

    public boolean isAttack() {
        return switch (this) {
            case SWORD_AUTO1, SWORD_AUTO2, SWORD_AUTO3, SWORD_AUTO4,
                 SWORD_DASH, SWORD_AIRSLASH, SWEEPING_EDGE, SWORD_MOUNT_ATTACK,
                 SWORD_DUAL_AUTO1, SWORD_DUAL_AUTO2, SWORD_DUAL_AUTO3,
                 SWORD_DUAL_DASH, SWORD_DUAL_AIRSLASH, DANCING_EDGE -> true;
            default -> false;
        };
    }

    public boolean isMountedAttack() {
        return this == SWORD_MOUNT_ATTACK;
    }

    public boolean isRolling() {
        return isRollAnimation() || isStepAnimation();
    }

    public boolean isUtilityAnimation() {
        return switch (this) {
            case BOW_AIM_DOWN, BOW_AIM_MID, BOW_AIM_UP,
                 BOW_SHOT_DOWN, BOW_SHOT_MID, BOW_SHOT_UP,
                 KNOCKDOWN_WAKEUP_LEFT, KNOCKDOWN_WAKEUP_RIGHT,
                 EAT_MAINHAND, EAT_OFFHAND, THROW_ENDER_PEARL,
                 SHIELD_OFFHAND, HIT_SHIELD_OFFHAND -> true;
            default -> false;
        };
    }

    public boolean isShieldGuardAnimation() {
        return this == SHIELD_OFFHAND;
    }

    public boolean isShieldGuardHitAnimation() {
        return this == HIT_SHIELD_OFFHAND;
    }

    public boolean isBowAnimation() {
        return isBowAimAnimation() || isBowShotAnimation();
    }

    public boolean isBowAimAnimation() {
        return switch (this) {
            case BOW_AIM_DOWN, BOW_AIM_MID, BOW_AIM_UP -> true;
            default -> false;
        };
    }

    public boolean isBowShotAnimation() {
        return switch (this) {
            case BOW_SHOT_DOWN, BOW_SHOT_MID, BOW_SHOT_UP -> true;
            default -> false;
        };
    }

    public boolean isRollAnimation() {
        String name = this.name();
        return name.startsWith("ROLL_") || name.contains("_ROLL");
    }

    public boolean isStepAnimation() {
        return switch (this) {
            case STEP_FORWARD, STEP_BACKWARD, STEP_LEFT, STEP_RIGHT -> true;
            default -> false;
        };
    }

    public boolean isUltimateAttack() {
        return switch (this) {
            case DANCING_EDGE -> true;
            default -> false;
        };
    }

    public static RigAnimationId fromNetworkId(int networkId) {
        if (networkId < 0 || networkId >= VALUES.length) {
            return SWORD_AUTO1;
        }

        return VALUES[networkId];
    }
}
