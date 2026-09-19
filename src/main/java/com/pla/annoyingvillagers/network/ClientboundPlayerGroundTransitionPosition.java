package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundPlayerGroundTransitionPosition(double x, double y, double z)  implements AnnoyingVillagersPayload {
    public static void encode(ClientboundPlayerGroundTransitionPosition msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.x);
        buf.writeDouble(msg.y);
        buf.writeDouble(msg.z);
    }

    public static ClientboundPlayerGroundTransitionPosition decode(FriendlyByteBuf buf) {
        return new ClientboundPlayerGroundTransitionPosition(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(ClientboundPlayerGroundTransitionPosition msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handlePlayerGroundTransitionPosition(msg));
    }
}
