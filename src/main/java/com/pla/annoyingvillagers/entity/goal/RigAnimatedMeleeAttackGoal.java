package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpec;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigCombatProfile;
import com.pla.annoyingvillagers.rig.RigCombatProfiles;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class RigAnimatedMeleeAttackGoal extends Goal {
    private static final int PATH_RECALCULATION_BASE_TICKS = 4;
    private static final int MIN_CLOSING_ATTACK_DISTANCE_BLOCKS = 6;

    private final PathfinderMob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;

    private Path path;
    private int ticksUntilNextPathRecalculation;
    private int attackCooldownTicks;
    private int activeAnimationTicks;
    private int normalComboIndex;
    private Item lastWeaponItem;
    private RigAnimationId previousAnimation;

    public RigAnimatedMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (!isValidMeleeState(target)) {
            return false;
        }

        this.path = this.mob.getNavigation().createPath(target, 0);
        return this.path != null || isWithinMeleeReach(target);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (!isValidMeleeState(target)) {
            return false;
        }

        return this.followingTargetEvenIfNotSeen || !this.mob.getNavigation().isDone() || this.activeAnimationTicks > 0 || isWithinMeleeReach(target);
    }

    @Override
    public void start() {
        if (this.path != null) {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
        }

        this.mob.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.attackCooldownTicks = 0;
        this.activeAnimationTicks = 0;
    }

    @Override
    public void stop() {
        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
        this.path = null;
        this.activeAnimationTicks = 0;
        this.attackCooldownTicks = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return this.activeAnimationTicks <= 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }

        updateWeaponComboState();
        this.mob.getLookControl().setLookAt(target, 60.0F, 60.0F);

        if (this.attackCooldownTicks > 0) {
            this.attackCooldownTicks--;
        }

        if (this.activeAnimationTicks > 0) {
            this.activeAnimationTicks--;
            this.mob.getNavigation().stop();
            return;
        }

        RigCombatProfile profile = RigCombatProfiles.getCombatProfile(this.mob);
        RigAnimationId selectedAnimation = selectAnimation(target, profile);

        if (selectedAnimation != null) {
            playAnimationAttack(target, selectedAnimation);
            return;
        }

        repathToTarget(target);
    }

    private RigAnimationId selectAnimation(LivingEntity target, RigCombatProfile profile) {
        if (this.attackCooldownTicks > 0) {
            return null;
        }

        if (!isWithinMeleeReach(target)) {
            if (profile.hasClosingAttack() && this.mob.distanceTo(target) <= maxClosingAttackDistance(profile, target)) {
                return profile.pickClosingAttack(this.mob.getRandom(), this.previousAnimation);
            }

            return null;
        }

        RigAnimationId interrupt = profile.pickInterrupt(this.mob.getRandom(), this.previousAnimation);
        if (interrupt != null) {
            return interrupt;
        }

        RigAnimationId normal = profile.normalAt(this.normalComboIndex);
        this.normalComboIndex = (this.normalComboIndex + 1) % profile.normalAttacks().size();
        return normal;
    }

    private void playAnimationAttack(LivingEntity target, RigAnimationId animationId) {
        RigAnimationSpec spec = RigAnimationSpecs.get(animationId);
        RigAnimationController.play(this.mob, spec, target);
        this.previousAnimation = animationId;
        this.activeAnimationTicks = spec.durationTicks();
        this.attackCooldownTicks = spec.durationTicks() + 4 + this.mob.getRandom().nextInt(5);
    }

    private void repathToTarget(LivingEntity target) {
        if (--this.ticksUntilNextPathRecalculation > 0 && !this.mob.getNavigation().isDone()) {
            return;
        }

        this.ticksUntilNextPathRecalculation = PATH_RECALCULATION_BASE_TICKS + this.mob.getRandom().nextInt(7);
        this.mob.getNavigation().moveTo(target, this.speedModifier);
    }

    private void updateWeaponComboState() {
        ItemStack mainHand = this.mob.getMainHandItem();
        Item currentWeapon = mainHand.isEmpty() ? null : mainHand.getItem();
        if (currentWeapon == this.lastWeaponItem) {
            return;
        }

        this.lastWeaponItem = currentWeapon;
        this.normalComboIndex = 0;
        this.previousAnimation = null;
    }

    private boolean isValidMeleeState(LivingEntity target) {
        return !this.mob.level().isClientSide
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && !this.mob.isPassenger()
                && !(this.mob.getMainHandItem().getItem() instanceof BowItem)
                && target != null
                && target.isAlive()
                && !target.isRemoved()
                && !target.isDeadOrDying();
    }

    private boolean isWithinMeleeReach(LivingEntity target) {
        double reach = this.mob.getBbWidth() * 2.0D + target.getBbWidth() + 1.0D;
        return this.mob.distanceToSqr(target) <= reach * reach;
    }

    private double maxClosingAttackDistance(RigCombatProfile profile, LivingEntity target) {
        double distance = 0.0D;
        for (RigAnimationId animationId : profile.specialAttacks()) {
            RigAnimationSpec spec = RigAnimationSpecs.get(animationId);
            distance = Math.max(distance, spec.attackReachBlocks() + spec.lungeDistanceBlocks());
        }

        return Math.max(distance + this.mob.getBbWidth() + target.getBbWidth(), MIN_CLOSING_ATTACK_DISTANCE_BLOCKS);
    }
}
