package com.pla.annoyinglib.network;

import com.pla.annoyinglib.client.engine.ClientPacketHandlers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundMuteExplosionAtPos(BlockPos pos, int lifetimeTicks) {

    public static void encode(ClientboundMuteExplosionAtPos msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeVarInt(msg.lifetimeTicks);
    }

    public static ClientboundMuteExplosionAtPos decode(FriendlyByteBuf buf) {
        return new ClientboundMuteExplosionAtPos(buf.readBlockPos(), buf.readVarInt());
    }

    public static void handle(ClientboundMuteExplosionAtPos msg, Supplier<NetworkEvent.Context> ctx) {
        var c = ctx.get();
        c.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleMuteExplosionAtPos(msg));
        });
        c.setPacketHandled(true);
    }
}
