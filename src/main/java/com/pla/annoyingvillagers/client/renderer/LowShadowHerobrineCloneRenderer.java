package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class LowShadowHerobrineCloneRenderer extends RigMobRenderer<LowShadowHerobrineCloneEntity> {

    public LowShadowHerobrineCloneRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull LowShadowHerobrineCloneEntity lowShadowHerobrineCloneEntity) {
        return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
    }
}
