package com.pla.annoyingvillagers.rig.pose.generated;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.pose.RigPoseClip;

import java.util.Map;

import static com.pla.annoyingvillagers.rig.RigColliderAnchor.*;
import static com.pla.annoyingvillagers.rig.pose.RigPoseClip.part;

public final class RigHookGunPoseData {
    private RigHookGunPoseData() {}

    public static void register(Map<RigAnimationId, RigPoseClip> clips) {
        clips.put(RigAnimationId.HOOK_GUN, hook_gun());
        clips.put(RigAnimationId.LEFT_HAND_HOOK, left_hand_hook());
        clips.put(RigAnimationId.LEFT_HAND_HOOK_TOP, left_hand_hook_top());
        clips.put(RigAnimationId.RIGHT_HAND_HOOK, right_hand_hook());
        clips.put(RigAnimationId.RIGHT_HAND_HOOK_TOP, right_hand_hook_top());
    }

    private static RigPoseClip hook_gun() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, -0.7F, -1.319F, -0.288F}, new float[]{0F, 5.082F, 6.321F, 0F}),
                part(HEAD, new float[]{0F, -0.699F, -1.318F, -0.282F}, new float[]{0F, 6.529F, 6.321F, 0F}),
                part(RIGHT_ARM, new float[]{0F, -1.464F, -2.028F, -0.678F, 0.3F, -0.714F, -1.448F, -0.313F, 0.7F, -0.649F, -0.99F, -0.184F, 0.9F, -0.795F, -1.595F, -0.44F, 1.15F, -1.464F, -2.028F, -0.678F}, new float[]{0F, -64.18F, 5.554F, 68.427F, 0.2F, -76.443F, -7.361F, 33.986F, 0.35F, -79.378F, -19.1F, 10.79F, 0.7F, -71.936F, -21.491F, -10.06F, 0.9F, -73.265F, -8.17F, 25.036F, 1.15F, -64.18F, 5.554F, 68.427F}),
                part(RIGHT_HAND, new float[]{0F, 0F, -0.19F, 0.379F, 0.7F, 0F, -0.079F, 0.254F, 1.15F, 0F, -0.19F, 0.379F}, new float[]{0F, -49.744F, 0F, 0F, 0.35F, -43.777F, 2.02F, -1.685F, 0.7F, -43.927F, 5.147F, -4.092F, 1.15F, -49.744F, 0F, 0F}),
                part(RIGHT_TOOL, new float[]{0F, 0.023F, -0.04F, 0.74F, 0.35F, 0.07F, -0.04F, 0.739F, 0.7F, 1.05F, -0.048F, 0.729F, 1.15F, 0.023F, -0.04F, 0.74F}, new float[]{0F, 33.072F, -1.889F, -1.493F, 0.7F, 33.051F, -0.636F, -0.502F, 1.15F, 33.072F, -1.889F, -1.493F}),
                part(LEFT_ARM, new float[]{0F, -0.009F, -2.014F, -0.917F}, new float[]{0F, -77.223F, 10.248F, -69.474F, 0.35F, -78.441F, 11.394F, -74.104F, 1.15F, -77.223F, 10.248F, -69.474F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.123F, 0.315F}, new float[]{0F, -39.308F, 0F, 0F, 0.35F, -51.725F, 2.352F, 2.792F, 0.7F, -51.725F, 2.352F, 2.792F, 1.15F, -39.308F, 0F, 0F}),
                part(RIGHT_LEG, new float[]{0F, -0.783F, -1.309F, 0.225F}, new float[]{0F, 4.215F, 46.924F, 36.617F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0F, 0.051F, 0.112F}, new float[]{0F, 39.188F, -0.08F, 0F}),
                part(LEFT_LEG, new float[]{0F, -0.633F, -1.291F, -0.248F}, new float[]{0F, -36.439F, 4.489F, -16.344F}),
                part(LEFT_LOWER_LEG, new float[]{0F, -0.01F, 0.038F, 0.087F}, new float[]{0F, 31.151F, -2.948F, 1.78F})
        );
    }

    private static RigPoseClip left_hand_hook() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(HEAD, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(RIGHT_ARM, new float[]{0F, -0.029F, -0.586F, -0.108F}, new float[]{0F, -10.637F, 3.019F, 4.343F}),
                part(RIGHT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.37F, 0F, 0F}),
                part(RIGHT_TOOL, new float[]{0F, -0.034F, -0.018F, 0.65F}, new float[]{0F, 12.269F, 3.742F, 0.849F}),
                part(LEFT_ARM, new float[]{0F, 0F, -0.241F, -0.311F}, new float[]{0F, -88.405F, 0F, 0F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.369F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, 0F, 0.03F, 0.571F}, new float[]{0F, 5.488F, 0F, 0F}),
                part(RIGHT_LEG, new float[]{0F, -0.024F, -0.522F, -0.054F}, new float[]{0F, -24.684F, -12.02F, 11.023F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0F, 0.022F, 0.072F}, new float[]{0F, 23.785F, 0F, 0F}),
                part(LEFT_LEG, new float[]{0F, 0.017F, -0.522F, -0.036F}, new float[]{0F, -15.565F, 7.206F, -6.519F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.034F, 0.092F}, new float[]{0F, 31.118F, 0F, 0F})
        );
    }

    private static RigPoseClip left_hand_hook_top() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(HEAD, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(RIGHT_ARM, new float[]{0F, -0.029F, -0.586F, -0.108F}, new float[]{0F, -10.637F, 3.019F, 4.343F}),
                part(RIGHT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.37F, 0F, 0F}),
                part(RIGHT_TOOL, new float[]{0F, -0.034F, -0.018F, 0.65F}, new float[]{0F, 12.269F, 3.742F, 0.849F}),
                part(LEFT_ARM, new float[]{0F, 2.031F, -0.092F, -0.109F}, new float[]{0F, 1.286F, -6.643F, -170.374F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.369F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, 0.84F, 0.754F, 8.412F}, new float[]{0F, 3.305F, 7.602F, 1.11F}),
                part(RIGHT_LEG, new float[]{0F, -0.024F, -0.522F, -0.054F}, new float[]{0F, -24.684F, -12.02F, 11.023F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0F, 0.022F, 0.072F}, new float[]{0F, 23.785F, 0F, 0F}),
                part(LEFT_LEG, new float[]{0F, 0.017F, -0.522F, -0.036F}, new float[]{0F, -15.565F, 7.206F, -6.519F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.034F, 0.092F}, new float[]{0F, 31.118F, 0F, 0F})
        );
    }

    private static RigPoseClip right_hand_hook() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(HEAD, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(RIGHT_ARM, new float[]{0F, -0.103F, -0.487F, -0.475F}, new float[]{0F, -85.163F, 9.739F, 14.266F}),
                part(RIGHT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.369F, 0F, 0F}),
                part(RIGHT_TOOL, new float[]{0F, -0.182F, -0.018F, 0.342F}, new float[]{0F, 6.769F, 15.495F, 2.756F}),
                part(LEFT_ARM, new float[]{0F, 0F, -0.514F, -0.032F}, new float[]{0F, -6.223F, 0F, 0F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.37F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, 0F, 0F, 0.701F}, new float[]{0F, 11.635F, 0F, 0F}),
                part(RIGHT_LEG, new float[]{0F, -0.024F, -0.522F, -0.054F}, new float[]{0F, -24.684F, -12.02F, 11.023F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0F, 0.022F, 0.072F}, new float[]{0F, 23.785F, 0F, 0F}),
                part(LEFT_LEG, new float[]{0F, 0.017F, -0.522F, -0.036F}, new float[]{0F, -15.565F, 7.206F, -6.519F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.034F, 0.092F}, new float[]{0F, 31.118F, 0F, 0F})
        );
    }

    private static RigPoseClip right_hand_hook_top() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(HEAD, new float[]{0F, 0F, -0.514F, 0F}, null),
                part(RIGHT_ARM, new float[]{0F, -2.033F, -0.102F, -0.106F}, new float[]{0F, 3.193F, 7.008F, 169.835F}),
                part(RIGHT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.369F, 0F, 0F}),
                part(RIGHT_TOOL, new float[]{0F, -1.949F, 0.028F, 1.39F}, new float[]{0F, 175.722F, 4.611F, 175.478F}),
                part(LEFT_ARM, new float[]{0F, 0F, -0.514F, -0.032F}, new float[]{0F, -6.223F, 0F, 0F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.005F, 0.056F}, new float[]{0F, -6.37F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, 0F, 0F, 0.701F}, new float[]{0F, 11.635F, 0F, 0F}),
                part(RIGHT_LEG, new float[]{0F, -0.024F, -0.522F, -0.054F}, new float[]{0F, -24.684F, -12.02F, 11.023F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0F, 0.022F, 0.072F}, new float[]{0F, 23.785F, 0F, 0F}),
                part(LEFT_LEG, new float[]{0F, 0.017F, -0.522F, -0.036F}, new float[]{0F, -15.565F, 7.206F, -6.519F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.034F, 0.092F}, new float[]{0F, 31.118F, 0F, 0F})
        );
    }

}
