package com.pla.annoyingvillagers.rig;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.ArrayDeque;
import java.util.Deque;

public final class RigDamageContext {
    public static final float CRITICAL_DAMAGE_MULTIPLIER = 1.5F;
    private static final ThreadLocal<Deque<HitContext>> CONTEXTS = ThreadLocal.withInitial(ArrayDeque::new);

    private RigDamageContext() {}

    static void push(Mob attacker, LivingEntity target, float damageMultiplier, boolean critical) {
        CONTEXTS.get().push(new HitContext(attacker, target, damageMultiplier, critical));
    }

    static void pop() {
        Deque<HitContext> contexts = CONTEXTS.get();
        if (!contexts.isEmpty()) contexts.pop();
        if (contexts.isEmpty()) CONTEXTS.remove();
    }

    public static boolean matches(LivingEntity target, DamageSource source) {
        HitContext context = current();
        return context != null && context.matches(target, source);
    }

    public static boolean isCritical(LivingEntity target, DamageSource source) {
        HitContext context = current();
        return context != null && context.critical && context.matchesPrimary(target, source);
    }

    public static float finalIncomingMultiplier(LivingEntity target, DamageSource source) {
        HitContext context = current();
        if (context == null || !context.matches(target, source) || !context.bindPrimarySource(source) || context.multiplierApplied) return 1.0F;
        context.multiplierApplied = true;
        return context.damageMultiplier * (context.critical ? CRITICAL_DAMAGE_MULTIPLIER : 1.0F);
    }

    private static HitContext current() {
        Deque<HitContext> contexts = CONTEXTS.get();
        return contexts.isEmpty() ? null : contexts.peek();
    }

    private static final class HitContext {
        private final Mob attacker;
        private final LivingEntity target;
        private final float damageMultiplier;
        private final boolean critical;
        private DamageSource primarySource;
        private boolean multiplierApplied;

        private HitContext(Mob attacker, LivingEntity target, float damageMultiplier, boolean critical) {
            this.attacker = attacker;
            this.target = target;
            this.damageMultiplier = damageMultiplier;
            this.critical = critical;
        }

        private boolean matches(LivingEntity target, DamageSource source) {
            return this.target == target && source.getEntity() == this.attacker;
        }

        private boolean bindPrimarySource(DamageSource source) {
            if (this.primarySource == null) this.primarySource = source;
            return this.primarySource == source;
        }

        private boolean matchesPrimary(LivingEntity target, DamageSource source) {
            return matches(target, source) && (this.primarySource == null || this.primarySource == source);
        }
    }
}
