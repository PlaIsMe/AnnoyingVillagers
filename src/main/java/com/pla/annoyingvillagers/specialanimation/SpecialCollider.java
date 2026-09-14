package com.pla.annoyingvillagers.specialanimation;

import com.pla.annoyingvillagers.rig.RigOrientedBox;
import com.pla.annoyingvillagers.specialanimation.pose.SpecialPoseSampler;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public record SpecialCollider(String boneName, double halfX, double halfY, double halfZ, Vec3 center) {
    public SpecialCollider {
        if (boneName == null || boneName.isBlank()) throw new IllegalArgumentException("boneName cannot be blank");
        if (center == null) center = Vec3.ZERO;
    }

    public static SpecialCollider box(String boneName, double halfX, double halfY, double halfZ) {
        return new SpecialCollider(boneName, halfX, halfY, halfZ, Vec3.ZERO);
    }

    public static SpecialCollider box(String boneName, double halfX, double halfY, double halfZ, double centerX, double centerY, double centerZ) {
        return new SpecialCollider(boneName, halfX, halfY, halfZ, new Vec3(centerX, centerY, centerZ));
    }

    public RigOrientedBox worldBox(Mob mob, SpecialAnimationId animationId, float elapsedTicks, float bodyYaw) {
        return RigOrientedBox.from(SpecialPoseSampler.sample(mob, animationId, elapsedTicks, this.boneName, bodyYaw), this.halfX, this.halfY, this.halfZ, this.center);
    }
}
