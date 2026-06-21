package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.PurpleVillagerKnightEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PurpleVillagerKnightRenderer extends HumanoidMobRenderer<PurpleVillagerKnightEntity, PlayerModel<PurpleVillagerKnightEntity>> {

    public PurpleVillagerKnightRenderer(Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        ArrowLayer<PurpleVillagerKnightEntity, PlayerModel<PurpleVillagerKnightEntity>> arrowLayer = new ArrowLayer<>(context, this);
        this.addLayer(arrowLayer);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PurpleVillagerKnightEntity purpleVillagerKnightEntity) {
        if (purpleVillagerKnightEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/purple_villager_knight_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/purple_villager_knight.png");
        }
    }
}
