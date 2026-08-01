package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundRigAnimation(int entityId, RigAnimationId animationId, int durationTicks) {
    public static void encode(ClientboundRigAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeVarInt(msg.animationId.networkId());
        buf.writeVarInt(msg.durationTicks);
    }

    public static ClientboundRigAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundRigAnimation(
                buf.readVarInt(),
                RigAnimationId.fromNetworkId(buf.readVarInt()),
                buf.readVarInt()
        );
    }

    public static void handle(ClientboundRigAnimation msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleRigAnimation(msg)));
        c.setPacketHandled(true);
    }
}
