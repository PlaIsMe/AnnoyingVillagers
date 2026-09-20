package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.AbstractIllager;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;

public class IllagerMobVanillaLayer<S extends IllagerRenderState, M extends IllagerModel<S>> extends RenderLayer<S, M> {
    public IllagerMobVanillaLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       S state, float yRot, float xRot) {
        Identifier texture = VanillaOverlayRenderStateCache.get(state);
        if (texture == null) return;
        collector.submitModel(this.getParentModel(), state, poseStack,
                net.minecraft.client.renderer.rendertype.RenderTypes.eyes(texture), packedLight,
                OverlayTexture.NO_OVERLAY, state.outlineColor, null);
    }
}
