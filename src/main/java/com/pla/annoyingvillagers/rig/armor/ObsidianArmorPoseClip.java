package com.pla.annoyingvillagers.rig.armor;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ObsidianArmorPoseClip {
    private static final float TICKS_PER_SECOND = 20.0F;
    private static final float DEG_TO_RAD = (float)Math.PI / 180.0F;
    private final float lengthTicks;
    private final Map<String, Track> tracks;

    private ObsidianArmorPoseClip(float lengthTicks, BoneTrack[] tracks) {
        this.lengthTicks = lengthTicks;
        LinkedHashMap<String, Track> map = new LinkedHashMap<>();
        for (BoneTrack track : tracks) map.put(track.name(), track.track());
        this.tracks = Map.copyOf(map);
    }

    public static ObsidianArmorPoseClip of(float lengthTicks, BoneTrack... tracks) {
        return new ObsidianArmorPoseClip(lengthTicks, tracks);
    }

    public static BoneTrack bone(String name, float[] position, float[] rotation) {
        return new BoneTrack(name, new Track(position, rotation));
    }

    public float lengthTicks() {
        return this.lengthTicks;
    }

    public boolean hasTrack(String bone) {
        return this.tracks.containsKey(bone);
    }

    public java.util.Set<String> trackedBones() {
        return this.tracks.keySet();
    }

    public Pose sampleModel(String bone, float elapsedTicks) {
        Track track = this.tracks.get(bone);
        // KeyframeAnimations.posVec also negates Blockbench's Y translation.
        // Both model parts and collision sampling use Minecraft model coordinates.
        return track == null ? Pose.ZERO : track.sample(elapsedTicks, true);
    }

    public Pose sampleServer(String bone, float elapsedTicks) {
        Track track = this.tracks.get(bone);
        return track == null ? Pose.ZERO : track.sample(elapsedTicks, true);
    }

    public record BoneTrack(String name, Track track) {}

    public record Pose(float x, float y, float z, float xRot, float yRot, float zRot) {
        public static final Pose ZERO = new Pose(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    private static final class Track {
        private final float[] position;
        private final float[] rotation;

        private Track(float[] position, float[] rotation) {
            this.position = position == null ? new float[0] : position.clone();
            this.rotation = rotation == null ? new float[0] : rotation.clone();
        }

        private Pose sample(float elapsedTicks, boolean serverCoordinates) {
            float timeSeconds = Math.max(0.0F, elapsedTicks) / TICKS_PER_SECOND;
            Vec position = sampleVec(this.position, timeSeconds);
            Vec rotation = sampleVec(this.rotation, timeSeconds);
            float y = serverCoordinates ? -position.y : position.y;
            return new Pose(position.x, y, position.z, rotation.x * DEG_TO_RAD, rotation.y * DEG_TO_RAD, rotation.z * DEG_TO_RAD);
        }

        private static Vec sampleVec(float[] data, float timeSeconds) {
            if (data.length == 0) return Vec.ZERO;
            if (timeSeconds <= data[0]) return new Vec(data[1], data[2], data[3]);
            int last = data.length - 4;
            if (timeSeconds >= data[last]) return new Vec(data[last + 1], data[last + 2], data[last + 3]);
            for (int i = 4; i < data.length; i += 4) {
                if (timeSeconds > data[i]) continue;
                int previous = i - 4;
                float span = data[i] - data[previous];
                float alpha = span <= 0.0F ? 1.0F : (timeSeconds - data[previous]) / span;
                return new Vec(lerp(data[previous + 1], data[i + 1], alpha), lerp(data[previous + 2], data[i + 2], alpha), lerp(data[previous + 3], data[i + 3], alpha));
            }
            return new Vec(data[last + 1], data[last + 2], data[last + 3]);
        }

        private static float lerp(float start, float end, float alpha) {
            return start + (end - start) * alpha;
        }

        private record Vec(float x, float y, float z) {
            private static final Vec ZERO = new Vec(0.0F, 0.0F, 0.0F);
        }
    }
}
