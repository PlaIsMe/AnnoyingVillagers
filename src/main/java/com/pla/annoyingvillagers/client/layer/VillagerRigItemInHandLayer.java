package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import com.pla.annoyingvillagers.client.renderer.RigItemVisualResolver;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VillagerRigItemInHandLayer<T extends Mob> extends ItemInHandLayer<LegacyEntityRenderState<T>, ModelRigVillager<T>> {
    private static final float ITEM_X_OFFSET = 1.0F / 16.0F;
    private static final float ITEM_DEPTH_OFFSET = 2.0F / 16.0F;
    private final ItemInHandRenderer itemInHandRenderer;

    public VillagerRigItemInHandLayer(RenderLayerParent<LegacyEntityRenderState<T>, ModelRigVillager<T>> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    protected void submitArmWithItem(LegacyEntityRenderState<T> state, ItemStackRenderState item,
                                     ItemStack itemStack, HumanoidArm arm, PoseStack poseStack,
                                     SubmitNodeCollector collector, int packedLight) {
        T entity = state.entity;
        if (itemStack.isEmpty()) {
            return;
        }

        if (RigClientAnimationState.isToolHidden(entity, arm)) {
            return;
        }

        poseStack.pushPose();
        this.getParentModel().translateToTool(state, arm, poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        boolean leftHand = arm == HumanoidArm.LEFT;
        poseStack.translate(leftHand ? -ITEM_X_OFFSET : ITEM_X_OFFSET, ITEM_DEPTH_OFFSET, 0.0F);
        ItemDisplayContext displayContext = leftHand
                ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        this.itemInHandRenderer.renderItem(entity, RigItemVisualResolver.resolve(entity, itemStack, leftHand),
                displayContext, poseStack, collector, packedLight);
        poseStack.popPose();
    }
}
