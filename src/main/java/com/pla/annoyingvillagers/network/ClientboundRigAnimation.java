package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Server-authoritative rig animation timing. Trail timing is deliberately sent with the
 * animation instead of being hard-coded client-side: attacks emit continuously from the
 * first attack-window start through the final attack-window end.
 */
public record ClientboundRigAnimation(int entityId, RigAnimationId animationId, int durationTicks,
                                      int trailStartTick, int trailEndTickExclusive) {
    public static final int NO_TRAIL_TICK = -1;

    public static void encode(ClientboundRigAnimation msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeVarInt(msg.animationId.networkId());
        buf.writeVarInt(msg.durationTicks);
        buf.writeInt(msg.trailStartTick);
        buf.writeInt(msg.trailEndTickExclusive);
    }

    public static ClientboundRigAnimation decode(FriendlyByteBuf buf) {
        return new ClientboundRigAnimation(
                buf.readVarInt(),
                RigAnimationId.fromNetworkId(buf.readVarInt()),
                buf.readVarInt(),
                buf.readInt(),
                buf.readInt()
        );
    }

    public static void handle(ClientboundRigAnimation msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlers.handleRigAnimation(msg)));
        c.setPacketHandled(true);
    }
}
