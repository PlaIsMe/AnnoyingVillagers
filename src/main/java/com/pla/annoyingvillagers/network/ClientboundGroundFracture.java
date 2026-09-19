package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundGroundFracture(Vec3 location, double radius, boolean noSound, boolean noParticle)  implements AnnoyingVillagersPayload {
    public static void encode(ClientboundGroundFracture msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.location.x);
        buf.writeDouble(msg.location.y);
        buf.writeDouble(msg.location.z);
        buf.writeDouble(msg.radius);
        buf.writeBoolean(msg.noSound);
        buf.writeBoolean(msg.noParticle);
    }

    public static ClientboundGroundFracture decode(FriendlyByteBuf buf) {
        return new ClientboundGroundFracture(new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()), buf.readDouble(), buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(ClientboundGroundFracture msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleGroundFracture(msg));
    }
}
