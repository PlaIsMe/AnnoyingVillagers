package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ElectricAreaEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ElectricAreaRenderer extends EntityRenderer<ElectricAreaEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");

    public ElectricAreaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ElectricAreaEntity dragonBeam) {
        return TEXTURE;
    }
}