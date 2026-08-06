package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundEpicFightCameraFx(int action, float fovModifier, int fovTicks, float blurStrength, int blurTicks) {
    public static final int ACTION_ZOOM_IN = 0;
    public static final int ACTION_RESET_ZOOM_AND_BLUR = 1;
    public static final int ACTION_BLUR = 2;

    public static ClientboundEpicFightCameraFx zoomIn(float fovModifier, int fovTicks) {
        return new ClientboundEpicFightCameraFx(ACTION_ZOOM_IN, fovModifier, fovTicks, 0.0F, 0);
    }

    public static ClientboundEpicFightCameraFx resetZoomAndBlur(float blurStrength, int blurTicks) {
        return new ClientboundEpicFightCameraFx(ACTION_RESET_ZOOM_AND_BLUR, 0.0F, 0, blurStrength, blurTicks);
    }

    public static ClientboundEpicFightCameraFx blur(float blurStrength, int blurTicks) {
        return new ClientboundEpicFightCameraFx(ACTION_BLUR, 0.0F, 0, blurStrength, blurTicks);
    }

    public static void encode(ClientboundEpicFightCameraFx msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.action);
        buf.writeFloat(msg.fovModifier);
        buf.writeVarInt(msg.fovTicks);
        buf.writeFloat(msg.blurStrength);
        buf.writeVarInt(msg.blurTicks);
    }

    public static ClientboundEpicFightCameraFx decode(FriendlyByteBuf buf) {
        return new ClientboundEpicFightCameraFx(
                buf.readVarInt(),
                buf.readFloat(),
                buf.readVarInt(),
                buf.readFloat(),
                buf.readVarInt()
        );
    }

    public static void handle(ClientboundEpicFightCameraFx msg, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context c = ctx.get();
        c.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(
                Dist.CLIENT,
                () -> () -> ClientPacketHandlers.handleEpicFightCameraFx(msg)
        ));
        c.setPacketHandled(true);
    }
}
