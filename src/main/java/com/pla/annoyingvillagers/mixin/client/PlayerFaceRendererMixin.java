package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.util.NpcTabSkin;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerFaceRenderer.class)
public abstract class PlayerFaceRendererMixin {
    @Inject(method = "draw(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/Identifier;IIIZZI)V",
            at = @At("TAIL"))
    private static void av$drawMissingNpcHeadOverlay(GuiGraphics graphics, Identifier texture,
                                                     int x, int y, int size, boolean drawHat,
                                                     boolean upsideDown, int color, CallbackInfo callback) {
        if (!drawHat && NpcTabSkin.isNpcTexture(texture)) {
            int v = 8 + (upsideDown ? 8 : 0);
            int height = 8 * (upsideDown ? -1 : 1);
            graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 40.0F, v,
                    size, size, 8, height, 64, 64, color);
        }
    }
}
