package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class RenderTwinDiamondSpear extends RenderItemBase {
    public RenderTwinDiamondSpear(JsonElement json) {
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

        ItemStack mainHandStack = livingEntityPatch.getOriginal().getMainHandItem();
        ItemStack offHandStack = livingEntityPatch.getOriginal().getOffhandItem();
        boolean dualTwinSpear = mainHandStack.is(AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR.get())
                && offHandStack.is(AnnoyingVillagersModItems.TWIN_DIAMOND_SPEAR.get());

        if (dualTwinSpear && hand == InteractionHand.MAIN_HAND) {
            ItemStack renderStack = new ItemStack(AnnoyingVillagersModItems.DUAL_TWIN_DIAMOND_SPEAR.get());
            if (mainHandStack.isEnchanted()) {
                renderStack.getOrCreateTag().putBoolean("foil", true);
            }

            renderStack(renderStack, livingEntityPatch, hand, poses, buffer, poseStack, packedLight);
            return;
        }

        if (dualTwinSpear && hand == InteractionHand.OFF_HAND) {
            renderStack(ItemStack.EMPTY, livingEntityPatch, hand, poses, buffer, poseStack, packedLight);
            return;
        }

        renderStack(stack, livingEntityPatch, hand, poses, buffer, poseStack, packedLight);
    }

    private void renderStack(ItemStack renderStack,
                             LivingEntityPatch<?> livingEntityPatch,
                             InteractionHand hand,
                             OpenMatrix4f[] poses,
                             MultiBufferSource buffer,
                             PoseStack poseStack,
                             int packedLight) {
        OpenMatrix4f correctionMatrix = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, hand, poses));
        ItemDisplayContext displayContext = hand == InteractionHand.MAIN_HAND
                ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                : ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

        poseStack.pushPose();
        MathUtils.mulStack(poseStack, correctionMatrix);
        Minecraft.getInstance().getItemRenderer().renderStatic(
                renderStack,
                displayContext,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                livingEntityPatch.getOriginal().level(),
                0
        );
        poseStack.popPose();
    }
}
