package com.pla.annoyingvillagers.rig;

import net.minecraft.world.entity.Mob;

import java.util.List;

public record RigAnimationSpec(
        RigAnimationId animationId,
        int durationTicks,
        RigAttackWindow[] attackWindows,
        RigAnimationPlaybackType playbackType,
        boolean damagesTarget,
        boolean jumpOnStart,
        List<RigTimedAnimationHook> timedHooks
) {
    public RigAnimationSpec {
        if (animationId == null) throw new IllegalArgumentException("animationId cannot be null");
        if (durationTicks <= 0) throw new IllegalArgumentException("durationTicks must be > 0");
        if (attackWindows == null) throw new IllegalArgumentException("attackWindows cannot be null");
        attackWindows = attackWindows.clone();
        for (RigAttackWindow attackWindow : attackWindows) {
            if (attackWindow == null) throw new IllegalArgumentException("attackWindows cannot contain null");
            if (attackWindow.endTickExclusive() > durationTicks) throw new IllegalArgumentException("attack window cannot end after durationTicks");
        }
        if (playbackType == null) throw new IllegalArgumentException("playbackType cannot be null");
        if (timedHooks == null) throw new IllegalArgumentException("timedHooks cannot be null");
        for (RigTimedAnimationHook timedHook : timedHooks) {
            if (timedHook == null) throw new IllegalArgumentException("timedHooks cannot contain null");
            if (timedHook.isTimed() && timedHook.tick() > durationTicks) throw new IllegalArgumentException("timed hook tick must be within animation duration");
        }
        timedHooks = List.copyOf(timedHooks);
        if (damagesTarget && attackWindows.length == 0) throw new IllegalArgumentException("Damaging attack specs require at least one attack window");
        if (!damagesTarget && attackWindows.length > 0) throw new IllegalArgumentException("Non-damaging specs cannot define attack windows");
        if (!damagesTarget && jumpOnStart) throw new IllegalArgumentException("jumpOnStart is only valid for attack specs");
    }

    public static RigAnimationSpec attack(RigAnimationId animationId, int durationTicks, boolean jumpOnStart, RigAttackWindow... attackWindows) {
        return attack(animationId, durationTicks, jumpOnStart, List.of(), attackWindows);
    }

    public static RigAnimationSpec attack(RigAnimationId animationId, int durationTicks, boolean jumpOnStart, List<RigTimedAnimationHook> timedHooks, RigAttackWindow... attackWindows) {
        return new RigAnimationSpec(animationId, durationTicks, attackWindows, RigAnimationPlaybackType.DEFAULT, true, jumpOnStart, timedHooks);
    }

    public static RigAnimationSpec rolling(RigAnimationId animationId, int durationTicks) {
        if (!animationId.isRolling()) throw new IllegalArgumentException("Rolling specs require ROLL_* or STEP_* animation ids");
        return nonDamaging(animationId, durationTicks);
    }

    public static RigAnimationSpec nonDamaging(RigAnimationId animationId, int durationTicks) {
        return nonDamaging(animationId, durationTicks, RigAnimationPlaybackType.DEFAULT);
    }

    public static RigAnimationSpec nonDamaging(RigAnimationId animationId, int durationTicks, RigAnimationPlaybackType playbackType) {
        return nonDamaging(animationId, durationTicks, playbackType, List.of());
    }

    public static RigAnimationSpec nonDamaging(RigAnimationId animationId, int durationTicks, RigAnimationPlaybackType playbackType, List<RigTimedAnimationHook> timedHooks) {
        return new RigAnimationSpec(animationId, durationTicks, new RigAttackWindow[0], playbackType, false, false, timedHooks);
    }

    @Override
    public RigAttackWindow[] attackWindows() {
        return this.attackWindows.clone();
    }

    public int[] impactDelayTicks() {
        int[] impactDelayTicks = new int[this.attackWindows.length];
        for (int i = 0; i < this.attackWindows.length; i++) impactDelayTicks[i] = this.attackWindows[i].fallbackImpactTick();
        return impactDelayTicks;
    }

    @FunctionalInterface
    public interface RigAnimationHook {
        RigAnimationHook NO_OP = mob -> {};
        void run(Mob mob);
    }

    public record RigTimedAnimationHook(int tick, RigAnimationHook action) {
        public static final int START = -1;
        public static final int END = -2;

        public RigTimedAnimationHook {
            if (tick < 0 && tick != START && tick != END) throw new IllegalArgumentException("tick must be >= 0, START, or END");
            if (action == null) throw new IllegalArgumentException("action cannot be null");
        }

        public static RigTimedAnimationHook at(int tick, RigAnimationHook action) {
            return new RigTimedAnimationHook(tick, action);
        }

        public boolean isStart() {
            return this.tick == START;
        }

        public boolean isEnd() {
            return this.tick == END;
        }

        public boolean isTimed() {
            return this.tick >= 0;
        }
    }
}
