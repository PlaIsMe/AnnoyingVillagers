package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.model.ModelAvGolem;
import com.pla.annoyingvillagers.entity.AvGolem;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class AvGolemItemInHandLayer extends RenderLayer<AvGolem, ModelAvGolem> {
    private final ItemInHandRenderer itemRenderer;

    public AvGolemItemInHandLayer(RenderLayerParent<AvGolem, ModelAvGolem> parent, ItemInHandRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AvGolem entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        renderHand(entity, entity.getMainHandItem(), entity.getMainArm(), poseStack, buffer, packedLight);
        renderHand(entity, entity.getOffhandItem(), entity.getMainArm().getOpposite(), poseStack, buffer, packedLight);
    }

    private void renderHand(AvGolem entity, ItemStack stack, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (stack.isEmpty()) return;
        poseStack.pushPose();
        this.getParentModel().translateToTool(arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        boolean left = arm == HumanoidArm.LEFT;
        poseStack.translate(left ? -0.0625D : 0.0625D, 0.125D, 0.0D);
        ItemDisplayContext context = left ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        this.itemRenderer.renderItem(entity, stack, context, left, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
