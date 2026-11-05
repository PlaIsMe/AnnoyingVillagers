package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.BlueVillagerKnightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class BlueVillagerKnightRenderer extends RigMobRenderer<BlueVillagerKnightEntity> {

    public BlueVillagerKnightRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull BlueVillagerKnightEntity blueVillagerKnightEntity) {
        if (blueVillagerKnightEntity.isDeadOrDying()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_villager_knight_dead.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/blue_villager_knight.png");
        }
    }
}
