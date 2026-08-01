package com.pla.annoyingvillagers.rig;

public record RigAnimationSpec(
        RigAnimationId animationId,
        int durationTicks,
        int impactDelayTicks,
        double attackReachBlocks,
        RigMovementType movementType,
        double lungeDistanceBlocks,
        double jumpStrength,
        boolean damagesTarget
) {
    public RigAnimationSpec {
        if (animationId == null) {
            throw new IllegalArgumentException("animationId cannot be null");
        }
        if (durationTicks <= 0) {
            throw new IllegalArgumentException("durationTicks must be > 0");
        }
        if (impactDelayTicks < -1) {
            throw new IllegalArgumentException("impactDelayTicks must be >= -1");
        }
        if (attackReachBlocks < 0.0D) {
            throw new IllegalArgumentException("attackReachBlocks must be >= 0");
        }
        if (lungeDistanceBlocks < 0.0D) {
            throw new IllegalArgumentException("lungeDistanceBlocks must be >= 0");
        }
        if (jumpStrength < 0.0D) {
            throw new IllegalArgumentException("jumpStrength must be >= 0");
        }
        if (damagesTarget && !animationId.isAttack()) {
            throw new IllegalArgumentException("Only *_ATTACK animations can damage targets");
        }
    }

    public static RigAnimationSpec normalAttack(RigAnimationId animationId, int durationTicks, int impactDelayTicks) {
        return attack(animationId, durationTicks, impactDelayTicks, 3.0D, RigMovementType.NONE, 0.0D, 0.0D);
    }

    public static RigAnimationSpec dashAttack(RigAnimationId animationId, int durationTicks, int impactDelayTicks, double lungeDistanceBlocks) {
        return attack(animationId, durationTicks, impactDelayTicks, 3.4D, RigMovementType.LUNGE, lungeDistanceBlocks, 0.0D);
    }

    public static RigAnimationSpec jumpAttack(RigAnimationId animationId, int durationTicks, int impactDelayTicks, double jumpStrength) {
        return attack(animationId, durationTicks, impactDelayTicks, 3.2D, RigMovementType.JUMP, 0.0D, jumpStrength);
    }

    public static RigAnimationSpec jumpTowardAttack(RigAnimationId animationId, int durationTicks, int impactDelayTicks, double jumpStrength, double lungeDistanceBlocks) {
        return attack(animationId, durationTicks, impactDelayTicks, 3.6D, RigMovementType.JUMP_LUNGE, lungeDistanceBlocks, jumpStrength);
    }

    public static RigAnimationSpec ultimateAttack(RigAnimationId animationId, int durationTicks, int impactDelayTicks, RigMovementType movementType, double lungeDistanceBlocks, double jumpStrength) {
        return attack(animationId, durationTicks, impactDelayTicks, 4.0D, movementType, lungeDistanceBlocks, jumpStrength);
    }

    public static RigAnimationSpec rolling(RigAnimationId animationId, int durationTicks, RigMovementType movementType, double rollDistanceBlocks) {
        if (!animationId.isRolling()) {
            throw new IllegalArgumentException("Rolling specs require ROLL_* or STEP_* animation ids");
        }

        return new RigAnimationSpec(animationId, durationTicks, -1, 0.0D, movementType, rollDistanceBlocks, 0.0D, false);
    }

    private static RigAnimationSpec attack(RigAnimationId animationId, int durationTicks, int impactDelayTicks, double attackReachBlocks, RigMovementType movementType, double lungeDistanceBlocks, double jumpStrength) {
        if (!animationId.isAttack()) {
            throw new IllegalArgumentException("Attack specs require sword attack animation ids");
        }

        return new RigAnimationSpec(animationId, durationTicks, impactDelayTicks, attackReachBlocks, movementType, lungeDistanceBlocks, jumpStrength, true);
    }
}
