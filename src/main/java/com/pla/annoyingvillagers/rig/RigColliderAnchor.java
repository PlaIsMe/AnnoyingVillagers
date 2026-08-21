package com.pla.annoyingvillagers.rig;

public enum RigColliderAnchor {
    ROOT(null, 0.0F, 0.0F, 0.0F),
    BODY(ROOT, 0.0F, 0.0F, 0.0F),
    HEAD(ROOT, 0.0F, 0.0F, 0.0F),
    RIGHT_ARM(ROOT, -5.0F, 2.0F, 0.0F),
    RIGHT_HAND(RIGHT_ARM, 0.0F, 4.0F, 0.0F),
    RIGHT_TOOL(RIGHT_HAND, 0.0F, 6.0F, 0.0F),
    LEFT_ARM(ROOT, 5.0F, 2.0F, 0.0F),
    LEFT_HAND(LEFT_ARM, 0.0F, 4.0F, 0.0F),
    LEFT_TOOL(LEFT_HAND, 0.0F, 6.0F, 0.0F),
    RIGHT_LEG(ROOT, -1.9F, 12.0F, 0.0F),
    RIGHT_LOWER_LEG(RIGHT_LEG, 0.0F, 6.0F, 0.0F),
    LEFT_LEG(ROOT, 1.9F, 12.0F, 0.0F),
    LEFT_LOWER_LEG(LEFT_LEG, 0.0F, 6.0F, 0.0F);

    private final RigColliderAnchor parent;
    private final float pivotX;
    private final float pivotY;
    private final float pivotZ;

    RigColliderAnchor(RigColliderAnchor parent, float pivotX, float pivotY, float pivotZ) {
        this.parent = parent;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.pivotZ = pivotZ;
    }

    public RigColliderAnchor parent() { return this.parent; }
    public float pivotX() { return this.pivotX; }
    public float pivotY() { return this.pivotY; }
    public float pivotZ() { return this.pivotZ; }
    public boolean isTopLevel() { return this.parent == ROOT; }
}
