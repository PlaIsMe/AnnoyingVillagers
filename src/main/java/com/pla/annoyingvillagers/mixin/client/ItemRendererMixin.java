package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyExpressionValue(method = {"getFoilBuffer", "getSpecialFoilBuffer", "getFoilRenderTypes"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;glint()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private static RenderType av$glint(RenderType original) {
        return ColoredGlintRenderTypes.getGlintDirect(ColoredGlintState.getMode(), original);
    }

    @ModifyExpressionValue(method = {"getFoilBuffer", "getSpecialFoilBuffer", "getFoilRenderTypes"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;glintTranslucent()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private static RenderType av$translucentGlint(RenderType original) {
        return ColoredGlintRenderTypes.getGlint(ColoredGlintState.getMode(), original);
    }

    @ModifyExpressionValue(method = {"getFoilBuffer", "getFoilRenderTypes"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;entityGlint()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private static RenderType av$entityGlint(RenderType original) {
        return ColoredGlintRenderTypes.getEntityGlintDirect(ColoredGlintState.getMode(), original);
    }
}
