package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ShadowHerobrineShootDarkObGoal extends Goal {
    private final ShadowHerobrineEntity shadowHerobrine;
    private LivingEntity target;

    public ShadowHerobrineShootDarkObGoal(ShadowHerobrineEntity shadowHerobrine) {
        this.shadowHerobrine = shadowHerobrine;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.shadowHerobrine.getTarget();

        return this.target != null
                && this.target.isAlive()
                && !this.target.isRemoved()
                && this.shadowHerobrine.isDarkObReady()
                && this.shadowHerobrine.getObsidianMachineGunTick() == 0
                && !this.shadowHerobrine.isSacrificing()
                && !this.shadowHerobrine.isPassenger()
                && !RigStunController.isStunned(this.shadowHerobrine)
                && !RigAnimationController.hasActiveAnimation(this.shadowHerobrine);
    }

    @Override
    public void start() {
        this.shadowHerobrine.getNavigation().stop();
        this.shadowHerobrine.setAggressive(false);
        RigAnimationController.play(this.shadowHerobrine, RigAnimationSpecs.get(RigAnimationId.POINT_LEFT_HAND_TOWARD), this.target);
    }

    @Override
    public boolean canContinueToUse() {
        return RigAnimationController.getActiveAnimationId(this.shadowHerobrine) == RigAnimationId.POINT_LEFT_HAND_TOWARD;
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
        this.target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}