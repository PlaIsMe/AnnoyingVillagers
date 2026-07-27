/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-or-later AND Apache-2.0
 *
 * Upstream: Skyfall: Meteorites - Yoshi01111
 * Source: https://www.curseforge.com/minecraft/mc-mods/skyfall-meteorites
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved (including Apache-2.0 NOTICE if provided).
 *
 * License texts:
 *   - third_party/licenses/GPL-3.0.md
 *   - third_party/licenses/Apache-2.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

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
public class BigSplashParticle extends TextureSheetParticle {

    private final SpriteSet spriteSet;

    public static BigSplashParticle.BigSplashParticleProvider provider(SpriteSet spriteset) {
        return new BigSplashParticle.BigSplashParticleProvider(spriteset);
    }

    protected BigSplashParticle(ClientLevel clientlevel, double d0, double d1, double d2, double d3, double d4, double d5, SpriteSet spriteset) {
        super(clientlevel, d0, d1, d2);
        this.spriteSet = spriteset;
        this.setSize(0.2F, 0.2F);
        this.lifetime = Math.max(1, 30 + (this.random.nextInt(40) - 20));
        this.gravity = 0.7F;
        this.hasPhysics = false;
        this.xd = d3;
        this.yd = d4;
        this.zd = d5;
        this.setSpriteFromAge(spriteset);
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getQuadSize(float f) {
        return (float) (super.getQuadSize(f) * (float) 19.0D + Math.sin(this.age * 0.2D) * 2.0D);
    }

    public void tick() {
        super.tick();
        if (!this.removed) {
            this.setSprite(this.spriteSet.get(this.age / 6 % 11 + 1, 11));
        }

    }

    public static class BigSplashParticleProvider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public BigSplashParticleProvider(SpriteSet spriteset) {
            this.spriteSet = spriteset;
        }

        public Particle createParticle(@NotNull SimpleParticleType simpleParticleType, @NotNull ClientLevel clientLevel, double d0, double d1, double d2, double d3, double d4, double d5) {
            return new BigSplashParticle(clientLevel, d0, d1, d2, d3, d4, d5, this.spriteSet);
        }
    }
}
