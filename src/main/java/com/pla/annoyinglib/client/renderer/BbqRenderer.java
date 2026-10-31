package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.client.model.ModelBbq;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import org.jetbrains.annotations.NotNull;

public class BbqRenderer extends ChickenRenderer {
    public BbqRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.model = new ModelBbq<>(context.bakeLayer(ModelLayers.CHICKEN));
        this.addLayer(new BbqHeldItemLayer(this, context.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Chicken entity) {
        return ResourceLocation.fromNamespaceAndPath(
                AnnoyingLib.MODID,
                "textures/entities/chicken.png"
        );
    }
}