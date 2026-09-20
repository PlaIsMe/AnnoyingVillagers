package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class HumanoidMobVanillaLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {
    public HumanoidMobVanillaLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       S state, float yRot, float xRot) {
        Identifier texture = VanillaOverlayRenderStateCache.get(state);
        if (texture == null) {
            return;
        }

        RenderType renderType = VanillaOverlayTexturePicker.isBloodTexture(texture)
                ? net.minecraft.client.renderer.rendertype.RenderTypes.entityCutout(texture)
                : net.minecraft.client.renderer.rendertype.RenderTypes.eyes(texture);
        collector.submitModel(this.getParentModel(), state, poseStack, renderType, packedLight,
                OverlayTexture.NO_OVERLAY, state.outlineColor, null);
    }
}
