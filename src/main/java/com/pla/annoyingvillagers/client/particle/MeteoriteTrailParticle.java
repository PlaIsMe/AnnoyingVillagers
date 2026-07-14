package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MeteoriteTrailParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private float angularVelocity;
    private final float angularAcceleration;

    public static MeteoriteTrailParticle.MeteoriteTrailParticleProvider provider(SpriteSet spriteset) {
        return new MeteoriteTrailParticle.MeteoriteTrailParticleProvider(spriteset);
    }

    protected MeteoriteTrailParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2);
        this.spriteSet = spriteset;
        this.setSize(0.2F, 0.2F);
        this.quadSize *= 16.0F;
        this.lifetime = 15;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = d3;
        this.yd = d4;
        this.zd = d5;
        this.angularVelocity = 0.0F;
        this.angularAcceleration = 0.03F;
        this.setSpriteFromAge(spriteset);
    }

    public int getLightColor(float f) {
        return 15728880;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public void tick() {
        super.tick();
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age / 2 % 8 + 1, 8));
        }
    }

    public static class MeteoriteTrailParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public MeteoriteTrailParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d0, double d1, double d2, double d3, double d4, double d5) {

            return new MeteoriteTrailParticle(clientLevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}

