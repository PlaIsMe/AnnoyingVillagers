package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.model.ModelBbq;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public final class BbqHeldItemLayer extends RenderLayer<BbqRenderState, ModelBbq> {
    public BbqHeldItemLayer(RenderLayerParent<BbqRenderState, ModelBbq> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                       BbqRenderState state, float yRot, float xRot) {
        submitAtBeak(poseStack, collector, lightCoords, state, false);
        submitAtBeak(poseStack, collector, lightCoords, state, true);
    }

    private void submitAtBeak(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords,
                              BbqRenderState state, boolean offHand) {
        var item = offHand ? state.offHand : state.mainHand;
        if (item.isEmpty()) return;
        poseStack.pushPose();
        this.getParentModel().translateToBeak(poseStack);
        poseStack.translate(offHand ? 0.0D : -0.8D, -0.1875D, -0.1875D);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        item.submit(poseStack, collector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
    }
}
