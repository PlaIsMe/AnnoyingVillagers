package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

final class HookItemRenderTransforms {
    private static final float SWORD_PROJECTILE_ROLL = -45.0F;
    private static final float CUSTOM_3D_FIXED_POSITIVE_Y_ROLL = -45.0F;
    private static final float CUSTOM_3D_FIXED_NEGATIVE_Y_ROLL = -135.0F;
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
    private static final double ITEM_DISPLAY_TRANSLATION_SCALE = 16.0D;

    private HookItemRenderTransforms() {
    }

    static void applyProjectileFacing(PoseStack poseStack, ItemStack stack, float yaw, float pitch) {
        applyProjectileFacing(poseStack, stack, null, yaw, pitch);
    }

    static void applyProjectileFacing(PoseStack poseStack, ItemStack stack, @Nullable BakedModel model, float yaw, float pitch) {
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-pitch));
        if (HookUtil.shouldAlignSharpEdge(stack)) {
            applySharpModelAlignment(poseStack, stack, model);
        }
    }

    static ItemDisplayContext getProjectileDisplayContext(ItemStack stack) {
        return getProjectileDisplayContext(stack, null);
    }

    static ItemDisplayContext getProjectileDisplayContext(ItemStack stack, @Nullable BakedModel model) {
        if (isCustom3DSharpModel(stack, model)) {
            return ItemDisplayContext.FIXED;
        }

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

    static ItemDisplayContext getHookGunProjectileDisplayContext(ItemStack stack, @Nullable BakedModel model) {
        if (HookUtil.shouldUseShieldFacing(stack)) {
            return ItemDisplayContext.NONE;
        }

        return getProjectileDisplayContext(stack, model);
    }

    static float getHookGunProjectileScale(ItemStack stack) {
        return HookUtil.shouldUseShieldFacing(stack)
                ? SHIELD_HOOK_GUN_PROJECTILE_SCALE
                : HOOK_GUN_PROJECTILE_SCALE;
    }

    static void applyShieldProjectileTransform(PoseStack poseStack, @Nullable BakedModel model) {
        if (model == null || !model.getTransforms().hasTransform(ItemDisplayContext.FIXED)) {
            return;
        }

        ItemTransform fixedTransform = model.getTransforms().getTransform(ItemDisplayContext.FIXED);
        poseStack.translate(
                -fixedTransform.translation.x() / ITEM_DISPLAY_TRANSLATION_SCALE,
                -fixedTransform.translation.y() / ITEM_DISPLAY_TRANSLATION_SCALE,
                -fixedTransform.translation.z() / ITEM_DISPLAY_TRANSLATION_SCALE
        );
    }

    private static void applyHookGunAttachmentTransform(PoseStack poseStack, double zOffset) {
        poseStack.translate(HOOK_GUN_ATTACHMENT_X, HOOK_GUN_ATTACHMENT_Y, HOOK_GUN_ATTACHMENT_Z + zOffset);
        poseStack.mulPose(Axis.ZP.rotationDegrees(HOOK_GUN_ATTACHMENT_ROLL));
        poseStack.scale(HOOK_GUN_ATTACHMENT_SCALE, HOOK_GUN_ATTACHMENT_SCALE, HOOK_GUN_ATTACHMENT_SCALE);
    }

    private static void applySharpModelAlignment(PoseStack poseStack, ItemStack stack, @Nullable BakedModel model) {
        if (isCustom3DSharpModel(stack, model)) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(getCustom3DFixedModelRoll(model)));
            return;
        }

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

    private static boolean isCustom3DSharpModel(ItemStack stack, @Nullable BakedModel model) {
        return model != null
                && model.isGui3d()
                && model.getTransforms().hasTransform(ItemDisplayContext.FIXED)
                && HookUtil.shouldAlignSharpEdge(stack);
    }

    private static float getCustom3DFixedModelRoll(BakedModel model) {
        ItemTransform fixedTransform = model.getTransforms().getTransform(ItemDisplayContext.FIXED);
        return fixedTransform.rotation.y() < 0.0F || Math.abs(fixedTransform.rotation.z()) >= 135.0F
                ? CUSTOM_3D_FIXED_NEGATIVE_Y_ROLL
                : CUSTOM_3D_FIXED_POSITIVE_Y_ROLL;
    }

    private static boolean isPickaxeLike(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof PickaxeItem
                || stack.canPerformAction(ToolActions.PICKAXE_DIG));
    }

    private static boolean isAxeLike(ItemStack stack) {
        return !stack.isEmpty()
                && !(stack.getItem() instanceof SwordItem)
                && (stack.getItem() instanceof AxeItem
                || stack.canPerformAction(ToolActions.AXE_DIG));
    }

    private static boolean isHoeLike(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof HoeItem
                || stack.canPerformAction(ToolActions.HOE_DIG));
    }

    private static boolean isShovelLike(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof ShovelItem
                || stack.canPerformAction(ToolActions.SHOVEL_DIG));
    }
}
