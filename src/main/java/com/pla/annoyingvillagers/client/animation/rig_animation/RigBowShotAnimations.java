package com.pla.annoyingvillagers.client.animation.rig_animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class RigBowShotAnimations {
	public static final AnimationDefinition BOW_SHOT_DOWN = AnimationDefinition.Builder.withLength(0.1F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(88.3738F, 15.3481F, 79.6738F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(89.9705F, 15.4319F, 85.685F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(90.1707F, 15.431F, 86.4375F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(90.7699F, 15.4132F, 88.6904F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.047F, -4.5609F, -6.4454F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0472F, -4.5623F, -6.4458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0473F, -4.5625F, -6.4458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0473F, -4.5631F, -6.446F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.9329F, 4.7927F, -5.1552F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(4.933F, 4.7929F, -5.1553F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(4.9329F, 4.7929F, -5.1553F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(4.9328F, 4.7929F, -5.1554F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0505F, -4.7245F, -6.6271F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0505F, -4.7245F, -6.6271F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0505F, -4.7245F, -6.6271F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0505F, -4.7245F, -6.6271F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-11.5568F, 9.5266F, 19.1956F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(2.9267F, 16.7958F, 72.4838F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(5.3518F, 16.6433F, 79.6534F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(12.3195F, 14.3338F, 100.8537F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(2.2068F, 0.9095F, -3.1487F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(2.9284F, 1.258F, -2.9148F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(3.0552F, 1.3396F, -2.8709F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(3.4707F, 1.6077F, -2.7343F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.4121F, -5.1526F, -28.684F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-7.6339F, -7.869F, -69.0756F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-6.945F, -7.9536F, -74.078F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-4.8743F, -7.8487F, -89.0573F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.3702F, -0.0787F, 0.1971F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-1.1141F, 0.1125F, 0.2028F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-1.2097F, 0.0991F, 0.1984F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-1.4837F, 0.0111F, 0.1787F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.3273F, 59.3071F, -13.1151F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-18.0105F, 60.7585F, -17.802F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-17.3032F, 60.9324F, -18.4614F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-15.2627F, 61.4405F, -20.5041F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-5.6578F, -9.104F, -5.4101F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-5.089F, -9.1326F, -5.3584F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-5.0175F, -9.1334F, -5.3567F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-4.8036F, -9.1317F, -5.3556F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.3122F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-12.9799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-13.8142F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-16.3123F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4812F, 0.0552F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0F, -0.4927F, 0.1126F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, -0.4946F, 0.1197F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -0.501F, 0.1407F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.8964F, 42.8946F, 10.8941F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(1.8964F, 42.8946F, 10.8941F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.8964F, 42.8946F, 10.8941F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.8964F, 42.8946F, 10.8941F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.1591F, -0.0127F, 0.8176F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.1591F, -0.0127F, 0.8176F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.1591F, -0.0127F, 0.8176F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.1591F, -0.0127F, 0.8176F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-11.9882F, 17.2784F, -5.5887F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-11.9882F, 17.2784F, -5.5887F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-11.9882F, 17.2784F, -5.5887F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-11.9882F, 17.2784F, -5.5887F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.191F, -0.0084F, -0.892F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.191F, -0.0084F, -0.892F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.191F, -0.0084F, -0.892F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-0.191F, -0.0084F, -0.892F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition BOW_SHOT_MID = AnimationDefinition.Builder.withLength(0.1F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5436F, 66.0309F, 0.7747F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-3.4738F, 72.6896F, -0.2177F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-3.6443F, 73.5223F, -0.3959F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-4.2782F, 76.0144F, -1.0526F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.5836F, -0.0301F, 0.4611F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.5842F, -0.0301F, 0.4595F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.5843F, -0.0301F, 0.4593F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.5844F, -0.0301F, 0.4587F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.9328F, 4.7927F, -5.1553F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(4.9327F, 4.7927F, -5.1553F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(4.9327F, 4.7927F, -5.1553F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(4.9327F, 4.7927F, -5.1552F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.6046F, -0.0263F, 0.4931F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.6045F, -0.0264F, 0.4931F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.6046F, -0.0264F, 0.4931F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.6046F, -0.0264F, 0.4931F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-88.7416F, 21.8577F, 0.108F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-88.5187F, 63.243F, -1.7289F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-88.3081F, 65.6669F, -1.8822F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-87.7429F, 72.9233F, -2.4067F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(1.5083F, -0.0883F, 3.5857F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(2.221F, -0.165F, 4.4534F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(2.4065F, -0.1711F, 4.5987F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(2.9972F, -0.1837F, 4.9841F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.8722F, -6.554F, -43.6975F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-9.1361F, -7.4028F, -63.8019F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-8.9152F, -7.4969F, -65.0654F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-8.2416F, -7.7493F, -68.8517F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.6302F, 0.0532F, 0.208F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-1.0111F, 0.1174F, 0.2077F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-1.0357F, 0.1169F, 0.2075F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-1.1091F, 0.1123F, 0.206F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-91.1394F, 8.8497F, -4.9539F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-89.3807F, -0.5547F, -17.175F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-89.039F, -1.69F, -18.7455F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-87.8379F, -5.0148F, -23.5178F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-4.3352F, 0.0651F, -5.4446F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-3.8736F, -0.199F, -5.5065F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-3.8104F, -0.2261F, -5.5127F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-3.6146F, -0.3F, -5.5281F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.3122F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-6.3122F, 0.0F, 6.6677F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-6.3122F, 0.0F, 7.502F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-6.3122F, 0.0F, 10.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4812F, 0.0552F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0646F, -0.3684F, 0.0552F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0736F, -0.3549F, 0.0552F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.1017F, -0.3151F, 0.0552F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.9063F, 43.0153F, 10.9085F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(1.9063F, 43.0153F, 10.9085F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.9063F, 43.0153F, 10.9085F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.9063F, 43.0153F, 10.9085F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8174F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8174F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8174F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8174F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-11.9899F, 17.3995F, -5.5943F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-11.9899F, 17.3995F, -5.5943F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-11.9899F, 17.3995F, -5.5943F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-11.9899F, 17.3995F, -5.5943F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition BOW_SHOT_UP = AnimationDefinition.Builder.withLength(0.1F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-82.5008F, 23.0261F, -63.8085F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-85.3706F, 23.7316F, -71.0325F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-85.7369F, 23.7962F, -71.9414F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-86.8405F, 23.958F, -74.6676F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.5836F, -3.8315F, 6.8059F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.5842F, -3.83F, 6.8052F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.5843F, -3.8298F, 6.8051F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.5844F, -3.8292F, 6.8048F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.9155F, -0.189F, -5.5847F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(4.9155F, -0.1889F, -5.5846F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(4.9155F, -0.1889F, -5.5846F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(4.9155F, -0.1889F, -5.5846F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.6032F, -3.9552F, 7.0422F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.6032F, -3.9552F, 7.0422F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.6032F, -3.9552F, 7.0422F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.6032F, -3.9552F, 7.0422F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-164.0907F, 2.9401F, -11.4954F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-169.4828F, 12.2301F, -50.6831F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-170.6377F, 13.0937F, -56.1251F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-174.5327F, 14.9637F, -72.6171F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(1.6083F, -5.6364F, 6.4085F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(2.4422F, -6.3589F, 6.606F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(2.5833F, -6.4705F, 6.6331F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(3.0285F, -6.7961F, 6.7089F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.4121F, -5.1526F, -28.684F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-10.2114F, -3.9559F, -55.6341F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-9.9845F, -3.7392F, -58.9776F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-9.3862F, -3.0165F, -68.9779F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.3702F, -0.0787F, 0.1971F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.8474F, 0.1159F, 0.1577F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.9115F, 0.1238F, 0.152F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-1.1046F, 0.1249F, 0.1343F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.0387F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.32F, -0.3035F, 0.0537F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-124.7461F, -78.4074F, -63.7383F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-147.0186F, -67.3427F, -31.691F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-148.4897F, -65.9688F, -28.9657F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-152.2383F, -61.868F, -21.444F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-3.426F, 2.7419F, 1.7015F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-2.5654F, 2.3747F, 1.8119F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-2.4593F, 2.3331F, 1.845F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-2.1453F, 2.2102F, 1.9653F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.3122F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-12.9799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-13.8142F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-16.3123F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.4812F, 0.0552F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0F, -0.4927F, 0.1126F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, -0.4946F, 0.1197F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -0.501F, 0.1407F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.9435F, 1.094F, -9.9406F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-0.1121F, -0.4594F, 0.071F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.9039F, 42.986F, 10.905F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(1.9039F, 42.986F, 10.905F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.9039F, 42.986F, 10.905F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.9039F, 42.986F, 10.905F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8175F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8175F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8175F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.159F, -0.0127F, 0.8175F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(13.7323F, 0.0001F, -0.4998F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0015F, 0.0097F, 0.0429F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-11.9895F, 17.3701F, -5.593F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(-11.9895F, 17.3701F, -5.593F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-11.9895F, 17.3701F, -5.593F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-11.9895F, 17.3701F, -5.593F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-0.1909F, -0.0084F, -0.8918F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(10.0746F, 0.0001F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0333F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, 0.0055F, 0.0317F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();
}