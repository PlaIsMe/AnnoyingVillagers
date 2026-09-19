package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelAvWarden;
import com.pla.annoyingvillagers.entity.AvWarden;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

public class AvWardenEmissiveLayer extends RenderLayer<AvWarden, ModelAvWarden> {
    private final ResourceLocation texture;
    private final BiFunction<AvWarden, Float, Float> alphaFunction;
    private final boolean tendrilsOnly;

    public AvWardenEmissiveLayer(RenderLayerParent<AvWarden, ModelAvWarden> parent, ResourceLocation texture, BiFunction<AvWarden, Float, Float> alphaFunction, boolean tendrilsOnly) {
        super(parent);
        this.texture = texture;
        this.alphaFunction = alphaFunction;
        this.tendrilsOnly = tendrilsOnly;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AvWarden entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        float alpha = this.alphaFunction.apply(entity, partialTick);
        if (alpha <= 0.0F) return;
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.texture));
        if (this.tendrilsOnly) this.getParentModel().renderTendrils(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, alpha);
        else this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, net.minecraft.util.FastColor.ARGB32.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F));
    }
}
