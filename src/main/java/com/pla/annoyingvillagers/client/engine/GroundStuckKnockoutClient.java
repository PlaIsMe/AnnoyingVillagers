package com.pla.annoyingvillagers.client.engine;

import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

public final class GroundStuckKnockoutClient {
    private static final Map<Integer, SpinState> SPINS = new HashMap<>();
    private GroundStuckKnockoutClient() {}

    public static void set(int entityId, int ticks) {
        if (ticks <= 0) { SPINS.remove(entityId); return; }
        if (Minecraft.getInstance().level != null) SPINS.put(entityId, new SpinState(Minecraft.getInstance().level.getGameTime(), ticks));
    }

    public static float getAngle(int entityId, float partialTick) {
        SpinState state = SPINS.get(entityId);
        if (state == null || Minecraft.getInstance().level == null) return Float.NaN;
        long age = Minecraft.getInstance().level.getGameTime() - state.startTick;
        if (age < 0L || age > state.duration) { SPINS.remove(entityId); return Float.NaN; }
        return (age + partialTick) * 48.0F;
    }

    private record SpinState(long startTick, int duration) {}
}
