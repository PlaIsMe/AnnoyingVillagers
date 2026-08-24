package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RigStunController {
    private static final String NBT_STUN_ACTIVE = "AVRigStunActive";
    private static final String NBT_STUN_ORIGINAL_NO_AI = "AVRigStunOriginalNoAi";
    private static final Map<UUID, StunState> STUNNED = new HashMap<>();

    private RigStunController() {}

    public static boolean supports(Mob mob) {
        return mob instanceof RigStunnableEntity;
    }

    public static boolean isStunned(LivingEntity entity) {
        return entity instanceof Mob mob && STUNNED.containsKey(mob.getUUID());
    }

    public static boolean isKnockdown(LivingEntity entity) {
        if (!(entity instanceof Mob mob)) return false;
        StunState state = STUNNED.get(mob.getUUID());
        return state != null && state.knockdown && !state.recovering;
    }

    public static RigAnimationId currentAnimation(Mob mob) {
        StunState state = STUNNED.get(mob.getUUID());
        return state == null ? null : state.animationId;
    }

    public static boolean applyStun(Mob mob) {
        if (!canStun(mob)) return false;
        StunState previous = STUNNED.get(mob.getUUID());
        if (previous != null && previous.knockdown && !previous.recovering) return false;

        int chainStep = previous == null || previous.recovering ? 1 : previous.chainStep + 1;
        RigAnimationId lastSideHit = previous == null ? null : previous.lastSideHit;
        RigAnimationId animationId;
        boolean knockdown = false;

        if (chainStep == 1) {
            animationId = RigAnimationId.HIT_BACKWARD;
        } else if (chainStep == 2) {
            animationId = mob.getRandom().nextBoolean() ? RigAnimationId.HIT_LEFT : RigAnimationId.HIT_RIGHT;
            lastSideHit = animationId;
        } else if (chainStep == 3) {
            if (lastSideHit == RigAnimationId.HIT_LEFT) animationId = RigAnimationId.HIT_RIGHT;
            else if (lastSideHit == RigAnimationId.HIT_RIGHT) animationId = RigAnimationId.HIT_LEFT;
            else animationId = mob.getRandom().nextBoolean() ? RigAnimationId.HIT_LEFT : RigAnimationId.HIT_RIGHT;
            lastSideHit = animationId;
        } else {
            animationId = randomKnockdown(mob);
            knockdown = true;
        }

        return applyInternal(mob, animationId, chainStep, lastSideHit, knockdown, false);
    }

    public static boolean applyStun(Mob mob, RigAnimationId animationId) {
        if (animationId == RigAnimationId.STUN_BACK) return applyStunBack(mob);
        if (isKnockdownAnimation(animationId)) return applyKnockdown(mob, animationId);
        if (isHitAnimation(animationId)) return applyHitAnimation(mob, animationId);
        return false;
    }

    public static boolean applyStunBack(Mob mob) {
        return applyStun(mob);
    }

    public static boolean applyHitAnimation(Mob mob, RigAnimationId animationId) {
        if (!isHitAnimation(animationId) || !canStun(mob)) return false;
        StunState previous = STUNNED.get(mob.getUUID());
        if (previous != null && previous.knockdown && !previous.recovering) return false;
        int chainStep = previous == null || previous.recovering ? 1 : previous.chainStep + 1;
        RigAnimationId lastSideHit = animationId == RigAnimationId.HIT_LEFT || animationId == RigAnimationId.HIT_RIGHT ? animationId : previous == null ? null : previous.lastSideHit;
        return applyInternal(mob, animationId, chainStep, lastSideHit, false, false);
    }

    public static boolean applyKnockdown(Mob mob) {
        return canStun(mob) && applyInternal(mob, randomKnockdown(mob), 4, null, true, false);
    }

    public static boolean applyKnockdown(Mob mob, RigAnimationId animationId) {
        return isKnockdownAnimation(animationId) && canStun(mob) && applyInternal(mob, animationId, 4, null, true, false);
    }

    public static boolean applySuperKnockback(Mob mob) {
        return applyStun(mob, RigAnimationId.SUPER_KNOCK_BACK);
    }

    public static boolean applyLegendarySwordKnockdown(Mob mob) {
        return applyStun(mob, RigAnimationId.LEGENDARY_SWORD_KNOCKDOWN);
    }

    public static void clear(Mob mob) {
        StunState state = STUNNED.remove(mob.getUUID());
        if (state != null) restoreMob(mob, state.originalNoAi);
        else restoreStaleState(mob);
    }

    public static void clearAll() {
        for (StunState state : STUNNED.values()) restoreMob(state.mob, state.originalNoAi);
        STUNNED.clear();
    }

    public static void restoreStaleState(Mob mob) {
        if (mob == null || mob.level().isClientSide) return;
        CompoundTag data = mob.getPersistentData();
        if (!data.getBoolean(NBT_STUN_ACTIVE)) return;
        STUNNED.remove(mob.getUUID());
        boolean originalNoAi = data.getBoolean(NBT_STUN_ORIGINAL_NO_AI);
        clearPersistentStunData(mob);
        mob.setNoAi(originalNoAi);
    }

    private static boolean applyInternal(Mob mob, RigAnimationId animationId, int chainStep, RigAnimationId lastSideHit, boolean knockdown, boolean recovering) {
        StunState previous = STUNNED.get(mob.getUUID());
        boolean originalNoAi = previous == null ? mob.isNoAi() : previous.originalNoAi;
        StunState state = new StunState(mob, animationId, chainStep, lastSideHit, knockdown, recovering, originalNoAi);
        STUNNED.put(mob.getUUID(), state);

        markPersistentStun(mob, originalNoAi);
        lockMob(mob);
        RigAnimationController.playStunAnimation(mob, animationId);
        int durationTicks = RigAnimationSpecs.get(animationId).durationTicks();

        new DelayedTask(durationTicks) {
            @Override
            public void run() {
                if (STUNNED.remove(mob.getUUID(), state)) restoreMob(mob, state.originalNoAi);
            }
        };

        if (knockdown && !recovering) scheduleEscapeCheck(mob, state, 5 + mob.getRandom().nextInt(5));
        return true;
    }

    private static void scheduleEscapeCheck(Mob mob, StunState state, int delayTicks) {
        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (STUNNED.get(mob.getUUID()) != state || !mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) return;
                if (canUseStunEscape(mob)) {
                    beginWakeup(mob, state);
                    return;
                }
                scheduleEscapeCheck(mob, state, 5);
            }
        };
    }

    private static void beginWakeup(Mob mob, StunState state) {
        if (STUNNED.get(mob.getUUID()) != state) return;
        if (mob instanceof RigStunEscapeEntity escapeEntity) escapeEntity.setStunEscapeCooldown(0);
        RigAnimationId wakeup = mob.getRandom().nextBoolean() ? RigAnimationId.KNOCKDOWN_WAKEUP_LEFT : RigAnimationId.KNOCKDOWN_WAKEUP_RIGHT;
        applyInternal(mob, wakeup, state.chainStep, state.lastSideHit, false, true);
    }

    private static boolean canUseStunEscape(Mob mob) {
        return mob instanceof RigStunEscapeEntity escapeEntity && escapeEntity.getStunEscapeCooldown() > 0;
    }

    private static boolean canStun(Mob mob) {
        return mob != null && supports(mob) && !mob.level().isClientSide && mob.isAlive() && !mob.isRemoved() && !mob.isDeadOrDying();
    }

    private static void lockMob(Mob mob) {
        mob.stopUsingItem();
        mob.getNavigation().stop();
        mob.xxa = 0.0F;
        mob.yya = 0.0F;
        mob.zza = 0.0F;
        mob.setDeltaMovement(Vec3.ZERO);
        mob.hasImpulse = true;
        mob.setAggressive(false);
        mob.setNoAi(true);
    }

    private static void markPersistentStun(Mob mob, boolean originalNoAi) {
        CompoundTag data = mob.getPersistentData();
        data.putBoolean(NBT_STUN_ACTIVE, true);
        data.putBoolean(NBT_STUN_ORIGINAL_NO_AI, originalNoAi);
    }

    private static void restoreMob(Mob mob, boolean originalNoAi) {
        clearPersistentStunData(mob);
        if (!mob.isRemoved()) mob.setNoAi(originalNoAi);
    }

    private static void clearPersistentStunData(Mob mob) {
        CompoundTag data = mob.getPersistentData();
        data.remove(NBT_STUN_ACTIVE);
        data.remove(NBT_STUN_ORIGINAL_NO_AI);
    }

    private static boolean isHitAnimation(RigAnimationId animationId) {
        return animationId == RigAnimationId.HIT_BACKWARD || animationId == RigAnimationId.HIT_LEFT || animationId == RigAnimationId.HIT_RIGHT || animationId == RigAnimationId.STUN_BACK;
    }

    private static boolean isKnockdownAnimation(RigAnimationId animationId) {
        return animationId == RigAnimationId.KNOCKDOWN_FORWARD || animationId == RigAnimationId.KNOCKDOWN_BACKWARD || animationId == RigAnimationId.KNOCKDOWN_LEFT || animationId == RigAnimationId.KNOCKDOWN_RIGHT || animationId == RigAnimationId.SUPER_KNOCK_BACK || animationId == RigAnimationId.LEGENDARY_SWORD_KNOCKDOWN;
    }

    private static RigAnimationId randomKnockdown(Mob mob) {
        return switch (mob.getRandom().nextInt(4)) {
            case 0 -> RigAnimationId.KNOCKDOWN_LEFT;
            case 1 -> RigAnimationId.KNOCKDOWN_RIGHT;
            case 2 -> RigAnimationId.KNOCKDOWN_BACKWARD;
            default -> RigAnimationId.KNOCKDOWN_FORWARD;
        };
    }

    private static final class StunState {
        private final Mob mob;
        private final RigAnimationId animationId;
        private final int chainStep;
        private final RigAnimationId lastSideHit;
        private final boolean knockdown;
        private final boolean recovering;
        private final boolean originalNoAi;

        private StunState(Mob mob, RigAnimationId animationId, int chainStep, RigAnimationId lastSideHit, boolean knockdown, boolean recovering, boolean originalNoAi) {
            this.mob = mob;
            this.animationId = animationId;
            this.chainStep = chainStep;
            this.lastSideHit = lastSideHit;
            this.knockdown = knockdown;
            this.recovering = recovering;
            this.originalNoAi = originalNoAi;
        }
    }
}
