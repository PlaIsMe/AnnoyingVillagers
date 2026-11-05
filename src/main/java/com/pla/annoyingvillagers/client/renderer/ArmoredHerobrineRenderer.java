package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.ArmoredHerobrineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class ArmoredHerobrineRenderer extends RigMobRenderer<ArmoredHerobrineEntity> {

    public ArmoredHerobrineRenderer(Context context) {
        super(context);
    }

    public @NotNull Identifier getTextureLocation(@NotNull ArmoredHerobrineEntity armoredHerobrineEntity) {
        return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
    }
}
