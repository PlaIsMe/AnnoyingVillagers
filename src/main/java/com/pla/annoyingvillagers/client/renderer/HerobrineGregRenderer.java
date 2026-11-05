package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.RigMobRenderer;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class HerobrineGregRenderer extends RigMobRenderer<HerobrineGregEntity> {

    public HerobrineGregRenderer(Context context) {
        super(context);
    }

    public @NotNull Identifier getTextureLocation(HerobrineGregEntity herobrineGregEntity) {
        if ((herobrineGregEntity.isUseHerobrineTexture() || herobrineGregEntity.isSupportingHerobrine()) && !herobrineGregEntity.isHooked()) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/transporter_herobrine.png");
        } else {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/greg.png");
        }
    }
}
