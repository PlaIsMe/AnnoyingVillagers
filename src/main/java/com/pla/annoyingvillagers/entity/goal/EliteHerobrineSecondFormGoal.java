package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EliteHerobrineSecondFormGoal<T extends HerobrineMob> extends Goal {
    private static final int DEFAULT_CHECK_INTERVAL_TICKS = 5;

    // Cooldown between individual ULT actions.
    // 60–120 ticks = 3–6 seconds.
    private static final int DEFAULT_MIN_COOLDOWN_TICKS = 60;
    private static final int DEFAULT_RANDOM_COOLDOWN_TICKS = 61;

    private final T mob;
    private final RigAnimationId animationId;
    private final Predicate<T> extraCondition;
    private final int checkIntervalTicks;
    private final int minCooldownTicks;
    private final int randomCooldownTicks;

    private LivingEntity target;
    private int cooldownTicks;

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId animationId) {
        this(mob,animationId,ignored -> true);
    }

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId animationId,Predicate<T> extraCondition) {
        this(mob,animationId,extraCondition,DEFAULT_CHECK_INTERVAL_TICKS,DEFAULT_MIN_COOLDOWN_TICKS,DEFAULT_RANDOM_COOLDOWN_TICKS);
    }

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId animationId,Predicate<T> extraCondition,int checkIntervalTicks,int minCooldownTicks,int randomCooldownTicks) {
        this.mob = mob;
        this.animationId = animationId;
        this.extraCondition = extraCondition;
        this.checkIntervalTicks = Math.max(1,checkIntervalTicks);
        this.minCooldownTicks = Math.max(0,minCooldownTicks);
        this.randomCooldownTicks = Math.max(1,randomCooldownTicks);
        this.cooldownTicks = 40 + mob.getRandom().nextInt(41);
        this.setFlags(EnumSet.of(Flag.MOVE,Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.cooldownTicks > 0) {
            this.cooldownTicks--;
            return false;
        }

        if (this.mob.tickCount % this.checkIntervalTicks != 0) return false;
        if (!this.isMobReady()) return false;
        if (!this.extraCondition.test(this.mob)) return false;
        if (!this.mob.canStartSecondFormAction()) return false;

        LivingEntity currentTarget = this.mob.getTarget();
        if (!isValidTarget(currentTarget)) return false;

        float chance = switch (this.mob.getState()) {
            case 0 -> 0.60F;
            case 1 -> 0.45F;
            case 2 -> 0.50F;
            default -> 0.0F;
        };

        if (this.mob.getRandom().nextFloat() >= chance) return false;

        this.target = currentTarget;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && RigAnimationController.getActiveAnimationId(this.mob) == this.animationId;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        if (!isValidTarget(this.target)) return;

        // State 0 opens a limited state-1 window first.
        if (this.mob.getState() == 0 && !this.mob.beginSecondFormActionWindow()) return;

        // State 1 requires hitLeft > 0.
        // State 2 is always allowed.
        if (!this.mob.canUseSecondFormAction()) return;

        this.mob.getNavigation().stop();
        this.mob.setAggressive(false);
        this.faceTarget();
        RigAnimationController.play(this.mob,RigAnimationSpecs.get(this.animationId),this.target);
    }

    @Override
    public void tick() {
        if (!isValidTarget(this.target)) return;

        this.mob.getNavigation().stop();
        this.faceTarget();
    }

    @Override
    public void stop() {
        this.cooldownTicks = this.minCooldownTicks + this.mob.getRandom().nextInt(this.randomCooldownTicks);
        this.target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private boolean isMobReady() {
        return !this.mob.level().isClientSide
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && !this.mob.isPassenger()
                && !RigStunController.isStunned(this.mob)
                && !RigAnimationController.hasActiveAnimation(this.mob);
    }

    private void faceTarget() {
        this.mob.getLookControl().setLookAt(this.target,70.0F,70.0F);
        this.mob.lookAt(this.target,70.0F,70.0F);
    }

    private static boolean isValidTarget(LivingEntity target) {
        return target != null
                && target.isAlive()
                && !target.isRemoved()
                && !target.isDeadOrDying();
    }
}