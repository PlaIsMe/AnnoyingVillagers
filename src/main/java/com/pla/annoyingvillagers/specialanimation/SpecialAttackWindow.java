package com.pla.annoyingvillagers.specialanimation;

public record SpecialAttackWindow(int startTickInclusive, int endTickExclusive, SpecialCollider[] colliders) {
    public SpecialAttackWindow {
        if (startTickInclusive < 0 || endTickExclusive <= startTickInclusive) throw new IllegalArgumentException("Invalid special attack window");
        if (colliders == null) colliders = new SpecialCollider[0];
    }

    public boolean contains(float tick) {
        return tick >= this.startTickInclusive && tick < this.endTickExclusive;
    }
}
