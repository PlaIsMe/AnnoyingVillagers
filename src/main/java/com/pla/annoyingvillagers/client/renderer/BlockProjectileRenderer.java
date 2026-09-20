package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.entity.BlockProjectileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockProjectileRenderer extends LegacyEntityRenderer<BlockProjectileEntity> {
    public BlockProjectileRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void submit(LegacyEntityRenderState<BlockProjectileEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        BlockProjectileEntity entity = state.entity;
        float partialTicks = state.partialTick;
        super.submit(state, poseStack, collector, camera);
        BlockState block = entity.getCarriedBlock();

        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        poseStack.translate(-0.5, -0.5, -0.5);

        float age = entity.tickCount + partialTicks;
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getRotX() * age));
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getRotY() * age));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getRotZ() * age));

        collector.submitMovingBlock(poseStack, state.movingBlock);

        poseStack.popPose();
    }

    @Override
    public void extractRenderState(BlockProjectileEntity entity, LegacyEntityRenderState<BlockProjectileEntity> state,
                                   float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        BlockPos pos = entity.blockPosition();
        state.movingBlock.randomSeedPos = pos;
        state.movingBlock.blockPos = pos;
        state.movingBlock.blockState = entity.getCarriedBlock();
        if (entity.level() instanceof ClientLevel level) {
            state.movingBlock.biome = level.getBiome(pos);
            state.movingBlock.cardinalLighting = level.cardinalLighting();
            state.movingBlock.lightEngine = level.getLightEngine();
        }
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull BlockProjectileEntity blockProjectileEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
