package com.pla.annoyingvillagers.rig.pose.generated;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.pose.RigPoseClip;

import java.util.Map;

import static com.pla.annoyingvillagers.rig.RigColliderAnchor.*;
import static com.pla.annoyingvillagers.rig.pose.RigPoseClip.part;

public final class RigShieldPoseData {
    private RigShieldPoseData() {}

    public static void register(Map<RigAnimationId, RigPoseClip> clips) {
        clips.put(RigAnimationId.BLOCK_SHIELD_MAINHAND, block_shield_mainhand());
        clips.put(RigAnimationId.SHIELD_MAINHAND, shield_mainhand());
        clips.put(RigAnimationId.SHIELD_OFFHAND, shield_offhand());
    }

    private static RigPoseClip block_shield_mainhand() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, -0.003F, -0.132F, 0F, 0.05F, -0.008F, -0.501F, -0.399F}, new float[]{0F, 0F, -12.119F, 0F, 0.05F, 14.099F, -34.868F, -6.011F}),
                part(HEAD, new float[]{0F, 0F, -0.132F, 0F, 0.05F, -0.002F, -0.508F, -0.439F}, new float[]{0F, 0.253F, -3.556F, -0.198F, 0.05F, 0.642F, -9.817F, 1.946F}),
                part(RIGHT_ARM, new float[]{0F, -0.719F, -0.772F, -0.819F, 0.05F, -0.209F, -1.602F, -3.051F}, new float[]{0F, -105.39F, -40.632F, 84.612F, 0.05F, -137.445F, -36.844F, 117.107F}),
                part(RIGHT_HAND, new float[]{0F, -0.216F, -0.879F, 0.69F}, new float[]{0F, -64.657F, -15.024F, 39.029F}),
                part(RIGHT_TOOL, new float[]{0F, -1.398F, 0.021F, -0.587F}, new float[]{0F, -49.878F, 87.44F, -51.791F, 0.05F, -49.879F, 87.44F, -51.792F}),
                part(LEFT_ARM, new float[]{0F, -0.026F, -0.154F, 0.685F, 0.05F, -0.778F, -0.119F, 2.571F}, new float[]{0F, 35.634F, -12.929F, -6.073F, 0.05F, 52.489F, -34.065F, -13.204F}),
                part(LEFT_HAND, new float[]{0F, 0F, -0.075F, 0.25F}, new float[]{0F, -30.005F, 0F, 0F}),
                part(RIGHT_LEG, new float[]{0F, 0.275F, -0.139F, 0.999F, 0.05F, 0.039F, -0.454F, 0.395F}, new float[]{0F, 3.415F, 27.757F, 5.14F, 0.05F, -3.234F, 23.334F, 4.177F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0F, 0.005F, 0.029F, 0.05F, 0F, 0.025F, 0.078F}, new float[]{0F, 9.115F, 0F, 0F, 0.05F, 25.92F, 0F, 0F}),
                part(LEFT_LEG, new float[]{0F, -0.286F, -0.134F, -1.076F, 0.05F, -0.049F, -0.166F, -0.498F}, new float[]{0F, -12.807F, 1.377F, -1.596F, 0.05F, -13.222F, -0.597F, -1.749F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0F, 0.005F, 0.031F, 0.05F, 0F, 0.008F, 0.042F}, new float[]{0F, 9.945F, 0F, 0F, 0.05F, 13.279F, 0F, 0F})
        );
    }

    private static RigPoseClip shield_mainhand() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, 0.199F, -2.229F, -1.368F, 1.2F, 0.199F, -1.719F, -1.019F, 2.4F, 0.199F, -2.229F, -1.368F}, new float[]{0F, 13.354F, -47.977F, -6.597F, 1.2F, 12.092F, -48.067F, -5.659F, 2.4F, 13.354F, -47.977F, -6.597F}),
                part(HEAD, new float[]{0F, 0.222F, -2.23F, -1.368F, 1.2F, 0.223F, -1.719F, -1.014F, 2.4F, 0.222F, -2.23F, -1.368F}, new float[]{0F, 7.626F, -1.57F, -0.133F}),
                part(RIGHT_ARM, new float[]{0F, 2.061F, -2.82F, -5.058F, 0.6F, 2.132F, -2.634F, -4.94F, 1.2F, 2.061F, -2.255F, -4.746F, 1.8F, 1.974F, -2.427F, -4.86F, 2.4F, 2.061F, -2.82F, -5.058F}, new float[]{0F, -45.639F, -18.288F, -1.977F, 0.6F, -45.342F, -17.144F, -1.22F, 1.8F, -46.934F, -19.258F, -2.121F, 2.4F, -45.639F, -18.288F, -1.977F}),
                part(RIGHT_HAND, new float[]{0F, -1.18F, -0.199F, 0.767F}, new float[]{0F, -20.576F, -39.355F, -67.453F}),
                part(RIGHT_TOOL, new float[]{0F, 0.32F, -2.212F, 0.265F}, new float[]{0F, 1.039F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, 0.403F, -3.422F, 0.878F, 1.2F, 0.403F, -2.945F, 1.18F, 2.4F, 0.403F, -3.422F, 0.878F}, new float[]{0F, -81.415F, 1.762F, -89.537F}),
                part(LEFT_HAND, new float[]{0F, 0.103F, -0.357F, 0.233F}, new float[]{0F, -30.508F, -1.174F, 11.813F}),
                part(LEFT_TOOL, new float[]{0F, 1.16F, -0.356F, -0.42F}, new float[]{0F, 46.492F, -81.954F, -51.49F}),
                part(RIGHT_LEG, new float[]{0F, 0.072F, -2.088F, -0.705F, 1.2F, 0.072F, -1.704F, -0.698F, 2.4F, 0.072F, -2.088F, -0.705F}, new float[]{0F, -40.878F, -9.058F, 13.509F, 1.2F, -38.806F, -10.028F, 13.442F, 2.4F, -40.878F, -9.058F, 13.509F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.005F, 0.055F, 0.112F}, new float[]{0F, 39.284F, 0.042F, -2.208F, 1.2F, 35.303F, -0.077F, -2.053F, 2.4F, 39.284F, 0.042F, -2.208F}),
                part(LEFT_LEG, new float[]{0F, -0.041F, -2.164F, 0.538F, 1.2F, -0.044F, -1.777F, 0.548F, 2.4F, -0.041F, -2.164F, 0.538F}, new float[]{0F, -9.439F, -18.843F, -12.641F, 1.2F, -6.781F, -17.669F, -12.529F, 2.4F, -9.439F, -18.843F, -12.641F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0.016F, 0.078F, 0.145F}, new float[]{0F, 50.58F, 5.64F, -0.5F, 1.2F, 43.787F, 5.44F, -0.316F, 2.4F, 50.58F, 5.64F, -0.5F})
        );
    }

    private static RigPoseClip shield_offhand() {
        return RigPoseClip.of(
                part(BODY, new float[]{0F, -0.347F, -2.243F, -1.348F, 1.2F, -0.347F, -1.746F, -1.248F, 2.4F, -0.347F, -2.243F, -1.348F}, new float[]{0F, 23.955F, 63.148F, 18.22F}),
                part(HEAD, new float[]{0F, -0.371F, -2.245F, -1.349F, 1.2F, -0.371F, -1.748F, -1.247F, 2.4F, -0.371F, -2.245F, -1.349F}, new float[]{0F, 8.153F, 2.722F, -0.184F}),
                part(RIGHT_ARM, new float[]{0F, 3.107F, -2.141F, 3.869F, 0.6F, 3.132F, -2.111F, 3.901F, 1.2F, 3.107F, -1.674F, 3.994F, 1.8F, 3.083F, -1.715F, 3.952F, 2.4F, 3.107F, -2.141F, 3.869F}, new float[]{0F, 43.735F, 36.502F, 40.121F}),
                part(RIGHT_HAND, new float[]{0F, 0F, -0.586F, 0.299F}, new float[]{0F, -36.903F, -0.009F, -0.031F, 1.2F, -40.728F, -0.009F, -0.031F, 2.4F, -36.903F, -0.009F, -0.031F}),
                part(RIGHT_TOOL, new float[]{0F, 0.488F, 0.701F, 0.039F, 1.2F, 0.488F, 0.701F, 0.039F, 2.4F, 0.32F, 0.603F, 0.322F}, new float[]{0F, 39.53F, 14.853F, -5.269F, 1.2F, 39.53F, 14.853F, -5.269F, 2.4F, 40.217F, 0F, 0F}),
                part(LEFT_ARM, new float[]{0F, -3.235F, -3.294F, -5.686F, 0.6F, -3.331F, -3.236F, -5.595F, 1.2F, -3.236F, -2.786F, -5.594F, 1.8F, -3.189F, -2.881F, -5.688F, 2.4F, -3.235F, -3.294F, -5.686F}, new float[]{0F, -58.358F, 62.733F, -50.652F}),
                part(LEFT_HAND, new float[]{0F, 0.241F, -0.559F, 0.693F}, new float[]{0F, -75.649F, 13.287F, 21.69F}),
                part(LEFT_TOOL, new float[]{0F, -0.32F, -0.22F, -0.123F}, new float[]{0F, -7.963F, 0F, 0F}),
                part(RIGHT_LEG, new float[]{0F, 0.409F, -2.172F, 1.287F, 1.2F, 0.422F, -1.867F, 1.332F, 2.4F, 0.409F, -2.172F, 1.287F}, new float[]{0F, 4.169F, 42.532F, 32.811F, 1.2F, 9.066F, 42.274F, 32.082F, 2.4F, 4.169F, 42.532F, 32.811F}),
                part(RIGHT_LOWER_LEG, new float[]{0F, 0.001F, 0.068F, 0.127F}, new float[]{0F, 45.964F, -0.368F, -0.961F, 1.2F, 33.321F, -0.708F, -0.647F, 2.4F, 45.964F, -0.368F, -0.961F}),
                part(LEFT_LEG, new float[]{0F, -0.509F, -2.102F, -1.398F, 1.2F, -0.5F, -1.8F, -1.35F, 2.4F, -0.509F, -2.102F, -1.398F}, new float[]{0F, -47.09F, 38.775F, -24.438F, 1.2F, -42.374F, 40.869F, -24.417F, 2.4F, -47.09F, 38.775F, -24.438F}),
                part(LEFT_LOWER_LEG, new float[]{0F, 0.004F, 0.071F, 0.132F}, new float[]{0F, 46.434F, 2.668F, 1.629F, 1.2F, 36.633F, 2.688F, 1.84F, 2.4F, 46.434F, 2.668F, 1.629F})
        );
    }

}
