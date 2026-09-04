package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class NullSkeletonRenderer extends RigMobRenderer<NullSkeletonEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/skeleton/wither_skeleton.png");

    public NullSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NullSkeletonEntity entity) {
        return TEXTURE;
    }
}
