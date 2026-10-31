package com.pla.annoyinglib.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyinglib.entity.BlockProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockProjectileRenderer extends EntityRenderer<BlockProjectileEntity> {
    public BlockProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BlockProjectileEntity entity, float yaw, float partialTicks,
                       PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        BlockState block = entity.getCarriedBlock();

        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.translate(-0.5, -0.5, -0.5);

        float age = entity.tickCount + partialTicks;
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRotX() * age));
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRotY() * age));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getRotZ() * age));

        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(block, poseStack, buffer, packedLight,
                OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlockProjectileEntity blockProjectileEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
