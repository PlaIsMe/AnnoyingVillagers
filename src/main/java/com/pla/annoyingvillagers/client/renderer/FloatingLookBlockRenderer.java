package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.entity.FloatingLookBlockEntity;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FloatingLookBlockRenderer extends LegacyEntityRenderer<FloatingLookBlockEntity> {
    public FloatingLookBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.35F;
    }

    @Override
    public void submit(LegacyEntityRenderState<FloatingLookBlockEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        FloatingLookBlockEntity entity = state.entity;
        float partialTicks = state.partialTick;
        super.submit(state, poseStack, collector, camera);
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

        collector.submitMovingBlock(poseStack, state.movingBlock);

        poseStack.popPose();

    }

    @Override
    public void extractRenderState(FloatingLookBlockEntity entity, LegacyEntityRenderState<FloatingLookBlockEntity> state,
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
    public @NotNull Identifier getTextureLocation(@NotNull FloatingLookBlockEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
