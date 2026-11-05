package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.PurpleVillagerKnightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class PurpleVillagerKnightRenderer extends RigMobRenderer<PurpleVillagerKnightEntity> {

    public PurpleVillagerKnightRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull PurpleVillagerKnightEntity purpleVillagerKnightEntity) {
        if (purpleVillagerKnightEntity.isDeadOrDying()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/purple_villager_knight_dead.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/purple_villager_knight.png");
        }
    }
}
