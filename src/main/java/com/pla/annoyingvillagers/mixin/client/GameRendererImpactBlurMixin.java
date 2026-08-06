package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.shader.ImpactBlurShaderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 1500)
public class GameRendererImpactBlurMixin {
    @Inject(method = "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V", at = @At("TAIL"))
    public void annoyingVillagers$renderImpactBlur(float partialTick, long nanoTime, PoseStack poseStack, CallbackInfo callbackInfo) {
        if (!ImpactBlurShaderManager.isInitialized()) {
            ImpactBlurShaderManager.init();
        }

        ImpactBlurShaderManager.render(Minecraft.getInstance().levelRenderer.getTicks(), partialTick);
    }
}
