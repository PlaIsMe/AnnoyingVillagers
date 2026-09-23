package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.compat.BetterCombatSnakeAttachment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Captures the blade tip after Minecraft 26.1 applies the item's display/local transforms. */
@Mixin(ItemStackRenderState.LayerRenderState.class)
public abstract class SnakeAttachmentItemLayerStateMixin {
    @Inject(method = "submit", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState;applyTransform(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;)V",
            shift = At.Shift.AFTER))
    private void annoyingVillagers$captureSnakeSocket(PoseStack poseStack,
                                                       SubmitNodeCollector collector,
                                                       int packedLight, int packedOverlay,
                                                       int outlineColor, CallbackInfo ci) {
        BetterCombatSnakeAttachment.captureTransformed(poseStack);
    }
}
