package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.rig.pose.RigPoseSampler;
import net.minecraft.world.entity.Mob;

public record RigCollider(RigColliderAnchor anchor, RigColliderPreset preset) {
    public RigCollider {
        if (anchor == null) throw new IllegalArgumentException("anchor cannot be null");
        if (preset == null) throw new IllegalArgumentException("preset cannot be null");
    }

    public static RigCollider of(RigColliderAnchor anchor, RigColliderPreset preset) { return new RigCollider(anchor, preset); }

    public RigOrientedBox worldBox(Mob mob, RigAnimationId animationId, float elapsedTicks) {
        return RigOrientedBox.from(RigPoseSampler.sample(mob, animationId, elapsedTicks, this.anchor), this.preset);
    }

    public RigOrientedBox worldBox(Mob mob, RigAnimationId animationId, float elapsedTicks, float bodyYaw) {
        return RigOrientedBox.from(RigPoseSampler.sample(mob, animationId, elapsedTicks, this.anchor, bodyYaw), this.preset);
    }

    public RigOrientedBox localBox(RigAnimationId animationId, float elapsedTicks) {
        return RigOrientedBox.from(RigPoseSampler.sampleLocal(animationId, elapsedTicks, this.anchor), this.preset);
    }
}
