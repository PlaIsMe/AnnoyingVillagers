package com.pla.annoyinglib.client.animation.rig_animation.god;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class HerobrineGodAnimations {
	public static final AnimationDefinition GOD_IDLE = AnimationDefinition.Builder.withLength(2.65F).looping()
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(21.13F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.3F, KeyframeAnimations.degreeVec(24.602F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(21.13F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.429F, -1.797F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, -1.395F, -2.432F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.posVec(0.0F, -0.429F, -1.797F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.297F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.45F, -1.875F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, -1.422F, -2.525F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.posVec(0.0F, -0.45F, -1.875F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(24.491F, -2.697F, 16.094F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(26.072F, -2.997F, 15.403F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.65F, KeyframeAnimations.degreeVec(22.262F, -3.241F, 20.364F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(24.491F, -2.697F, 16.094F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.143F, -0.481F, -1.037F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.posVec(-0.116F, -0.776F, -1.149F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.posVec(-0.271F, -1.133F, -1.694F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.65F, KeyframeAnimations.posVec(-0.313F, -0.819F, -1.624F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.posVec(-0.143F, -0.481F, -1.037F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-31.382F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-30.142F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.65F, KeyframeAnimations.degreeVec(-39.953F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(-31.382F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.081F, 0.26F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.786F, 3.185F, -16.182F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.65F, KeyframeAnimations.degreeVec(31.61F, 6.736F, -19.961F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(25.786F, 3.185F, -16.182F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.145F, -0.478F, -1.022F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.posVec(0.117F, -0.778F, -1.148F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.posVec(0.275F, -1.106F, -1.616F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.65F, KeyframeAnimations.posVec(0.317F, -0.785F, -1.522F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.posVec(0.145F, -0.478F, -1.022F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.691F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-30.071F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.65F, KeyframeAnimations.degreeVec(-34.976F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(-30.691F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.078F, 0.255F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.007F, 1.44F, 6.656F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.degreeVec(-27.345F, 1.215F, 1.914F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(-41.007F, 1.44F, 6.656F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.02F, 0.023F, -0.123F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.posVec(-0.007F, -0.829F, -0.086F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.posVec(-0.02F, 0.023F, -0.123F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(54.155F, -0.979F, 0.007F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.degreeVec(71.63F, -0.969F, -0.142F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(54.155F, -0.979F, 0.007F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.003F, 0.088F, 0.143F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.22F, -1.064F, -6.522F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.degreeVec(-22.201F, -0.732F, -1.805F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(-5.22F, -1.064F, -6.522F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.021F, -0.011F, -0.018F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.35F, KeyframeAnimations.posVec(0.006F, -0.834F, -0.07F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.posVec(0.021F, -0.011F, -0.018F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(33.097F, -0.062F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.3F, KeyframeAnimations.degreeVec(44.655F, -0.061F, -0.005F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.65F, KeyframeAnimations.degreeVec(33.097F, -0.062F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.038F, 0.097F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition GOD_STRANGE_ABILITY = AnimationDefinition.Builder.withLength(1.7F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.916F, -20.104F, 5.097F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(5.995F, -20.766F, 0.22F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(15.373F, 2.698F, 3.275F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(31.757F, 31.85F, 14.318F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(27.949F, 17.696F, 7.351F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(13.356F, 11.391F, 1.098F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.828F, -1.528F, -0.324F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.46F, -1.488F, -3.705F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.177F, -2.31F, -3.84F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.286F, -2.749F, -4.886F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.206F, -3.504F, -6.026F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(-0.021F, -3.47F, -6.021F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(-0.015F, -2.272F, -4.66F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.916F, -20.104F, 5.097F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(1.059F, -6.955F, 4.413F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-12.722F, -7.156F, 2.371F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-2.333F, -5.576F, 8.629F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(14.395F, -4.384F, 9.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(11.865F, -5.03F, 4.853F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(12.271F, -0.086F, 2.976F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.828F, -1.528F, -0.324F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.463F, -1.491F, -3.726F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.187F, -2.311F, -3.91F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.26F, -2.759F, -4.954F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.269F, -3.519F, -6.067F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(-0.056F, -3.491F, -6.073F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(-0.035F, -2.273F, -4.662F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-28.532F, -21.448F, 16.693F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-88.24F, -10.759F, 12.434F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-142.904F, -10.806F, 10.467F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-99.907F, -16.255F, 7.55F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-45.827F, -5.262F, -15.586F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-64.612F, -3.82F, -10.577F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(-75.707F, -0.018F, -8.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.45F, KeyframeAnimations.degreeVec(-39.909F, 12.598F, -3.501F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.55F, KeyframeAnimations.degreeVec(-14.157F, 16.197F, 3.925F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(24.851F, 12.695F, 15.337F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.803F, -1.197F, -1.809F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.854F, -0.69F, -4.411F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.841F, 0.092F, -6.614F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.616F, -0.605F, -6.676F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.12F, -1.146F, -5.887F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.288F, -1.401F, -4.655F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(-0.285F, -1.975F, -6.327F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.55F, KeyframeAnimations.posVec(-0.186F, -2.023F, -4.468F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(-0.079F, -1.713F, -3.092F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-21.533F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-21.533F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-60.982F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-110.278F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(-71.071F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(-71.071F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.041F, 0.184F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, -0.041F, 0.184F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.0F, -0.692F, 0.452F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, -0.354F, 0.466F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(0.0F, -0.354F, 0.466F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.863F, -29.185F, -10.548F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(23.969F, -36.681F, -18.747F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(27.148F, -13.843F, -16.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(32.444F, 16.072F, -11.694F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(35.306F, 1.78F, -16.605F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(24.499F, -9.313F, -18.454F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.353F, -2.143F, 1.543F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.104F, -1.853F, -1.796F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(-0.027F, -2.561F, -2.05F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.311F, -3.178F, -4.531F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.643F, -4.402F, -7.457F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(0.005F, -3.908F, -6.261F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(0.139F, -2.437F, -4.799F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.757F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.132F, 0.325F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.054F, 5.1F, 17.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(8.763F, 5.1F, 17.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-0.393F, 5.1F, 17.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-28.81F, 10.273F, 27.159F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(-17.641F, 7.075F, 21.388F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.052F, -1.438F, 0.037F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.055F, -1.444F, -2.777F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(-0.059F, -2.279F, -2.805F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(-0.099F, -2.827F, -2.899F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(-0.098F, -2.805F, -2.898F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(-0.076F, -2.106F, -2.861F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.984F, -0.081F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(4.026F, -0.081F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(23.133F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(53.63F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(46.047F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.002F, 0.013F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(0.0F, 0.067F, 0.127F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.788F, -3.735F, -16.317F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-19.961F, -3.735F, -16.315F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-32.908F, -3.375F, -15.762F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-33.793F, 1.953F, -27.984F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(-21.953F, 3.411F, -25.799F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.053F, -1.419F, -0.113F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.056F, -1.44F, -2.863F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.052F, -2.256F, -2.9F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.posVec(0.083F, -2.83F, -2.893F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(0.083F, -2.808F, -2.892F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(0.082F, -2.108F, -2.856F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(43.318F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(39.471F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(54.271F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(54.041F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(35.184F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.011F, 0.064F, 0.116F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.posVec(-0.01F, 0.046F, 0.097F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition BEING_STRANGE = AnimationDefinition.Builder.withLength(1.15F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(32.509F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(6.905F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-1.925F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.063F, -4.313F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.059F, 0.068F, -2.45F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(2.118F, 0.908F, -0.484F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(2.514F, 1.911F, 2.335F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(2.835F, 2.687F, 3.81F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(2.826F, 4.216F, 3.495F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(2.738F, 5.804F, 2.156F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(2.68F, 6.614F, 1.198F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(32.509F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(3.144F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-13.009F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-19.049F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.063F, -4.313F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.059F, 0.065F, -2.457F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(2.118F, 0.906F, -0.499F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(2.514F, 1.909F, 2.305F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(2.835F, 2.686F, 3.769F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(2.826F, 4.219F, 3.443F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(2.738F, 5.81F, 2.094F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(2.68F, 6.621F, 1.132F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.872F, 2.92F, 15.476F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(18.66F, 7.63F, 27.207F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-15.194F, -17.572F, 36.215F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-57.647F, -26.565F, 56.789F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-74.621F, -20.554F, 51.677F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-79.865F, -21.156F, 39.613F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-80.001F, -23.401F, 32.263F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.1F, -1.027F, -3.318F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.702F, 0.577F, -2.096F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(1.594F, 2.076F, -0.508F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(1.841F, 2.885F, 2.578F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(1.965F, 3.408F, 4.041F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(2.142F, 4.791F, 3.572F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(2.269F, 6.333F, 2.216F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(2.309F, 7.15F, 1.285F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.695F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-38.695F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-97.714F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-100.048F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-88.661F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.12F, 0.311F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.12F, 0.311F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -0.586F, 0.481F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.0F, -0.507F, 0.488F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.123F, -1.208F, -21.33F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(23.48F, -9.196F, -27.367F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-17.907F, 8.443F, -29.234F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-65.089F, 19.8F, -44.156F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-73.307F, 20.01F, -47.075F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-85.26F, 18.858F, -50.111F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-92.293F, 17.239F, -51.054F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-93.545F, 16.622F, -50.74F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.161F, -1.117F, -3.291F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.381F, 0.228F, -1.987F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(2.566F, 1.428F, -0.434F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(2.99F, 2.318F, 2.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(3.383F, 2.915F, 3.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(3.346F, 4.274F, 3.535F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(3.206F, 5.716F, 2.127F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(3.119F, 6.457F, 1.139F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.757F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-40.757F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-93.05F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-85.996F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.132F, 0.325F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.132F, 0.325F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.563F, 0.485F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.0F, -0.483F, 0.488F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.028F, 0.736F, 7.939F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-5.884F, 0.736F, 7.939F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-8.231F, 0.737F, 7.983F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(3.388F, 0.735F, 7.979F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-5.172F, 0.736F, 7.976F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-49.56F, 0.736F, 7.964F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(-41.54F, 0.734F, 7.961F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(16.85F, 0.733F, 7.952F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-2.349F, 0.736F, 7.939F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.024F, 0.003F, -0.09F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(2.093F, 1.006F, 1.042F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(2.488F, 1.952F, 3.369F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(2.81F, 2.702F, 4.546F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(2.806F, 4.263F, 3.679F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(2.715F, 5.807F, 2.206F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(2.654F, 6.605F, 1.049F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(70.898F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(26.206F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(6.36F, -0.081F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(4.162F, -0.081F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(72.516F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(26.206F, -0.08F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.137F, 0.163F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.002F, 0.013F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, 0.142F, 0.164F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.0F, 0.026F, 0.079F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.162F, -0.429F, -6.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-9.979F, -0.429F, -6.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-17.726F, -0.432F, -6.103F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(24.043F, -0.431F, -6.092F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(11.124F, -0.431F, -6.09F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-55.468F, -0.431F, -6.085F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(1.178F, -0.429F, -6.079F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.019F, 0.0F, -0.078F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(2.138F, 1.009F, 1.03F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(2.533F, 1.959F, 3.34F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(2.854F, 2.704F, 4.54F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(2.843F, 4.236F, 3.892F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(2.752F, 5.862F, 2.002F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(2.699F, 6.609F, 1.06F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.256F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(17.585F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(11.921F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(17.017F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(63.041F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(66.866F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(65.441F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(17.585F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.01F, 0.036F, 0.085F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.008F, 0.01F, 0.032F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.posVec(-0.011F, 0.128F, 0.153F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(-0.009F, 0.016F, 0.049F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition HEROBRINE_GOD_INIT = AnimationDefinition.Builder.withLength(1.0F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(13.26F, -2.022F, -1.109F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(13.203F, 0.898F, -1.108F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(13.292F, -3.678F, -1.11F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(13.19F, 1.589F, -1.108F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(13.221F, -0.035F, -1.108F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(13.195F, 1.301F, -1.108F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(13.26F, -2.045F, -1.109F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(13.201F, 1.023F, -1.108F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.273F, -2.732F, -1.109F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.165F, -1.255F, -2.391F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.044F, -0.387F, -3.045F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.197F, 54.972F, -3.037F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.23F, 56.901F, -3.034F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.233F, 58.043F, -3.034F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.015F, 58.935F, -3.045F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.082F, 59.384F, -3.044F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.027F, 59.722F, -3.042F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.166F, 60.1F, -3.024F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(0.038F, 61.719F, -2.671F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.194F, 61.812F, -2.638F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.261F, -1.946F, -0.005F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.5F, 0.939F, 0.059F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(5.197F, -3.753F, 0.248F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(7.699F, 1.431F, 0.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(10.165F, -0.282F, 0.485F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.654F, 0.96F, 0.599F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(15.091F, -2.485F, 0.708F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(22.559F, 0.261F, 1.008F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(24.964F, -3.61F, 1.101F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.163F, -1.264F, -2.441F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.039F, -0.396F, -3.09F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.194F, 54.964F, -3.077F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.228F, 56.893F, -3.07F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.23F, 58.036F, -3.065F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.009F, 58.93F, -3.066F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.076F, 59.381F, -3.055F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.02F, 59.722F, -3.044F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.158F, 60.102F, -3.017F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(0.03F, 61.732F, -2.637F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.183F, 61.83F, -2.596F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.735F, 1.532F, 4.688F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(7.457F, 12.123F, 8.654F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(23.548F, -3.538F, 30.865F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(20.679F, -11.249F, 49.266F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(23.074F, -11.852F, 58.643F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(20.891F, -14.254F, 61.814F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(18.526F, -14.962F, 58.416F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(23.813F, -8.668F, 36.394F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(24.737F, -11.105F, 30.925F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.158F, -1.375F, -2.167F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.01F, -0.583F, -2.742F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.074F, 54.478F, -2.768F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.197F, 56.302F, -2.775F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.333F, 57.381F, -2.739F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.679F, 58.225F, -2.18F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.676F, 58.67F, -2.324F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.714F, 59.01F, -2.182F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-0.561F, 59.397F, -2.507F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-0.391F, 60.242F, -2.343F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-0.306F, 61.172F, -1.915F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(-0.106F, 61.337F, -2.228F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-11.891F, 0.117F, 1.277F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-13.169F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.011F, -0.036F, 0.101F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.017F, 0.114F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.437F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.34F, 0.165F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.89F, -0.748F, -6.001F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(2.953F, -9.794F, -8.227F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(21.952F, 10.762F, -62.224F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(16.783F, 15.663F, -74.169F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(14.791F, 16.502F, -76.071F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(18.594F, 14.258F, -69.474F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(18.573F, 11.97F, -59.398F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(16.676F, 5.328F, -26.841F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(17.759F, 3.19F, -26.761F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.217F, -1.173F, -1.729F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.146F, -0.354F, -2.908F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.998F, 54.466F, -2.063F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(1.125F, 56.388F, -1.944F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(1.194F, 57.532F, -1.917F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(1.093F, 58.427F, -2.473F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(1.163F, 58.879F, -2.296F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(1.119F, 59.216F, -2.437F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(1.15F, 59.589F, -2.077F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.904F, 60.39F, -2.099F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(0.374F, 61.584F, -2.044F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.444F, 61.584F, -1.818F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.853F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-16.471F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-16.345F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.016F, 0.112F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.025F, 0.141F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.233F, -0.492F, 6.654F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-40.894F, 2.408F, 6.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-41.425F, -2.137F, 6.658F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-40.814F, 3.095F, 6.663F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-41.002F, 1.482F, 6.656F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-40.847F, 2.809F, 6.662F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-41.235F, -0.515F, 6.654F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(-40.88F, 2.532F, 6.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-41.315F, -1.198F, 6.655F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.015F, -0.964F, -0.188F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.022F, -0.097F, -0.739F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.013F, 55.263F, -0.863F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.01F, 57.192F, -0.89F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.01F, 58.333F, -0.892F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.022F, 59.226F, -0.716F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-0.015F, 60.39F, -0.822F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-0.022F, 62.009F, -0.361F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(-0.013F, 62.102F, -0.461F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(54.155F, -0.979F, 0.007F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.003F, 0.088F, 0.143F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.998F, -2.997F, -6.53F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-5.33F, -0.096F, -6.521F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-4.809F, -4.642F, -6.543F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-5.409F, 0.591F, -6.522F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-5.224F, -1.022F, -6.522F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5.376F, 0.305F, -6.521F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-4.996F, -3.02F, -6.53F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(-5.345F, 0.029F, -6.521F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-4.917F, -3.703F, -6.535F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.02F, -0.998F, 0.047F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.021F, -0.131F, -0.7F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.019F, 55.228F, -0.576F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.018F, 57.157F, -0.549F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.017F, 58.299F, -0.546F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.02F, 59.191F, -0.723F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(0.02F, 60.594F, -0.569F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(0.021F, 61.975F, -0.33F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.019F, 62.068F, -0.178F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(33.097F, -0.062F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.038F, 0.097F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition HEROBRINE_GOD_SWING_HANDS = AnimationDefinition.Builder.withLength(2.65F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.243F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-9.719F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-0.238F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.394F, -2.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.019F, -2.165F, -1.466F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-0.081F, -1.521F, 1.131F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.posVec(-0.099F, -1.109F, 1.907F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6F, KeyframeAnimations.posVec(-0.103F, -0.294F, 1.729F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-0.103F, 1.108F, 0.869F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-0.103F, 1.689F, 0.082F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.684F, 0.574F, 0.171F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(8.782F, 0.592F, -0.091F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(8.856F, 0.599F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -2.393F, -2.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.019F, -2.162F, -1.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-0.081F, -1.514F, 1.19F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.posVec(-0.099F, -1.105F, 1.979F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6F, KeyframeAnimations.posVec(-0.102F, -0.289F, 1.798F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-0.102F, 1.113F, 0.92F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-0.103F, 1.694F, 0.117F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(26.402F, 19.529F, 30.642F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(13.669F, 22.036F, 28.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-48.067F, 29.371F, 20.28F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-65.518F, 33.472F, 20.129F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(-59.754F, 42.447F, 29.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-24.785F, 54.861F, 54.54F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.279F, -2.761F, -1.816F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.312F, -2.543F, -1.429F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.posVec(-0.5F, -1.151F, 0.833F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-0.903F, 0.927F, -0.177F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-1.046F, 1.431F, -0.822F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.538F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.052F, 0.208F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.054F, -19.0F, -30.479F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(8.881F, -21.485F, -28.873F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-44.089F, -30.289F, -22.357F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-59.406F, -34.48F, -21.846F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-34.854F, -45.257F, -44.286F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.291F, -2.768F, -1.835F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.287F, -2.55F, -1.443F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.posVec(0.327F, -1.192F, 0.834F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(0.574F, 0.883F, -0.114F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(0.649F, 1.392F, -0.762F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.843F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.145F, 0.338F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.71F, 10.818F, 19.09F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.degreeVec(-25.397F, 9.765F, 17.58F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.45F, KeyframeAnimations.degreeVec(-10.913F, 8.294F, 15.429F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.1F, KeyframeAnimations.degreeVec(-32.443F, 14.584F, 6.32F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-39.526F, 20.29F, -0.79F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.074F, -2.141F, -0.108F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-0.144F, -1.522F, -0.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-0.144F, 1.182F, -0.345F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-0.142F, 1.729F, -0.366F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(59.49F, 0.243F, -0.431F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(50.204F, 0.177F, -0.384F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(39.563F, 0.112F, -0.318F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.45F, KeyframeAnimations.degreeVec(19.323F, 0.025F, -0.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.55F, KeyframeAnimations.degreeVec(23.08F, 0.037F, -0.196F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.2F, KeyframeAnimations.degreeVec(74.788F, 0.366F, -0.482F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.degreeVec(84.056F, 0.445F, -0.497F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(88.505F, 0.484F, -0.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.001F, 0.104F, 0.149F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(0.001F, 0.194F, 0.167F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-43.313F, -6.046F, -9.852F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-37.353F, -3.157F, -9.9F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-28.235F, -1.551F, -9.856F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.4F, KeyframeAnimations.degreeVec(-16.56F, -0.919F, -9.561F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.85F, KeyframeAnimations.degreeVec(-10.796F, -3.061F, -5.393F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.1F, KeyframeAnimations.degreeVec(-17.727F, -5.149F, -2.577F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-18.301F, -7.868F, 1.029F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.038F, -2.114F, -0.136F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-0.045F, -1.498F, -0.286F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-0.092F, 1.167F, -0.285F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-0.1F, 1.703F, -0.291F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(62.325F, -0.312F, 0.522F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(52.118F, -0.224F, 0.466F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(35.03F, -0.103F, 0.339F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.degreeVec(12.591F, -0.01F, 0.129F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.4F, KeyframeAnimations.degreeVec(0.734F, 0.0F, 0.008F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.04F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(12.589F, -0.01F, 0.129F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.92F, -0.046F, 0.239F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(33.724F, -0.095F, 0.328F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.002F, 0.112F, 0.153F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-0.001F, 0.04F, 0.099F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition HEROBRINE_GOD_DROP_BLACK_HOLE = AnimationDefinition.Builder.withLength(2.0F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.243F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(11.03F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(14.773F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(26.631F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(30.54F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.degreeVec(29.468F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(17.182F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.422F, -2.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, -1.426F, -1.43F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -2.033F, -1.421F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.946F, -2.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.posVec(0.0F, -3.252F, -2.426F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -3.344F, -2.487F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, -3.138F, -2.387F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -1.064F, -1.172F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.684F, 0.574F, 0.171F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(9.498F, 0.587F, 0.119F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(14.564F, 0.578F, 0.157F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(28.487F, 0.534F, 0.272F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(32.548F, 0.556F, 0.497F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.degreeVec(30.304F, 0.67F, 0.897F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(16.117F, 1.171F, 1.339F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.421F, -2.079F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, -1.427F, -1.436F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -2.034F, -1.422F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -2.942F, -2.251F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.posVec(0.0F, -3.247F, -2.418F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, -3.335F, -2.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.posVec(-0.001F, -3.136F, -2.384F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(-0.003F, -1.065F, -1.176F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(26.402F, 19.529F, 30.642F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(14.77F, 15.67F, 28.608F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-9.212F, 5.534F, 27.986F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-42.654F, -6.961F, 34.623F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-52.611F, -8.95F, 37.807F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-29.642F, -10.089F, 34.707F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(51.014F, -6.84F, 30.774F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(68.897F, -6.428F, 31.82F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(74.76F, -5.643F, 32.374F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(72.659F, -6.579F, 32.059F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.degreeVec(65.637F, -10.218F, 31.243F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(43.786F, -19.63F, 32.458F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6F, KeyframeAnimations.degreeVec(20.571F, -28.548F, 38.627F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(32.669F, 13.335F, 33.865F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.279F, -1.789F, -1.816F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(-0.31F, -1.879F, -1.187F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-0.323F, -2.476F, -0.958F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-0.261F, -3.119F, -1.15F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-0.225F, -3.389F, -1.239F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.posVec(-0.257F, -3.206F, -1.113F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6F, KeyframeAnimations.posVec(-0.528F, -2.589F, -0.652F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(-0.294F, -1.461F, -0.746F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.538F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-38.017F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-28.445F, 0.864F, -2.11F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-25.819F, 1.159F, -2.653F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.052F, 0.208F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(-0.017F, -0.011F, 0.197F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.054F, -19.0F, -30.479F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(9.995F, -15.246F, -26.25F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-23.638F, 5.045F, -18.878F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-39.712F, 15.324F, -20.199F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-43.653F, 17.146F, -21.043F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-24.5F, 17.088F, -20.008F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(38.487F, 17.118F, -20.087F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(53.253F, 17.649F, -20.263F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(58.852F, 15.221F, -18.392F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(56.818F, 16.257F, -17.879F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.4F, KeyframeAnimations.degreeVec(44.165F, 23.398F, -16.491F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(26.374F, 32.827F, -18.237F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6F, KeyframeAnimations.degreeVec(-2.609F, 44.489F, -28.33F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(9.865F, 9.78F, -23.487F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.291F, -1.796F, -1.835F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(0.136F, -1.674F, -1.01F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.166F, -2.282F, -0.812F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.233F, -2.993F, -1.025F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(0.216F, -3.248F, -1.102F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.posVec(0.237F, -3.046F, -0.986F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.6F, KeyframeAnimations.posVec(0.514F, -2.462F, -0.532F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.222F, -1.35F, -0.449F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-42.843F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-48.991F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-43.228F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-25.038F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(-20.727F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-19.118F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.145F, 0.338F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.054F, 0.211F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, -0.033F, 0.164F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-19.009F, 12.543F, 17.419F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-19.009F, 12.543F, 17.419F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-25.68F, 9.582F, 20.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(-26.733F, 9.112F, 20.611F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(-22.919F, 9.966F, 19.81F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-6.807F, 12.026F, 17.8F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.068F, -1.18F, -0.08F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(-0.07F, -1.314F, 0.021F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-0.075F, -2.293F, 0.771F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-0.076F, -2.486F, 0.907F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.posVec(-0.075F, -2.344F, 0.914F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(-0.063F, -0.801F, 0.964F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(38.076F, 0.104F, -0.308F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(38.076F, 0.104F, -0.308F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(41.67F, 0.124F, -0.332F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(61.425F, 0.258F, -0.439F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(64.588F, 0.283F, -0.451F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(56.7F, 0.223F, -0.418F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(23.283F, 0.038F, -0.198F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.049F, 0.109F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.021F, 0.071F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-32.114F, -7.103F, -8.706F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-34.049F, -7.619F, -8.993F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-46.338F, -11.447F, -10.912F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-48.618F, -12.163F, -11.226F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.45F, KeyframeAnimations.degreeVec(-44.549F, -12.408F, -10.961F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-23.807F, -13.042F, -10.124F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.036F, -1.159F, -0.109F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(0.038F, -1.291F, -0.01F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.052F, -2.252F, 0.714F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(0.055F, -2.441F, 0.847F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.35F, KeyframeAnimations.posVec(0.055F, -2.302F, 0.855F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.047F, -0.783F, 0.912F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(41.94F, -0.147F, 0.394F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(44.486F, -0.165F, 0.413F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(58.655F, -0.279F, 0.504F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.2F, KeyframeAnimations.degreeVec(60.919F, -0.299F, 0.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.5F, KeyframeAnimations.degreeVec(53.988F, -0.239F, 0.477F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(24.061F, -0.047F, 0.241F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.001F, 0.058F, 0.118F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 0.022F, 0.073F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}