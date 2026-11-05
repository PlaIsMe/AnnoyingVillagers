package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.compat.BetterCombatSnakeAttachment;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Capture the actual world projection, including hurt/view bob and nausea. */
@Mixin(GameRenderer.class)
public abstract class SnakeAttachmentGameRendererMixin {
    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V"),
            require = 1)
    private void av$beginSnakeFrame(DeltaTracker deltaTracker, CallbackInfo ci,
                                   @Local(ordinal = 0) Matrix4f projectionMatrix) {
        var camera = ((GameRenderer)(Object)this).getMainCamera();
        BetterCombatSnakeAttachment.beginFrame(
                new Matrix4f().rotation(camera.rotation().conjugate(new org.joml.Quaternionf())), projectionMatrix, camera.position());
    }
}
