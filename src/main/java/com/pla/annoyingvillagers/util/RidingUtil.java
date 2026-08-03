package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.pathfinder.Path;

import java.util.List;
import java.util.Random;


public class RidingUtil {
    public static boolean hasUsableMountedMob(Mob rider) {
        return mountedNavigationMob(rider) != null;
    }

    public static Mob navigationMobOrSelf(Mob rider) {
        Mob mount = mountedNavigationMob(rider);
        return mount == null ? rider : mount;
    }

    public static Path createNavigationPath(Mob rider, LivingEntity target) {
        if (target == null) {
            return null;
        }

        Mob navigationMob = navigationMobOrSelf(rider);
        if (rider.isPassenger() && navigationMob == rider) {
            return null;
        }

        return navigationMob.getNavigation().createPath(target, 0);
    }

    public static Path createNavigationPath(Mob rider, BlockPos pos) {
        if (pos == null) {
            return null;
        }

        Mob navigationMob = navigationMobOrSelf(rider);
        if (rider.isPassenger() && navigationMob == rider) {
            return null;
        }

        return navigationMob.getNavigation().createPath(pos, 0);
    }

    public static boolean moveToPath(Mob rider, Path path, double speedModifier) {
        if (path == null) {
            return false;
        }

        return navigationMobOrSelf(rider).getNavigation().moveTo(path, speedModifier);
    }

    public static boolean moveTo(Mob rider, LivingEntity target, double speedModifier) {
        if (target == null) {
            return false;
        }

        Mob navigationMob = navigationMobOrSelf(rider);
        if (rider.isPassenger() && navigationMob == rider) {
            return false;
        }

        return navigationMob.getNavigation().moveTo(target, speedModifier);
    }

    public static boolean moveTo(Mob rider, BlockPos pos, double speedModifier) {
        return moveToPath(rider, createNavigationPath(rider, pos), speedModifier);
    }

    public static boolean moveTo(Mob rider, double x, double y, double z, double speedModifier) {
        Mob navigationMob = navigationMobOrSelf(rider);
        if (rider.isPassenger() && navigationMob == rider) {
            return false;
        }

        return navigationMob.getNavigation().moveTo(x, y, z, speedModifier);
    }

    public static boolean isNavigationDone(Mob rider) {
        Mob navigationMob = navigationMobOrSelf(rider);
        if (rider.isPassenger() && navigationMob == rider) {
            return true;
        }

        return navigationMob.getNavigation().isDone();
    }

    public static void stopNavigation(Mob rider) {
        rider.getNavigation().stop();
        Mob mount = mountedNavigationMob(rider);
        if (mount != null) {
            mount.getNavigation().stop();
        }
    }

    public static void lookAtTarget(Mob rider, LivingEntity target, float maxYawChange, float maxPitchChange) {
        rider.getLookControl().setLookAt(target, maxYawChange, maxPitchChange);
        Mob mount = mountedNavigationMob(rider);
        if (mount != null) {
            mount.getLookControl().setLookAt(target, maxYawChange, maxPitchChange);
            mount.lookAt(target, maxYawChange, maxPitchChange);
        }
    }

    public static void rideRandomAnimal(ServerLevel serverLevel, Entity entity) {
        List<EntityType<? extends LivingEntity>> pool = List.of(
                EntityType.HORSE, EntityType.DONKEY, EntityType.CAMEL, EntityType.POLAR_BEAR, EntityType.COW
        );
        Random rand = new Random();
        EntityType<? extends LivingEntity> type = pool.get(rand.nextInt(pool.size()));

        LivingEntity mount = type.create(serverLevel);
        if (mount != null) {
            mount.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
            ((Mob) mount).setPersistenceRequired();
            ((Mob) mount).finalizeSpawn(
                    serverLevel,
                    serverLevel.getCurrentDifficultyAt(entity.blockPosition()),
                    MobSpawnType.MOB_SUMMONED,
                    null,
                    null
            );
            if (entity instanceof VillagerScoutEntity || entity instanceof VillagerScoutCaptainEntity
                    || entity instanceof RedVillagerKnightEntity || entity instanceof BlueVillagerKnightEntity
                    || entity instanceof GreenVillagerKnightEntity || entity instanceof PurpleVillagerKnightEntity) {
                TeamUtil.addOrJoinTeam(mount, "villagers");
            }
            serverLevel.addFreshEntity(mount);
            entity.startRiding(mount);
            mount.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 99999, new Random().nextInt(1, 3), false, false));
            mount.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 99999, 1, false, false));
            mount.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 99999,  new Random().nextInt(1, 9), false, false));
        }
    }

    private static Mob mountedNavigationMob(Mob rider) {
        if (!rider.isPassenger() || !(rider.getVehicle() instanceof Mob mount)) {
            return null;
        }
        if (!mount.isAlive() || mount.isRemoved() || mount.isNoAi()) {
            return null;
        }

        return mount;
    }
}
