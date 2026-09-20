package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.pla.annoyingvillagers.entity.DiamondBoltProjectileEntity;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DiamondBoltProjectileRenderer extends LegacyEntityRenderer<DiamondBoltProjectileEntity> {
    private final ItemModelResolver itemModelResolver;

    public DiamondBoltProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
        this.shadowRadius = 0.15F;
    }

    @Override
    public void extractRenderState(DiamondBoltProjectileEntity entity,
                                   LegacyEntityRenderState<DiamondBoltProjectileEntity> state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        this.itemModelResolver.updateForNonLiving(state.item, entity.getPickupItem(), ItemDisplayContext.GROUND, entity);
    }

    @Override
    public void submit(LegacyEntityRenderState<DiamondBoltProjectileEntity> state, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        DiamondBoltProjectileEntity entity = state.entity;
        float partialTick = state.partialTick;
        ItemStack stack = entity.getPickupItem();

        if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
            poseStack.translate(-0.2D, 0.0D, 0.0D);
            poseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F));
            poseStack.scale(1.5F, 1.5F, 1.5F);

            state.item.submit(poseStack, submitNodeCollector, state.lightCoords,
                    OverlayTexture.NO_OVERLAY, state.outlineColor);

            poseStack.popPose();
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull DiamondBoltProjectileEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
