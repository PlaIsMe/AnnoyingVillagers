package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelGolemArm;
import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class GolemArmsEmissiveLayer extends RenderLayer<LegacyEntityRenderState<GolemArms>, ModelGolemArm> {
    private final Identifier texture;

    public GolemArmsEmissiveLayer(RenderLayerParent<LegacyEntityRenderState<GolemArms>, ModelGolemArm> parent, Identifier texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       LegacyEntityRenderState<GolemArms> state, float yRot, float xRot) {
        collector.submitModel(this.getParentModel(), state, poseStack,
                net.minecraft.client.renderer.rendertype.RenderTypes.eyes(this.texture), packedLight,
                OverlayTexture.NO_OVERLAY, state.outlineColor, null);
    }
}
