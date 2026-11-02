package com.pla.annoyinglib.network;

import com.pla.annoyinglib.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundDiamondAttractorFx(int entityId) {

    public ClientboundDiamondAttractorFx(Entity entity) {
        this(entity.getId());
    }

    public static void encode(ClientboundDiamondAttractorFx msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
    }

    public static ClientboundDiamondAttractorFx decode(FriendlyByteBuf buf) {
        return new ClientboundDiamondAttractorFx(buf.readVarInt());
    }

    public static void handle(ClientboundDiamondAttractorFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();

        c.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(
                    Dist.CLIENT,
                    () -> () -> ClientPacketHandlers.handleDiamondAttractor(msg)
            );
        });

        c.setPacketHandled(true);
    }
}
