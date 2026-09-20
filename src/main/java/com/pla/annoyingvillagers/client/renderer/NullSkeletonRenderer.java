package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public final class NullSkeletonRenderer extends AbstractSkeletonRenderer<NullSkeletonEntity, SkeletonRenderState> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace(
            "textures/entity/skeleton/wither_skeleton.png");

    public NullSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.SKELETON, ModelLayers.SKELETON_ARMOR);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull SkeletonRenderState state) {
        return TEXTURE;
    }

    @Override
    protected void scale(@NotNull SkeletonRenderState state, @NotNull PoseStack poseStack) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }
}
