package com.pla.annoyingvillagers.rig.pose;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigColliderAnchor;
import com.pla.annoyingvillagers.rig.pose.generated.RigGeneratedPoseRegistry;
import net.minecraft.world.phys.Vec3;
import java.util.EnumMap;
import java.util.Map;

public final class RigPoseLibrary {
    private static final double MODEL_UNITS_PER_BLOCK = 16.0D;
    private static final double EPSILON = 1.0E-6D;
    private static final Map<RigAnimationId, RigPoseClip> CLIPS = new EnumMap<>(RigAnimationId.class);

    static {
        RigGeneratedPoseRegistry.register(CLIPS);

    }

    private RigPoseLibrary() {}

    public static RigPoseClip get(RigAnimationId animationId) {
        RigPoseClip clip = CLIPS.get(animationId);
        if (clip == null) throw new IllegalArgumentException("No collider pose data for " + animationId);
        return clip;
    }

    public static RigPoseClip find(RigAnimationId animationId) {
        return CLIPS.get(animationId);
    }

    public static boolean hasMotion(RigAnimationId animationId) {
        RigPoseClip clip = CLIPS.get(animationId);
        return clip != null && clip.has(RigColliderAnchor.BODY);
    }

    public static Vec3 modelMotion(RigAnimationId animationId, float elapsedTicks) {
        RigPoseClip clip = CLIPS.get(animationId);
        if (clip == null) return Vec3.ZERO;
        RigPoseClip.Pose body = clip.sample(RigColliderAnchor.BODY, elapsedTicks);
        return new Vec3(body.x(), 0.0D, body.z());
    }

    public static Vec3 worldMotionDelta(RigAnimationId animationId, float previousElapsedTicks, float elapsedTicks, Vec3 forward) {
        Vec3 previous = modelMotion(animationId, previousElapsedTicks);
        Vec3 current = modelMotion(animationId, elapsedTicks);
        double sideBlocks = (previous.x - current.x) / MODEL_UNITS_PER_BLOCK;
        double forwardBlocks = (previous.z - current.z) / MODEL_UNITS_PER_BLOCK;
        if (Math.abs(sideBlocks) < EPSILON && Math.abs(forwardBlocks) < EPSILON) return Vec3.ZERO;
        Vec3 horizontalForward = horizontalForward(forward);
        Vec3 right = new Vec3(-horizontalForward.z, 0.0D, horizontalForward.x);
        return right.scale(sideBlocks).add(horizontalForward.scale(forwardBlocks));
    }

    public static double maxHorizontalMotionBlocks(RigAnimationId animationId) {
        RigPoseClip clip = CLIPS.get(animationId);
        if (clip == null) return 0.0D;
        return clip.maxHorizontalDistanceModelUnits(RigColliderAnchor.BODY) / MODEL_UNITS_PER_BLOCK;
    }

    private static Vec3 horizontalForward(Vec3 forward) {
        Vec3 horizontal = new Vec3(forward.x, 0.0D, forward.z);
        return horizontal.lengthSqr() < EPSILON ? new Vec3(0.0D, 0.0D, 1.0D) : horizontal.normalize();
    }
}
