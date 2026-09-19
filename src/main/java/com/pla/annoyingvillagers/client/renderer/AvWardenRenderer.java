package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.layer.AvWardenEmissiveLayer;
import com.pla.annoyingvillagers.client.model.ModelAvWarden;
import com.pla.annoyingvillagers.entity.AvWarden;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class AvWardenRenderer extends MobRenderer<AvWarden, ModelAvWarden> {
    private static final ResourceLocation BASE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden.png");
    private static final ResourceLocation BIOLUM = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_bioluminescent_layer.png");
    private static final ResourceLocation HEART = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_heart.png");
    private static final ResourceLocation SPOTS1 = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_pulsating_spots_1.png");
    private static final ResourceLocation SPOTS2 = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_pulsating_spots_2.png");

    public AvWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAvWarden(context.bakeLayer(ModelAvWarden.LAYER_LOCATION)), 0.9F);
        this.addLayer(new AvWardenEmissiveLayer(this, BIOLUM, (entity, partialTick) -> 1.0F, false));
        this.addLayer(new AvWardenEmissiveLayer(this, SPOTS1, (entity, partialTick) -> Math.max(0.0F, Mth.cos((entity.tickCount + partialTick) * 0.045F) * 0.25F), false));
        this.addLayer(new AvWardenEmissiveLayer(this, SPOTS2, (entity, partialTick) -> Math.max(0.0F, Mth.cos((entity.tickCount + partialTick) * 0.045F + (float)Math.PI) * 0.25F), false));
        this.addLayer(new AvWardenEmissiveLayer(this, BASE, AvWarden::getTendrilAnimation, true));
        this.addLayer(new AvWardenEmissiveLayer(this, HEART, AvWarden::getHeartAnimation, false));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AvWarden entity) {
        return BASE;
    }

    @Override
    protected float getFlipDegrees(@NotNull AvWarden entity) {
        return 0.0F;
    }
}
