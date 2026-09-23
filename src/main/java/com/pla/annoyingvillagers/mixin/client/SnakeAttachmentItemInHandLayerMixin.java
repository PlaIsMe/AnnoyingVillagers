package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.compat.BetterCombatSnakeAttachment;
import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Associates vanilla third-person held-item layers with their extracted entity. */
@Mixin(ItemInHandLayer.class)
public abstract class SnakeAttachmentItemInHandLayerMixin {
    @Inject(method = "submitArmWithItem", at = @At("HEAD"))
    private void annoyingVillagers$beginSnakeItem(ArmedEntityRenderState state,
                                                   ItemStackRenderState itemState, ItemStack stack,
                                                   HumanoidArm arm, PoseStack poseStack,
                                                   SubmitNodeCollector collector, int packedLight,
                                                   CallbackInfo ci) {
        LivingEntity entity = VanillaOverlayRenderStateCache.getEntity(state);
        ItemDisplayContext context = arm == HumanoidArm.LEFT
                ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        BetterCombatSnakeAttachment.beginItem(entity, stack, context);
    }

    @Inject(method = "submitArmWithItem", at = @At("RETURN"))
    private void annoyingVillagers$endSnakeItem(ArmedEntityRenderState state,
                                                 ItemStackRenderState itemState, ItemStack stack,
                                                 HumanoidArm arm, PoseStack poseStack,
                                                 SubmitNodeCollector collector, int packedLight,
                                                 CallbackInfo ci) {
        BetterCombatSnakeAttachment.endItem();
    }
}
