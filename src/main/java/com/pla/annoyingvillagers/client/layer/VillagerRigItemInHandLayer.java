package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VillagerRigItemInHandLayer<T extends Mob> extends ItemInHandLayer<T, ModelRigVillager<T>> {
    private static final float ITEM_X_OFFSET = 1.0F / 16.0F;
    private static final float ITEM_DEPTH_OFFSET = 2.0F / 16.0F;
    private final ItemInHandRenderer itemInHandRenderer;

    public VillagerRigItemInHandLayer(RenderLayerParent<T, ModelRigVillager<T>> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer, itemInHandRenderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    protected void renderArmWithItem(@NotNull LivingEntity entity, ItemStack itemStack, @NotNull ItemDisplayContext displayContext, @NotNull HumanoidArm arm, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (itemStack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        this.getParentModel().translateToTool(arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        boolean leftHand = arm == HumanoidArm.LEFT;
        poseStack.translate(leftHand ? -ITEM_X_OFFSET : ITEM_X_OFFSET, ITEM_DEPTH_OFFSET, 0.0F);
        this.itemInHandRenderer.renderItem(entity, itemStack, displayContext, leftHand, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
