package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.RollItemUser;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpec;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.util.RidingUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RollItemGoal extends Goal {
    private final PathfinderMob mob;
    private final RollItemUser rollItemUser;
    private LivingEntity target;
    private RigAnimationId animationId;
    private int switchDelayTicks;
    private int animationTicks;
    private int ticks;
    private boolean rolledItem;

    public RollItemGoal(PathfinderMob mob) {
        if (!(mob instanceof RollItemUser rollItemUser)) {
            throw new IllegalArgumentException("RollItemGoal requires a RollItemUser mob");
        }

        this.mob = mob;
        this.rollItemUser = rollItemUser;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(this.mob.level() instanceof ServerLevel)
                || !this.mob.isAlive()
                || this.mob.isRemoved()
                || this.mob.isDeadOrDying()
                || this.mob.isNoAi()
                || this.mob.isPassenger()
                || this.mob.isUsingItem()
                || RigShieldGuardController.isGuarding(this.mob)
                || RigAnimationController.hasActiveAnimation(this.mob)
                || this.mob instanceof AVNpc avNpc && avNpc.isHealing()
                || !this.rollItemUser.canRollItem()) {
            return false;
        }

        LivingEntity currentTarget = this.mob.getTarget();
        if (currentTarget == null
                || !currentTarget.isAlive()
                || currentTarget.isRemoved()
                || currentTarget.isDeadOrDying()) {
            return false;
        }

        this.target = currentTarget;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.rolledItem
                || this.ticks < this.animationTicks;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return this.rolledItem && this.ticks >= this.animationTicks;
    }

    @Override
    public void start() {
        this.animationId = this.rollItemUser.selectRollItemAnimation(this.mob);
        RigAnimationSpec spec = RigAnimationSpecs.get(this.animationId);
        this.animationTicks = RigAnimationController.animationPlaybackTicks(spec);
        this.switchDelayTicks = Math.min(this.rollItemUser.getRollItemSwitchDelayTicks(this.animationId), this.animationTicks);
        this.ticks = 0;
        this.rolledItem = false;

        RigShieldGuardController.stop(this.mob);
        RidingUtil.stopNavigation(this.mob);
        if (this.target != null) {
            RidingUtil.lookAtTarget(this.mob, this.target, 60.0F, 60.0F);
        }
        RigAnimationController.play(this.mob, spec, this.target);
    }

    @Override
    public void tick() {
        if (this.target != null && this.target.isAlive()) {
            RidingUtil.lookAtTarget(this.mob, this.target, 60.0F, 60.0F);
        }

        if (!this.rolledItem && this.ticks >= this.switchDelayTicks) {
            this.rollItemUser.rollItem();
            this.rolledItem = true;
        }

        this.ticks++;
    }

    @Override
    public void stop() {
        this.target = null;
        this.animationId = null;
        this.switchDelayTicks = 0;
        this.animationTicks = 0;
        this.ticks = 0;
        this.rolledItem = false;
    }
}
