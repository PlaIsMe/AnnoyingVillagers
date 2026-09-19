package com.pla.annoyingvillagers.specialanimation.pose;

import com.pla.annoyingvillagers.rig.pose.generated.special.SpecialGeneratedPoseRegistry;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.phys.Vec3;

import java.util.EnumMap;
import java.util.Map;

public final class SpecialPoseLibrary {
    private static final Map<SpecialAnimationFamily, SpecialSkeleton> SKELETONS = new EnumMap<>(SpecialAnimationFamily.class);
    private static final Map<SpecialAnimationId, SpecialPoseClip> CLIPS = new EnumMap<>(SpecialAnimationId.class);

    static {
        SpecialGeneratedPoseRegistry.register(SKELETONS, CLIPS);
    }

    private SpecialPoseLibrary() {
    }

    public static SpecialSkeleton skeleton(SpecialAnimationFamily family) {
        SpecialSkeleton skeleton = SKELETONS.get(family);
        if (skeleton == null) throw new IllegalArgumentException("No generated special skeleton for " + family);
        return skeleton;
    }

    public static SpecialPoseClip get(SpecialAnimationId id) {
        SpecialPoseClip clip = CLIPS.get(id);
        if (clip == null) throw new IllegalArgumentException("No generated special pose data for " + id);
        return clip;
    }

    public static Vec3 rootMotion(SpecialAnimationId id, float elapsedTicks) {
        SpecialPoseClip.Pose root = get(id).sample("Root", elapsedTicks);
        return new Vec3(root.position().x(), root.position().y(), root.position().z());
    }

    public static Vec3 accumulatedRootMotion(SpecialAnimationId id, float elapsedTicks) {
        return rootMotion(id, elapsedTicks).subtract(rootMotion(id, 0.0F));
    }

    public static Vec3 serverRootMotion(SpecialAnimationId id, float elapsedTicks) {
        SpecialPoseClip.Pose root = get(id).sampleNonReversingRootMotion("Root", elapsedTicks);
        return new Vec3(root.position().x(), root.position().y(), root.position().z());
    }

    public static Vec3 worldRootMotionDelta(SpecialAnimationId id, float previousElapsedTicks, float elapsedTicks, Vec3 forward) {
        Vec3 previous = serverRootMotion(id, previousElapsedTicks);
        Vec3 current = serverRootMotion(id, elapsedTicks);
        Vec3 horizontalForward = horizontalForward(forward);
        Vec3 right = new Vec3(-horizontalForward.z, 0.0D, horizontalForward.x);
        double sideBlocks = (previous.x - current.x) / 16.0D;
        double forwardBlocks = (previous.z - current.z) / 16.0D;
        return right.scale(sideBlocks).add(horizontalForward.scale(forwardBlocks));
    }

    private static Vec3 horizontalForward(Vec3 forward) {
        Vec3 horizontal = new Vec3(forward.x, 0.0D, forward.z);
        return horizontal.lengthSqr() < 1.0E-8D ? new Vec3(0.0D, 0.0D, 1.0D) : horizontal.normalize();
    }
}
