package com.pla.annoyingvillagers.specialanimation;

import net.minecraft.world.entity.Mob;

import java.util.List;

public record SpecialAnimationSpec(SpecialAnimationId animationId, int durationTicks, float damageMultiplier, SpecialAttackWindow[] attackWindows, List<TimedHook> timedHooks) {
    public SpecialAnimationSpec {
        if (animationId == null) throw new IllegalArgumentException("animationId cannot be null");
        if (durationTicks <= 0) throw new IllegalArgumentException("durationTicks must be positive");
        if (attackWindows == null) attackWindows = new SpecialAttackWindow[0];
        if (timedHooks == null) timedHooks = List.of();
    }

    public int lastAttackWindowEndTick() {
        int result = 0;
        for (SpecialAttackWindow window : this.attackWindows) result = Math.max(result, window.endTickExclusive());
        return result;
    }

    @FunctionalInterface
    public interface Hook {
        void run(Mob mob);
    }

    public record TimedHook(int tick, Hook hook) {
        public static TimedHook at(int tick, Hook hook) {
            return new TimedHook(tick, hook);
        }
    }
}
