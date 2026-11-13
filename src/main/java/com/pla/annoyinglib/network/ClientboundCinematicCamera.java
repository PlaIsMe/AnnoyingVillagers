package com.pla.annoyinglib.network;

import com.pla.annoyinglib.client.camera.CinematicCameraController;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public record ClientboundCinematicCamera(ResourceLocation dimension, int subjectId, UUID subjectUuid,
                                         Vec3 focus, float facingYaw, int durationTicks, int approachTicks) {
    public ClientboundCinematicCamera(ResourceLocation dimension, int subjectId, UUID subjectUuid,
                                      Vec3 focus, float facingYaw, int durationTicks) {
        this(dimension, subjectId, subjectUuid, focus, facingYaw, durationTicks, Math.max(1, Math.round(durationTicks * 0.18F)));
    }
    public static void encode(ClientboundCinematicCamera message, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(message.dimension);
        buffer.writeVarInt(message.subjectId);
        buffer.writeUUID(message.subjectUuid);
        buffer.writeDouble(message.focus.x);
        buffer.writeDouble(message.focus.y);
        buffer.writeDouble(message.focus.z);
        buffer.writeFloat(message.facingYaw);
        buffer.writeVarInt(message.durationTicks);
        buffer.writeVarInt(message.approachTicks);
    }

    public static ClientboundCinematicCamera decode(FriendlyByteBuf buffer) {
        return new ClientboundCinematicCamera(buffer.readResourceLocation(), buffer.readVarInt(), buffer.readUUID(),
                new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()), buffer.readFloat(), buffer.readVarInt(), buffer.readVarInt());
    }

    public static void handle(ClientboundCinematicCamera message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> CinematicCameraController.start(message)));
        context.get().setPacketHandled(true);
    }
}
