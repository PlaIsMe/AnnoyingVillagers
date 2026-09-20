package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.pla.annoyingvillagers.entity.ShockWaveBlockEntity;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class ShockWaveBlockRenderer extends LegacyEntityRenderer<ShockWaveBlockEntity> {
    public ShockWaveBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void extractRenderState(ShockWaveBlockEntity entity,
                                   LegacyEntityRenderState<ShockWaveBlockEntity> state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        BlockPos renderPos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
        state.movingBlock.randomSeedPos = entity.getSourceBlockPos();
        state.movingBlock.blockPos = renderPos;
        state.movingBlock.blockState = entity.getBlockState();
        if (entity.level() instanceof ClientLevel level) {
            state.movingBlock.biome = level.getBiome(renderPos);
            state.movingBlock.cardinalLighting = level.cardinalLighting();
            state.movingBlock.lightEngine = level.getLightEngine();
        }
    }

    @Override
    public void submit(LegacyEntityRenderState<ShockWaveBlockEntity> state, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        ShockWaveBlockEntity entity = state.entity;
        BlockState blockState = entity.getBlockState();
        if (blockState.getRenderShape() != RenderShape.MODEL || blockState.getRenderShape() == RenderShape.INVISIBLE) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(-0.5D, 0.0D, -0.5D);
        submitNodeCollector.submitMovingBlock(poseStack, state.movingBlock);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull ShockWaveBlockEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
