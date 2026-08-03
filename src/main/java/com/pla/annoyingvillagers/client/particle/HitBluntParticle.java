package com.pla.annoyingvillagers.client.particle;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class HitBluntParticle extends HitParticle {
    public static HitBluntParticle.Provider provider(SpriteSet spriteSet) {
        return new HitBluntParticle.Provider(spriteSet);
    }

    protected HitBluntParticle(
            ClientLevel clientLevel,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed,
            SpriteSet spriteSet
    ) {
        super(clientLevel, x, y, z, spriteSet);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.quadSize = 1.0F;
        this.lifetime = 2;

        double sign = 1.0D;

        for (int i = 0; i < 7; i++) {
            double xMovement = this.random.nextDouble() * sign;
            sign *= this.random.nextBoolean() ? 1.0D : -1.0D;
            double zMovement = this.random.nextDouble() * sign;
            sign *= this.random.nextBoolean() ? 1.0D : -1.0D;
            this.level.addParticle(
                    AnnoyingVillagersModParticleTypes.SPARK.get(),
                    this.x,
                    this.y,
                    this.z,
                    xMovement,
                    this.random.nextDouble() * 0.5D,
                    zMovement
            );
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(
                @NotNull SimpleParticleType particleType,
                @NotNull ClientLevel clientLevel,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            return new HitBluntParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
