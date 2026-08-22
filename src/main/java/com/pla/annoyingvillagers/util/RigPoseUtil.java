package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigColliderAnchor;
import com.pla.annoyingvillagers.rig.RigPart;
import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import com.pla.annoyingvillagers.rig.pose.RigPoseSampler;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public final class RigPoseUtil {
    private RigPoseUtil() {}

    public static Vec3 getPartPosition(Mob mob, RigAnimationId animationId, float elapsedTicks, RigPart part) {
        return getPartTransform(mob, animationId, elapsedTicks, part).origin();
    }

    public static Vec3 getPartPosition(Mob mob, RigAnimationId animationId, float elapsedTicks, RigPart part, Vec3 localOffset) {
        RigPartTransform transform = getPartTransform(mob, animationId, elapsedTicks, part);
        return transform.transformPoint(localOffset == null ? Vec3.ZERO : localOffset);
    }

    public static Vec3 getPartPosition(Mob mob, RigAnimationId animationId, float elapsedTicks, RigPart part, Vec3 translation, double forwardOffset, double yOffset) {
        RigPartTransform transform = getPartTransform(mob, animationId, elapsedTicks, part);
        Vec3 localOffset = translation == null ? Vec3.ZERO : translation;
        if (forwardOffset != 0.0D) localOffset = localOffset.add(0.0D, 0.0D, -forwardOffset);
        return transform.transformPoint(localOffset).add(0.0D, yOffset, 0.0D);
    }

    public static Vec3 getRightWeaponPosition(Mob mob, RigAnimationId animationId, float elapsedTicks) {
        return getPartPosition(mob, animationId, elapsedTicks, RigPart.RIGHT_WEAPON);
    }

    public static Vec3 getLeftWeaponPosition(Mob mob, RigAnimationId animationId, float elapsedTicks) {
        return getPartPosition(mob, animationId, elapsedTicks, RigPart.LEFT_WEAPON);
    }

    public static Vec3 getRightHandPosition(Mob mob, RigAnimationId animationId, float elapsedTicks) {
        return getPartPosition(mob, animationId, elapsedTicks, RigPart.RIGHT_HAND);
    }

    public static Vec3 getLeftHandPosition(Mob mob, RigAnimationId animationId, float elapsedTicks) {
        return getPartPosition(mob, animationId, elapsedTicks, RigPart.LEFT_HAND);
    }

    public static Vec3 getRightWeaponPosition(Mob mob, RigAnimationId animationId, float elapsedTicks, double forwardOffset) {
        return getPartPosition(mob, animationId, elapsedTicks, RigPart.RIGHT_WEAPON, Vec3.ZERO, forwardOffset, 0.0D);
    }

    public static Vec3 getLeftWeaponPosition(Mob mob, RigAnimationId animationId, float elapsedTicks, double forwardOffset) {
        return getPartPosition(mob, animationId, elapsedTicks, RigPart.LEFT_WEAPON, Vec3.ZERO, forwardOffset, 0.0D);
    }

    private static RigPartTransform getPartTransform(Mob mob, RigAnimationId animationId, float elapsedTicks, RigPart part) {
        return RigPoseSampler.sample(mob, animationId, elapsedTicks, toAnchor(part));
    }

    public static void debugPartParticles(Mob mob, RigAnimationId animationId, float elapsedTicks, RigPart part) {
        if (!(mob.level() instanceof ServerLevel serverLevel)) return;

        Vec3 pos = getPartPosition(mob, animationId, elapsedTicks, part);
        if (pos == null) return;

        serverLevel.sendParticles(ParticleTypes.FLAME, pos.x, pos.y, pos.z, 40, 0.08D, 0.08D, 0.08D, 0.01D);
    }

    private static RigColliderAnchor toAnchor(RigPart part) {
        return switch (part) {
            case BODY -> RigColliderAnchor.BODY;
            case HEAD -> RigColliderAnchor.HEAD;
            case RIGHT_ARM -> RigColliderAnchor.RIGHT_ARM;
            case RIGHT_HAND -> RigColliderAnchor.RIGHT_HAND;
            case RIGHT_WEAPON -> RigColliderAnchor.RIGHT_TOOL;
            case LEFT_ARM -> RigColliderAnchor.LEFT_ARM;
            case LEFT_HAND -> RigColliderAnchor.LEFT_HAND;
            case LEFT_WEAPON -> RigColliderAnchor.LEFT_TOOL;
            case RIGHT_LEG -> RigColliderAnchor.RIGHT_LEG;
            case RIGHT_LOWER_LEG -> RigColliderAnchor.RIGHT_LOWER_LEG;
            case LEFT_LEG -> RigColliderAnchor.LEFT_LEG;
            case LEFT_LOWER_LEG -> RigColliderAnchor.LEFT_LOWER_LEG;
        };
    }
}