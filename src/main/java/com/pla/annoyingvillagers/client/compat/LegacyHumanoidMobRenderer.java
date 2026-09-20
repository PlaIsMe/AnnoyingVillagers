package com.pla.annoyingvillagers.client.compat;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

/** Humanoid renderer bridge used by the existing rig renderers. */
public abstract class LegacyHumanoidMobRenderer<T extends Mob, M extends HumanoidModel<LegacyEntityRenderState<T>>>
        extends HumanoidMobRenderer<T, LegacyEntityRenderState<T>, M> {
    protected LegacyHumanoidMobRenderer(EntityRendererProvider.Context context, M model, float shadowRadius) {
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
