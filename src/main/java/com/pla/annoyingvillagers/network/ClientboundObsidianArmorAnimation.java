package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.network.FriendlyByteBuf;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundObsidianArmorAnimation(int entityId, SpecialAnimationId animationId, int durationTicks)  implements AnnoyingVillagersPayload {
    public static void encode(ClientboundObsidianArmorAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId());
        buf.writeVarInt(msg.animationId().networkId());
        buf.writeVarInt(msg.durationTicks());
    }

    public static ClientboundObsidianArmorAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundObsidianArmorAnimation(buf.readVarInt(), SpecialAnimationId.fromNetworkId(buf.readVarInt()), buf.readVarInt());
    }

    public static void handle(ClientboundObsidianArmorAnimation msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleObsidianArmorAnimation(msg));
    }
}
