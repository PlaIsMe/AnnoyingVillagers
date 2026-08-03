package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpec;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.InventoryUtils;
import com.pla.annoyingvillagers.util.RidingUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class AVNpcRangedBowAttackGoal extends RangedBowAttackGoal<AVNpc> {
    private static final double MOUNTED_MELEE_SWITCH_DISTANCE_BLOCKS = 6.0D;

    private final AVNpc avNpc;
    private final double speedModifier;
    private int attackIntervalMin;
    private final float attackRadiusSqr;
    private ItemStack previousMainHand = ItemStack.EMPTY;
    private ItemStack previousOffHand = ItemStack.EMPTY;
    private boolean usingTemporaryBow;
    private boolean started;
    private int attackTime = -1;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public AVNpcRangedBowAttackGoal(AVNpc avNpc, double speedModifier, int attackIntervalMin, float attackRadius) {
        super(avNpc, speedModifier, attackIntervalMin, attackRadius);
        this.avNpc = avNpc;
        this.speedModifier = speedModifier;
        this.attackIntervalMin = attackIntervalMin;
        this.attackRadiusSqr = attackRadius * attackRadius;
    }

    @Override
    public void setMinAttackInterval(int attackCooldown) {
        super.setMinAttackInterval(attackCooldown);
        this.attackIntervalMin = attackCooldown;
    }

    @Override
    public boolean canUse() {
        return !RigShieldGuardController.isGuarding(this.avNpc)
                && !RigAnimationController.hasActiveAnimation(this.avNpc)
                && this.canUseBow()
                && (this.isHoldingBow() || InventoryUtils.hasBow(this.avNpc));
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUseBow()
                && this.isHoldingBow()
                && (this.avNpc.getTarget() != null || !RidingUtil.isNavigationDone(this.avNpc));
    }

    @Override
    public void start() {
        this.started = false;
        boolean equippedFromInventory = false;
        if (!this.isHoldingBow()) {
            if (!this.equipInventoryBow()) {
                return;
            }
            equippedFromInventory = true;
        }

        this.started = true;
        this.attackTime = -1;
        this.seeTime = 0;
        this.strafingTime = -1;
        if (equippedFromInventory && !this.avNpc.isPassenger()) {
            this.playSwapAnimationSequence();
        }
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        RidingUtil.stopNavigation(this.avNpc);
        this.avNpc.stopUsingItem();
        if (this.usingTemporaryBow) {
            this.restorePreviousHands();
        }
        if (this.started) {
            this.avNpc.setSwapToBowCooldown();
        }
        this.started = false;
        this.seeTime = 0;
        this.attackTime = -1;
        this.strafingTime = -1;
    }

    @Override
    protected boolean isHoldingBow() {
        return this.avNpc.isHolding(stack -> stack.getItem() instanceof BowItem);
    }

    @Override
    public void tick() {
        LivingEntity target = this.avNpc.getTarget();
        if (target == null) {
            return;
        }

        double distanceSqr = this.avNpc.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean hasLineOfSight = this.avNpc.getSensing().hasLineOfSight(target);
        boolean wasSeeingTarget = this.seeTime > 0;
        if (hasLineOfSight != wasSeeingTarget) {
            this.seeTime = 0;
        }

        if (hasLineOfSight) {
            this.seeTime++;
        } else {
            this.seeTime--;
        }

        if (distanceSqr <= this.attackRadiusSqr && this.seeTime >= 20) {
            RidingUtil.stopNavigation(this.avNpc);
            this.strafingTime++;
        } else {
            RidingUtil.moveTo(this.avNpc, target, this.speedModifier);
            this.strafingTime = -1;
        }

        if (this.strafingTime >= 20) {
            if (this.avNpc.getRandom().nextFloat() < 0.3F) {
                this.strafingClockwise = !this.strafingClockwise;
            }
            if (this.avNpc.getRandom().nextFloat() < 0.3F) {
                this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
        }

        if (this.strafingTime > -1) {
            if (distanceSqr > this.attackRadiusSqr * 0.75F) {
                this.strafingBackwards = false;
            } else if (distanceSqr < this.attackRadiusSqr * 0.25F) {
                this.strafingBackwards = true;
            }

            if (!this.avNpc.isPassenger()) {
                this.avNpc.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            }
            Entity vehicle = this.avNpc.getVehicle();
            if (vehicle instanceof Mob mount) {
                mount.lookAt(target, 30.0F, 30.0F);
            }

            this.avNpc.lookAt(target, 30.0F, 30.0F);
        } else {
            RidingUtil.lookAtTarget(this.avNpc, target, 30.0F, 30.0F);
        }

        if (this.avNpc.isUsingItem()) {
            if (!hasLineOfSight && this.seeTime < -60) {
                this.avNpc.stopUsingItem();
            } else if (hasLineOfSight) {
                int ticksUsingItem = this.avNpc.getTicksUsingItem();
                if (ticksUsingItem >= 20) {
                    this.avNpc.stopUsingItem();
                    this.avNpc.performRangedAttack(target, BowItem.getPowerForTime(ticksUsingItem));
                    this.attackTime = this.attackIntervalMin;
                }
            }
        } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
            this.avNpc.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.avNpc, item -> item instanceof BowItem));
        }
    }

    private boolean canUseBow() {
        LivingEntity target = this.avNpc.getTarget();
        return !this.avNpc.level().isClientSide
                && this.avNpc.isAlive()
                && !this.avNpc.isRemoved()
                && !this.avNpc.isDeadOrDying()
                && !this.avNpc.isNoAi()
                && !this.avNpc.isHealing()
                && !this.avNpc.isOnFire()
                && !this.avNpc.isInLava()
                && this.avNpc.isUseBow()
                && this.avNpc.getSwapToBowCooldown() <= 0
                && target != null
                && target.isAlive()
                && !target.isRemoved()
                && !target.isDeadOrDying()
                && !isMountedMeleeRange(target)
                && InventoryUtils.hasArrowAmmo(this.avNpc, target instanceof HerobrineMob);
    }

    private boolean isMountedMeleeRange(LivingEntity target) {
        return this.avNpc.isPassenger()
                && this.avNpc.distanceToSqr(target) <= MOUNTED_MELEE_SWITCH_DISTANCE_BLOCKS * MOUNTED_MELEE_SWITCH_DISTANCE_BLOCKS;
    }

    private boolean equipInventoryBow() {
        ItemStack bow = InventoryUtils.consumeBow(this.avNpc).orElse(ItemStack.EMPTY);
        if (bow.isEmpty()) {
            return false;
        }

        this.previousMainHand = this.avNpc.getMainHandItem().copy();
        this.previousOffHand = this.avNpc.getOffhandItem().copy();
        this.usingTemporaryBow = true;
        this.avNpc.setUseBow(true);
        this.avNpc.setItemInHand(InteractionHand.MAIN_HAND, bow.copy());
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        return true;
    }

    private void restorePreviousHands() {
        ItemStack currentMainHand = this.avNpc.getMainHandItem().copy();
        if (!currentMainHand.isEmpty() && currentMainHand.getItem() instanceof BowItem) {
            this.giveOrDrop(currentMainHand);
        }

        this.avNpc.setItemInHand(InteractionHand.MAIN_HAND, this.previousMainHand.copy());
        this.avNpc.setItemInHand(InteractionHand.OFF_HAND, this.previousOffHand.copy());
        this.previousMainHand = ItemStack.EMPTY;
        this.previousOffHand = ItemStack.EMPTY;
        this.usingTemporaryBow = false;
    }

    private void playSwapAnimationSequence() {
        boolean roll = this.avNpc.getRandom().nextBoolean();
        RigAnimationId backward = roll ? RigAnimationId.ROLL_BACKWARD : RigAnimationId.STEP_BACKWARD;
        RigAnimationId forward = roll ? RigAnimationId.ROLL_FORWARD : RigAnimationId.STEP_FORWARD;
        LivingEntity target = this.avNpc.getTarget();
        RigAnimationSpec backwardSpec = RigAnimationSpecs.get(backward);

        RigAnimationController.play(this.avNpc, backwardSpec, target);
        new DelayedTask(RigAnimationController.animationPlaybackTicks(backwardSpec)) {
            @Override
            public void run() {
                if (avNpc.isAlive()
                        && !avNpc.isRemoved()
                        && !avNpc.isDeadOrDying()
                        && avNpc.getMainHandItem().getItem() instanceof BowItem) {
                    RigAnimationController.play(avNpc, RigAnimationSpecs.get(forward), target);
                }
            }
        };
    }

    private void giveOrDrop(ItemStack stack) {
        if (!InventoryUtils.addItem(this.avNpc, stack)) {
            this.avNpc.spawnAtLocation(stack);
        }
    }
}
