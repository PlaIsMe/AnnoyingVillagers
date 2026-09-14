package com.pla.annoyingvillagers.specialanimation.pose;

import com.pla.annoyingvillagers.rig.pose.generated.special.SpecialGeneratedPoseRegistry;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;

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
}
