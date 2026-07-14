package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BowFunction {
    public static boolean hasClearShot(LivingEntity shooter, LivingEntity target) {
        if (target == null || !target.isAlive() || shooter.level() != target.level()) {
            return false;
        }

        return hasClearShotFrom(shooter.level(), shooter, shooter.getEyePosition(), target);
    }

    public static boolean hasClearShotFrom(Level level, Entity clipOwner, Vec3 from, LivingEntity target) {
        if (target == null || !target.isAlive() || level != target.level()) {
            return false;
        }

        Vec3 eye = target.getEyePosition();
        Vec3 body = new Vec3(target.getX(), target.getY(0.5D), target.getZ());

        return hasClearPath(level, clipOwner, from, eye)
                || hasClearPath(level, clipOwner, from, body);
    }

    private static boolean hasClearPath(Level level, Entity clipOwner, Vec3 from, Vec3 to) {
        return level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, clipOwner))
                .getType() == HitResult.Type.MISS;
    }
}
