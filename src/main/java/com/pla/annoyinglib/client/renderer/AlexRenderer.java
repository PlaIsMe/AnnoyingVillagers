package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.RigMobRenderer;
import com.pla.annoyinglib.client.model.ModelRig;
import com.pla.annoyinglib.entity.AlexEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AlexRenderer extends RigMobRenderer<AlexEntity> {

    public AlexRenderer(Context context) {
        super(context, ModelRig.SLIM_LAYER_LOCATION, true);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AlexEntity alexEntity) {
        if (alexEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/alex_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/alex.png");
        }
    }
}
