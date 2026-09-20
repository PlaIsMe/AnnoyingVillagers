package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.VillagerScoutCaptainEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class VillagerScoutCaptainRenderer extends RigMobRenderer<VillagerScoutCaptainEntity> {

    public VillagerScoutCaptainRenderer(Context context) {
        super(context);
    }

    public @NotNull Identifier getTextureLocation(@NotNull VillagerScoutCaptainEntity villagerScoutCaptainEntity) {
        if (villagerScoutCaptainEntity.isDeadOrDying()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/villager_scout_dead.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/villager_scout.png");
        }
    }
}

