package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.layer.AvGolemArmorLayer;
import com.pla.annoyingvillagers.client.layer.AvGolemItemInHandLayer;
import com.pla.annoyingvillagers.client.model.ModelAvGolem;
import com.pla.annoyingvillagers.client.model.ModelAvGolemArmor;
import com.pla.annoyingvillagers.entity.GolemWarriors;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AvGolemRenderer extends MobRenderer<GolemWarriors, ModelAvGolem> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/iron_golem/iron_golem.png");

    public AvGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAvGolem(context.bakeLayer(ModelAvGolem.LAYER_LOCATION)), 0.8F);
        this.addLayer(new AvGolemItemInHandLayer(this, context.getItemInHandRenderer()));
        this.addLayer(new AvGolemArmorLayer(this, new ModelAvGolemArmor(context.bakeLayer(ModelAvGolemArmor.LAYER_LOCATION))));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GolemWarriors entity) {
        return TEXTURE;
    }
}
