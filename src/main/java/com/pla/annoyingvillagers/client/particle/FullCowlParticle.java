package com.pla.annoyingvillagers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;

public class FullCowlParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;

    public static FullCowlParticle.FullCowlProvider provider(SpriteSet spriteset) {
        return new FullCowlParticle.FullCowlProvider(spriteset);
    }

    protected FullCowlParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2, spriteset.first());
        this.spriteSet = spriteset;
        this.setSize(0.3F, 0.3F);
        this.quadSize *= 2.5F;
        this.lifetime = 20 + this.random.nextInt(21);
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
            this.setSprite(this.spriteSet.get(this.age / 1 % 27 + 1, 27));
        }

    }

    public static class FullCowlProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public FullCowlProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, net.minecraft.util.RandomSource random) {
            return new FullCowlParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
