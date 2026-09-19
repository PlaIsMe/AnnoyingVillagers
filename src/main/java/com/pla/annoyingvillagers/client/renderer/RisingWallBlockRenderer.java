package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.entity.RisingWallBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class RisingWallBlockRenderer extends EntityRenderer<RisingWallBlockEntity> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public RisingWallBlockRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(
            RisingWallBlockEntity entity,
            float entityYaw,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int packedLight
    ) {
        if (!entity.isRiseStarted()) {
            return;
        }
        BlockState blockState = entity.getBlockState();
        if (blockState.getRenderShape() != RenderShape.MODEL || blockState.getRenderShape() == RenderShape.INVISIBLE) {
            return;
        }

        Level level = entity.level();

        poseStack.pushPose();
        poseStack.translate(-0.5D, 0.0D, -0.5D);

        BlockPos renderPos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());

        var model = this.blockRenderDispatcher.getBlockModel(blockState);

        BlockPos seedPos = entity.getFinalBlockPos();
        long seed = blockState.getSeed(seedPos);
        RandomSource seededRandom = RandomSource.create(seed);

        for (var renderType : model.getRenderTypes(blockState, seededRandom, ModelData.EMPTY)) {
            this.blockRenderDispatcher.getModelRenderer().tesselateBlock(
                    level,
                    model,
                    blockState,
                    renderPos,
                    poseStack,
                    bufferSource.getBuffer(renderType),
                    false,
                    RandomSource.create(),
                    seed,
                    OverlayTexture.NO_OVERLAY,
                    ModelData.EMPTY,
                    renderType
            );
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull RisingWallBlockEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}