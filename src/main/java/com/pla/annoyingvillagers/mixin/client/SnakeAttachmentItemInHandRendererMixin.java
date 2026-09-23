package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.compat.BetterCombatSnakeAttachment;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Associates direct held-item rendering (first person and AV rig mobs) with its owner. */
@Mixin(ItemInHandRenderer.class)
public abstract class SnakeAttachmentItemInHandRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void annoyingVillagers$beginSnakeItem(LivingEntity entity, ItemStack stack,
                                                   ItemDisplayContext context, PoseStack poseStack,
                                                   SubmitNodeCollector collector, int packedLight,
                                                   CallbackInfo ci) {
        BetterCombatSnakeAttachment.beginItem(entity, stack, context);
    }

    @Inject(method = "renderItem", at = @At("RETURN"))
    private void annoyingVillagers$endSnakeItem(LivingEntity entity, ItemStack stack,
                                                 ItemDisplayContext context, PoseStack poseStack,
                                                 SubmitNodeCollector collector, int packedLight,
                                                 CallbackInfo ci) {
        BetterCombatSnakeAttachment.endItem();
    }
}
