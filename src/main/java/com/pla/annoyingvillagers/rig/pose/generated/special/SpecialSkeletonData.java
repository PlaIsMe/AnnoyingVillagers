package com.pla.annoyingvillagers.rig.pose.generated.special;

import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.pose.SpecialSkeleton;

import java.util.Map;

import static com.pla.annoyingvillagers.specialanimation.pose.SpecialSkeleton.bone;

public final class SpecialSkeletonData {
    private SpecialSkeletonData() {
    }

    public static void register(Map<SpecialAnimationFamily, SpecialSkeleton> skeletons) {
        skeletons.put(SpecialAnimationFamily.AV_WARDEN, avwarden());
        skeletons.put(SpecialAnimationFamily.AV_GOLEM, avgolem());
        skeletons.put(SpecialAnimationFamily.GOLEM_ARMS, golemarms());
    }

    private static SpecialSkeleton avwarden() {
        return SpecialSkeleton.of(
                bone("Root", null, 0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("body", "Root", 0.0F, -13.0F, -1.0F, 0.0F, 0.0F, 0.0F),
                bone("chest", "body", 0.0F, -9.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("head", "chest", 0.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("ear_R", "head", -8.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.0F),
                bone("ear_L", "head", 8.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.0F),
                bone("shoudler_R", "chest", 0.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_up_R", "shoudler_R", -13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_down_R", "arm_up_R", 0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("bow_R", "arm_up_R", 0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("shoudler_L", "chest", 0.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_up_L", "shoudler_L", 13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_down_L", "arm_up_L", 0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("bow_L", "arm_up_L", 0.0F, 14.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("left_ribcage", "body", 7.0F, -10.0F, -4.0F, 0.0F, 0.0F, 0.0F),
                bone("left_bone_last_r1", "left_ribcage", 0.0F, 21.0F, 3.0F, 0.0F, -0.3491F, 0.0F),
                bone("right_ribcage", "body", -7.0F, -10.0F, -4.0F, 0.0F, 0.0F, 0.0F),
                bone("right_bone_last_r1", "right_ribcage", 7.0F, 21.0F, 1.0F, 0.0F, 0.3491F, 0.0F),
                bone("leg_up_R", "Root", -6.0F, -13.0F, -1.0F, 0.0F, 0.0F, 0.0F),
                bone("leg_down_R", "leg_up_R", 0.0F, 6.5F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("knee_R", "leg_up_R", 0.0F, 6.5F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("leg_up_L", "Root", 6.0F, -13.0F, -1.0F, 0.0F, 0.0F, 0.0F),
                bone("leg_down_L", "leg_up_L", 0.0F, 6.5F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("knee_L", "leg_up_L", 0.0F, 6.5F, 0.0F, 0.0F, 0.0F, 0.0F)
        );
    }

    private static SpecialSkeleton avgolem() {
        return SpecialSkeleton.of(
                bone("Root", null, 0.0F, 9.5018F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("body", "Root", 0.0F, -1.3465F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("chest", "body", 0.0F, -5.2822F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("head", "chest", 0.0F, -11.9829F, -2.5116F, 0.0F, 0.0F, 0.0F),
                bone("shoudler_L", "chest", 0.0F, -11.9829F, 0.0F, 0.0F, 0.0F, 1.7311F),
                bone("arm_1_L", "shoudler_L", -0.1662F, -11.07F, 0.0F, 0.0F, 0.0F, 1.4105F),
                bone("arm_2_L", "arm_1_L", 0.0F, -0.4856F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_3_L", "arm_2_L", 0.0F, -7.0938F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_4_L", "arm_3_L", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_5_L", "arm_4_L", 0.0F, -6.9809F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_6_L", "arm_5_L", 0.0F, -0.017F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_7_L", "arm_6_L", 0.0F, -6.6288F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("Tool_L", "arm_7_L", 0.0F, -5.4125F, 0.0F, 1.5708F, 0.0F, 0.0F),
                bone("shoudler_R", "chest", 0.0F, -11.9829F, 0.0F, 0.0F, 0.0F, -1.7311F),
                bone("arm_1_R", "shoudler_R", 0.1662F, -11.07F, 0.0F, 0.0F, 0.0F, -1.4105F),
                bone("arm_2_R", "arm_1_R", 0.0F, -0.4856F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_3_R", "arm_2_R", 0.0F, -7.0938F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_4_R", "arm_3_R", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_5_R", "arm_4_R", 0.0F, -6.9809F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_6_R", "arm_5_R", 0.0F, -0.017F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("arm_7_R", "arm_6_R", 0.0F, -6.6288F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("Tool_R", "arm_7_R", 0.0F, -5.4125F, 0.0F, 1.5708F, 0.0F, 0.0F),
                bone("leg_up_L", "Root", 4.5755F, -1.3465F, 0.0F, 1.5708F, -1.5642F, 1.5708F),
                bone("leg_down_L", "leg_up_L", 0.0F, -6.9034F, 0.0F, 0.0F, 0.0F, 0.0121F),
                bone("knee_L", "leg_up_L", 0.0F, -6.9034F, 0.0F, 0.0F, 0.0F, -1.5642F),
                bone("leg_up_R", "Root", -4.5755F, -1.3465F, 0.0F, 1.5708F, 1.5642F, -1.5708F),
                bone("leg_down_R", "leg_up_R", 0.0F, -6.9034F, 0.0F, 0.0F, 0.0F, -0.0121F),
                bone("knee_R", "leg_up_R", 0.0F, -6.9034F, 0.0F, 0.0F, 0.0F, 1.5642F),
                bone("arm_s_L", null, 10.9546F, -7.5071F, 0.0F, 0.0F, 0.0F, 3.1416F),
                bone("arm_s_R", null, -10.9546F, -7.5071F, 0.0F, 0.0F, 0.0F, 3.1416F)
        );
    }

    private static SpecialSkeleton golemarms() {
        return SpecialSkeleton.of(
                bone("Root", null, 0.0001F, 11.7765F, -0.0151F, 0.0F, 0.0F, 0.0F),
                bone("Torso", "Root", 0.0F, -0.8F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("Chest", "Torso", 0.0F, -4.8F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_up_1_L", "Chest", 3.2805F, -4.0356F, 4.9955F, 0.0F, 0.0F, 0.7854F),
                bone("garm_up_2_L", "garm_up_1_L", 0.0F, -5.507F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_up_3_L", "garm_up_2_L", 0.0F, -7.9777F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_up_4_L", "garm_up_3_L", 0.0F, -6.9475F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_down_1_L", "Chest", 3.2346F, 1.6954F, 4.9955F, 0.0F, 0.0F, 2.3562F),
                bone("garm_down_2_L", "garm_down_1_L", 0.0F, -5.507F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_down_3_L", "garm_down_2_L", 0.0F, -7.9777F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_down_4_L", "garm_down_3_L", 0.0F, -6.9475F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_up_1_R", "Chest", -3.2807F, -4.0357F, 4.9953F, 0.0F, 0.0F, -0.7854F),
                bone("garm_up_2_R", "garm_up_1_R", 0.0F, -5.507F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_up_3_R", "garm_up_2_R", 0.0F, -7.9777F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_up_4_R", "garm_up_3_R", 0.0F, -6.9475F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_down_1_R", "Chest", -3.235F, 1.6953F, 4.9953F, 0.0F, 0.0F, -2.3562F),
                bone("garm_down_2_R", "garm_down_1_R", 0.0F, -5.507F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_down_3_R", "garm_down_2_R", 0.0F, -7.9777F, 0.0F, 0.0F, 0.0F, 0.0F),
                bone("garm_down_4_R", "garm_down_3_R", 0.0F, -6.9475F, 0.0F, 0.0F, 0.0F, 0.0F)
        );
    }

}
