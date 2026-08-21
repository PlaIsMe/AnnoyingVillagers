package com.pla.annoyingvillagers.rig.pose;

import net.minecraft.world.phys.Vec3;

public record RigPartTransform(Vec3 origin, Vec3 axisX, Vec3 axisY, Vec3 axisZ) {
    public Vec3 transformPoint(Vec3 point) {
        return this.origin.add(this.axisX.scale(point.x)).add(this.axisY.scale(point.y)).add(this.axisZ.scale(point.z));
    }

    public Vec3 transformDirection(Vec3 direction) {
        return this.axisX.scale(direction.x).add(this.axisY.scale(direction.y)).add(this.axisZ.scale(direction.z));
    }
}
