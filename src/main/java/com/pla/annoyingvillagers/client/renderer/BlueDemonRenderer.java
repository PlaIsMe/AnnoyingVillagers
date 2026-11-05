package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import org.jetbrains.annotations.NotNull;

public class BlueDemonRenderer extends RigMobRenderer<BlueDemonEntity> {

    public BlueDemonRenderer(Context context) {
        super(context);
    }

    public @NotNull Identifier getTextureLocation(@NotNull BlueDemonEntity blueDemonEntity) {
        if (blueDemonEntity.getState() == 2) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_demon_exhausted.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_demon.png");
        }
    }
}
