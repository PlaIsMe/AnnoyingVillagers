package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.layer.GolemArmsEmissiveLayer;
import com.pla.annoyingvillagers.client.model.ModelGolemArm;
import com.pla.annoyingvillagers.entity.GolemArms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GolemArmsRenderer extends MobRenderer<GolemArms, ModelGolemArm> {
    private static final ResourceLocation BASE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/iron_golem/iron_golem.png");
    private static final ResourceLocation EMISSIVE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/golem_arms_emissive.png");

    public GolemArmsRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelGolemArm(context.bakeLayer(ModelGolemArm.LAYER_LOCATION)), 0.0F);
        this.addLayer(new GolemArmsEmissiveLayer(this, EMISSIVE));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GolemArms entity) {
        return BASE;
    }
}
