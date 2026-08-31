package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.entity.AegisHerobrineEntity;
import com.pla.annoyingvillagers.item.EnderAegisItem;
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
        GuardMode guardMode = resolveGuardMode(mob);
        if (mob.level().isClientSide || durationTicks <= 0 || guardMode == null) {
            return;
        }

        stop(mob);
        ActiveShieldGuard state = new ActiveShieldGuard(
                mob.tickCount,
                durationTicks,
                Math.max(1, hitBudget),
                guardMode
        );
        ACTIVE_GUARDS.put(mob.getUUID(), state);
        ensureUsingShield(mob);
        playGuardAnimation(mob, state, durationTicks, target);

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
        if (state == null || !isGuardItemAvailable(mob, state.guardMode)) {
            return false;
        }

        if (state.guardMode.usesVanillaItemUse
                && (!mob.isUsingItem() || mob.getUsedItemHand() != state.guardMode.hand)) {
            return false;
        }

        return mob.tickCount - state.startTick >= SHIELD_RAISE_TICKS;
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

        mob.setAggressive(false);
        damageShield(mob, blockedAmount, state.guardMode);

        if (state.guardMode == GuardMode.AEGIS_MAINHAND
                && mob instanceof AegisHerobrineEntity aegisHerobrineEntity) {
            // State 1 spends one limited second-form action per successful block;
            // state 2 deliberately shoots on every successful block without decrementing.
            aegisHerobrineEntity.fireSecondFormShieldShot();
        }

        state.remainingHitBudget--;

        if (state.remainingHitBudget <= 0 || !isGuardItemAvailable(mob, state.guardMode)) {
            stop(mob);
            return true;
        }

        int remainingTicks = state.remainingTicks(mob);
        if (remainingTicks <= 0) {
            stop(mob);
            return true;
        }

        // AegisHerobrine has its own authored looping guard. Do not replace it with the
        // generic main-hand shield hit animation; restart/keep the Aegis guard instead.
        if (state.guardMode == GuardMode.AEGIS_MAINHAND) {
            playGuardAnimation(mob, state, remainingTicks, animationTarget);
            return true;
        }

        RigAnimationSpec hitSpec = RigAnimationSpecs.get(state.guardMode.blockAnimationId);
        RigAnimationController.play(mob, hitSpec, animationTarget);

        new DelayedTask(hitSpec.durationTicks()) {
            @Override
            public void run() {
                ActiveShieldGuard current = getActiveGuard(mob);
                if (current != state || !mob.isAlive() || mob.isRemoved() || mob.isDeadOrDying()) {
                    return;
                }

                int currentRemainingTicks = current.remainingTicks(mob);
                if (currentRemainingTicks > 0 && isGuardItemAvailable(mob, current.guardMode)) {
                    ensureUsingShield(mob);
                    playGuardAnimation(mob, current, currentRemainingTicks, mob.getTarget());
                }
            }
        };

        return true;
    }

    public static void stop(Mob mob) {
        ActiveShieldGuard state = ACTIVE_GUARDS.remove(mob.getUUID());
        if (state != null
                && state.guardMode.usesVanillaItemUse
                && mob.isUsingItem()
                && mob.getUsedItemHand() == state.guardMode.hand) {
            mob.stopUsingItem();
        }
    }

    public static void clear() {
        ACTIVE_GUARDS.clear();
    }

    public static boolean hasGuardItem(Mob mob) {
        return resolveGuardMode(mob) != null;
    }

    public static boolean hasOffhandShield(Mob mob) {
        return isShield(mob.getOffhandItem());
    }

    public static boolean hasMainhandShield(Mob mob) {
        return isShield(mob.getMainHandItem());
    }

    public static boolean hasAegisMainhand(Mob mob) {
        ItemStack mainhand = mob.getMainHandItem();
        return mob instanceof AegisHerobrineEntity
                && !mainhand.isEmpty()
                && mainhand.getItem() instanceof EnderAegisItem;
    }

    public static void ensureUsingShield(Mob mob) {
        ActiveShieldGuard state = getActiveGuard(mob);
        if (state == null || !state.guardMode.usesVanillaItemUse) {
            return;
        }
        if (!mob.isUsingItem() || mob.getUsedItemHand() != state.guardMode.hand) {
            mob.startUsingItem(state.guardMode.hand);
        }
    }

    @Nullable
    private static ActiveShieldGuard getActiveGuard(Mob mob) {
        ActiveShieldGuard state = ACTIVE_GUARDS.get(mob.getUUID());
        if (state == null) {
            return null;
        }

        if (state.remainingTicks(mob) <= 0 || !isGuardItemAvailable(mob, state.guardMode)) {
            stop(mob);
            return null;
        }

        return state;
    }

    @Nullable
    private static GuardMode resolveGuardMode(Mob mob) {
        // AegisHerobrine's Ender Aegis is a SwordItem, not a ShieldItem, so it must be
        // recognized explicitly. Prefer its custom main-hand guard even if an offhand
        // shield is present so the authored AEGIS_HEROBRINE_GUARD animation is used.
        if (hasAegisMainhand(mob)) {
            return GuardMode.AEGIS_MAINHAND;
        }
        if (hasOffhandShield(mob)) {
            return GuardMode.OFFHAND_SHIELD;
        }
        if (hasMainhandShield(mob)) {
            return GuardMode.MAINHAND_SHIELD;
        }
        return null;
    }

    private static boolean isGuardItemAvailable(Mob mob, GuardMode guardMode) {
        return switch (guardMode) {
            case OFFHAND_SHIELD -> hasOffhandShield(mob);
            case MAINHAND_SHIELD -> hasMainhandShield(mob);
            case AEGIS_MAINHAND -> hasAegisMainhand(mob);
        };
    }

    private static boolean isShield(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ShieldItem;
    }

    private static void playGuardAnimation(Mob mob, ActiveShieldGuard state, int durationTicks, @Nullable LivingEntity target) {
        RigAnimationSpec baseSpec = RigAnimationSpecs.get(state.guardMode.guardAnimationId);
        RigAnimationController.play(
                mob,
                RigAnimationSpec.nonDamaging(
                        state.guardMode.guardAnimationId,
                        durationTicks,
                        baseSpec.playbackType()
                ),
                target
        );
        mob.setAggressive(false);
    }

    private static void damageShield(Mob mob, float blockedAmount, GuardMode guardMode) {
        if (!guardMode.usesVanillaItemUse || blockedAmount < 3.0F) {
            return;
        }

        ItemStack shield = guardMode.hand == InteractionHand.MAIN_HAND
                ? mob.getMainHandItem()
                : mob.getOffhandItem();
        if (!isShield(shield)) {
            return;
        }

        shield.hurtAndBreak(
                1 + Mth.floor(blockedAmount),
                mob,
                entity -> entity.broadcastBreakEvent(guardMode.hand)
        );
    }

    private enum GuardMode {
        OFFHAND_SHIELD(
                InteractionHand.OFF_HAND,
                RigAnimationId.SHIELD_OFFHAND,
                RigAnimationId.BLOCK_SHIELD_OFFHAND,
                true
        ),
        MAINHAND_SHIELD(
                InteractionHand.MAIN_HAND,
                RigAnimationId.SHIELD_MAINHAND,
                RigAnimationId.BLOCK_SHIELD_MAINHAND,
                true
        ),
        AEGIS_MAINHAND(
                InteractionHand.MAIN_HAND,
                RigAnimationId.AEGIS_HEROBRINE_GUARD,
                null,
                false
        );

        private final InteractionHand hand;
        private final RigAnimationId guardAnimationId;
        @Nullable
        private final RigAnimationId blockAnimationId;
        private final boolean usesVanillaItemUse;

        GuardMode(
                InteractionHand hand,
                RigAnimationId guardAnimationId,
                @Nullable RigAnimationId blockAnimationId,
                boolean usesVanillaItemUse
        ) {
            this.hand = hand;
            this.guardAnimationId = guardAnimationId;
            this.blockAnimationId = blockAnimationId;
            this.usesVanillaItemUse = usesVanillaItemUse;
        }
    }

    private static final class ActiveShieldGuard {
        private final int startTick;
        private final int durationTicks;
        private int remainingHitBudget;
        private final GuardMode guardMode;

        private ActiveShieldGuard(int startTick, int durationTicks, int remainingHitBudget, GuardMode guardMode) {
            this.startTick = startTick;
            this.durationTicks = durationTicks;
            this.remainingHitBudget = remainingHitBudget;
            this.guardMode = guardMode;
        }

        private int remainingTicks(Mob mob) {
            return this.durationTicks - Math.max(0, mob.tickCount - this.startTick);
        }
    }
}
