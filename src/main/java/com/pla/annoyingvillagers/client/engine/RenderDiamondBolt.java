package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class RenderDiamondBolt extends RenderItemBase {
    private static final double AIM_HAND_X_OFFSET = 0.0D;
    private static final double AIM_HAND_Y_OFFSET = -1.5D;
    private static final double AIM_HAND_Z_OFFSET = 0.0D;

    public RenderDiamondBolt(JsonElement json) {
        super(json);
    }

    @Override
    public void renderItemInHand(ItemStack stack,
                                 LivingEntityPatch<?> livingEntityPatch,
                                 InteractionHand hand,
                                 OpenMatrix4f[] poses,
                                 MultiBufferSource buffer,
                                 PoseStack poseStack,
                                 int packedLight,
                                 float partialTicks) {
        if (livingEntityPatch == null) {
            return;
        }

        LivingEntity entity = livingEntityPatch.getOriginal();
        OpenMatrix4f correctionMatrix = this.getCorrectionMatrix(livingEntityPatch, hand, poses);
        ItemDisplayContext displayContext = hand == InteractionHand.MAIN_HAND
                ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                : ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        poseStack.pushPose();
        MathUtils.mulStack(poseStack, correctionMatrix);

        if (isAimingDiamondBolt(stack, entity, hand)) {
            poseStack.translate(AIM_HAND_X_OFFSET, AIM_HAND_Y_OFFSET, AIM_HAND_Z_OFFSET);

            BakedModel model = itemRenderer.getModel(stack, entity.level(), entity, entity.getId());
            model = ForgeHooksClient.handleCameraTransforms(
                    poseStack,
                    model,
                    displayContext,
                    hand == InteractionHand.OFF_HAND
            );
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            itemRenderer.render(
                    stack,
                    ItemDisplayContext.NONE,
                    false,
                    poseStack,
                    buffer,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    model
            );
        } else {
            itemInHandRenderer.renderItem(
                    entity,
                    stack,
                    displayContext,
                    hand == InteractionHand.OFF_HAND,
                    poseStack,
                    buffer,
                    packedLight
            );
        }

        poseStack.popPose();
    }

    private static boolean isAimingDiamondBolt(ItemStack stack,
                                               LivingEntity entity,
                                               InteractionHand hand) {
        return stack.is(AnnoyingVillagersModItems.DIAMOND_BOLT.get())
                && entity.isUsingItem()
                && entity.getUsedItemHand() == hand;
    }
}
