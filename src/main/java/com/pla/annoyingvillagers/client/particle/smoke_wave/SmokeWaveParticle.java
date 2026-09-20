package com.pla.annoyingvillagers.client.particle.smoke_wave;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public final class SmokeWaveParticle extends SingleQuadParticle {
    private final double yaw;
    private final double pitch;
    private final double roll;
    private final SpriteSet sprites;

    private SmokeWaveParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
                              double yaw, double pitch, double roll, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites.first());
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.sprites = sprites;
        this.lifetime = 7;
        this.quadSize = 0.75F + this.random.nextFloat() * 0.75F;
        this.alpha = 0.4F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) this.remove();
        else this.setSpriteFromAge(this.sprites);
    }

    @Override
    public FacingCameraMode getFacingCameraMode() {
        return (rotation, camera, partialTick) -> rotation
                .rotateY((float) this.yaw)
                .rotateX((float) this.pitch)
                .rotateZ((float) this.roll);
    }

    @Override
    protected int getLightCoords(float partialTick) {
        return 15728880;
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SmokeWaveOptions> {
        @Override
        public @NotNull Particle createParticle(@NotNull SmokeWaveOptions options, @NotNull ClientLevel level,
                                                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, net.minecraft.util.RandomSource random) {
            return new SmokeWaveParticle(level, x, y, z, xSpeed, ySpeed, zSpeed,
                    Math.toRadians(options.yaw()), Math.toRadians(options.pitch()), Math.toRadians(options.roll()), this.sprites);
        }
    }
}
