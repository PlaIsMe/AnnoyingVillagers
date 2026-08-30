package com.pla.annoyingvillagers.client.particle;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.function.BiFunction;

public class HitParticleType extends SimpleParticleType {
    public static final BiFunction<Entity, Entity, Vec3> CENTER_OF_TARGET = (target, attacker) -> {
        EntityDimensions dimensions = target.getDimensions(target.getPose());
        return new Vec3(target.getX(), target.getY() + dimensions.height * 0.5D, target.getZ());
    };
    public static final BiFunction<Entity, Entity, Vec3> RANDOM_WITHIN_BOUNDING_BOX = (target, attacker) -> {
        AABB boundingBox = target.getBoundingBox();
        Random random = new Random();
        double x = boundingBox.minX + random.nextDouble() * boundingBox.getXsize();
        double y = boundingBox.minY + random.nextDouble() * boundingBox.getYsize();
        double z = boundingBox.minZ + random.nextDouble() * boundingBox.getZsize();
        return new Vec3(x, y, z);
    };
    public static final BiFunction<Entity, Entity, Vec3> FRONT_OF_EYES = (target, attacker) -> {
        Vec3 eyePosition = target.getEyePosition();
        Vec3 lookAngle = target.getLookAngle().scale(2.0D);
        return eyePosition.add(lookAngle);
    };
    public static final BiFunction<Entity, Entity, Vec3> MIDDLE_OF_ENTITIES = (target, attacker) -> {
        Vec3 targetCenter = target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
        Vec3 attackerCenter = attacker.position().add(0.0D, attacker.getBbHeight() * 0.5D, 0.0D);
        return targetCenter.add(attackerCenter.subtract(targetCenter).scale(0.5D));
    };
    public static final BiFunction<Entity, Entity, Vec3> ZERO = (target, attacker) -> Vec3.ZERO;
    public static final BiFunction<Entity, Entity, Vec3> ATTACKER_XY_ROTATION =
            (target, attacker) -> new Vec3(attacker.getXRot(), 180.0F - attacker.getYRot(), -1.0D);
    public static final BiFunction<Entity, Entity, Vec3> ATTACKER_Y_ROTATION =
            (target, attacker) -> new Vec3(90.0D, attacker.getYRot(), -1.0D);

    public BiFunction<Entity, Entity, Vec3> positionProvider;
    public BiFunction<Entity, Entity, Vec3> argumentProvider;

    public HitParticleType(boolean overrideLimiter) {
        this(overrideLimiter, CENTER_OF_TARGET, ZERO);
    }

    public HitParticleType(
            boolean overrideLimiter,
            BiFunction<Entity, Entity, Vec3> positionProvider,
            BiFunction<Entity, Entity, Vec3> argumentProvider
    ) {
        super(overrideLimiter);
        this.positionProvider = positionProvider;
        this.argumentProvider = argumentProvider;
    }

    public void spawnParticleWithArgument(ServerLevel serverLevel, Entity target, Entity attacker) {
        this.spawnParticleWithArgument(serverLevel, null, null, target, attacker);
    }

    public void spawnParticleWithArgument(
            ServerLevel serverLevel,
            BiFunction<Entity, Entity, Vec3> positionProvider,
            BiFunction<Entity, Entity, Vec3> argumentProvider,
            Entity target,
            Entity attacker
    ) {
        Vec3 position = (positionProvider == null ? this.positionProvider : positionProvider).apply(target, attacker);
        Vec3 argument = (argumentProvider == null ? this.argumentProvider : argumentProvider).apply(target, attacker);
        serverLevel.sendParticles(
                this,
                position.x,
                position.y,
                position.z,
                0,
                argument.x,
                argument.y,
                argument.z,
                1.0D
        );
    }

    public void spawnParticleWithArgument(
            ServerLevel serverLevel,
            double x,
            double y,
            double z,
            double xArgument,
            double yArgument,
            double zArgument
    ) {
        serverLevel.sendParticles(this, x, y, z, 0, xArgument, yArgument, zArgument, 1.0D);
    }
}
