package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.blockentity.FractureBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class FractureBlockRenderer implements BlockEntityRenderer<FractureBlockEntity, FractureBlockRenderer.State> {
    private final BlockModelResolver blockModelResolver;

    public FractureBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(FractureBlockEntity blockEntity, State state, float partialTicks,
                                   Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.visible = blockEntity.getOriginalBlockState() != null && blockEntity.getLevel() != null;
        if (!state.visible) return;

        this.blockModelResolver.update(state.block, blockEntity.getOriginalBlockState(), BlockDisplayContext.create());
        state.partialTicks = partialTicks;
        state.translate.set(blockEntity.getTranslate());
        state.rotation.set(blockEntity.getRotation());
        state.bouncing = blockEntity.getBouncing();
        state.maxLifeTime = blockEntity.getMaxLifeTime();
        state.lifeTime = blockEntity.getLifeTime();
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        if (!state.visible) return;

        float turnBackTime = 5.0F;
        float lerpAmount = Mth.clamp(state.partialTicks / turnBackTime
                + (turnBackTime - (state.maxLifeTime - state.lifeTime)) / turnBackTime, 0.0F, 1.0F);
        Vector3f translate = state.maxLifeTime > state.lifeTime + turnBackTime
                ? state.translate : lerpVector(state.translate, new Vector3f(), lerpAmount);
        Quaternionf rotation = state.maxLifeTime > state.lifeTime + turnBackTime
                ? state.rotation : lerpQuaternion(state.rotation, new Quaternionf(), lerpAmount);

        double time = Math.max(state.bouncing * 8.0D, 8.0D);
        double extender = 1.0D / Math.pow(time * 0.5D, 2.0D);
        double moveGraph = Math.sqrt(state.bouncing / extender);
        double bouncingAnimation = Math.max(-extender * Math.pow(state.lifeTime + state.partialTicks - moveGraph, 2.0D)
                + state.bouncing, 0.0D);

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.mulPose(rotation);
        poseStack.translate(translate.x(), translate.y() + bouncingAnimation, translate.z());
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        state.block.submitMultiLayer(poseStack, submitNodeCollector, state.lightCoords,
                OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    private static Vector3f lerpVector(Vector3f from, Vector3f to, float delta) {
        return new Vector3f(from.x() + (to.x() - from.x()) * delta,
                from.y() + (to.y() - from.y()) * delta,
                from.z() + (to.z() - from.z()) * delta);
    }

    private static Quaternionf lerpQuaternion(Quaternionf from, Quaternionf to, float delta) {
        float dot = from.w() * to.w() + from.x() * to.x() + from.y() * to.y() + from.z() * to.z();
        float inverse = 1.0F - delta;
        return new Quaternionf(
                inverse * from.x() + delta * (dot < 0.0F ? -to.x() : to.x()),
                inverse * from.y() + delta * (dot < 0.0F ? -to.y() : to.y()),
                inverse * from.z() + delta * (dot < 0.0F ? -to.z() : to.z()),
                inverse * from.w() + delta * (dot < 0.0F ? -to.w() : to.w())).normalize();
    }

    public static final class State extends BlockEntityRenderState {
        final BlockModelRenderState block = new BlockModelRenderState();
        final Vector3f translate = new Vector3f();
        final Quaternionf rotation = new Quaternionf();
        double bouncing;
        int maxLifeTime;
        int lifeTime;
        float partialTicks;
        boolean visible;
    }
}
