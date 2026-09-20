package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface SnakeAttachmentGameRendererAccessor {
    @Invoker("bobHurt")
    void av$bobHurt(CameraRenderState cameraState, PoseStack pose);

    @Invoker("bobView")
    void av$bobView(CameraRenderState cameraState, PoseStack pose);
}
