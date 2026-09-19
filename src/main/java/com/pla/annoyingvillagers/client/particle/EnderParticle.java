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
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EnderParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static EnderParticle.EnderParticleProvider provider(SpriteSet spriteset) {
        return new EnderParticle.EnderParticleProvider(spriteset);
    }

    protected EnderParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2);
        this.spriteSet = spriteset;
        this.setSize(0.4F, 0.4F);
        this.quadSize *= 0.7F;
        this.lifetime = Math.max(1, 20 + (this.random.nextInt(12) - 6));
        this.gravity = -0.1F;
        this.hasPhysics = false;
        this.xd = d3;
        this.yd = d4;
        this.zd = d5;
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
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age % 8 + 1, 8));
        }

    }

    public static class EnderParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public EnderParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(@NotNull SimpleParticleType simpleparticletype, @NotNull ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new EnderParticle(clientlevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
