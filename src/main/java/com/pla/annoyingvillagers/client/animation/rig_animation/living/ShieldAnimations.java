package com.pla.annoyingvillagers.client.animation.rig_animation.living;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class ShieldAnimations {
	public static final AnimationDefinition BLOCK_SHIELD_MAINHAND = AnimationDefinition.Builder.withLength(0.05F).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.119F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(14.099F, -34.868F, -6.011F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.003F, -0.132F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.008F, -0.501F, -0.399F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.253F, -3.556F, -0.198F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.642F, -9.817F, 1.946F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.132F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.002F, -0.508F, -0.439F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-105.39F, -40.632F, 84.612F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-137.445F, -36.844F, 117.107F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.719F, -0.772F, -0.819F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.209F, -1.602F, -3.051F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-64.657F, -15.024F, 39.029F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.216F, -0.879F, 0.69F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-49.878F, 87.44F, -51.791F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-49.879F, 87.44F, -51.792F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.398F, 0.021F, -0.587F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.634F, -12.929F, -6.073F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(52.489F, -34.065F, -13.204F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.026F, -0.154F, 0.685F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.778F, -0.119F, 2.571F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.005F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.075F, 0.25F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.415F, 27.757F, 5.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-3.234F, 23.334F, 4.177F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.275F, -0.139F, 0.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.039F, -0.454F, 0.395F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.115F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(25.92F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.005F, 0.029F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.025F, 0.078F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.807F, 1.377F, -1.596F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-13.222F, -0.597F, -1.749F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.286F, -0.134F, -1.076F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.049F, -0.166F, -0.498F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.945F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(13.279F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.005F, 0.031F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.008F, 0.042F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition BLOCK_SHIELD_OFFHAND = AnimationDefinition.Builder.withLength(0.05F).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.653F, 31.197F, 2.839F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(18.795F, 52.875F, 10.827F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.149F, -0.143F, -0.468F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.463F, -0.417F, -0.008F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.43F, -0.178F, 0.574F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(2.554F, 4.645F, -1.217F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.142F, -0.144F, -0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.485F, -0.424F, -0.035F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.338F, 2.878F, 15.851F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(60.328F, 22.353F, 16.824F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.904F, -0.056F, 2.891F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(2.038F, 0.17F, 4.964F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-53.686F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.217F, 0.399F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.436F, -9.552F, 0.742F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-50.857F, 3.044F, -17.36F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.424F, -0.298F, -4.832F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-3.717F, -1.413F, -5.24F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.352F, 42.735F, 104.193F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.672F, 0.101F, 0.647F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.852F, 0.209F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.415F, 27.757F, 5.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-1.507F, 25.279F, 6.027F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.275F, -0.139F, 0.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.809F, -0.141F, 1.576F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.115F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(12.399F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.005F, 0.029F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.008F, 0.039F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.102F, 1.364F, -1.554F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-10.548F, 0.02F, -4.02F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.286F, -0.133F, -1.077F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.821F, -0.138F, -1.657F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.847F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(11.95F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.009F, 0.043F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.007F, 0.038F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition SHIELD_MAINHAND = AnimationDefinition.Builder.withLength(2.4F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.354F, -47.977F, -6.597F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(12.092F, -48.067F, -5.659F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(13.354F, -47.977F, -6.597F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.199F, -2.229F, -1.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.199F, -1.719F, -1.019F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(0.199F, -2.229F, -1.368F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.626F, -1.57F, -0.133F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.222F, -2.23F, -1.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.223F, -1.719F, -1.014F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(0.222F, -2.23F, -1.368F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.639F, -18.288F, -1.977F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-45.342F, -17.144F, -1.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8F, KeyframeAnimations.degreeVec(-46.934F, -19.258F, -2.121F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(-45.639F, -18.288F, -1.977F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(2.061F, -2.82F, -5.058F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(2.132F, -2.634F, -4.94F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(2.061F, -2.255F, -4.746F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8F, KeyframeAnimations.posVec(1.974F, -2.427F, -4.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(2.061F, -2.82F, -5.058F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-20.576F, -39.355F, -67.453F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.18F, -0.199F, 0.767F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -2.212F, 0.265F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-81.415F, 1.762F, -89.537F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.403F, -3.422F, 0.878F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.403F, -2.945F, 1.18F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(0.403F, -3.422F, 0.878F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.508F, -1.174F, 11.813F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.103F, -0.357F, 0.233F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(46.492F, -81.954F, -51.49F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.16F, -0.356F, -0.42F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.878F, -9.058F, 13.509F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-38.806F, -10.028F, 13.442F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(-40.878F, -9.058F, 13.509F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.072F, -2.088F, -0.705F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.072F, -1.704F, -0.698F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(0.072F, -2.088F, -0.705F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(39.284F, 0.042F, -2.208F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(35.303F, -0.077F, -2.053F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(39.284F, 0.042F, -2.208F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.005F, 0.055F, 0.112F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.439F, -18.843F, -12.641F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-6.781F, -17.669F, -12.529F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(-9.439F, -18.843F, -12.641F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.041F, -2.164F, 0.538F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.044F, -1.777F, 0.548F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(-0.041F, -2.164F, 0.538F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(50.58F, 5.64F, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(43.787F, 5.44F, -0.316F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(50.58F, 5.64F, -0.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.016F, 0.078F, 0.145F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition SHIELD_OFFHAND = AnimationDefinition.Builder.withLength(2.4F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(23.955F, 63.148F, 18.22F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.347F, -2.243F, -1.348F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.347F, -1.746F, -1.248F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(-0.347F, -2.243F, -1.348F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(8.153F, 2.722F, -0.184F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.371F, -2.245F, -1.349F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.371F, -1.748F, -1.247F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(-0.371F, -2.245F, -1.349F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(43.735F, 36.502F, 40.121F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(3.107F, -2.141F, 3.869F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(3.132F, -2.111F, 3.901F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(3.107F, -1.674F, 3.994F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8F, KeyframeAnimations.posVec(3.083F, -1.715F, 3.952F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(3.107F, -2.141F, 3.869F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-36.903F, -0.009F, -0.031F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-40.728F, -0.009F, -0.031F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(-36.903F, -0.009F, -0.031F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.586F, 0.299F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(39.53F, 14.853F, -5.269F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(39.53F, 14.853F, -5.269F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(40.217F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.488F, 0.701F, 0.039F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.488F, 0.701F, 0.039F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(0.32F, 0.603F, 0.322F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-58.358F, 62.733F, -50.652F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-3.235F, -3.294F, -5.686F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-3.331F, -3.236F, -5.595F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-3.236F, -2.786F, -5.594F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8F, KeyframeAnimations.posVec(-3.189F, -2.881F, -5.688F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(-3.235F, -3.294F, -5.686F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-75.649F, 13.287F, 21.69F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.241F, -0.559F, 0.693F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.963F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.22F, -0.123F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.169F, 42.532F, 32.811F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(9.066F, 42.274F, 32.082F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(4.169F, 42.532F, 32.811F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.409F, -2.172F, 1.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.422F, -1.867F, 1.332F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(0.409F, -2.172F, 1.287F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(45.964F, -0.368F, -0.961F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(33.321F, -0.708F, -0.647F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(45.964F, -0.368F, -0.961F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.001F, 0.068F, 0.127F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.09F, 38.775F, -24.438F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-42.374F, 40.869F, -24.417F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(-47.09F, 38.775F, -24.438F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.509F, -2.102F, -1.398F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.5F, -1.8F, -1.35F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(-0.509F, -2.102F, -1.398F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(46.434F, 2.668F, 1.629F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(36.633F, 2.688F, 1.84F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(46.434F, 2.668F, 1.629F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.004F, 0.071F, 0.132F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}