package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.layer.AvWardenEmissiveLayer;
import com.pla.annoyingvillagers.client.model.ModelAvWarden;
import com.pla.annoyingvillagers.entity.AvWarden;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.pla.annoyingvillagers.client.compat.LegacyMobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class AvWardenRenderer extends LegacyMobRenderer<AvWarden, ModelAvWarden> {
    private static final Identifier BASE = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden.png");
    private static final Identifier BIOLUM = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_bioluminescent_layer.png");
    private static final Identifier HEART = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_heart.png");
    private static final Identifier SPOTS1 = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_pulsating_spots_1.png");
    private static final Identifier SPOTS2 = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden_pulsating_spots_2.png");

    public AvWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAvWarden(context.bakeLayer(ModelAvWarden.LAYER_LOCATION)), 0.9F);
        this.addLayer(new AvWardenEmissiveLayer(this, BIOLUM, (entity, partialTick) -> 1.0F, false));
        this.addLayer(new AvWardenEmissiveLayer(this, SPOTS1, (entity, partialTick) -> Math.max(0.0F, Mth.cos((entity.tickCount + partialTick) * 0.045F) * 0.25F), false));
        this.addLayer(new AvWardenEmissiveLayer(this, SPOTS2, (entity, partialTick) -> Math.max(0.0F, Mth.cos((entity.tickCount + partialTick) * 0.045F + (float)Math.PI) * 0.25F), false));
        this.addLayer(new AvWardenEmissiveLayer(this, BASE, AvWarden::getTendrilAnimation, true));
        this.addLayer(new AvWardenEmissiveLayer(this, HEART, AvWarden::getHeartAnimation, false));
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull AvWarden entity) {
        return BASE;
    }

    @Override
    protected float getFlipDegrees() {
        return 0.0F;
    }
}
