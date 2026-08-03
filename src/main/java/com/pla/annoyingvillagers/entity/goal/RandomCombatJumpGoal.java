package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RandomCombatJumpGoal extends Goal {
    private static final int MIN_COOLDOWN_TICKS = 15;
    private static final int RANDOM_COOLDOWN_TICKS = 31;
    private static final int JUMP_CHANCE_BOUND = 4;

    private final AVNpc avNpc;
    private int cooldownTicks;

    public RandomCombatJumpGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
        this.cooldownTicks = this.nextCooldown();
        this.setFlags(EnumSet.of(Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (!this.hasCombatTarget()
                || this.avNpc.isNoAi()
                || this.avNpc.isPassenger()
                || this.avNpc.isInWater()
                || this.avNpc.isInLava()
                || this.avNpc.isHealing()
                || !this.avNpc.onGround()) {
            return false;
        }

        if (this.cooldownTicks > 0) {
            this.cooldownTicks--;
            return false;
        }

        return this.avNpc.getRandom().nextInt(JUMP_CHANCE_BOUND) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        RigAnimationController.play(this.avNpc, RigAnimationId.JUMP);
        this.avNpc.jump();
        this.cooldownTicks = this.nextCooldown();
    }

    private boolean hasCombatTarget() {
        LivingEntity target = this.avNpc.getTarget();
        return target != null && target.isAlive();
    }

    private int nextCooldown() {
        return MIN_COOLDOWN_TICKS + this.avNpc.getRandom().nextInt(RANDOM_COOLDOWN_TICKS);
    }
}
