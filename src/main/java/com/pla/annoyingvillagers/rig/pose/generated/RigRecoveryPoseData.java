package com.pla.annoyingvillagers.rig.pose.generated;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.pose.RigPoseClip;

import java.util.Map;

import static com.pla.annoyingvillagers.rig.RigColliderAnchor.*;
import static com.pla.annoyingvillagers.rig.pose.RigPoseClip.part;

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
