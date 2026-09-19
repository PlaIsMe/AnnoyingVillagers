package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundBlackFireFx(int entityId)  implements AnnoyingVillagersPayload {

    public ClientboundBlackFireFx(Entity entity) {
        this(entity.getId());
    }

    public static void encode(ClientboundBlackFireFx msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
    }

    public static ClientboundBlackFireFx decode(FriendlyByteBuf buf) {
        return new ClientboundBlackFireFx(buf.readVarInt());
    }

    public static void handle(ClientboundBlackFireFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleBlackFire(msg));
    }
}