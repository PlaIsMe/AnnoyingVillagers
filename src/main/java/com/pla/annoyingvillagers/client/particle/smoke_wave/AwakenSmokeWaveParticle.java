package com.pla.annoyingvillagers.client.particle.smoke_wave;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.particle.AwakenParticleRenderType;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class AwakenSmokeWaveParticle extends TextureSheetParticle {
    private final double yaw;
    private final double pitch;
    private final double roll;
    private int delay;
    protected final SpriteSet sprites;

    protected AwakenSmokeWaveParticle(ClientLevel level, double x, double y, double z,
                                      double xSpeed, double ySpeed, double zSpeed,
                                      double yaw, double pitch, double roll, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.sprites = sprites;
        this.lifetime = 7;
        this.delay = 0;
        this.quadSize = (float) (Math.random() * 0.75D + 0.75D);
        this.alpha = 0.4F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        if (this.delay-- <= 0) {
            this.xo = this.x;
            this.yo = this.y;
            this.zo = this.z;
            if (this.age++ >= this.lifetime) {
                this.remove();
            } else {
                this.setSpriteFromAge(this.sprites);
            }
        }
    }

    @Override
    public void render(@NotNull VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        if (this.delay > 0) {
            return;
        }

        Vec3 cameraPosition = camera.getPosition();
        float x = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPosition.x);
        float y = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPosition.y);
        float z = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPosition.z);
        float quadSize = this.getQuadSize(partialTicks);
        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int light = this.getLightColor(partialTicks);
        Vector3f forward = new Vector3f((float) -Math.sin(this.yaw), 0.0F, (float) Math.cos(this.yaw));
        Vector3f right = new Vector3f((float) Math.cos(this.yaw), 0.0F, (float) Math.sin(this.yaw));
        Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F);
        Quaternionf pitchRotation = new Quaternionf().fromAxisAngleRad(right, (float) this.pitch);

        forward.rotate(pitchRotation);
        up.rotate(pitchRotation);
        Quaternionf rollRotation = new Quaternionf().fromAxisAngleRad(forward, (float) this.roll);

        right.rotate(rollRotation);
        up.rotate(rollRotation);
        double[] localX = new double[]{-quadSize, -quadSize, quadSize, quadSize};
        double[] localY = new double[]{0.0D, 0.0D, 0.0D, 0.0D};
        double[] localZ = new double[]{-quadSize, quadSize, quadSize, -quadSize};

        for (int index = 0; index < 4; ++index) {
            double currentX = localX[index];
            double currentY = localY[index];
            double currentZ = localZ[index];
            double vertexX = x + currentX * right.x() + currentY * up.x() + currentZ * forward.x();
            double vertexY = y + currentX * right.y() + currentY * up.y() + currentZ * forward.y();
            double vertexZ = z + currentX * right.z() + currentY * up.z() + currentZ * forward.z();
            float u = index != 0 && index != 1 ? u1 : u0;
            float v = index != 0 && index != 3 ? v0 : v1;

            vertexConsumer.vertex(vertexX, vertexY, vertexZ)
                    .uv(u, v)
                    .color(this.rCol, this.gCol, this.bCol, this.alpha)
                    .uv2(light)
                    .endVertex();
        }
    }

    @Override
    public int getLightColor(float partialTicks) {
        return 15728880;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return AwakenParticleRenderType.PARTICLE_SHEET_OPAQUE_NO_CULL;
    }

    @OnlyIn(Dist.CLIENT)
    public record Provider(SpriteSet sprites) implements ParticleProvider<AwakenSmokeWaveOptions> {
        @Override
        public @NotNull Particle createParticle(@NotNull AwakenSmokeWaveOptions options, @NotNull ClientLevel level,
                                                double x, double y, double z,
                                                double xSpeed, double ySpeed, double zSpeed) {
            return new AwakenSmokeWaveParticle(level, x, y, z, xSpeed, ySpeed, zSpeed,
                    Math.toRadians(options.getYaw()),
                    Math.toRadians(options.getPitch()),
                    Math.toRadians(options.getRoll()),
                    this.sprites);
        }
    }
}
