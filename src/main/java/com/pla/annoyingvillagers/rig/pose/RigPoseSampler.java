package com.pla.annoyingvillagers.rig.pose;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigColliderAnchor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.Deque;

public final class RigPoseSampler {
    private static final float MODEL_UNITS_PER_BLOCK = 16.0F;
    private static final double MODEL_ORIGIN_HEIGHT = 1.5D;

    private RigPoseSampler() {}

    public static RigPartTransform sample(Mob mob, RigAnimationId animationId, float elapsedTicks, RigColliderAnchor anchor) {
        return toWorld(sampleLocal(animationId, elapsedTicks, anchor), mob.position(), mob.yBodyRot);
    }

    public static RigPartTransform sample(Mob mob, RigAnimationId animationId, float elapsedTicks, RigColliderAnchor anchor, float bodyYaw) {
        return toWorld(sampleLocal(animationId, elapsedTicks, anchor), mob.position(), bodyYaw);
    }

    public static RigPartTransform sampleLocal(RigAnimationId animationId, float elapsedTicks, RigColliderAnchor anchor) {
        RigPoseClip clip = RigPoseLibrary.get(animationId);
        Matrix4f matrix = new Matrix4f();
        Vec3 motion = RigPoseLibrary.modelMotion(animationId, elapsedTicks);
        Deque<RigColliderAnchor> chain = new ArrayDeque<>();
        for (RigColliderAnchor part = anchor; part != null; part = part.parent()) chain.push(part);

        while (!chain.isEmpty()) {
            RigColliderAnchor part = chain.pop();
            if (part == RigColliderAnchor.ROOT) continue;
            RigPoseClip.Pose pose = clip.sample(part, elapsedTicks);
            float x = part.pivotX() + pose.x();
            float y = part.pivotY() + pose.y();
            float z = part.pivotZ() + pose.z();
            if (part.isTopLevel()) {
                x -= (float) motion.x;
                z -= (float) motion.z;
            }
            matrix.translate(x / MODEL_UNITS_PER_BLOCK, y / MODEL_UNITS_PER_BLOCK, z / MODEL_UNITS_PER_BLOCK);
            matrix.rotateZYX(pose.zRot(), pose.yRot(), pose.xRot());
        }

        Vector3f origin = matrix.transformPosition(new Vector3f());
        Vector3f xAxis = matrix.transformDirection(new Vector3f(1.0F, 0.0F, 0.0F)).normalize();
        Vector3f yAxis = matrix.transformDirection(new Vector3f(0.0F, 1.0F, 0.0F)).normalize();
        Vector3f zAxis = matrix.transformDirection(new Vector3f(0.0F, 0.0F, 1.0F)).normalize();
        return new RigPartTransform(
                new Vec3(-origin.x, MODEL_ORIGIN_HEIGHT - origin.y, -origin.z),
                new Vec3(-xAxis.x, -xAxis.y, -xAxis.z),
                new Vec3(-yAxis.x, -yAxis.y, -yAxis.z),
                new Vec3(-zAxis.x, -zAxis.y, -zAxis.z)
        );
    }

    private static RigPartTransform toWorld(RigPartTransform local, Vec3 entityPosition, float bodyYaw) {
        Vec3 forward = Vec3.directionFromRotation(0.0F, bodyYaw);
        forward = new Vec3(forward.x, 0.0D, forward.z);
        if (forward.lengthSqr() < 1.0E-8D) forward = new Vec3(0.0D, 0.0D, 1.0D);
        forward = forward.normalize();
        Vec3 right = new Vec3(-forward.z, 0.0D, forward.x);
        Vec3 origin = entityPosition.add(toWorldVector(local.origin(), right, forward));
        return new RigPartTransform(origin, toWorldVector(local.axisX(), right, forward).normalize(), toWorldVector(local.axisY(), right, forward).normalize(), toWorldVector(local.axisZ(), right, forward).normalize());
    }

    private static Vec3 toWorldVector(Vec3 local, Vec3 right, Vec3 forward) {
        return right.scale(local.x).add(0.0D, local.y, 0.0D).add(forward.scale(local.z));
    }
}
