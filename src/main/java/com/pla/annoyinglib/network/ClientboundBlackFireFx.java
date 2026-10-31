package com.pla.annoyinglib.network;

import com.pla.annoyinglib.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundBlackFireFx(int entityId) {

    public ClientboundBlackFireFx(Entity entity) {
        this(entity.getId());
    }

    public static void encode(ClientboundBlackFireFx msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
    }

    public static ClientboundBlackFireFx decode(FriendlyByteBuf buf) {
        return new ClientboundBlackFireFx(buf.readVarInt());
    }

    public static void handle(ClientboundBlackFireFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();

        c.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(
                    Dist.CLIENT,
                    () -> () -> ClientPacketHandlers.handleBlackFire(msg)
            );
        });

        c.setPacketHandled(true);
    }
}