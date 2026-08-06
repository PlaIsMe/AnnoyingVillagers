package com.pla.annoyingvillagers.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AwakenJointFollowParticleOptions implements ParticleOptions {
    public static final Deserializer<AwakenJointFollowParticleOptions> DESERIALIZER =
            new Deserializer<>() {
                @Override
                public @NotNull AwakenJointFollowParticleOptions fromCommand(
                        @NotNull ParticleType<AwakenJointFollowParticleOptions> particleType,
                        StringReader reader) throws CommandSyntaxException {
                    int entityId = reader.readInt();
                    reader.expect(' ');
                    int jointId = reader.readInt();
                    reader.expect(' ');
                    double offsetX = reader.readDouble();
                    reader.expect(' ');
                    double offsetY = reader.readDouble();
                    reader.expect(' ');
                    double offsetZ = reader.readDouble();

                    return new AwakenJointFollowParticleOptions(particleType, entityId, jointId,
                            new Vec3(offsetX, offsetY, offsetZ));
                }

                @Override
                public @NotNull AwakenJointFollowParticleOptions fromNetwork(
                        @NotNull ParticleType<AwakenJointFollowParticleOptions> particleType,
                        FriendlyByteBuf buffer) {
                    return new AwakenJointFollowParticleOptions(particleType, buffer.readInt(), buffer.readInt(),
                            new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
                }
            };

    private final ParticleType<?> type;
    private final int entityId;
    private final int jointId;
    private final Vec3 offset;

    public AwakenJointFollowParticleOptions(ParticleType<?> type, int entityId, int jointId, Vec3 offset) {
        this.type = type;
        this.entityId = entityId;
        this.jointId = jointId;
        this.offset = offset;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.jointId);
        buffer.writeDouble(this.offset.x);
        buffer.writeDouble(this.offset.y);
        buffer.writeDouble(this.offset.z);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%d %d %.2f %.2f %.2f", this.entityId, this.jointId,
                this.offset.x, this.offset.y, this.offset.z);
    }

    public static Codec<AwakenJointFollowParticleOptions> codec(
            ParticleType<AwakenJointFollowParticleOptions> particleType) {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("entity_id").forGetter(AwakenJointFollowParticleOptions::getEntityId),
                Codec.INT.fieldOf("joint_id").forGetter(AwakenJointFollowParticleOptions::getJointId),
                Codec.DOUBLE.fieldOf("offset_x").forGetter(options -> options.getOffset().x),
                Codec.DOUBLE.fieldOf("offset_y").forGetter(options -> options.getOffset().y),
                Codec.DOUBLE.fieldOf("offset_z").forGetter(options -> options.getOffset().z)
        ).apply(instance, (entityId, jointId, offsetX, offsetY, offsetZ) ->
                new AwakenJointFollowParticleOptions(particleType, entityId, jointId,
                        new Vec3(offsetX, offsetY, offsetZ))));
    }

    public int getEntityId() {
        return this.entityId;
    }

    public int getJointId() {
        return this.jointId;
    }

    public Vec3 getOffset() {
        return this.offset;
    }
}
