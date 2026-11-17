package com.pla.annoyinglib.client.gui;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.bossbar.BossBarHead;
import com.pla.annoyinglib.bossbar.BossBarStyle;
import com.pla.annoyinglib.network.ClientboundBossBarStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Opt-in UUID-based rendering. Ordinary vanilla/other-mod boss bars remain untouched. */
@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, value = Dist.CLIENT)
public final class StyledBossBarOverlay {
    private static final int WIDTH = 220;
    private static final Map<UUID, ClientboundBossBarStyle> STYLES = new HashMap<>();

    private StyledBossBarOverlay() {}

    public static void accept(ClientboundBossBarStyle message) {
        if (message.style() == null) STYLES.remove(message.id());
        else STYLES.put(message.id(), message);
    }

    @SubscribeEvent public static void logout(ClientPlayerNetworkEvent.LoggingOut event) { STYLES.clear(); }

    @SubscribeEvent
    public static void render(CustomizeGuiOverlayEvent.BossEventProgress event) {
        ClientboundBossBarStyle data = STYLES.get(event.getBossEvent().getId());
        if (data == null || data.style() == null) return;
        BossBarStyle style = data.style();
        event.setCanceled(true);
        GuiGraphics g = event.getGuiGraphics();
        float scale = Math.max(0.1F, Math.min(1F, (event.getWindow().getGuiScaledWidth() - 16F) / WIDTH));
        float left = (event.getWindow().getGuiScaledWidth() - WIDTH * scale) / 2F;
        g.pose().pushPose();
        g.pose().translate(left, event.getY() - 9, 0);
        g.pose().scale(scale, scale, 1);
        drawText(g, event.getBossEvent().getName(), 0, tint(style.color(), 0xFFFFFF, 0.72F));
        int trackLeft, trackTop, trackWidth;
        if (style.compact()) {
            g.fill(0, 9, WIDTH, 16, 0xFF080D09);
            tiles(g, style.frame(), 1, 10, WIDTH - 2, 5);
            trackLeft = 3; trackTop = 11; trackWidth = WIDTH - 6;
        } else {
            drawFrame(g, style);
            trackLeft = 12; trackTop = 14; trackWidth = WIDTH - 24;
        }
        int fill = Mth.ceil(trackWidth * Mth.clamp(event.getBossEvent().getProgress(), 0, 1));
        g.fill(trackLeft, trackTop, trackLeft + trackWidth, trackTop + 3, 0xFF160F24);
        if (fill > 0) {
            g.fill(trackLeft, trackTop, trackLeft + fill, trackTop + 1, tint(style.color(), 0xFFFFFF, 0.55F));
            g.fill(trackLeft, trackTop + 1, trackLeft + fill, trackTop + 2, 0xFF000000 | style.color());
            g.fill(trackLeft, trackTop + 2, trackLeft + fill, trackTop + 3, tint(style.color(), 0, 0.45F));
        }
        for (int section = 1; section < style.phases(); section++) {
            int x = trackLeft + Math.round(trackWidth * section / (float) style.phases());
            g.fill(x, trackTop, x + 1, trackTop + 3, 0xFF12070B);
        }
        if (style.head() != null) drawPortrait(g, style);
        Component subtitle = data.subtitle();
        if (subtitle.equals(Component.empty()) && style.phases() > 0 && data.phase() > 0)
            subtitle = Component.literal(data.phase() + "/" + style.phases());
        boolean hasSubtitle = !subtitle.equals(Component.empty());
        if (hasSubtitle) drawText(g, subtitle, style.compact() ? 19 : 23, tint(style.color(), 0xFFFFFF, 0.4F));
        g.pose().popPose();
        // Include all decoration and both text rows when stacking vanilla and custom bars.
        event.setIncrement(Mth.ceil((style.compact() ? (hasSubtitle ? 30 : 24) : 34) * scale));
    }

    /** Long localized titles fit the bar instead of overlapping neighbouring UI at small GUI sizes. */
    private static void drawText(GuiGraphics g, Component text, int y, int color) {
        var font = Minecraft.getInstance().font;
        float scale = Math.min(1F, (WIDTH - 4F) / Math.max(1, font.width(text)));
        g.pose().pushPose();
        g.pose().translate(WIDTH / 2F, y, 0);
        g.pose().scale(scale, scale, 1);
        g.drawCenteredString(font, text, 0, 0, color);
        g.pose().popPose();
    }

    private static void drawFrame(GuiGraphics g, BossBarStyle style) {
        g.fill(8, 11, WIDTH - 8, 20, 0xFF08070C);
        tiles(g, style.frame(), 9, 12, WIDTH - 18, 7);
        g.fill(12, 12, WIDTH - 12, 13, tint(style.color(), 0, 0.5F));
        g.fill(12, 18, WIDTH - 12, 19, tint(style.color(), 0, 0.7F));
        g.fill(11, 13, WIDTH - 11, 18, 0xFF08070C);
        drawEndCap(g, style, 0);
        drawEndCap(g, style, WIDTH - 12);
    }

    private static void drawEndCap(GuiGraphics g, BossBarStyle style, int x) {
        g.fill(x + 3, 11, x + 9, 20, 0xFF08070C);
        g.fill(x + 1, 13, x + 11, 18, 0xFF08070C);
        tiles(g, style.frame(), x + 3, 12, 6, 7);
        tiles(g, style.accent(), x + 2, 14, 8, 3);
        tiles(g, style.detail(), x + 5, 12, 2, 1);
    }

    private static void drawPortrait(GuiGraphics g, BossBarStyle style) {
        BossBarHead h = style.head();
        int center = WIDTH / 2;
        g.fill(center - 6, 9, center + 6, 22, 0xFF08070C);
        tiles(g, style.frame(), center - 5, 10, 10, 11);
        g.blit(h.texture(), center - 4, 11, 8, 8, h.u(), h.v(), h.width(), h.height(), h.textureWidth(), h.textureHeight());
        tiles(g, style.accent(), center - 4, 20, 8, 1);
    }

    private static int tint(int color, int target, float amount) {
        int r = Math.round(Mth.lerp(amount, (color >> 16) & 255, (target >> 16) & 255));
        int g = Math.round(Mth.lerp(amount, (color >> 8) & 255, (target >> 8) & 255));
        int b = Math.round(Mth.lerp(amount, color & 255, target & 255));
        return 0xFF000000 | r << 16 | g << 8 | b;
    }

    private static void tiles(GuiGraphics g, ResourceLocation texture, int x, int y, int width, int height) {
        for (int dy = 0; dy < height; dy += 16) for (int dx = 0; dx < width; dx += 16) {
            int w = Math.min(16, width - dx), h = Math.min(16, height - dy);
            g.blit(texture, x + dx, y + dy, w, h, 0F, 0F, w, h, 16, 16);
        }
    }
}
