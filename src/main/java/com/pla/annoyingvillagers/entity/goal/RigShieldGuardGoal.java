package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.util.RidingUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;

import java.util.EnumSet;

public class RigShieldGuardGoal extends Goal {
    private static final double GUARD_MOVE_SPEED = 0.45D;
    private static final double APPROACH_DISTANCE_SQR = 7.0D * 7.0D;
    private static final int MIN_GUARD_TICKS = 24;
    private static final int RANDOM_GUARD_TICKS = 33;
    private static final int MIN_COOLDOWN_TICKS = 80;
    private static final int RANDOM_COOLDOWN_TICKS = 160;
    private static final int MIN_HITS_BEFORE_CANCEL = 1;
    private static final int RANDOM_HITS_BEFORE_CANCEL = 2;
    private static final int START_CHECK_INTERVAL_TICKS = 8;
    private static final float RANGED_THREAT_GUARD_CHANCE = 0.62F;
    private static final float REGULAR_GUARD_CHANCE = 0.18F;

    private final PathfinderMob mob;
    private LivingEntity target;
    private int guardTicks;
    private int cooldownTicks;
    private boolean wasSprinting;

    public RigShieldGuardGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.cooldownTicks > 0) {
            this.cooldownTicks--;
            return false;
        }
        if (!this.canTryGuard()) {
            return false;
        }
        if (this.mob.tickCount % START_CHECK_INTERVAL_TICKS != 0) {
            return false;
        }

        LivingEntity currentTarget = this.mob.getTarget();
        float chance = this.isRangedThreat(currentTarget) ? RANGED_THREAT_GUARD_CHANCE : REGULAR_GUARD_CHANCE;
        if (this.mob.getRandom().nextFloat() > chance) {
            return false;
        }

        this.target = currentTarget;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.guardTicks > 0
                && this.target != null
                && this.target.isAlive()
                && !this.target.isRemoved()
                && !this.target.isDeadOrDying()
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && RigShieldGuardController.hasOffhandShield(this.mob)
                && RigShieldGuardController.isGuarding(this.mob);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        if (this.target == null || !RigShieldGuardController.hasOffhandShield(this.mob)) {
            this.target = null;
            return;
        }

        this.guardTicks = MIN_GUARD_TICKS + this.mob.getRandom().nextInt(RANDOM_GUARD_TICKS);
        int hitBudget = MIN_HITS_BEFORE_CANCEL + this.mob.getRandom().nextInt(RANDOM_HITS_BEFORE_CANCEL);
        this.wasSprinting = this.mob.isSprinting();
        this.mob.setSprinting(false);
        this.mob.setAggressive(false);
        RigShieldGuardController.start(this.mob, this.guardTicks, hitBudget, this.target);
        this.updateMovement();
    }

    @Override
    public void tick() {
        this.guardTicks--;
        this.mob.setSprinting(false);
        this.mob.setAggressive(false);
        RigShieldGuardController.ensureUsingShield(this.mob);
        this.updateMovement();

        if (this.guardTicks <= 0) {
            RigShieldGuardController.stop(this.mob);
        }
    }

    @Override
    public void stop() {
        RigShieldGuardController.stop(this.mob);
        this.mob.setSprinting(this.wasSprinting);
        this.wasSprinting = false;
        this.guardTicks = 0;
        this.cooldownTicks = this.nextCooldown();
        this.target = null;
    }

    private boolean canTryGuard() {
        LivingEntity currentTarget = this.mob.getTarget();
        return !this.mob.level().isClientSide
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && !this.mob.isPassenger()
                && !this.mob.isUsingItem()
                && !this.mob.isOnFire()
                && !(this.mob instanceof AVNpc avNpc && avNpc.isHealing())
                && !RigShieldGuardController.isGuarding(this.mob)
                && !RigAnimationController.hasActiveAnimation(this.mob)
                && RigShieldGuardController.hasOffhandShield(this.mob)
                && currentTarget != null
                && currentTarget.isAlive()
                && !currentTarget.isRemoved()
                && !currentTarget.isDeadOrDying();
    }

    private void updateMovement() {
        if (this.target == null) {
            RidingUtil.stopNavigation(this.mob);
            return;
        }

        RidingUtil.lookAtTarget(this.mob, this.target, 70.0F, 70.0F);
        if (this.mob.distanceToSqr(this.target) > APPROACH_DISTANCE_SQR) {
            RidingUtil.moveTo(this.mob, this.target, GUARD_MOVE_SPEED);
        } else {
            RidingUtil.stopNavigation(this.mob);
        }
    }

    private boolean isRangedThreat(LivingEntity target) {
        return target != null
                && ((target.isUsingItem() && isRangedWeapon(target.getUseItem()))
                || isRangedWeapon(target.getMainHandItem())
                || isRangedWeapon(target.getOffhandItem()));
    }

    private boolean isRangedWeapon(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() instanceof BowItem
                || stack.getItem() instanceof CrossbowItem
                || stack.getItem() instanceof ProjectileWeaponItem);
    }

    private int nextCooldown() {
        return MIN_COOLDOWN_TICKS + this.mob.getRandom().nextInt(RANDOM_COOLDOWN_TICKS);
    }
}
