package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class BladeRushParticle extends HitParticle {
    public static BladeRushParticle.Provider provider(SpriteSet spriteSet) {
        return new BladeRushParticle.Provider(spriteSet);
    }

    protected BladeRushParticle(
            ClientLevel clientLevel,
            double x,
            double y,
            double z,
            SpriteSet spriteSet
    ) {
        super(clientLevel, x, y, z, spriteSet);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.lifetime = 4;
        this.quadSize = 2.0F;
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
            return new BladeRushParticle(clientLevel, x, y, z, this.spriteSet);
        }
    }
}
