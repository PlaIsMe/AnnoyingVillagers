package com.pla.annoyingvillagers.rig.armor;

import net.minecraft.world.phys.Vec3;

public record ObsidianArmorColliderSpec(String bone, double halfX, double halfY, double halfZ, double centerX, double centerY, double centerZ) {
    public Vec3 center() {
        return new Vec3(this.centerX, this.centerY, this.centerZ);
    }
}
