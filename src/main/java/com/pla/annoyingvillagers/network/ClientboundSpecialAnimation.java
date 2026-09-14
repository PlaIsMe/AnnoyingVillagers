package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.animation.SpecialClientAnimationState;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundSpecialAnimation(int entityId, SpecialAnimationId animationId, int durationTicks) {
    public static void encode(ClientboundSpecialAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeVarInt(msg.animationId.networkId());
        buf.writeVarInt(msg.durationTicks);
    }

    public static ClientboundSpecialAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundSpecialAnimation(buf.readVarInt(), SpecialAnimationId.fromNetworkId(buf.readVarInt()), buf.readVarInt());
    }

    public static void handle(ClientboundSpecialAnimation msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SpecialClientAnimationState.start(msg.entityId(), msg.animationId(), msg.durationTicks())));
        context.setPacketHandled(true);
    }
}
