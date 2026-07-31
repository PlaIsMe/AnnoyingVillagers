package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AngrySteveRenderer extends RigMobRenderer<AngrySteveEntity> {

    public AngrySteveRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AngrySteveEntity angrySteveEntity) {
        if (angrySteveEntity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/steve_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/angry_steve.png");
        }
    }
}