package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.engine.GroundStuckKnockoutClient;
import net.minecraft.network.FriendlyByteBuf;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundGroundStuckKnockoutFx(int entityId, int ticks)  implements AnnoyingVillagersPayload {
    public static void encode(ClientboundGroundStuckKnockoutFx msg, FriendlyByteBuf buf) { buf.writeVarInt(msg.entityId); buf.writeVarInt(msg.ticks); }
    public static ClientboundGroundStuckKnockoutFx decode(FriendlyByteBuf buf) { return new ClientboundGroundStuckKnockoutFx(buf.readVarInt(), buf.readVarInt()); }
    public static void handle(ClientboundGroundStuckKnockoutFx msg, IPayloadContext context) {
        context.enqueueWork(() -> GroundStuckKnockoutClient.set(msg.entityId(), msg.ticks()));
    }
}
