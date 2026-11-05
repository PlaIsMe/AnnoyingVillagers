package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GameRenderer.class)
public interface SnakeAttachmentGameRendererAccessor {
    @Invoker("bobHurt")
    void av$bobHurt(PoseStack pose, float partialTick);

    @Invoker("bobView")
    void av$bobView(PoseStack pose, float partialTick);

    @Invoker("getFov")
    float av$getFov(Camera camera, float partialTick, boolean useFovSetting);
}
