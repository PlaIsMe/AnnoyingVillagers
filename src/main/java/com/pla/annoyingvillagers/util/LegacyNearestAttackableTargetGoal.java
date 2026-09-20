package com.pla.annoyingvillagers.util;

import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

/** Adapts legacy one-argument target predicates to 26.1's level-aware selector. */
public class LegacyNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public LegacyNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
        super(mob, targetType, mustSee);
    }

    public LegacyNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, boolean mustReach) {
        super(mob, targetType, mustSee, mustReach);
    }

    public LegacyNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, Predicate<? super LivingEntity> selector) {
        super(mob, targetType, mustSee, (target, level) -> selector.test(target));
    }

    public LegacyNearestAttackableTargetGoal(
            Mob mob,
            Class<T> targetType,
            int randomInterval,
            boolean mustSee,
            boolean mustReach,
            Predicate<? super LivingEntity> selector
    ) {
        super(mob, targetType, randomInterval, mustSee, mustReach, (target, level) -> selector.test(target));
    }
}
