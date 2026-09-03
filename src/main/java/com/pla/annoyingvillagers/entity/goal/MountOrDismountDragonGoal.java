package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MountOrDismountDragonGoal extends Goal {
    private static final int MIN_COOLDOWN_TICKS = 20 * 60;
    private static final int RANDOM_COOLDOWN_TICKS = 20 * 60 * 2 + 1;
    private static final int RETRY_COOLDOWN_TICKS = 20;

    private final ReaperHerobrineEntity reaper;

    /*
     * Absolute mob tick deadline instead of decrementing inside canUse().
     *
     * GoalSelector does not promise canUse() is evaluated every game tick, so a
     * counter decremented there can take far longer than its nominal duration.
     *
     * -1 means the state-2 cycle has not started yet. The first time Reaper is in
     * state 2, the action is due immediately.
     */
    private int nextActionTick = -1;

    public MountOrDismountDragonGoal(ReaperHerobrineEntity reaper) {
        this.reaper = reaper;

        /*
         * Intentionally no MOVE/LOOK flags. This goal is a combat-side action and
         * must still be selectable while the normal melee goal is running.
         */
    }

    @Override
    public boolean canUse() {
        if (!this.reaper.canRideSummonedDragon()) {
            // Outside full second form the mount cycle does not exist.
            this.nextActionTick = -1;
            return false;
        }

        // First state-2 attempt is immediate.
        if (this.nextActionTick < 0) {
            this.nextActionTick = this.reaper.tickCount;
        }

        if (this.reaper.tickCount < this.nextActionTick) {
            return false;
        }

        if (!isReaperUsable()) {
            return false;
        }

        LivingEntity target = this.reaper.getTarget();
        if (!isValidTarget(target)) {
            return false;
        }

        // Never interrupt the actual summon process or a stun.
        if (this.reaper.isDragonSummonPending() || RigStunController.isStunned(this.reaper)) {
            return false;
        }

        /*
         * Once the deadline has expired, keep returning true as soon as a mount
         * target is available. Do not roll another cooldown just because a dragon
         * reference was temporarily not loaded.
         */
        return this.reaper.isPassenger() || getMountableDragon() != null;
    }

    @Override
    public void start() {
        this.reaper.getNavigation().stop();
        this.reaper.setAggressive(false);

        // Dismount remains immediate.
        if (this.reaper.isPassenger()) {
            this.reaper.stopRiding();
            scheduleNextCycle();
            return;
        }

        HerobrineDragonEntity dragon = getMountableDragon();
        if (dragon == null) {
            scheduleRetry();
            return;
        }

        /*
         * The deadline has expired, so the mount action owns this transition.
         * Stop the current non-stun rig animation and immediately play the call.
         *
         * REAPER_HEROBRINE_EXTRA_ULT is not part of the normal combat profile,
         * so the normal profile lock applied below does not block this animation.
         */
        RigAnimationId activeAnimation = RigAnimationController.getActiveAnimationId(this.reaper);
        if (activeAnimation != null) {
            RigAnimationController.stop(this.reaper, activeAnimation);
        }

        RigAnimationController.play(
                this.reaper,
                RigAnimationSpecs.get(RigAnimationId.REAPER_HEROBRINE_EXTRA_ULT),
                this.reaper.getTarget()
        );

        if (RigAnimationController.getActiveAnimationId(this.reaper)
                != RigAnimationId.REAPER_HEROBRINE_EXTRA_ULT) {
            scheduleRetry();
            return;
        }

        // Block normal melee/profile attacks for the whole dragon-call animation.
        RigAnimationController.lockProfileAttacksFor(
                this.reaper,
                RigAnimationId.REAPER_HEROBRINE_EXTRA_ULT
        );

        /*
         * A stale recallActive flag must not make a live dragon permanently
         * unavailable. recallAndLand(true) refreshes the landing position and
         * auto-mount request.
         */
        dragon.recallAndLand(true);
        scheduleNextCycle();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private void scheduleNextCycle() {
        this.nextActionTick = this.reaper.tickCount + nextCooldown();
    }

    private void scheduleRetry() {
        this.nextActionTick = this.reaper.tickCount + RETRY_COOLDOWN_TICKS;
    }

    private boolean isReaperUsable() {
        return !this.reaper.level().isClientSide
                && this.reaper.isAlive()
                && !this.reaper.isRemoved()
                && !this.reaper.isDeadOrDying()
                && !this.reaper.isNoAi();
    }

    @Nullable
    private HerobrineDragonEntity getMountableDragon() {
        HerobrineDragonEntity thunder = resolveDragon(
                this.reaper.getThunderHerobrineDragon(),
                this.reaper.getThunderHerobrineDragonUUID()
        );
        if (isAvailable(thunder)) return thunder;

        HerobrineDragonEntity meteorite = resolveDragon(
                this.reaper.getMeteoriteHerobrineDragon(),
                this.reaper.getMeteoriteHerobrineDragonUUID()
        );
        if (isAvailable(meteorite)) return meteorite;

        return null;
    }

    @Nullable
    private HerobrineDragonEntity resolveDragon(@Nullable HerobrineDragonEntity direct, @Nullable UUID uuid) {
        if (isAvailable(direct)) {
            return direct;
        }

        if (uuid == null || !(this.reaper.level() instanceof ServerLevel serverLevel)) {
            return null;
        }

        Entity entity = serverLevel.getEntity(uuid);
        return entity instanceof HerobrineDragonEntity dragon ? dragon : null;
    }

    private static boolean isAvailable(@Nullable HerobrineDragonEntity dragon) {
        /*
         * Intentionally do not reject isRecallActive(). If a previous recall was
         * left active, calling recallAndLand(true) again is the recovery path.
         */
        return dragon != null
                && dragon.isAlive()
                && !dragon.isRemoved();
    }

    private static boolean isValidTarget(@Nullable LivingEntity target) {
        return target != null
                && target.isAlive()
                && !target.isRemoved()
                && !target.isDeadOrDying();
    }

    private int nextCooldown() {
        return MIN_COOLDOWN_TICKS
                + this.reaper.getRandom().nextInt(RANDOM_COOLDOWN_TICKS);
    }
}
