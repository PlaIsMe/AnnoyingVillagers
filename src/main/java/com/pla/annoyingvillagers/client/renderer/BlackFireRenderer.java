package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class BlackFireRenderer extends LegacyEntityRenderer<BlackFireEntity> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");

    public BlackFireRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Identifier getTextureLocation(@NotNull BlackFireEntity dragonBeam) {
        return TEXTURE;
    }
}
