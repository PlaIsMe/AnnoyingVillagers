package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ObsidianMachineGunGoal extends Goal {
    private final ShadowHerobrineEntity shadowHerobrine;
    private LivingEntity target;

    public ObsidianMachineGunGoal(ShadowHerobrineEntity shadowHerobrine) {
        this.shadowHerobrine = shadowHerobrine;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.shadowHerobrine.getTarget();

        return !this.shadowHerobrine.level().isClientSide
                && this.shadowHerobrine.isAlive()
                && !this.shadowHerobrine.isNoAi()
                && !this.shadowHerobrine.isPassenger()
                && this.target != null
                && this.target.isAlive()
                && this.shadowHerobrine.getState() == 2
                && this.shadowHerobrine.getObsidianMachineGunCooldown() == 0
                && this.shadowHerobrine.getObsidianMachineGunTick() == 0
                && !this.shadowHerobrine.isHealing()
                && !this.shadowHerobrine.isSacrificing()
                && !RigStunController.isStunned(this.shadowHerobrine)
                && !RigAnimationController.hasActiveAnimation(this.shadowHerobrine);
    }

    @Override
    public void start() {
        // AV_EFM play animations
        this.shadowHerobrine.getNavigation().stop();
        this.shadowHerobrine.setAggressive(false);

        RigAnimationController.play(this.shadowHerobrine, RigAnimationSpecs.get(RigAnimationId.OBSIDIAN_MACHINE_GUN), this.target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.shadowHerobrine.isAlive()
                && RigAnimationController.getActiveAnimationId(this.shadowHerobrine) == RigAnimationId.OBSIDIAN_MACHINE_GUN;
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