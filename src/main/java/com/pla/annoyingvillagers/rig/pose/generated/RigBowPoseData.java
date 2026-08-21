package com.pla.annoyingvillagers.rig.pose.generated;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.pose.RigPoseClip;

import java.util.Map;

import static com.pla.annoyingvillagers.rig.RigColliderAnchor.*;
import static com.pla.annoyingvillagers.rig.pose.RigPoseClip.part;

public final class RigBowPoseData {
    private RigBowPoseData() {}

    public static void register(Map<RigAnimationId, RigPoseClip> clips) {
        clips.put(RigAnimationId.BOW_AIM_DOWN, bow_aim_down());
        clips.put(RigAnimationId.BOW_AIM_MID, bow_aim_mid());
        clips.put(RigAnimationId.BOW_AIM_UP, bow_aim_up());
        clips.put(RigAnimationId.BOW_ATTACK_DOWN, bow_attack_down());
        clips.put(RigAnimationId.BOW_ATTACK_MID, bow_attack_mid());
        clips.put(RigAnimationId.BOW_ATTACK_UP, bow_attack_up());
    }

    private static RigPoseClip bow_aim_down() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, -0.207F, -2.804F, -4.59F, 0.1F, -0.094F, -3.882F, -5.472F, 0.2F, 0.077F, -5.084F, -6.11F, 0.4F, -0.022F, -5.048F, -6.32F, 0.7F, 0.047F, -4.971F, -6.445F}, new float[]{0F, 54.69F, 14.632F, 15.656F, 0.2F, 76.882F, 4.892F, 17.871F, 0.4F, 82.92F, 12.777F, 53.94F, 0.7F, 88.374F, 15.348F, 79.674F}),
                part(HEAD, new float[]{0F, -0.222F, -2.9F, -4.741F, 0.1F, -0.105F, -4.02F, -5.654F, 0.2F, 0.071F, -5.268F, -6.315F, 0.4F, -0.027F, -5.223F, -6.511F, 0.7F, 0.05F, -5.135F, -6.627F}, new float[]{0F, 5F, 0F, 0F, 0.2F, 0.119F, -0.007F, -0.024F, 0.7F, 4.933F, 4.793F, -5.155F}),
                part(RIGHT_ARM, new float[]{0F, -0.455F, -3.652F, -2.474F, 0.2F, 0.351F, -4.543F, -4.557F, 0.3F, 0.182F, -3.016F, -4.115F, 0.4F, 0.626F, -1.396F, -3.621F, 0.7F, 2.207F, 0.499F, -3.149F}, new float[]{0F, -80.07F, -74.417F, 53.15F, 0.05F, -31.12F, -63.894F, 0.451F, 0.1F, -16.566F, -45.644F, -15.994F, 0.2F, -7.659F, -5.825F, -22.673F, 0.4F, -10.975F, 5.027F, 1.154F, 0.7F, -11.557F, 9.527F, 19.196F}),
                part(RIGHT_HAND, new float[]{0F, -0.029F, -0.432F, 0.086F, 0.2F, -0.028F, -0.447F, 0.144F, 0.4F, -0.211F, -0.21F, 0.18F, 0.7F, -0.37F, -0.079F, 0.197F}, new float[]{0F, -8.115F, -0.887F, -2.99F, 0.2F, -14.516F, -1.082F, -2.874F, 0.4F, -13.553F, -3.625F, -18.151F, 0.7F, -12.412F, -5.153F, -28.684F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -0.304F, 0.054F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -2.202F, -5.56F, -5.512F, 0.1F, -1.959F, -6.704F, -4.908F, 0.2F, -1.73F, -7.596F, -4.208F, 0.4F, -4.05F, -9.165F, -5.061F, 0.7F, -5.658F, -9.514F, -5.41F}, new float[]{0F, -5.308F, 54.456F, 16.629F, 0.2F, -30.931F, 57.829F, -15.267F, 0.7F, -24.327F, 59.307F, -13.115F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.481F, 0.055F}, new float[]{0F, -6.312F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, -0.32F, -0.304F, 0.054F, 0.7F, -0.112F, -0.459F, 0.071F}, new float[]{0F, 1.039F, 0F, 0F, 0.2F, 1.039F, 0F, 0F, 0.7F, 0.944F, 1.094F, -9.941F}),
                part(RIGHT_LEG, new float[]{0F, 0.158F, -0.423F, 0.816F, 0.2F, 0.093F, -0.423F, 0.665F, 0.7F, 0.159F, -0.423F, 0.818F}, new float[]{0F, 2.011F, 44.247F, 11.06F, 0.2F, 0.8F, 39.461F, 10.252F, 0.7F, 1.896F, 42.895F, 10.894F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.002F, 0.01F, 0.043F}, new float[]{0F, 13.732F, 0F, -0.5F}),
                part(LEFT_LEG, new float[]{0F, -0.191F, -0.419F, -0.89F, 0.2F, -0.122F, -0.419F, -0.741F, 0.7F, -0.191F, -0.419F, -0.892F}, new float[]{0F, -12.008F, 18.635F, -5.651F, 0.2F, -11.513F, 13.788F, -5.514F, 0.7F, -11.988F, 17.278F, -5.589F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.006F, 0.032F}, new float[]{0F, 10.075F, 0F, 0F})
        );
    }

    private static RigPoseClip bow_aim_mid() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, -0.207F, -2.038F, -4.694F, 0.2F, -0.049F, -1.097F, -2.927F, 0.4F, 0.02F, -0.542F, -1.046F, 0.7F, 0.584F, -0.441F, 0.461F}, new float[]{0F, 44.614F, 17.105F, 12.894F, 0.2F, 28.298F, 16.971F, 5.314F, 0.4F, 16.263F, 45.762F, 9.466F, 0.7F, -2.544F, 66.031F, 0.775F}),
                part(HEAD, new float[]{0F, -0.222F, -2.103F, -4.82F, 0.2F, -0.066F, -1.128F, -3.025F, 0.4F, 0.013F, -0.547F, -1.073F, 0.7F, 0.605F, -0.437F, 0.493F}, new float[]{0F, 5F, 0F, 0F, 0.2F, 0.117F, 0.009F, 0.022F, 0.7F, 4.933F, 4.793F, -5.155F}),
                part(RIGHT_ARM, new float[]{0F, -0.457F, -3.212F, -3.104F, 0.1F, 0.073F, -2.471F, -3.005F, 0.2F, 0.327F, -1.593F, -2.93F, 0.3F, 0.122F, -0.929F, -1.128F, 0.4F, 0.365F, -0.546F, 0.823F, 0.55F, 0.846F, -0.423F, 2.296F, 0.7F, 1.508F, -0.499F, 3.586F}, new float[]{0F, -94.329F, -65.7F, 67.019F, 0.05F, -74.558F, -57.149F, 39.452F, 0.1F, -66.296F, -46.329F, 21.881F, 0.2F, -64.494F, -24.96F, -4.116F, 0.4F, -76.859F, 2.805F, 0.025F, 0.7F, -88.742F, 21.858F, 0.108F}),
                part(RIGHT_HAND, new float[]{0F, -0.029F, -0.432F, 0.086F, 0.2F, -0.028F, -0.447F, 0.144F, 0.4F, -0.343F, -0.097F, 0.193F, 0.7F, -0.63F, 0.053F, 0.208F}, new float[]{0F, -8.115F, -0.887F, -2.99F, 0.2F, -14.516F, -1.082F, -2.874F, 0.4F, -12.887F, -4.673F, -27.04F, 0.7F, -10.872F, -6.554F, -43.697F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -0.304F, 0.054F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -2.202F, -4.562F, -6.428F, 0.2F, -1.653F, -3.326F, -5.105F, 0.4F, -3.567F, -1.858F, -5.798F, 0.7F, -4.335F, -0.345F, -5.445F}, new float[]{0F, -22.673F, 56.119F, 2.318F, 0.1F, -64.363F, 47.385F, -37.272F, 0.2F, -85.841F, 26.841F, -57.898F, 0.4F, -88.957F, 13.238F, -28.501F, 0.7F, -91.139F, 8.85F, -4.954F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.481F, 0.055F}, new float[]{0F, -6.312F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, -0.32F, -0.304F, 0.054F, 0.7F, -0.112F, -0.459F, 0.071F}, new float[]{0F, 1.039F, 0F, 0F, 0.2F, 1.039F, 0F, 0F, 0.7F, 0.944F, 1.094F, -9.941F}),
                part(RIGHT_LEG, new float[]{0F, 0.158F, -0.423F, 0.816F, 0.2F, 0.093F, -0.423F, 0.665F, 0.7F, 0.159F, -0.423F, 0.817F}, new float[]{0F, 2.007F, 44.201F, 11.054F, 0.2F, 0.796F, 39.384F, 10.245F, 0.7F, 1.906F, 43.015F, 10.909F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.002F, 0.01F, 0.043F}, new float[]{0F, 13.732F, 0F, -0.5F}),
                part(LEFT_LEG, new float[]{0F, -0.191F, -0.419F, -0.89F, 0.2F, -0.122F, -0.419F, -0.741F, 0.7F, -0.191F, -0.419F, -0.892F}, new float[]{0F, -12.007F, 18.588F, -5.649F, 0.2F, -11.513F, 13.711F, -5.511F, 0.7F, -11.99F, 17.4F, -5.594F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.006F, 0.032F}, new float[]{0F, 10.075F, 0F, 0F})
        );
    }

    private static RigPoseClip bow_aim_up() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, -0.207F, -0.477F, 1.009F, 0.2F, 0.082F, -0.915F, 2.715F, 0.4F, 0.022F, -2.618F, 5.478F, 0.7F, 0.584F, -4.242F, 6.806F}, new float[]{0F, -2.985F, 21.026F, -3.511F, 0.2F, -23.988F, 16.849F, -7.885F, 0.3F, -39.942F, 24.543F, -21.959F, 0.4F, -58.463F, 27.767F, -39.665F, 0.7F, -82.501F, 23.026F, -63.808F}),
                part(HEAD, new float[]{0F, -0.222F, -0.475F, 1.04F, 0.2F, 0.076F, -0.927F, 2.804F, 0.4F, 0.017F, -2.686F, 5.667F, 0.7F, 0.603F, -4.366F, 7.042F}, new float[]{0F, 5F, 0F, 0F, 0.2F, 0.122F, 0.013F, 0.019F, 0.4F, 3.014F, -1.855F, -3.3F, 0.7F, 4.915F, -0.189F, -5.585F}),
                part(RIGHT_ARM, new float[]{0F, -0.455F, -1.845F, -0.111F, 0.1F, 0.102F, -1.046F, 0.358F, 0.2F, 0.354F, -0.205F, 1.039F, 0.3F, 0.098F, -1.442F, 3.144F, 0.4F, 0.381F, -3.23F, 4.902F, 0.6F, 1.116F, -5.128F, 6.068F, 0.7F, 1.608F, -6.047F, 6.409F}, new float[]{0F, -112.093F, -22.111F, 79.986F, 0.2F, -119.653F, -20.299F, 11.716F, 0.4F, -144.922F, -5.304F, -1.129F, 0.7F, -164.091F, 2.94F, -11.495F}),
                part(RIGHT_HAND, new float[]{0F, -0.029F, -0.432F, 0.086F, 0.2F, -0.028F, -0.447F, 0.144F, 0.4F, -0.211F, -0.21F, 0.18F, 0.7F, -0.37F, -0.079F, 0.197F}, new float[]{0F, -8.115F, -0.887F, -2.99F, 0.2F, -14.516F, -1.082F, -2.874F, 0.4F, -13.553F, -3.625F, -18.151F, 0.7F, -12.412F, -5.153F, -28.684F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -0.304F, 0.054F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -2.266F, -0.391F, -3.424F, 0.2F, -1.732F, -0.046F, -2.097F, 0.3F, -2.397F, 1.23F, -1.256F, 0.4F, -3.201F, 2.334F, 0.14F, 0.7F, -3.848F, 3.012F, 2.696F}, new float[]{0F, -81.289F, 36.486F, -39.515F, 0.1F, -102.204F, 10.142F, -53.021F, 0.2F, -115.535F, -19.692F, -56.75F, 0.3F, -124.561F, -34.101F, -55.983F, 0.4F, -139.216F, -46.629F, -46.787F, 0.7F, -170.813F, -55.339F, -17.178F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.481F, 0.055F}, new float[]{0F, -6.312F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, -0.32F, -0.304F, 0.054F, 0.7F, -0.112F, -0.459F, 0.071F}, new float[]{0F, 1.039F, 0F, 0F, 0.2F, 1.039F, 0F, 0F, 0.7F, 0.944F, 1.094F, -9.941F}),
                part(RIGHT_LEG, new float[]{0F, 0.158F, -0.423F, 0.816F, 0.2F, 0.093F, -0.423F, 0.664F, 0.7F, 0.159F, -0.423F, 0.817F}, new float[]{0F, 2.007F, 44.206F, 11.055F, 0.2F, 0.801F, 39.481F, 10.254F, 0.7F, 1.904F, 42.986F, 10.905F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.002F, 0.01F, 0.043F}, new float[]{0F, 13.732F, 0F, -0.5F}),
                part(LEFT_LEG, new float[]{0F, -0.191F, -0.419F, -0.89F, 0.2F, -0.122F, -0.419F, -0.741F, 0.7F, -0.191F, -0.419F, -0.892F}, new float[]{0F, -12.007F, 18.594F, -5.649F, 0.2F, -11.514F, 13.808F, -5.514F, 0.7F, -11.989F, 17.37F, -5.593F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.006F, 0.032F}, new float[]{0F, 10.075F, 0F, 0F})
        );
    }

    private static RigPoseClip bow_attack_down() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0.047F, -4.971F, -6.445F, 0.1F, 0.047F, -4.974F, -6.446F}, new float[]{0F, 88.374F, 15.348F, 79.674F, 0.1F, 90.77F, 15.413F, 88.69F}),
                part(HEAD, new float[]{0F, 0.05F, -5.135F, -6.627F}, new float[]{0F, 4.933F, 4.793F, -5.155F}),
                part(RIGHT_ARM, new float[]{0F, 2.207F, 0.499F, -3.149F, 0.1F, 3.471F, 1.197F, -2.734F}, new float[]{0F, -11.557F, 9.527F, 19.196F, 0.05F, 5.352F, 16.643F, 79.653F, 0.1F, 12.32F, 14.334F, 100.854F}),
                part(RIGHT_HAND, new float[]{0F, -0.37F, -0.079F, 0.197F, 0.05F, -1.21F, 0.099F, 0.198F, 0.1F, -1.484F, 0.011F, 0.179F}, new float[]{0F, -12.412F, -5.153F, -28.684F, 0.05F, -6.945F, -7.954F, -74.078F, 0.1F, -4.874F, -7.849F, -89.057F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -0.304F, 0.054F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -5.658F, -9.514F, -5.41F, 0.1F, -4.804F, -9.542F, -5.356F}, new float[]{0F, -24.327F, 59.307F, -13.115F, 0.1F, -15.263F, 61.441F, -20.504F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.481F, 0.055F, 0.1F, 0F, -0.501F, 0.141F}, new float[]{0F, -6.312F, 0F, 0F, 0.1F, -16.312F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, -0.112F, -0.459F, 0.071F}, new float[]{0F, 0.944F, 1.094F, -9.941F}),
                part(RIGHT_LEG, new float[]{0F, 0.159F, -0.423F, 0.818F}, new float[]{0F, 1.896F, 42.895F, 10.894F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.002F, 0.01F, 0.043F}, new float[]{0F, 13.732F, 0F, -0.5F}),
                part(LEFT_LEG, new float[]{0F, -0.191F, -0.419F, -0.892F}, new float[]{0F, -11.988F, 17.278F, -5.589F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.006F, 0.032F}, new float[]{0F, 10.075F, 0F, 0F})
        );
    }

    private static RigPoseClip bow_attack_mid() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0.584F, -0.441F, 0.461F, 0.1F, 0.584F, -0.441F, 0.459F}, new float[]{0F, -2.544F, 66.031F, 0.775F, 0.1F, -4.278F, 76.014F, -1.053F}),
                part(HEAD, new float[]{0F, 0.605F, -0.437F, 0.493F}, new float[]{0F, 4.933F, 4.793F, -5.155F}),
                part(RIGHT_ARM, new float[]{0F, 1.508F, -0.499F, 3.586F, 0.05F, 2.407F, -0.582F, 4.599F, 0.1F, 2.997F, -0.594F, 4.984F}, new float[]{0F, -88.742F, 21.858F, 0.108F, 0.05F, -88.308F, 65.667F, -1.882F, 0.1F, -87.743F, 72.923F, -2.407F}),
                part(RIGHT_HAND, new float[]{0F, -0.63F, 0.053F, 0.208F, 0.1F, -1.109F, 0.112F, 0.206F}, new float[]{0F, -10.872F, -6.554F, -43.697F, 0.05F, -8.915F, -7.497F, -65.065F, 0.1F, -8.242F, -7.749F, -68.852F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -0.304F, 0.054F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -4.335F, -0.345F, -5.445F, 0.1F, -3.615F, -0.711F, -5.528F}, new float[]{0F, -91.139F, 8.85F, -4.954F, 0.1F, -87.838F, -5.015F, -23.518F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.481F, 0.055F, 0.1F, 0.102F, -0.315F, 0.055F}, new float[]{0F, -6.312F, 0F, 0F, 0.1F, -6.312F, 0F, 10F}),
                part(LEFT_TOOL, new float[]{0F, -0.112F, -0.459F, 0.071F}, new float[]{0F, 0.944F, 1.094F, -9.941F}),
                part(RIGHT_LEG, new float[]{0F, 0.159F, -0.423F, 0.817F}, new float[]{0F, 1.906F, 43.015F, 10.909F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.002F, 0.01F, 0.043F}, new float[]{0F, 13.732F, 0F, -0.5F}),
                part(LEFT_LEG, new float[]{0F, -0.191F, -0.419F, -0.892F}, new float[]{0F, -11.99F, 17.4F, -5.594F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.006F, 0.032F}, new float[]{0F, 10.075F, 0F, 0F})
        );
    }

    private static RigPoseClip bow_attack_up() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0.584F, -4.242F, 6.806F, 0.1F, 0.584F, -4.24F, 6.805F}, new float[]{0F, -82.501F, 23.026F, -63.808F, 0.1F, -86.841F, 23.958F, -74.668F}),
                part(HEAD, new float[]{0F, 0.603F, -4.366F, 7.042F}, new float[]{0F, 4.915F, -0.189F, -5.585F, 0.1F, 4.916F, -0.189F, -5.585F}),
                part(RIGHT_ARM, new float[]{0F, 1.608F, -6.047F, 6.409F, 0.05F, 2.583F, -6.881F, 6.633F, 0.1F, 3.028F, -7.207F, 6.709F}, new float[]{0F, -164.091F, 2.94F, -11.495F, 0.05F, -170.638F, 13.094F, -56.125F, 0.1F, -174.533F, 14.964F, -72.617F}),
                part(RIGHT_HAND, new float[]{0F, -0.37F, -0.079F, 0.197F, 0.1F, -1.105F, 0.125F, 0.134F}, new float[]{0F, -12.412F, -5.153F, -28.684F, 0.05F, -9.984F, -3.739F, -58.978F, 0.1F, -9.386F, -3.017F, -68.978F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -0.304F, 0.054F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -3.426F, 2.331F, 1.702F, 0.05F, -2.459F, 1.923F, 1.845F, 0.1F, -2.145F, 1.8F, 1.965F}, new float[]{0F, -124.746F, -78.407F, -63.738F, 0.05F, -148.49F, -65.969F, -28.966F, 0.1F, -152.238F, -61.868F, -21.444F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.481F, 0.055F, 0.1F, 0F, -0.501F, 0.141F}, new float[]{0F, -6.312F, 0F, 0F, 0.1F, -16.312F, 0F, 0F}),
                part(LEFT_TOOL, new float[]{0F, -0.112F, -0.459F, 0.071F}, new float[]{0F, 0.944F, 1.094F, -9.941F}),
                part(RIGHT_LEG, new float[]{0F, 0.159F, -0.423F, 0.817F}, new float[]{0F, 1.904F, 42.986F, 10.905F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.002F, 0.01F, 0.043F}, new float[]{0F, 13.732F, 0F, -0.5F}),
                part(LEFT_LEG, new float[]{0F, -0.191F, -0.419F, -0.892F}, new float[]{0F, -11.989F, 17.37F, -5.593F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.006F, 0.032F}, new float[]{0F, 10.075F, 0F, 0F})
        );
    }

}
