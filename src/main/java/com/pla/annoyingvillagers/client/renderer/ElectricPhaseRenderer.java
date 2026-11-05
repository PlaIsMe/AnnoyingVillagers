package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class ElectricPhaseRenderer extends LegacyEntityRenderer<ElectricPhaseEntity> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");

    public ElectricPhaseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Identifier getTextureLocation(@NotNull ElectricPhaseEntity dragonBeam) {
        return TEXTURE;
    }
}
