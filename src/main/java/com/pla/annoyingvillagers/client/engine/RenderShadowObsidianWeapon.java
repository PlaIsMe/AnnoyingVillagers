package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderShadowObsidianWeapon extends RenderItemBase {

    public RenderShadowObsidianWeapon(JsonElement json) {
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
        if (livingEntityPatch != null) {
            OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, InteractionHand.MAIN_HAND, poses));
            AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
            float elapsedTimeFloat = animationPlayer.getElapsedTime();
            EntityState entityState = (dynamicAnimation.get()).getState(livingEntityPatch, elapsedTimeFloat);
            ItemStack itemstack;

            if (dynamicAnimation == AVAnimations.HEROBRINE_RUN
                    || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL
                    || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_1
                    || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_3
                    || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_1
                    || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_2
                    || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_DASH
                    || dynamicAnimation.get() instanceof ExecutionAttackAnimation) {
                itemstack = ItemStack.EMPTY;
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            }  else if (dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL && entityState.getLevel() > 1) {
                itemstack = ItemStack.EMPTY;
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            } else {
                itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            }
        }
    }
}
