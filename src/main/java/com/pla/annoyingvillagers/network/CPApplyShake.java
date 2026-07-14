package com.pla.annoyingvillagers.network;

import com.pla.annoyingvillagers.client.engine.CameraEngine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CPApplyShake {
    private final int time;
    private final float strength;
    private final float frequency;
    private final int decay_time;

    public CPApplyShake(int time, float strength, float frequency, int decay_time){
        this.time = time;
        this.strength = strength;
        this.frequency = frequency;
        this.decay_time = decay_time;
    }
    public CPApplyShake(FriendlyByteBuf buf){
        this.time = buf.readInt();
        this.strength = buf.readFloat();
        this.frequency = buf.readFloat();
        this.decay_time = buf.readInt();
    }
    public void encode(FriendlyByteBuf buf){
        buf.writeInt(time);
        buf.writeFloat(strength);
        buf.writeFloat(frequency);
        buf.writeInt(decay_time);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            CameraEngine engine = CameraEngine.getInstance();
            if (engine != null) {
                engine.shakeCamera(strength, time, frequency, decay_time);
            }
        });
        ctx.setPacketHandled(true);
    }
}