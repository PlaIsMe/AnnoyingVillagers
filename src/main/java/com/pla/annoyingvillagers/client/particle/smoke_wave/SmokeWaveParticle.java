package com.pla.annoyingvillagers.client.particle.smoke_wave;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.particle.EFAParticleRenderType;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public final class SmokeWaveParticle extends TextureSheetParticle {
    private final double yaw;
    private final double pitch;
    private final double roll;
    private final SpriteSet sprites;

    private SmokeWaveParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
                              double yaw, double pitch, double roll, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
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
    public void render(@NotNull VertexConsumer consumer, Camera camera, float partialTick) {
        Vec3 cameraPos = camera.getPosition();
        float x = (float) (Mth.lerp(partialTick, this.xo, this.x) - cameraPos.x);
        float y = (float) (Mth.lerp(partialTick, this.yo, this.y) - cameraPos.y);
        float z = (float) (Mth.lerp(partialTick, this.zo, this.z) - cameraPos.z);
        float size = this.getQuadSize(partialTick);
        Vector3f forward = new Vector3f((float) -Math.sin(this.yaw), 0.0F, (float) Math.cos(this.yaw));
        Vector3f right = new Vector3f((float) Math.cos(this.yaw), 0.0F, (float) Math.sin(this.yaw));
        forward.rotate(new Quaternionf().fromAxisAngleRad(right, (float) this.pitch));
        right.rotate(new Quaternionf().fromAxisAngleRad(forward, (float) this.roll));
        int light = this.getLightColor(partialTick);

        this.vertex(consumer, x, y, z, right, forward, -size, -size, this.getU0(), this.getV1(), light);
        this.vertex(consumer, x, y, z, right, forward, -size, size, this.getU0(), this.getV0(), light);
        this.vertex(consumer, x, y, z, right, forward, size, size, this.getU1(), this.getV0(), light);
        this.vertex(consumer, x, y, z, right, forward, size, -size, this.getU1(), this.getV1(), light);
    }

    private void vertex(VertexConsumer consumer, float x, float y, float z, Vector3f right, Vector3f forward,
                        float rightOffset, float forwardOffset, float u, float v, int light) {
        consumer.vertex(x + rightOffset * right.x() + forwardOffset * forward.x(),
                        y + rightOffset * right.y() + forwardOffset * forward.y(),
                        z + rightOffset * right.z() + forwardOffset * forward.z())
                .uv(u, v).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(light).endVertex();
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return EFAParticleRenderType.PARTICLE_SHEET_OPAQUE_NO_CULL;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<SmokeWaveOptions> {
        @Override
        public @NotNull Particle createParticle(@NotNull SmokeWaveOptions options, @NotNull ClientLevel level,
                                                double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SmokeWaveParticle(level, x, y, z, xSpeed, ySpeed, zSpeed,
                    Math.toRadians(options.yaw()), Math.toRadians(options.pitch()), Math.toRadians(options.roll()), this.sprites);
        }
    }
}
