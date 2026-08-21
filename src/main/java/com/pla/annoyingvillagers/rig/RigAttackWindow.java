package com.pla.annoyingvillagers.rig;

public record RigAttackWindow(int startTickInclusive, int endTickExclusive, RigCollider[] colliders) {
    public RigAttackWindow {
        if (startTickInclusive < 0) throw new IllegalArgumentException("startTickInclusive must be >= 0");
        if (endTickExclusive <= startTickInclusive) throw new IllegalArgumentException("endTickExclusive must be > startTickInclusive");
        if (colliders == null) throw new IllegalArgumentException("colliders cannot be null");
        colliders = colliders.clone();
        for (RigCollider collider : colliders) if (collider == null) throw new IllegalArgumentException("colliders cannot contain null");
    }

    public static RigAttackWindow of(int startTickInclusive, int endTickExclusive, RigCollider... colliders) {
        return new RigAttackWindow(startTickInclusive, endTickExclusive, colliders);
    }

    @Override
    public RigCollider[] colliders() { return this.colliders.clone(); }

    public boolean contains(float elapsedTicks) { return elapsedTicks >= this.startTickInclusive && elapsedTicks < this.endTickExclusive; }

    public boolean hasColliders() { return this.colliders.length > 0; }

    public int fallbackImpactTick() { return Math.max(this.startTickInclusive, this.endTickExclusive - 1); }
}
