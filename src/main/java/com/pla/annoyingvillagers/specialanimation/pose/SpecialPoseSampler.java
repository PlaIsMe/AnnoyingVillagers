package com.pla.annoyingvillagers.specialanimation.pose;

import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.Deque;

public final class SpecialPoseSampler {
    private static final float MODEL_UNITS_PER_BLOCK = 16.0F;
    private static final double MODEL_ORIGIN_HEIGHT = 1.5D;

    private SpecialPoseSampler() {
    }

    public static RigPartTransform sample(Mob mob, SpecialAnimationId animationId, float elapsedTicks, String boneName, float bodyYaw) {
        return sample(animationId, elapsedTicks, boneName, mob.position(), bodyYaw);
    }

    public static RigPartTransform sample(SpecialAnimationId animationId, float elapsedTicks, String boneName, Vec3 entityPosition, float bodyYaw) {
        return toWorld(sampleLocal(animationId, elapsedTicks, boneName), entityPosition, bodyYaw);
    }

    public static RigPartTransform sampleLocal(SpecialAnimationId animationId, float elapsedTicks, String boneName) {
        SpecialSkeleton skeleton = SpecialPoseLibrary.skeleton(animationId.family());
        SpecialPoseClip clip = SpecialPoseLibrary.get(animationId);
        Deque<SpecialSkeleton.Bone> chain = new ArrayDeque<>();
        SpecialSkeleton.Bone bone = skeleton.bone(boneName);
        while (bone != null) {
            chain.push(bone);
            bone = bone.parent() == null ? null : skeleton.bone(bone.parent());
        }

        Matrix4f matrix = new Matrix4f();
        Vec3 rootMotion = usesServerRootMotion(animationId) ? SpecialPoseLibrary.accumulatedRootMotion(animationId, elapsedTicks) : Vec3.ZERO;
        while (!chain.isEmpty()) {
            SpecialSkeleton.Bone part = chain.pop();
            SpecialPoseClip.Pose pose = clip.sample(part.name(), elapsedTicks);
            float x = part.pivotX() + pose.position().x();
            float y = part.pivotY() + pose.position().y();
            float z = part.pivotZ() + pose.position().z();
            if ("Root".equals(part.name())) {
                x -= (float)rootMotion.x;
                z -= (float)rootMotion.z;
            }
            matrix.translate(x / MODEL_UNITS_PER_BLOCK, y / MODEL_UNITS_PER_BLOCK, z / MODEL_UNITS_PER_BLOCK);
            matrix.rotateZYX(part.zRot() + pose.rotation().z(), part.yRot() + pose.rotation().y(), part.xRot() + pose.rotation().x());
        }

        Vector3f origin = matrix.transformPosition(new Vector3f());
        Vector3f xAxis = matrix.transformDirection(new Vector3f(1.0F, 0.0F, 0.0F)).normalize();
        Vector3f yAxis = matrix.transformDirection(new Vector3f(0.0F, 1.0F, 0.0F)).normalize();
        Vector3f zAxis = matrix.transformDirection(new Vector3f(0.0F, 0.0F, 1.0F)).normalize();
        return new RigPartTransform(new Vec3(-origin.x, MODEL_ORIGIN_HEIGHT - origin.y, -origin.z), new Vec3(-xAxis.x, -xAxis.y, -xAxis.z), new Vec3(-yAxis.x, -yAxis.y, -yAxis.z), new Vec3(-zAxis.x, -zAxis.y, -zAxis.z));
    }

    private static boolean usesServerRootMotion(SpecialAnimationId animationId) {
        return animationId.family() == SpecialAnimationFamily.AV_GOLEM || animationId.family() == SpecialAnimationFamily.AV_WARDEN;
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
