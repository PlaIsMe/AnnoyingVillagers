package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundObsidianArmorAnimation(int entityId, SpecialAnimationId animationId, int durationTicks) {
    public static void encode(ClientboundObsidianArmorAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId());
        buf.writeVarInt(msg.animationId().networkId());
        buf.writeVarInt(msg.durationTicks());
    }

    public static ClientboundObsidianArmorAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundObsidianArmorAnimation(buf.readVarInt(), SpecialAnimationId.fromNetworkId(buf.readVarInt()), buf.readVarInt());
    }

    public static void handle(ClientboundObsidianArmorAnimation msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleObsidianArmorAnimation(msg)));
        context.setPacketHandled(true);
    }
}
