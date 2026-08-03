package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RigShieldGuardController {
    private static final int SHIELD_RAISE_TICKS = 5;
    private static final Map<UUID, ActiveShieldGuard> ACTIVE_GUARDS = new HashMap<>();

    private RigShieldGuardController() {
    }

    public static void start(Mob mob, int durationTicks, int hitBudget, @Nullable LivingEntity target) {
        if (mob.level().isClientSide || durationTicks <= 0 || !hasOffhandShield(mob)) {
            return;
        }

        stop(mob);
        ActiveShieldGuard state = new ActiveShieldGuard(mob.tickCount, durationTicks, Math.max(1, hitBudget));
        ACTIVE_GUARDS.put(mob.getUUID(), state);
        ensureUsingShield(mob);
        playGuardAnimation(mob, durationTicks, target);

        new DelayedTask(durationTicks + 1) {
            @Override
            public void run() {
                if (ACTIVE_GUARDS.get(mob.getUUID()) == state) {
                    stop(mob);
                }
            }
        };
    }

    public static boolean isGuarding(Mob mob) {
        return getActiveGuard(mob) != null;
    }

    public static boolean canBlock(Mob mob) {
        ActiveShieldGuard state = getActiveGuard(mob);
        return state != null
                && mob.isUsingItem()
                && mob.getUsedItemHand() == InteractionHand.OFF_HAND
                && mob.tickCount - state.startTick >= SHIELD_RAISE_TICKS;
    }

    public static boolean handleBlockedHit(Mob mob, float blockedAmount, @Nullable Entity attacker) {
        ActiveShieldGuard state = getActiveGuard(mob);
        if (state == null) {
            return false;
        }

        LivingEntity animationTarget = attacker instanceof LivingEntity living ? living : mob.getTarget();
        if (attacker != null) {
            mob.getLookControl().setLookAt(attacker, 70.0F, 70.0F);
            mob.lookAt(attacker, 70.0F, 70.0F);
        }

        RigAnimationSpec hitSpec = RigAnimationSpecs.get(RigAnimationId.HIT_SHIELD_OFFHAND);
        RigAnimationController.play(mob, hitSpec, animationTarget);
        mob.setAggressive(false);
        damageShield(mob, blockedAmount);
        state.remainingHitBudget--;

        if (state.remainingHitBudget <= 0 || !hasOffhandShield(mob)) {
            stop(mob);
            return true;
        }

        new DelayedTask(hitSpec.durationTicks()) {
            @Override
            public void run() {
                ActiveShieldGuard current = getActiveGuard(mob);
                if (current != state || !mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) {
                    return;
                }

                int remainingTicks = current.remainingTicks(mob);
                if (remainingTicks > 0 && hasOffhandShield(mob)) {
                    ensureUsingShield(mob);
                    playGuardAnimation(mob, remainingTicks, mob.getTarget());
                }
            }
        };

        return true;
    }

    public static void stop(Mob mob) {
        ACTIVE_GUARDS.remove(mob.getUUID());
        if (mob.isUsingItem() && mob.getUsedItemHand() == InteractionHand.OFF_HAND) {
            mob.stopUsingItem();
        }
    }

    public static void clear() {
        ACTIVE_GUARDS.clear();
    }

    public static boolean hasOffhandShield(Mob mob) {
        ItemStack offhand = mob.getOffhandItem();
        return !offhand.isEmpty() && offhand.getItem() instanceof ShieldItem;
    }

    public static void ensureUsingShield(Mob mob) {
        if (!hasOffhandShield(mob)) {
            return;
        }
        if (!mob.isUsingItem() || mob.getUsedItemHand() != InteractionHand.OFF_HAND) {
            mob.startUsingItem(InteractionHand.OFF_HAND);
        }
    }

    @Nullable
    private static ActiveShieldGuard getActiveGuard(Mob mob) {
        ActiveShieldGuard state = ACTIVE_GUARDS.get(mob.getUUID());
        if (state == null) {
            return null;
        }

        if (state.remainingTicks(mob) <= 0 || !hasOffhandShield(mob)) {
            stop(mob);
            return null;
        }

        return state;
    }

    private static void playGuardAnimation(Mob mob, int durationTicks, @Nullable LivingEntity target) {
        RigAnimationController.play(
                mob,
                RigAnimationSpec.nonDamaging(RigAnimationId.SHIELD_OFFHAND, durationTicks, RigAnimationPlaybackType.UPPER_BODY),
                target
        );
        mob.setAggressive(false);
    }

    private static void damageShield(Mob mob, float blockedAmount) {
        ItemStack shield = mob.getOffhandItem();
        if (shield.isEmpty() || !(shield.getItem() instanceof ShieldItem) || blockedAmount < 3.0F) {
            return;
        }

        shield.hurtAndBreak(1 + Mth.floor(blockedAmount), mob, entity -> entity.broadcastBreakEvent(InteractionHand.OFF_HAND));
    }

    private static final class ActiveShieldGuard {
        private final int startTick;
        private final int durationTicks;
        private int remainingHitBudget;

        private ActiveShieldGuard(int startTick, int durationTicks, int remainingHitBudget) {
            this.startTick = startTick;
            this.durationTicks = durationTicks;
            this.remainingHitBudget = remainingHitBudget;
        }

        private int remainingTicks(Mob mob) {
            return this.durationTicks - Math.max(0, mob.tickCount - this.startTick);
        }
    }
}
