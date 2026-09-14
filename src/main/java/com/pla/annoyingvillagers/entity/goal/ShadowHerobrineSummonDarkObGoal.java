package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.LivingEntity;

import java.util.EnumSet;

public class ShadowHerobrineSummonDarkObGoal extends AnimatedMobGoal {
    private final ShadowHerobrineEntity shadowHerobrine;
    private LivingEntity target;

    public ShadowHerobrineSummonDarkObGoal(ShadowHerobrineEntity shadowHerobrine) {
        super(shadowHerobrine);
        this.shadowHerobrine = shadowHerobrine;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.shadowHerobrine.getTarget();

        return this.target != null
                && this.target.isAlive()
                && !this.target.isRemoved()
                && !this.shadowHerobrine.isDarkObReady()
                && this.shadowHerobrine.getSummonDarkObCooldown() == 0
                && this.shadowHerobrine.getObsidianMachineGunTick() == 0
                && !this.shadowHerobrine.isSacrificing()
                && !this.shadowHerobrine.isPassenger()
                && !this.isAnimationStunned()
                && !this.isAnimationBusy();
    }

    @Override
    public void start() {
        this.shadowHerobrine.getNavigation().stop();
        this.shadowHerobrine.setAggressive(false);
        this.playGoalAnimation(RigAnimationId.POINT_LEFT_HAND_MIDDLE, this.target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.isGoalAnimationPlaying(RigAnimationId.POINT_LEFT_HAND_MIDDLE);
    }

    @Override
    public void tick() {
        this.shadowHerobrine.getNavigation().stop();

        if (this.target != null && this.target.isAlive()) {
            this.shadowHerobrine.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        }
    }

    @Override
    public void stop() {
        this.finishGoalAnimation();
        this.target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
