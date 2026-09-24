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
            "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/renderer/state/level/CameraRenderState;Lorg/joml/Matrix4fc;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;ZLnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;)V"),
            require = 1)
    private void av$beginSnakeFrame(DeltaTracker deltaTracker, CallbackInfo ci,
                                   @Local(name = "projectionMatrix") Matrix4f projectionMatrix) {
        var cameraState = ((GameRenderer)(Object)this).getGameRenderState().levelRenderState.cameraRenderState;
        BetterCombatSnakeAttachment.beginFrame(
                new Matrix4f(cameraState.viewRotationMatrix), projectionMatrix, cameraState.pos);
    }
}
