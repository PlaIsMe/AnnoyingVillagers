/*
 * SPDX-License-Identifier: GPL-3.0-only
 * Behavior scheduling concepts adapted from Combat Evolution by ShelMarow.
 */
package com.pla.annoyingvillagers.advancedmobpatch;

import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public final class AdvancedCombatBehaviors<T extends MobPatch<?>> {
    private final List<BehaviorRoot<T>> behaviorRoots;
    private final HurtHandler<T> noBehaviorHurtHandler;
    private Behavior<T> currentBehavior;

    private AdvancedCombatBehaviors(Builder<T> builder) {
        this.behaviorRoots = builder.behaviorRoots.stream().map(BehaviorRoot.Builder::build).toList();
        this.noBehaviorHurtHandler = builder.noBehaviorHurtHandler;
    }

    public void tick(T patch) {
        this.behaviorRoots.forEach(BehaviorRoot::tickCooldown);

        if (this.currentBehavior == null) {
            Behavior<T> selected = this.selectOpeningBehavior(patch);
            if (selected != null) {
                this.startBehavior(patch, selected);
            }
            return;
        }

        if (patch.isStunned()) {
            this.clearCurrentBehavior();
            return;
        }

        if (!this.currentBehavior.isReadyToAdvance(patch)) {
            return;
        }

        Behavior<T> next = selectBehavior(
                patch,
                this.currentBehavior.nextBehaviors,
                patch.getOriginal().getRandom()
        );
        if (next != null) {
            this.startBehavior(patch, next);
            return;
        }

        this.currentBehavior.behaviorRoot.startCooldown();
        this.currentBehavior = null;
    }

    public Behavior<T> getCurrentBehavior() {
        return this.currentBehavior;
    }

    public void clearCurrentBehavior() {
        if (this.currentBehavior != null) {
            this.currentBehavior.behaviorRoot.startCooldown();
            this.currentBehavior = null;
        }
    }

    public AttackResult executeCurrentBehaviorOnHurt(T patch, DamageSource source, AttackResult result) {
        return this.currentBehavior == null
                ? this.noBehaviorHurtHandler.apply(patch, source, result)
                : this.currentBehavior.executeOnHurt(patch, source, result);
    }

    private Behavior<T> selectOpeningBehavior(T patch) {
        List<BehaviorRoot<T>> eligibleRoots = this.behaviorRoots.stream()
                .filter(BehaviorRoot::isReady)
                .filter(root -> root.hasEligibleBehavior(patch))
                .toList();
        if (eligibleRoots.isEmpty()) {
            return null;
        }

        double highestPriority = eligibleRoots.stream()
                .mapToDouble(BehaviorRoot::getPriority)
                .max()
                .orElse(0.0D);
        List<BehaviorRoot<T>> highestPriorityRoots = eligibleRoots.stream()
                .filter(root -> Double.compare(root.getPriority(), highestPriority) == 0)
                .toList();
        RandomSource random = patch.getOriginal().getRandom();
        BehaviorRoot<T> root = selectWeighted(highestPriorityRoots, BehaviorRoot::getWeight, random);
        return root == null ? null : selectBehavior(patch, root.firstBehaviors, random);
    }

    private void startBehavior(T patch, Behavior<T> behavior) {
        this.currentBehavior = behavior;
        behavior.execute(patch);
    }

    private static <T extends MobPatch<?>> Behavior<T> selectBehavior(
            T patch,
            List<Behavior<T>> behaviors,
            RandomSource random
    ) {
        List<Behavior<T>> eligible = behaviors.stream().filter(behavior -> behavior.checkPredicates(patch)).toList();
        if (eligible.isEmpty()) {
            return null;
        }

        double highestPriority = eligible.stream().mapToDouble(Behavior::getPriority).max().orElse(0.0D);
        List<Behavior<T>> highestPriorityBehaviors = eligible.stream()
                .filter(behavior -> Double.compare(behavior.getPriority(), highestPriority) == 0)
                .toList();
        return selectWeighted(highestPriorityBehaviors, Behavior::getWeight, random);
    }

    private static <E> E selectWeighted(List<E> entries, WeightGetter<E> weightGetter, RandomSource random) {
        if (entries.isEmpty()) {
            return null;
        }

        double totalWeight = entries.stream().mapToDouble(entry -> Math.max(0.0D, weightGetter.get(entry))).sum();
        if (totalWeight <= 0.0D) {
            return entries.get(random.nextInt(entries.size()));
        }

        double selectedWeight = random.nextDouble() * totalWeight;
        for (E entry : entries) {
            selectedWeight -= Math.max(0.0D, weightGetter.get(entry));
            if (selectedWeight <= 0.0D) {
                return entry;
            }
        }
        return entries.get(entries.size() - 1);
    }

    public static <T extends MobPatch<?>> Builder<T> builder() {
        return new Builder<>();
    }

    @FunctionalInterface
    private interface WeightGetter<E> {
        double get(E entry);
    }

    @FunctionalInterface
    public interface HurtHandler<T extends MobPatch<?>> {
        AttackResult apply(T patch, DamageSource source, AttackResult result);
    }

    public static final class Builder<T extends MobPatch<?>> {
        private final List<BehaviorRoot.Builder<T>> behaviorRoots = new ArrayList<>();
        private HurtHandler<T> noBehaviorHurtHandler = (patch, source, result) -> result;

        public Builder<T> newBehaviorRoot(BehaviorRoot.Builder<T> behaviorRoot) {
            this.behaviorRoots.add(Objects.requireNonNull(behaviorRoot));
            return this;
        }

        public Builder<T> setNoBehaviorHurt(HurtHandler<T> handler) {
            this.noBehaviorHurtHandler = Objects.requireNonNull(handler);
            return this;
        }

        public AdvancedCombatBehaviors<T> build() {
            return new AdvancedCombatBehaviors<>(this);
        }
    }

    public static final class BehaviorRoot<T extends MobPatch<?>> {
        private final String rootName;
        private final List<Behavior<T>> firstBehaviors;
        private final double priority;
        private final double weight;
        private final int maxCooldown;
        private int cooldown;

        private BehaviorRoot(Builder<T> builder) {
            this.rootName = builder.rootName;
            this.priority = builder.priority;
            this.weight = builder.weight;
            this.maxCooldown = builder.maxCooldown;
            this.cooldown = builder.initialCooldown;
            this.firstBehaviors = builder.firstBehaviors.stream().map(behavior -> behavior.build(this)).toList();
        }

        public String getRootName() {
            return this.rootName;
        }

        public double getPriority() {
            return this.priority;
        }

        public double getWeight() {
            return this.weight;
        }

        private boolean isReady() {
            return this.cooldown <= 0;
        }

        private boolean hasEligibleBehavior(T patch) {
            return this.firstBehaviors.stream().anyMatch(behavior -> behavior.checkPredicates(patch));
        }

        private void tickCooldown() {
            if (this.cooldown > 0) {
                this.cooldown--;
            }
        }

        private void startCooldown() {
            this.cooldown = Math.max(0, this.maxCooldown);
        }

        public static <T extends MobPatch<?>> Builder<T> builder() {
            return new Builder<>();
        }

        public static final class Builder<T extends MobPatch<?>> {
            private String rootName = "";
            private final List<Behavior.Builder<T>> firstBehaviors = new ArrayList<>();
            private double priority;
            private double weight = 1.0D;
            private int maxCooldown;
            private int initialCooldown;

            public Builder<T> rootName(String rootName) {
                this.rootName = Objects.requireNonNullElse(rootName, "");
                return this;
            }

            public Builder<T> addFirstBehavior(Behavior.Builder<T> behavior) {
                this.firstBehaviors.add(Objects.requireNonNull(behavior));
                return this;
            }

            public Builder<T> priority(double priority) {
                this.priority = priority;
                return this;
            }

            public Builder<T> weight(double weight) {
                this.weight = weight;
                return this;
            }

            public Builder<T> maxCooldown(int maxCooldown) {
                this.maxCooldown = Math.max(0, maxCooldown);
                return this;
            }

            public Builder<T> cooldown(int cooldown) {
                this.initialCooldown = Math.max(0, cooldown);
                return this;
            }

            private BehaviorRoot<T> build() {
                return new BehaviorRoot<>(this);
            }
        }
    }

    public static final class Behavior<T extends MobPatch<?>> {
        private final String name;
        private final Consumer<T> action;
        private final List<Consumer<T>> extraActions;
        private final List<Function<T, Boolean>> predicates;
        private final List<Behavior<T>> nextBehaviors;
        private final HurtHandler<T> hurtHandler;
        private final double priority;
        private final double weight;
        private final BehaviorRoot<T> behaviorRoot;
        private boolean justStarted;

        private Behavior(Builder<T> builder, BehaviorRoot<T> behaviorRoot) {
            this.name = builder.name;
            this.action = builder.action;
            this.extraActions = List.copyOf(builder.extraActions);
            this.predicates = List.copyOf(builder.predicates);
            this.hurtHandler = builder.hurtHandler;
            this.priority = builder.priority;
            this.weight = builder.weight;
            this.behaviorRoot = behaviorRoot;
            this.nextBehaviors = builder.nextBehaviors.stream().map(next -> next.build(behaviorRoot)).toList();
        }

        public String getName() {
            return this.name;
        }

        public double getPriority() {
            return this.priority;
        }

        public double getWeight() {
            return this.weight;
        }

        private boolean checkPredicates(T patch) {
            for (Function<T, Boolean> predicate : this.predicates) {
                if (!Boolean.TRUE.equals(predicate.apply(patch))) {
                    return false;
                }
            }
            return true;
        }

        private void execute(T patch) {
            this.action.accept(patch);
            this.extraActions.forEach(action -> action.accept(patch));
            patch.updateEntityState();
            this.justStarted = true;
        }

        private boolean isReadyToAdvance(T patch) {
            if (this.justStarted) {
                this.justStarted = false;
                return false;
            }
            return patch.getEntityState().canBasicAttack() || !patch.getEntityState().inaction();
        }

        private AttackResult executeOnHurt(T patch, DamageSource source, AttackResult result) {
            return this.hurtHandler.apply(patch, source, result);
        }

        public static <T extends MobPatch<?>> Builder<T> builder() {
            return new Builder<>();
        }

        public static final class Builder<T extends MobPatch<?>> {
            private String name = "";
            private Consumer<T> action = patch -> {
            };
            private final List<Consumer<T>> extraActions = new ArrayList<>();
            private final List<Function<T, Boolean>> predicates = new ArrayList<>();
            private final List<Behavior.Builder<T>> nextBehaviors = new ArrayList<>();
            private HurtHandler<T> hurtHandler = (patch, source, result) -> result;
            private double priority;
            private double weight = 1.0D;

            public Builder<T> name(String name) {
                this.name = Objects.requireNonNullElse(name, "");
                return this;
            }

            public Builder<T> customBehavior(Consumer<T> action) {
                this.action = Objects.requireNonNull(action);
                return this;
            }

            public Builder<T> animationBehavior(
                    AnimationAccessor<? extends StaticAnimation> animation,
                    float transitionTime
            ) {
                this.action = patch -> patch.playAnimationSynchronized(animation, transitionTime);
                return this;
            }

            public Builder<T> addExBehavior(Consumer<T> action) {
                this.extraActions.add(Objects.requireNonNull(action));
                return this;
            }

            @SafeVarargs
            public final Builder<T> addExBehavior(Consumer<T>... actions) {
                for (Consumer<T> action : actions) {
                    this.addExBehavior(action);
                }
                return this;
            }

            public Builder<T> custom(Function<T, Boolean> predicate) {
                this.predicates.add(Objects.requireNonNull(predicate));
                return this;
            }

            public Builder<T> withinDistance(double minDistance, double maxDistance) {
                double minimum = Math.max(0.0D, minDistance);
                double maximum = Math.max(minimum, maxDistance);
                this.predicates.add(patch -> {
                    LivingEntity target = patch.getTarget();
                    if (target == null || !target.isAlive()) {
                        return false;
                    }
                    double distanceSqr = patch.getOriginal().distanceToSqr(target);
                    return distanceSqr >= minimum * minimum && distanceSqr <= maximum * maximum;
                });
                return this;
            }

            public Builder<T> randomChance(float chance) {
                float clampedChance = Math.max(0.0F, Math.min(1.0F, chance));
                this.predicates.add(patch -> patch.getOriginal().getRandom().nextFloat() < clampedChance);
                return this;
            }

            public Builder<T> priority(double priority) {
                this.priority = priority;
                return this;
            }

            public Builder<T> weight(double weight) {
                this.weight = weight;
                return this;
            }

            public Builder<T> addNextBehavior(Behavior.Builder<T> behavior) {
                this.nextBehaviors.add(Objects.requireNonNull(behavior));
                return this;
            }

            public Builder<T> onHurt(HurtHandler<T> hurtHandler) {
                this.hurtHandler = Objects.requireNonNull(hurtHandler);
                return this;
            }

            private Behavior<T> build(BehaviorRoot<T> behaviorRoot) {
                return new Behavior<>(this, behaviorRoot);
            }
        }
    }
}
