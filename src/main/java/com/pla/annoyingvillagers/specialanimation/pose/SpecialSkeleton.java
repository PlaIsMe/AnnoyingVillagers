package com.pla.annoyingvillagers.specialanimation.pose;

import java.util.HashMap;
import java.util.Map;

public record SpecialSkeleton(Map<String, Bone> bones) {
    public static SpecialSkeleton of(Bone... bones) {
        Map<String, Bone> values = new HashMap<>();
        for (Bone bone : bones) values.put(bone.name(), bone);
        return new SpecialSkeleton(Map.copyOf(values));
    }

    public static Bone bone(String name, String parent, float pivotX, float pivotY, float pivotZ, float xRot, float yRot, float zRot) {
        return new Bone(name, parent, pivotX, pivotY, pivotZ, xRot, yRot, zRot);
    }

    public Bone bone(String name) {
        Bone bone = this.bones.get(name);
        if (bone == null) throw new IllegalArgumentException("Missing special skeleton bone " + name);
        return bone;
    }

    public record Bone(String name, String parent, float pivotX, float pivotY, float pivotZ, float xRot, float yRot, float zRot) {
    }
}
