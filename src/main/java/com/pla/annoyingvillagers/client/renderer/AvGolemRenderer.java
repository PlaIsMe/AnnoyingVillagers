package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.layer.AvGolemArmorLayer;
import com.pla.annoyingvillagers.client.layer.AvGolemItemInHandLayer;
import com.pla.annoyingvillagers.client.model.ModelAvGolem;
import com.pla.annoyingvillagers.client.model.ModelAvGolemArmor;
import com.pla.annoyingvillagers.entity.AvGolem;
import com.pla.annoyingvillagers.entity.IronGolemWarrior;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AvGolemRenderer extends MobRenderer<AvGolem, ModelAvGolem> {
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/iron_golem/iron_golem.png");

    public AvGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAvGolem(context.bakeLayer(ModelAvGolem.LAYER_LOCATION)), 0.8F);
        this.addLayer(new AvGolemItemInHandLayer(this, context.getItemInHandRenderer()));
        this.addLayer(new AvGolemArmorLayer(this, new ModelAvGolemArmor(context.bakeLayer(ModelAvGolemArmor.LAYER_LOCATION))));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AvGolem entity) {
        if (entity instanceof IronGolemWarrior) {
            return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/iron_golem_warrior.png");
        }
        return DEFAULT_TEXTURE;
    }
}
