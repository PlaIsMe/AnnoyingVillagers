package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundDiamondAttractorFx(int entityId)  implements AnnoyingVillagersPayload {

    public ClientboundDiamondAttractorFx(Entity entity) {
        this(entity.getId());
    }

    public static void encode(ClientboundDiamondAttractorFx msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
    }

    public static ClientboundDiamondAttractorFx decode(FriendlyByteBuf buf) {
        return new ClientboundDiamondAttractorFx(buf.readVarInt());
    }

    public static void handle(ClientboundDiamondAttractorFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleDiamondAttractor(msg));
    }
}