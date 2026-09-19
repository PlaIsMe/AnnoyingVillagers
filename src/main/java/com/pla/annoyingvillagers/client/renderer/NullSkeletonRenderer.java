package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class NullSkeletonRenderer extends SkeletonRenderer<NullSkeletonEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace(
            "textures/entity/skeleton/wither_skeleton.png");

    public NullSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NullSkeletonEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(@NotNull NullSkeletonEntity entity, @NotNull PoseStack poseStack, float partialTick) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}
