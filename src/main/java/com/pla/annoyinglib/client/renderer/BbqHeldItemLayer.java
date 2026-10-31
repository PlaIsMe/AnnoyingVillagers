package com.pla.annoyinglib.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyinglib.client.model.ModelBbq;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BbqHeldItemLayer extends RenderLayer<Chicken, ChickenModel<Chicken>> {
    private final ItemInHandRenderer itemInHandRenderer;

    public BbqHeldItemLayer(RenderLayerParent<Chicken, ChickenModel<Chicken>> parent,
                            ItemInHandRenderer itemInHandRenderer) {
        super(parent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight,
                       @NotNull Chicken entity, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack mainHandItem = entity.getMainHandItem();
        if (!mainHandItem.isEmpty()) {
            if (this.getParentModel() instanceof ModelBbq<?> bbqModel) {
                poseStack.pushPose();
                bbqModel.getBeak().translateAndRotate(poseStack);
                poseStack.translate(-0.8D, -0.1875D, -0.1875D);
                poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                this.itemInHandRenderer.renderItem(
                        entity,
                        mainHandItem,
                        ItemDisplayContext.GROUND,
                        false,
                        poseStack,
                        buffer,
                        packedLight
                );

                poseStack.popPose();
            }
        }

        ItemStack offHandItem = entity.getOffhandItem();
        if (!offHandItem.isEmpty()) {
            if (this.getParentModel() instanceof ModelBbq<?> bbqModel) {
                poseStack.pushPose();
                bbqModel.getBeak().translateAndRotate(poseStack);
                poseStack.translate(0.0D, -0.1875D, -0.1875D);
                poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                this.itemInHandRenderer.renderItem(
                        entity,
                        offHandItem,
                        ItemDisplayContext.GROUND,
                        false,
                        poseStack,
                        buffer,
                        packedLight
                );

                poseStack.popPose();
            }
        }
    }
}