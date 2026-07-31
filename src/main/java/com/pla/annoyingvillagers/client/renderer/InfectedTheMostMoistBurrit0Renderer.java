package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.InfectedTheMostMoistBurrit0Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class InfectedTheMostMoistBurrit0Renderer extends RigMobRenderer<InfectedTheMostMoistBurrit0Entity> {

    public InfectedTheMostMoistBurrit0Renderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull InfectedTheMostMoistBurrit0Entity infectedTheMostMoistBurrit0Entity) {
        if (infectedTheMostMoistBurrit0Entity.isDeadOrDying()) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,"textures/entities/infected_themostmoistburrit0.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/infected_themostmoistburrit0.png");
        }
    }
}
