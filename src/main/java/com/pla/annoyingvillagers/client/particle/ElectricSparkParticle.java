package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricSparkParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static ElectricSparkParticle.DianhuParticleProvider provider(SpriteSet spriteset) {
        return new ElectricSparkParticle.DianhuParticleProvider(spriteset);
    }

    protected ElectricSparkParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2);
        this.spriteSet = spriteset;
        this.setSize(0.3F, 0.3F);
        this.quadSize *= 2.5F;
        this.lifetime = Math.max(1, 60 + (this.random.nextInt(10) - 5));
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = d3 * 0.0D;
        this.yd = d4 * 0.0D;
        this.zd = d5 * 0.0D;
        this.setSpriteFromAge(spriteset);
    }

    public int getLightColor(float f) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age / 1 % 27 + 1, 27));
        }

    }

    public static class DianhuParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public DianhuParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new ElectricSparkParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
