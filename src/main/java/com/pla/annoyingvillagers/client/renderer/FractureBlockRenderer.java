package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.blockentity.FractureBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FractureBlockRenderer implements BlockEntityRenderer<FractureBlockEntity> {
    private final BlockRenderDispatcher blockRenderDispatcher;

    public FractureBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public boolean shouldRender(FractureBlockEntity blockEntity, Vec3 cameraPos) {
        return Vec3.atCenterOf(blockEntity.getBlockPos()).closerThan(cameraPos, this.getViewDistance());
    }

    @Override
    public void render(FractureBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int lightColor, int overlayColor) {
        if (blockEntity.getOriginalBlockState() == null || blockEntity.getLevel() == null) return;

        float turnBackTime = 5.0F;
        float lerpAmount = Mth.clamp(partialTicks / turnBackTime + (turnBackTime - (blockEntity.getMaxLifeTime() - blockEntity.getLifeTime())) / turnBackTime, 0.0F, 1.0F);
        Vector3f translate = blockEntity.getMaxLifeTime() > blockEntity.getLifeTime() + turnBackTime ? blockEntity.getTranslate() : lerpVector(blockEntity.getTranslate(), new Vector3f(), lerpAmount);
        Quaternionf rotation = blockEntity.getMaxLifeTime() > blockEntity.getLifeTime() + turnBackTime ? blockEntity.getRotation() : lerpQuaternion(blockEntity.getRotation(), new Quaternionf(), lerpAmount);

        double bounceMaxHeight = blockEntity.getBouncing();
        double time = Math.max(bounceMaxHeight * 8.0D, 8.0D);
        double extender = 1.0D / Math.pow(time * 0.5D, 2.0D);
        double moveGraph = Math.sqrt(bounceMaxHeight / extender);
        double bouncingAnimation = Math.max(-extender * Math.pow(blockEntity.getLifeTime() + partialTicks - moveGraph, 2.0D) + bounceMaxHeight, 0.0D);

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.mulPose(rotation);
        poseStack.translate(translate.x(), translate.y() + bouncingAnimation, translate.z());
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        this.blockRenderDispatcher.renderBreakingTexture(blockEntity.getOriginalBlockState(), blockEntity.getBlockPos().above(), blockEntity.getLevel(), poseStack, bufferSource.getBuffer(RenderType.cutout()), ModelData.EMPTY);
        poseStack.popPose();
    }

    private static Vector3f lerpVector(Vector3f from, Vector3f to, float delta) {
        return new Vector3f(from.x() + (to.x() - from.x()) * delta, from.y() + (to.y() - from.y()) * delta, from.z() + (to.z() - from.z()) * delta);
    }

    private static Quaternionf lerpQuaternion(Quaternionf from, Quaternionf to, float delta) {
        float dot = from.w() * to.w() + from.x() * to.x() + from.y() * to.y() + from.z() * to.z();
        float inverse = 1.0F - delta;
        float x = inverse * from.x() + delta * (dot < 0.0F ? -to.x() : to.x());
        float y = inverse * from.y() + delta * (dot < 0.0F ? -to.y() : to.y());
        float z = inverse * from.z() + delta * (dot < 0.0F ? -to.z() : to.z());
        float w = inverse * from.w() + delta * (dot < 0.0F ? -to.w() : to.w());
        return new Quaternionf(x, y, z, w).normalize();
    }
}
