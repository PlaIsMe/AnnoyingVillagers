package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class RigOrientedBox {
    private static final double EPSILON = 1.0E-7D;
    private final Vec3 center;
    private final Vec3 axisX;
    private final Vec3 axisY;
    private final Vec3 axisZ;
    private final double halfX;
    private final double halfY;
    private final double halfZ;

    private RigOrientedBox(Vec3 center, Vec3 axisX, Vec3 axisY, Vec3 axisZ, double halfX, double halfY, double halfZ) {
        this.center = center;
        this.axisX = axisX.normalize();
        this.axisY = axisY.normalize();
        this.axisZ = axisZ.normalize();
        this.halfX = halfX;
        this.halfY = halfY;
        this.halfZ = halfZ;
    }

    public static RigOrientedBox from(RigPartTransform transform, RigColliderPreset preset) {
        return new RigOrientedBox(transform.transformPoint(preset.center()), transform.axisX(), transform.axisY(), transform.axisZ(), preset.halfX(), preset.halfY(), preset.halfZ());
    }

    public static RigOrientedBox from(RigPartTransform transform, double halfX, double halfY, double halfZ, Vec3 center) {
        return new RigOrientedBox(transform.transformPoint(center), transform.axisX(), transform.axisY(), transform.axisZ(), halfX, halfY, halfZ);
    }

    public Vec3 center() { return this.center; }
    public Vec3 axisX() { return this.axisX; }
    public Vec3 axisY() { return this.axisY; }
    public Vec3 axisZ() { return this.axisZ; }
    public double halfX() { return this.halfX; }
    public double halfY() { return this.halfY; }
    public double halfZ() { return this.halfZ; }

    public AABB bounds() {
        double x = Math.abs(this.axisX.x) * this.halfX + Math.abs(this.axisY.x) * this.halfY + Math.abs(this.axisZ.x) * this.halfZ;
        double y = Math.abs(this.axisX.y) * this.halfX + Math.abs(this.axisY.y) * this.halfY + Math.abs(this.axisZ.y) * this.halfZ;
        double z = Math.abs(this.axisX.z) * this.halfX + Math.abs(this.axisY.z) * this.halfY + Math.abs(this.axisZ.z) * this.halfZ;
        return new AABB(this.center.x - x, this.center.y - y, this.center.z - z, this.center.x + x, this.center.y + y, this.center.z + z);
    }

    public Vec3[] corners() {
        Vec3[] result = new Vec3[8];
        int index = 0;
        for (int x = -1; x <= 1; x += 2) for (int y = -1; y <= 1; y += 2) for (int z = -1; z <= 1; z += 2) {
            result[index++] = this.center.add(this.axisX.scale(this.halfX * x)).add(this.axisY.scale(this.halfY * y)).add(this.axisZ.scale(this.halfZ * z));
        }
        return result;
    }

    public double maxHorizontalDistanceFromOrigin() {
        double max = 0.0D;
        for (Vec3 corner : corners()) max = Math.max(max, Math.sqrt(corner.x * corner.x + corner.z * corner.z));
        return max;
    }

    public boolean intersects(RigOrientedBox other) {
        if (other == null) return false;

        Vec3[] aAxes = {this.axisX, this.axisY, this.axisZ};
        Vec3[] bAxes = {other.axisX, other.axisY, other.axisZ};
        double[] aHalf = {this.halfX, this.halfY, this.halfZ};
        double[] bHalf = {other.halfX, other.halfY, other.halfZ};
        double[][] rotation = new double[3][3];
        double[][] absRotation = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotation[i][j] = aAxes[i].dot(bAxes[j]);
                absRotation[i][j] = Math.abs(rotation[i][j]) + EPSILON;
            }
        }

        Vec3 delta = other.center.subtract(this.center);
        double[] translation = {
                delta.dot(aAxes[0]),
                delta.dot(aAxes[1]),
                delta.dot(aAxes[2])
        };

        // The three local axes of this box.
        for (int i = 0; i < 3; i++) {
            double radiusOther = bHalf[0] * absRotation[i][0]
                    + bHalf[1] * absRotation[i][1]
                    + bHalf[2] * absRotation[i][2];
            if (Math.abs(translation[i]) > aHalf[i] + radiusOther) return false;
        }

        // The three local axes of the other box.
        for (int j = 0; j < 3; j++) {
            double projected = Math.abs(translation[0] * rotation[0][j]
                    + translation[1] * rotation[1][j]
                    + translation[2] * rotation[2][j]);
            double radiusThis = aHalf[0] * absRotation[0][j]
                    + aHalf[1] * absRotation[1][j]
                    + aHalf[2] * absRotation[2][j];
            if (projected > radiusThis + bHalf[j]) return false;
        }

        // The nine axes made by crossing one axis from each box.
        for (int i = 0; i < 3; i++) {
            int i1 = (i + 1) % 3;
            int i2 = (i + 2) % 3;
            for (int j = 0; j < 3; j++) {
                int j1 = (j + 1) % 3;
                int j2 = (j + 2) % 3;
                double radiusThis = aHalf[i1] * absRotation[i2][j] + aHalf[i2] * absRotation[i1][j];
                double radiusOther = bHalf[j1] * absRotation[i][j2] + bHalf[j2] * absRotation[i][j1];
                double distance = Math.abs(translation[i2] * rotation[i1][j] - translation[i1] * rotation[i2][j]);
                if (distance > radiusThis + radiusOther) return false;
            }
        }

        return true;
    }

    public boolean intersects(AABB box) {
        Vec3 otherCenter = box.getCenter();
        double[] a = {this.halfX, this.halfY, this.halfZ};
        double[] b = {(box.maxX - box.minX) * 0.5D, (box.maxY - box.minY) * 0.5D, (box.maxZ - box.minZ) * 0.5D};
        Vec3[] u = {this.axisX, this.axisY, this.axisZ};
        double[][] r = new double[3][3];
        double[][] absR = new double[3][3];
        for (int i = 0; i < 3; i++) {
            r[i][0] = u[i].x;
            r[i][1] = u[i].y;
            r[i][2] = u[i].z;
            for (int j = 0; j < 3; j++) absR[i][j] = Math.abs(r[i][j]) + EPSILON;
        }

        Vec3 delta = otherCenter.subtract(this.center);
        double[] t = {delta.dot(u[0]), delta.dot(u[1]), delta.dot(u[2])};
        double[] worldT = {delta.x, delta.y, delta.z};

        for (int i = 0; i < 3; i++) {
            double radius = b[0] * absR[i][0] + b[1] * absR[i][1] + b[2] * absR[i][2];
            if (Math.abs(t[i]) > a[i] + radius) return false;
        }
        for (int j = 0; j < 3; j++) {
            double radius = a[0] * absR[0][j] + a[1] * absR[1][j] + a[2] * absR[2][j];
            if (Math.abs(worldT[j]) > b[j] + radius) return false;
        }
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
            int i1 = (i + 1) % 3;
            int i2 = (i + 2) % 3;
            int j1 = (j + 1) % 3;
            int j2 = (j + 2) % 3;
            double ra = a[i1] * absR[i2][j] + a[i2] * absR[i1][j];
            double rb = b[j1] * absR[i][j2] + b[j2] * absR[i][j1];
            double distance = Math.abs(t[i2] * r[i1][j] - t[i1] * r[i2][j]);
            if (distance > ra + rb) return false;
        }
        return true;
    }
}
