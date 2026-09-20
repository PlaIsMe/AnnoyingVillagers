package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class EliteHerobrineRenderer extends RigMobRenderer<HerobrineMob> {

    public EliteHerobrineRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HerobrineMob herobrineMob) {
        return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/elite_herobrine.png");
    }
}
