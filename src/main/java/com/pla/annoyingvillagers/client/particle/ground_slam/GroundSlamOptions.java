package com.pla.annoyingvillagers.client.particle.ground_slam;

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

public record GroundSlamOptions(double yaw, double pitch, double roll) implements ParticleOptions {
    public static final Codec<GroundSlamOptions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("yaw").forGetter(GroundSlamOptions::yaw),
            Codec.DOUBLE.fieldOf("pitch").forGetter(GroundSlamOptions::pitch),
            Codec.DOUBLE.fieldOf("roll").forGetter(GroundSlamOptions::roll)
    ).apply(instance, GroundSlamOptions::new));

    public static final ParticleOptions.Deserializer<GroundSlamOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public @NotNull GroundSlamOptions fromCommand(@NotNull ParticleType<GroundSlamOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double yaw = reader.readDouble();
            reader.expect(' ');
            double pitch = reader.readDouble();
            reader.expect(' ');
            return new GroundSlamOptions(yaw, pitch, reader.readDouble());
        }

        @Override
        public @NotNull GroundSlamOptions fromNetwork(@NotNull ParticleType<GroundSlamOptions> type, FriendlyByteBuf buffer) {
            return new GroundSlamOptions(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        }
    };

    @Override
    public @NotNull ParticleType<?> getType() {
        return AnnoyingVillagersModParticleTypes.GROUND_SLAM.get();
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

    public static Codec<GroundSlamOptions> codec() {
        return CODEC;
    }
}
