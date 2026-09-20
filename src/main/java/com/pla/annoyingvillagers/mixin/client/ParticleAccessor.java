package com.pla.annoyingvillagers.mixin.client;

import net.minecraft.client.particle.SingleQuadParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleQuadParticle.class)
public interface ParticleAccessor {
    @Accessor("alpha")
    void annoyingVillagers$setAlpha(float alpha);
}
