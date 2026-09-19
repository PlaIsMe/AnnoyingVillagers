package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundWoopieSwordWindFx(Vec3 from)  implements AnnoyingVillagersPayload {

    public static void encode(ClientboundWoopieSwordWindFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x);
        buf.writeDouble(msg.from.y);
        buf.writeDouble(msg.from.z);
    }

    public static ClientboundWoopieSwordWindFx decode(FriendlyByteBuf buf) {
        Vec3 f = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundWoopieSwordWindFx(f);
    }

    public static void handle(ClientboundWoopieSwordWindFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleWoopieSwordWind(msg));
    }
}
