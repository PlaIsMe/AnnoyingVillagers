package com.pla.annoyingvillagers.rig;

public record RigAnimationSpec(
        RigAnimationId animationId,
        int durationTicks,
        RigAttackWindow[] attackWindows,
        double attackReachBlocks,
        RigAnimationPlaybackType playbackType,
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
        if (playbackType == null) {
            throw new IllegalArgumentException("playbackType cannot be null");
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
        return normalAttack(animationId, durationTicks, attackStartTickInclusive, attackEndTickExclusive, 1.0D);
    }

    public static RigAnimationSpec normalAttack(RigAnimationId animationId, int durationTicks, int attackStartTickInclusive, int attackEndTickExclusive, double attackReachBlocks) {
        return attack(animationId, durationTicks, attackReachBlocks, RigAttackWindow.of(attackStartTickInclusive, attackEndTickExclusive));
    }

    public static RigAnimationSpec ultimateAttack(RigAnimationId animationId, int durationTicks, RigAttackWindow... attackWindows) {
        return attack(animationId, durationTicks, 4.0D, attackWindows);
    }

    public static RigAnimationSpec rolling(RigAnimationId animationId, int durationTicks) {
        if (!animationId.isRolling()) {
            throw new IllegalArgumentException("Rolling specs require ROLL_* or STEP_* animation ids");
        }

        return nonDamaging(animationId, durationTicks);
    }

    public static RigAnimationSpec nonDamaging(RigAnimationId animationId, int durationTicks) {
        return nonDamaging(animationId, durationTicks, RigAnimationPlaybackType.DEFAULT);
    }

    public static RigAnimationSpec nonDamaging(RigAnimationId animationId, int durationTicks, RigAnimationPlaybackType playbackType) {
        return new RigAnimationSpec(animationId, durationTicks, new RigAttackWindow[0], 0.0D, playbackType, false);
    }

    private static RigAnimationSpec attack(RigAnimationId animationId, int durationTicks, double attackReachBlocks, RigAttackWindow... attackWindows) {
        if (!animationId.isAttack()) {
            throw new IllegalArgumentException("Attack specs require sword attack animation ids");
        }

        return new RigAnimationSpec(animationId, durationTicks, attackWindows, attackReachBlocks, RigAnimationPlaybackType.DEFAULT, true);
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
