package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelGolemArm;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class GolemArmsRedGlintLayer extends RenderLayer<LegacyEntityRenderState<GolemArms>, ModelGolemArm> {
    public GolemArmsRedGlintLayer(RenderLayerParent<LegacyEntityRenderState<GolemArms>, ModelGolemArm> parent) {
        super(parent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       LegacyEntityRenderState<GolemArms> state, float yRot, float xRot) {
        collector.submitModel(this.getParentModel(), state, poseStack, ColoredGlintRenderTypes.ENTITY_GLINT_RED,
                packedLight, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
    }
}
