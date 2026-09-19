package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BlackFireRenderer extends EntityRenderer<BlackFireEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");

    public BlackFireRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull BlackFireEntity dragonBeam) {
        return TEXTURE;
    }
}