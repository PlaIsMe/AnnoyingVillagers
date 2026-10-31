package com.pla.annoyinglib.client.renderer;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.clazz.RigMobRenderer;
import com.pla.annoyinglib.entity.AngrySteveEntity;
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
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/steve_dead.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, "textures/entities/angry_steve.png");
        }
    }
}