package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;

public class LightParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;

    public static LightParticle.LightParticleProvider provider(SpriteSet spriteset) {
        return new LightParticle.LightParticleProvider(spriteset);
    }

    protected LightParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2, spriteset.first());
        this.spriteSet = spriteset;
        this.quadSize = 0.06F;
        this.lifetime = 30;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = d3 * 0.0D;
        this.yd = d4 * 0.0D;
        this.zd = d5 * 0.0D;
        this.setSpriteFromAge(spriteset);
    }

    protected int getLightCoords(float f) {
        return 15728880;
    }

    protected SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get((this.age / 3) % 9, 9));
        }
    }

    public static class LightParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public LightParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, net.minecraft.util.RandomSource random) {
            return new LightParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
