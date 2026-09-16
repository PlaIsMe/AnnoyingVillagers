package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

/** Animation-system hooks for dangerous reactions; standalone AV uses rig animations. */
public final class DangerousReactionAnimations {
    private DangerousReactionAnimations() {}

    public static boolean isDangerous(LivingEntity entity) {
        return entity instanceof Mob mob && RigAnimationController.isDangerous(mob);
    }

    public static Object animationKey(LivingEntity entity) {
        return entity instanceof Mob mob ? RigAnimationController.getActiveAnimationId(mob) : null;
    }

    public static int animationStartTick(LivingEntity entity) {
        return entity instanceof Mob mob ? RigAnimationController.getActiveAnimationStartTick(mob) : -1;
    }

    public static boolean isStunned(Mob mob) {
        return RigStunController.isStunned(mob);
    }

    public static boolean isBusy(Mob mob) {
        return RigAnimationController.hasActiveAnimation(mob);
    }

    public static boolean isPlaying(Mob mob, RigAnimationId animation) {
        return RigAnimationController.getActiveAnimationId(mob) == animation;
    }

    public static void play(Mob mob, RigAnimationId animation) {
        RigAnimationController.play(mob, animation);
    }
}
