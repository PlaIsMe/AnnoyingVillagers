package com.pla.annoyingvillagers.rig.armor;

import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigColliderAnchor;
import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import com.pla.annoyingvillagers.rig.pose.RigPoseSampler;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.Deque;

public final class ObsidianArmorPoseSampler {
    private static final float MODEL_UNITS_PER_BLOCK = 16.0F;
    private static final double MODEL_ORIGIN_HEIGHT = 1.5D;

    private ObsidianArmorPoseSampler() {}

    public static RigPartTransform sample(LivingEntity wearer, SpecialAnimationId animationId, float elapsedTicks, String boneName) {
        ObsidianArmorPart armorPart = ObsidianArmorPart.fromAnimationId(animationId);
        ObsidianArmorSkeleton skeleton = ObsidianArmorPoseLibrary.skeleton(armorPart);
        ObsidianArmorPoseClip clip = ObsidianArmorPoseLibrary.clip(animationId);
        ObsidianArmorSkeleton.Bone target = skeleton.get(boneName);
        if (target == null) throw new IllegalArgumentException("Unknown " + armorPart + " armor bone: " + boneName);
        ObsidianArmorSkeleton.Bone top = topLevelBone(skeleton, target);
        RigPartTransform base = baseTransform(wearer, top);
        RigPartTransform relative = sampleRelative(skeleton, clip, elapsedTicks, target, top);
        return compose(base, relative);
    }

    private static ObsidianArmorSkeleton.Bone topLevelBone(ObsidianArmorSkeleton skeleton, ObsidianArmorSkeleton.Bone bone) {
        ObsidianArmorSkeleton.Bone current = bone;
        while (current.parent() != null) {
            ObsidianArmorSkeleton.Bone parent = skeleton.get(current.parent());
            if (parent == null) break;
            current = parent;
        }
        return current;
    }

    private static RigPartTransform sampleRelative(ObsidianArmorSkeleton skeleton, ObsidianArmorPoseClip clip, float elapsedTicks, ObsidianArmorSkeleton.Bone target, ObsidianArmorSkeleton.Bone top) {
        Deque<ObsidianArmorSkeleton.Bone> chain = new ArrayDeque<>();
        ObsidianArmorSkeleton.Bone current = target;
        while (current != null && current != top) {
            chain.push(current);
            current = current.parent() == null ? null : skeleton.get(current.parent());
        }

        Matrix4f matrix = new Matrix4f();
        while (!chain.isEmpty()) {
            ObsidianArmorSkeleton.Bone bone = chain.pop();
            ObsidianArmorPoseClip.Pose pose = clip.sampleServer(bone.name(), elapsedTicks);
            matrix.translate((bone.pivotX() + pose.x()) / MODEL_UNITS_PER_BLOCK, (bone.pivotY() + pose.y()) / MODEL_UNITS_PER_BLOCK, (bone.pivotZ() + pose.z()) / MODEL_UNITS_PER_BLOCK);
            matrix.rotateZYX(bone.zRot() + pose.zRot(), bone.yRot() + pose.yRot(), bone.xRot() + pose.xRot());
        }

        Vector3f origin = matrix.transformPosition(new Vector3f());
        Vector3f axisX = matrix.transformDirection(new Vector3f(1.0F, 0.0F, 0.0F)).normalize();
        Vector3f axisY = matrix.transformDirection(new Vector3f(0.0F, 1.0F, 0.0F)).normalize();
        Vector3f axisZ = matrix.transformDirection(new Vector3f(0.0F, 0.0F, 1.0F)).normalize();
        return new RigPartTransform(new Vec3(origin.x, origin.y, origin.z), new Vec3(axisX.x, axisX.y, axisX.z), new Vec3(axisY.x, axisY.y, axisY.z), new Vec3(axisZ.x, axisZ.y, axisZ.z));
    }

    private static RigPartTransform baseTransform(LivingEntity wearer, ObsidianArmorSkeleton.Bone top) {
        if (wearer instanceof Mob mob) {
            RigAnimationId active = RigAnimationController.getActiveAnimationId(mob);
            RigColliderAnchor anchor = anchorForTop(top.name());
            if (active != null && anchor != null) {
                int startTick = RigAnimationController.getActiveAnimationStartTick(mob);
                return RigPoseSampler.sample(mob, active, Math.max(0, mob.tickCount - startTick), anchor, mob.yBodyRot);
            }
        }
        return staticBaseTransform(wearer, top);
    }

    private static RigPartTransform staticBaseTransform(LivingEntity wearer, ObsidianArmorSkeleton.Bone top) {
        Matrix4f matrix = new Matrix4f();
        float xRot = top.xRot();
        float yRot = top.yRot();
        float zRot = top.zRot();
        if ("Head".equals(top.name())) {
            xRot += (float)Math.toRadians(wearer.getXRot());
            yRot += (float)Math.toRadians(wearer.getYHeadRot() - wearer.yBodyRot);
        }
        matrix.translate(top.pivotX() / MODEL_UNITS_PER_BLOCK, top.pivotY() / MODEL_UNITS_PER_BLOCK, top.pivotZ() / MODEL_UNITS_PER_BLOCK);
        matrix.rotateZYX(zRot, yRot, xRot);

        Vector3f origin = matrix.transformPosition(new Vector3f());
        Vector3f axisX = matrix.transformDirection(new Vector3f(1.0F, 0.0F, 0.0F)).normalize();
        Vector3f axisY = matrix.transformDirection(new Vector3f(0.0F, 1.0F, 0.0F)).normalize();
        Vector3f axisZ = matrix.transformDirection(new Vector3f(0.0F, 0.0F, 1.0F)).normalize();
        RigPartTransform local = new RigPartTransform(new Vec3(-origin.x, MODEL_ORIGIN_HEIGHT - origin.y, -origin.z), new Vec3(-axisX.x, -axisX.y, -axisX.z), new Vec3(-axisY.x, -axisY.y, -axisY.z), new Vec3(-axisZ.x, -axisZ.y, -axisZ.z));
        return toWorld(local, wearer.position(), wearer.yBodyRot);
    }

    private static RigPartTransform compose(RigPartTransform base, RigPartTransform relative) {
        Vec3 origin = base.transformPoint(relative.origin());
        Vec3 axisX = base.transformDirection(relative.axisX()).normalize();
        Vec3 axisY = base.transformDirection(relative.axisY()).normalize();
        Vec3 axisZ = base.transformDirection(relative.axisZ()).normalize();
        return new RigPartTransform(origin, axisX, axisY, axisZ);
    }

    private static RigColliderAnchor anchorForTop(String name) {
        return switch (name) {
            case "Head" -> RigColliderAnchor.HEAD;
            case "Body" -> RigColliderAnchor.BODY;
            case "RightArm" -> RigColliderAnchor.RIGHT_ARM;
            case "LeftArm" -> RigColliderAnchor.LEFT_ARM;
            default -> null;
        };
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
