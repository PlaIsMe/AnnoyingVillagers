package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.SteveEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class SteveRenderer extends RigMobRenderer<SteveEntity> {

    public SteveRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull SteveEntity steveEntity) {
        if (steveEntity.isDeadOrDying()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/steve_dead.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/steve.png");
        }
    }
}