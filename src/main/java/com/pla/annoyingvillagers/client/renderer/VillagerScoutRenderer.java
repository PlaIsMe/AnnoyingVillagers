package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.VillagerScoutEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VillagerScoutRenderer extends RigMobRenderer<VillagerScoutEntity> {

    public VillagerScoutRenderer(Context context) {
        super(context);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull VillagerScoutEntity villagerScoutEntity) {
        if (villagerScoutEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/villager_scout_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/villager_scout.png");
        }
    }
}