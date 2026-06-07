package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundEliteHerobrineFx(int entityId, int tickCount, Vec3 pos, boolean extraParticle) {

    public static void encode(ClientboundEliteHerobrineFx msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.tickCount);
        buf.writeDouble(msg.pos.x);
        buf.writeDouble(msg.pos.y);
        buf.writeDouble(msg.pos.z);
        buf.writeBoolean(msg.extraParticle);
    }

    public static ClientboundEliteHerobrineFx decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int tickCount = buf.readInt();
        Vec3 pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundEliteHerobrineFx(entityId, tickCount, pos, buf.readBoolean());
    }

    public static void handle(ClientboundEliteHerobrineFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleEliteHerobrineFx(msg)));
        c.setPacketHandled(true);
    }
}
