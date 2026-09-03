package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.rig.LockableRigAttackAnimation;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpec;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigColliderSystem;
import com.pla.annoyingvillagers.rig.RigCombatProfile;
import com.pla.annoyingvillagers.rig.RigCombatProfiles;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.util.RidingUtil;
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

    private final PathfinderMob mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;

    private Path path;
    private int ticksUntilNextPathRecalculation;
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
        if (!isValidMeleeState(target) || isMovementLocked()) return false;

        if (isProfileAttackLocked()) {
            if (!canMoveWhileProfileAttackLocked()) return false;
            this.path = RidingUtil.createNavigationPath(this.mob, target);
            return true;
        }

        RigCombatProfile profile = RigCombatProfiles.getCombatProfile(this.mob);
        if (hasBlockingAnimation(profile)) return false;
        if (DangerousReaction.hasDangerousTarget(this.mob)) {
            this.path = null;
            return canStartAnyAttack(target, profile);
        }
        this.path = RidingUtil.createNavigationPath(this.mob, target);
        return this.path != null || canStartAnyAttack(target, profile);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (!isValidMeleeState(target) || isMovementLocked()) return false;
        if (isProfileAttackLocked()) return canMoveWhileProfileAttackLocked();

        RigCombatProfile profile = RigCombatProfiles.getCombatProfile(this.mob);
        if (DangerousReaction.hasDangerousTarget(this.mob)) {
            return RigAnimationController.hasActiveAnimation(this.mob) || canStartAnyAttack(target, profile);
        }
        return this.followingTargetEvenIfNotSeen || !RidingUtil.isNavigationDone(this.mob) || RigAnimationController.hasActiveAnimation(this.mob) || canStartAnyAttack(target, profile);
    }

    @Override
    public void start() {
        if (this.path != null && !DangerousReaction.hasDangerousTarget(this.mob)) RidingUtil.moveToPath(this.mob, this.path, this.speedModifier);
        this.mob.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
    }

    @Override
    public void stop() {
        this.mob.setAggressive(false);
        RidingUtil.stopNavigation(this.mob);
        this.path = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        if (!RigAnimationController.hasActiveAnimation(this.mob)) {
            return true;
        }
        return RigAnimationController.hasActiveProfileAttack(this.mob)
                && RigAnimationController.isAttackChainReady(this.mob);
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) return;
        if (isMovementLocked()) {
            RidingUtil.stopNavigation(this.mob);
            return;
        }

        updateWeaponComboState();
        RidingUtil.lookAtTarget(this.mob, target, 60.0F, 60.0F);

        // A profile attack lock only blocks attack selection. Keep closing the
        // distance so snake-blade users do not freeze in place for the whole action.
        if (isProfileAttackLocked()) {
            if (canMoveWhileProfileAttackLocked()) {
                repathToTargetWhileAttackLocked(target);
            } else {
                RidingUtil.stopNavigation(this.mob);
            }
            return;
        }

        RigCombatProfile profile = RigCombatProfiles.getCombatProfile(this.mob);
        if (hasBlockingAnimation(profile)) {
            RidingUtil.stopNavigation(this.mob);
            return;
        }

        RigAnimationId selectedAnimation = isNormalAttackChainReady(profile) ? selectQueuedNormalAttack(target, profile) : selectAnimation(target, profile);
        if (selectedAnimation != null) {
            playAnimationAttack(target, selectedAnimation);
            return;
        }
        repathToTarget(target);
    }

    private RigAnimationId selectAnimation(LivingEntity target, RigCombatProfile profile) {
        if (this.mob.isPassenger()) return canStartAttack(target, RigAnimationId.BASIC_MOUNT_ATTACK) ? RigAnimationId.BASIC_MOUNT_ATTACK : null;

        RigAnimationId normal = profile.normalAt(this.normalComboIndex);
        if (!canStartAttack(target, normal)) {
            RigAnimationId closing = profile.pickClosingAttack(this.mob.getRandom(), this.previousAnimation);
            return closing != null && canStartAttack(target, closing) ? closing : null;
        }

        RigAnimationId interrupt = profile.pickInterrupt(this.mob.getRandom(), this.previousAnimation);
        if (interrupt != null) return interrupt;
        this.normalComboIndex = (this.normalComboIndex + 1) % profile.normalAttacks().size();
        return normal;
    }

    private RigAnimationId selectQueuedNormalAttack(LivingEntity target, RigCombatProfile profile) {
        RigAnimationId normal = profile.normalAt(this.normalComboIndex);
        if (!canStartAttack(target, normal)) return null;
        this.normalComboIndex = (this.normalComboIndex + 1) % profile.normalAttacks().size();
        return normal;
    }

    private void playAnimationAttack(LivingEntity target, RigAnimationId animationId) {
        RigAnimationSpec spec = RigAnimationSpecs.get(animationId);
        RigAnimationController.play(this.mob, spec, target);
        this.previousAnimation = animationId;
    }

    private boolean hasBlockingAnimation(RigCombatProfile profile) {
        if (!RigAnimationController.hasActiveAnimation(this.mob)) return false;
        return !isNormalAttackChainReady(profile);
    }

    private boolean isNormalAttackChainReady(RigCombatProfile profile) {
        if (!RigAnimationController.isAttackChainReady(this.mob)) return false;
        RigAnimationId activeAnimation = RigAnimationController.getActiveAnimationId(this.mob);
        return activeAnimation != null && profile.normalAttacks().contains(activeAnimation);
    }

    private void repathToTarget(LivingEntity target) {
        if (DangerousReaction.hasDangerousTarget(this.mob)) {
            RidingUtil.stopNavigation(this.mob);
            return;
        }
        if (--this.ticksUntilNextPathRecalculation > 0 && !RidingUtil.isNavigationDone(this.mob)) return;
        this.ticksUntilNextPathRecalculation = PATH_RECALCULATION_BASE_TICKS + this.mob.getRandom().nextInt(7);
        RidingUtil.moveTo(this.mob, target, this.speedModifier);
    }

    private void repathToTargetWhileAttackLocked(LivingEntity target) {
        if (this.mob.isPassenger()) return;
        if (--this.ticksUntilNextPathRecalculation > 0 && !RidingUtil.isNavigationDone(this.mob)) return;
        this.ticksUntilNextPathRecalculation = PATH_RECALCULATION_BASE_TICKS + this.mob.getRandom().nextInt(7);
        RidingUtil.moveTo(this.mob, target, this.speedModifier);
    }

    private void updateWeaponComboState() {
        ItemStack mainHand = this.mob.getMainHandItem();
        Item currentWeapon = mainHand.isEmpty() ? null : mainHand.getItem();
        if (currentWeapon == this.lastWeaponItem) return;
        this.lastWeaponItem = currentWeapon;
        this.normalComboIndex = 0;
        this.previousAnimation = null;
    }

    private boolean isMovementLocked() {
        return RigStunController.isStunned(this.mob);
    }

    private boolean isProfileAttackLocked() {
        return this.mob instanceof LockableRigAttackAnimation lockable && lockable.isLocked();
    }

    private boolean canMoveWhileProfileAttackLocked() {
        return DemoniacVoltageReaverItem.hasSnakeProfileAttackLock(this.mob);
    }

    private boolean isValidMeleeState(LivingEntity target) {
        if (this.mob instanceof ReaperHerobrineEntity reaper && reaper.isSecondFormDragonRider()) {
            return false;
        }
        return !this.mob.level().isClientSide && this.mob.isAlive() && !this.mob.isRemoved() && !this.mob.isDeadOrDying() && !this.mob.isNoAi()
                && !(this.mob.getMainHandItem().getItem() instanceof BowItem) && target != null && target.isAlive() && !target.isRemoved() && !target.isDeadOrDying();
    }

    private boolean canStartNormalAttack(LivingEntity target, RigCombatProfile profile) {
        return canStartAttack(target, profile.normalAt(this.normalComboIndex));
    }

    private boolean canStartAnyAttack(LivingEntity target, RigCombatProfile profile) {
        if (this.mob.isPassenger()) return canStartAttack(target, RigAnimationId.BASIC_MOUNT_ATTACK);
        for (RigAnimationId animationId : profile.normalAttacks()) if (canStartAttack(target, animationId)) return true;
        for (RigAnimationId animationId : profile.specialAttacks()) if (canStartAttack(target, animationId)) return true;
        for (RigAnimationId animationId : profile.ultimateAttacks()) if (canStartAttack(target, animationId)) return true;
        return false;
    }

    private boolean canStartAttack(LivingEntity target, RigAnimationId animationId) {
        RigAnimationSpec spec = RigAnimationSpecs.get(animationId);
        return spec.damagesTarget() && RigColliderSystem.canStartAttack(this.mob, target, spec);
    }
}
