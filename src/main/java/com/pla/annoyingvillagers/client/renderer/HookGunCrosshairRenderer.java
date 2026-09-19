package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.HookGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class HookGunCrosshairRenderer {
    private static final ResourceLocation GUI_ICONS =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/icons.png");

    private HookGunCrosshairRenderer() {
    }

    @SubscribeEvent
    public static void onRenderCrosshair(RenderGuiLayerEvent.Post event) {
        if (!event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }

        Options options = minecraft.options;
        if (!options.getCameraType().isFirstPerson()) {
            return;
        }
        if (player.isSpectator()) {
            return;
        }
        if (minecraft.getDebugOverlay().showDebugScreen() && !options.hideGui && !player.isReducedDebugInfo() && !options.reducedDebugInfo().get()) {
            return;
        }
        if (!HookGunItem.isHoldingHookGunInBothHands(player)) {
            return;
        }

        Window window = minecraft.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        double fov = Math.toRadians(options.fov().get());
        fov *= player.getFieldOfViewModifier();
        double projectedDistance = ((double) height / 2.0D) / Math.tan(fov / 2.0D);
        int offset = (int) (Math.tan(Math.toRadians(HookGunItem.getDoubleHookAngle(player))) * projectedDistance);

        if (offset != 0) {
            drawCrosshair(event.getGuiGraphics(), width / 2 + offset, height / 2);
            drawCrosshair(event.getGuiGraphics(), width / 2 - offset, height / 2);
        }
    }

    private static void drawCrosshair(GuiGraphics graphics, int x, int y) {
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
                GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        graphics.blit(GUI_ICONS, x - 7, y - 7, 0, 0, 15, 15);
        RenderSystem.defaultBlendFunc();
    }
}
