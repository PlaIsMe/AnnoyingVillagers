package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.compat.BetterCombatSnakeAttachment;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class SnakeAttachmentLevelRendererMixin {
    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void av$beginSnakeFrame(DeltaTracker deltaTracker, boolean blockOutline,
                                    Camera camera, GameRenderer gameRenderer, LightTexture lightTexture,
                                    Matrix4f frustumMatrix, Matrix4f projection, CallbackInfo ci) {
        BetterCombatSnakeAttachment.beginFrame(frustumMatrix, projection, camera.getPosition());
    }
}
