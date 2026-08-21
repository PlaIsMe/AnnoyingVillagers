package com.pla.annoyingvillagers.rig.pose;

import com.pla.annoyingvillagers.rig.RigColliderAnchor;

import java.util.EnumMap;
import java.util.Map;

public final class RigPoseClip {
    private static final float TICKS_PER_SECOND = 20.0F;
    private final Map<RigColliderAnchor, Track> tracks;

    private RigPoseClip(Map<RigColliderAnchor, Track> tracks) {
        this.tracks = tracks;
    }

    public static RigPoseClip of(Part... parts) {
        EnumMap<RigColliderAnchor, Track> map = new EnumMap<>(RigColliderAnchor.class);
        for (Part part : parts) map.put(part.anchor(), part.track());
        return new RigPoseClip(map);
    }

    public static Part part(RigColliderAnchor anchor, float[] position, float[] rotation) {
        return new Part(anchor, new Track(position, rotation));
    }

    public Pose sample(RigColliderAnchor anchor, float elapsedTicks) {
        Track track = this.tracks.get(anchor);
        return track == null ? Pose.ZERO : track.sample(Math.max(0.0F, elapsedTicks) / TICKS_PER_SECOND);
    }

    public boolean has(RigColliderAnchor anchor) {
        return this.tracks.containsKey(anchor);
    }

    public double maxHorizontalDistanceModelUnits(RigColliderAnchor anchor) {
        Track track = this.tracks.get(anchor);
        return track == null ? 0.0D : track.maxHorizontalDistanceModelUnits();
    }

    public record Part(RigColliderAnchor anchor, Track track) {}

    public record Pose(float x, float y, float z, float xRot, float yRot, float zRot) {
        public static final Pose ZERO = new Pose(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    public static final class Track {
        private static final float DEG_TO_RAD = (float) Math.PI / 180.0F;
        private final float[] position;
        private final float[] rotation;

        private Track(float[] position, float[] rotation) {
            this.position = position == null ? new float[0] : position.clone();
            this.rotation = rotation == null ? new float[0] : rotation.clone();
        }

        private Pose sample(float timeSeconds) {
            Vec position = sampleVec(this.position, timeSeconds);
            Vec rotation = sampleVec(this.rotation, timeSeconds);
            return new Pose(position.x, -position.y, position.z, rotation.x * DEG_TO_RAD, rotation.y * DEG_TO_RAD, rotation.z * DEG_TO_RAD);
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

        private double maxHorizontalDistanceModelUnits() {
            double max = 0.0D;
            for (int i = 0; i + 3 < this.position.length; i += 4) max = Math.max(max, Math.sqrt(this.position[i + 1] * this.position[i + 1] + this.position[i + 3] * this.position[i + 3]));
            return max;
        }

        private static float lerp(float start, float end, float alpha) { return start + (end - start) * alpha; }
        private record Vec(float x, float y, float z) { private static final Vec ZERO = new Vec(0.0F, 0.0F, 0.0F); }
    }
}
