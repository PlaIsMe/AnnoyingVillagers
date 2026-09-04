package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.client.layer.RigItemInHandLayer;
import com.pla.annoyingvillagers.client.model.ModelRig;
import net.minecraft.core.BlockPos;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class NullWeaponRenderer extends HumanoidMobRenderer<NullWeapon, ModelRig<NullWeapon>> {
    private static final double MAX_RENDER_DISTANCE_SQR = 128.0D * 128.0D;

    public NullWeaponRenderer(Context context) {
        super(context, new ModelRig<>(context.bakeLayer(ModelRig.LAYER_LOCATION)), 0.0F);
        this.layers.removeIf(layer -> layer instanceof ItemInHandLayer<?, ?>);
        this.addLayer(new RigItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public boolean shouldRender(@NotNull NullWeapon entity, @NotNull Frustum frustum, double x, double y, double z) {
        if (entity.distanceToSqr(x, y, z) > MAX_RENDER_DISTANCE_SQR) return false;

        AABB renderBounds = new AABB(entity.getX() - 2.0D, entity.getY() - 2.0D, entity.getZ() - 2.0D, entity.getX() + 2.0D, entity.getY() + 2.0D, entity.getZ() + 2.0D);
        return frustum.isVisible(renderBounds);
    }

    @Override
    protected int getBlockLightLevel(@NotNull NullWeapon entity, @NotNull BlockPos pos) {
        return 15;
    }

    @Override
    protected float getFlipDegrees(@NotNull NullWeapon entity) {
        return 0.0F;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NullWeapon nullWeapon) {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/empty.png");
    }
}
