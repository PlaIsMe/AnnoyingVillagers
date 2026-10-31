package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.RigMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import com.pla.annoyinglib.entity.BlueDemonEntity;
import org.jetbrains.annotations.NotNull;

public class BlueDemonRenderer extends RigMobRenderer<BlueDemonEntity> {

    public BlueDemonRenderer(Context context) {
        super(context);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull BlueDemonEntity blueDemonEntity) {
        if (blueDemonEntity.getState() == 2) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/blue_demon_exhausted.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/blue_demon.png");
        }
    }
}
