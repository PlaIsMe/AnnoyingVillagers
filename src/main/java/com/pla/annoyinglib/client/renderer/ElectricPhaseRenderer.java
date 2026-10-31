package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.entity.ElectricPhaseEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ElectricPhaseRenderer extends EntityRenderer<ElectricPhaseEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/empty.png");

    public ElectricPhaseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ElectricPhaseEntity dragonBeam) {
        return TEXTURE;
    }
}