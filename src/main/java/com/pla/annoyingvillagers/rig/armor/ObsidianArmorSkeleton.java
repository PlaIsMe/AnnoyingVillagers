package com.pla.annoyingvillagers.rig.armor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ObsidianArmorSkeleton {
    private final Map<String, Bone> bones;

    private ObsidianArmorSkeleton(Bone[] bones) {
        LinkedHashMap<String, Bone> map = new LinkedHashMap<>();
        Arrays.stream(bones).forEach(bone -> map.put(bone.name(), bone));
        this.bones = Map.copyOf(map);
    }

    public static ObsidianArmorSkeleton of(Bone... bones) {
        return new ObsidianArmorSkeleton(bones);
    }

    public static Bone bone(String name, String parent, float pivotX, float pivotY, float pivotZ, float xRot, float yRot, float zRot) {
        return new Bone(name, parent, pivotX, pivotY, pivotZ, xRot, yRot, zRot);
    }

    public Bone get(String name) {
        return this.bones.get(name);
    }

    public record Bone(String name, String parent, float pivotX, float pivotY, float pivotZ, float xRot, float yRot, float zRot) {
        public boolean isTopLevel() {
            return this.parent == null;
        }
    }
}
