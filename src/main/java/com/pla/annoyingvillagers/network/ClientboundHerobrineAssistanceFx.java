package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundHerobrineAssistanceFx(Vec3 from)  implements AnnoyingVillagersPayload {

    public static void encode(ClientboundHerobrineAssistanceFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x);
        buf.writeDouble(msg.from.y);
        buf.writeDouble(msg.from.z);
    }

    public static ClientboundHerobrineAssistanceFx decode(FriendlyByteBuf buf) {
        Vec3 f = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundHerobrineAssistanceFx(f);
    }

    public static void handle(ClientboundHerobrineAssistanceFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleHerobrineAssistanceFx(msg));
    }
}
