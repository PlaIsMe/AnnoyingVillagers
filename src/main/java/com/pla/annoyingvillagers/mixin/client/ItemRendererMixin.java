package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Routes 26.1 item-feature glint passes through the mod's colored pipelines. */
@Mixin(ItemFeatureRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyReturnValue(method = "getFoilRenderType", at = @At("RETURN"))
    private static RenderType annoyingVillagers$coloredGlint(RenderType original) {
        return ColoredGlintRenderTypes.getGlint(ColoredGlintState.getMode(), original);
    }
}
