package com.pla.annoyingvillagers.util;

import net.minecraft.world.level.Level;

/** Compatibility names for code that intentionally follows the overworld clock. */
public final class LegacyLevelTime {
    private LegacyLevelTime() {
    }

    public static long dayTime(Level level) {
        return level.getOverworldClockTime();
    }

    public static boolean isNight(Level level) {
        long timeOfDay = Math.floorMod(dayTime(level), 24000L);
        return timeOfDay >= 13000L && timeOfDay < 23000L;
    }
}
