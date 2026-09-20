package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.compat.BetterCombatSnakeAttachment;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class SnakeAttachmentLevelRendererMixin {
    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void av$beginSnakeFrame(GraphicsResourceAllocator resourceAllocator,
                                    DeltaTracker deltaTracker, boolean renderOutline,
                                    CameraRenderState cameraState, Matrix4fc modelViewMatrix,
                                    GpuBufferSlice terrainFog, Vector4f fogColor,
                                    boolean shouldRenderSky, ChunkSectionsToRender chunkSectionsToRender,
                                    CallbackInfo ci) {
        BetterCombatSnakeAttachment.beginFrame(
                new org.joml.Matrix4f(modelViewMatrix), cameraState.projectionMatrix, cameraState.pos);
    }
}
