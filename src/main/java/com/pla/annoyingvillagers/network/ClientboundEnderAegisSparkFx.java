package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;


import net.neoforged.neoforge.network.handling.IPayloadContext;



public record ClientboundEnderAegisSparkFx(Vec3 from, Vec3 to)  implements AnnoyingVillagersPayload {

    public static void encode(ClientboundEnderAegisSparkFx msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.from.x);
        buf.writeDouble(msg.from.y);
        buf.writeDouble(msg.from.z);
        buf.writeDouble(msg.to.x);
        buf.writeDouble(msg.to.y);
        buf.writeDouble(msg.to.z);
    }

    public static ClientboundEnderAegisSparkFx decode(FriendlyByteBuf buf) {
        Vec3 from = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        Vec3 to = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundEnderAegisSparkFx(from, to);
    }

    public static void handle(ClientboundEnderAegisSparkFx msg, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandlers.handleEnderAegisSparkFx(msg));
    }
}
