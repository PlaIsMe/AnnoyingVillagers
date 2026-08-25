package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.FishingRodUser;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.TonyTheFishingRod;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigShieldGuardController;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.RidingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class CombatFishingRodGoal extends Goal {
    private static final double MAX_TARGET_DISTANCE = 32.0D;
    private static final double MAX_TARGET_DISTANCE_SQR = MAX_TARGET_DISTANCE * MAX_TARGET_DISTANCE;
    private static final int MIN_COOLDOWN_TICKS = 60;
    private static final int RANDOM_COOLDOWN_TICKS = 60;
    private static final int MAX_WAIT_TICKS = 55;
    private static final int NEXT_HOOK_MIN_DELAY_TICKS = 30;
    private static final int NEXT_HOOK_RANDOM_DELAY_TICKS = 20;
    private static final int AROUND_SEARCH_RADIUS = 12;
    private static final int START_CHECK_INTERVAL_TICKS = 20;
    private static final int MIN_SESSION_HOOKS_BEFORE_RESTORE = 2;
    private static final int MAX_SESSION_HOOKS = 3;
    private static final double START_CHANCE = 0.15D;
    private static final double RESTORE_CHANCE_PER_EXTRA_HOOK = 0.50D;
    private static final double MAX_RESTORE_CHANCE = 0.75D;
    private static final double CLOSE_ESCAPE_DISTANCE_SQR = 25.0D;
    private static final double LOW_HEALTH_RATIO = 0.45D;
    private static final double LOW_HEALTH_ESCAPE_CHANCE = 0.40D;
    private static final double STICK_CHANCE_MIN = 0.30D;
    private static final double STICK_CHANCE_MAX = 0.50D;
    private static final double STICK_LOSE_CHANCE = 0.35D;

    private final PathfinderMob mob;
    private final FishingRodUser fishingRodUser;
    @Nullable
    private LivingEntity target;
    @Nullable
    private Vec3 hookAnchor;
    private Action action = Action.AROUND;
    private long nextStartCheckTick;
    private long nextHookTick;
    private boolean awaitingResolution;
    private boolean escapeCast;

    public CombatFishingRodGoal(PathfinderMob mob) {
        if (!(mob instanceof FishingRodUser fishingRodUser)) {
            throw new IllegalArgumentException("CombatFishingRodGoal requires a FishingRodUser mob");
        }

        this.mob = mob;
        this.fishingRodUser = fishingRodUser;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!this.canAttempt() || this.awaitingResolution) {
            return false;
        }

        ServerLevel serverLevel = (ServerLevel) this.mob.level();
        long gameTime = serverLevel.getGameTime();
        this.target = this.resolveTarget();
        if (this.target == null) {
            this.restoreSessionIfActive();
            return false;
        }

        if (this.mob.distanceToSqr(this.target) > MAX_TARGET_DISTANCE_SQR) {
            this.restoreSessionIfActive();
            this.target = null;
            return false;
        }

        if (this.fishingRodUser.isCombatFishingRodSessionActive()) {
            if (gameTime < this.nextHookTick || this.tryRestoreBeforeNextHook()) {
                return false;
            }
        } else {
            if (this.fishingRodUser.getCombatFishingRodState().getCooldownTicks() > 0
                    || !this.fishingRodUser.canStartCombatFishingRodSession(this.mob)
                    || gameTime < this.nextStartCheckTick) {
                return false;
            }

            this.nextStartCheckTick = gameTime + START_CHECK_INTERVAL_TICKS;
            if (this.mob.getRandom().nextDouble() > START_CHANCE) {
                return false;
            }
        }

        this.escapeCast = this.shouldUseEscapeCast(this.target);
        this.action = this.escapeCast ? Action.AROUND : this.chooseAction(this.target);
        this.hookAnchor = this.resolveHookAnchor(this.target, this.action, this.escapeCast);
        return this.hookAnchor != null || this.target != null;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        LivingEntity castTarget = this.target;
        Vec3 castAnchor = this.hookAnchor;
        Action castAction = this.action;

        this.fishingRodUser.beginCombatFishingRodSession(this.mob);
        RigShieldGuardController.stop(this.mob);
        this.mob.stopUsingItem();
        RidingUtil.stopNavigation(this.mob);
        this.mob.swing(InteractionHand.OFF_HAND, true);
        this.mob.playSound(SoundEvents.FISHING_BOBBER_THROW, 1.0F, 1.0F);
        RigAnimationController.lockProfileAttacksFor(this.mob, RigAnimationId.POINT_LEFT_HAND_TOWARD);
        RigAnimationController.play(this.mob, RigAnimationId.POINT_LEFT_HAND_TOWARD);

        if (castTarget != null) {
            RidingUtil.lookAtTarget(this.mob, castTarget, 70.0F, 70.0F);
        }

        Vec3 visualTarget = castAnchor;
        if (visualTarget == null && castTarget != null) {
            visualTarget = castTarget.position().add(0.0D, castTarget.getBbHeight() * 0.55D, 0.0D);
        }

        Entity trackedHookTarget = castTarget != null && (this.isTargetPullAction(castAction) || castAnchor == null)
                ? castTarget
                : null;
        FishingHook hook = visualTarget == null
                ? null
                : FishingRodGrappleUtil.spawnNpcCombatFishingHook(this.mob, visualTarget, trackedHookTarget);

        ItemStack stuckItem = ItemStack.EMPTY;
        if (castAction == Action.JESSICA_PULL_TARGET) {
            stuckItem = new ItemStack(AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get());
            FishingRodGrappleUtil.attachNpcCombatFishingHookPayload(hook, this.mob, stuckItem);
        }

        this.awaitingResolution = true;
        this.scheduleResolution(castTarget, castAction, castAnchor, stuckItem, hook, 0);
        this.fishingRodUser.getCombatFishingRodState().incrementUseCount();
        this.clearPreparedCast();
    }

    @Override
    public void stop() {
        this.clearPreparedCast();
    }

    private boolean canAttempt() {
        if (!(this.mob.level() instanceof ServerLevel)) {
            return false;
        }
        if (!this.mob.isAlive()) {
            return false;
        }
        if (this.mob.isRemoved()) {
            return false;
        }
        if (this.mob.isDeadOrDying()) {
            return false;
        }
        if (this.mob.isNoAi() || RigStunController.isStunned(this.mob)) {
            return false;
        }
        if (this.mob.isPassenger()) {
            return false;
        }
        if (RigShieldGuardController.isGuarding(this.mob)) {
            return false;
        }
        if (RigAnimationController.hasActiveProfileAttack(this.mob)) {
            return false;
        }
        if (this.mob instanceof AVNpc avNpc && avNpc.isHealing()) {
            return false;
        }

        return true;
    }

    @Nullable
    private LivingEntity resolveTarget() {
        LivingEntity stickyTarget = this.getStickyTarget();
        if (stickyTarget != null) {
            return stickyTarget;
        }

        LivingEntity currentTarget = this.mob.getTarget();
        if (currentTarget == null
                || !currentTarget.isAlive()
                || currentTarget.isRemoved()
                || currentTarget.isDeadOrDying()) {
            return null;
        }

        return currentTarget;
    }

    private boolean tryRestoreBeforeNextHook() {
        int useCount = Math.max(0, this.fishingRodUser.getCombatFishingRodState().getUseCount());
        if (useCount >= MAX_SESSION_HOOKS) {
            this.fishingRodUser.restoreCombatFishingRodSession(this.mob, true, MIN_COOLDOWN_TICKS, RANDOM_COOLDOWN_TICKS);
            return true;
        }

        if (this.getStickyTarget() != null || useCount < MIN_SESSION_HOOKS_BEFORE_RESTORE) {
            return false;
        }

        int extraHooks = useCount - MIN_SESSION_HOOKS_BEFORE_RESTORE + 1;
        double restoreChance = Math.min(MAX_RESTORE_CHANCE, extraHooks * RESTORE_CHANCE_PER_EXTRA_HOOK);
        if (this.mob.getRandom().nextDouble() > restoreChance) {
            return false;
        }

        this.fishingRodUser.restoreCombatFishingRodSession(this.mob, true, MIN_COOLDOWN_TICKS, RANDOM_COOLDOWN_TICKS);
        return true;
    }

    private void restoreSessionIfActive() {
        if (this.fishingRodUser.isCombatFishingRodSessionActive()) {
            this.fishingRodUser.restoreCombatFishingRodSession(this.mob, true, MIN_COOLDOWN_TICKS, RANDOM_COOLDOWN_TICKS);
        }
    }

    private Action chooseAction(@Nullable LivingEntity target) {
        if (target == null) {
            return Action.AROUND;
        }

        if (this.getStickyTarget() != null) {
            return Action.PULL_TARGET;
        }

        double roll = this.mob.getRandom().nextDouble();
        double distance = this.mob.distanceTo(target);

        if (this.fishingRodUser.canUseJessicaCombatFishingRodHook()) {
            if (roll < 0.50D) return Action.JESSICA_PULL_TARGET;
            if (roll < 0.70D) return Action.PULL_TARGET;
            if (roll < 0.90D) return Action.SELF_TO_TARGET;
            return Action.AROUND;
        }

        Item rodItem = this.fishingRodUser.getCombatFishingRodItem();
        if (rodItem instanceof TonyTheFishingRod) {
            if (roll < 0.30D) return Action.PULL_TARGET;
            if (roll < 0.70D || distance > 8.0D) return Action.SELF_TO_TARGET;
            return Action.AROUND;
        }

        if (distance > 12.0D) {
            return roll < 0.55D ? Action.SELF_TO_TARGET : Action.PULL_TARGET;
        }
        if (distance < 3.0D) {
            return roll < 0.45D ? Action.PULL_TARGET : Action.AROUND;
        }

        if (roll < 0.45D) return Action.PULL_TARGET;
        if (roll < 0.80D) return Action.SELF_TO_TARGET;
        return Action.AROUND;
    }

    private boolean shouldUseEscapeCast(LivingEntity target) {
        if (this.mob.distanceToSqr(target) <= CLOSE_ESCAPE_DISTANCE_SQR) {
            return true;
        }

        if (this.mob instanceof AVNpc avNpc
                && avNpc.getHealth() <= avNpc.getMaxHealth() * LOW_HEALTH_RATIO) {
            return this.mob.getRandom().nextDouble() < LOW_HEALTH_ESCAPE_CHANCE;
        }

        return false;
    }

    @Nullable
    private LivingEntity getStickyTarget() {
        int stickyTargetId = this.fishingRodUser.getCombatFishingRodState().getStickyTargetId();
        if (stickyTargetId <= 0) {
            return null;
        }

        Entity entity = this.mob.level().getEntity(stickyTargetId);
        if (!(entity instanceof LivingEntity livingEntity)
                || !livingEntity.isAlive()
                || livingEntity.isRemoved()
                || livingEntity == this.mob
                || this.mob.isAlliedTo(livingEntity)) {
            this.fishingRodUser.getCombatFishingRodState().setStickyTargetId(0);
            return null;
        }

        return livingEntity;
    }

    private void updateStickyTarget(LivingEntity target, Action action) {
        if (action != Action.PULL_TARGET || !this.fishingRodUser.canUseStickyCombatFishingRodTarget()) {
            return;
        }

        FishingRodUser.State state = this.fishingRodUser.getCombatFishingRodState();
        int stickyTargetId = state.getStickyTargetId();
        if (stickyTargetId == target.getId()) {
            if (this.mob.getRandom().nextDouble() < STICK_LOSE_CHANCE) {
                state.setStickyTargetId(0);
            }
            return;
        }

        double stickChance = STICK_CHANCE_MIN
                + this.mob.getRandom().nextDouble() * (STICK_CHANCE_MAX - STICK_CHANCE_MIN);
        if (this.mob.getRandom().nextDouble() < stickChance) {
            state.setStickyTargetId(target.getId());
        }
    }

    private void scheduleResolution(
            @Nullable LivingEntity castTarget,
            Action castAction,
            @Nullable Vec3 castAnchor,
            ItemStack stuckItem,
            @Nullable FishingHook hook,
            int waitedTicks
    ) {
        new DelayedTask(1) {
            @Override
            public void run() {
                if (!CombatFishingRodGoal.this.isCastOwnerValid()) {
                    FishingRodGrappleUtil.forceNpcCombatFishingHookReturn(hook);
                    CombatFishingRodGoal.this.awaitingResolution = false;
                    return;
                }

                boolean maxWaitReached = waitedTicks >= MAX_WAIT_TICKS;
                if (!FishingRodGrappleUtil.isNpcCombatFishingHookResolved(hook) && !maxWaitReached) {
                    CombatFishingRodGoal.this.scheduleResolution(castTarget, castAction, castAnchor, stuckItem, hook, waitedTicks + 1);
                    return;
                }

                if (maxWaitReached) {
                    FishingRodGrappleUtil.forceNpcCombatFishingHookReturn(hook);
                }

                CombatFishingRodGoal.this.resolveAction(castTarget, castAction, castAnchor, stuckItem);
                CombatFishingRodGoal.this.mob.playSound(SoundEvents.FISHING_BOBBER_RETRIEVE, 1.0F, 1.0F);
                CombatFishingRodGoal.this.finishScheduledCast();
            }
        };
    }

    private boolean isCastOwnerValid() {
        return this.mob.level() instanceof ServerLevel
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && !RigStunController.isStunned(this.mob);
    }

    private void resolveAction(@Nullable LivingEntity castTarget, Action castAction, @Nullable Vec3 castAnchor, ItemStack stuckItem) {
        LivingEntity currentTarget = castTarget != null && castTarget.isAlive() ? castTarget : this.mob.getTarget();
        if (currentTarget != null) {
            RidingUtil.lookAtTarget(this.mob, currentTarget, 70.0F, 70.0F);
        }

        if (this.isTargetPullAction(castAction) && currentTarget != null && currentTarget.isAlive()) {
            this.pullTargetToMob(currentTarget);
            this.updateStickyTarget(currentTarget, castAction);
            if (!stuckItem.isEmpty()) {
                FishingRodGrappleUtil.damageEnemyHitByNpcHookedFishingRodItem(this.mob, currentTarget, stuckItem);
            }
            return;
        }

        Vec3 destination = castAnchor;
        if (destination == null && currentTarget != null) {
            destination = currentTarget.position().add(0.0D, currentTarget.getBbHeight() * 0.45D, 0.0D);
        }
        if (destination != null) {
            this.pullEntityToward(this.mob, destination, 1.25D, 0.25D);
        }
    }

    private void finishScheduledCast() {
        this.awaitingResolution = false;
        if (this.mob.level() instanceof ServerLevel serverLevel) {
            this.nextHookTick = serverLevel.getGameTime()
                    + NEXT_HOOK_MIN_DELAY_TICKS
                    + this.mob.getRandom().nextInt(NEXT_HOOK_RANDOM_DELAY_TICKS + 1);
        }
    }

    @Nullable
    private Vec3 resolveHookAnchor(@Nullable LivingEntity target, Action action, boolean escape) {
        if (action == Action.SELF_TO_TARGET) {
            Vec3 blockBetween = this.findHookBlockBetween(target);
            if (blockBetween != null) {
                return blockBetween;
            }
            return target == null ? null : target.position().add(0.0D, target.getBbHeight() * 0.45D, 0.0D);
        }

        if (action == Action.AROUND) {
            Vec3 aroundAnchor = this.findAroundAnchor(target, escape);
            if (aroundAnchor != null) {
                return aroundAnchor;
            }
            if (escape && target != null) {
                Vec3 away = this.mob.position().subtract(target.position());
                if (away.lengthSqr() > 1.0E-6D) {
                    Vec3 horizontal = new Vec3(away.x, 0.0D, away.z).normalize();
                    return this.mob.position().add(horizontal.scale(6.0D)).add(0.0D, 2.0D, 0.0D);
                }
            }
            return target == null ? null : target.position().add(0.0D, target.getBbHeight() * 0.45D, 0.0D);
        }

        return null;
    }

    @Nullable
    private Vec3 findHookBlockBetween(@Nullable LivingEntity target) {
        if (target == null) {
            return null;
        }

        Level level = this.mob.level();
        Vec3 start = this.mob.getEyePosition();
        Vec3 end = target.getEyePosition();
        Vec3 delta = end.subtract(start);
        for (int i = 2; i <= 14; i++) {
            double t = (double) i / 16.0D;
            BlockPos pos = BlockPos.containing(start.add(delta.scale(t)));
            BlockState state = level.getBlockState(pos);
            if (this.isHookAnchorBlock(level, pos, state) && this.hasHookLine(level, pos)) {
                return Vec3.atCenterOf(pos);
            }
        }

        return null;
    }

    @Nullable
    private Vec3 findAroundAnchor(@Nullable LivingEntity target, boolean escape) {
        Level level = this.mob.level();
        BlockPos origin = this.mob.blockPosition();
        Vec3 targetDirection = Vec3.ZERO;
        if (target != null) {
            Vec3 towardTarget = target.position().subtract(this.mob.position());
            Vec3 horizontal = new Vec3(towardTarget.x, 0.0D, towardTarget.z);
            if (horizontal.lengthSqr() > 1.0E-6D) {
                targetDirection = horizontal.normalize();
            }
        }

        BlockPos bestPos = null;
        double bestScore = -Double.MAX_VALUE;
        int radiusSqr = AROUND_SEARCH_RADIUS * AROUND_SEARCH_RADIUS;
        for (int dy = -2; dy <= 12; dy++) {
            for (int dx = -AROUND_SEARCH_RADIUS; dx <= AROUND_SEARCH_RADIUS; dx++) {
                for (int dz = -AROUND_SEARCH_RADIUS; dz <= AROUND_SEARCH_RADIUS; dz++) {
                    int distSqr = dx * dx + dy * dy + dz * dz;
                    if (distSqr < 9 || distSqr > radiusSqr) {
                        continue;
                    }

                    BlockPos pos = origin.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (!this.isHookAnchorBlock(level, pos, state) || !this.hasHookLine(level, pos)) {
                        continue;
                    }

                    double score = Math.sqrt(distSqr);
                    if (state.is(BlockTags.LEAVES)) {
                        score += 1000.0D;
                    }
                    if (dy > 0) {
                        score += dy * 4.0D;
                    }

                    if (targetDirection.lengthSqr() > 1.0E-6D) {
                        Vec3 toAnchor = new Vec3(dx, 0.0D, dz);
                        if (toAnchor.lengthSqr() > 1.0E-6D) {
                            double dot = toAnchor.normalize().dot(targetDirection);
                            if (escape) {
                                score -= dot * 90.0D;
                            } else {
                                score += dot > 0.75D ? -60.0D : (0.35D - Math.min(dot, 0.35D)) * 35.0D;
                            }
                        }
                    }

                    if (score > bestScore) {
                        bestScore = score;
                        bestPos = pos;
                    }
                }
            }
        }

        return bestPos == null ? null : Vec3.atCenterOf(bestPos);
    }

    private boolean isHookAnchorBlock(Level level, BlockPos pos, BlockState state) {
        return state.is(BlockTags.LEAVES) || !state.getCollisionShape(level, pos).isEmpty();
    }

    private boolean hasHookLine(Level level, BlockPos pos) {
        BlockHitResult hit = level.clip(new ClipContext(
                this.mob.getEyePosition(),
                Vec3.atCenterOf(pos),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this.mob
        ));
        return hit.getType() == HitResult.Type.MISS || hit.getBlockPos().equals(pos);
    }

    private boolean isTargetPullAction(Action action) {
        return action == Action.PULL_TARGET || action == Action.JESSICA_PULL_TARGET;
    }

    private void pullTargetToMob(LivingEntity target) {
        Vec3 destination = this.mob.position().add(0.0D, this.mob.getBbHeight() * 0.45D, 0.0D);
        this.pullEntityToward(target, destination, 1.05D, 0.20D);
    }

    private void pullEntityToward(Entity entity, Vec3 destination, double power, double yBoost) {
        Vec3 center = entity.position().add(0.0D, entity.getBbHeight() * 0.5D, 0.0D);
        Vec3 delta = destination.subtract(center);
        if (delta.lengthSqr() < 1.0E-4D) {
            return;
        }

        Vec3 impulse = delta.normalize().scale(power);
        impulse = new Vec3(impulse.x, Math.max(impulse.y + yBoost, yBoost), impulse.z);
        entity.setDeltaMovement(entity.getDeltaMovement().add(impulse));
        entity.hasImpulse = true;
        entity.hurtMarked = true;
        entity.fallDistance = 0.0F;

        if (entity instanceof Mob pulledMob) {
            RidingUtil.stopNavigation(pulledMob);
        }
    }

    private void clearPreparedCast() {
        this.target = null;
        this.hookAnchor = null;
        this.action = Action.AROUND;
        this.escapeCast = false;
    }

    private enum Action {
        PULL_TARGET,
        SELF_TO_TARGET,
        AROUND,
        JESSICA_PULL_TARGET
    }
}
