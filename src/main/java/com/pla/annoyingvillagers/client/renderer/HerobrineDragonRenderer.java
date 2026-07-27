/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 *
 * Upstream: Dragon Mounts: Legacy - Nico Bergemann (BarracudaATA), Kay9, contributors
 * Source: https://github.com/MWall541/Dragon-Mounts-Legacy
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/GPL-3.0.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelHerobrineDragon;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerobrineDragonRenderer extends MobRenderer<HerobrineDragonEntity, ModelHerobrineDragon> {
    private static final ResourceLocation BODY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_dragon/body.png");
    private static final ResourceLocation GLOW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_dragon/glow.png");
    private static final ResourceLocation DISSOLVE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine_dragon/dissolve.png");

    private static final RenderType DISSOLVE_TYPE = RenderType.dragonExplosionAlpha(DISSOLVE_TEXTURE);

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
    public @NotNull ResourceLocation getTextureLocation(@NotNull HerobrineDragonEntity dragon) {
        return BODY_TEXTURE;
    }

    // Let death layer handle rendering during death dissolve
    @Nullable
    @Override
    protected RenderType getRenderType(HerobrineDragonEntity entity, boolean visible, boolean invisToClient, boolean glowing) {
        return entity.deathTime > 0 ? null : super.getRenderType(entity, visible, invisToClient, glowing);
    }

    @Override
    protected void setupRotations(@NotNull HerobrineDragonEntity dragon, @NotNull PoseStack ps,
                                  float ageInTicks, float yaw, float partialTicks) {
        super.setupRotations(dragon, ps, ageInTicks, yaw, partialTicks);

        var animator = dragon.getAnimator();
        if (animator != null) {
            ps.translate(animator.getModelOffsetX(), animator.getModelOffsetY(), animator.getModelOffsetZ());
            ps.translate(0, 1.5, 0.5);
            ps.mulPose(Axis.XP.rotationDegrees(animator.getModelPitch(partialTicks)));
            ps.translate(0, -1.5, -0.5);
        }
    }

    @Override
    protected float getFlipDegrees(@NotNull HerobrineDragonEntity entity) {
        return 0;
    }

    private static class GlowLayer extends RenderLayer<HerobrineDragonEntity, ModelHerobrineDragon> {
        public GlowLayer(HerobrineDragonRenderer parent) {
            super(parent);
        }

        @Override
        public void render(@NotNull PoseStack ps, @NotNull MultiBufferSource buffer, int light,
                           @NotNull HerobrineDragonEntity dragon,
                           float limbSwing, float limbSwingAmount, float partialTicks,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            if (dragon.deathTime > 0) return;

            // Simple glow
            RenderType type = RenderType.eyes(GLOW_TEXTURE);
            getParentModel().renderToBuffer(ps, buffer.getBuffer(type), light, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, 1f);
        }
    }

    private static class DeathLayer extends RenderLayer<HerobrineDragonEntity, ModelHerobrineDragon> {
        public DeathLayer(HerobrineDragonRenderer parent) {
            super(parent);
        }

        @Override
        public void render(@NotNull PoseStack ps, @NotNull MultiBufferSource buffer, int light,
                           @NotNull HerobrineDragonEntity dragon,
                           float limbSwing, float limbSwingAmount, float partialTicks,
                           float ageInTicks, float netHeadYaw, float headPitch) {
            if (dragon.deathTime <= 0) return;

            float delta = dragon.deathTime / (float) dragon.getMaxDeathTime();
            getParentModel().renderToBuffer(ps, buffer.getBuffer(DISSOLVE_TYPE), light, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, delta);

            getParentModel().renderToBuffer(ps, buffer.getBuffer(RenderType.entityDecal(BODY_TEXTURE)), light,
                    OverlayTexture.pack(0, true), 1f, 1f, 1f, 1f);
        }
    }
}