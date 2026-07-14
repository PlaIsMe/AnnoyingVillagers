package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class LockedRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
    public LockedRandomStrollGoal(PathfinderMob mob, double speed) {
        super(mob, speed);
    }

    private boolean isPlayingIdle() {
        return mob instanceof AVNpc avNpc && avNpc.isPlayingIdle();
    }

    private void setStrolling(boolean strolling) {
        if (mob instanceof AVNpc avNpc) {
            avNpc.setStrolling(strolling);
        }
    }

    private boolean canUseAnimationState() {
        return !(mob instanceof AVNpc avNpc) || avNpc.canUseLockedRandomStrollGoal();
    }

    private boolean canContinueAnimationState() {
        return !(mob instanceof AVNpc avNpc) || avNpc.canContinueLockedRandomStrollGoal();
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null) return false;
        if (isPlayingIdle()) return false;
        if (!canUseAnimationState()) return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.getTarget() != null) return false;
        if (isPlayingIdle()) return false;
        if (!canContinueAnimationState()) return false;
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        if (mob instanceof AVNpc avNpc) {
            avNpc.onLockedRandomStrollGoalStart();
        }
        setStrolling(true);
        super.start();
    }

    @Override
    public void stop() {
        setStrolling(false);
        if (mob instanceof AVNpc avNpc) {
            avNpc.onLockedRandomStrollGoalStop();
        }
        super.stop();
    }
}
