package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import com.pla.annoyingvillagers.entity.HerobrineCloneEntity;
import org.jetbrains.annotations.NotNull;

public class HerobrineCloneRenderer extends RigMobRenderer<HerobrineCloneEntity> {

    public HerobrineCloneRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HerobrineCloneEntity herobrineCloneEntity) {
        return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/herobrine.png");
    }
}
