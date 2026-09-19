package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelGolemArm;
import com.pla.annoyingvillagers.entity.GolemArms;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GolemArmsEmissiveLayer extends RenderLayer<GolemArms, ModelGolemArm> {
    private final ResourceLocation texture;

    public GolemArmsEmissiveLayer(RenderLayerParent<GolemArms, ModelGolemArm> parent, ResourceLocation texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, GolemArms entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.eyes(this.texture));
        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
    }
}
