package com.pla.annoyinglib.rig.pose.generated.armor;

import com.pla.annoyinglib.rig.armor.ObsidianArmorColliderSpec;
import com.pla.annoyinglib.rig.armor.ObsidianArmorPart;
import com.pla.annoyinglib.rig.armor.ObsidianArmorPoseClip;
import com.pla.annoyinglib.rig.armor.ObsidianArmorSkeleton;
import com.pla.annoyinglib.specialanimation.SpecialAnimationId;

import java.util.List;
import java.util.Map;

public final class ObsidianArmorGeneratedPoseRegistry {
    private ObsidianArmorGeneratedPoseRegistry() {}

    public static void register(Map<ObsidianArmorPart, ObsidianArmorSkeleton> skeletons, Map<SpecialAnimationId, ObsidianArmorPoseClip> clips, Map<ObsidianArmorPart, List<ObsidianArmorColliderSpec>> colliders) {
        ObsidianArmorSkeletonData.register(skeletons, colliders);
        ObsidianHelmetPoseData1.register(clips);
        ObsidianHelmetPoseData2.register(clips);
        ObsidianHelmetPoseData3.register(clips);
        ObsidianHelmetPoseData4.register(clips);
        ObsidianHelmetPoseData5.register(clips);
        ObsidianHelmetPoseData6.register(clips);
        ObsidianChestplatePoseData1.register(clips);
        ObsidianChestplatePoseData2.register(clips);
        ObsidianChestplatePoseData3.register(clips);
        ObsidianChestplatePoseData4.register(clips);
    }
}
