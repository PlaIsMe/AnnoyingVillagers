package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.RigMobRenderer;
import com.pla.annoyinglib.entity.VillagerScoutCaptainEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VillagerScoutCaptainRenderer extends RigMobRenderer<VillagerScoutCaptainEntity> {

    public VillagerScoutCaptainRenderer(Context context) {
        super(context);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull VillagerScoutCaptainEntity villagerScoutCaptainEntity) {
        if (villagerScoutCaptainEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/villager_scout_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/villager_scout.png");
        }
    }
}

