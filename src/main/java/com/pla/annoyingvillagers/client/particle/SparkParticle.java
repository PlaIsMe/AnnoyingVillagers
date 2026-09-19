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
public class SparkParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static SparkParticle.SparkParticleProvider provider(SpriteSet spriteset) {
        return new SparkParticle.SparkParticleProvider(spriteset);
    }

    protected SparkParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2);
        this.spriteSet = spriteset;
        this.setSize(0.3F, 0.2F);
        this.quadSize *= 0.25F;
        this.lifetime = Math.max(1, 23 + (this.random.nextInt(20) - 10));
        this.gravity = 0.5F;
        this.hasPhysics = true;
        this.xd = d3 * 1.0D;
        this.yd = d4 * 1.0D;
        this.zd = d5 * 1.0D;
        this.pickSprite(spriteset);
    }

    public int getLightColor(float f) {
        return 15728880;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    public void tick() {
        super.tick();
    }

    public static class SparkParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public SparkParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new SparkParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
