package com.pla.annoyingvillagers.rig;

import net.minecraft.world.entity.LivingEntity;

public final class RigBowAnimationSelector {
    private static final float DOWN_PITCH_DEGREES = 18.0F;
    private static final float UP_PITCH_DEGREES = -18.0F;

    private RigBowAnimationSelector() {
    }

    public static RigAnimationId aimForPitch(float pitchDegrees) {
        if (pitchDegrees >= DOWN_PITCH_DEGREES) {
            return RigAnimationId.BOW_AIM_DOWN;
        }
        if (pitchDegrees <= UP_PITCH_DEGREES) {
            return RigAnimationId.BOW_AIM_UP;
        }

        return RigAnimationId.BOW_AIM_MID;
    }

    public static RigAnimationId shotForPitch(float pitchDegrees) {
        if (pitchDegrees >= DOWN_PITCH_DEGREES) {
            return RigAnimationId.BOW_SHOT_DOWN;
        }
        if (pitchDegrees <= UP_PITCH_DEGREES) {
            return RigAnimationId.BOW_SHOT_UP;
        }

        return RigAnimationId.BOW_SHOT_MID;
    }

    public static RigAnimationId shotForTarget(LivingEntity shooter, LivingEntity target) {
        double x = target.getX() - shooter.getX();
        double y = target.getEyeY() - shooter.getEyeY();
        double z = target.getZ() - shooter.getZ();
        double horizontalDistance = Math.sqrt(x * x + z * z);
        if (horizontalDistance < 1.0E-4D) {
            return shotForPitch(shooter.getXRot());
        }

        float pitchDegrees = (float) -Math.toDegrees(Math.atan2(y, horizontalDistance));
        return shotForPitch(pitchDegrees);
    }
}
