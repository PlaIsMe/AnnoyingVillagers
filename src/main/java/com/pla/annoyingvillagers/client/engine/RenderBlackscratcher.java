package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.gameasset.AVAnimations;
import com.pla.annoyingvillagers.gameasset.AnimsAVSpear;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class RenderBlackscratcher extends RenderItemBase {
    public RenderBlackscratcher(JsonElement json) {
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
        if (livingEntityPatch == null || hand != InteractionHand.MAIN_HAND) {
            return;
        }

        ItemStack heldStack = livingEntityPatch.getOriginal().getMainHandItem();
        if (!heldStack.is(AnnoyingVillagersModItems.BLACKSCRATCHER.get())) {
            return;
        }

        AnimationPlayer animationPlayer = livingEntityPatch.getAnimator().getPlayerFor(null);
        AssetAccessor<? extends StaticAnimation> currentAnimation = animationPlayer == null
                ? null
                : animationPlayer.getRealAnimation();

        if (currentAnimation == AnimsAVSpear.BLACKSCRATCHER_IDLE || currentAnimation == AnimsAVSpear.BLACKSCRATCHER_ATTACK) {
            renderStack(
                    new ItemStack(AnnoyingVillagersModItems.BLACKSCRATCHER_TOP.get()),
                    livingEntityPatch,
                    InteractionHand.MAIN_HAND,
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    poses,
                    buffer,
                    poseStack,
                    packedLight
            );
            renderStack(
                    new ItemStack(AnnoyingVillagersModItems.BLACKSCRATCHER_BOTTOM.get()),
                    livingEntityPatch,
                    InteractionHand.OFF_HAND,
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    poses,
                    buffer,
                    poseStack,
                    packedLight
            );
            return;
        }

        renderStack(
                heldStack,
                livingEntityPatch,
                InteractionHand.MAIN_HAND,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                poses,
                buffer,
                poseStack,
                packedLight
        );
    }

    private void renderStack(ItemStack renderStack,
                             LivingEntityPatch<?> livingEntityPatch,
                             InteractionHand hand,
                             ItemDisplayContext displayContext,
                             OpenMatrix4f[] poses,
                             MultiBufferSource buffer,
                             PoseStack poseStack,
                             int packedLight) {
        OpenMatrix4f correctionMatrix = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, hand, poses));

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
