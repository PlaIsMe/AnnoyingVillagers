package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.client.layer.VillagerRigArrowLayer;
import com.pla.annoyingvillagers.client.layer.VillagerRigItemInHandLayer;
import com.pla.annoyingvillagers.client.compat.LegacyHumanoidMobRenderer;
import com.pla.annoyingvillagers.client.model.ModelRigArmor;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import com.pla.annoyingvillagers.client.renderer.RigLighting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;

public abstract class RigVillagerRenderer<T extends Mob> extends LegacyHumanoidMobRenderer<T, ModelRigVillager<T>> {

    protected RigVillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelRigVillager<>(context.bakeLayer(ModelRigVillager.LAYER_LOCATION)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new),
                context.getEquipmentRenderer()));
        this.layers.removeIf(layer -> layer instanceof ItemInHandLayer<?, ?>);
        this.addLayer(new VillagerRigItemInHandLayer<>(this, new net.minecraft.client.renderer.ItemInHandRenderer(
                net.minecraft.client.Minecraft.getInstance(), context.getEntityRenderDispatcher(), context.getItemModelResolver())));
        this.addLayer(new VillagerRigArrowLayer<>(context, this));
    }

    @Override
    protected int getBlockLightLevel(@NotNull T entity, @NotNull BlockPos pos) {
        return entity.isOnFire() ? 15 : RigLighting.getBrightness(entity.level(), LightLayer.BLOCK, pos);
    }

    @Override
    protected int getSkyLightLevel(@NotNull T entity, @NotNull BlockPos pos) {
        return RigLighting.getBrightness(entity.level(), LightLayer.SKY, pos);
    }

    @Override
    protected float getFlipDegrees() {
        return 0.0F;
    }
}
