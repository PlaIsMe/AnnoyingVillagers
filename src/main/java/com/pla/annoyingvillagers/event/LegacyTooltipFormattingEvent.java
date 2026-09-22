package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.StringDecomposer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Converts legacy description formatting into separately styled tooltip lines. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class LegacyTooltipFormattingEvent {
    private LegacyTooltipFormattingEvent() {}

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        if (tooltip.stream().noneMatch(LegacyTooltipFormattingEvent::isModDescription)) return;
        var minecraft = Minecraft.getInstance();
        int width = Math.max(80, Math.min(320, minecraft.getWindow().getGuiScaledWidth() - 32));
        List<Component> formatted = new ArrayList<>();
        for (Component line : tooltip) {
            if (!isModDescription(line)) {
                formatted.add(line);
                continue;
            }
            StyledRuns runs = new StyledRuns();
            StringDecomposer.iterateFormatted(line, Style.EMPTY, (index, style, codepoint) -> {
                runs.append(style, codepoint);
                return true;
            });
            runs.flush();
            for (FormattedText wrapped : minecraft.font.getSplitter().splitLines(runs.text, width, Style.EMPTY)) {
                MutableComponent component = Component.empty();
                wrapped.visit((style, text) -> {
                    component.append(Component.literal(text).setStyle(style));
                    return Optional.empty();
                }, Style.EMPTY);
                formatted.add(component);
            }
        }
        tooltip.clear();
        tooltip.addAll(formatted);
    }

    private static boolean isModDescription(Component component) {
        return component.getContents() instanceof TranslatableContents translation
                && translation.getKey().startsWith("tooltip.annoyingvillagers.")
                || component.getSiblings().stream().anyMatch(LegacyTooltipFormattingEvent::isModDescription);
    }

    private static final class StyledRuns {
        private final MutableComponent text = Component.empty();
        private final StringBuilder run = new StringBuilder();
        private Style style = Style.EMPTY;

        void append(Style nextStyle, int codepoint) {
            if (!style.equals(nextStyle)) {
                flush();
                style = nextStyle;
            }
            run.appendCodePoint(codepoint);
        }

        void flush() {
            if (!run.isEmpty()) {
                text.append(Component.literal(run.toString()).setStyle(style));
                run.setLength(0);
            }
        }
    }
}
