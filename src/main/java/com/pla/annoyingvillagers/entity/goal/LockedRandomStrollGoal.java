package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.util.RidingUtil;
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
        if (mob.isPassenger() && !RidingUtil.hasUsableMountedMob(mob)) return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (mob.getTarget() != null) return false;
        if (isPlayingIdle()) return false;
        if (!canContinueAnimationState()) return false;
        if (mob.isPassenger()) {
            return RidingUtil.hasUsableMountedMob(mob) && !RidingUtil.isNavigationDone(mob);
        }
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        if (mob instanceof AVNpc avNpc) {
            avNpc.onLockedRandomStrollGoalStart();
        }
        setStrolling(true);
        if (mob.isPassenger()) {
            RidingUtil.moveTo(mob, this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        } else {
            super.start();
        }
    }

    @Override
    public void stop() {
        setStrolling(false);
        if (mob instanceof AVNpc avNpc) {
            avNpc.onLockedRandomStrollGoalStop();
        }
        RidingUtil.stopNavigation(mob);
    }
}
