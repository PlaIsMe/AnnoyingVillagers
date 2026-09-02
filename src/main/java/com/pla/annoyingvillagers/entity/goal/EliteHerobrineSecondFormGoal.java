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
    private static final int DEFAULT_MIN_COOLDOWN_TICKS = 60;
    private static final int DEFAULT_RANDOM_COOLDOWN_TICKS = 61;

    private final T mob;
    private final RigAnimationId[] animationIds;
    private final Predicate<T> extraCondition;
    private final int checkIntervalTicks;
    private final int minCooldownTicks;
    private final int randomCooldownTicks;

    private LivingEntity target;
    private RigAnimationId selectedAnimationId;
    private int cooldownTicks;

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId animationId) {
        this(mob,new RigAnimationId[]{animationId},ignored -> true,DEFAULT_CHECK_INTERVAL_TICKS,DEFAULT_MIN_COOLDOWN_TICKS,DEFAULT_RANDOM_COOLDOWN_TICKS);
    }

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId animationId,Predicate<T> extraCondition) {
        this(mob,new RigAnimationId[]{animationId},extraCondition,DEFAULT_CHECK_INTERVAL_TICKS,DEFAULT_MIN_COOLDOWN_TICKS,DEFAULT_RANDOM_COOLDOWN_TICKS);
    }

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId firstAnimationId,RigAnimationId secondAnimationId,Predicate<T> extraCondition) {
        this(mob,new RigAnimationId[]{firstAnimationId,secondAnimationId},extraCondition,DEFAULT_CHECK_INTERVAL_TICKS,DEFAULT_MIN_COOLDOWN_TICKS,DEFAULT_RANDOM_COOLDOWN_TICKS);
    }

    public EliteHerobrineSecondFormGoal(T mob,RigAnimationId animationId,Predicate<T> extraCondition,int checkIntervalTicks,int minCooldownTicks,int randomCooldownTicks) {
        this(mob,new RigAnimationId[]{animationId},extraCondition,checkIntervalTicks,minCooldownTicks,randomCooldownTicks);
    }

    private EliteHerobrineSecondFormGoal(T mob,RigAnimationId[] animationIds,Predicate<T> extraCondition,int checkIntervalTicks,int minCooldownTicks,int randomCooldownTicks) {
        if (animationIds.length == 0) throw new IllegalArgumentException("At least one second-form animation is required");
        this.mob = mob;
        this.animationIds = animationIds.clone();
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
        if (this.mob.tickCount % this.checkIntervalTicks != 0 || !this.isMobReady()) return false;
        if (!this.extraCondition.test(this.mob) || !this.mob.canStartSecondFormAction()) return false;
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
        this.selectedAnimationId = this.animationIds[this.mob.getRandom().nextInt(this.animationIds.length)];
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isAlive() && !this.mob.isRemoved() && !this.mob.isDeadOrDying() && this.selectedAnimationId != null && RigAnimationController.getActiveAnimationId(this.mob) == this.selectedAnimationId;
    }

    @Override
    public boolean isInterruptable() { return false; }

    @Override
    public void start() {
        if (!isValidTarget(this.target) || this.selectedAnimationId == null) return;
        if (this.mob.getState() == 0 && !this.mob.beginSecondFormActionWindow()) return;
        if (!this.mob.canUseSecondFormAction()) return;
        this.mob.getNavigation().stop();
        this.mob.setAggressive(false);
        this.faceTarget();
        RigAnimationController.play(this.mob,RigAnimationSpecs.get(this.selectedAnimationId),this.target);
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
        this.selectedAnimationId = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() { return true; }

    private boolean isMobReady() {
        return !this.mob.level().isClientSide && this.mob.isAlive() && !this.mob.isRemoved() && !this.mob.isDeadOrDying() && !this.mob.isNoAi() && !this.mob.isPassenger() && !RigStunController.isStunned(this.mob) && !RigAnimationController.hasActiveAnimation(this.mob);
    }

    private void faceTarget() {
        this.mob.getLookControl().setLookAt(this.target,70.0F,70.0F);
        this.mob.lookAt(this.target,70.0F,70.0F);
    }

    private static boolean isValidTarget(LivingEntity target) {
        return target != null && target.isAlive() && !target.isRemoved() && !target.isDeadOrDying();
    }
}
