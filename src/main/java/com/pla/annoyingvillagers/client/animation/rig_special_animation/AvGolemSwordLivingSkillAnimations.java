package com.pla.annoyingvillagers.client.animation.rig_special_animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class AvGolemSwordLivingSkillAnimations {
        public static final AnimationDefinition SWORD_DEATH = AnimationDefinition.Builder.withLength(1.0F)
                .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.6391F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, -2.8418F, -0.4945F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -5.2472F, -1.4835F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -7.0512F, -2.2252F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -7.2071F, -2.2893F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -7.2071F, -2.2893F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5199F, 11.4862F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(3.3937F, 30.448F, 11.852F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(5.6354F, 32.2857F, 12.6574F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(7.3671F, 33.6449F, 13.3287F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(7.5188F, 33.7615F, 13.3895F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0866F, 15.6723F, 12.3776F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(36.9699F, 18.2695F, 17.4015F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(43.0804F, 19.7599F, 21.427F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(43.614F, 19.869F, 21.7827F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(43.614F, 19.869F, 21.7827F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-26.4736F, -47.9903F, 0.7049F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-21.0251F, -48.2555F, 0.1555F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-9.9776F, -48.6773F, -1.0598F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-1.663F, -48.887F, -2.0417F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-0.9467F, -48.9006F, -2.1282F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-0.9467F, -48.9006F, -2.1282F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-13.5445F, -6.5557F, -45.8277F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(10.1519F, -5.3541F, -59.5774F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(26.9414F, -1.0427F, -68.7153F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(28.3054F, -0.5667F, -69.4127F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(28.3054F, -0.5667F, -69.4127F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.0799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.1178F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.0799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.1177F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.0799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.1177F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.0799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.1177F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.0799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.1177F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.686F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(13.0799F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(13.1177F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -180.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -179.9999F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.1964F, 10.7961F, 32.0538F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-95.8504F, 5.8509F, -164.4004F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-155.9833F, 10.0572F, -213.1287F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-108.3416F, 3.5504F, -261.9047F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-38.5519F, 3.7134F, -333.744F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-44.453F, 1.7001F, -329.5786F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-49.0647F, 1.41F, -325.1322F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-49.0647F, 1.41F, -325.1322F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.7875F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.6485F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 69.4885F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 80.5242F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 81.2764F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 81.2764F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.642F, -38.1205F, -0.2608F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-7.2217F, -51.5531F, 3.4236F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-4.8903F, -73.3594F, 8.3753F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-3.197F, -88.3895F, 12.4745F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-3.0031F, -89.6611F, 12.9042F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-3.0031F, -89.6611F, 12.9042F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -37.899F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -56.1188F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -79.7092F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -91.7076F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -92.5634F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -92.5634F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(7.6037F, -62.2254F, -47.2127F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(58.8494F, -63.8758F, -79.0561F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(89.8412F, -59.9733F, -93.7188F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(92.075F, -59.5932F, -94.5073F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(92.075F, -59.5932F, -94.5073F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-2.6612F, -6.1615F, -11.9505F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(-3.5724F, -8.1091F, -13.3749F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(-5.5377F, -12.1852F, -15.8751F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-7.0915F, -15.3655F, -17.416F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(-7.2275F, -15.644F, -17.535F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(-7.2275F, -15.644F, -17.535F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-53.4589F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-59.0883F, -13.4562F, -314.3787F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-70.3736F, -13.3362F, -312.9593F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-78.8034F, -13.068F, -311.9906F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-79.5287F, -13.0384F, -311.9118F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(-79.5287F, -13.0384F, -311.9118F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(4.7002F, 0.7937F, 2.8818F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(4.7491F, -0.2875F, 1.929F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(4.7444F, -2.5366F, -0.0092F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(4.6501F, -4.3034F, -1.4703F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(4.6383F, -4.4594F, -1.5962F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(4.6383F, -4.4594F, -1.5962F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();

        public static final AnimationDefinition SWORD_FALL = AnimationDefinition.Builder.withLength(1.0F)
                .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.3374F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.6391F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5198F, 11.4862F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(35.6322F, 10.3386F, 13.7844F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10.0674F, -43.6121F, -1.2109F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -180.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.1964F, 10.7961F, 32.0538F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-95.8504F, 5.8509F, -164.4004F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-147.8403F, 7.9871F, -220.9961F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 22.7357F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.1694F, -41.4188F, 0.2382F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.4578F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-38.0663F, -63.4833F, -16.8387F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-3.0905F, -8.6071F, -13.5076F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-63.6342F, -4.4067F, -317.5313F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(3.8189F, 0.6128F, -0.4688F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();

        public static final AnimationDefinition SWORD_IDLE = AnimationDefinition.Builder.withLength(3.0F)
                .looping()
                .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.6391F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -1.6478F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, -1.6715F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.7199F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, -1.8559F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(0.0F, -1.9079F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, -1.9421F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -1.9509F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.0F, -1.9421F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.posVec(0.0F, -1.9079F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.posVec(0.0F, -1.8559F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -1.7199F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.7F, KeyframeAnimations.posVec(0.0F, -1.6715F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.posVec(0.0F, -1.6478F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.posVec(0.0F, -1.6391F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5199F, 11.4862F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(1.5057F, 29.6578F, 11.0938F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5198F, 11.4862F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(25.6731F, 13.9929F, 10.3645F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(27.7367F, 13.1366F, 12.0762F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(28.0171F, 13.0145F, 12.3081F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.degreeVec(27.7367F, 13.1366F, 12.0763F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(25.6731F, 13.9929F, 10.3645F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-26.4736F, -47.9903F, 0.7049F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.686F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -180.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -179.9999F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.1964F, 10.7961F, 32.0538F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-95.8504F, 5.8509F, -164.4004F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-155.9833F, 10.0572F, -213.1287F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-155.8451F, 9.9972F, -213.2716F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-155.452F, 9.8305F, -213.6776F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-154.8319F, 9.5785F, -214.318F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-154.0135F, 9.2659F, -215.162F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-153.016F, 8.912F, -216.1887F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-151.2084F, 8.3388F, -218.0457F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.degreeVec(-145.5704F, 6.9673F, -223.81F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(-143.6718F, 6.6101F, -225.745F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-142.3071F, 6.3784F, -227.1335F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(-141.8486F, 6.3045F, -227.5997F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-142.3071F, 6.3784F, -227.1335F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.degreeVec(-143.6718F, 6.6101F, -225.745F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(-145.5704F, 6.9673F, -223.8103F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(-151.2084F, 8.3388F, -218.0457F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-153.016F, 8.912F, -216.1887F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-154.0135F, 9.2659F, -215.162F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.7F, KeyframeAnimations.degreeVec(-154.8319F, 9.5785F, -214.318F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.8F, KeyframeAnimations.degreeVec(-155.452F, 9.8305F, -213.6776F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.9F, KeyframeAnimations.degreeVec(-155.8451F, 9.9972F, -213.2716F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-155.9833F, 10.0572F, -213.1287F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.7875F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 24.0709F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 25.1527F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 29.6767F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 31.3968F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.2415F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.4532F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.2415F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 31.3968F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 29.6767F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 25.1527F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 24.0709F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.7875F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.642F, -38.1205F, -0.2608F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-9.6136F, -38.2321F, -0.2212F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-9.5042F, -38.6664F, -0.0683F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(-8.8653F, -41.3984F, 0.8519F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-8.7796F, -41.7904F, 0.9792F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-8.7582F, -41.8895F, 1.0112F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-8.7796F, -41.7904F, 0.9792F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-8.8653F, -41.3984F, 0.8519F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-9.5042F, -38.6664F, -0.0683F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.degreeVec(-9.6136F, -38.2321F, -0.2212F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-9.642F, -38.1205F, -0.2608F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -37.899F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.0868F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.8157F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -42.3555F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -43.3585F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -44.0036F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -44.1666F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -44.0037F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -43.3585F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -42.3555F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.8157F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.0868F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -37.899F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-13.8783F, -57.1888F, -33.9718F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-14.3171F, -57.3476F, -33.6027F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(-17.3034F, -58.3539F, -31.0741F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-17.7712F, -58.5004F, -30.6754F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-17.8911F, -58.5374F, -30.5732F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-17.7712F, -58.5004F, -30.6755F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-17.3034F, -58.3539F, -31.0741F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-14.3171F, -57.3476F, -33.6027F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.degreeVec(-13.8783F, -57.1888F, -33.9718F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-2.6612F, -6.1615F, -11.9505F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(-2.6612F, -6.1851F, -11.9563F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(-2.6612F, -6.2492F, -11.9718F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(-2.6612F, -6.3095F, -11.9862F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(-2.6612F, -6.3804F, -12.003F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(-2.6612F, -6.7505F, -12.0877F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(-2.6612F, -6.8928F, -12.1188F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.25F, KeyframeAnimations.posVec(-2.6612F, -6.9473F, -12.1305F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.posVec(-2.6612F, -6.9867F, -12.139F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.posVec(-2.6612F, -7.0078F, -12.1434F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(-2.6612F, -6.9867F, -12.139F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.posVec(-2.6612F, -6.9473F, -12.1305F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.posVec(-2.6612F, -6.8928F, -12.1188F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.posVec(-2.6612F, -6.7505F, -12.0877F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(-2.6612F, -6.3804F, -12.003F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.posVec(-2.6612F, -6.3095F, -11.9862F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.7F, KeyframeAnimations.posVec(-2.6612F, -6.2492F, -11.9718F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.posVec(-2.6612F, -6.1851F, -11.9563F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.posVec(-2.6612F, -6.1615F, -11.9505F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-53.4589F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-53.7195F, -13.1522F, -315.0629F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(-55.1429F, -11.7579F, -314.7558F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-55.337F, -11.566F, -314.7166F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.degreeVec(-55.1429F, -11.7579F, -314.7558F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-53.7195F, -13.1522F, -315.0629F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.degreeVec(-53.4589F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(4.7002F, 0.7937F, 2.8818F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(4.7002F, 0.7889F, 2.8673F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(4.7002F, 0.7758F, 2.8276F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(4.7002F, 0.7486F, 2.7466F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.posVec(4.7002F, 0.6685F, 2.5182F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.posVec(4.7002F, 0.6365F, 2.4305F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.posVec(4.7002F, 0.615F, 2.3727F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(4.7002F, 0.6095F, 2.358F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(4.7002F, 0.615F, 2.3727F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.posVec(4.7002F, 0.6365F, 2.4305F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.posVec(4.7002F, 0.6685F, 2.5182F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(4.7002F, 0.7486F, 2.7466F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.7F, KeyframeAnimations.posVec(4.7002F, 0.7758F, 2.8276F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.85F, KeyframeAnimations.posVec(4.7002F, 0.7889F, 2.8673F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(3.0F, KeyframeAnimations.posVec(4.7002F, 0.7937F, 2.8818F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();

        public static final AnimationDefinition SWORD_SKILL_1 = AnimationDefinition.Builder.withLength(2.65F)
                .addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.6391F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(0.0F, -1.6578F, -0.3029F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -1.7067F, -1.0952F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -1.7751F, -2.2041F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -1.8525F, -3.4571F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -1.9281F, -4.6813F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(0.0F, -1.9912F, -5.7042F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(0.0F, -2.0316F, -6.3592F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, -2.0523F, -6.6933F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, -2.0742F, -7.0482F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -2.1199F, -7.7888F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(0.0F, -2.212F, -9.28F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -2.8108F, -12.0823F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, -5.1137F, -15.6114F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -5.5438F, -16.0606F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -5.7279F, -16.1956F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(0.0F, -5.8826F, -16.3091F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(0.0F, -6.0104F, -16.403F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(0.0F, -6.114F, -16.479F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -6.2586F, -16.5851F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, -6.3368F, -16.6425F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.posVec(0.0F, -6.3691F, -16.6661F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.posVec(0.0F, -6.3703F, -16.666F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(0.0F, -6.3315F, -16.6309F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.posVec(0.0F, -6.2568F, -16.5633F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(0.0F, -6.1493F, -16.466F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.posVec(0.0F, -6.0121F, -16.3417F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.posVec(0.0F, -5.8481F, -16.1932F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(0.0F, -5.6604F, -16.0232F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, -5.2263F, -15.6301F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.posVec(0.0F, -4.7341F, -15.1844F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.posVec(0.0F, -3.41F, -13.9854F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.posVec(0.0F, -2.9071F, -13.53F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.posVec(0.0F, -2.4561F, -13.1216F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.posVec(0.0F, -2.2577F, -12.942F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.posVec(0.0F, -2.0814F, -12.7824F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.posVec(0.0F, -1.9304F, -12.6456F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(0.0F, -1.8075F, -12.5343F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.posVec(0.0F, -1.716F, -12.4514F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.posVec(0.0F, -1.6588F, -12.3997F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.posVec(0.0F, -1.6391F, -12.3818F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5199F, 11.4862F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(1.0952F, 28.1309F, 10.5848F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-1.8545F, 24.4517F, 8.4452F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-5.5632F, 19.2236F, 5.9136F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-9.2437F, 13.3319F, 3.6177F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.3558F, 7.7989F, 1.8845F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-14.5145F, 3.6807F, 0.8153F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-15.4716F, 1.7881F, 0.3803F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-16.1997F, 0.3232F, 0.0666F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-16.3569F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-8.133F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(31.8287F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(35.824F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(35.7836F, 0.0287F, 0.0222F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(34.9559F, 0.6232F, 0.4734F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(34.1727F, 1.1946F, 0.8946F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(30.6267F, 3.8891F, 2.7295F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(27.5024F, 6.4025F, 4.2393F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.degreeVec(22.143F, 10.9859F, 6.5657F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.degreeVec(16.509F, 16.1026F, 8.6018F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(11.1599F, 21.1384F, 10.0915F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(8.0171F, 24.1278F, 10.7456F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(5.4018F, 26.6088F, 11.1579F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(3.4837F, 28.4145F, 11.3818F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(2.4392F, 29.3904F, 11.4753F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(2.3002F, 29.5199F, 11.4862F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(22.5575F, 13.7039F, 9.2536F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(15.4355F, 12.3849F, 7.2845F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(5.6183F, 10.1658F, 4.8566F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-5.0346F, 7.2863F, 2.6788F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-14.657F, 4.337F, 1.1913F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-21.5894F, 2.0575F, 0.4331F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-24.7125F, 0.9998F, 0.1821F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-26.2347F, 0.479F, 0.0807F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-27.1031F, 0.1806F, 0.029F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-27.6204F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-17.2706F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(33.7294F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(38.731F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(38.5956F, 0.1198F, 0.1078F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(38.041F, 0.6154F, 0.5483F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(37.1303F, 1.4458F, 1.2677F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(35.2771F, 3.1998F, 2.7157F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.degreeVec(33.0832F, 5.3889F, 4.3998F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(30.0726F, 8.5923F, 6.6501F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(27.4631F, 11.5537F, 8.5353F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(26.461F, 12.7356F, 9.2411F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(25.7365F, 13.6052F, 9.7446F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-26.4736F, -47.9903F, 0.7049F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-21.9454F, -45.9982F, -1.2502F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-10.9554F, -40.2051F, -5.122F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(2.6988F, -31.1575F, -7.8319F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(16.3055F, -20.6648F, -7.8395F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(28.1077F, -11.3021F, -5.6485F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(36.5127F, -4.9975F, -2.9687F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(40.2421F, -2.3446F, -1.4976F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(42.0436F, -1.1034F, -0.7294F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(43.0757F, -0.4118F, -0.2776F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(43.6524F, -0.0055F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(30.4325F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-36.1235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-42.3063F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(-41.7946F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(-40.5181F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(-38.8166F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-35.5939F, -0.0542F, 0.0051F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(-34.3551F, -1.1734F, 0.0972F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(-33.1963F, -3.6126F, 0.2624F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-32.1232F, -7.1573F, 0.453F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(-31.1399F, -11.5803F, 0.6344F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-30.2477F, -16.6352F, 0.7838F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(-28.0999F, -32.8873F, 0.9476F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(-27.3201F, -39.9358F, 0.883F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(-26.9226F, -43.661F, 0.8142F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-26.6406F, -46.3512F, 0.7494F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-26.4926F, -47.7986F, 0.7101F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-26.4736F, -47.9903F, 0.7049F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-19.0642F, -4.0902F, -41.3844F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-0.2681F, 0.5778F, -47.2502F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(36.6557F, 14.0855F, -53.2212F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(94.1019F, 32.7232F, -44.411F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(148.8035F, 35.458F, -24.8517F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(176.2516F, 30.5617F, -16.4929F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(185.2484F, 27.7123F, -15.1805F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(189.0668F, 26.3977F, -14.8209F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(191.1471F, 25.3343F, -14.6858F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(192.1068F, 23.6766F, -15.2302F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(192.4579F, 20.6083F, -16.9436F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(177.1097F, 10.6471F, -23.381F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(96.7482F, -21.3864F, -14.7158F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(88.3143F, -22.9788F, -11.6356F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(87.691F, -23.1669F, -11.3693F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(87.4824F, -23.2479F, -11.1892F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(86.7901F, -23.2712F, -10.9974F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(86.254F, -23.2516F, -10.8916F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(85.0904F, -23.0007F, -10.8055F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(83.2551F, -22.4925F, -10.7578F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(80.7803F, -21.7388F, -10.7687F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(77.6922F, -20.7511F, -10.8657F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(74.0147F, -19.5436F, -11.0823F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(69.7722F, -18.1365F, -11.4557F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.degreeVec(64.9925F, -16.5591F, -12.0242F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(59.7112F, -14.8523F, -12.8234F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(47.8439F, -11.2771F, -15.2133F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(34.7333F, -7.9557F, -18.666F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.degreeVec(21.2191F, -5.4344F, -22.9022F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(8.3051F, -4.0103F, -27.3748F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(2.3692F, -3.6989F, -29.5112F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.25F, KeyframeAnimations.degreeVec(-3.095F, -3.6077F, -31.4985F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(-8.0215F, -3.6871F, -33.2937F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(-12.3643F, -3.8843F, -34.8697F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(-16.0935F, -4.1484F, -36.2119F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(-19.1892F, -4.4336F, -37.3147F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-21.6364F, -4.7012F, -38.1773F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(-23.4187F, -4.9192F, -38.7997F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-24.514F, -5.0629F, -39.1796F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(11.3628F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.3737F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.0268F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(10.0588F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(11.3821F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(11.3628F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.3738F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.0268F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(10.1198F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(11.3821F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(11.3628F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.3737F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.0268F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(10.0588F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(11.3821F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(11.3628F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.3738F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.0268F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0077F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(11.4559F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(11.3628F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(10.136F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(9.9422F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0077F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(11.456F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.686F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(11.3628F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(10.3737F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(10.0268F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.0077F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(11.456F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(11.686F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -180.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_1_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.1964F, 10.7961F, 32.0538F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-0.0122F, 9.9731F, 34.065F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(19.3575F, 6.0495F, 39.651F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(54.7992F, -5.2091F, 45.4425F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(103.8828F, -21.4134F, 40.9647F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(149.6011F, -27.8004F, 27.26F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(175.7317F, -26.3666F, 18.651F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(185.1066F, -24.7015F, 16.0698F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(189.1841F, -23.8311F, 15.0512F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(191.3704F, -23.094F, 14.7234F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(192.3201F, -21.9332F, 15.293F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(192.5702F, -19.778F, 17.023F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(177.0296F, -11.0287F, 23.3794F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(96.7495F, 21.3319F, 14.7605F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(88.3143F, 22.9788F, 11.6356F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(87.691F, 23.1669F, 11.3693F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(87.4824F, 23.2479F, 11.1892F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(86.7901F, 23.2712F, 10.9975F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(86.2669F, 23.2626F, 10.8969F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(85.195F, 23.0898F, 10.8465F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(83.5418F, 22.7366F, 10.8634F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(81.341F, 22.2156F, 10.9571F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(78.6234F, 21.5405F, 11.1413F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(75.4192F, 20.7274F, 11.4324F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(71.7598F, 19.796F, 11.8476F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(63.2229F, 17.6793F, 13.1129F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(53.374F, 15.4417F, 15.0173F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(42.7085F, 13.3833F, 17.5235F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(26.5605F, 11.1908F, 21.9718F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(16.6687F, 10.4762F, 24.9506F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(8.1397F, 10.27F, 27.5962F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(4.5159F, 10.2994F, 28.7281F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(1.3699F, 10.3819F, 29.7099F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(-1.2684F, 10.4918F, 30.531F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-3.3726F, 10.606F, 31.1831F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(-4.9159F, 10.7047F, 31.6596F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-5.8689F, 10.7719F, 31.9531F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-6.1964F, 10.7961F, 32.0538F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_2_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0758F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.475F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.9589F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.2593F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(11.0484F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(10.4603F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(9.9708F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(9.9457F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(10.4028F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(11.4008F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(12.8607F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(14.6989F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(16.8198F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.713F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(26.6941F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(28.2571F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(29.3784F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_3_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0758F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.475F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.9589F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.2593F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(11.0484F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(10.4603F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(9.9708F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(10.4028F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(12.078F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.degreeVec(13.7381F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.degreeVec(15.7306F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.713F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(26.6941F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(28.2571F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(29.3784F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_4_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0758F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.475F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.9589F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.2593F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(11.0484F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(10.1254F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(9.9262F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(10.1015F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.8391F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(12.078F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(14.6989F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(16.8198F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.713F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(26.6941F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(28.2571F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(29.3784F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_5_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0758F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.475F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.9589F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.2593F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(11.0484F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(10.4603F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(9.9708F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(9.9457F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.8391F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(12.078F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.degreeVec(13.7381F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.degreeVec(15.7306F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.713F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(26.6941F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(28.2571F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(29.3784F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_6_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0758F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.475F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.9589F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.2593F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(11.0484F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(10.1254F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(9.9262F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(10.1015F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.8391F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(12.078F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(14.6989F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(16.8198F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.713F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(26.6941F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(28.257F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(29.3784F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_7_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(29.0758F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(26.475F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(14.9588F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(12.2593F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(11.0484F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(10.1254F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(9.9262F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(9.9235F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(10.1015F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(10.8391F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(12.078F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(14.6989F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(16.8198F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(23.713F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(26.6941F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(28.2571F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(29.3784F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("Tool_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-95.8504F, 5.8509F, -164.4004F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-97.0422F, 4.8F, -164.2286F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-100.1424F, 2.0932F, -163.6822F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-104.46F, -1.5834F, -162.6901F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-109.2869F, -5.5152F, -161.2819F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-113.8529F, -9.0174F, -159.6828F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-117.3055F, -11.5059F, -158.3167F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-118.914F, -12.6151F, -157.6376F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-120.17F, -13.4585F, -157.0894F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-120.4447F, -13.6402F, -156.9675F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-105.0727F, -13.7344F, -157.2962F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-25.617F, -12.8999F, -158.6963F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-18.3761F, -12.7538F, -158.7382F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(-18.4879F, -12.8704F, -158.7286F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(-18.7417F, -12.979F, -158.7187F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.degreeVec(-19.2236F, -13.0635F, -158.7091F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.15F, KeyframeAnimations.degreeVec(-20.0062F, -13.082F, -158.7029F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(-21.1622F, -12.9928F, -158.7054F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-22.8089F, -12.7425F, -158.7168F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(-24.1133F, -12.472F, -158.6779F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(-27.9356F, -11.5644F, -158.491F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.degreeVec(-33.1761F, -10.2067F, -158.2521F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.degreeVec(-39.5848F, -8.4562F, -158.0844F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.degreeVec(-46.8821F, -6.401F, -158.1053F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.degreeVec(-54.7477F, -4.1681F, -158.4024F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.degreeVec(-66.8247F, -0.8274F, -159.4224F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(-78.0993F, 2.0572F, -160.9651F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(-84.565F, 3.5597F, -162.0853F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(-89.8168F, 4.6816F, -163.1095F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(-91.8995F, 5.1001F, -163.5419F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-93.5765F, 5.4257F, -163.9004F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(-94.8161F, 5.6598F, -164.171F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-95.5855F, 5.8023F, -164.3414F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-95.8504F, 5.8509F, -164.4004F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-155.9833F, 10.0572F, -213.1287F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-145.3895F, 7.1456F, -223.7822F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-77.2896F, 4.0954F, -291.9869F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-26.5984F, 9.7282F, -342.7639F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-16.0601F, 16.7242F, -353.3563F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.5352F, 22.3616F, -356.9106F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-11.2024F, 25.7122F, -358.2659F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(-10.8324F, 26.6388F, -358.684F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-10.9698F, 26.0466F, -358.5763F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-11.9565F, 22.977F, -357.666F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-16.7794F, 15.0583F, -352.9664F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-111.1521F, 3.6449F, -259.0656F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-173.1808F, 20.0109F, -198.9166F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-174.5505F, 22.6237F, -197.9355F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(-175.2469F, 23.737F, -197.5621F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(-175.7083F, 24.5899F, -197.3264F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.05F, KeyframeAnimations.degreeVec(-176.0774F, 25.3467F, -197.1469F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(-176.1993F, 25.6129F, -197.0896F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-176.1985F, 25.5974F, -197.0918F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(-175.9135F, 24.7121F, -197.2649F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(-175.2409F, 22.868F, -197.7005F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.degreeVec(-173.36F, 19.0321F, -199.0592F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-170.0412F, 14.882F, -201.7419F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(-166.8087F, 12.4261F, -204.5263F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(-160.7442F, 9.7578F, -209.9256F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(-156.9481F, 8.8227F, -213.3026F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.25F, KeyframeAnimations.degreeVec(-155.5289F, 8.6078F, -214.5247F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(-154.5822F, 8.5523F, -215.2848F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(-154.1346F, 8.6388F, -215.5586F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(-154.4449F, 9.1247F, -214.9506F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-155.8388F, 9.974F, -213.2932F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-155.9831F, 10.0572F, -213.1287F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.7875F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 28.0212F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 44.0818F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 50.0353F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 53.8519F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.8882F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 56.9701F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 57.08F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 56.4983F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 53.0766F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0164F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 41.8773F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 41.5704F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 42.5226F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 43.1634F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 43.5983F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.4123F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.8532F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.3025F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 43.8694F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 41.5319F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 36.4753F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 26.5562F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 25.1066F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 24.1357F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.7875F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_up_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.642F, -38.1205F, -0.2608F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-9.4774F, -38.2241F, 0.0473F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-9.1943F, -37.8595F, 0.6535F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(-9.0806F, -36.1275F, 1.1228F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(-9.3242F, -32.8148F, 1.1829F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10.0518F, -28.4075F, 0.6788F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(-11.2609F, -23.9478F, -0.3852F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(-12.5377F, -20.7511F, -1.5853F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(-14.6542F, -17.1161F, -3.6331F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(-18.7159F, -12.917F, -7.6341F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(-56.6694F, -4.396F, -45.5069F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(-93.3336F, -3.2599F, -81.6236F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(-4.2912F, -49.6736F, 9.609F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(-3.76F, -54.9562F, 10.5965F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(-3.4031F, -55.4237F, 11.3382F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.95F, KeyframeAnimations.degreeVec(-3.0697F, -55.7014F, 12.0336F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(-2.918F, -55.7782F, 12.3504F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(-2.8818F, -55.803F, 12.4261F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(-3.0022F, -55.9699F, 12.1722F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(-3.2597F, -56.2332F, 11.6295F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.degreeVec(-3.8429F, -56.3532F, 10.4032F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-4.595F, -55.5992F, 8.8317F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(-5.1676F, -54.3939F, 7.6506F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(-6.1197F, -51.4864F, 5.7422F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(-6.8186F, -48.8904F, 4.4074F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(-8.3478F, -42.8579F, 1.7363F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-9.0782F, -40.1123F, 0.5823F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-9.5701F, -38.367F, -0.1553F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-9.642F, -38.1205F, -0.2608F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("leg_down_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -37.899F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.191F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -50.0839F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -53.4906F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -54.863F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -54.6104F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -52.3117F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -50.0045F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -42.7075F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.3007F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -93.9771F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -98.4789F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -101.7022F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -103.8159F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -105.103F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -105.7879F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -106.068F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -106.0797F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -105.1091F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -102.9358F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -99.6176F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -95.2316F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -89.8335F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -86.7713F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -80.0462F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -72.6164F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -48.9469F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -45.4761F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -42.4431F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -40.0398F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.4556F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -37.899F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-4.4708F, -51.6873F, -37.3036F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(19.7583F, -34.9024F, -42.4225F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(59.3899F, -5.3901F, -41.6113F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(120.8864F, 24.0768F, -23.7325F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(183.4201F, 29.9782F, -0.6528F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(218.2415F, 27.9629F, 7.3577F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(230.9757F, 27.5494F, 8.262F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(236.6925F, 27.5784F, 8.471F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(239.7415F, 27.441F, 8.2534F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(240.7581F, 27.0319F, 6.7478F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(240.255F, 26.2695F, 3.0758F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(201.8113F, 19.504F, -16.8414F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(25.6161F, -21.4893F, 14.5715F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(8.4803F, -16.436F, 19.9236F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(7.8945F, -16.228F, 20.1617F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(7.7902F, -16.1076F, 20.2673F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(7.6002F, -16.0402F, 20.2865F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(6.6432F, -15.862F, 20.3597F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(4.8099F, -16.6413F, 20.2155F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(1.7245F, -18.615F, 19.6846F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(-2.5073F, -21.6872F, 18.5627F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(-7.641F, -25.7747F, 16.5899F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-13.1742F, -30.7464F, 13.5113F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(-18.312F, -36.3359F, 9.128F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.4328F, -39.2248F, 6.4094F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.degreeVec(-22.0992F, -42.0794F, 3.3353F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(-23.2199F, -44.8192F, -0.0821F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.degreeVec(-23.7392F, -47.366F, -3.8109F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(-23.6493F, -49.6514F, -7.793F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.25F, KeyframeAnimations.degreeVec(-22.9968F, -51.6246F, -11.9377F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.degreeVec(-20.4443F, -54.5491F, -20.1948F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(-18.8523F, -55.5196F, -23.996F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(-17.2735F, -56.2093F, -27.3672F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-15.8615F, -56.6691F, -30.1674F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(-14.7441F, -56.9514F, -32.2791F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-14.0213F, -57.1012F, -33.6056F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(-2.6612F, -6.1615F, -11.9505F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(-2.0332F, -5.7036F, -11.4645F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(-0.5991F, -4.6716F, -9.9518F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(0.8313F, -3.688F, -7.3832F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(1.5571F, -3.2993F, -4.2045F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(1.4076F, -3.6333F, -1.287F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(0.8217F, -4.3026F, 0.6008F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(0.4316F, -4.7312F, 1.2045F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(0.2141F, -4.9667F, 1.4675F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(0.0823F, -5.1186F, 1.448F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0195F, -5.2262F, 0.861F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(0.0011F, -5.3363F, -0.586F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -4.1149F, -6.966F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, -12.0125F, -27.8064F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -14.1589F, -29.1576F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -14.343F, -29.2926F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(0.0F, -14.4977F, -29.4062F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(0.0F, -14.6255F, -29.5F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(0.0F, -14.7291F, -29.576F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -14.8737F, -29.6821F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, -14.9519F, -29.7395F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.posVec(0.0F, -14.9842F, -29.7631F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.posVec(-0.0036F, -14.9812F, -29.7636F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(-0.0291F, -14.913F, -29.732F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.posVec(-0.0781F, -14.7813F, -29.6706F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(-0.1486F, -14.5909F, -29.5811F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.posVec(-0.2386F, -14.3464F, -29.4645F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.posVec(-0.3458F, -14.0524F, -29.3221F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(-0.4681F, -13.7134F, -29.1547F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.posVec(-0.603F, -13.3343F, -28.9632F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.posVec(-0.7482F, -12.9201F, -28.7487F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.posVec(-0.901F, -12.4757F, -28.5124F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.posVec(-1.2189F, -11.5193F, -27.9803F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.0F, KeyframeAnimations.posVec(-1.5363F, -10.5121F, -27.3839F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.posVec(-1.8341F, -9.5045F, -26.748F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.posVec(-2.0971F, -8.548F, -26.1062F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.25F, KeyframeAnimations.posVec(-2.2121F, -8.1048F, -25.7958F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.posVec(-2.3148F, -7.6931F, -25.4998F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.posVec(-2.4048F, -7.3188F, -25.2242F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.posVec(-2.4816F, -6.9873F, -24.975F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.posVec(-2.5451F, -6.7042F, -24.7582F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(-2.5951F, -6.4749F, -24.5799F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.posVec(-2.6314F, -6.3044F, -24.4458F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.posVec(-2.6536F, -6.1982F, -24.3615F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.posVec(-2.6612F, -6.1615F, -24.3323F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(-53.4589F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.degreeVec(-43.9329F, -12.629F, -313.2696F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.degreeVec(-16.0493F, -13.2658F, -306.9101F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.degreeVec(30.926F, -24.2388F, -298.4435F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.degreeVec(98.5887F, -46.0796F, -308.2567F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(176.4549F, -48.8826F, -344.7932F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.degreeVec(217.105F, -37.6281F, -361.4825F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.degreeVec(230.4319F, -31.5741F, -364.9353F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.degreeVec(236.2095F, -28.6498F, -366.1038F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.degreeVec(239.2536F, -26.9757F, -366.3266F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(240.3522F, -26.1518F, -365.2406F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.degreeVec(240.0714F, -25.7789F, -362.3362F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.degreeVec(201.7869F, -19.8551F, -343.3278F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.degreeVec(25.6272F, 21.5094F, -374.5008F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.degreeVec(8.4803F, 16.436F, -379.9236F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.degreeVec(7.8945F, 16.228F, -380.1617F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.degreeVec(7.7902F, 16.1076F, -380.2673F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.degreeVec(7.6002F, 16.0402F, -380.2865F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.degreeVec(6.675F, 15.8068F, -380.2799F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.degreeVec(6.1428F, 15.6488F, -379.6902F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.degreeVec(5.4909F, 15.4238F, -378.5167F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(4.7024F, 15.1134F, -376.807F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.degreeVec(3.7526F, 14.693F, -374.6094F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.degreeVec(1.239F, 13.4153F, -368.9613F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(-2.3327F, 11.416F, -362.0399F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.degreeVec(-7.1952F, 8.6511F, -354.3805F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.degreeVec(-13.3881F, 5.2553F, -346.5355F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.degreeVec(-24.5418F, -0.4054F, -335.484F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.degreeVec(-32.3881F, -4.1002F, -329.119F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.degreeVec(-39.7611F, -7.4248F, -323.8149F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.degreeVec(-46.0199F, -10.1762F, -319.6896F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.degreeVec(-48.5594F, -11.2814F, -318.0946F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.degreeVec(-50.626F, -12.1781F, -316.8253F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.degreeVec(-52.1657F, -12.8452F, -315.895F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.degreeVec(-53.1268F, -13.2613F, -315.3203F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.degreeVec(-53.4588F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR)
                ))
                .addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.POSITION,
                        new Keyframe(0.0F, KeyframeAnimations.posVec(4.7002F, 0.7937F, 2.8818F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.05F, KeyframeAnimations.posVec(4.6861F, 0.7173F, 2.9593F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.1F, KeyframeAnimations.posVec(4.5322F, 0.4079F, 3.1888F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.15F, KeyframeAnimations.posVec(4.0273F, -0.2986F, 3.4914F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.2F, KeyframeAnimations.posVec(3.0908F, -1.4522F, 3.6272F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(1.9263F, -2.8388F, 3.376F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.3F, KeyframeAnimations.posVec(0.9356F, -4.0288F, 2.8075F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.35F, KeyframeAnimations.posVec(0.4582F, -4.6207F, 2.2748F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.4F, KeyframeAnimations.posVec(0.2202F, -4.919F, 1.9797F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.45F, KeyframeAnimations.posVec(0.0832F, -5.1017F, 1.641F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0195F, -5.2223F, 0.9063F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.55F, KeyframeAnimations.posVec(0.0011F, -5.3361F, -0.5834F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -4.1149F, -6.966F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, -12.0125F, -27.8064F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -14.1589F, -29.1576F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.75F, KeyframeAnimations.posVec(0.0F, -14.343F, -29.2926F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.8F, KeyframeAnimations.posVec(0.0F, -14.4977F, -29.4062F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.85F, KeyframeAnimations.posVec(0.0F, -14.6255F, -29.5F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(0.9F, KeyframeAnimations.posVec(0.0F, -14.7291F, -29.576F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -14.8737F, -29.6821F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.1F, KeyframeAnimations.posVec(0.0F, -14.9519F, -29.7395F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.2F, KeyframeAnimations.posVec(0.0F, -14.9842F, -29.7631F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.3F, KeyframeAnimations.posVec(0.0F, -14.9908F, -29.768F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.35F, KeyframeAnimations.posVec(-0.0036F, -14.9652F, -29.751F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.4F, KeyframeAnimations.posVec(-0.0284F, -14.7851F, -29.6303F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.45F, KeyframeAnimations.posVec(-0.0736F, -14.4392F, -29.3945F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.5F, KeyframeAnimations.posVec(-0.1324F, -13.9434F, -29.0473F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.55F, KeyframeAnimations.posVec(-0.1966F, -13.3146F, -28.5907F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.6F, KeyframeAnimations.posVec(-0.257F, -12.5705F, -28.0262F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.65F, KeyframeAnimations.posVec(-0.3035F, -11.7301F, -27.3556F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.7F, KeyframeAnimations.posVec(-0.3264F, -10.8131F, -26.5818F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.75F, KeyframeAnimations.posVec(-0.3162F, -9.8403F, -25.7095F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.8F, KeyframeAnimations.posVec(-0.2645F, -8.8326F, -24.7456F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.85F, KeyframeAnimations.posVec(-0.1645F, -7.8111F, -23.6996F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.9F, KeyframeAnimations.posVec(-0.0114F, -6.7962F, -22.5838F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(1.95F, KeyframeAnimations.posVec(0.197F, -5.807F, -21.4132F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.0F, KeyframeAnimations.posVec(0.4603F, -4.8604F, -20.2049F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.05F, KeyframeAnimations.posVec(0.7752F, -3.9713F, -18.9779F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.1F, KeyframeAnimations.posVec(1.1357F, -3.151F, -17.7522F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.15F, KeyframeAnimations.posVec(1.533F, -2.408F, -16.5482F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.2F, KeyframeAnimations.posVec(1.9564F, -1.7473F, -15.386F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.25F, KeyframeAnimations.posVec(2.3934F, -1.1709F, -14.2849F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.3F, KeyframeAnimations.posVec(2.8302F, -0.678F, -13.2625F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.35F, KeyframeAnimations.posVec(3.2524F, -0.2654F, -12.3351F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.4F, KeyframeAnimations.posVec(3.6455F, 0.0713F, -11.5173F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.45F, KeyframeAnimations.posVec(3.9951F, 0.3377F, -10.8221F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.5F, KeyframeAnimations.posVec(4.2876F, 0.5392F, -10.2614F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.55F, KeyframeAnimations.posVec(4.5102F, 0.6806F, -9.8465F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.6F, KeyframeAnimations.posVec(4.6511F, 0.7652F, -9.5888F), AnimationChannel.Interpolations.LINEAR),
                        new Keyframe(2.65F, KeyframeAnimations.posVec(4.7002F, 0.7937F, -9.4999F), AnimationChannel.Interpolations.LINEAR)
                ))
                .build();

	public static final AnimationDefinition SWORD_WALK = AnimationDefinition.Builder.withLength(1.0F)
		.looping()
		.addAnimation("Root", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.9625F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(0.0F, -2.3489F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.posVec(0.0F, -2.3323F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, -2.2267F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.posVec(0.0F, -2.0229F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.posVec(0.0F, -1.9791F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, -1.9625F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, -2.3489F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.posVec(0.0F, -2.3323F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.posVec(0.0F, -2.2885F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.posVec(0.0F, -2.0229F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.posVec(0.0F, -1.9791F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, -1.9625F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5199F, 11.4862F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(2.1928F, 29.0877F, 11.3916F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(1.9075F, 27.9129F, 11.1412F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(1.5022F, 26.1773F, 10.7883F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-0.3137F, 17.3301F, 9.2554F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-0.6318F, 15.5838F, 8.9967F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(-0.8413F, 14.3997F, 8.8283F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0.9172F, 13.9636F, 8.7677F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(-0.8413F, 14.3997F, 8.8283F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-0.6318F, 15.5838F, 8.9967F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(-0.3137F, 17.3301F, 9.2554F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(1.5022F, 26.1773F, 10.7883F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(1.9075F, 27.9129F, 11.1412F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(2.1928F, 29.0877F, 11.3916F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.3002F, 29.5199F, 11.4862F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(25.0629F, 13.4718F, 9.9566F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(23.3527F, 8.3536F, 9.3486F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(23.1341F, 7.6806F, 9.2809F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(23.3527F, 8.3536F, 9.3486F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(25.0629F, 13.4718F, 9.9566F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(25.2941F, 14.1422F, 10.0491F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-26.4736F, -47.9903F, 0.7049F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-26.1582F, -47.3539F, 0.2698F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-25.3532F, -45.6148F, -0.8631F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(-24.2924F, -43.0245F, -2.4168F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-23.1799F, -39.8434F, -4.143F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(-21.3009F, -32.8333F, -7.419F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-20.6406F, -29.5937F, -8.751F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-20.1768F, -26.9256F, -9.7829F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(-19.8992F, -25.1175F, -10.4538F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-19.8041F, -24.4522F, -10.6956F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(-19.8992F, -25.1175F, -10.4538F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-20.1768F, -26.9256F, -9.7829F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(-20.6406F, -29.5937F, -8.751F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(-21.3009F, -32.8333F, -7.419F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-23.1799F, -39.8434F, -4.143F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(-24.2924F, -43.0245F, -2.4168F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(-25.3532F, -45.6148F, -0.8631F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(-26.1581F, -47.3539F, 0.2698F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-26.4736F, -47.9903F, 0.7049F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_1_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-25.3345F, -4.3104F, -39.3684F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-28.7533F, 1.7939F, -40.0226F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-29.2076F, 2.5915F, -40.1355F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-28.7533F, 1.7939F, -40.0226F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(-25.3345F, -4.3104F, -39.3684F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-24.8893F, -5.1138F, -39.3094F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_2_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_3_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_4_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_5_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_6_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.6861F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_7_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(11.686F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("Tool_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-93.1896F, -3.1011F, -179.9999F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_1_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.1964F, 10.7961F, 32.0538F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_2_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_3_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_4_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_5_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0582F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_6_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_7_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(30.0583F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("Tool_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-95.8504F, 5.8509F, -164.4004F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg_up_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-180.0F, 13.4603F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-180.0F, 10.7195F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-180.0F, 8.4794F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(-180.0F, 0.599F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-180.0F, -15.7388F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-180.0F, -34.8282F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(-180.0F, -53.4264F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-180.0F, -67.4036F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-180.0F, -73.6588F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(-180.0F, -60.965F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-180.0F, -42.3692F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-180.0F, -47.9753F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(-180.0F, -48.8785F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(-180.0F, -45.6459F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-180.0F, -33.7024F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-180.0F, -15.1858F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(-180.0F, 4.3097F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(-180.0F, 16.0074F, -180.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-180.0F, 13.4603F, -180.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg_down_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.4155F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 29.0233F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 33.7251F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 44.4565F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 63.4042F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 79.8096F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 89.3237F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 90.2753F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 84.2935F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 59.6938F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 29.0486F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 33.8125F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 38.0057F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 46.2863F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 58.4637F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 62.2839F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 53.8661F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 35.8015F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 20.6488F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 21.6394F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 23.4155F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg_up_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -41.6136F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, -47.2198F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, -48.123F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, -44.8904F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, -32.9469F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, -14.4303F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F, 5.0652F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 16.7629F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 14.2158F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 11.475F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 9.2349F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 1.3545F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, -14.9833F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, -34.0727F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, -52.6709F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, -66.6481F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, -72.9033F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, -60.2095F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -41.6137F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("leg_down_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -29.0485F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -33.8125F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -38.0057F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -46.2863F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -58.4637F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -62.2839F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -53.8662F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -35.8015F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -20.6488F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -21.6394F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -23.4155F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -29.0233F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -33.7251F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -44.4565F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -63.4042F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -79.8096F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -89.3237F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.2753F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -84.2935F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -59.6938F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -29.0486F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-14.5676F, -56.4064F, -33.5263F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-16.6115F, -54.3744F, -32.2013F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(-19.3307F, -51.3388F, -30.5657F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-25.099F, -43.4948F, -27.6897F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-29.8442F, -35.5431F, -26.0761F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-31.5414F, -32.3974F, -25.6898F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(-32.6535F, -30.2632F, -25.4943F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-33.0564F, -29.4773F, -25.4349F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(-32.6535F, -30.2632F, -25.4943F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-31.5414F, -32.3974F, -25.6898F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.degreeVec(-29.8442F, -35.5431F, -26.0761F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-25.099F, -43.4948F, -27.6898F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(-19.3307F, -51.3388F, -30.5657F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(-16.6115F, -54.3744F, -32.2013F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(-14.5676F, -56.4064F, -33.5263F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-13.7672F, -57.1481F, -34.0652F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_s_L", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(-2.6612F, -6.4849F, -11.9505F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(-2.5387F, -6.6715F, -11.8608F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(-2.2108F, -6.8463F, -11.61F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.posVec(-1.7408F, -6.801F, -11.2221F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.posVec(-1.1928F, -6.7203F, -10.7226F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.posVec(-0.627F, -6.6156F, -10.1455F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.posVec(-0.0952F, -6.4994F, -9.5354F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.posVec(0.3614F, -6.385F, -8.9478F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.posVec(0.7133F, -6.286F, -8.4461F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.posVec(0.9392F, -6.2165F, -8.0971F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.posVec(1.0198F, -6.1903F, -7.9668F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.posVec(0.9392F, -6.3931F, -8.0971F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.posVec(0.7133F, -6.612F, -8.4461F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.posVec(0.3614F, -6.6325F, -8.9478F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.posVec(-0.0952F, -6.6322F, -9.5354F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.posVec(-0.627F, -6.6156F, -10.1455F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.posVec(-1.1928F, -6.5875F, -10.7226F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.posVec(-1.7408F, -6.5535F, -11.2221F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.posVec(-2.2108F, -6.5203F, -11.61F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.posVec(-2.5387F, -6.4949F, -11.8608F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.posVec(-2.6612F, -6.4849F, -11.9505F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-53.4589F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.degreeVec(-53.0001F, -12.9571F, -315.2707F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(-51.7633F, -11.7351F, -315.6529F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.degreeVec(-49.9618F, -9.9197F, -316.1642F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.degreeVec(-41.1027F, -0.5345F, -317.8456F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-39.3882F, 1.3333F, -318.0027F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.degreeVec(-38.227F, 2.6009F, -318.0772F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-37.7994F, 3.0677F, -318.0981F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.degreeVec(-38.227F, 2.6009F, -318.0772F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-39.3882F, 1.3333F, -318.0028F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.degreeVec(-43.1935F, -2.7992F, -317.5785F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.degreeVec(-49.9618F, -9.9197F, -316.1642F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.degreeVec(-51.7633F, -11.7351F, -315.6529F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.degreeVec(-53.0001F, -12.9571F, -315.2707F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(-53.4588F, -13.4051F, -315.1227F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("arm_s_R", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(4.7002F, 0.4703F, 2.8818F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.05F, KeyframeAnimations.posVec(4.6546F, 0.2723F, 2.8102F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.posVec(4.5349F, 0.0658F, 2.6134F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.15F, KeyframeAnimations.posVec(4.3698F, 0.0624F, 2.3171F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.posVec(4.1879F, 0.0813F, 1.9486F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.posVec(4.0137F, 0.1155F, 1.5379F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.posVec(3.8648F, 0.1583F, 1.1189F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.35F, KeyframeAnimations.posVec(3.7507F, 0.2032F, 0.7281F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.posVec(3.6733F, 0.2434F, 0.4032F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.45F, KeyframeAnimations.posVec(3.6294F, 0.2726F, 0.1815F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.posVec(3.615F, 0.2838F, 0.0996F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.55F, KeyframeAnimations.posVec(3.6294F, 0.096F, 0.1815F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.posVec(3.6733F, -0.0825F, 0.4032F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.65F, KeyframeAnimations.posVec(3.7507F, -0.0444F, 0.7281F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7F, KeyframeAnimations.posVec(3.8648F, 0.0255F, 1.1189F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.posVec(4.0137F, 0.1155F, 1.5379F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.posVec(4.1879F, 0.2141F, 1.9486F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.85F, KeyframeAnimations.posVec(4.3698F, 0.3099F, 2.3171F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9F, KeyframeAnimations.posVec(4.5349F, 0.3918F, 2.6134F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.95F, KeyframeAnimations.posVec(4.6546F, 0.4488F, 2.8102F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.posVec(4.7002F, 0.4703F, 2.8818F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

}
