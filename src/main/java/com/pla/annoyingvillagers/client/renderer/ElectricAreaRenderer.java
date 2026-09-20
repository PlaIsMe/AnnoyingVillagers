package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.ElectricAreaEntity;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class ElectricAreaRenderer extends LegacyEntityRenderer<ElectricAreaEntity> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");

    public ElectricAreaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Identifier getTextureLocation(@NotNull ElectricAreaEntity dragonBeam) {
        return TEXTURE;
    }
}
