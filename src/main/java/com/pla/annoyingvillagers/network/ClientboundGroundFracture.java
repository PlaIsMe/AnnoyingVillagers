package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundGroundFracture(Vec3 location, double radius, boolean noSound, boolean noParticle) {
    public static void encode(ClientboundGroundFracture msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.location.x);
        buf.writeDouble(msg.location.y);
        buf.writeDouble(msg.location.z);
        buf.writeDouble(msg.radius);
        buf.writeBoolean(msg.noSound);
        buf.writeBoolean(msg.noParticle);
    }

    public static ClientboundGroundFracture decode(FriendlyByteBuf buf) {
        return new ClientboundGroundFracture(new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()), buf.readDouble(), buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(ClientboundGroundFracture msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleGroundFracture(msg)));
        context.setPacketHandled(true);
    }
}
