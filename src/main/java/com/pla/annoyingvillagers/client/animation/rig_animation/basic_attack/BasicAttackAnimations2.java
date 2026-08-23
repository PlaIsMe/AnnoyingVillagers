package com.pla.annoyingvillagers.client.animation.rig_animation.basic_attack;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class BasicAttackAnimations2 {
    public static final AnimationDefinition DUAL_BASIC_ATTACK1 = AnimationDefinition.Builder.withLength(0.6F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(72.912F, 69.567F, 50.659F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(43.309F, 5.951F, 1.593F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(76.453F, -74.495F, -72.647F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-3.452F, -114.606F, 4.19F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-0.694F, -121.658F, 1.65F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-0.959F, -126.32F, 1.717F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-2.629F, -119.691F, 4.482F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(82.446F, -82.866F, -76.283F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(24.094F, -58.484F, -14.736F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(17.774F, -17.468F, -3.266F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.949F, -5.019F, -3.034F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(1.011F, -5.581F, -13.581F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.075F, -3.492F, -18.293F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.251F, -2.628F, -16.963F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.621F, -2.598F, -16.577F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.627F, -2.521F, -16.735F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.461F, -2.378F, -16.633F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(0.258F, -2.175F, -17.157F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.45F, -2.043F, -17.659F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.132F, -1.859F, -17.836F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(8.069F, 4.92F, -1.841F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(7.225F, -2.909F, 5.26F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(8.24F, -3.23F, 0.032F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(4.437F, -7.765F, 3.698F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(5.681F, -2.392F, -0.945F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(6.284F, -4.155F, -0.831F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-1.028F, -5.047F, -3.064F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.976F, -5.647F, -13.702F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.099F, -3.497F, -18.305F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.252F, -2.625F, -16.931F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.616F, -2.595F, -16.531F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.45F, -2.375F, -16.591F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(0.293F, -2.176F, -17.144F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.488F, -2.05F, -17.677F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.145F, -1.87F, -17.876F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-249.52F, 4.815F, 161.393F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-239.383F, 100.021F, 186.911F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-251.576F, 243.106F, 141.228F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-273.318F, 264.256F, 158.409F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-269.708F, 289.558F, 156.245F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-264.408F, 315.737F, 152.318F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-251.177F, 319.225F, 142.803F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(-196.134F, 324.606F, 115.854F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-154.1F, 326.418F, 114.679F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-101.909F, 310.25F, 89.866F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-39.978F, 316.787F, 51.676F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(3.028F, -3.904F, 3.256F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.506F, -5.021F, -12.067F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(6.52F, -4.848F, -22.397F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(8.396F, -1.613F, -21.106F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(8.331F, -1.855F, -20.075F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(8.513F, -2.484F, -19.442F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(8.273F, -2.726F, -19.913F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(6.16F, -3.06F, -22.25F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(3.055F, -2.896F, -22.649F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(1.185F, -2.632F, -21.277F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.053F, -2.355F, -19.439F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-78.277F, 81.395F, -67.852F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-90.129F, 82.811F, -85.055F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-74.459F, 80.518F, -71.497F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-65.848F, 82.693F, -64.491F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-74.08F, 81.118F, -68.628F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-61.362F, 79.075F, -54.698F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(-34.784F, 67.261F, -26.21F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.223F, 53.255F, -16.49F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-22.918F, 20.943F, -6.12F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.84F, -0.354F, -0.93F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.822F, -0.334F, -0.912F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.364F, -0.332F, -0.671F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.046F, -0.417F, -0.177F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.468F, -1.463F, 0.205F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(10.206F, 0.039F, -0.511F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(36.348F, 0.367F, -0.294F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(19.036F, -0.123F, 0.287F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(5.434F, -0.047F, 0.296F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(19.239F, -0.063F, 0.184F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(23.825F, -0.049F, 0.126F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.325F, -0.332F, 0.15F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.331F, -0.35F, 0.244F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.328F, -0.34F, 0.803F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.314F, -0.389F, 0.438F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(0.314F, -0.339F, 0.145F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.316F, -0.387F, 0.441F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.317F, -0.387F, 0.541F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-69.295F, 64.917F, -107.277F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(36.094F, 43.383F, -33.729F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(79.381F, -24.43F, -77.784F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(104.312F, -43.543F, -101.072F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(111.376F, -35.22F, -101.359F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(118.814F, -38.787F, -109.728F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(108.03F, -13.541F, -87.489F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(79.578F, -29.824F, -82.057F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(49.82F, -37.803F, -59.701F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(18.59F, -16.923F, -38.18F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-3.604F, -6.972F, -6.963F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(1.399F, -6.117F, -12.152F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-3.406F, -3.015F, -12.365F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-7.644F, -2.947F, -11.996F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-8.773F, -2.1F, -11.68F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-8.894F, -2.583F, -12.071F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-9.031F, -2.656F, -12.293F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(-9.316F, -0.577F, -11.812F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-8.469F, 0.839F, -11.477F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-4.748F, -0.553F, -11.229F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-2.174F, -1.762F, -12.621F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(-0.765F, -1.831F, -13.852F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.022F, -1.915F, -15.562F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.503F, 2.11F, 4.2F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-46.918F, 2.341F, 3.185F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-50.521F, 3.439F, 15.817F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-49.817F, 3.323F, 15.443F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-40.009F, 2.203F, 10.145F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.044F, -0.518F, 0.34F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.147F, -0.417F, 0.442F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.095F, -0.436F, 0.358F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.791F, 3.033F, -0.065F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(3.018F, 1.054F, -0.039F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.337F, -0.331F, 0.184F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.326F, -0.319F, 0.113F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.692F, 35.525F, 28.836F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-28.413F, 8.748F, 19.915F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-55.084F, -18.214F, 18.276F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-52.46F, -41.999F, 30.425F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-42.913F, -34.163F, 30.182F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-36.749F, -12.34F, 15.892F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-13.384F, 5.023F, 16.336F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.055F, -3.771F, 0.732F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.594F, -3.481F, -8.289F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.463F, -3.14F, -16.716F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.992F, -2.943F, -17.757F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(1.442F, -2.648F, -17.959F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.243F, -1.939F, -17.084F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.029F, -1.713F, -16.347F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(75.069F, 0.453F, -3.737F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(60.248F, -2.301F, -2.579F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(59.781F, -6.904F, -2.419F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(48.267F, -7.924F, -2.083F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(41.424F, -0.595F, -7.297F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(48.95F, 2.359F, -7.14F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.003F, 0.157F, 0.164F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.019F, 0.088F, 0.128F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-73.914F, 25.996F, -36.525F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-32.399F, 4.271F, -18.942F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(2.161F, -22.318F, -31.7F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(21.352F, -43.943F, -46.494F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(31.171F, -55.245F, -57.179F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(24.112F, -56.328F, -52.975F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(-13.764F, -46.42F, -25.085F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-33.122F, -32.465F, -10.87F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-35.397F, -15.094F, -11.001F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.256F, -4.363F, -1.019F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.935F, -3.691F, -7.35F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.286F, -2.848F, -13.717F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.883F, -2.203F, -14.343F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-1.351F, -1.966F, -14.132F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.13F, -1.592F, -14.967F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.034F, -1.465F, -15.824F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(59.093F, -2.449F, 6.766F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(66.903F, -3.758F, 8.466F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(31.553F, -0.069F, 6.347F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(25.444F, -0.494F, 6.761F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(38.725F, -7.006F, 11.137F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(38.96F, -5.076F, 7.59F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.016F, 0.115F, 0.145F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.027F, 0.066F, 0.102F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition DUAL_BASIC_ATTACK2 = AnimationDefinition.Builder.withLength(0.6F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-160.672F, 2.727F, 165.784F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-218.832F, 76.263F, 100.294F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-155.303F, 157.596F, 190.993F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-151.746F, 184.803F, 180.666F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-134.023F, 214.895F, 164.51F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-149.387F, 190.687F, 178.976F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-2.81F, -5.926F, 0.607F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-4.93F, -6.804F, -11.062F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.073F, -5.794F, -14.022F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.994F, -5.572F, -13.9F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(1.037F, -5.631F, -14.435F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(1.359F, -5.705F, -14.942F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.855F, -3.588F, -14.073F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-163.653F, 42.485F, 177.716F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-168.458F, 138.593F, 174.873F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-168.161F, 175.896F, 179.242F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-167.5F, 184.269F, 181.795F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-176.703F, 181.842F, 179.39F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-2.804F, -5.936F, 0.638F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-5.029F, -6.86F, -11.048F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.078F, -5.809F, -14.061F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(1.012F, -5.598F, -13.956F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(1.068F, -5.669F, -14.505F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(1.404F, -5.755F, -15.019F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.876F, -3.625F, -14.17F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(200.743F, -3.845F, 120.978F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(148.966F, 23.298F, 82.155F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(74.222F, 11.829F, 56.643F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(69.87F, 0.559F, 48.498F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(64.172F, -9.019F, 44.096F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(47.763F, -11.163F, 34.856F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(7.955F, -5.017F, -0.469F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(2.805F, -5.022F, -5.701F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(2.003F, -4.881F, -9.868F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(1.26F, -5.123F, -10.67F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.862F, -5.411F, -11.716F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(0.709F, -5.904F, -13.809F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(0.953F, -6.24F, -14.884F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(1.254F, -6.416F, -15.579F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.746F, -4.265F, -13.197F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-23.601F, -12.694F, 5.408F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-24.255F, -0.83F, -3.087F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-25.161F, 2.881F, -0.452F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-31.055F, -0.064F, -0.124F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-35.741F, -2.175F, -4.348F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-31.581F, 3.43F, -4.203F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.033F, -0.614F, 0.415F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.007F, -0.523F, 0.162F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.045F, -0.506F, 0.329F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.019F, -0.486F, 0.201F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.735F, -0.402F, 1.244F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(10.437F, -0.509F, 1.206F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(20.008F, -0.568F, 1.178F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.053F, -0.557F, 1.183F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.053F, -0.557F, 1.183F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.294F, -0.391F, 0.306F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.295F, -0.405F, 0.467F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.3F, -0.316F, 0.043F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.3F, -0.316F, 0.043F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-246.822F, -13.003F, -89.988F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-141.6F, -6.904F, -97.73F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-1.011F, 12.16F, -98.865F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(22.575F, 0.929F, -91.106F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(42.122F, 4.099F, -88.184F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(38.516F, -4.207F, -48.929F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-12.749F, -8.723F, 0.177F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-9.665F, -9.64F, -16.131F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.848F, -7.019F, -15.861F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(2.19F, -6.193F, -13.155F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(1.552F, -5.389F, -11.13F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(1.276F, -5.184F, -10.772F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.811F, -4.186F, -11.717F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-36.912F, -3.73F, 1.255F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-13.424F, -2.764F, 1.741F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-13.4F, -1.112F, 2.961F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-18.426F, -0.925F, 3.508F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-21.142F, 2.79F, 13.511F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.009F, -0.564F, 0.233F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(0.028F, -0.447F, 0.142F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.146F, -0.297F, 0.229F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.13F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(23.342F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(1.067F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(1.631F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.379F, 0.373F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-0.32F, -0.385F, 0.529F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.32F, -0.308F, 0.066F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(155.936F, -12.313F, 156.085F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(104.938F, 40.352F, 120.648F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(17.067F, 33.158F, 60.333F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(4.4F, 18.83F, 49.716F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-4.264F, 10.578F, 29.914F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-17.97F, 3.354F, 16.707F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(3.887F, -4.878F, 0.111F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(2.873F, -4.313F, -6.406F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.23F, -4.214F, -7.794F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.307F, -4.262F, -8.368F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.069F, -3.791F, -9.359F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.022F, -2.264F, -9.389F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(86.921F, -2.473F, -3.521F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(50.666F, 1.391F, -2.505F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(36.574F, 5.782F, -4.158F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(55.558F, 6.749F, -12.449F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(55.284F, -1.844F, -1.144F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(59.503F, 0.954F, -5.283F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.009F, 0.194F, 0.172F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.021F, 0.054F, 0.095F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.01F, 0.113F, 0.148F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(100.25F, 22.977F, 165.421F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(36.446F, 94.662F, 97.42F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(130.253F, 135.498F, 163.5F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(128.645F, 164.085F, 156.827F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(125.681F, 179.481F, 157.914F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(140.749F, 181.221F, 169.504F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-3.896F, -5.076F, 0.098F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-3.042F, -5.132F, -9.774F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-1.377F, -5.486F, -11.447F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.392F, -4.995F, -10.937F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(0.052F, -4.353F, -9.964F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.009F, -2.845F, -9.955F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(84.569F, 1.054F, 2.249F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(101.809F, 7.582F, 2.861F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(102.872F, 9.026F, 3.24F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(90.57F, 8.069F, 3.948F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(80.768F, 2.097F, 0.866F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(59.41F, 0.165F, 2.044F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.003F, 0.185F, 0.17F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.032F, 0.242F, 0.173F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.002F, 0.107F, 0.15F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition DUAL_BASIC_ATTACK3 = AnimationDefinition.Builder.withLength(0.65F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-195.992F, -23.742F, 199.778F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-151.468F, -8.407F, 182.403F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-164.076F, 81.745F, 180.92F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-146.462F, 167.614F, 177.324F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-111.399F, 213.893F, 128.112F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-80.151F, 231.273F, 87.613F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-58.796F, 227.183F, 64.26F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(-54.208F, 227.947F, 57.825F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.323F, -6.228F, -3.844F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.655F, -5.6F, -6.648F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.404F, -5.661F, -9.991F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-1.871F, -5.438F, -15.158F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-1.937F, -5.395F, -20.704F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.715F, -6.384F, -29.845F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.031F, -7.393F, -39.647F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(0.678F, -6.288F, -38.044F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.703F, -6.089F, -37.507F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(0.368F, -5.815F, -36.928F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(20.051F, 284.286F, -8.072F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(8.182F, 265.328F, 5.107F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(14.427F, 249.957F, -2.447F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.319F, 145.465F, 3.288F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(17.669F, 40.238F, 2.391F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(16.312F, 4.739F, -2.988F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(10.576F, 1.557F, -1.005F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(15.365F, -4.682F, 2.113F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(17.611F, -4.734F, -0.362F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.321F, -6.23F, -3.865F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.652F, -5.603F, -6.607F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.436F, -5.688F, -9.896F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-1.892F, -5.45F, -15.134F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-1.964F, -5.394F, -20.63F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.735F, -6.417F, -29.901F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.006F, -7.475F, -39.768F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(0.71F, -6.327F, -38.107F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.754F, -6.12F, -37.551F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(0.415F, -5.839F, -36.962F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(54.658F, -86.035F, -74.273F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(37.486F, -96.524F, -57.49F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(46.408F, -104.532F, -75.922F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-36.268F, -130.68F, -13.879F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-64.254F, -166.679F, -4.433F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-91.986F, -228.135F, 26.637F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-96.359F, -72.348F, 48.999F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-101.3F, -108.873F, 59.156F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-85.662F, -130.12F, 49.174F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(-84.405F, -133.0F, 46.484F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(10.173F, -7.672F, -5.054F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(10.541F, -7.016F, -7.874F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(9.912F, -6.271F, -11.43F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(8.118F, -5.363F, -11.902F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(4.295F, -4.247F, -14.464F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-1.917F, -4.023F, -27.406F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(2.255F, -10.284F, -41.727F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(6.196F, -8.485F, -41.469F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(7.938F, -7.867F, -40.471F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(8.316F, -7.181F, -39.736F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-104.806F, 39.271F, -103.475F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-129.976F, 50.85F, -135.052F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-133.678F, 67.345F, -125.658F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-107.309F, 58.08F, -106.551F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-151.191F, 86.291F, -140.712F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-71.549F, 83.652F, -67.652F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(-65.654F, 82.724F, -64.379F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-1.112F, 0.104F, -0.274F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-1.416F, -0.091F, -0.553F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-1.123F, -0.193F, -0.802F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-1.108F, -0.039F, -0.612F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.943F, -0.447F, -0.998F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-0.92F, -0.366F, -0.948F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.219F, -0.145F, 1.299F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(7.153F, -0.174F, 1.048F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(15.235F, -0.214F, 0.783F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(7.199F, -0.309F, 0.565F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(9.175F, -0.086F, 0.349F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-7.309F, -0.071F, 0.328F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(1.213F, -0.057F, 0.307F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.294F, -0.327F, 0.06F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.304F, -0.39F, 0.357F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(0.313F, -0.361F, 0.225F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.315F, -0.232F, -0.11F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(0.314F, -0.31F, 0.058F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-279.858F, -9.462F, -88.835F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-268.076F, -6.873F, -84.488F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-260.132F, -2.794F, -88.414F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-186.333F, 0.772F, -95.005F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-45.651F, -1.112F, -95.092F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(14.655F, 6.018F, -103.241F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(47.488F, 17.619F, -104.899F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(90.721F, 20.951F, -105.611F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(102.3F, 19.099F, -104.827F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-10.19F, -5.826F, -1.152F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-10.1F, -5.532F, -4.216F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-10.825F, -6.97F, -7.694F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-12.431F, -6.721F, -17.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-9.618F, -6.954F, -25.688F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.525F, -6.842F, -29.804F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-1.169F, -4.011F, -34.851F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(-2.929F, -2.911F, -31.857F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-4.701F, -2.627F, -31.185F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-5.322F, -2.634F, -30.579F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-33.386F, 0.974F, 1.186F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.069F, -0.264F, 3.772F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-25.351F, 3.094F, 6.901F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(-24.87F, 3.542F, 6.88F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.014F, -0.547F, 0.291F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.002F, -0.488F, 0.077F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(0.075F, -0.416F, 0.268F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(0.077F, -0.415F, 0.272F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.037F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(31.502F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(66.025F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(11.033F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(16.032F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(9.252F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.379F, 0.371F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.32F, -0.368F, 0.706F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.32F, -0.033F, 1.366F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.32F, -0.363F, 0.263F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(-0.32F, -0.379F, 0.371F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-0.32F, -0.355F, 0.225F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(188.916F, -63.706F, 130.028F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(147.261F, -38.272F, 154.195F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(126.494F, -7.155F, 157.983F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(119.351F, 21.677F, 142.872F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(33.136F, 46.191F, 77.48F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-64.908F, 0.828F, 6.011F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-68.467F, -21.141F, 23.453F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(-65.659F, -26.515F, 25.022F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(3.074F, -5.549F, -1.589F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(3.675F, -4.847F, -6.293F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(3.858F, -4.152F, -10.813F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(3.572F, -3.851F, -17.177F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(1.028F, -4.447F, -23.029F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.006F, -4.304F, -32.204F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(0.525F, -4.789F, -33.391F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(0.632F, -4.847F, -33.481F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(100.951F, -1.074F, -1.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(53.477F, 1.901F, -4.202F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(30.578F, -3.882F, -0.5F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(86.934F, -3.442F, -1.195F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(80.814F, -5.31F, -1.781F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(81.007F, -7.16F, -2.054F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.004F, 0.234F, 0.163F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.011F, 0.19F, 0.174F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-0.023F, 0.172F, 0.179F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(105.902F, -45.027F, 190.864F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(108.919F, -14.977F, 178.197F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(104.562F, 15.561F, 169.186F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(81.736F, 42.096F, 144.61F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(125.449F, 132.758F, 161.635F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(195.336F, 182.383F, 166.464F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(205.172F, 208.531F, 133.602F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(209.084F, 210.75F, 129.064F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-2.809F, -4.926F, 1.707F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-3.847F, -4.434F, -10.498F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-3.685F, -4.774F, -18.869F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-1.124F, -5.549F, -26.632F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(0.182F, -3.962F, -31.777F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(-0.39F, -3.771F, -30.475F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-0.512F, -3.77F, -30.354F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(40.081F, 3.778F, 2.145F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(95.265F, 1.065F, 2.862F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(100.159F, 3.854F, 3.044F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(43.986F, 3.353F, 3.118F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(31.116F, 2.82F, 3.464F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(26.06F, 2.141F, 4.928F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.004F, 0.056F, 0.12F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(0.002F, 0.067F, 0.128F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-0.01F, 0.034F, 0.082F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition DUAL_BASIC_DASH_ATTACK = AnimationDefinition.Builder.withLength(0.75F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.56F, 0.967F, -0.166F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-8.795F, 1.574F, -0.009F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(16.918F, 2.085F, 0.885F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(60.123F, 4.199F, 0.71F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(46.889F, 6.121F, -0.382F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(53.672F, 6.089F, 0.222F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(48.149F, 9.01F, 1.017F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(18.684F, 43.813F, 6.988F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.043F, -5.078F, -0.405F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.049F, -5.082F, 2.459F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.171F, -5.688F, -16.17F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.286F, -10.899F, -41.741F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.354F, -8.459F, -56.366F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.344F, -7.452F, -65.236F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.355F, -6.977F, -67.673F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(-0.353F, -5.744F, -69.771F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.502F, -5.043F, -69.982F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7F, KeyframeAnimations.posVec(-0.848F, -2.42F, -67.267F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-0.477F, -1.447F, -65.842F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.249F, -0.3F, -0.648F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(3.874F, -0.271F, -0.048F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(21.935F, 0.835F, -1.348F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(13.963F, 1.202F, -1.59F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(19.845F, 1.375F, -1.488F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.516F, 0.345F, -1.295F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.043F, -5.087F, -0.444F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.049F, -5.081F, 2.508F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.169F, -5.694F, -16.188F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.294F, -11.002F, -41.844F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.365F, -8.538F, -56.476F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.356F, -7.524F, -65.343F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.367F, -7.061F, -67.771F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(-0.366F, -5.825F, -69.872F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.518F, -5.115F, -70.08F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7F, KeyframeAnimations.posVec(-0.879F, -2.442F, -67.316F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-0.505F, -1.453F, -65.86F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-129.56F, 14.672F, 110.871F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-143.251F, 21.525F, 110.749F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-138.068F, 9.686F, 113.234F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-120.844F, -8.551F, 121.731F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(12.368F, -3.779F, 109.804F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(47.235F, -7.53F, 110.443F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(66.604F, -16.561F, 106.594F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(83.743F, -0.437F, 90.795F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7F, KeyframeAnimations.degreeVec(73.849F, 34.974F, 65.577F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(53.487F, 48.889F, 48.857F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.551F, -4.95F, -3.735F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-0.511F, -3.705F, -1.015F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.729F, -5.573F, -18.771F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-1.659F, -11.582F, -42.086F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-2.063F, -8.231F, -55.729F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-1.921F, -6.804F, -63.762F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-1.644F, -6.157F, -65.481F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(-1.444F, -5.071F, -67.717F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-1.388F, -4.434F, -67.767F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7F, KeyframeAnimations.posVec(-0.342F, -2.083F, -63.867F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(0.786F, -1.259F, -62.037F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.638F, 2.116F, -1.316F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-35.063F, 2.038F, -1.253F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-21.433F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-23.713F, 0.493F, -0.466F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-27.657F, 5.955F, -6.031F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.005F, -0.619F, 0.318F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-0.032F, -0.44F, 0.127F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.216F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(25.744F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(35.612F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(16.876F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(4.955F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(34.198F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.379F, 0.57F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -0.379F, 0.581F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.346F, 0.793F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, -0.377F, 0.388F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.328F, 0.134F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.353F, 0.763F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-119.117F, -1.6F, -101.685F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-132.52F, -13.344F, -108.649F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-141.502F, -9.839F, -108.643F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-135.734F, 2.865F, -104.537F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(21.231F, 2.339F, -101.142F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(54.409F, 8.154F, -100.339F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(64.848F, 10.742F, -101.369F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(74.017F, 4.263F, -91.343F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(69.976F, -1.547F, -83.507F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.degreeVec(42.742F, -8.339F, -59.707F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.7F, KeyframeAnimations.degreeVec(14.79F, -0.025F, -37.448F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-9.11F, 19.596F, -23.508F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.281F, -5.945F, -3.523F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.279F, -4.659F, -1.395F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.339F, -6.735F, -19.378F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.064F, -13.031F, -42.557F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.896F, -8.454F, -54.539F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.796F, -7.685F, -63.448F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.336F, -5.456F, -68.657F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.65F, KeyframeAnimations.posVec(-0.361F, -4.458F, -69.09F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-1.524F, -2.092F, -68.952F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-36.601F, -2.35F, -4.443F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-35.79F, -2.226F, -4.282F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-16.657F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-23.607F, 0.135F, 0.442F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-31.206F, 2.044F, 5.382F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.04F, -0.668F, 0.256F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.505F, 0.144F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(0.054F, -0.47F, 0.294F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(18.657F, 3.604F, 2.588F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(19.305F, 3.603F, 2.591F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(31.943F, 3.565F, 2.646F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(12.87F, 3.565F, 2.646F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.226F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.058F, -0.333F, 0.49F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.057F, -0.334F, 0.504F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.04F, -0.316F, 0.777F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.066F, -0.318F, 0.364F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.309F, 0.078F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-67.227F, 8.377F, 9.489F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-69.116F, 7.21F, 6.444F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-19.847F, 10.203F, 10.675F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(45.758F, 7.8F, 20.901F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(19.618F, 13.007F, 21.84F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(5.079F, 16.052F, 22.154F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(6.218F, 19.87F, 21.099F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(11.87F, 39.718F, 25.922F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.037F, -4.606F, -0.092F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-0.03F, -4.674F, -0.204F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.034F, -5.299F, -13.344F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -5.846F, -32.006F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.005F, -5.132F, -48.107F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.01F, -4.642F, -57.624F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(0.012F, -4.096F, -60.521F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.042F, -2.767F, -63.52F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(0.484F, -1.203F, -62.88F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(68.956F, 2.089F, -1.099F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(62.365F, 4.173F, -0.995F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(23.051F, -3.572F, -1.057F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(52.619F, -2.299F, -1.599F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(68.288F, -1.481F, -1.649F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(46.668F, 0.093F, -3.926F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(23.676F, 3.307F, -1.128F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.007F, 0.133F, 0.157F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(0.008F, 0.024F, 0.066F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.67F, -2.019F, -8.818F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(4.444F, -3.055F, -8.374F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-73.063F, -0.663F, -5.682F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-74.294F, -10.054F, -5.249F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-54.328F, -2.735F, -5.692F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(-32.813F, 15.881F, -13.643F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.023F, -4.755F, -0.053F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.025F, -4.781F, -0.092F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.032F, -5.374F, -13.573F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.011F, -6.061F, -32.681F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.02F, -5.348F, -48.978F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.021F, -4.848F, -58.606F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(0.015F, -4.196F, -61.537F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -2.839F, -64.639F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-0.16F, -1.287F, -65.226F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(63.498F, 2.362F, 1.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(68.968F, 1.843F, 1.056F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(110.545F, 3.002F, 1.217F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(81.82F, 0.132F, 2.773F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(64.148F, -1.143F, 3.774F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(54.824F, -1.938F, 1.002F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(51.044F, 1.086F, 0.962F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.degreeVec(27.136F, -1.025F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.006F, 0.117F, 0.159F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.011F, 0.263F, 0.155F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.75F, KeyframeAnimations.posVec(-0.004F, 0.029F, 0.08F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition DUAL_BASIC_JUMP_ATTACK = AnimationDefinition.Builder.withLength(0.6F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-243.265F, 74.67F, 113.529F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-164.41F, 62.428F, 149.882F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-95.849F, 116.383F, 214.29F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-166.05F, 193.088F, 181.282F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-92.533F, 244.223F, 104.982F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.degreeVec(-65.6F, 255.123F, 80.798F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-39.604F, 259.0F, 55.744F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(30.448F, 257.584F, -13.312F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-2.099F, 266.262F, 14.563F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(46.925F, 276.77F, -40.56F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.972F, -0.422F, -2.997F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-1.165F, -1.927F, -2.854F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-2.73F, -4.11F, -1.909F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-3.468F, -5.704F, -3.647F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.729F, -0.381F, -6.548F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(2.202F, 1.912F, -11.62F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(1.958F, 3.247F, -11.068F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.719F, 4.427F, -10.2F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.295F, 1.659F, -10.738F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.565F, 22.156F, -1.539F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(12.213F, 25.685F, -16.374F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(17.955F, 10.505F, 3.09F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(8.05F, 0.323F, 0.083F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(13.676F, -6.846F, -0.381F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(8.277F, -6.744F, -0.386F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.971F, -0.42F, -2.99F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-1.214F, -1.938F, -2.809F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-2.807F, -4.159F, -1.818F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-3.625F, -5.793F, -3.665F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.749F, -0.388F, -6.569F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(2.259F, 1.888F, -11.663F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(2.027F, 3.238F, -11.056F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.79F, 4.421F, -10.105F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.33F, 1.66F, -10.712F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(83.41F, 0.053F, 98.349F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(94.707F, -7.561F, 68.079F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(100.888F, -4.166F, 37.662F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(77.777F, 10.804F, 69.76F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(244.647F, 4.389F, 38.86F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(200.232F, -8.221F, 39.328F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(166.281F, -3.716F, 45.23F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(183.419F, 4.528F, 34.792F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(7.037F, -0.351F, 3.218F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(7.272F, -2.741F, 2.914F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.749F, -5.519F, 2.421F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(1.494F, -1.381F, -9.305F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(9.708F, -0.806F, -15.717F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(10.101F, 1.222F, -15.456F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(9.642F, 3.819F, -15.011F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(8.135F, 0.96F, -16.214F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-55.854F, 0.129F, 7.319F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-26.942F, -1.112F, -13.357F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.7F, 7.681F, -32.159F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-18.625F, 0.067F, 0.03F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-14.966F, 0.033F, 0.016F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-31.312F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-46.312F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-36.312F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.027F, -0.839F, 0.408F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.428F, -0.025F, -0.14F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -0.508F, 0.159F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.643F, 0.359F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.583F, 0.295F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.166F, 0.141F, -0.112F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(84.018F, -78.56F, -89.418F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(163.287F, -0.141F, -180.112F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(163.287F, -0.141F, -180.112F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.322F, -0.337F, 0.157F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-0.106F, -0.026F, 1.696F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-1.676F, -0.115F, 1.278F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-1.676F, -0.115F, 1.278F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-193.662F, -10.73F, -115.692F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-199.36F, 3.416F, -73.308F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-165.155F, -5.428F, -76.472F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-0.81F, 6.793F, -68.956F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(103.596F, 2.342F, -52.981F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(106.793F, -6.309F, -67.055F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(85.487F, 2.921F, -65.571F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-6.973F, -2.168F, -7.701F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(-9.418F, -4.476F, -6.913F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-11.124F, -6.951F, -5.534F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-5.593F, -7.779F, -7.823F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.971F, -1.187F, -3.927F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-4.18F, 3.614F, -5.234F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.35F, KeyframeAnimations.posVec(-5.467F, 3.915F, -4.992F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-7.632F, 3.419F, -5.044F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-6.01F, 0.78F, -4.572F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.393F, 4.017F, 2.46F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-70.356F, -4.98F, -1.909F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-13.016F, -3.79F, -7.007F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-17.371F, -1.974F, -0.781F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-56.312F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-16.312F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-26.312F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.034F, -0.457F, 0.203F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.042F, -0.859F, 0.375F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.011F, -0.518F, 0.115F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -0.713F, 0.412F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -0.501F, 0.141F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.535F, 0.221F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(26.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(41.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(26.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.32F, -0.316F, 0.907F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(18.884F, 61.566F, 84.861F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(41.729F, 50.39F, 113.782F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-11.741F, 29.654F, 17.335F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.932F, -3.069F, 0.568F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(34.618F, -27.948F, -4.486F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(32.696F, -37.749F, -25.968F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(35.941F, -44.199F, -42.986F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-10.968F, -35.285F, -2.629F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(1.262F, 0.778F, 1.801F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(1.915F, -0.519F, 1.815F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(0.144F, -2.693F, 0.782F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(0.363F, 3.259F, -7.477F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(0.98F, 4.61F, -11.127F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.466F, 1.641F, -10.845F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(88.23F, -0.034F, -1.093F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(89.201F, -0.169F, -1.09F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(60.601F, 0.679F, -1.861F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(31.988F, 1.055F, -2.945F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(81.446F, -1.033F, -0.514F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.194F, 0.168F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.003F, 0.172F, 0.169F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.779F, 65.429F, 50.141F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(48.345F, 58.037F, 99.348F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(-30.626F, 23.164F, 12.694F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-38.795F, -11.386F, 0.244F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(-26.605F, -38.816F, -6.047F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.degreeVec(-28.497F, -50.437F, -19.972F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(-26.594F, -58.343F, -31.546F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(-32.76F, -40.015F, -5.638F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-1.546F, -0.345F, -1.744F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-2.233F, -2.143F, -1.64F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.29F, -3.351F, -0.899F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.009F, 0.201F, -2.984F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.355F, 3.523F, -5.286F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.801F, 5.424F, -8.05F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(-0.369F, 1.903F, -8.467F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(74.675F, 2.261F, -0.486F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(99.58F, -0.257F, -0.436F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.degreeVec(83.318F, 0.588F, -0.052F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.degreeVec(85.943F, -0.835F, 0.26F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(94.007F, 1.543F, -0.44F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.degreeVec(55.225F, -0.239F, 0.062F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.007F, 0.148F, 0.169F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 0.091F, 0.143F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

        public static final AnimationDefinition DUAL_BASIC_ULT = AnimationDefinition.Builder.withLength(1.65F)
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.595F, 14.256F, 0.045F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.451F, 52.075F, 17.382F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(58.871F, 66.585F, 45.545F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(116.274F, 74.872F, 98.1F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(115.482F, 75.245F, 94.311F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(48.22F, 54.282F, 26.756F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(29.931F, 5.671F, 3.196F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(56.124F, -66.314F, -52.467F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-33.636F, -117.514F, 29.854F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.526F, -132.213F, 12.54F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-2.452F, -143.927F, 7.923F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(12.147F, -171.992F, -1.892F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(12.361F, -206.777F, -3.483F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(2.544F, -246.267F, -13.852F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(46.262F, -311.895F, 21.245F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(27.435F, -319.261F, 15.796F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(18.598F, -350.334F, 4.618F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(37.872F, -399.422F, -21.148F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(84.109F, -413.08F, -73.721F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(107.614F, -416.361F, -104.284F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(138.294F, -407.151F, -137.563F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(144.05F, -404.28F, -144.046F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(111.468F, -424.669F, -111.753F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(46.376F, -423.529F, -46.779F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(27.408F, -414.018F, -27.789F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.238F, -389.928F, -10.34F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(0.465F, -350.613F, 1.012F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-0.016F, -0.988F, -0.111F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(0.225F, -1.83F, -0.997F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-0.371F, -3.799F, -2.348F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(-1.201F, -4.927F, -7.077F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(-0.057F, -5.63F, -10.821F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(1.266F, -5.37F, -16.624F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(0.029F, -3.602F, -25.726F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(-0.8F, -0.382F, -31.741F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(-0.743F, 2.405F, -34.866F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(0.12F, 3.595F, -35.259F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(-0.455F, 1.862F, -34.403F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(-0.866F, 0.491F, -35.852F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(-0.729F, -0.522F, -39.357F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.833F, -0.707F, -52.113F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(2.254F, 1.906F, -55.251F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(2.237F, 2.01F, -59.181F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(1.686F, -0.358F, -65.312F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(0.905F, -3.312F, -70.977F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.225F, -6.813F, -72.099F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.posVec(0.207F, -6.67F, -73.621F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(0.12F, -5.35F, -76.315F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.posVec(-0.939F, -4.247F, -75.318F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(-1.192F, -3.505F, -74.323F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(-0.971F, -2.815F, -73.277F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.196F, -1.969F, -72.086F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(23.287F, 15.938F, 4.688F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(16.294F, 24.806F, -4.754F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(20.231F, 4.461F, 4.105F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(11.154F, -26.486F, -8.299F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(12.171F, -55.824F, -11.092F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(2.451F, -109.753F, -0.193F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(11.522F, -179.373F, -6.552F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(8.629F, -225.324F, -6.416F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-68.195F, -265.007F, -80.459F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(18.649F, -334.577F, 5.542F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(13.721F, -350.231F, 4.186F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(8.052F, -354.826F, 2.55F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(17.076F, -366.452F, -0.444F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(13.897F, -377.818F, -1.717F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(1.876F, -363.869F, 0.998F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-0.019F, -0.987F, -0.105F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(0.208F, -1.83F, -0.993F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-0.414F, -3.797F, -2.329F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(-1.246F, -4.937F, -7.056F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(-0.094F, -5.645F, -10.833F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(1.256F, -5.388F, -16.657F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(0.046F, -3.609F, -25.747F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(-0.784F, -0.383F, -31.741F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(-0.734F, 2.406F, -34.859F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(0.131F, 3.594F, -35.263F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(-0.43F, 1.863F, -34.401F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(-0.848F, 0.491F, -35.83F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(-0.743F, -0.525F, -39.33F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.783F, -0.737F, -52.15F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(2.229F, 1.897F, -55.273F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(2.234F, 1.999F, -59.22F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(1.717F, -0.386F, -65.375F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(0.94F, -3.347F, -71.032F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.247F, -6.835F, -72.139F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.posVec(0.231F, -6.688F, -73.654F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(0.15F, -5.362F, -76.335F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.posVec(-0.927F, -4.256F, -75.342F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(-1.191F, -3.51F, -74.342F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(-0.976F, -2.817F, -73.287F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.191F, -1.969F, -72.081F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(4.569F, -341.069F, -354.802F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(34.793F, -335.052F, -332.878F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(66.812F, -339.0F, -319.087F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(95.455F, -343.911F, -317.459F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(119.147F, -333.809F, -295.385F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(128.156F, -342.878F, -276.729F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(102.68F, -351.605F, -257.458F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(63.653F, -314.493F, -245.78F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(101.719F, -190.263F, -214.629F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(117.059F, -109.194F, -246.626F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(85.684F, -56.212F, -216.669F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(98.888F, -24.621F, -219.308F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(124.194F, -49.36F, -196.686F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(160.01F, -31.78F, -214.31F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(162.565F, -7.78F, -228.221F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(104.408F, 2.955F, -154.717F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(102.785F, 19.345F, -126.813F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(101.705F, 46.659F, -128.832F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(98.332F, 184.651F, -134.153F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(92.068F, 233.9F, -131.555F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(70.394F, 280.52F, -113.327F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.degreeVec(82.844F, 296.694F, -126.02F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(87.114F, 314.796F, -125.664F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(75.761F, 305.121F, -111.604F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(32.772F, 298.767F, -61.689F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(13.844F, 304.465F, -38.641F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(-1.45F, 324.845F, -13.863F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(3.988F, 374.092F, 6.026F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.089F, -1.047F, 1.079F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(1.246F, -1.705F, 2.598F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(3.003F, -2.115F, 3.671F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(5.109F, -2.373F, 3.874F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(6.347F, -3.313F, 1.196F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(5.931F, -4.132F, -1.216F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(3.182F, -4.796F, -4.687F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(0.219F, -5.628F, -14.846F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(4.575F, -5.269F, -30.646F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(7.235F, -1.537F, -35.163F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(8.653F, 1.426F, -36.922F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(10.357F, 2.95F, -35.565F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(10.01F, 1.927F, -34.817F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(9.426F, 0.234F, -33.627F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(7.309F, -0.931F, -34.218F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(2.722F, 1.49F, -46.06F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(3.531F, 4.157F, -49.917F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(1.203F, 3.691F, -56.799F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(2.219F, -1.783F, -67.866F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(4.668F, -6.313F, -74.459F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(6.438F, -8.708F, -75.909F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.posVec(8.339F, -7.739F, -77.53F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(8.508F, -7.129F, -78.187F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.25F, KeyframeAnimations.posVec(5.953F, -6.487F, -79.17F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.posVec(4.546F, -6.043F, -79.157F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(1.932F, -4.952F, -78.072F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(0.171F, -3.709F, -75.78F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.posVec(-0.214F, -2.488F, -72.82F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.182F, -1.944F, -71.32F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-11.891F, 0.117F, 1.277F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-30.119F, 14.52F, 1.787F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-41.728F, 10.943F, 2.737F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-43.989F, 23.687F, -3.543F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-40.557F, 64.252F, -24.459F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-99.454F, 83.467F, -95.896F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-78.39F, 81.101F, -75.229F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-108.83F, 84.567F, -105.091F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-64.352F, 79.003F, -59.211F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-23.15F, 22.938F, -18.435F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-96.169F, 60.169F, -93.418F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-164.465F, 9.564F, -162.865F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-122.379F, 67.428F, -115.932F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(-83.551F, 73.148F, -76.506F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(-82.291F, 76.803F, -75.859F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(-120.985F, 84.636F, -116.577F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-76.023F, 83.961F, -72.067F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(-67.914F, 83.108F, -65.568F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(-54.286F, 81.823F, -52.376F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(-28.54F, 74.993F, -25.749F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(-15.944F, 58.826F, -11.334F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-11.891F, 0.117F, 1.277F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.011F, -0.036F, 0.101F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.037F, -0.087F, -0.087F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(-0.965F, 0.108F, -0.952F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(-0.846F, 0.153F, -0.909F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(-0.192F, 0.247F, -0.21F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(-0.986F, 0.429F, -0.635F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(-1.817F, 0.26F, -0.063F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(-1.082F, 0.312F, -0.778F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(-0.987F, 0.079F, -0.971F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(-0.884F, 0.108F, -0.946F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.posVec(-0.141F, 0.051F, -0.472F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.011F, -0.036F, 0.101F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.437F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(18.907F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(22.898F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(37.353F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(14.698F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(22.402F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(36.241F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(12.294F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(6.699F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(6.437F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.32F, -0.34F, 0.165F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -0.38F, 0.432F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, -0.381F, 0.519F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, -0.336F, 0.83F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -0.372F, 0.341F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(0.0F, -0.379F, 0.425F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, -0.343F, 0.807F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.364F, 0.289F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.32F, -0.34F, 0.165F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-359.182F, 14.364F, -5.17F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-356.542F, 35.127F, -16.508F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-369.416F, 55.472F, -37.494F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-415.568F, 64.362F, -86.928F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-460.745F, 55.795F, -128.72F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-465.637F, 53.544F, -135.604F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-411.576F, 68.847F, -91.197F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-338.596F, 37.698F, -39.178F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-288.281F, -17.642F, -72.399F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-252.86F, -8.524F, -92.853F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-249.463F, -0.033F, -83.904F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-270.748F, -0.483F, -72.879F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-279.617F, -9.1F, -74.465F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-234.758F, -9.42F, -82.787F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-169.027F, -3.254F, -85.815F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-54.74F, 2.36F, -88.754F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(33.056F, 1.735F, -78.42F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(74.506F, 6.1F, -82.409F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(100.042F, 15.669F, -81.553F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(106.418F, 19.48F, -87.732F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(122.259F, 10.411F, -78.061F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(81.026F, -9.991F, -77.299F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(37.599F, -19.622F, -48.991F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(13.356F, -9.966F, -25.003F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(1.137F, 9.503F, -4.11F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-0.13F, -1.065F, -1.32F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(-0.512F, -2.645F, -3.409F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(-1.583F, -4.344F, -5.071F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-3.165F, -6.034F, -6.202F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(-5.222F, -6.517F, -8.986F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(-5.254F, -7.145F, -11.505F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(-1.709F, -7.906F, -13.844F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(1.547F, -6.595F, -15.84F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(-3.425F, -2.795F, -19.797F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(-7.826F, -0.039F, -25.743F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(-9.534F, 2.204F, -29.644F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(-9.752F, 3.007F, -31.078F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(-11.241F, 0.193F, -33.38F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(-11.507F, -1.077F, -37.72F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(-9.513F, -2.003F, -44.069F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(-1.462F, -3.088F, -56.926F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(1.102F, 0.057F, -59.444F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(3.027F, 0.892F, -60.212F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(1.435F, 0.656F, -60.937F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(-2.43F, -0.231F, -64.795F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(-4.529F, -3.945F, -65.511F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.posVec(-6.93F, -3.423F, -68.866F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(-7.591F, -3.039F, -70.236F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.posVec(-4.557F, -2.66F, -69.193F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(-2.63F, -2.519F, -69.565F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(-1.108F, -2.394F, -70.596F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.129F, -2.13F, -72.881F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.853F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-37.602F, 2.114F, 4.169F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-49.652F, 2.321F, 2.119F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-44.957F, 1.45F, 6.375F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-21.345F, -3.344F, 1.839F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-13.123F, -2.919F, 1.632F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(-15.684F, -0.731F, 3.466F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-34.274F, 2.855F, 6.747F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(-38.536F, 3.357F, 6.75F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-12.853F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.016F, 0.112F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(0.029F, -0.153F, 0.418F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.054F, -0.05F, 0.376F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(0.006F, -0.008F, 0.123F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(0.009F, 0.012F, 0.063F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(0.073F, -0.007F, 0.368F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.0F, -0.016F, 0.112F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(6.303F, 2.266F, -0.019F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(30.359F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(11.83F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(1.039F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-0.017F, -0.328F, 0.184F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(-0.012F, -0.336F, 0.202F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -0.368F, 0.681F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -0.362F, 0.28F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(-0.32F, -0.304F, 0.054F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.619F, 19.734F, 4.785F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-3.45F, 40.979F, 29.581F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-2.026F, 58.379F, 42.627F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-5.744F, 58.555F, 42.266F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-14.203F, 29.932F, 23.805F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-27.414F, 1.351F, 16.485F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-59.814F, -25.368F, 6.393F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-72.661F, -50.179F, -6.295F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-51.12F, -75.161F, -29.173F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-71.44F, -107.106F, 13.453F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-31.488F, -152.25F, -6.09F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-38.818F, -185.977F, -20.068F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-67.332F, -209.999F, -43.47F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(-110.136F, -223.658F, -90.976F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(-159.224F, -212.589F, -118.09F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(-202.322F, -199.906F, -131.602F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(-229.151F, -179.414F, -146.451F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(-238.891F, -158.575F, -154.578F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-245.171F, -161.773F, -152.292F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(-242.613F, -148.622F, -143.57F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(-197.443F, -170.665F, -162.371F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-181.166F, -194.892F, -174.385F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.048F, -0.664F, 0.232F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-0.036F, -2.987F, 0.673F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(1.05F, -3.991F, -3.819F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(1.003F, -4.501F, -7.173F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(1.143F, -4.395F, -13.554F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(0.967F, -3.271F, -24.023F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(1.639F, -0.248F, -30.932F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(2.548F, 2.528F, -34.559F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(3.306F, 3.702F, -35.331F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(3.957F, 0.997F, -35.228F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(3.806F, 0.282F, -36.528F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(3.084F, 1.652F, -44.854F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(2.24F, 2.955F, -50.374F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(1.416F, 2.587F, -55.899F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(0.783F, 0.438F, -61.815F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(0.608F, -2.088F, -66.79F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.725F, -5.931F, -68.612F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.posVec(0.932F, -5.93F, -70.526F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(1.259F, -4.825F, -73.893F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(0.275F, -3.239F, -73.108F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.036F, -1.612F, -71.926F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(14.286F, 0.148F, -1.743F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(68.097F, 0.396F, -3.709F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(90.021F, -2.208F, -3.227F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(118.975F, 3.464F, -1.887F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(114.394F, 4.235F, -1.761F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(68.91F, -0.011F, -3.415F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(45.112F, 0.133F, -1.167F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(70.672F, 1.951F, -3.408F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(85.81F, 4.447F, -4.012F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(65.318F, 4.296F, -2.447F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(71.832F, -7.956F, -1.166F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(71.111F, -9.782F, -0.934F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(14.286F, 0.148F, -1.743F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.005F, 0.012F, 0.044F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.009F, 0.275F, 0.137F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.003F, 0.067F, 0.125F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(0.014F, 0.193F, 0.16F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.005F, 0.012F, 0.044F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.55F, 14.171F, -5.52F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-66.969F, 28.357F, -35.421F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-73.348F, 46.555F, -39.268F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-65.172F, 49.685F, -31.674F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-47.976F, 22.176F, -14.145F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-32.867F, -5.367F, -10.942F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-1.93F, -29.675F, -15.719F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(29.443F, -49.4F, -37.559F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(82.203F, -63.337F, -94.843F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(128.223F, -38.327F, -163.404F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(108.787F, 17.877F, -189.891F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(98.163F, 51.837F, -194.172F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(69.971F, 80.518F, -224.07F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(159.08F, 137.985F, -140.592F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(138.387F, 146.934F, -175.23F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(133.134F, 170.051F, -187.048F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(126.288F, 237.364F, -179.144F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(172.749F, 224.601F, -224.614F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(212.583F, 225.1F, -246.846F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(224.716F, 224.851F, -251.658F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(186.395F, 212.809F, -213.21F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(175.724F, 200.407F, -200.925F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(166.798F, 170.688F, -184.455F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.132F, -0.664F, -0.415F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-0.24F, -3.592F, -1.1F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(-0.117F, -4.093F, -4.177F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.408F, -4.558F, -6.581F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(1.428F, -4.93F, -7.736F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(1.268F, -4.468F, -11.802F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(-0.282F, -2.777F, -20.656F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(-1.36F, 0.437F, -26.8F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(-2.257F, 3.044F, -30.505F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(-3.086F, 3.971F, -32.215F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(-3.875F, 2.032F, -34.548F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(-3.631F, 0.653F, -36.503F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(-2.808F, -0.36F, -39.369F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(-0.427F, 0.245F, -48.554F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(0.751F, 2.071F, -53.531F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(1.196F, 2.245F, -57.752F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(1.031F, 0.594F, -61.846F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(0.404F, -1.356F, -64.984F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(-0.494F, -4.866F, -65.454F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.posVec(-0.837F, -4.801F, -66.958F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(-1.245F, -3.703F, -69.959F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(-0.12F, -2.698F, -70.868F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.16F, -1.679F, -72.241F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.581F, -0.122F, 1.628F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(49.157F, -1.802F, 6.311F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(89.105F, -6.907F, 9.138F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(92.307F, -8.826F, 10.084F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(84.665F, -7.737F, 10.161F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(60.881F, -2.671F, 7.509F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(51.79F, -2.149F, 6.881F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(55.179F, -2.177F, 6.223F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(81.781F, 0.663F, 2.55F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(104.808F, 4.244F, 2.257F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(57.268F, 5.42F, 3.939F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(32.729F, 5.142F, 6.291F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(69.271F, -5.434F, 13.139F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(72.999F, -8.855F, 13.968F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(29.36F, 2.817F, 2.747F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(17.581F, -0.122F, 1.628F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-0.005F, 0.016F, 0.054F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(-0.018F, 0.214F, 0.154F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(-0.017F, 0.095F, 0.134F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.016F, 0.248F, 0.164F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(-0.005F, 0.048F, 0.105F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(-0.032F, 0.176F, 0.146F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(-0.002F, 0.036F, 0.092F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(-0.005F, 0.016F, 0.054F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();

    public static final AnimationDefinition BASIC_MOUNT_ATTACK = AnimationDefinition.Builder.withLength(0.55F)
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.582F, 0.451F, 5.608F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-6.214F, 0.47F, 6.465F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(3.926F, -1.35F, 4.102F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(57.158F, 0.797F, -11.722F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(70.809F, 4.717F, -14.026F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(41.661F, 8.082F, -0.853F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(8.565F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.606F, -3.697F, 0.489F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.697F, -3.719F, 0.067F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.455F, -3.702F, -1.845F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.243F, -4.727F, -6.019F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.723F, -6.912F, -9.188F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.937F, -8.483F, -10.608F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-1.014F, -6.377F, -8.318F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-0.812F, -5.365F, -6.691F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.351F, -4.266F, -3.989F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -3.703F, -1.032F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-0.741F, -0.109F, 5.633F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(23.673F, -0.061F, -2.45F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-0.886F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.606F, -3.697F, 0.508F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.697F, -3.719F, 0.094F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.449F, -3.701F, -1.843F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.262F, -4.76F, -6.087F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.746F, -7.012F, -9.296F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.96F, -8.629F, -10.72F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-1.045F, -6.465F, -8.426F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-0.84F, -5.424F, -6.787F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.365F, -4.291F, -4.06F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -3.708F, -1.068F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-152.908F, 2.198F, 1.136F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.degreeVec(-167.674F, 0.469F, 7.324F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-155.451F, -3.407F, 9.263F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-48.779F, -10.607F, 0.597F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-9.681F, -12.572F, -0.616F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(-14.394F, 1.827F, 7.518F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-33.174F, -0.352F, 1.086F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.467F, -2.549F, 0.073F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.485F, -1.902F, -0.386F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.239F, -1.454F, -2.599F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.366F, -2.975F, -7.655F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.595F, -6.323F, -11.307F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.posVec(-0.616F, -7.786F, -12.129F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.693F, -8.678F, -12.441F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-1.264F, -5.02F, -9.671F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-1.166F, -3.578F, -7.465F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.posVec(-0.651F, -3.172F, -4.092F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(-0.003F, -3.68F, -1.032F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.561F, 0.193F, 0.188F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-16.465F, 0.24F, 0.223F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_hand", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.002F, -0.021F, 0.114F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(6.339F, 0.106F, 0.086F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(38.072F, 0.464F, 0.66F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(45.949F, 0.499F, 0.826F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.degreeVec(24.512F, 0.35F, 0.391F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_tool", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.003F, -0.04F, 0.146F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.012F, -0.017F, 0.905F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.013F, 0.054F, 1.082F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-0.009F, -0.079F, 0.583F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.27F, -0.669F, 0.818F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.degreeVec(-46.08F, -0.128F, 0.02F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.degreeVec(-34.39F, 1.261F, 0.099F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-30.371F, 1.535F, 0.416F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-33.156F, 0.038F, -1.362F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_arm", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.409F, -4.159F, -0.042F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.05F, KeyframeAnimations.posVec(0.466F, -4.268F, -0.482F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.315F, -4.048F, -1.892F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.15F, KeyframeAnimations.posVec(-0.189F, -4.001F, -5.105F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.63F, -4.927F, -8.028F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.857F, -5.893F, -9.642F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.4F, KeyframeAnimations.posVec(-0.837F, -5.318F, -7.908F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-0.704F, -4.883F, -6.618F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(0.006F, -3.683F, -1.025F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.168F, 43.846F, 16.592F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-27.431F, 39.859F, 25.787F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-41.168F, 43.846F, 16.592F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(-0.142F, -3.595F, -0.161F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(-0.142F, -3.603F, -0.944F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(-0.142F, -3.614F, -2.151F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(-0.142F, -3.617F, -2.569F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(-0.142F, -3.608F, -1.444F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(-0.142F, -3.595F, -0.161F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(61.824F, -0.157F, -0.724F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("right_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.111F, 0.153F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-43.974F, -46.0F, -17.746F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.degreeVec(-29.921F, -41.755F, -27.519F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.degreeVec(-43.974F, -46.0F, -17.746F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.154F, -3.586F, -0.166F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.1F, KeyframeAnimations.posVec(0.154F, -3.595F, -0.949F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.2F, KeyframeAnimations.posVec(0.154F, -3.606F, -2.157F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.3F, KeyframeAnimations.posVec(0.154F, -3.609F, -2.576F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.45F, KeyframeAnimations.posVec(0.154F, -3.6F, -1.45F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.55F, KeyframeAnimations.posVec(0.154F, -3.586F, -0.166F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(66.341F, 0.006F, 0.126F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("left_lower_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.124F, 0.158F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}