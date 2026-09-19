package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundTeleportPortalFx(Vec3 pos, Vec3 normal)  implements AnnoyingVillagersPayload {

    public static void encode(ClientboundTeleportPortalFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.pos.x);
        buf.writeDouble(msg.pos.y);
        buf.writeDouble(msg.pos.z);
        buf.writeDouble(msg.normal.x);
        buf.writeDouble(msg.normal.y);
        buf.writeDouble(msg.normal.z);
    }

    public static ClientboundTeleportPortalFx decode(FriendlyByteBuf buf) {
        Vec3 pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 normal = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundTeleportPortalFx(pos, normal);
    }

    public static void handle(ClientboundTeleportPortalFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleTeleportPortalFx(msg));
    }
}
