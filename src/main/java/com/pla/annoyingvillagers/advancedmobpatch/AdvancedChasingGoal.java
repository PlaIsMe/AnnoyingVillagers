/*
 * SPDX-License-Identifier: GPL-3.0-only
 * Chasing behavior adapted from Combat Evolution by ShelMarow.
 */
package com.pla.annoyingvillagers.advancedmobpatch;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.EnumSet;
import java.util.function.BooleanSupplier;

public final class AdvancedChasingGoal extends Goal {
    private final MobPatch<? extends Mob> mobPatch;
    private final Mob mob;
    private final double attackRadius;
    private final double speedModifier;
    private final BooleanSupplier movementAllowed;
    private Path path;
    private int pathRecalculationTicks;

    public AdvancedChasingGoal(
            MobPatch<? extends Mob> mobPatch,
            double attackRadius,
            double speedModifier,
            BooleanSupplier movementAllowed
    ) {
        this.mobPatch = mobPatch;
        this.mob = mobPatch.getOriginal();
        this.attackRadius = Math.max(0.0D, attackRadius);
        this.speedModifier = Math.max(0.0D, speedModifier);
        this.movementAllowed = movementAllowed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (!this.canChase(target)) {
            return false;
        }
        this.path = this.mob.getNavigation().createPath(target, 0);
        return this.path != null || this.mob.distanceToSqr(target) > this.getAttackReachSqr(target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.canChase(this.mob.getTarget());
    }

    @Override
    public void start() {
        if (this.path != null) {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        }
        this.mob.setAggressive(true);
        this.pathRecalculationTicks = 0;
    }

    @Override
    public void stop() {
        LivingEntity target = this.mob.getTarget();
        if (target != null && !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.mob.setTarget(null);
        }
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }

        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        double distanceSqr = this.mob.distanceToSqr(target);
        if (distanceSqr <= this.attackRadius * this.attackRadius) {
            this.mob.getNavigation().stop();
            this.mobPatch.rotateTo(target, 30.0F, true);
            return;
        }

        if (--this.pathRecalculationTicks <= 0 && this.mob.getSensing().hasLineOfSight(target)) {
            this.pathRecalculationTicks = this.adjustedTickDelay(distanceSqr > 1024.0D ? 30 : distanceSqr > 256.0D ? 25 : 20);
            if (!this.mob.getNavigation().moveTo(target, this.speedModifier)) {
                this.pathRecalculationTicks += 15;
            }
        }
    }

    private boolean canChase(LivingEntity target) {
        return this.movementAllowed.getAsBoolean()
                && !this.mobPatch.getEntityState().inaction()
                && target != null
                && target.isAlive()
                && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target);
    }

    private double getAttackReachSqr(LivingEntity target) {
        return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + target.getBbWidth();
    }
}
