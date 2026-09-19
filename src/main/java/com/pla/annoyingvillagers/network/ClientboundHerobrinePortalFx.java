package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;

public record ClientboundHerobrinePortalFx(Vec3 from)  implements AnnoyingVillagersPayload {
    private static final double DELIVERY_RADIUS = 128.0D;

    /**
     * Portal effects are often emitted in the same tick an entity is added.
     * At that point NeoForge's entity tracking set can still be empty, so
     * deliver by position instead.
     */
    public static void sendToNearby(Entity source, Vec3 from) {
        if (source != null && source.level() instanceof ServerLevel serverLevel) {
            sendToNearby(serverLevel, from);
        }
    }

    public static void sendToNearby(ServerLevel level, Vec3 from) {
        if (level == null || from == null) return;
        PacketDistributor.sendToPlayersNear(level, null, from.x, from.y, from.z,
                DELIVERY_RADIUS, new ClientboundHerobrinePortalFx(from));
    }

    public static void encode(ClientboundHerobrinePortalFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x);
        buf.writeDouble(msg.from.y);
        buf.writeDouble(msg.from.z);
    }

    public static ClientboundHerobrinePortalFx decode(FriendlyByteBuf buf) {
        Vec3 f = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundHerobrinePortalFx(f);
    }

    public static void handle(ClientboundHerobrinePortalFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleHerobrinePortalFx(msg));
    }
}
