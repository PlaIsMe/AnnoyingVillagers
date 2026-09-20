package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelVacuumSlice;
import com.pla.annoyingvillagers.entity.VacuumSliceEntity;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public final class VacuumSliceRenderer extends LegacyEntityRenderer<VacuumSliceEntity> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/vacuum_slice.png");
    private static final RenderType RENDER_TYPE = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucentEmissive(TEXTURE);
    private final ModelVacuumSlice model = new ModelVacuumSlice();

    public VacuumSliceRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void submit(LegacyEntityRenderState<VacuumSliceEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        VacuumSliceEntity entity = state.entity;
        float partialTick = state.partialTick;
        super.submit(state, poseStack, collector, camera);
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot())));
        float pulse = 1.0F + Mth.sin((entity.tickCount + partialTick) * 0.65F) * 0.025F;
        poseStack.scale(pulse, pulse, pulse);
        int color = net.minecraft.util.ARGB.colorFromFloat(entity.getRenderAlpha(partialTick), 1.0F, 1.0F, 1.0F);
        collector.submitCustomGeometry(poseStack, RENDER_TYPE, (rootPose, consumer) -> {
            PoseStack stack = new PoseStack(); stack.last().set(rootPose);
            this.model.renderToBuffer(stack, consumer, LightCoordsUtil.FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY, color);
        });
        poseStack.popPose();
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull VacuumSliceEntity entity) {
        return TEXTURE;
    }
}
