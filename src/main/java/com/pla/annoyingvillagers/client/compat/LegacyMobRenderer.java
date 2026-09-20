package com.pla.annoyingvillagers.client.compat;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

/** Keeps entity-aware texture selection while 26.1 renders from extracted state. */
public abstract class LegacyMobRenderer<T extends Mob, M extends EntityModel<LegacyEntityRenderState<T>>>
        extends MobRenderer<T, LegacyEntityRenderState<T>, M> {
    protected LegacyMobRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
        super(context, model, shadowRadius);
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

    @Override
    public final Identifier getTextureLocation(LegacyEntityRenderState<T> state) {
        return getTextureLocation(state.entity);
    }

    public abstract Identifier getTextureLocation(T entity);
}
