package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.RigMobRenderer;
import com.pla.annoyinglib.entity.VillagerScoutEntity;
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
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/villager_scout_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/villager_scout.png");
        }
    }
}