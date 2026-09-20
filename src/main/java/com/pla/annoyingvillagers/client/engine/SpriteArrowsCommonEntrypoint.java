package com.pla.annoyingvillagers.client.engine;

import com.pla.annoyingvillagers.client.renderer.SpriteArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

public class SpriteArrowsCommonEntrypoint {
    public static void replace() {
        EntityRenderers.register(EntityType.ARROW, SpriteArrowRenderer::new);
        EntityRenderers.register(EntityType.SPECTRAL_ARROW, SpriteArrowRenderer::new);
    }
}
