package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererStateMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void annoyingVillagers$extractOverlay(LivingEntity entity, LivingEntityRenderState state,
                                                   float partialTick, CallbackInfo ci) {
        VanillaOverlayRenderStateCache.update(entity, state);
    }
}
