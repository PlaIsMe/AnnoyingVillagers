package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class GroundSlamParticle extends NoRenderParticle {
    protected GroundSlamParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double radius,
            double particleCount,
            double spread,
            BlockPos blockPos,
            BlockState blockState
    ) {
        super(level, x, y, z);
        this.lifetime = 1;

        if (blockState.isAir()) {
            blockState = level.getBlockState(blockPos.below());
        }

        if (!blockState.shouldSpawnParticlesOnBreak()) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        int count = Math.max(0, (int) particleCount);

        for (int i = 0; i < count; i++) {
            double angle = this.random.nextDouble() * Math.PI * 2.0D;
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);
            double particleX = x + sin * radius;
            double particleZ = z + cos * radius;
            double debrisMotionX = (sin * spread + (this.random.nextDouble() - 0.5D)) * 0.3D;
            double debrisMotionZ = (cos * spread + (this.random.nextDouble() - 0.5D)) * 0.3D;

            Particle blockParticle = new TerrainParticle(level, particleX, y, particleZ, 0.0D, 0.0D, 0.0D, blockState, blockPos);
            blockParticle.setParticleSpeed(debrisMotionX, this.random.nextDouble() * 0.5D, debrisMotionZ);
            blockParticle.setLifetime(60 + this.random.nextInt(20));
            minecraft.particleEngine.add(blockParticle);

            Particle smokeParticle = minecraft.particleEngine.createParticle(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    x + sin * radius * 0.5D,
                    y + 1.5D,
                    z + cos * radius * 0.5D,
                    0.0D,
                    0.0D,
                    0.0D
            );

            if (smokeParticle != null) {
                smokeParticle.setParticleSpeed(sin * spread * 0.1D, this.random.nextDouble() * 0.05D, cos * spread * 0.1D);
                smokeParticle.scale(3.0F);
                minecraft.particleEngine.add(smokeParticle);
            }
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        @Nullable
        @Override
        public Particle createParticle(
                @NotNull SimpleParticleType type,
                @NotNull ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            BlockPos blockPos = new BlockPos.MutableBlockPos(x, y, z);
            BlockState blockState = level.getBlockState(blockPos);
            return new GroundSlamParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, blockPos, blockState);
        }
    }
}
