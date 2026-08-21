package com.pla.annoyingvillagers.client.animation.rig_animation.greatsword;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class GreatswordAnimations1 {
	public static final AnimationDefinition CARRY = AnimationDefinition.Builder.withLength(2.65F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(2.398F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, -0.005F, -0.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(2.398F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, -0.005F, -0.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.453F, -7.072F, 1.138F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(-33.037F, -7.114F, 0.84F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-35.453F, -7.072F, 1.138F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.007F, 0.026F, -0.051F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(0.007F, 0.021F, -0.226F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(0.007F, 0.026F, -0.051F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.695F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.12F, 0.311F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.584F, 4.34F, 3.11F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.degreeVec(-32.183F, 4.207F, 3.289F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.degreeVec(-34.584F, 4.34F, 3.11F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.021F, 0.099F, -0.095F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.3F, KeyframeAnimations.posVec(-0.021F, 0.092F, -0.274F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.65F, KeyframeAnimations.posVec(-0.021F, 0.099F, -0.095F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.757F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.132F, 0.325F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-3.754F, 0.586F, 6.308F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.02F, -0.011F, -0.013F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.984F, -0.081F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.002F, 0.013F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.029F, -0.359F, -5.083F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.016F, -0.009F, -0.013F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.237F, -2.948F, 1.78F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.007F, 0.005F, 0.008F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition GREATSWORD_IDLE = AnimationDefinition.Builder.withLength(3.0F).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.437F, 27.613F, 4.653F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(5.598F, 28.362F, 4.295F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(5.437F, 27.613F, 4.653F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.443F, -0.477F, -0.991F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.377F, -0.516F, -0.975F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.356F, -0.786F, -0.955F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.posVec(0.381F, -0.835F, -0.965F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.posVec(0.443F, -0.477F, -0.991F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.473F, 1.56F, 5.602F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(7.627F, 2.307F, 5.174F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(7.473F, 1.56F, 5.602F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.424F, -0.474F, -0.979F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.358F, -0.513F, -0.963F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.337F, -0.783F, -0.944F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.posVec(0.362F, -0.832F, -0.953F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.posVec(0.424F, -0.474F, -0.979F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.916F, 19.706F, 41.033F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.281F, -0.51F, 0.88F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.215F, -0.58F, 0.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.202F, -0.868F, 0.881F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.posVec(0.261F, -0.905F, 0.975F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.posVec(0.281F, -0.51F, 0.88F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-104.517F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-105.373F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.8F, KeyframeAnimations.degreeVec(-102.587F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(-104.517F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.644F, 0.468F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.482F, 24.157F, 19.869F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.25F, KeyframeAnimations.degreeVec(-7.769F, 23.205F, 22.979F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(-9.482F, 24.157F, 19.869F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.302F, -1.567F, -0.205F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-140.835F, 35.639F, -87.377F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-138.955F, 35.926F, -87.876F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(-140.835F, 35.639F, -87.377F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.739F, -1.709F, -4.68F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.81F, -1.714F, -4.7F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.posVec(-0.932F, -2.032F, -4.816F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.posVec(-0.739F, -1.709F, -4.68F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-52.527F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(-52.839F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(-55.477F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(-52.527F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.209F, 0.393F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(163.843F, 23.212F, -39.343F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.579F, 41.959F, 24.376F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(11.989F, 41.952F, 24.516F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(7.053F, 41.878F, 25.165F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(5.573F, 41.856F, 24.961F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.25F, KeyframeAnimations.degreeVec(6.741F, 42.04F, 24.086F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(12.579F, 41.959F, 24.376F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.162F, -0.307F, 0.889F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.141F, -0.363F, 0.856F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(0.148F, -0.64F, 0.859F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.posVec(0.199F, -0.679F, 0.942F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.posVec(0.162F, -0.307F, 0.889F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.987F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(22.287F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(32.895F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.degreeVec(35.292F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(32.486F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(20.987F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.018F, 0.064F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.672F, 1.229F, -9.427F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-30.339F, 1.22F, -9.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.degreeVec(-35.387F, 1.185F, -9.603F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.7F, KeyframeAnimations.degreeVec(-36.258F, 1.171F, -9.549F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(-29.672F, 1.229F, -9.427F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.209F, -0.571F, -1.011F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.215F, -0.602F, -1.024F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.15F, KeyframeAnimations.posVec(-0.197F, -0.86F, -1.027F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.65F, KeyframeAnimations.posVec(-0.179F, -0.909F, -1.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.posVec(-0.209F, -0.571F, -1.011F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(24.326F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(25.402F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(34.263F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.55F, KeyframeAnimations.degreeVec(36.192F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.05F, KeyframeAnimations.degreeVec(34.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(3.0F, KeyframeAnimations.degreeVec(24.326F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.022F, 0.074F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition GREATSWORD_RUN = AnimationDefinition.Builder.withLength(0.45F).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(41.157F, 28.292F, 30.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(41.207F, 25.322F, 29.698F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(45.865F, 22.15F, 31.073F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(48.462F, 30.748F, 36.129F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(48.303F, 40.987F, 40.735F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(44.718F, 31.74F, 32.381F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(43.925F, 25.579F, 28.281F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(41.157F, 28.294F, 30.457F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.882F, -3.041F, -4.264F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(1.469F, -4.177F, -4.673F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.239F, -2.617F, -4.862F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.976F, -1.154F, -4.09F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.672F, -1.693F, -4.019F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.282F, -3.751F, -4.256F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.108F, -3.118F, -4.476F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.091F, -1.471F, -4.162F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.347F, -1.01F, -3.854F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.882F, -3.041F, -4.264F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(18.666F, 0.102F, 11.466F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(19.819F, -1.619F, 11.07F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(25.185F, -3.341F, 10.586F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(22.069F, -5.391F, 9.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(20.07F, -1.631F, 11.703F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(22.387F, -0.017F, 9.517F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(18.666F, 0.104F, 11.467F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.857F, -3.069F, -4.317F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(1.446F, -4.207F, -4.727F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.217F, -2.651F, -4.912F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.941F, -1.183F, -4.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.631F, -1.716F, -4.064F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.24F, -3.771F, -4.299F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.076F, -3.145F, -4.524F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.065F, -1.503F, -4.212F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.322F, -1.041F, -3.906F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.856F, -3.069F, -4.317F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.438F, 26.583F, 64.029F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-27.596F, 30.669F, 66.761F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-30.287F, 26.993F, 52.949F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-32.748F, 26.443F, 52.866F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-22.779F, 31.811F, 68.644F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-30.535F, 27.01F, 56.609F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-27.44F, 26.582F, 64.027F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.734F, -1.842F, -1.535F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(1.202F, -2.889F, -2.107F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.171F, -1.068F, -2.317F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(1.415F, 0.541F, -0.828F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(1.318F, -0.098F, -0.416F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(1.044F, -2.22F, -0.423F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.13F, -1.855F, -1.413F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.131F, -0.374F, -1.609F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.272F, 0.175F, -1.207F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.733F, -1.842F, -1.535F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-102.413F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-97.987F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-98.68F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-83.785F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-84.442F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-93.217F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-102.41F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.626F, 0.473F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -0.464F, 0.487F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, -0.626F, 0.473F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.388F, 34.9F, 29.867F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-5.11F, 38.445F, 27.795F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-16.204F, 28.994F, 16.331F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-27.967F, 26.928F, 14.776F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-21.523F, 36.901F, 20.781F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-10.535F, 38.664F, 26.387F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-13.311F, 30.965F, 21.27F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-5.389F, 34.898F, 29.866F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.069F, -1.552F, -0.245F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.081F, -1.529F, -0.269F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.261F, -1.428F, -0.362F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.295F, -1.24F, -0.507F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.087F, -1.45F, -0.351F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.14F, -1.472F, -0.335F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.069F, -1.552F, -0.245F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-131.474F, 26.591F, -82.976F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-142.986F, 25.413F, -82.017F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-143.441F, 37.518F, -85.859F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-146.653F, 41.626F, -89.411F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-137.122F, 37.992F, -88.094F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-132.862F, 25.842F, -84.545F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-134.198F, 23.317F, -83.672F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-130.553F, 24.92F, -84.098F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-131.469F, 26.591F, -82.976F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-5.382F, -4.22F, -9.915F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-4.435F, -5.105F, -10.455F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-4.284F, -3.971F, -10.382F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-5.2F, -2.189F, -9.579F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-5.78F, -2.335F, -9.497F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-6.926F, -4.274F, -9.653F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-6.968F, -4.154F, -10.001F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-6.554F, -2.935F, -9.676F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-6.101F, -2.377F, -9.432F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-5.382F, -4.22F, -9.914F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-77.716F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-65.705F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-61.812F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-82.93F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-82.64F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-72.1F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-77.718F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-77.725F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.411F, 0.48F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.279F, 0.436F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, -0.454F, 0.486F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, -0.411F, 0.48F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(34.064F, 0.701F, -4.897F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-1.529F, 0.926F, -3.391F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-50.254F, 1.046F, -1.416F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-65.386F, 1.374F, 0.172F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-55.097F, 0.646F, -1.012F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-8.575F, 0.369F, -1.994F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(22.051F, 0.417F, -2.402F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(42.42F, 0.344F, -3.567F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(34.064F, 0.701F, -4.897F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.232F, 0.071F, 3.331F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.163F, -0.682F, 3.169F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.515F, 1.204F, 3.25F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.655F, 2.158F, 3.521F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.488F, 1.468F, 3.614F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.127F, -0.545F, 3.625F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.197F, 0.216F, 3.452F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.375F, 1.56F, 3.393F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.409F, 1.823F, 3.357F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.232F, 0.071F, 3.331F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(39.895F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(86.537F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(99.908F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(64.155F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(12.533F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.269F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.269F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(39.896F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.052F, 0.114F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, 0.229F, 0.162F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.008F, 0.039F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, 0.052F, 0.114F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-43.584F, -1.261F, -0.014F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-49.813F, -0.975F, 1.575F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-10.828F, -0.942F, 1.488F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(41.697F, -0.961F, 0.17F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(57.252F, -1.368F, -0.909F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(26.883F, -0.971F, -2.957F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-19.648F, -1.369F, -3.485F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-73.644F, -1.296F, -2.57F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-83.53F, -1.301F, -1.642F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-43.584F, -1.261F, -0.014F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.567F, -1.239F, 2.21F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.189F, -2.05F, 2.261F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.089F, -0.577F, 2.156F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.287F, 0.285F, 1.996F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.54F, -0.323F, 1.947F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.753F, -2.216F, 1.932F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-0.805F, -1.411F, 2.038F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.78F, 0.261F, 2.067F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.737F, 0.605F, 2.141F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.567F, -1.239F, 2.21F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.269F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(51.97F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(44.042F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(18.934F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(3.103F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(65.62F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(111.131F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(113.014F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(75.642F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(0.269F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, 0.082F, 0.138F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.001F, 0.01F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, 0.268F, 0.147F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition GREATSWORD_WALK = AnimationDefinition.Builder.withLength(0.7F).looping()
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(8.865F, 38.506F, 5.754F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(10.184F, 48.069F, 7.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.695F, 51.069F, 7.667F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(9.351F, 42.881F, 4.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(8.865F, 38.506F, 5.754F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.111F, -0.866F, -1.423F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.025F, -0.901F, -1.432F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -0.464F, -1.444F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.006F, 0.209F, -1.457F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.043F, 0.276F, -1.447F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.316F, -0.862F, -1.379F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.452F, -0.897F, -1.388F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.552F, -0.463F, -1.412F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.632F, 0.207F, -1.444F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.593F, 0.273F, -1.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-0.111F, -0.866F, -1.423F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(8.026F, 0.987F, 1.847F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(8.243F, 8.525F, 2.596F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(8.216F, 9.513F, 1.399F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(8.096F, 3.824F, 0.498F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(8.026F, 0.987F, 1.847F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.126F, -0.865F, -1.416F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.04F, -0.899F, -1.424F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.015F, -0.462F, -1.436F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.02F, 0.211F, -1.448F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.056F, 0.278F, -1.436F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.329F, -0.86F, -1.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.466F, -0.895F, -1.377F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.566F, -0.461F, -1.401F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.647F, 0.208F, -1.434F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.608F, 0.275F, -1.448F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-0.126F, -0.865F, -1.416F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-40.997F, 19.737F, 44.541F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-42.925F, 21.627F, 44.444F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-44.272F, 23.051F, 37.888F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-44.174F, 22.322F, 40.103F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-43.89F, 23.012F, 47.4F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-39.403F, 20.052F, 41.752F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-40.996F, 19.737F, 44.541F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.262F, -0.863F, 1.469F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.414F, -0.846F, 1.544F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.627F, -0.355F, 1.739F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.88F, 0.378F, 1.984F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(1.003F, 0.443F, 2.185F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.871F, -0.803F, 2.485F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.653F, -0.885F, 2.398F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.434F, -0.488F, 2.227F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.221F, 0.145F, 2.012F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.113F, 0.202F, 1.815F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.262F, -0.863F, 1.469F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-77.352F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-75.03F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-67.677F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-65.196F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-75.696F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-79.414F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-77.352F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.408F, 0.479F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, -0.306F, 0.448F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -0.416F, 0.481F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -0.408F, 0.479F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-26.493F, 27.193F, 18.698F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-26.008F, 27.622F, 17.672F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-32.166F, 24.134F, 12.209F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-36.295F, 26.018F, 10.873F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-31.021F, 30.14F, 13.934F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-28.1F, 25.085F, 15.873F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-26.493F, 27.193F, 18.698F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.051F, -0.57F, -0.767F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.109F, -0.458F, -0.811F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.078F, -0.365F, -0.872F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.051F, -0.455F, -0.843F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.038F, -0.545F, -0.77F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-0.051F, -0.57F, -0.767F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-161.487F, 35.924F, -88.535F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-164.759F, 34.942F, -87.699F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-161.316F, 44.639F, -94.826F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-154.333F, 37.861F, -90.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-154.5F, 42.952F, -93.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-161.486F, 35.924F, -88.535F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-1.059F, -2.161F, -4.663F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-1.003F, -2.194F, -4.685F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-1.039F, -1.737F, -4.753F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-1.13F, -1.03F, -4.85F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-1.22F, -0.944F, -4.913F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-1.463F, -2.106F, -4.879F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-1.52F, -2.135F, -4.84F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-1.47F, -1.666F, -4.712F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-1.416F, -0.946F, -4.592F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-1.332F, -0.88F, -4.541F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-1.059F, -2.161F, -4.663F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-33.087F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-26.964F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-39.449F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-48.964F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-43.142F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-33.088F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.09F, 0.272F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, -0.184F, 0.374F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -0.09F, 0.272F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(15.555F, 12.216F, 5.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(18.135F, 12.505F, 5.866F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-23.307F, 12.98F, 1.544F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30.367F, 13.389F, -1.209F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-37.518F, 13.747F, -3.566F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-30.414F, 13.326F, -2.472F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-9.671F, 13.153F, -1.109F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(6.148F, 12.867F, 0.767F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(15.909F, 12.986F, 2.494F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(20.401F, 12.993F, 4.133F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(15.555F, 12.216F, 5.081F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.062F, -0.661F, 0.953F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(0.128F, -0.683F, 0.989F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.192F, -0.247F, 1.011F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(0.261F, 0.431F, 1.043F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.305F, 0.512F, 1.068F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.273F, -0.617F, 1.161F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.194F, -0.665F, 1.121F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.109F, -0.256F, 1.065F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.03F, 0.382F, 1.029F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.012F, 0.446F, 1.007F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.062F, -0.661F, 0.953F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(18.794F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(17.384F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(37.334F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(47.13F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(52.861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(33.063F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(43.571F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(39.281F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.268F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(3.206F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(18.794F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.015F, 0.058F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.085F, 0.14F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, 0.051F, 0.112F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, 0.015F, 0.058F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-34.399F, -2.966F, -2.012F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-37.868F, -2.854F, -2.128F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-32.254F, -2.789F, -2.367F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-14.777F, -2.949F, -2.465F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-9.933F, -2.541F, -2.642F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-8.117F, -3.603F, -3.569F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-2.797F, -2.916F, -4.081F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-3.102F, -3.34F, -4.705F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-18.122F, -3.258F, -3.797F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-30.862F, -3.141F, -2.771F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-36.348F, -2.48F, -1.962F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-28.107F, -2.352F, -1.72F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-34.399F, -2.966F, -2.012F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.382F, -0.881F, -1.026F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.363F, -0.921F, -1.089F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-0.386F, -0.503F, -1.171F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-0.436F, 0.144F, -1.236F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-0.488F, 0.206F, -1.286F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-0.649F, -0.921F, -1.296F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.68F, -0.943F, -1.284F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.675F, -0.491F, -1.265F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-0.655F, 0.202F, -1.238F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.609F, 0.287F, -1.2F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-0.382F, -0.881F, -1.026F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(19.017F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(38.77F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(40.651F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(19.597F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(17.926F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(38.753F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(41.081F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(54.386F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(55.246F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(3.835F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(19.017F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.015F, 0.059F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, 0.054F, 0.115F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 0.013F, 0.056F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.091F, 0.144F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, 0.015F, 0.059F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition GREATSWORD_EXTRA_ATTACK = AnimationDefinition.Builder.withLength(2.5F)
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(5.437F, 27.613F, -355.347F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(74.307F, 53.852F, -326.076F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(120.217F, 62.385F, -302.601F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(178.79F, 65.693F, -265.515F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(220.742F, 56.84F, -233.515F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(206.135F, 5.114F, -216.809F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(181.639F, -51.877F, -210.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(155.947F, -72.13F, -196.469F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(126.987F, -80.714F, -174.735F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(40.505F, -83.147F, -94.98F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.degreeVec(-1.634F, -71.839F, -57.161F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.degreeVec(-12.844F, -58.966F, -51.181F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(-11.758F, -41.06F, -53.521F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(16.001F, 36.32F, -56.149F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(21.699F, 49.403F, -52.16F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(21.761F, 49.328F, -52.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(23.908F, 46.229F, -46.032F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(26.488F, 38.208F, -32.66F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.25F, KeyframeAnimations.degreeVec(25.243F, 33.054F, -18.983F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(2.319F, 26.191F, 1.214F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.443F, -0.477F, -6.328F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.941F, -2.239F, -8.527F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-3.676F, -4.815F, -9.121F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-6.526F, -7.768F, -7.563F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-8.254F, -10.662F, -4.252F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-7.803F, -11.933F, -1.737F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-5.479F, -10.125F, -2.909F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-2.841F, -8.465F, -4.614F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.824F, -7.053F, -6.86F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.74F, -5.981F, -9.584F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-1.61F, -4.982F, -15.496F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(-2.402F, -3.606F, -21.387F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-3.006F, -3.201F, -23.806F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-3.703F, -3.073F, -25.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.posVec(-5.218F, -4.02F, -24.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-4.866F, -4.057F, -24.477F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-2.997F, -4.731F, -29.81F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(-1.929F, -7.309F, -34.012F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(-1.354F, -9.677F, -35.624F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(-1.354F, -10.906F, -35.624F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(-1.343F, -9.351F, -35.633F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.posVec(-0.865F, -8.32F, -35.974F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(0.567F, -6.227F, -36.55F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.3F, KeyframeAnimations.posVec(1.391F, -3.253F, -35.829F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(1.874F, -1.701F, -34.616F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(2.082F, -0.6F, -32.939F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-352.527F, 1.56F, 5.602F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-305.132F, 42.293F, 19.144F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-269.543F, 57.424F, 38.472F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-217.772F, 64.599F, 74.368F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-174.133F, 56.408F, 108.731F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-160.214F, 11.775F, 125.651F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-149.909F, -38.561F, 127.594F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-137.135F, -64.162F, 118.542F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-72.032F, -81.197F, 55.562F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-1.525F, -75.127F, -14.941F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(14.848F, -62.332F, -32.659F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(14.693F, 8.709F, -50.936F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(43.504F, 44.323F, -34.604F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(103.179F, 65.399F, 15.327F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(134.19F, 62.618F, 43.425F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(133.818F, 62.662F, 43.215F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(117.806F, 63.781F, 33.723F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(77.144F, 59.732F, 9.186F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.3F, KeyframeAnimations.degreeVec(37.641F, 36.88F, -6.022F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(4.653F, 0.218F, 3.531F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.424F, -0.474F, -6.316F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.969F, -2.241F, -8.523F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-3.714F, -4.83F, -9.109F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-6.562F, -7.801F, -7.529F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-8.268F, -10.712F, -4.194F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-7.79F, -11.985F, -1.672F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-5.462F, -10.148F, -2.854F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-2.82F, -8.466F, -4.579F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-0.807F, -7.044F, -6.851F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-0.739F, -5.972F, -9.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-1.66F, -4.998F, -15.514F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.posVec(-2.471F, -3.638F, -21.377F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-3.079F, -3.241F, -23.776F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-3.771F, -3.116F, -25.318F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.posVec(-5.253F, -4.051F, -24.174F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-4.885F, -4.067F, -24.383F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-3.003F, -4.673F, -29.733F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(-1.924F, -7.217F, -33.984F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(-1.348F, -9.582F, -35.618F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(-1.348F, -10.81F, -35.618F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(-1.337F, -9.256F, -35.627F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.posVec(-0.853F, -8.234F, -35.962F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(0.586F, -6.167F, -36.527F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.3F, KeyframeAnimations.posVec(1.401F, -3.227F, -35.796F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(1.87F, -1.692F, -34.589F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(2.063F, -0.598F, -32.927F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.916F, 19.706F, 41.033F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(9.382F, 33.435F, 52.052F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(52.43F, 39.934F, 73.492F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(96.277F, 33.783F, 96.107F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(132.068F, 18.097F, 108.189F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(151.792F, 4.901F, 111.523F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(134.106F, -5.002F, 113.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(124.053F, -26.085F, 91.246F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(144.43F, -31.202F, 40.327F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(153.384F, -27.105F, 16.607F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(153.722F, -13.323F, -0.702F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.degreeVec(167.793F, -2.176F, 9.738F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(191.449F, -2.194F, 13.783F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(338.821F, -0.114F, 4.889F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(368.452F, -1.132F, 3.631F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(368.788F, -1.017F, 3.85F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(369.911F, 8.181F, 12.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(383.53F, 39.573F, 39.197F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(330.807F, 20.219F, 37.887F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.281F, -0.51F, -4.457F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-0.088F, -1.544F, -4.675F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-1.188F, -3.404F, -3.92F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-2.012F, -5.739F, -1.736F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-1.524F, -8.172F, 1.398F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.775F, -9.269F, 3.032F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(4.016F, -7.58F, 1.041F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(7.362F, -6.042F, -1.506F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(9.949F, -4.675F, -4.638F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(10.512F, -3.49F, -8.409F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(10.478F, -2.597F, -12.588F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(9.395F, -0.614F, -20.467F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(7.329F, 1.397F, -27.073F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(5.807F, 1.862F, -29.451F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8F, KeyframeAnimations.posVec(3.809F, 0.936F, -30.136F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.posVec(1.984F, -0.047F, -30.057F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(1.06F, -2.118F, -30.787F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(1.498F, -8.492F, -32.338F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(2.87F, -10.594F, -30.453F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(4.039F, -11.767F, -30.597F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(4.041F, -11.951F, -29.971F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(4.038F, -11.65F, -30.772F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.posVec(4.102F, -9.572F, -30.551F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(4.207F, -7.876F, -31.912F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.35F, KeyframeAnimations.posVec(2.684F, -3.612F, -32.248F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(1.95F, -0.898F, -31.311F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-104.517F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-20.798F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-8.253F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-48.225F, -0.015F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-56.322F, -0.302F, -0.03F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(-28.034F, -1.883F, -1.175F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-32.951F, -1.624F, -0.519F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(-26.777F, -1.91F, -1.431F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(-20.893F, 2.833F, -14.067F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-104.517F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.644F, 0.468F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -0.286F, 0.439F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.007F, 0.072F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.142F, 0.336F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-0.002F, -0.236F, 0.417F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(-0.018F, -0.046F, 0.267F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(-0.02F, -0.036F, 0.258F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-0.135F, 0.193F, 0.129F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.35F, KeyframeAnimations.posVec(-0.038F, -0.224F, 0.484F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -0.644F, 0.468F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.482F, 24.157F, 19.869F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(13.231F, 7.907F, 19.644F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(44.92F, -11.144F, 9.096F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(52.615F, -4.703F, 1.118F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(20.627F, -2.057F, 2.616F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(15.658F, -1.024F, 2.215F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(20.575F, -1.288F, 1.819F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(14.46F, -0.935F, 2.24F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(14.727F, 2.01F, -7.286F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-9.482F, 24.157F, 19.869F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.302F, -1.567F, -0.205F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(0.1F, -1.18F, 0.33F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-0.306F, -0.09F, 1.134F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(-0.077F, 0.093F, 1.179F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-0.058F, -0.108F, 0.471F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(-0.04F, -0.094F, 0.456F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(-0.047F, -0.091F, 0.317F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(0.15F, 0.072F, 0.274F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.35F, KeyframeAnimations.posVec(0.277F, -0.973F, -0.032F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.302F, -1.567F, -0.205F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-140.835F, 35.639F, 272.623F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-189.874F, 73.783F, 217.877F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-259.618F, 46.758F, 126.428F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-244.442F, 14.746F, 98.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-213.281F, 6.297F, 66.085F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-191.517F, 15.437F, 50.684F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-182.873F, -9.863F, 72.983F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-178.408F, -26.579F, 73.198F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-167.89F, -42.448F, 62.019F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-146.955F, -50.337F, 35.563F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-128.737F, -47.615F, 8.874F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-122.066F, -43.87F, -16.669F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-131.506F, -33.597F, -30.249F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.degreeVec(-127.218F, -16.051F, -21.279F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(-108.894F, -3.282F, -17.751F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-36.56F, 20.628F, -7.759F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(32.994F, 35.679F, 13.141F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(57.685F, 39.863F, 22.993F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(68.492F, 35.624F, 29.625F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(54.98F, 40.974F, 22.135F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(66.792F, 49.345F, 64.469F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.1F, KeyframeAnimations.degreeVec(85.742F, 72.69F, 74.005F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.2F, KeyframeAnimations.degreeVec(113.835F, 86.62F, 111.36F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.25F, KeyframeAnimations.degreeVec(60.053F, 95.434F, 68.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.3F, KeyframeAnimations.degreeVec(63.313F, 104.407F, 81.627F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(40.933F, 147.121F, 91.591F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.739F, -1.709F, -10.017F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.posVec(-3.806F, -4.968F, -11.463F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-7.183F, -8.067F, -10.613F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.posVec(-9.763F, -10.843F, -7.974F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-10.855F, -13.297F, -4.374F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-10.3F, -14.49F, -1.879F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.posVec(-8.79F, -12.979F, -2.022F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(-6.528F, -11.057F, -2.477F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(-4.338F, -8.845F, -3.547F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(-3.524F, -6.481F, -5.51F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(-2.808F, -4.34F, -8.467F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(-2.93F, -0.77F, -15.322F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(-4.463F, 1.953F, -21.296F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(-5.441F, 2.667F, -23.271F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.posVec(-7.01F, 2.261F, -23.167F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(-6.66F, 2.388F, -24.93F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(-5.629F, -1.354F, -34.626F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(-6.305F, -8.854F, -38.311F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(-6.191F, -13.095F, -37.6F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(-6.191F, -13.676F, -38.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(-6.178F, -12.89F, -37.397F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.posVec(-5.506F, -11.295F, -38.225F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(-4.045F, -9.268F, -39.329F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.25F, KeyframeAnimations.posVec(-2.553F, -6.842F, -39.557F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.4F, KeyframeAnimations.posVec(-0.202F, -3.462F, -38.44F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(1.111F, -1.518F, -36.667F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-52.527F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-37.668F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-56.666F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-62.733F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(-62.611F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(-57.625F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(-45.067F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-52.527F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.209F, 0.393F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-347.421F, 41.959F, 24.376F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.05F, KeyframeAnimations.degreeVec(-337.267F, 62.421F, 39.305F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-291.656F, 72.705F, 92.924F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.15F, KeyframeAnimations.degreeVec(-254.152F, 61.087F, 140.786F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-253.771F, 24.137F, 162.653F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-238.287F, -28.435F, 188.365F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.degreeVec(-199.476F, -45.735F, 171.089F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(-150.901F, -49.785F, 149.375F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-117.955F, -50.897F, 138.927F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(-103.049F, -66.804F, 120.329F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-52.4F, -74.82F, 66.893F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-5.782F, -65.14F, 18.352F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(4.962F, -46.522F, 3.613F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.degreeVec(-5.051F, 10.27F, 5.47F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(2.081F, 34.54F, 17.651F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(15.462F, 46.94F, 35.359F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(27.968F, 49.715F, 58.617F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(9.647F, 33.39F, 65.096F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(24.173F, 43.415F, 66.428F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(33.424F, 55.913F, 74.698F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.35F, KeyframeAnimations.degreeVec(12.808F, 52.951F, 36.836F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(8.434F, 41.523F, 19.588F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.162F, -0.307F, -4.449F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(1.455F, -1.855F, -2.052F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(2.961F, -3.374F, -1.091F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(3.908F, -3.925F, -1.819F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(7.699F, -3.747F, -6.936F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(8.752F, -3.731F, -9.535F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(5.952F, -3.579F, -17.37F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.posVec(5.302F, -1.039F, -24.26F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(5.073F, -0.425F, -25.776F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.posVec(4.786F, -0.451F, -25.162F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(5.223F, -0.466F, -25.218F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(7.262F, -2.31F, -28.962F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(7.732F, -3.92F, -29.933F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(7.715F, -5.138F, -29.943F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(7.733F, -3.614F, -29.936F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(7.1F, -2.851F, -30.718F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(2.18F, -0.575F, -31.676F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.987F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(47.917F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(61.217F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(118.083F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(127.514F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(49.828F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(20.52F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(6.819F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(18.334F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(60.363F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(35.634F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(36.442F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(42.755F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(20.987F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.018F, 0.064F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, 0.194F, 0.168F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.017F, 0.063F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, 0.106F, 0.151F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.018F, 0.064F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(-29.672F, -358.771F, -9.427F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.degreeVec(-31.586F, -338.325F, -31.137F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(-59.711F, -328.659F, -56.287F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.degreeVec(-76.827F, -320.549F, -60.569F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3F, KeyframeAnimations.degreeVec(-99.016F, -291.73F, -65.217F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(-49.016F, -256.071F, -2.236F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(-75.851F, -229.619F, -21.032F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(-71.692F, -173.701F, -18.123F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.65F, KeyframeAnimations.degreeVec(-51.765F, -107.574F, -21.084F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7F, KeyframeAnimations.degreeVec(-55.765F, -74.836F, -6.888F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(-45.385F, -46.253F, -12.769F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(-87.567F, 12.755F, -31.913F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(-77.26F, 29.009F, -44.818F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(-87.828F, 44.355F, -61.999F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(-111.527F, 39.441F, -69.647F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(-121.388F, 34.489F, -74.236F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(-108.662F, 40.543F, -68.006F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.95F, KeyframeAnimations.degreeVec(-97.997F, 39.617F, -55.76F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(-80.245F, 33.195F, -35.573F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(-32.048F, -0.797F, -11.463F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(-0.209F, -0.571F, -6.349F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1F, KeyframeAnimations.posVec(-1.527F, -2.989F, -5.804F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(-3.066F, -5.364F, -3.799F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.25F, KeyframeAnimations.posVec(-2.968F, -6.068F, -3.31F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.posVec(0.325F, -5.58F, -6.587F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(1.493F, -5.268F, -8.249F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.posVec(0.585F, -4.648F, -11.759F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.556F, -4.065F, -13.69F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(3.417F, 1.214F, -23.036F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.85F, KeyframeAnimations.posVec(3.651F, 2.482F, -25.747F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.posVec(3.466F, 2.201F, -27.492F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.posVec(4.172F, -0.46F, -30.483F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(4.631F, -3.13F, -32.734F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.posVec(4.954F, -5.293F, -33.595F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(4.943F, -6.51F, -33.601F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.posVec(4.954F, -4.985F, -33.599F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.posVec(4.591F, -3.875F, -34.413F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(1.865F, -0.628F, -33.489F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(24.326F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.35F, KeyframeAnimations.degreeVec(105.803F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.degreeVec(108.842F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.degreeVec(67.697F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.degreeVec(96.967F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9F, KeyframeAnimations.degreeVec(73.779F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.95F, KeyframeAnimations.degreeVec(51.275F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.degreeVec(39.927F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.05F, KeyframeAnimations.degreeVec(69.11F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.degreeVec(80.489F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.15F, KeyframeAnimations.degreeVec(60.78F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.degreeVec(24.326F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.022F, 0.074F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, 0.256F, 0.153F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.127F, 0.16F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, 0.22F, 0.164F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.052F, 0.114F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, 0.168F, 0.167F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, 0.022F, 0.074F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}