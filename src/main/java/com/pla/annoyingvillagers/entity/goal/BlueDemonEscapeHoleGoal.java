package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.BbqEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Sauce rendezvous and collision-aware vertical carry escape for Blue Demon. */
public class BlueDemonEscapeHoleGoal extends AdvancedEscapeHoleGoal<BlueDemonEntity> {
    private static final String ESCAPE_TRANSIENT_TAG = "AVBlueDemonHoleEscapeTransient";
    private static final int RENDEZVOUS_TIMEOUT_TICKS = 100;
    private static final double RENDEZVOUS_DISTANCE_SQR = 0.35D * 0.35D;
    private static final double MAX_LIFT_PER_TICK = 0.35D;
    private BbqEntity carrier;
    private double liftStartY;
    private int liftStartTick;
    private int liftDuration;
    private boolean lifting;
    private boolean previousNoGravity;
    public RigAnimationId exitRoll;
    private int retryAfterTick;
    private int obstructionWaitTicks;
    private final RigEscapeObstructionBreaker obstructionBreaker = new RigEscapeObstructionBreaker();

    public BlueDemonEscapeHoleGoal(BlueDemonEntity mob) {
        super(mob);
    }

    public boolean ownsEscape() {
        return this.ownsLock();
    }

    public void forceCancel() {
        if (this.ownsLock()) {
            this.stop();
            return;
        }
        if (this.mob.getPersistentData().getBoolean(ESCAPE_TRANSIENT_TAG)) this.recoverAfterLoad();
        else this.stopEscapeAnimation();
    }

    public void recoverAfterLoad() {
        if (this.ownsLock() || !this.mob.getPersistentData().getBoolean(ESCAPE_TRANSIENT_TAG)) return;
        this.stopEscapeAnimation();
        this.mob.setNoGravity(false);
        this.mob.setBbqHoleEscapeActive(false);
        this.mob.setDeltaMovement(Vec3.ZERO);
        this.mob.getPersistentData().remove(ESCAPE_TRANSIENT_TAG);
    }

    @Override
    protected boolean canStartSpecializedEscape() {
        this.recoverAfterLoad();
        if (this.mob.tickCount < this.retryAfterTick || !this.mob.canUseBbqHoleEscape()) return false;
        this.carrier = this.mob.findAvailableHoleEscapeSauce();
        return this.carrier != null;
    }

    @Override
    protected boolean canContinueSpecializedEscape() {
        if (this.exitRoll != null) return this.mob.canUseBbqHoleEscape();
        boolean valid = this.mob.canUseBbqHoleEscape() && this.carrier != null && this.carrier.isAlive()
                && this.carrier.getLeader() == this.mob && this.carrier.getHoleCarryLeader() == this.mob;
        if (!valid && this.ownsLock()) this.delayRetry();
        return valid;
    }

    @Override
    protected double verticalClearanceExtra() {
        return this.carrier == null ? 2.0D : this.carrier.getBbHeight() + 0.5D;
    }

    @Override
    protected Mob verticalObstructionBreaker() {
        return this.carrier == null ? this.mob : this.carrier;
    }

    protected int getZiplineAnimationDurationTicks() {
//            AV_EFM patch with epicfight animations
        return RigAnimationSpecs.get(RigAnimationId.BLUE_DEMON_ZIPLINE).durationTicks();
    }

    protected void playHeldZiplineAnimation() {
//            AV_EFM patch with epicfight animations
        RigAnimationController.playHeldPose(this.mob, RigAnimationId.BLUE_DEMON_ZIPLINE);
    }

    protected boolean isZiplineAnimationActive() {
//            AV_EFM patch with epicfight animations
        return RigAnimationController.getActiveAnimationId(this.mob) == RigAnimationId.BLUE_DEMON_ZIPLINE;
    }

    protected int getExitRollAnimationDurationTicks() {
//            AV_EFM patch with epicfight animations
        return RigAnimationSpecs.get(this.exitRoll).durationTicks();
    }

    protected boolean isExitRollAnimationActive() {
//            AV_EFM patch with epicfight animations
        return RigAnimationController.getActiveAnimationId(this.mob) == this.exitRoll;
    }

    protected void playExitRollAnimation() {
//            AV_EFM patch with epicfight animations
        RigAnimationController.play(this.mob, this.exitRoll);
    }

    protected void stopEscapeAnimation() {
//            AV_EFM patch with epicfight animations
        RigAnimationId active = RigAnimationController.getActiveAnimationId(this.mob);
        if (active == RigAnimationId.BLUE_DEMON_ZIPLINE || active == this.exitRoll) {
            RigAnimationController.stop(this.mob, active);
        }
    }

    @Override
    protected void startEscape() {
        this.mob.getPersistentData().putBoolean(ESCAPE_TRANSIENT_TAG, true);
        this.previousNoGravity = this.mob.isNoGravity();
        this.lifting = false;
        this.exitRoll = null;
        this.obstructionWaitTicks = 0;
        this.mob.setBbqHoleEscapeActive(true);
        if (this.carrier == null || !this.carrier.beginHoleCarry(this.mob)) {
            this.delayRetry();
            this.finished = true;
        }
    }

    @Override
    protected void tickEscape() {
        if (this.exitRoll != null) {
            if (this.mob.tickCount - this.liftStartTick > this.getExitRollAnimationDurationTicks()
                    && !this.isExitRollAnimationActive()) this.finished = true;
            return;
        }
        if (this.carrier == null || !this.carrier.isAlive() || this.carrier.isRemoved()
                || this.carrier.getHoleCarryLeader() != this.mob) {
            this.delayRetry();
            this.stop();
            return;
        }

        if (!this.lifting) {
            if (this.elapsed >= RENDEZVOUS_TIMEOUT_TICKS) {
                this.delayRetry();
                this.stop();
                return;
            }
            Vec3 rendezvous = new Vec3(this.mob.getX(), this.mob.getBoundingBox().maxY + 0.3D, this.mob.getZ());
            this.mob.getLookControl().setLookAt(this.carrier, 60.0F, 60.0F);
            if (this.carrier.distanceToSqr(rendezvous) > RENDEZVOUS_DISTANCE_SQR) return;
            this.lifting = true;
            this.liftStartY = this.mob.getY();
            this.liftStartTick = this.mob.tickCount;
            this.liftDuration = Math.max(this.getZiplineAnimationDurationTicks(),
                    (int) Math.ceil(this.upAmountBlocks / MAX_LIFT_PER_TICK));
            this.previousNoGravity = this.mob.isNoGravity();
            this.mob.setNoGravity(true);
            this.mob.setDeltaMovement(Vec3.ZERO);
            this.carrier.setHoleCarryLifting(true);
            this.playHeldZiplineAnimation();
            return;
        }

        if (!this.isZiplineAnimationActive()) {
            this.finished = true;
            return;
        }

        int liftAge = Math.max(0, this.mob.tickCount - this.liftStartTick);
        double desiredY = this.liftStartY + this.upAmountBlocks * Math.min(1.0D, liftAge / (double) this.liftDuration);
        double dy = desiredY - this.mob.getY();
        if (dy > 1.0E-5D) {
            AABB sweptPair = this.mob.getBoundingBox().expandTowards(0.0D, dy, 0.0D)
                    .minmax(this.carrier.getBoundingBox().expandTowards(0.0D, dy, 0.0D));
            if (this.obstructionBreaker.clear(this.carrier, sweptPair)) {
                if (++this.obstructionWaitTicks >= RENDEZVOUS_TIMEOUT_TICKS) {
                    this.delayRetry();
                    this.stop();
                    return;
                }
                this.liftStartTick++;
                this.mob.setDeltaMovement(Vec3.ZERO);
                this.carrier.setDeltaMovement(Vec3.ZERO);
                this.carrier.flapForHoleCarry();
                return;
            }
            this.obstructionWaitTicks = 0;
        }
        if (dy > 1.0E-5D) this.mob.move(MoverType.SELF, new Vec3(0.0D, dy, 0.0D));
        this.mob.setDeltaMovement(Vec3.ZERO);
        this.mob.fallDistance = 0.0F;

        Vec3 carriedAt = new Vec3(this.mob.getX(), this.mob.getBoundingBox().maxY + 0.3D, this.mob.getZ());
        this.carrier.move(MoverType.SELF, carriedAt.subtract(this.carrier.position()));
        this.carrier.setDeltaMovement(Vec3.ZERO);
        this.carrier.flapForHoleCarry();
        if (this.carrier.distanceToSqr(carriedAt) > 0.12D * 0.12D) {
            this.finished = true;
            return;
        }

        if (liftAge <= this.liftDuration) return;
        if (this.mob.getY() < this.liftStartY + this.upAmountBlocks - 0.02D) {
            this.finished = true;
            return;
        }

        this.lifting = false;
        this.mob.setNoGravity(this.previousNoGravity);
        this.carrier.endHoleCarry(true);
        this.exitRoll = this.prepareRandomExitRoll();
        this.liftStartTick = this.mob.tickCount;
        this.playExitRollAnimation();
    }

    @Override
    protected void stopEscape() {
        if (!this.ownsLock()) return;
        if (this.carrier != null) this.obstructionBreaker.reset(this.carrier);
        this.stopEscapeAnimation();
        if (this.lifting) this.mob.setNoGravity(this.previousNoGravity);
        if (this.carrier != null && this.carrier.getHoleCarryLeader() == this.mob) {
            this.carrier.endHoleCarry(true);
        }
        this.mob.setBbqHoleEscapeActive(false);
        this.mob.fallDistance = 0.0F;
        this.carrier = null;
        this.exitRoll = null;
        this.lifting = false;
        this.liftDuration = 0;
        this.obstructionWaitTicks = 0;
        this.mob.getPersistentData().remove(ESCAPE_TRANSIENT_TAG);
    }

    private void delayRetry() {
        this.retryAfterTick = Math.max(this.retryAfterTick, this.mob.tickCount + 100);
    }
}
