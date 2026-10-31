package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.RigMobRenderer;
import com.pla.annoyinglib.entity.BlueVillagerKnightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BlueVillagerKnightRenderer extends RigMobRenderer<BlueVillagerKnightEntity> {

    public BlueVillagerKnightRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlueVillagerKnightEntity blueVillagerKnightEntity) {
        if (blueVillagerKnightEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/blue_villager_knight_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/blue_villager_knight.png");
        }
    }
}
