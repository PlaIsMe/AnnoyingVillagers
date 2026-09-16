package com.pla.annoyingvillagers.rig.armor;

import com.pla.annoyingvillagers.rig.pose.generated.armor.ObsidianArmorGeneratedPoseRegistry;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ObsidianArmorPoseLibrary {
    private static final Map<ObsidianArmorPart, ObsidianArmorSkeleton> SKELETONS = new EnumMap<>(ObsidianArmorPart.class);
    private static final Map<SpecialAnimationId, ObsidianArmorPoseClip> CLIPS = new EnumMap<>(SpecialAnimationId.class);
    private static final Map<ObsidianArmorPart, List<ObsidianArmorColliderSpec>> COLLIDERS = new EnumMap<>(ObsidianArmorPart.class);

    static {
        ObsidianArmorGeneratedPoseRegistry.register(SKELETONS, CLIPS, COLLIDERS);
    }

    private ObsidianArmorPoseLibrary() {}

    public static ObsidianArmorSkeleton skeleton(ObsidianArmorPart part) {
        return SKELETONS.get(part);
    }

    public static ObsidianArmorPoseClip clip(SpecialAnimationId animationId) {
        ObsidianArmorPart.fromAnimationId(animationId);
        ObsidianArmorPoseClip clip = CLIPS.get(animationId);
        if (clip == null) throw new IllegalArgumentException("Missing generated armor pose clip for " + animationId);
        return clip;
    }

    public static List<ObsidianArmorColliderSpec> colliders(ObsidianArmorPart part) {
        return COLLIDERS.getOrDefault(part, List.of());
    }
}
