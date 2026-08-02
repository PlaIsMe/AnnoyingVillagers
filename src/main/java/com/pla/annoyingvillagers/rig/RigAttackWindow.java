package com.pla.annoyingvillagers.rig;

public record RigAttackWindow(int startTickInclusive, int endTickExclusive) {
    public RigAttackWindow {
        if (startTickInclusive < 0) {
            throw new IllegalArgumentException("startTickInclusive must be >= 0");
        }
        if (endTickExclusive <= startTickInclusive) {
            throw new IllegalArgumentException("endTickExclusive must be > startTickInclusive");
        }
    }

    public static RigAttackWindow of(int startTickInclusive, int endTickExclusive) {
        return new RigAttackWindow(startTickInclusive, endTickExclusive);
    }

    public int fallbackImpactTick() {
        return Math.max(this.startTickInclusive, this.endTickExclusive - 1);
    }
}
