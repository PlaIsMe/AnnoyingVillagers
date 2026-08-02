package com.pla.annoyingvillagers.rig;

public record RigAnimationSpec(
        RigAnimationId animationId,
        int durationTicks,
        RigAttackWindow[] attackWindows,
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
        if (attackWindows == null) {
            throw new IllegalArgumentException("attackWindows cannot be null");
        }
        attackWindows = attackWindows.clone();
        for (RigAttackWindow attackWindow : attackWindows) {
            if (attackWindow == null) {
                throw new IllegalArgumentException("attackWindows cannot contain null");
            }
            if (attackWindow.endTickExclusive() > durationTicks) {
                throw new IllegalArgumentException("attack window cannot end after durationTicks");
            }
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
        if (damagesTarget && attackWindows.length == 0) {
            throw new IllegalArgumentException("Damaging attack specs require at least one attack window");
        }
        if (!damagesTarget && attackWindows.length > 0) {
            throw new IllegalArgumentException("Non-damaging specs cannot define attack windows");
        }
    }

    public static RigAnimationSpec normalAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive) {
        return attack(animationId, durationTicks, 3.0D, RigMovementType.NONE, 0.0D, 0.0D, RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec normalAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive, RigMovementType movementType, double moveDistanceBlocks) {
        return attack(animationId, durationTicks, 3.0D, movementType, moveDistanceBlocks, 0.0D,
                RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec dashAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive, double lungeDistanceBlocks) {
        return attack(animationId, durationTicks, 3.4D, RigMovementType.LUNGE, lungeDistanceBlocks, 0.0D, RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec jumpAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive, double jumpStrength) {
        return attack(animationId, durationTicks, 3.2D, RigMovementType.JUMP, 0.0D, jumpStrength, RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec jumpTowardAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive, double jumpStrength, double lungeDistanceBlocks) {
        return attack(animationId, durationTicks, 3.6D, RigMovementType.JUMP_LUNGE, lungeDistanceBlocks, jumpStrength, RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec ultimateAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive, RigMovementType movementType, double lungeDistanceBlocks, double jumpStrength) {
        return attack(animationId, durationTicks, 4.0D, movementType, lungeDistanceBlocks, jumpStrength, RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec ultimateAttack(RigAnimationId animationId, int durationTicks, RigMovementType movementType, double lungeDistanceBlocks, double jumpStrength, RigAttackWindow... attackWindows) {
        return attack(animationId, durationTicks, 4.0D, movementType, lungeDistanceBlocks, jumpStrength, attackWindows);
    }

    public static RigAnimationSpec rolling(RigAnimationId animationId, int durationTicks, RigMovementType movementType, double rollDistanceBlocks) {
        if (!animationId.isRolling()) {
            throw new IllegalArgumentException("Rolling specs require ROLL_* or STEP_* animation ids");
        }

        return new RigAnimationSpec(animationId, durationTicks, new RigAttackWindow[0], 0.0D, movementType, rollDistanceBlocks, 0.0D, false);
    }

    public static RigAnimationSpec movementOnly(RigAnimationId animationId, int durationTicks, RigMovementType movementType, double lungeDistanceBlocks, double jumpStrength) {
        return new RigAnimationSpec(animationId, durationTicks, new RigAttackWindow[0], 0.0D, movementType, lungeDistanceBlocks, jumpStrength, false);
    }

    private static RigAnimationSpec attack(RigAnimationId animationId, int durationTicks, double attackReachBlocks, RigMovementType movementType, double lungeDistanceBlocks, double jumpStrength, RigAttackWindow... attackWindows) {
        if (!animationId.isAttack()) {
            throw new IllegalArgumentException("Attack specs require sword attack animation ids");
        }

        return new RigAnimationSpec(animationId, durationTicks, attackWindows, attackReachBlocks, movementType, lungeDistanceBlocks, jumpStrength, true);
    }

    @Override
    public RigAttackWindow[] attackWindows() {
        return this.attackWindows.clone();
    }

    public int[] impactDelayTicks() {
        int[] impactDelayTicks = new int[this.attackWindows.length];
        for (int i = 0; i < this.attackWindows.length; i++) {
            impactDelayTicks[i] = this.attackWindows[i].fallbackImpactTick();
        }

        return impactDelayTicks;
    }
}
