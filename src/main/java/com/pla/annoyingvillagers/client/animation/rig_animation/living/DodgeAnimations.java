package com.pla.annoyingvillagers.client.animation.rig_animation.living;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class DodgeAnimations {
	public static final AnimationDefinition KNOCKDOWN_WAKEUP_LEFT = AnimationDefinition.Builder.withLength(0.6F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-88.607F, 0.0F, -320.941F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-103.943F, -15.381F, -264.478F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-77.474F, -13.858F, -194.864F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-80.112F, 34.194F, -177.865F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-62.527F, 60.423F, -150.265F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(15.029F, 56.141F, -55.976F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(45.782F, 39.076F, -9.943F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(63.492F, 19.489F, 22.563F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(63.307F, 16.551F, 23.876F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(2.034F, -21.428F, 11.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(5.556F, -19.046F, 11.58F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(9.894F, -16.038F, 10.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(20.636F, -14.265F, 11.127F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(29.925F, -14.812F, 10.854F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(28.218F, -15.45F, 11.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(26.825F, -15.375F, 9.379F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(26.01F, -14.37F, 5.449F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(28.029F, -11.105F, -0.415F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(33.645F, -8.788F, -5.059F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(40.479F, -9.899F, -8.655F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(41.161F, -10.095F, -8.817F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-74.979F, -12.267F, -322.524F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-103.943F, -15.381F, -264.478F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-77.474F, -13.858F, -194.864F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-86.173F, 31.86F, -172.54F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-68.43F, 54.266F, -138.994F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-16.544F, 42.64F, -63.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-1.632F, 23.486F, -26.663F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.921F, 3.312F, -0.969F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(5.639F, -0.1F, 0.105F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(2.039F, -21.497F, 11.805F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(5.541F, -19.077F, 11.586F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(9.894F, -16.038F, 10.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(20.636F, -14.265F, 11.127F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(29.925F, -14.812F, 10.854F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(28.218F, -15.467F, 11.078F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(26.821F, -15.41F, 9.371F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(25.995F, -14.424F, 5.425F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(28.001F, -11.164F, -0.492F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(33.618F, -8.862F, -5.183F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(40.455F, -10.014F, -8.812F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(41.142F, -10.212F, -8.978F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(76.411F, 89.518F, -219.59F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(98.276F, 100.666F, -197.329F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(111.896F, 111.195F, -184.787F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(92.471F, 128.546F, -134.728F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(86.525F, 144.243F, -70.688F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(52.185F, 135.126F, -55.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(25.636F, 111.361F, -39.795F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(21.141F, 78.683F, -14.717F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(11.831F, 46.104F, -0.046F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(35.448F, 14.105F, 63.838F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(33.269F, 11.992F, 64.691F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.044F, -17.803F, 9.089F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(6.534F, -13.213F, 7.73F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(13.783F, -9.24F, 6.342F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(27.997F, -7.579F, 7.068F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(39.53F, -10.631F, 7.212F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(38.997F, -12.182F, 9.705F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(37.666F, -13.039F, 10.434F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(35.524F, -13.604F, 9.035F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(33.534F, -12.6F, 4.259F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(35.727F, -9.264F, -0.646F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(40.668F, -7.65F, -5.285F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(41.199F, -7.673F, -5.692F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-31.679F, 0.215F, 0.328F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-18.179F, 0.23F, 0.556F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-18.179F, 0.23F, 0.556F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-36.85F, 0.163F, 0.157F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-41.284F, 0.105F, 0.077F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-35.946F, 0.073F, 0.212F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.702F, 0.055F, -0.024F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-14.611F, 0.051F, -0.039F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.003F, -0.089F, 0.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.005F, -0.04F, 0.152F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.001F, -0.136F, 0.326F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.02F, 0.126F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.019F, 0.126F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(23.916F, -0.408F, 0.196F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(23.916F, -0.408F, 0.196F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(5.975F, -0.094F, 0.065F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(4.789F, -0.075F, 0.053F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(19.127F, -0.32F, 0.171F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(23.916F, -0.408F, 0.196F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(23.916F, -0.408F, 0.196F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.005F, -0.092F, 0.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.005F, -0.092F, 0.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, -0.036F, 0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.005F, -0.092F, 0.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-0.005F, -0.092F, 0.516F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(207.205F, -89.757F, -281.494F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(116.795F, -84.177F, -197.792F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(87.963F, -75.475F, -176.786F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(137.832F, -82.941F, -163.434F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(107.752F, -90.699F, -70.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(37.211F, -119.547F, 12.123F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(45.323F, -147.6F, 20.397F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(63.03F, -175.631F, 36.328F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(101.326F, -190.422F, 74.506F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(130.801F, -195.209F, 115.124F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(159.167F, -188.765F, 154.57F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(161.333F, -185.954F, 157.041F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(3.217F, -18.354F, 9.085F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(6.162F, -20.435F, 9.945F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(7.006F, -20.1F, 10.107F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(11.943F, -17.407F, 9.841F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(18.507F, -12.833F, 10.179F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(17.754F, -12.767F, 7.858F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(18.313F, -12.172F, 4.268F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(20.661F, -10.533F, -0.51F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(26.887F, -7.279F, -4.846F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(34.044F, -7.678F, -7.652F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(40.109F, -11.632F, -9.153F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(40.688F, -11.964F, -9.051F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.561F, -0.069F, -0.131F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-15.56F, -0.069F, -0.131F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-37.186F, 0.059F, 0.022F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-28.56F, -0.014F, 0.024F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-8.521F, -0.02F, -0.064F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-7.78F, -0.03F, -0.068F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.001F, -0.025F, 0.133F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.001F, -0.025F, 0.133F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.111F, 0.302F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.008F, 0.067F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(255.476F, 15.61F, -45.594F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(271.995F, 24.89F, 31.952F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(278.797F, 26.332F, 108.373F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(198.333F, 8.937F, 129.769F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(122.573F, -9.285F, 180.424F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(120.359F, 12.325F, 174.794F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(110.083F, 32.35F, 167.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(91.11F, 50.971F, 147.307F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(43.831F, 50.179F, 95.597F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(21.539F, 34.561F, 68.161F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(22.799F, 17.622F, 50.256F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(23.629F, 16.723F, 49.853F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(2.249F, -8.291F, -0.227F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(14.534F, -5.422F, -0.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(23.037F, -3.921F, -0.795F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(30.828F, -2.717F, -0.737F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(33.921F, -3.272F, 0.058F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(36.669F, -3.823F, 0.814F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(38.73F, -4.653F, 1.447F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(39.643F, -5.777F, 1.515F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(40.556F, -5.793F, 1.343F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(41.817F, -5.36F, 1.07F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(41.888F, -5.373F, 1.003F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(21.278F, -0.109F, 0.187F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(34.474F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(57.326F, 0.115F, 0.022F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(59.933F, 0.063F, 0.036F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(69.062F, 0.029F, 0.038F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(60.061F, 0.016F, 0.02F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(43.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(41.692F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.018F, 0.065F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.056F, 0.118F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-134.341F, -21.642F, -331.941F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-147.792F, -13.406F, -282.394F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-155.332F, -18.044F, -173.637F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-173.229F, -0.201F, -155.066F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-180.449F, 16.302F, -132.613F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-168.757F, 31.303F, -106.193F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-132.268F, 45.159F, -63.659F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-105.1F, 21.781F, -25.621F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-84.456F, -15.553F, -16.763F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-91.601F, -28.975F, -1.493F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.558F, -10.747F, -0.195F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(6.147F, -10.164F, 0.139F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(10.53F, -9.161F, 0.353F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(16.811F, -6.756F, 0.125F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(23.402F, -3.77F, -0.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(29.562F, -3.775F, -1.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(33.007F, -3.659F, -1.917F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(36.647F, -3.657F, -1.841F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(39.378F, -4.624F, -1.58F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(41.399F, -6.027F, -1.162F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(41.57F, -6.104F, -1.079F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.183F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(62.74F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(35.183F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(79.347F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(94.436F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(95.578F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.67F, 0.005F, 0.124F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(88.61F, 0.0F, 0.041F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.173F, 0.168F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.042F, 0.102F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, 0.212F, 0.166F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.194F, 0.168F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition KNOCKDOWN_WAKEUP_RIGHT = AnimationDefinition.Builder.withLength(0.6F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-88.607F, 0.0F, 327.472F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-103.116F, 17.137F, 276.528F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-79.23F, 11.444F, 187.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-77.179F, -30.736F, 168.745F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-68.991F, -54.842F, 150.035F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-1.995F, -74.807F, 64.726F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(56.023F, -51.715F, -6.979F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(62.913F, -19.627F, -27.804F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(63.364F, -15.73F, -28.874F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.006F, -21.413F, 11.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-5.377F, -19.337F, 11.577F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-9.439F, -16.598F, 10.962F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-20.317F, -14.227F, 11.299F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-29.42F, -14.884F, 11.355F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-28.101F, -15.112F, 11.431F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-27.059F, -14.712F, 10.001F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-26.357F, -12.991F, 6.664F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-28.018F, -9.655F, 0.793F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-33.485F, -8.393F, -4.892F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-41.247F, -9.802F, -8.607F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-41.993F, -10.149F, -8.885F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-88.607F, 0.0F, 341.983F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-92.468F, 10.747F, 310.227F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-103.116F, 17.137F, 276.528F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-79.23F, 11.444F, 187.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-80.127F, -28.99F, 166.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-71.709F, -51.227F, 144.042F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-20.863F, -66.327F, 71.058F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(12.124F, -42.395F, 15.891F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(10.845F, -5.501F, -0.836F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(9.133F, -0.126F, -0.364F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.01F, -21.415F, 11.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-5.379F, -19.339F, 11.577F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-9.439F, -16.598F, 10.962F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-20.317F, -14.227F, 11.299F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-29.42F, -14.884F, 11.355F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-28.099F, -15.12F, 11.428F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-27.054F, -14.727F, 9.995F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-26.346F, -13.016F, 6.651F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-27.996F, -9.692F, 0.765F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-33.447F, -8.459F, -4.967F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-41.23F, -9.908F, -8.745F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-41.985F, -10.264F, -9.034F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(76.404F, 89.518F, 157.837F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-3.658F, 88.114F, 84.742F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-80.1F, 92.72F, 15.807F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-41.799F, 92.612F, -8.696F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(10.637F, 88.774F, -17.701F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(34.211F, 115.294F, -15.769F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(50.849F, 139.58F, -24.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(67.509F, 163.268F, -43.053F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(92.859F, 179.852F, -79.127F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(126.055F, 190.123F, -112.626F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(160.013F, 182.378F, -148.023F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(164.319F, 180.12F, -153.063F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-3.315F, -19.533F, 9.089F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-4.965F, -21.767F, 9.511F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-4.934F, -20.685F, 8.949F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-10.93F, -16.621F, 9.524F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-18.051F, -12.502F, 10.061F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-17.771F, -11.94F, 7.958F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-18.548F, -10.966F, 4.893F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-20.604F, -9.226F, 0.525F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-25.764F, -7.348F, -5.059F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-33.175F, -8.589F, -8.601F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-40.671F, -12.086F, -9.211F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-41.275F, -12.528F, -9.117F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-31.678F, 0.348F, 0.328F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-18.179F, -0.095F, -0.087F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-27.52F, -0.017F, 0.021F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-24.022F, 0.0F, 0.042F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-15.604F, 0.009F, -0.037F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-14.611F, 0.051F, -0.039F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.004F, -0.089F, 0.256F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.001F, -0.028F, 0.158F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.064F, 0.231F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.019F, 0.126F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(207.185F, -89.757F, 80.561F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(131.256F, -107.781F, 155.883F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(143.514F, -125.796F, 142.653F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(114.129F, -137.022F, 108.649F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(102.338F, -151.759F, 57.206F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(67.207F, -136.935F, 40.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(38.435F, -111.144F, 26.388F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(53.695F, -79.881F, -23.046F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(35.218F, -50.296F, -34.478F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(24.648F, -27.882F, -55.197F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.555F, -15.581F, -75.374F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(5.278F, -15.962F, -77.869F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.813F, -18.557F, 9.085F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-5.712F, -14.733F, 7.583F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-12.025F, -11.121F, 6.03F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-26.852F, -7.6F, 7.318F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-39.211F, -11.03F, 8.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-38.856F, -12.227F, 10.311F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-37.918F, -12.876F, 10.975F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-36.109F, -12.464F, 9.965F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-34.462F, -9.984F, 6.075F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-36.186F, -7.676F, 0.412F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-41.303F, -7.086F, -5.391F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-41.837F, -7.252F, -6.034F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.561F, -0.069F, -0.131F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-15.561F, -0.019F, -0.131F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-37.182F, 0.041F, -0.041F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-29.252F, 0.065F, 0.075F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10.049F, 0.0F, -0.057F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-7.78F, -0.03F, -0.068F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.001F, -0.025F, 0.133F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.001F, -0.025F, 0.134F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.112F, 0.302F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, -0.07F, 0.245F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.011F, 0.088F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.008F, 0.067F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(51.329F, 0.158F, -0.026F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(51.329F, 0.158F, -0.026F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.823F, 0.036F, -0.02F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(1.053F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(4.205F, 0.011F, -0.007F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(21.301F, 0.062F, -0.028F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(45.392F, 0.14F, -0.03F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(51.329F, 0.158F, -0.026F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.002F, 0.071F, 1.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.002F, 0.071F, 1.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, -0.008F, 0.021F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, -0.09F, 0.453F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.002F, 0.013F, 0.965F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.002F, 0.071F, 1.081F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-141.17F, 16.677F, 348.476F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-141.81F, 5.488F, 270.45F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-146.883F, 11.086F, 165.901F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-163.738F, -4.992F, 149.555F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-172.354F, -19.388F, 129.018F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-165.802F, -37.851F, 97.978F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-122.486F, -49.946F, 28.616F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-108.13F, -15.391F, -3.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-103.49F, 29.674F, -9.555F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-96.802F, 34.054F, -2.962F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.635F, -10.544F, -0.177F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-10.992F, -9.463F, 0.431F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-16.879F, -6.961F, 0.322F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-23.319F, -3.505F, 0.241F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-32.396F, -3.242F, -1.635F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-35.445F, -4.032F, -2.034F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-38.603F, -5.147F, -1.812F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-41.444F, -6.35F, -1.007F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-41.695F, -6.433F, -0.876F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.134F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(69.369F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(13.715F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(62.298F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(81.171F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(89.24F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(95.838F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(87.631F, 0.071F, -0.019F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.189F, 0.168F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.009F, 0.043F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.196F, 0.168F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.191F, 0.168F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(277.801F, -15.435F, 3.933F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(264.152F, -27.365F, -15.247F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(241.768F, -36.242F, -24.555F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(185.15F, 7.462F, -86.604F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(119.855F, 11.387F, -180.399F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(112.197F, -25.976F, -173.414F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(100.954F, -43.977F, -163.613F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(70.496F, -53.658F, -130.718F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(24.669F, -48.164F, -72.088F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(21.504F, -15.438F, -39.154F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(22.181F, -9.049F, -33.069F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.148F, -8.568F, -0.225F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-14.242F, -5.683F, -0.808F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-23.121F, -3.951F, -0.521F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-30.898F, -2.957F, -0.261F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-36.355F, -3.767F, 1.082F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-38.225F, -4.372F, 1.636F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-38.93F, -5.151F, 1.866F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-40.1F, -5.24F, 1.559F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-41.913F, -5.063F, 0.895F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-42.11F, -5.026F, 0.819F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.592F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(20.498F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(59.957F, -0.038F, -0.016F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(65.648F, -0.009F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(77.609F, -0.014F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(67.735F, -0.094F, -0.135F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(49.125F, -0.26F, -0.3F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(45.586F, -0.078F, 0.09F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.002F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.159F, 0.166F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.066F, 0.126F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition ROLL_BACKWARD = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.395F, 83.574F, 13.418F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(70.449F, 121.722F, 26.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(90.249F, 163.585F, 16.532F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(131.258F, 180.433F, 2.766F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(176.943F, 182.118F, -11.347F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(211.22F, 175.483F, -14.566F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(255.072F, 160.082F, -6.293F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(313.073F, 159.267F, 55.769F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(256.809F, 189.325F, 133.68F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(259.886F, 161.428F, 166.003F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.253F, -2.42F, 0.667F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-5.268F, -2.058F, 13.235F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-2.088F, -3.864F, 24.988F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.332F, -10.006F, 36.522F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(1.156F, -16.218F, 44.639F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.699F, -21.123F, 50.467F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.357F, -22.353F, 55.352F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(1.08F, -17.297F, 56.488F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-1.749F, -12.761F, 64.351F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-3.717F, -12.701F, 71.79F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-187.264F, 67.267F, -186.831F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-122.008F, 46.82F, -173.229F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-72.66F, 8.589F, -166.871F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-24.042F, -2.173F, -182.153F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(24.415F, 4.938F, -188.552F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(60.628F, 9.913F, -180.721F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(104.126F, 14.397F, -165.087F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(152.538F, -4.011F, -112.353F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(69.033F, -1.591F, -16.63F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(50.197F, 3.234F, -14.651F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.299F, -2.429F, 0.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-5.275F, -2.044F, 13.213F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-2.104F, -3.789F, 24.996F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.349F, -9.955F, 36.603F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(1.161F, -16.244F, 44.742F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.719F, -21.202F, 50.538F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.347F, -22.441F, 55.352F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(1.059F, -17.333F, 56.435F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-1.737F, -12.775F, 64.347F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-2.576F, -12.79F, 67.882F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-3.749F, -12.819F, 71.76F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-314.33F, -297.791F, 17.921F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-256.938F, -256.459F, 45.566F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-254.961F, -217.605F, 19.955F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-254.563F, -200.485F, 11.884F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-269.426F, -189.98F, 0.265F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-265.082F, -192.87F, 6.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-198.428F, -167.831F, 43.941F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-92.283F, -110.617F, 21.532F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-14.631F, -47.599F, 38.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-3.339F, -49.674F, 17.419F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(4.421F, -42.811F, -3.945F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(3.32F, -2.375F, 5.828F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(3.803F, -2.386F, 16.239F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(8.323F, -3.047F, 24.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(9.811F, -6.739F, 34.7F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(10.728F, -11.457F, 43.971F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(10.195F, -16.314F, 51.243F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(9.26F, -18.672F, 58.799F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(8.205F, -20.281F, 60.376F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.38F, -16.112F, 65.308F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-1.917F, -14.618F, 70.261F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-2.909F, -12.96F, 75.614F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-55.008F, -10.221F, -10.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-54.556F, -2.813F, -2.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-58.076F, -6.911F, -3.417F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-59.57F, -0.761F, -12.944F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-67.719F, 22.611F, -41.42F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-90.371F, 37.847F, -79.935F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-83.805F, 19.646F, -62.101F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-57.506F, 17.312F, -27.474F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-37.385F, 1.966F, 2.114F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.156F, -0.066F, 0.577F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.037F, -0.176F, 0.452F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.085F, -0.045F, 0.44F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.79F, 0.575F, -0.231F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.515F, 0.497F, 0.121F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.024F, -0.15F, 0.268F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.777F, 0.106F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.035F, 63.333F, -45.799F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-142.81F, 48.092F, -122.174F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-191.265F, 8.203F, -149.091F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-180.634F, -22.992F, -164.482F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-144.768F, -43.846F, -198.225F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-113.712F, -35.649F, -234.769F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-107.777F, 12.334F, -253.906F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-173.139F, 70.143F, -285.676F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-140.965F, 152.546F, -240.253F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-128.53F, 157.15F, -242.218F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-120.766F, 158.242F, -250.896F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-6.249F, -2.841F, -4.257F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-11.601F, -0.445F, 7.926F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-11.241F, -0.877F, 21.821F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-10.366F, -7.023F, 34.933F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-9.336F, -13.764F, 43.797F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-9.993F, -18.605F, 49.506F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-10.961F, -18.53F, 53.938F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-6.348F, -9.243F, 56.997F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-1.839F, -8.272F, 66.173F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-2.056F, -9.233F, 68.39F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-3.034F, -10.256F, 70.71F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.866F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-57.305F, 1.699F, 1.761F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-55.928F, 1.372F, 1.774F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-28.64F, 0.552F, -0.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-30.768F, 4.891F, 8.509F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.099F, 0.285F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.019F, -0.203F, 0.434F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, -0.075F, 0.249F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.1F, 0.061F, 0.34F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.484F, -8.115F, 1.392F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(19.223F, -11.009F, 0.932F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.028F, -0.791F, 0.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.009F, -0.841F, 0.302F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-76.131F, 67.35F, -26.023F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-0.768F, 119.248F, 35.023F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-7.495F, 158.309F, 7.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(36.269F, 166.62F, -1.931F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(90.05F, 171.588F, -2.037F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(130.187F, 168.096F, 6.441F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(188.0F, 168.485F, 43.308F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(185.306F, 215.9F, 99.807F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(146.981F, 169.94F, 185.442F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(111.167F, 162.965F, 186.991F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.259F, -2.259F, 1.045F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(1.415F, 0.581F, 8.749F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(2.795F, 3.797F, 15.462F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(3.369F, 5.421F, 26.637F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(3.323F, 5.039F, 39.806F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(3.129F, 0.925F, 51.355F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(2.833F, -4.399F, 62.712F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(1.523F, -8.934F, 65.365F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.145F, -7.493F, 73.906F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.107F, -6.645F, 80.209F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.446F, 1.343F, -1.132F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(97.734F, 4.563F, -1.091F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(109.145F, 2.712F, -1.881F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(109.793F, 0.957F, -3.025F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(101.182F, -1.766F, -4.828F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(125.0F, 4.7F, -2.238F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(120.444F, -5.39F, -5.199F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.004F, 0.176F, 0.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.024F, 0.296F, 0.143F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.199F, 38.948F, -20.44F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-81.037F, 60.949F, -77.668F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-121.797F, 41.957F, -138.361F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-99.478F, 13.339F, -155.948F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-68.696F, -0.212F, -177.526F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-55.127F, 10.418F, -187.594F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-45.153F, 33.268F, -171.9F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(43.778F, -14.87F, -98.209F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(17.465F, -1.842F, -22.233F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(8.711F, -1.821F, -22.659F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.422F, -1.857F, -1.268F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-1.56F, 2.222F, 5.302F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-2.87F, 5.952F, 12.79F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-3.749F, 7.2F, 25.737F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-4.284F, 5.405F, 39.629F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-4.347F, 0.871F, 50.263F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-3.783F, -2.95F, 60.412F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-2.09F, -5.096F, 65.688F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.48F, -5.706F, 74.073F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.178F, -5.791F, 79.617F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(57.426F, -3.183F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(9.085F, -4.923F, 0.877F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.999F, -5.142F, 1.723F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.999F, -5.06F, 2.809F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(16.557F, -4.836F, 3.929F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(63.132F, -5.009F, 5.181F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(37.45F, -4.515F, 11.555F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(59.067F, -4.692F, 10.713F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(72.48F, -6.156F, 7.337F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.01F, 0.099F, 0.141F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.01F, 0.005F, -0.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.034F, 0.072F, 0.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.023F, 0.158F, 0.152F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition ROLL_FORWARD = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-305.519F, 18.393F, 16.711F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-259.585F, -7.826F, 26.334F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-233.223F, -12.078F, 23.976F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-199.833F, -10.211F, 18.385F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-172.725F, -8.949F, 20.799F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-110.81F, -20.002F, 16.488F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-58.002F, -25.542F, -1.281F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(32.774F, -11.355F, -15.694F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(51.012F, -1.109F, -14.446F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.184F, -5.238F, -7.746F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.4F, -4.994F, -19.805F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.017F, -5.577F, -29.827F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.355F, -9.623F, -41.087F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.595F, -14.063F, -51.915F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-1.91F, -20.071F, -56.977F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-3.3F, -22.047F, -64.737F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-3.733F, -20.407F, -67.447F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-2.609F, -12.867F, -79.794F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-2.083F, -13.456F, -83.505F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-295.069F, 4.693F, 14.052F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-249.099F, -12.383F, 14.815F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-212.044F, -15.41F, 15.619F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-168.036F, -14.446F, 9.526F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-141.331F, -9.989F, 11.223F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-79.216F, -12.86F, 9.915F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-39.983F, -18.233F, -1.984F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(32.824F, -7.958F, -5.674F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.792F, 4.393F, -12.196F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.211F, -5.191F, -7.718F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.378F, -4.945F, -19.802F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.0F, -5.537F, -29.844F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.355F, -9.573F, -41.152F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.602F, -14.061F, -52.035F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-1.937F, -20.118F, -57.083F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-3.351F, -22.153F, -64.747F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-3.767F, -20.457F, -67.409F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-2.633F, -12.867F, -79.793F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-2.064F, -13.508F, -83.561F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(65.781F, -0.699F, -335.815F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(30.461F, -48.199F, -310.727F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-71.317F, -60.682F, -239.709F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-105.989F, -56.498F, -228.057F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-196.607F, -82.079F, -140.518F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-259.273F, -68.492F, -55.849F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-218.575F, -23.115F, -58.595F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-193.774F, 19.143F, -55.518F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-72.84F, 9.301F, -10.755F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-33.795F, 17.365F, -0.517F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.63F, 18.165F, 12.894F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.215F, -3.242F, -4.661F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.027F, -1.778F, -17.083F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.064F, -1.642F, -28.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.752F, -5.721F, -40.374F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-1.495F, -9.788F, -50.664F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-2.248F, -14.831F, -55.887F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-2.98F, -16.296F, -66.564F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-3.305F, -16.993F, -71.498F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-1.927F, -13.577F, -81.905F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-1.787F, -14.622F, -83.828F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-101.949F, -0.821F, 0.103F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-18.075F, 0.585F, 1.271F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-28.329F, -1.246F, -2.173F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-61.672F, -2.254F, -1.012F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-46.152F, 11.919F, 13.757F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-62.836F, 1.582F, 5.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-47.407F, 0.2F, 5.472F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.007F, -0.624F, 0.488F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.012F, -0.052F, 0.145F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.023F, -0.26F, 0.474F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.106F, -0.426F, 0.144F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.028F, -0.384F, 0.412F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.028F, -0.271F, 0.362F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.777F, 0.106F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(47.231F, 41.992F, 18.638F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(14.11F, 48.66F, -1.56F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-20.727F, 44.699F, -22.334F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(6.898F, 43.351F, -7.787F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(55.638F, 38.077F, 17.337F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(94.641F, 13.598F, 18.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(133.531F, 2.229F, 13.215F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(161.345F, -6.29F, 10.044F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(254.141F, -24.088F, -7.785F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(282.478F, -16.372F, -22.212F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(306.656F, -2.771F, -30.044F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.639F, -6.432F, -7.849F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.004F, -6.55F, -18.329F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.517F, -6.841F, -27.053F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.038F, -10.048F, -38.011F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.457F, -13.258F, -49.315F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-1.969F, -19.228F, -54.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-3.44F, -20.15F, -62.3F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-3.827F, -16.861F, -65.704F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-2.269F, -10.097F, -80.085F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-1.683F, -11.541F, -83.646F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-44.075F, -0.777F, -1.534F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-31.639F, -0.294F, -0.726F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-53.59F, -2.388F, -2.933F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-58.026F, 0.553F, 0.803F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-73.778F, 13.302F, 5.716F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-85.588F, 16.565F, 3.816F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-92.836F, 0.569F, 0.087F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-100.789F, 2.555F, -0.205F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-83.235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.014F, -0.179F, 0.332F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.008F, -0.236F, 0.429F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.188F, -0.425F, 0.752F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.023F, -0.616F, 0.52F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.459F, 0.486F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.777F, 0.106F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-31.939F, 15.595F, 16.118F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-17.996F, -9.267F, 17.64F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(8.13F, -14.618F, 20.819F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(38.911F, -20.343F, 17.601F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(107.707F, -31.722F, 0.144F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(148.206F, -20.899F, -5.676F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(212.428F, -7.209F, -20.429F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(234.81F, 1.611F, -19.688F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(236.176F, 6.003F, -11.562F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.008F, -1.649F, 0.197F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.026F, 7.072F, -18.681F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.26F, 7.076F, -30.915F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.512F, 6.105F, -44.774F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.522F, 2.821F, -54.859F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.483F, -2.471F, -70.9F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.214F, -9.218F, -77.613F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.08F, -10.865F, -79.834F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.028F, -10.955F, -80.074F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(76.081F, -0.664F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(102.076F, -0.84F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(102.189F, -1.229F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(99.442F, -8.069F, -1.747F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(75.861F, -6.122F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(60.647F, -4.744F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(86.157F, -5.334F, -1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(84.696F, -1.045F, -1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.002F, 0.155F, 0.167F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.027F, 0.23F, 0.175F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.013F, 0.108F, 0.159F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.003F, 0.183F, 0.17F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.093F, 8.177F, 7.577F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(54.741F, -9.371F, 6.048F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(89.531F, -16.808F, 17.195F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(101.126F, -16.598F, 13.643F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(123.116F, -16.293F, 13.463F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(148.487F, -13.42F, -0.91F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(222.379F, -10.205F, -11.019F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(248.742F, -1.284F, 2.784F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1F, -2.507F, -0.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.134F, 6.21F, -17.859F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.044F, 5.967F, -30.261F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.234F, 4.832F, -44.157F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.178F, 1.594F, -53.645F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.08F, -3.148F, -69.181F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.127F, -8.231F, -76.123F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.079F, -9.63F, -79.682F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.039F, -10.106F, -80.318F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(43.591F, -0.626F, 1.411F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.0F, 0.724F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(1.0F, 1.026F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(9.253F, -0.877F, 6.347F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(27.551F, -0.72F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(38.459F, -0.723F, 1.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(46.784F, -1.876F, 1.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.004F, 0.063F, 0.121F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.003F, 0.002F, 0.004F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.007F, 0.071F, 0.125F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition STEP_BACKWARD = AnimationDefinition.Builder.withLength(0.4F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.895F, -49.478F, -8.47F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(2.565F, -30.253F, 4.723F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-13.065F, -9.145F, 4.75F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(3.015F, 7.204F, 2.629F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(38.212F, 36.246F, 15.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(62.408F, 46.34F, 28.395F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(74.869F, 55.394F, 43.881F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(94.989F, 60.795F, 67.025F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.989F, -5.261F, -1.123F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.285F, -4.639F, 11.163F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.012F, -4.893F, 23.367F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.337F, -3.441F, 32.23F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.134F, -2.626F, 40.91F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.161F, -3.193F, 47.013F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-1.376F, -4.24F, 53.288F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-2.107F, -6.646F, 59.855F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.577F, -4.666F, 2.869F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(14.982F, -1.004F, -0.057F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(17.445F, 8.569F, -1.435F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.028F, -5.271F, -1.135F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.297F, -4.633F, 11.201F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.016F, -4.887F, 23.466F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.341F, -3.433F, 32.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.121F, -2.632F, 40.896F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.205F, -3.221F, 46.966F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-1.457F, -4.3F, 53.225F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-2.197F, -6.69F, 59.828F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.405F, -53.916F, 48.001F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-0.833F, -30.246F, 33.339F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(2.793F, -6.209F, 19.595F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(5.123F, -7.482F, 29.815F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(35.532F, 9.533F, 33.025F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(69.938F, 20.43F, 43.59F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(106.543F, 21.531F, 57.654F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(2.024F, -5.851F, -4.443F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.521F, -4.343F, 8.735F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.116F, -4.357F, 22.228F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.526F, -2.159F, 44.068F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.401F, -1.962F, 51.552F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(1.637F, -2.063F, 58.651F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(3.506F, -3.96F, 65.705F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.367F, -3.44F, -5.425F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-38.995F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-38.354F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-28.222F, -2.773F, -5.445F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.062F, -0.504F, 0.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, -0.594F, 0.309F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.059F, -0.452F, 0.284F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.311F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(31.454F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(18.44F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(16.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.306F, 0.059F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.32F, -0.368F, 0.705F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.32F, -0.379F, 0.371F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(53.789F, -58.514F, -51.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(13.503F, -43.572F, -20.479F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-8.402F, -22.192F, -16.283F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(12.166F, 5.676F, -24.236F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(26.278F, 54.288F, -24.766F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(7.859F, 67.976F, -42.156F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-39.347F, 73.876F, -87.364F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.783F, -4.674F, 3.127F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.335F, -4.991F, 13.616F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.061F, -5.268F, 23.593F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.454F, -3.634F, 31.557F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.037F, -2.979F, 39.406F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.657F, -3.798F, 44.824F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-2.293F, -5.04F, 50.561F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-3.757F, -6.821F, 53.027F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-5.376F, -8.519F, 55.848F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.543F, 4.963F, 7.949F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-38.029F, 8.11F, 12.846F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-39.408F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-37.863F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.095F, -0.46F, 0.389F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.165F, -0.392F, 0.445F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, -0.6F, 0.316F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, -0.592F, 0.306F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(36.454F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(11.606F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(3.462F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.32F, -0.345F, 0.811F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.32F, -0.365F, 0.275F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.32F, -0.321F, 0.103F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-66.84F, -31.474F, 22.738F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-73.389F, -10.064F, 12.622F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-44.033F, 0.885F, 7.976F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-22.853F, 26.547F, 18.89F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(9.47F, 37.102F, 58.914F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.406F, -4.571F, -1.262F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.089F, -2.897F, 17.896F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.021F, -1.898F, 41.153F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.187F, -2.618F, 58.49F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.378F, -5.079F, 65.361F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.822F, -10.0F, -1.052F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(36.953F, -0.014F, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(77.751F, 0.933F, -0.991F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(72.717F, -2.169F, -1.854F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(69.565F, 1.571F, -2.339F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.033F, 0.176F, 0.182F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.001F, 0.047F, 0.107F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.003F, 0.161F, 0.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.007F, 0.138F, 0.158F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-39.863F, -49.151F, -25.298F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-55.64F, -37.425F, 0.327F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-59.299F, -18.283F, 12.389F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-66.144F, 23.954F, -13.755F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-75.53F, 36.346F, -31.41F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.217F, -5.024F, 0.997F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.053F, -3.782F, 18.564F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.003F, -2.072F, 41.112F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.317F, -2.034F, 56.496F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.609F, -4.94F, 62.497F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(95.237F, 6.546F, 0.957F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(43.673F, -0.701F, 0.13F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(93.436F, 0.086F, 1.175F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(80.348F, 4.361F, 1.561F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(83.38F, 5.957F, 0.982F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.022F, 0.216F, 0.175F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.002F, 0.061F, 0.121F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.211F, 0.166F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.019F, 0.178F, 0.177F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition STEP_FORWARD = AnimationDefinition.Builder.withLength(0.4F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(59.536F, 24.997F, 24.571F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(74.95F, 39.435F, 50.306F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(71.488F, 21.881F, 34.81F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(51.264F, 8.056F, 14.116F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(41.743F, -17.563F, -1.869F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(41.986F, -41.833F, -16.19F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.323F, -6.299F, -6.504F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.567F, -7.094F, -26.447F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-1.048F, -7.077F, -39.774F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.891F, -7.31F, -53.313F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.442F, -6.294F, -57.889F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.894F, -5.929F, -62.266F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(1.131F, -4.332F, -68.142F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.704F, 0.257F, 1.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(12.984F, 4.793F, 1.286F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(11.24F, -0.62F, 0.046F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.362F, -6.382F, -6.606F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.621F, -7.17F, -26.542F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-1.094F, -7.173F, -39.885F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.925F, -7.428F, -53.437F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.449F, -6.378F, -58.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.937F, -5.983F, -62.347F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(1.19F, -4.369F, -68.204F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(77.587F, 19.477F, 51.176F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(92.034F, 16.701F, 66.817F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(87.537F, 5.531F, 61.319F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(59.028F, 3.584F, 44.46F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(32.813F, -13.059F, 30.049F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-3.924F, -31.578F, 23.093F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.161F, -3.96F, -2.192F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.708F, -3.906F, -21.626F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.372F, -4.154F, -35.466F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.337F, -4.621F, -49.809F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.34F, -4.913F, -55.485F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.553F, -5.702F, -61.668F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(1.567F, -4.868F, -70.218F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-31.489F, -2.03F, -5.398F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-33.208F, -1.355F, -4.532F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-23.356F, 0.201F, -4.957F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-28.918F, 1.748F, -5.302F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-28.918F, 1.748F, -5.302F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.054F, -0.467F, 0.296F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.037F, -0.455F, 0.211F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.938F, 0.476F, 0.143F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(17.056F, 0.909F, 0.3F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(5.791F, 1.032F, 0.377F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(15.791F, 1.032F, 0.377F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.314F, -0.34F, 0.147F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.312F, -0.386F, 0.377F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.306F, -0.343F, 0.134F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.309F, -0.385F, 0.347F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.189F, 58.096F, -15.714F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(49.195F, 55.644F, 5.219F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(61.056F, 42.578F, -0.962F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(42.664F, 21.31F, -14.774F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(34.893F, -3.55F, -24.969F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(47.469F, -22.568F, -35.166F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.469F, -7.897F, -5.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-2.282F, -9.753F, -27.421F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-2.065F, -9.252F, -40.199F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-1.478F, -9.033F, -52.891F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.378F, -6.844F, -56.541F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.32F, -5.248F, -59.003F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.544F, -4.009F, -60.705F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-1.885F, -2.978F, -62.813F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-55.357F, 0.847F, 4.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-39.085F, -0.423F, 3.026F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-36.057F, -1.017F, 3.132F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-38.529F, -1.017F, 3.132F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.034F, -0.622F, 0.422F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.017F, -0.541F, 0.292F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.919F, -0.489F, -0.141F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-7.274F, -0.799F, -0.232F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-8.6F, -0.929F, -0.302F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.903F, -1.059F, -0.37F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.314F, -0.339F, 0.146F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.307F, -0.231F, -0.124F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.304F, -0.218F, -0.151F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.304F, -0.309F, 0.033F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-54.152F, 25.877F, 10.266F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-20.325F, 31.887F, 31.115F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(37.97F, 34.225F, 44.575F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(17.739F, 22.938F, 28.254F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-14.477F, 16.525F, 17.139F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-50.998F, 2.991F, 8.576F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-63.999F, -11.786F, 17.01F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-52.337F, -20.203F, 21.839F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.295F, -3.31F, 0.718F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.553F, -3.162F, -17.337F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.047F, -2.701F, -44.231F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.025F, -3.579F, -51.61F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.396F, -4.32F, -58.976F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.692F, -3.174F, -65.609F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(48.461F, 9.431F, -1.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(76.131F, 3.721F, -4.076F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(28.25F, -7.291F, -1.005F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(95.865F, -4.109F, -2.124F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(104.87F, 3.302F, -1.422F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(79.276F, -1.864F, -1.206F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(55.208F, -3.373F, -0.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.026F, 0.077F, 0.113F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.014F, 0.22F, 0.172F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.009F, 0.092F, 0.149F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-23.083F, 20.517F, -3.691F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-48.734F, 27.535F, 16.034F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-56.963F, 11.973F, 8.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-56.086F, 14.225F, -10.816F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-35.737F, -6.314F, -10.443F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-1.683F, -22.893F, -27.1F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(9.261F, -35.447F, -36.985F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.078F, -3.877F, -0.926F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.608F, -4.487F, -19.833F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.1F, -3.108F, -45.47F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.001F, -3.637F, -51.013F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.347F, -4.317F, -56.422F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.528F, -3.229F, -62.541F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.646F, -1.368F, 2.298F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(124.406F, 5.673F, 3.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(80.426F, 6.374F, 1.009F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(56.075F, -1.407F, 1.745F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(83.261F, -1.576F, 2.289F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(45.306F, 5.668F, -0.255F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.005F, 0.179F, 0.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.021F, 0.303F, 0.136F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.005F, 0.181F, 0.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.015F, 0.065F, 0.135F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition STEP_LEFT = AnimationDefinition.Builder.withLength(0.4F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(36.84F, -62.297F, 17.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(23.813F, -58.034F, 35.385F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(32.246F, -19.829F, 34.674F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(48.275F, 14.695F, 2.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(43.303F, 22.344F, 0.85F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(3.735F, -7.528F, -2.133F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(13.165F, -6.507F, -2.38F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(29.742F, -7.277F, -2.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(48.719F, -6.574F, -3.695F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(52.829F, -5.341F, -4.626F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(56.604F, -5.425F, -4.35F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(61.792F, -8.202F, -4.559F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(9.49F, -11.872F, 12.873F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(7.582F, -26.518F, 15.195F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(11.444F, -9.328F, 8.259F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(16.726F, 0.917F, -2.073F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(10.467F, 2.219F, -3.016F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(3.85F, -7.615F, -2.15F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(13.278F, -6.599F, -2.398F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(29.852F, -7.373F, -2.385F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(48.818F, -6.663F, -3.761F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(52.865F, -5.408F, -4.72F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(56.574F, -5.495F, -4.443F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(61.745F, -8.265F, -4.655F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.032F, -10.764F, 57.842F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.499F, -11.384F, 83.01F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(28.745F, 12.139F, 80.888F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(51.629F, 18.982F, 47.779F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(65.937F, 24.633F, 37.909F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(5.005F, -6.858F, -6.56F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(14.161F, -5.262F, -6.765F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(30.683F, -5.38F, -6.835F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(39.302F, -4.269F, -6.241F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(48.098F, -3.291F, -5.141F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(51.993F, -3.45F, -4.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(56.47F, -4.92F, -2.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(62.187F, -7.797F, -1.784F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-23.42F, 0.012F, -0.066F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-15.268F, -4.115F, 0.304F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-26.844F, -4.801F, -8.825F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-22.473F, 1.588F, 1.009F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-37.473F, 1.588F, 1.009F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.523F, 0.199F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.103F, -0.393F, 0.308F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.013F, -0.538F, 0.163F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.015F, -0.607F, 0.275F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.798F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(37.211F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(8.793F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(8.515F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.382F, 0.409F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.32F, -0.341F, 0.827F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.32F, -0.385F, 0.522F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.32F, -0.353F, 0.215F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.32F, -0.351F, 0.209F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(60.276F, -48.899F, -9.965F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(63.985F, -22.493F, -5.497F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(56.02F, -12.414F, 10.044F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(55.478F, 1.062F, 23.435F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(36.504F, 21.114F, -17.672F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(50.393F, 26.471F, -20.598F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.451F, -7.493F, 2.98F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(9.865F, -6.283F, 2.667F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(27.136F, -6.905F, 2.533F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(37.131F, -7.965F, 0.773F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(46.493F, -8.562F, -0.997F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(52.116F, -6.644F, -3.417F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(56.678F, -5.635F, -4.808F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(62.085F, -7.535F, -5.119F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.39F, 0.034F, -0.041F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-44.783F, 2.128F, -3.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-54.64F, -0.251F, -0.224F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.549F, 0.246F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.008F, -0.703F, 0.387F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.003F, -0.705F, 0.4F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-0.87F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(16.288F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.32F, -0.288F, 0.015F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.32F, -0.379F, 0.376F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-72.847F, -64.625F, -5.296F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-52.407F, -61.229F, 19.226F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-16.135F, -56.427F, 45.099F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-68.918F, -33.644F, 35.028F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-64.154F, -11.659F, 15.417F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-67.079F, -11.633F, 5.968F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-80.234F, 14.1F, 17.701F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(1.099F, -4.861F, -1.634F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(7.994F, -3.533F, -1.813F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(22.176F, -2.907F, -1.96F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(42.114F, -2.457F, -1.478F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(57.711F, -3.373F, -0.721F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(63.957F, -6.343F, 0.084F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(77.168F, 0.22F, -0.967F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(80.986F, -1.529F, -1.286F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(30.06F, 1.26F, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(88.616F, 3.311F, -1.79F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(104.953F, 0.694F, -1.56F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(112.435F, 0.161F, -1.53F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(84.351F, -0.765F, -0.657F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.001F, 0.159F, 0.166F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.004F, 0.033F, 0.087F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.002F, 0.269F, 0.147F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.002F, 0.181F, 0.169F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.013F, -68.217F, -26.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-68.642F, -71.386F, 6.474F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-41.715F, -49.988F, -0.535F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-26.742F, -12.745F, -19.286F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-6.79F, 9.378F, -15.817F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.85F, -4.986F, 1.717F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(6.111F, -3.761F, 1.522F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(20.404F, -3.316F, 1.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(41.172F, -3.425F, 0.987F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(57.588F, -3.394F, 0.508F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(64.034F, -6.483F, -0.257F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(97.526F, 3.045F, 0.818F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(118.866F, -6.353F, 1.257F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(112.429F, 6.675F, 0.995F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(89.274F, 6.834F, 1.229F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(79.395F, -3.213F, 2.003F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(91.709F, -4.265F, 1.419F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.01F, 0.223F, 0.169F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.013F, 0.206F, 0.159F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition STEP_RIGHT = AnimationDefinition.Builder.withLength(0.4F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(39.564F, 15.304F, -9.621F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(33.31F, -4.455F, -16.844F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(18.294F, -23.705F, -30.397F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(23.614F, -28.744F, -26.382F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(34.504F, -50.291F, 10.884F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(24.751F, -59.099F, 12.56F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.151F, -6.662F, -4.082F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-7.367F, -5.529F, -3.881F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-23.184F, -5.12F, -2.021F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-35.117F, -4.127F, -2.699F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-45.246F, -2.797F, -2.622F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-49.815F, -3.704F, -2.61F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-54.54F, -5.132F, -2.509F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-60.316F, -5.605F, -1.916F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.601F, 4.234F, -5.96F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(19.105F, -4.608F, -4.892F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(13.261F, -9.356F, -12.052F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.738F, -7.945F, -9.408F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(10.078F, -9.472F, 2.194F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(5.635F, -22.546F, 10.898F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-2.191F, -6.721F, -4.164F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-7.398F, -5.561F, -3.929F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-23.225F, -5.135F, -2.034F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-35.135F, -4.139F, -2.723F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-45.24F, -2.808F, -2.655F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-49.758F, -3.734F, -2.651F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-54.44F, -5.195F, -2.548F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-60.239F, -5.647F, -1.937F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(44.282F, 12.954F, 15.957F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(32.858F, -4.825F, 9.693F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(12.94F, -17.299F, -1.118F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-13.097F, -40.919F, 12.312F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(5.052F, -42.095F, 18.681F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(3.795F, -56.951F, 27.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(10.663F, -66.583F, 47.678F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.522F, -7.128F, -1.672F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-6.884F, -6.797F, -3.345F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-21.465F, -7.389F, -3.674F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-33.789F, -6.035F, -4.706F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-44.23F, -3.346F, -5.539F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-49.057F, -3.347F, -5.665F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-53.836F, -3.832F, -5.718F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-59.174F, -4.388F, -5.749F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.39F, -0.034F, 0.041F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-47.027F, -1.209F, 1.939F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-45.796F, -3.674F, 3.206F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-41.441F, 0.251F, 0.224F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-38.734F, 0.251F, 0.224F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.549F, 0.246F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.008F, -0.695F, 0.419F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.003F, -0.601F, 0.307F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.148F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(15.668F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(30.537F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(7.101F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-1.763F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.268F, -0.03F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.32F, -0.371F, 0.685F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.32F, -0.28F, -0.003F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(32.423F, 27.442F, -32.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(35.434F, 4.354F, -40.777F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(32.998F, -21.698F, -59.744F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(38.325F, -27.006F, -60.237F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(44.703F, -36.528F, -53.775F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(68.841F, -42.71F, -58.462F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(77.905F, -58.036F, -26.681F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.626F, -5.022F, -4.51F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-6.948F, -3.673F, -2.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-23.179F, -2.81F, 0.612F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-35.288F, -2.158F, 0.303F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-45.919F, -2.021F, 0.902F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-56.753F, -5.658F, 2.017F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-63.495F, -5.71F, 2.762F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.42F, -0.012F, 0.066F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-32.202F, 4.115F, -0.304F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-36.82F, 3.333F, -2.089F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-33.274F, 2.225F, 5.142F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-46.626F, -1.587F, -1.009F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.594F, 0.309F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.006F, -0.622F, 0.356F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.053F, -0.48F, 0.312F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.016F, -0.663F, 0.333F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(11.534F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(2.072F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-12.928F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.32F, -0.365F, 0.274F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.32F, -0.162F, -0.215F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-79.635F, 22.651F, 1.091F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-73.187F, 4.074F, -11.586F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-49.775F, -17.243F, -36.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-40.097F, -25.189F, -29.632F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-42.936F, -39.097F, -16.114F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-41.703F, -37.039F, 13.156F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-17.789F, -43.115F, 14.513F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.016F, -4.86F, 0.366F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-4.582F, -4.448F, -0.369F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-17.094F, -4.137F, -1.051F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-43.392F, -2.399F, -1.553F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-57.21F, -3.557F, -1.182F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-63.449F, -4.397F, -1.382F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(77.37F, 0.0F, -0.5F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(78.759F, 2.037F, -0.708F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(55.254F, 5.718F, -2.382F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(75.764F, -3.756F, -0.939F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(107.616F, -4.973F, -0.512F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(96.78F, 5.059F, -1.111F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(74.327F, 2.699F, -1.102F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.159F, 0.166F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.019F, 0.096F, 0.133F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.016F, 0.253F, 0.162F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.009F, 0.15F, 0.16F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-18.632F, 15.415F, -3.566F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-22.704F, -3.462F, -16.878F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-31.454F, -23.177F, -37.03F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-47.125F, -32.864F, -43.124F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-29.305F, -46.739F, -26.193F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-61.576F, -46.913F, -11.79F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-47.994F, -46.175F, -21.72F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.056F, -4.992F, -0.42F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-4.622F, -3.737F, -0.062F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-17.824F, -2.306F, 0.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-31.013F, -1.879F, 0.508F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-44.304F, -1.827F, 0.905F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-57.813F, -3.189F, 0.99F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-64.432F, -4.318F, 1.273F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(92.37F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(49.256F, 0.702F, 0.931F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(82.813F, 0.76F, 1.222F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(81.604F, 1.289F, 1.115F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(48.408F, -3.168F, 3.855F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.206F, 0.167F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.015F, 0.08F, 0.126F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}