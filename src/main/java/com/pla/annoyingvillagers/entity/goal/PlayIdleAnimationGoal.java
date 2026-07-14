package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.annoyingvillagers.entity.JevEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class PlayIdleAnimationGoal extends Goal {
    private final AVNpc avNpc;
    private final int maxDurationTicks;
    private int ticksLeft;
    @Nullable
    private IdleAnimation activeChoice;

    public PlayIdleAnimationGoal(AVNpc avNpc, int maxDurationTicks) {
        this.avNpc = avNpc;
        this.maxDurationTicks = maxDurationTicks;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (!baseCanRun()) return false;
        if (!avNpc.isIdleAnimationGoalAvailable()) return false;
        if (avNpc.isPlayingIdle()) return false;
        if (avNpc.getPlayingIdleCooldown() != 0) return false;
        if (avNpc.getNavigation().isInProgress() && !avNpc.isStrolling()) return false;

        IdleAnimation choice = avNpc.getIdleAnimationChoice();
        return avNpc.canStartIdleAnimationGoal(choice);
    }

    @Override
    public boolean canContinueToUse() {
        if (ticksLeft <= 0) return false;
        if (!baseCanRun()) return false;
        if (!avNpc.isPlayingIdle()) return false;
        if (avNpc.getNavigation().isInProgress()) return false;

        return avNpc.canContinueIdleAnimationGoal(activeChoice, ticksLeft);
    }

    @Override
    public void start() {
        ticksLeft = maxDurationTicks;
        activeChoice = avNpc.getIdleAnimationChoice();
        if (activeChoice == null) {
            activeChoice = pickIdleAnimation();
            avNpc.setIdleAnimationChoice(activeChoice);
        }

        avNpc.getNavigation().stop();
        avNpc.setDeltaMovement(0.0D, 0.0D, 0.0D);
        avNpc.setPlayingIdle(true);
        avNpc.onIdleAnimationGoalStart(activeChoice);
    }

    @Override
    public void tick() {
        if (avNpc.getTarget() != null
                || !avNpc.onGround()
                || avNpc.getNavigation().isInProgress()
                || avNpc.isHealing()) {
            ticksLeft = 0;
            return;
        }

        if (!(avNpc.level() instanceof ServerLevel)) {
            ticksLeft = 0;
            return;
        }

        avNpc.getNavigation().stop();
        avNpc.setDeltaMovement(0.0D, 0.0D, 0.0D);

        if (activeChoice != null) {
            avNpc.onIdleAnimationGoalTick(activeChoice);
        }
        ticksLeft--;
    }

    @Override
    public void stop() {
        IdleAnimation stoppedChoice = activeChoice;
        avNpc.onIdleAnimationGoalStop(stoppedChoice);
        avNpc.clearIdleAnimationState();
        avNpc.setPlayingIdle(false);
        avNpc.setPlayingIdleCooldown(new Random().nextInt(400, 1200));
        activeChoice = null;
        ticksLeft = 0;
    }

    private boolean baseCanRun() {
        if (avNpc.level().isClientSide) return false;
        if (avNpc instanceof JevEntity) return false;
        if (avNpc.tickCount <= 30) return false;
        if (!avNpc.isAlive() || avNpc.isRemoved() || avNpc.isDeadOrDying()) return false;
        if (avNpc.isPassenger()) return false;
        if (avNpc.getTarget() != null) return false;
        if (!avNpc.onGround()) return false;
        return !avNpc.isHealing();
    }

    private IdleAnimation pickIdleAnimation() {
        IdleAnimation[] all = IdleAnimation.values();
        return all[avNpc.getRandom().nextInt(all.length)];
    }
}
