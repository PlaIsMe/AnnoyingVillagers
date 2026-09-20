package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelHerobrineDragon;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.pla.annoyingvillagers.client.compat.LegacyMobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerobrineDragonRenderer extends LegacyMobRenderer<HerobrineDragonEntity, ModelHerobrineDragon> {
    private static final Identifier BODY_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_dragon/body.png");
    private static final Identifier GLOW_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_dragon/glow.png");
    private static final Identifier DISSOLVE_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_dragon/dissolve.png");

    private static final RenderType DISSOLVE_TYPE = RenderTypes.entityCutoutDissolve(BODY_TEXTURE, DISSOLVE_TEXTURE);

    public HerobrineDragonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new ModelHerobrineDragon(ctx.bakeLayer(ModelHerobrineDragon.LAYER_LOCATION)), 2.0F);
        addLayer(new GlowLayer(this));
        addLayer(new DeathLayer(this));
    }

    @Override
    public boolean shouldRender(@NotNull HerobrineDragonEntity dragon, @NotNull Frustum frustum,
                                double camX, double camY, double camZ) {
        return super.shouldRender(dragon, frustum, camX, camY, camZ);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HerobrineDragonEntity dragon) {
        return BODY_TEXTURE;
    }

    // Let death layer handle rendering during death dissolve
    @Nullable
    @Override
    protected RenderType getRenderType(LegacyEntityRenderState<HerobrineDragonEntity> state,
                                       boolean visible, boolean invisToClient, boolean glowing) {
        return state.deathTime > 0.0F ? null : super.getRenderType(state, visible, invisToClient, glowing);
    }

    @Override
    protected void setupRotations(@NotNull LegacyEntityRenderState<HerobrineDragonEntity> state,
                                  @NotNull PoseStack ps, float yaw, float scale) {
        super.setupRotations(state, ps, yaw, scale);

        HerobrineDragonEntity dragon = state.entity;
        var animator = dragon.getAnimator();
        if (animator != null) {
            ps.translate(animator.getModelOffsetX(), animator.getModelOffsetY(), animator.getModelOffsetZ());
            ps.translate(0, 1.5, 0.5);
            ps.mulPose(Axis.XP.rotationDegrees(animator.getModelPitch(state.partialTick)));
            ps.translate(0, -1.5, -0.5);
        }
    }

    @Override
    protected float getFlipDegrees() {
        return 0;
    }

    private static class GlowLayer extends RenderLayer<LegacyEntityRenderState<HerobrineDragonEntity>, ModelHerobrineDragon> {
        public GlowLayer(HerobrineDragonRenderer parent) {
            super(parent);
        }

        @Override
        public void submit(@NotNull PoseStack ps, @NotNull SubmitNodeCollector collector, int light,
                           @NotNull LegacyEntityRenderState<HerobrineDragonEntity> state,
                           float yRot, float xRot) {
            if (state.deathTime > 0.0F) return;
            collector.submitModel(getParentModel(), state, ps, RenderTypes.eyes(GLOW_TEXTURE), light,
                    OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
        }
    }

    private static class DeathLayer extends RenderLayer<LegacyEntityRenderState<HerobrineDragonEntity>, ModelHerobrineDragon> {
        public DeathLayer(HerobrineDragonRenderer parent) {
            super(parent);
        }

        @Override
        public void submit(@NotNull PoseStack ps, @NotNull SubmitNodeCollector collector, int light,
                           @NotNull LegacyEntityRenderState<HerobrineDragonEntity> state,
                           float yRot, float xRot) {
            if (state.deathTime <= 0.0F) return;

            float delta = state.deathTime / state.entity.getMaxDeathTime();
            collector.submitModel(getParentModel(), state, ps, DISSOLVE_TYPE, light, OverlayTexture.NO_OVERLAY,
                    net.minecraft.util.ARGB.colorFromFloat(delta, 1.0F, 1.0F, 1.0F), null,
                    state.outlineColor, null);
            collector.submitModel(getParentModel(), state, ps, RenderTypes.entityCutout(BODY_TEXTURE), light,
                    OverlayTexture.pack(0, true), -1, null, state.outlineColor, null);
        }
    }
}
