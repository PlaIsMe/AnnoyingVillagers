package com.pla.annoyinglib.rig.pose.generated.special;

import com.pla.annoyinglib.specialanimation.SpecialAnimationFamily;
import com.pla.annoyinglib.specialanimation.SpecialAnimationId;
import com.pla.annoyinglib.specialanimation.pose.SpecialPoseClip;
import com.pla.annoyinglib.specialanimation.pose.SpecialSkeleton;

import java.util.Map;

public final class SpecialGeneratedPoseRegistry {
    private SpecialGeneratedPoseRegistry() {
    }

    public static void register(Map<SpecialAnimationFamily, SpecialSkeleton> skeletons, Map<SpecialAnimationId, SpecialPoseClip> clips) {
        SpecialSkeletonData.register(skeletons);
        AvWardenPoseData1.register(clips);
        AvWardenPoseData2.register(clips);
        AvWardenPoseData3.register(clips);
        AvWardenPoseData4.register(clips);
        AvGolemPoseData1.register(clips);
        AvGolemPoseData2.register(clips);
        AvGolemPoseData3.register(clips);
        AvGolemPoseData4.register(clips);
        AvGolemPoseData5.register(clips);
        AvGolemPoseData6.register(clips);
        AvGolemPoseData7.register(clips);
        GolemArmsPoseData1.register(clips);
        GolemArmsPoseData2.register(clips);
    }
}
