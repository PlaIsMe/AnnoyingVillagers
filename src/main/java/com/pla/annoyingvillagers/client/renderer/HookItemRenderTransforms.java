package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.item.LegacySwordItem;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;

final class HookItemRenderTransforms {
    private static final float SWORD_PROJECTILE_ROLL = -45.0F;
    private static final float PICKAXE_HOE_ALIGNMENT_ROLL = -90.0F;
    private static final float PICKAXE_HOE_ALIGNMENT_PITCH = -45.0F;
    private static final float AXE_ALIGNMENT_YAW = 45.0F;
    private static final float SHOVEL_ALIGNMENT_ROLL = -45.0F;
    private static final float HOOK_GUN_ATTACHMENT_ROLL = -90.0F;
    private static final double HOOK_GUN_ATTACHMENT_X = 0.9D;
    private static final double HOOK_GUN_ATTACHMENT_Y = 0.1D;
    private static final double HOOK_GUN_ATTACHMENT_Z = 0.55D;
    private static final float HOOK_GUN_ATTACHMENT_SCALE = 0.65F;
    private static final double SHIELD_HOOK_GUN_ATTACHMENT_Z_OFFSET = 0.25D;
    private static final float HOOK_GUN_PROJECTILE_SCALE = 0.5F;
    private static final float SHIELD_HOOK_GUN_PROJECTILE_SCALE = 1.0F;

    private HookItemRenderTransforms() {
    }

    static void applyProjectileFacing(PoseStack poseStack, ItemStack stack, float yaw, float pitch) {
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-pitch));
        if (HookUtil.shouldAlignSharpEdge(stack)) {
            applySharpModelAlignment(poseStack, stack);
        }
    }

    static ItemDisplayContext getProjectileDisplayContext(ItemStack stack) {
        return HookUtil.shouldAlignSharpEdge(stack) ? ItemDisplayContext.NONE : ItemDisplayContext.FIXED;
    }

    static boolean shouldUseDisplayAttachmentRenderer(ItemStack stack, ItemDisplayContext context) {
        return true;
    }

    static ItemDisplayContext getHookGunAttachmentDisplayContext(ItemStack stack, ItemDisplayContext context) {
        if (HookUtil.shouldUseShieldFacing(stack)) {
            return ItemDisplayContext.NONE;
        }

        return ItemDisplayContext.GUI;
    }

    static void applyHookGunAttachment(PoseStack poseStack, ItemStack stack, ItemDisplayContext context) {
        double zOffset = HookUtil.shouldUseShieldFacing(stack) ? SHIELD_HOOK_GUN_ATTACHMENT_Z_OFFSET : 0.0D;
        applyHookGunAttachmentTransform(poseStack, zOffset);
    }

    static ItemDisplayContext getHookGunProjectileDisplayContext(ItemStack stack) {
        if (HookUtil.shouldUseShieldFacing(stack)) {
            return ItemDisplayContext.NONE;
        }

        return getProjectileDisplayContext(stack);
    }

    static float getHookGunProjectileScale(ItemStack stack) {
        return HookUtil.shouldUseShieldFacing(stack)
                ? SHIELD_HOOK_GUN_PROJECTILE_SCALE
                : HOOK_GUN_PROJECTILE_SCALE;
    }

    static void applyShieldProjectileTransform(PoseStack poseStack) {
        // The 26.1 item renderer applies model transforms inside ItemStackRenderState.
        // Shields use NONE here, so there is no FIXED transform to compensate.
    }

    private static void applyHookGunAttachmentTransform(PoseStack poseStack, double zOffset) {
        poseStack.translate(HOOK_GUN_ATTACHMENT_X, HOOK_GUN_ATTACHMENT_Y, HOOK_GUN_ATTACHMENT_Z + zOffset);
        poseStack.mulPose(Axis.ZP.rotationDegrees(HOOK_GUN_ATTACHMENT_ROLL));
        poseStack.scale(HOOK_GUN_ATTACHMENT_SCALE, HOOK_GUN_ATTACHMENT_SCALE, HOOK_GUN_ATTACHMENT_SCALE);
    }

    private static void applySharpModelAlignment(PoseStack poseStack, ItemStack stack) {
        if (isPickaxeLike(stack) || isHoeLike(stack)) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(PICKAXE_HOE_ALIGNMENT_ROLL));
            poseStack.mulPose(Axis.XP.rotationDegrees(PICKAXE_HOE_ALIGNMENT_PITCH));
            return;
        }

        if (isAxeLike(stack)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(AXE_ALIGNMENT_YAW));
            return;
        }

        if (isShovelLike(stack)) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(SHOVEL_ALIGNMENT_ROLL));
            return;
        }

        poseStack.mulPose(Axis.ZP.rotationDegrees(SWORD_PROJECTILE_ROLL));
    }

    private static boolean isPickaxeLike(ItemStack stack) {
        return !stack.isEmpty()
                && stack.is(ItemTags.PICKAXES);
    }

    private static boolean isAxeLike(ItemStack stack) {
        return !stack.isEmpty()
                && !LegacySwordItem.isSword(stack)
                && stack.is(ItemTags.AXES);
    }

    private static boolean isHoeLike(ItemStack stack) {
        return !stack.isEmpty()
                && stack.is(ItemTags.HOES);
    }

    private static boolean isShovelLike(ItemStack stack) {
        return !stack.isEmpty()
                && stack.is(ItemTags.SHOVELS);
    }
}
