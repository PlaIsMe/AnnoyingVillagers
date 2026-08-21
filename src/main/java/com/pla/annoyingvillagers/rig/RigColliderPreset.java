package com.pla.annoyingvillagers.rig;

import net.minecraft.world.phys.Vec3;

public enum RigColliderPreset {
    FIST(0.42D, 0.42D, 0.48D, 0.0D, 0.0D, -0.12D),
    DAGGER(0.26D, 0.26D, 0.72D, 0.0D, 0.0D, -0.31D),
    SWORD(0.30D, 0.30D, 1.16D, 0.0D, 0.0D, -0.53D),
    LONGSWORD(0.32D, 0.34D, 1.40D, 0.0D, 0.0D, -0.64D),
    GREATSWORD(0.38D, 0.42D, 1.55D, 0.0D, 0.0D, -0.72D),
    SPEAR(0.24D, 0.24D, 2.05D, 0.0D, 0.0D, -0.96D),
    AXE(0.52D, 0.54D, 1.10D, 0.0D, 0.0D, -0.48D),
    TACHI(0.30D, 0.30D, 1.36D, 0.0D, 0.0D, -0.62D),
    GLAIVE(0.34D, 0.34D, 2.05D, 0.0D, 0.0D, -0.95D),
    SCYTHE(0.58D, 0.42D, 1.82D, 0.0D, 0.0D, -0.82D),
    SLEDGEHAMMER(0.62D, 0.62D, 1.45D, 0.0D, 0.0D, -0.63D),
    FOOT(0.44D, 0.36D, 0.56D, 0.0D, 0.0D, -0.16D),
    BODY(0.90D, 1.25D, 0.62D, 0.0D, 0.38D, 0.0D);

    private final double sizeX;
    private final double sizeY;
    private final double sizeZ;
    private final Vec3 center;

    RigColliderPreset(double sizeX, double sizeY, double sizeZ, double centerX, double centerY, double centerZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.center = new Vec3(centerX, centerY, centerZ);
    }

    public double halfX() { return this.sizeX * 0.5D; }
    public double halfY() { return this.sizeY * 0.5D; }
    public double halfZ() { return this.sizeZ * 0.5D; }
    public Vec3 center() { return this.center; }
}
