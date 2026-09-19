package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundMuteExplosionAtPos(BlockPos pos, int lifetimeTicks)  implements AnnoyingVillagersPayload {

    public static void encode(ClientboundMuteExplosionAtPos msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeVarInt(msg.lifetimeTicks);
    }

    public static ClientboundMuteExplosionAtPos decode(FriendlyByteBuf buf) {
        return new ClientboundMuteExplosionAtPos(buf.readBlockPos(), buf.readVarInt());
    }

    public static void handle(ClientboundMuteExplosionAtPos msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleMuteExplosionAtPos(msg));
    }
}
