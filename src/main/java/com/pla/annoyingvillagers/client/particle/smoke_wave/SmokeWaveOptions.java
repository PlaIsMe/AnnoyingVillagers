package com.pla.annoyingvillagers.client.particle.smoke_wave;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public record SmokeWaveOptions(double yaw, double pitch, double roll) implements ParticleOptions {
    public static final MapCodec<SmokeWaveOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            com.mojang.serialization.Codec.DOUBLE.fieldOf("yaw").forGetter(SmokeWaveOptions::yaw),
            com.mojang.serialization.Codec.DOUBLE.fieldOf("pitch").forGetter(SmokeWaveOptions::pitch),
            com.mojang.serialization.Codec.DOUBLE.fieldOf("roll").forGetter(SmokeWaveOptions::roll)
    ).apply(instance, SmokeWaveOptions::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SmokeWaveOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, SmokeWaveOptions::yaw,
            ByteBufCodecs.DOUBLE, SmokeWaveOptions::pitch,
            ByteBufCodecs.DOUBLE, SmokeWaveOptions::roll,
            SmokeWaveOptions::new
    );

    @Override
    public @NotNull ParticleType<?> getType() {
        return AnnoyingVillagersModParticleTypes.SMOKE_WAVE.get();
    }

    public static MapCodec<SmokeWaveOptions> codec() {
        return CODEC;
    }
}
