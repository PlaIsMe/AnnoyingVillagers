package com.pla.annoyinglib.bossbar;

import net.minecraft.resources.ResourceLocation;
import java.util.Objects;

/** A texture region displayed as an 8x8 portrait. Coordinates are source pixels. */
public record BossBarHead(ResourceLocation texture, int u, int v, int width, int height,
                          int textureWidth, int textureHeight) {
    public BossBarHead {
        Objects.requireNonNull(texture);
        if (u < 0 || v < 0 || width < 1 || height < 1 || textureWidth < 1 || textureHeight < 1
                || (long) u + width > textureWidth || (long) v + height > textureHeight)
            throw new IllegalArgumentException("Head region must fit inside its texture");
    }

    public static BossBarHead image(ResourceLocation texture, int width, int height) {
        return new BossBarHead(texture, 0, 0, width, height, width, height);
    }
}
