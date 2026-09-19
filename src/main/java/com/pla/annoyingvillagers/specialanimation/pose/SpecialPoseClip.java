package com.pla.annoyingvillagers.specialanimation.pose;

import java.util.HashMap;
import java.util.Map;

public final class SpecialPoseClip {
    private static final float TICKS_PER_SECOND = 20.0F;
    private final float lengthTicks;
    private final Map<String, Track> tracks;

    private SpecialPoseClip(float lengthTicks, Map<String, Track> tracks) {
        this.lengthTicks = lengthTicks;
        this.tracks = tracks;
    }

    public static SpecialPoseClip of(float lengthTicks, Bone... bones) {
        Map<String, Track> tracks = new HashMap<>();
        for (Bone bone : bones) tracks.put(bone.name(), bone.track());
        return new SpecialPoseClip(lengthTicks, Map.copyOf(tracks));
    }

    public static Bone bone(String name, float[] position, float[] rotation) {
        return new Bone(name, new Track(position, rotation));
    }

    public float lengthTicks() {
        return this.lengthTicks;
    }

    public Pose sample(String boneName, float elapsedTicks) {
        Track track = this.tracks.get(boneName);
        return track == null ? Pose.ZERO : track.sample(Math.max(0.0F, elapsedTicks) / TICKS_PER_SECOND);
    }

    public Pose sampleNonReversingRootMotion(String boneName, float elapsedTicks) {
        Track track = this.tracks.get(boneName);
        return track == null ? Pose.ZERO : track.sampleNonReversingRootMotion(Math.max(0.0F, elapsedTicks) / TICKS_PER_SECOND);
    }

    public record Bone(String name, Track track) {
    }

    public record Pose(Vector position, Vector rotation) {
        public static final Pose ZERO = new Pose(Vector.ZERO, Vector.ZERO);
    }

    public record Vector(float x, float y, float z) {
        public static final Vector ZERO = new Vector(0.0F, 0.0F, 0.0F);
    }

    public static final class Track {
        private static final float DEG_TO_RAD = (float)Math.PI / 180.0F;
        private final float[] position;
        private final float[] rotation;

        private Track(float[] position, float[] rotation) {
            this.position = position == null ? new float[0] : position.clone();
            this.rotation = rotation == null ? new float[0] : rotation.clone();
        }

        private Pose sample(float timeSeconds) {
            Vector position = sampleVector(this.position, timeSeconds);
            Vector rotation = sampleVector(this.rotation, timeSeconds);
            return new Pose(new Vector(position.x(), -position.y(), position.z()), new Vector(rotation.x() * DEG_TO_RAD, rotation.y() * DEG_TO_RAD, rotation.z() * DEG_TO_RAD));
        }

        private Pose sampleNonReversingRootMotion(float timeSeconds) {
            Vector position = sampleVector(this.position, timeSeconds);
            return new Pose(new Vector(position.x(), -position.y(), nonReversingZ(timeSeconds)), Vector.ZERO);
        }

        private float nonReversingZ(float timeSeconds) {
            if (this.position.length < 4) return 0.0F;
            float startZ = this.position[3];
            float minZ = startZ;
            float maxZ = startZ;
            for (int i = 3; i < this.position.length; i += 4) {
                minZ = Math.min(minZ, this.position[i]);
                maxZ = Math.max(maxZ, this.position[i]);
            }
            boolean forward = startZ - minZ >= maxZ - startZ;
            float effectiveZ = startZ;
            for (int i = 0; i + 3 < this.position.length && this.position[i] <= timeSeconds; i += 4) {
                effectiveZ = forward ? Math.min(effectiveZ, this.position[i + 3]) : Math.max(effectiveZ, this.position[i + 3]);
            }
            float sampledZ = sampleVector(this.position, timeSeconds).z();
            return forward ? Math.min(effectiveZ, sampledZ) : Math.max(effectiveZ, sampledZ);
        }

        private static Vector sampleVector(float[] data, float timeSeconds) {
            if (data.length < 4) return Vector.ZERO;
            if (timeSeconds <= data[0]) return valueAt(data, 0);
            int last = data.length - 4;
            if (timeSeconds >= data[last]) return valueAt(data, last);
            for (int i = 4; i + 3 < data.length; i += 4) {
                if (timeSeconds > data[i]) continue;
                int previous = i - 4;
                float span = data[i] - data[previous];
                float alpha = span <= 0.0F ? 1.0F : (timeSeconds - data[previous]) / span;
                Vector before = valueAt(data, previous);
                Vector after = valueAt(data, i);
                return new Vector(lerp(before.x(), after.x(), alpha), lerp(before.y(), after.y(), alpha), lerp(before.z(), after.z(), alpha));
            }
            return valueAt(data, last);
        }

        private static Vector valueAt(float[] data, int offset) {
            return new Vector(data[offset + 1], data[offset + 2], data[offset + 3]);
        }

        private static float lerp(float start, float end, float alpha) {
            return start + (end - start) * alpha;
        }
    }
}
