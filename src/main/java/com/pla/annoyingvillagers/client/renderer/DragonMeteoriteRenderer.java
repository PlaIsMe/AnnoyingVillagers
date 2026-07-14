package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelDragonMeteorite;
import com.pla.annoyingvillagers.entity.DragonMeteoriteEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DragonMeteoriteRenderer extends MobRenderer<DragonMeteoriteEntity, ModelDragonMeteorite<DragonMeteoriteEntity>> {

    public DragonMeteoriteRenderer(Context context) {
        super(context, new ModelDragonMeteorite<>(context.bakeLayer(ModelDragonMeteorite.LAYER_LOCATION)), 0.0F);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull DragonMeteoriteEntity dragonMeteoriteEntity) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_meteorite.png");
    }

    protected boolean isShaking(@NotNull DragonMeteoriteEntity dragonMeteoriteEntity) {
        return true;
    }
}
