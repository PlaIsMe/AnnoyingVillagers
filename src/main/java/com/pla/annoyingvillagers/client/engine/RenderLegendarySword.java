package com.pla.annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.annoyingvillagers.gameasset.AVSkills;
import com.pla.annoyingvillagers.gameasset.AnimsLegendarySword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.skill.LegendarySwordSkill;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderLegendarySword extends RenderItemBase {

    public RenderLegendarySword(JsonElement json) {
        super(json);
    }

    private static boolean shouldRenderAwakened(ItemStack stack, LivingEntityPatch<?> livingEntityPatch) {
        if (LegendarySwordSkill.isAwakened(stack, livingEntityPatch.getOriginal())) {
            return true;
        }

        if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            SkillContainer skillContainer = playerPatch.getSkill(AVSkills.LEGENDARY_SWORD);
            return skillContainer != null
                    && Boolean.TRUE.equals(skillContainer.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED.get()));
        }

        return false;
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
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            ItemStack itemstack;

            if (dynamicAnimation == AnimsLegendarySword.LEGENDARY_SWORD_INNATE
                    || shouldRenderAwakened(stack, livingEntityPatch)) {
                itemstack = new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            } else {
                itemstack = stack;
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, livingEntityPatch.getOriginal().level(), 0);
                poseStack.popPose();
            }
        }
    }
}
