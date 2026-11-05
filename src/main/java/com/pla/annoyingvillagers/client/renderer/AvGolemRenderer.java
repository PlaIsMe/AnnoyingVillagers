package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.layer.AvGolemArmorLayer;
import com.pla.annoyingvillagers.client.layer.AvGolemItemInHandLayer;
import com.pla.annoyingvillagers.client.model.ModelAvGolem;
import com.pla.annoyingvillagers.client.model.ModelAvGolemArmor;
import com.pla.annoyingvillagers.entity.AvGolem;
import com.pla.annoyingvillagers.entity.IronGolemWarrior;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.pla.annoyingvillagers.client.compat.LegacyMobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class AvGolemRenderer extends LegacyMobRenderer<AvGolem, ModelAvGolem> {
    private static final Identifier DEFAULT_TEXTURE = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/iron_golem/iron_golem.png");

    public AvGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAvGolem(context.bakeLayer(ModelAvGolem.LAYER_LOCATION)), 0.8F);
        this.addLayer(new AvGolemItemInHandLayer(this, new net.minecraft.client.renderer.ItemInHandRenderer(
                net.minecraft.client.Minecraft.getInstance(), context.getEntityRenderDispatcher(), context.getItemModelResolver())));
        this.addLayer(new AvGolemArmorLayer(this, new ModelAvGolemArmor(context.bakeLayer(ModelAvGolemArmor.LAYER_LOCATION))));
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull AvGolem entity) {
        if (entity instanceof IronGolemWarrior) {
            return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/iron_golem_warrior.png");
        }
        return DEFAULT_TEXTURE;
    }

    @Override
    protected float getFlipDegrees() {
        return 0.0F;
    }
}
