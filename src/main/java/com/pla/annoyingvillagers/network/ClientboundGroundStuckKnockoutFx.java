package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.engine.GroundStuckKnockoutClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundGroundStuckKnockoutFx(int entityId, int ticks) {
    public static void encode(ClientboundGroundStuckKnockoutFx msg, FriendlyByteBuf buf) { buf.writeVarInt(msg.entityId); buf.writeVarInt(msg.ticks); }
    public static ClientboundGroundStuckKnockoutFx decode(FriendlyByteBuf buf) { return new ClientboundGroundStuckKnockoutFx(buf.readVarInt(), buf.readVarInt()); }
    public static void handle(ClientboundGroundStuckKnockoutFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            GroundStuckKnockoutClient.set(msg.entityId(), msg.ticks());
        }));
        context.setPacketHandled(true);
    }
}
