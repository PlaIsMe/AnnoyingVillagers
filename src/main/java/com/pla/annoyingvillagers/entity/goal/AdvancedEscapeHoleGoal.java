package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.ai.RecoveryAi;
import com.pla.annoyingvillagers.rig.LockableRigAttackAnimation;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.EnumSet;

/** Shared combat-hole admission and counted profile-attack ownership for rig mobs. */
public abstract class AdvancedEscapeHoleGoal<T extends PathfinderMob & LockableRigAttackAnimation> extends Goal {
    private static final int MAX_TARGET_HEIGHT_DIFFERENCE = 32;
    protected final T mob;
    protected LivingEntity target;
    protected Vec3 exitPosition;
    protected int upAmountBlocks;
    protected boolean finished;
    protected int elapsed;
    private int nextCheck;
    private boolean ownsLock;

    protected AdvancedEscapeHoleGoal(T mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public final boolean canUse() {
        if (this.mob.tickCount < this.nextCheck) return false;
        this.nextCheck = this.mob.tickCount + 20 + this.mob.getRandom().nextInt(11);
        if (!this.canAct() || this.mob.isUsingItem() || this.mob.isInWaterOrBubble() || this.mob.isInLava()
                || !this.mob.onGround() || this.isEscapeAttackLocked() || !this.canStartSpecializedEscape()) return false;
        if (this.hasBlockingEscapeAnimation()) return false;

        if (!HoleEscapePlanner.safeFooting(this.mob, this.mob.blockPosition())) return false;
        LivingEntity currentTarget = this.mob.getTarget();
        if (RecoveryAi.validTarget(this.mob, currentTarget)) {
            double heightDifference = currentTarget.getY() - this.mob.getY();
            if (heightDifference <= 2.0D || heightDifference > MAX_TARGET_HEIGHT_DIFFERENCE) return false;
            if (!RecoveryAi.loadedCorridor(this.mob, currentTarget.position()) || !RecoveryAi.admitPath(this.mob)) return false;
            if (RecoveryAi.reachable(RecoveryAi.targetPath(this.mob, currentTarget.blockPosition()))) return false;
            this.target = currentTarget;
            this.exitPosition = currentTarget.position();
            this.upAmountBlocks = upAmountBlocks(this.mob, currentTarget);
        } else {
            BlockPos exit = HoleEscapePlanner.findPassiveShaftExit(
                    this.mob, this.mob.blockPosition(), MAX_TARGET_HEIGHT_DIFFERENCE, false, (reason, pos) -> {});
            if (exit == null) return false;
            this.target = null;
            this.exitPosition = Vec3.atCenterOf(exit);
            this.upAmountBlocks = Math.max(1, Mth.ceil(exit.getY() - this.mob.getY()));
        }
        return this.hasTraversableVerticalRoute(this.plannedVerticalTravel());
    }

    static int upAmountBlocks(PathfinderMob mob, LivingEntity target) {
        return Math.max(0, Mth.ceil(target.getY() - mob.getY()));
    }

    @Override
    public final void start() {
        this.elapsed = 0;
        this.finished = false;
        this.acquireEscapeAttackLock();
        this.ownsLock = true;
        this.stopEscapeShieldGuard();
        this.stopActiveEscapeProfileAttack();
        this.mob.getNavigation().stop();
        this.startEscape();
    }

    @Override
    public final boolean canContinueToUse() {
        return this.ownsLock && !this.finished && this.elapsed < 800 && this.canAct()
                && this.canContinueSpecializedEscape()
                && (this.target == null || RecoveryAi.validTarget(this.mob, this.target));
    }

    @Override
    public final void tick() {
        this.elapsed++;
        this.mob.getNavigation().stop();
        this.tickEscape();
    }

    @Override
    public final void stop() {
        this.stopEscape();
        this.mob.getNavigation().stop();
        if (this.ownsLock) this.releaseEscapeAttackLock();
        this.ownsLock = false;
        this.finished = true;
        this.target = null;
        this.exitPosition = null;
        this.nextCheck = this.mob.tickCount + 20 + this.mob.getRandom().nextInt(11);
    }

    @Override public final boolean requiresUpdateEveryTick() { return true; }

    protected final boolean ownsLock() { return this.ownsLock; }

    public final boolean shouldForceCancel() {
        return this.ownsLock && (!this.canAct() || !this.canContinueSpecializedEscape()
                || this.target != null && !RecoveryAi.validTarget(this.mob, this.target));
    }

    protected final RigAnimationId prepareRandomExitRoll() {
//            AV_EFM patch with epicfight animations
        RigAnimationId roll = this.chooseEscapeRollAnimation();
        double dx = this.exitPosition.x - this.mob.getX();
        double dz = this.exitPosition.z - this.mob.getZ();
        if (this.isBackwardEscapeRoll(roll)) {
            dx = -dx;
            dz = -dz;
        }
        if (dx * dx + dz * dz > 1.0E-6D) {
            float yaw = (float) (Math.atan2(-dx, dz) * (180.0D / Math.PI));
            this.mob.setYRot(yaw);
            this.mob.yBodyRot = yaw;
            this.mob.yHeadRot = yaw;
        }
        return roll;
    }

    protected final boolean canAct() {
        return this.mob.level() instanceof ServerLevel && this.mob.isAlive() && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying() && !this.mob.isNoAi() && !this.mob.isPassenger()
                && !this.isEscapeRigStunned();
    }

    protected boolean isEscapeAttackLocked() {
//            AV_EFM patch with epicfight animations
        return this.mob.isLocked();
    }

    protected boolean hasBlockingEscapeAnimation() {
//            AV_EFM patch with epicfight animations
        return RigAnimationController.hasActiveAnimation(this.mob)
                && !RigAnimationController.hasActiveProfileAttack(this.mob);
    }

    protected void acquireEscapeAttackLock() {
//            AV_EFM patch with epicfight animations
        this.mob.lock();
    }

    protected void releaseEscapeAttackLock() {
//            AV_EFM patch with epicfight animations
        this.mob.unlock();
    }

    protected void stopEscapeShieldGuard() {
//            AV_EFM patch with epicfight animations
        RigShieldGuardController.stop(this.mob);
    }

    protected void stopActiveEscapeProfileAttack() {
//            AV_EFM patch with epicfight animations
        if (RigAnimationController.hasActiveProfileAttack(this.mob)) {
            RigAnimationController.stop(this.mob, RigAnimationController.getActiveAnimationId(this.mob));
        }
    }

    protected boolean isEscapeRigStunned() {
//            AV_EFM patch with epicfight animations
        return RigStunController.isStunned(this.mob);
    }

    protected RigAnimationId chooseEscapeRollAnimation() {
//            AV_EFM patch with epicfight animations
        return this.mob.getRandom().nextBoolean() ? RigAnimationId.ROLL_FORWARD : RigAnimationId.ROLL_BACKWARD;
    }

    protected boolean isBackwardEscapeRoll(RigAnimationId animation) {
//            AV_EFM patch with epicfight animations
        return animation == RigAnimationId.ROLL_BACKWARD;
    }

    protected int plannedVerticalTravel() { return this.upAmountBlocks; }

    protected double verticalClearanceExtra() { return 0.25D; }

    protected Mob verticalObstructionBreaker() { return this.mob; }

    private boolean hasTraversableVerticalRoute(int blocks) {
        BlockPos base = this.mob.blockPosition();
        for (int y = 0; y <= blocks + 2; y++) {
            BlockPos pos = base.above(y);
            if (!this.mob.level().hasChunkAt(pos) || !this.mob.level().isInWorldBounds(pos)
                    || !this.mob.level().getWorldBorder().isWithinBounds(pos)) return false;
        }
        var route = this.mob.getBoundingBox().expandTowards(0.0D, blocks + this.verticalClearanceExtra(), 0.0D);
        for (BlockPos pos : BlockPos.betweenClosed(BlockPos.containing(route.minX, route.minY, route.minZ),
                BlockPos.containing(route.maxX, route.maxY, route.maxZ))) {
            var state = this.mob.level().getBlockState(pos);
            var shape = state.getCollisionShape(this.mob.level(), pos);
            if (!shape.isEmpty() && Shapes.joinIsNotEmpty(Shapes.create(route),
                    shape.move(pos.getX(), pos.getY(), pos.getZ()), BooleanOp.AND)
                    && !RigEscapeObstructionBreaker.canBreak(this.verticalObstructionBreaker(), pos, state)) return false;
        }
        return true;
    }

    protected abstract boolean canStartSpecializedEscape();
    protected abstract boolean canContinueSpecializedEscape();
    protected abstract void startEscape();
    protected abstract void tickEscape();
    protected abstract void stopEscape();
}
