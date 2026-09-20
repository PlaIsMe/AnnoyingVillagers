package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.EliteHerobrineKnockedEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class EliteHerobrineKnockedRenderer extends RigMobRenderer<EliteHerobrineKnockedEntity> {

    public EliteHerobrineKnockedRenderer(Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(EliteHerobrineKnockedEntity eliteHerobrineKnockedEntity) {
        if (eliteHerobrineKnockedEntity.isDeadOrDying()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID,"textures/entities/elite_herobrine_dead.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/elite_herobrine.png");
        }
    }
}
