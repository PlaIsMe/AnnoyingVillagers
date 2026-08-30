package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.DangerousReaction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class KeepPositionGoal extends Goal {
    private static final double EATING_MOVE_SPEED = 1.0D;

    private final Mob mob;
    private int eatingRepathTicks;

    public KeepPositionGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return DangerousReaction.hasDangerousTarget(this.mob) || this.isEatingInCombat();
    }

    @Override
    public boolean canContinueToUse() {
        return DangerousReaction.hasDangerousTarget(this.mob) || this.isEatingInCombat();
    }

    @Override
    public void start() {
        this.eatingRepathTicks = 0;
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.eatingRepathTicks = 0;
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (this.isEatingInCombat()) {
            this.tickEatingKeepPosition(target);
            return;
        }

        this.mob.getNavigation().stop();
    }

    private boolean isEatingInCombat() {
        LivingEntity target = this.mob.getTarget();
        return this.mob instanceof AVNpc avNpc
                && avNpc.isHealing()
                && target != null
                && target.isAlive()
                && !target.isRemoved();
    }

    private void tickEatingKeepPosition(LivingEntity target) {
        if (!(this.mob instanceof AVNpc avNpc) || target == null) {
            this.mob.getNavigation().stop();
            return;
        }

        if (this.eatingRepathTicks > 0 && !this.mob.getNavigation().isDone()) {
            this.eatingRepathTicks--;
            return;
        }

        this.eatingRepathTicks = 8 + this.mob.getRandom().nextInt(8);
        Vec3 awayPos = DefaultRandomPos.getPosAway(avNpc, 6, 3, target.position());
        if (awayPos != null) this.mob.getNavigation().moveTo(awayPos.x, awayPos.y, awayPos.z, EATING_MOVE_SPEED);
    }
}
