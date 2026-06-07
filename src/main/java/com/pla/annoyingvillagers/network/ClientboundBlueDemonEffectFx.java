package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundBlueDemonEffectFx(int entityId, int tickCount, boolean followEntity, Vec3 pos,
                                           int count, double xOffset, double yOffset, double zOffset, double speed) {

    public static void encode(ClientboundBlueDemonEffectFx msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeInt(msg.tickCount);
        buf.writeBoolean(msg.followEntity);
        buf.writeDouble(msg.pos.x);
        buf.writeDouble(msg.pos.y);
        buf.writeDouble(msg.pos.z);
        buf.writeInt(msg.count);
        buf.writeDouble(msg.xOffset);
        buf.writeDouble(msg.yOffset);
        buf.writeDouble(msg.zOffset);
        buf.writeDouble(msg.speed);
    }

    public static ClientboundBlueDemonEffectFx decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int tickCount = buf.readInt();
        boolean followEntity = buf.readBoolean();
        Vec3 pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new ClientboundBlueDemonEffectFx(entityId, tickCount, followEntity, pos, buf.readInt(),
                buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void handle(ClientboundBlueDemonEffectFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleBlueDemonEffectFx(msg)));
        c.setPacketHandled(true);
    }
}
