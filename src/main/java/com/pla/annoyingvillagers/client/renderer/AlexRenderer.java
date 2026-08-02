package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.client.model.ModelRig;
import com.pla.annoyingvillagers.entity.AlexEntity;
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
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/alex_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/alex.png");
        }
    }
}
