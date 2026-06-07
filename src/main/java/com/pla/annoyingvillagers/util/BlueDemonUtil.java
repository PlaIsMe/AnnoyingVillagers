package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.network.ClientboundBlueDemonEffectFx;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public final class BlueDemonUtil {
    private BlueDemonUtil() {
    }

    public static void spawnBlueDemonEffect(Level level, Entity entity) {
        if (entity == null) {
            return;
        }

        spawnBlueDemonEffect(level, entity, entity.position(), 1, 0.3D, 1.2D, 0.3D, 0.0D);
    }

    public static void spawnBlueDemonChestplateEffect(Level level, Entity entity) {
        if (entity == null) {
            return;
        }

        sendBlueDemonEffect(level, entity, true, entity.position(), 1, 0.3D, 1.2D, 0.3D, 0.0D);
    }

    public static void spawnBlueDemonEffect(Level level, Entity trackingEntity, Vec3 pos,
                                           int count, double xOffset, double yOffset, double zOffset, double speed) {
        sendBlueDemonEffect(level, trackingEntity, false, pos, count, xOffset, yOffset, zOffset, speed);
    }

    private static void sendBlueDemonEffect(Level level, Entity trackingEntity, boolean followEntity, Vec3 pos,
                                           int count, double xOffset, double yOffset, double zOffset, double speed) {
        if (!(level instanceof ServerLevel) || trackingEntity == null || pos == null) {
            return;
        }

        AnnoyingVillagers.PACKET_HANDLER.send(
                PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> trackingEntity),
                new ClientboundBlueDemonEffectFx(trackingEntity.getId(), trackingEntity.tickCount, followEntity,
                        pos, count, xOffset, yOffset, zOffset, speed)
        );
    }
}
