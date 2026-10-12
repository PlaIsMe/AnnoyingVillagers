package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.network.CPApplyShake;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class ScreenShakeUtil {
    public static void applyScreenShake(ServerPlayer player, int durationTicks, int amplifier) {
        if (!player.isAlive() || player.isSpectator()) return;
        PacketDistributor.sendToPlayer(player, new CPApplyShake(durationTicks, amplifier, (float) durationTicks / 10, 1));
    }

    public static void applyScreenShake(ServerLevel level, Vec3 center, double range,
                                        int durationTicks, int amplifier) {
        double rangeSq = range * range;
        AABB box = new AABB(center, center).inflate(range);

        for (Player player : level.getEntitiesOfClass(Player.class, box,
                player -> player.isAlive() && !player.isSpectator())) {

            double distSq = player.distanceToSqr(center.x, center.y, center.z);
            if (distSq > rangeSq) continue;

            PacketDistributor.sendToPlayersTrackingEntityAndSelf(player, new CPApplyShake(durationTicks, amplifier, (float) durationTicks / 10, 1));
        }
    }
}
