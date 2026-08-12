package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.gameasset.AnimsAVSword;
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
public class RenderDNAxHookSword extends RenderItemBase {
    public RenderDNAxHookSword(JsonElement json) {
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

        ItemStack heldStack = livingEntityPatch.getOriginal().getItemInHand(hand);
        if (!isDNAxHookSword(heldStack)) {
            return;
        }

        AnimationPlayer animationPlayer = livingEntityPatch.getAnimator().getPlayerFor(null);
        if (animationPlayer == null) {
            renderStack(
                    heldStack,
                    livingEntityPatch,
                    hand,
                    poses,
                    buffer,
                    poseStack,
                    packedLight
            );
            return;
        }

        AssetAccessor<? extends StaticAnimation> currentAnimation = animationPlayer.getRealAnimation();

        ItemStack renderStack;
        if (currentAnimation == AnimsAVSword.DNAX_HOOK_SWORD_INNATE || currentAnimation == AnimsAVSword.DNAX_HOOK_SWORD_DUAL_INNATE) {
            renderStack = new ItemStack(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD_ABILITY.get());
            if (heldStack.isEnchanted()) {
                renderStack.getOrCreateTag().putBoolean("foil", true);
            }
        } else {
            renderStack = heldStack.copy();
        }
        renderStack(renderStack, livingEntityPatch, hand, poses, buffer, poseStack, packedLight);
    }

    private static boolean isDNAxHookSword(ItemStack stack) {
        return stack.is(AnnoyingVillagersModItems.DNAX_HOOKED_SWORD.get());
    }

    private void renderStack(ItemStack renderStack,
                             LivingEntityPatch<?> livingEntityPatch,
                             InteractionHand hand,
                             OpenMatrix4f[] poses,
                             MultiBufferSource buffer,
                             PoseStack poseStack,
                             int packedLight) {
        OpenMatrix4f correctionMatrix = new OpenMatrix4f(
                this.getCorrectionMatrix(livingEntityPatch, hand, poses)
        );

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