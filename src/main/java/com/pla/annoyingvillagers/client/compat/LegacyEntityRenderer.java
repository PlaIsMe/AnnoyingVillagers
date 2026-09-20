package com.pla.annoyingvillagers.client.compat;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

/** Base state extraction for non-living renderers being migrated to 26.1 submission. */
public abstract class LegacyEntityRenderer<T extends Entity> extends EntityRenderer<T, LegacyEntityRenderState<T>> {
    protected LegacyEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public LegacyEntityRenderState<T> createRenderState() {
        return new LegacyEntityRenderState<>();
    }

    @Override
    public void extractRenderState(T entity, LegacyEntityRenderState<T> state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.entity = entity;
    }

    public abstract Identifier getTextureLocation(T entity);
}
