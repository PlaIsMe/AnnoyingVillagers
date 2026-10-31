package com.pla.annoyinglib.rig.pose.generated;

import com.pla.annoyinglib.rig.RigAnimationId;
import com.pla.annoyinglib.rig.pose.RigPoseClip;

import java.util.Map;

public final class RigRecoveryPoseData {
    private RigRecoveryPoseData() {}

    public static void register(Map<RigAnimationId, RigPoseClip> clips) {
        clips.put(RigAnimationId.DIG_MAINHAND, dig_mainhand());
        clips.put(RigAnimationId.USE_MAINHAND, use_mainhand());
    }

    private static RigPoseClip dig_mainhand() {
        return RigPoseClip.of(
        );
    }

    private static RigPoseClip use_mainhand() {
        return RigPoseClip.of(
        );
    }

}
