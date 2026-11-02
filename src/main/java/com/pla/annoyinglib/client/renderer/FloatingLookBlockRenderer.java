package com.pla.annoyinglib.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyinglib.entity.FloatingLookBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FloatingLookBlockRenderer extends EntityRenderer<FloatingLookBlockEntity> {
    public FloatingLookBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.35F;
    }

    @Override
    public void render(
            FloatingLookBlockEntity entity,
            float entityYaw,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight
    ) {
        BlockState blockState = entity.getCarriedBlock();

        if (blockState.isAir() || blockState.getRenderShape() != RenderShape.MODEL) {
            return;
        }

        poseStack.pushPose();

        float age = entity.tickCount + partialTicks;

        if (entity.getPhase() == FloatingLookBlockEntity.PHASE_FLOATING) {
            poseStack.mulPose(Axis.YP.rotationDegrees(age * 2.5F));
        } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(age * 1.0F));
        }

        poseStack.translate(-0.5D, -0.5D, -0.5D);

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                blockState,
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FloatingLookBlockEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
