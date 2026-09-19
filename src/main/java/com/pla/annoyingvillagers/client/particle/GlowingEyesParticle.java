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
public class GlowingEyesParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;
    private float angularVelocity;
    private float angularAcceleration;

    public static GlowingEyesParticle.GlowingeyesParticleProvider provider(SpriteSet spriteset) {
        return new GlowingEyesParticle.GlowingeyesParticleProvider(spriteset);
    }

    protected GlowingEyesParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2);
        this.spriteSet = spriteset;
        this.setSize(0.0F, 0.0F);
        this.quadSize *= 1.7F;
        this.lifetime = 1;
        this.gravity = 0.0F;
        this.hasPhysics = false;
        this.xd = d3 * 0.0D;
        this.yd = d4 * 0.0D;
        this.zd = d5 * 0.0D;
        this.angularVelocity = 0.1F;
        this.angularAcceleration = 0.0F;
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
        this.oRoll = this.roll;
        this.roll += this.angularVelocity;
        this.angularVelocity += this.angularAcceleration;
    }

    public static class GlowingeyesParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public GlowingeyesParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(SimpleParticleType simpleparticletype, ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new GlowingEyesParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
