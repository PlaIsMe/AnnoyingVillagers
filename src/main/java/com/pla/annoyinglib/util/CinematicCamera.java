package com.pla.annoyinglib.util;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.network.ClientboundCinematicCamera;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

/** Server-side presentation API. It never teleports, freezes or grants protection to a player. */
public final class CinematicCamera {
    private CinematicCamera() {}

    public static void focusNearby(Entity subject, Vec3 focus, float facingYaw, double radius, int durationTicks) {
        focusNearby(subject, focus, facingYaw, radius, durationTicks, Math.max(1, Math.round(durationTicks * 0.18F)));
    }

    /** Explicit approach duration lets an event open its portal only after the camera reaches its shot. */
    public static void focusNearby(Entity subject, Vec3 focus, float facingYaw, double radius, int durationTicks, int approachTicks) {
        if (!(subject.level() instanceof ServerLevel level) || !subject.isAlive()
                || !Double.isFinite(focus.x) || !Double.isFinite(focus.y) || !Double.isFinite(focus.z)
                || !Float.isFinite(facingYaw) || !Double.isFinite(radius)) return;
        double range = Mth.clamp(radius, 1.0D, 64.0D);
        ClientboundCinematicCamera message = new ClientboundCinematicCamera(level.dimension().location(),
                subject.getId(), subject.getUUID(), focus, facingYaw, Mth.clamp(durationTicks, 30, 200),
                Mth.clamp(approachTicks, 1, Mth.clamp(durationTicks, 30, 200) / 2));
        for (ServerPlayer player : level.players()) {
            if (player.isAlive() && !player.isSpectator() && player.distanceToSqr(focus) <= range * range) {
                AnnoyingLib.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), message);
            }
        }
    }
}
