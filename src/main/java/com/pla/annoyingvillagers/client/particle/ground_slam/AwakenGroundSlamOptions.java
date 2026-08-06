package com.pla.annoyingvillagers.client.particle.ground_slam;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class AwakenGroundSlamOptions implements ParticleOptions {
    public static final Deserializer<AwakenGroundSlamOptions> DESERIALIZER =
            new Deserializer<>() {
                @Override
                public @NotNull AwakenGroundSlamOptions fromCommand(
                        @NotNull ParticleType<AwakenGroundSlamOptions> particleType,
                        StringReader reader) throws CommandSyntaxException {
                    double yaw = reader.readDouble();
                    reader.expect(' ');
                    double pitch = reader.readDouble();
                    reader.expect(' ');
                    double roll = reader.readDouble();

                    return new AwakenGroundSlamOptions(yaw, pitch, roll);
                }

                @Override
                public @NotNull AwakenGroundSlamOptions fromNetwork(
                        @NotNull ParticleType<AwakenGroundSlamOptions> particleType,
                        FriendlyByteBuf buffer) {
                    return new AwakenGroundSlamOptions(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
                }
            };

    private final double yaw;
    private final double pitch;
    private final double roll;

    public AwakenGroundSlamOptions(double yaw, double pitch, double roll) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return AnnoyingVillagersModParticleTypes.AWAKEN_GROUND_SLAM.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.yaw);
        buffer.writeDouble(this.pitch);
        buffer.writeDouble(this.roll);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%.2f %.2f %.2f", this.yaw, this.pitch, this.roll);
    }

    public static Codec<AwakenGroundSlamOptions> codec() {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.DOUBLE.fieldOf("yaw").forGetter(AwakenGroundSlamOptions::getYaw),
                Codec.DOUBLE.fieldOf("pitch").forGetter(AwakenGroundSlamOptions::getPitch),
                Codec.DOUBLE.fieldOf("roll").forGetter(AwakenGroundSlamOptions::getRoll)
        ).apply(instance, AwakenGroundSlamOptions::new));
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getPitch() {
        return this.pitch;
    }

    public double getRoll() {
        return this.roll;
    }
}
