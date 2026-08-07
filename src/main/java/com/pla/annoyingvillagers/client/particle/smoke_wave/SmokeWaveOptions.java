package com.pla.annoyingvillagers.client.particle.smoke_wave;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public record SmokeWaveOptions(double yaw, double pitch, double roll) implements ParticleOptions {
    public static final Codec<SmokeWaveOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("yaw").forGetter(SmokeWaveOptions::yaw),
            Codec.DOUBLE.fieldOf("pitch").forGetter(SmokeWaveOptions::pitch),
            Codec.DOUBLE.fieldOf("roll").forGetter(SmokeWaveOptions::roll)
    ).apply(instance, SmokeWaveOptions::new));

    public static final ParticleOptions.Deserializer<SmokeWaveOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public @NotNull SmokeWaveOptions fromCommand(@NotNull ParticleType<SmokeWaveOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double yaw = reader.readDouble();
            reader.expect(' ');
            double pitch = reader.readDouble();
            reader.expect(' ');
            return new SmokeWaveOptions(yaw, pitch, reader.readDouble());
        }

        @Override
        public @NotNull SmokeWaveOptions fromNetwork(@NotNull ParticleType<SmokeWaveOptions> type, FriendlyByteBuf buffer) {
            return new SmokeWaveOptions(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        }
    };

    @Override
    public @NotNull ParticleType<?> getType() {
        return AnnoyingVillagersModParticleTypes.SMOKE_WAVE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.yaw);
        buffer.writeDouble(this.pitch);
        buffer.writeDouble(this.roll);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.yaw, this.pitch, this.roll);
    }

    public static Codec<SmokeWaveOptions> codec() {
        return CODEC;
    }
}
