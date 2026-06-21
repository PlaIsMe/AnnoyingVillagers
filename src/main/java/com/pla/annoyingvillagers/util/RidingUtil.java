package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.entity.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;

import java.util.List;
import java.util.Random;


public class RidingUtil {
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
}
