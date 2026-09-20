package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class ShadowHerobrineRenderer extends RigMobRenderer<HerobrineMob> {

    public ShadowHerobrineRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HerobrineMob herobrineMob) {
        if (herobrineMob instanceof ShadowHerobrineEntity) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dark_shadow_herobrine.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/shadow_herobrine.png");
        }
    }
}
