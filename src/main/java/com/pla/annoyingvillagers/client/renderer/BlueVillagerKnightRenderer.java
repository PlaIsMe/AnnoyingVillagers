package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlueVillagerKnightEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BlueVillagerKnightRenderer extends HumanoidMobRenderer<BlueVillagerKnightEntity, PlayerModel<BlueVillagerKnightEntity>> {

    public BlueVillagerKnightRenderer(Context context) {
        super(context, new PlayerModel<>(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
        ArrowLayer<BlueVillagerKnightEntity, PlayerModel<BlueVillagerKnightEntity>> arrowLayer = new ArrowLayer<>(context, this);
        this.addLayer(arrowLayer);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlueVillagerKnightEntity blueVillagerKnightEntity) {
        if (blueVillagerKnightEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_villager_knight_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_villager_knight.png");
        }
    }
}
