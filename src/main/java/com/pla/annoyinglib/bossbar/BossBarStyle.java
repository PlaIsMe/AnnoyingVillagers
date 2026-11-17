package com.pla.annoyinglib.bossbar;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;

/** Immutable presentation; content textures stay in the calling mod's namespace. */
public record BossBarStyle(int color, @Nullable BossBarHead head, int phases, boolean compact,
                           ResourceLocation frame, ResourceLocation accent, ResourceLocation detail) {
    public BossBarStyle {
        color &= 0xFFFFFF;
        if (phases < 0 || phases > 64) throw new IllegalArgumentException("Phases must be 0..64");
        if (compact && head != null) throw new IllegalArgumentException("Compact bars cannot display a head");
        Objects.requireNonNull(frame);
        Objects.requireNonNull(accent);
        Objects.requireNonNull(detail);
    }

    public static Builder builder(int rgb) { return new Builder(rgb); }

    public static final class Builder {
        private final int color;
        private BossBarHead head;
        private int phases;
        private boolean compact;
        private ResourceLocation frame = vanilla("obsidian");
        private ResourceLocation accent = vanilla("crying_obsidian");
        private ResourceLocation detail = vanilla("obsidian");

        private Builder(int color) { this.color = color; }
        public Builder head(BossBarHead head) { this.head = head; return this; }
        public Builder phases(int count) { this.phases = count; return this; }
        public Builder compact(boolean compact) { this.compact = compact; return this; }
        /** All three textures are tiled as 16x16 block textures. */
        public Builder textures(ResourceLocation frame, ResourceLocation accent, ResourceLocation detail) {
            this.frame = frame; this.accent = accent; this.detail = detail; return this;
        }
        public BossBarStyle build() { return new BossBarStyle(color, head, phases, compact, frame, accent, detail); }
        private static ResourceLocation vanilla(String name) {
            return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/" + name + ".png");
        }
    }
}
