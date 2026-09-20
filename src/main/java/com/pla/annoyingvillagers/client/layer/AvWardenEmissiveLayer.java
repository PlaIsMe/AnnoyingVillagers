package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelAvWarden;
import com.pla.annoyingvillagers.entity.AvWarden;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

import java.util.function.BiFunction;

public class AvWardenEmissiveLayer extends RenderLayer<LegacyEntityRenderState<AvWarden>, ModelAvWarden> {
    private final Identifier texture;
    private final BiFunction<AvWarden, Float, Float> alphaFunction;
    private final boolean tendrilsOnly;

    public AvWardenEmissiveLayer(RenderLayerParent<LegacyEntityRenderState<AvWarden>, ModelAvWarden> parent, Identifier texture, BiFunction<AvWarden, Float, Float> alphaFunction, boolean tendrilsOnly) {
        super(parent);
        this.texture = texture;
        this.alphaFunction = alphaFunction;
        this.tendrilsOnly = tendrilsOnly;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       LegacyEntityRenderState<AvWarden> state, float yRot, float xRot) {
        float alpha = this.alphaFunction.apply(state.entity, state.partialTick);
        if (alpha <= 0.0F) return;
        RenderType renderType = net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucentEmissive(this.texture);
        if (this.tendrilsOnly) {
            collector.submitCustomGeometry(poseStack, renderType, (rootPose, consumer) -> {
                PoseStack stack = new PoseStack();
                stack.last().set(rootPose);
                this.getParentModel().renderTendrils(stack, consumer, packedLight, OverlayTexture.NO_OVERLAY, alpha);
            });
        } else {
            collector.submitModel(this.getParentModel(), state, poseStack, renderType, packedLight,
                    OverlayTexture.NO_OVERLAY, net.minecraft.util.ARGB.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F),
                    null, state.outlineColor, null);
        }
    }
}
