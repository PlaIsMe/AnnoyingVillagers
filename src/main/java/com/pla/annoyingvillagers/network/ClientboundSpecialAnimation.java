package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.animation.SpecialClientAnimationState;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.network.FriendlyByteBuf;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundSpecialAnimation(int entityId, SpecialAnimationId animationId, int durationTicks)  implements AnnoyingVillagersPayload {
    public static void encode(ClientboundSpecialAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeVarInt(msg.animationId.networkId());
        buf.writeVarInt(msg.durationTicks);
    }

    public static ClientboundSpecialAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundSpecialAnimation(buf.readVarInt(), SpecialAnimationId.fromNetworkId(buf.readVarInt()), buf.readVarInt());
    }

    public static void handle(ClientboundSpecialAnimation msg, IPayloadContext context) {
        context.enqueueWork(() -> SpecialClientAnimationState.start(msg.entityId(), msg.animationId(), msg.durationTicks()));
    }
}
