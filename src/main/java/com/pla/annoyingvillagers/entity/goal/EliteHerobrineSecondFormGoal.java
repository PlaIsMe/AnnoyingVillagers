package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

public class EliteHerobrineSecondFormGoal<T extends HerobrineMob> extends Goal {
    private static final int DEFAULT_CHECK_INTERVAL_TICKS = 5;
    private static final int DEFAULT_MIN_COOLDOWN_TICKS = 60;
    private static final int DEFAULT_RANDOM_COOLDOWN_TICKS = 61;

    /*
     * Full second form is intentionally much more aggressive for every Herobrine.
     * These delays begin after the previous second-form animation finishes.
     *
     * 20-40 ticks = about 1-2 seconds.
     */
    private static final int STATE_TWO_MIN_COOLDOWN_TICKS = 20;
    private static final int STATE_TWO_RANDOM_COOLDOWN_TICKS = 21;

    /*
     * Mounted Reaper uses dragon-command animations at a controlled pace.
     *
     * 200-400 ticks = 10-20 seconds.
     */
    private static final int MOUNTED_REAPER_MIN_COOLDOWN_TICKS = 20 * 10;
    private static final int MOUNTED_REAPER_RANDOM_COOLDOWN_TICKS = 20 * 10 + 1;
    private static final float MOUNTED_REAPER_USE_CHANCE = 0.80F;

    // A failed 20% chance roll or failed animation start should retry soon.
    // Do NOT consume another full 10-20 second cooldown unless a cast really happened.
    private static final int MOUNTED_REAPER_FAILED_RETRY_TICKS = 20;
    private static final double SWORDSMAN_ULT_MAX_TARGET_DISTANCE = 12.0D;
    private static final double SWORDSMAN_ULT_MAX_TARGET_DISTANCE_SQR = SWORDSMAN_ULT_MAX_TARGET_DISTANCE * SWORDSMAN_ULT_MAX_TARGET_DISTANCE;

    private final T mob;
    private final RigAnimationId[] animationIds;
    private final Predicate<T> extraCondition;
    private final Function<T, RigAnimationId> animationSelector;
    private final int checkIntervalTicks;
    private final int minCooldownTicks;
    private final int randomCooldownTicks;

    private LivingEntity target;
    private RigAnimationId selectedAnimationId;
    private boolean animationStarted;
    private boolean forcedSwordsmanPortalUlt;
    private UUID forcedSwordsmanPortalGroup;

    /*
     * Absolute mob tick deadline.
     *
     * The old cooldownTicks was decremented from canUse(), but canUse() is not
     * guaranteed to run every game tick. That made all second-form cooldowns take
     * substantially longer than their configured tick values.
     */
    private int nextUseTick;

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

    /**
     * Variant for mobs whose legal second-form animation depends on live runtime state.
     * Returning {@code null} from the selector simply skips this activation attempt.
     */
    public EliteHerobrineSecondFormGoal(T mob, Predicate<T> extraCondition, Function<T, RigAnimationId> animationSelector) {
        this(mob, new RigAnimationId[]{RigAnimationId.POINT_LEFT_HAND_MIDDLE}, extraCondition, animationSelector,
                DEFAULT_CHECK_INTERVAL_TICKS, DEFAULT_MIN_COOLDOWN_TICKS, DEFAULT_RANDOM_COOLDOWN_TICKS);
    }

    private EliteHerobrineSecondFormGoal(T mob,RigAnimationId[] animationIds,Predicate<T> extraCondition,int checkIntervalTicks,int minCooldownTicks,int randomCooldownTicks) {
        this(mob, animationIds, extraCondition, null, checkIntervalTicks, minCooldownTicks, randomCooldownTicks);
    }

    private EliteHerobrineSecondFormGoal(T mob,RigAnimationId[] animationIds,Predicate<T> extraCondition,Function<T, RigAnimationId> animationSelector,int checkIntervalTicks,int minCooldownTicks,int randomCooldownTicks) {
        if (animationIds.length == 0) throw new IllegalArgumentException("At least one second-form animation is required");
        this.mob = mob;
        this.animationIds = animationIds.clone();
        this.extraCondition = extraCondition;
        this.animationSelector = animationSelector;
        this.checkIntervalTicks = Math.max(1,checkIntervalTicks);
        this.minCooldownTicks = Math.max(0,minCooldownTicks);
        this.randomCooldownTicks = Math.max(1,randomCooldownTicks);
        this.nextUseTick = mob.tickCount + 40 + mob.getRandom().nextInt(41);
        this.setFlags(EnumSet.of(Flag.MOVE,Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob.tickCount < this.nextUseTick) return false;

        int state = this.mob.getState();
        boolean stateTwo = state == 2;
        boolean mountedReaper = isMountedSecondFormReaper();

        if (!stateTwo && this.mob.tickCount % this.checkIntervalTicks != 0) return false;

        if (!this.isMobReady()) return false;
        if (!this.extraCondition.test(this.mob) || !this.mob.canStartSecondFormAction()) return false;

        LivingEntity currentTarget = this.mob.getTarget();
        if (!isValidTarget(currentTarget)) return false;

        this.forcedSwordsmanPortalGroup = this.findSwordsmanSixPortalGroup(state);
        this.forcedSwordsmanPortalUlt = this.forcedSwordsmanPortalGroup != null;
        float chance = this.forcedSwordsmanPortalUlt ? 1.00F : mountedReaper ? MOUNTED_REAPER_USE_CHANCE : switch (state) {
            case 0 -> 0.60F;
            case 1 -> 0.45F;
            case 2 -> 1.00F;
            default -> 0.0F;
        };

        if (this.mob.getRandom().nextFloat() >= chance) {
            if (mountedReaper) {
                scheduleMountedReaperRetry();
            }
            return false;
        }

        this.target = currentTarget;
        this.selectedAnimationId = this.forcedSwordsmanPortalUlt
                ? RigAnimationId.SWORDSMAN_HEROBRINE_ULT
                : this.animationSelector != null
                ? this.animationSelector.apply(this.mob)
                : this.animationIds[this.mob.getRandom().nextInt(this.animationIds.length)];

        if (this.selectedAnimationId == null) {
            this.target = null;
            this.nextUseTick = this.mob.tickCount
                    + (mountedReaper ? MOUNTED_REAPER_FAILED_RETRY_TICKS : 10);
            return false;
        }

        if (this.mob instanceof SwordsmanHerobrineEntity && this.selectedAnimationId == RigAnimationId.SWORDSMAN_HEROBRINE_ULT && this.mob.distanceToSqr(currentTarget) > SWORDSMAN_ULT_MAX_TARGET_DISTANCE_SQR) {
            this.target = null;
            this.selectedAnimationId = null;
            this.forcedSwordsmanPortalUlt = false;
            this.forcedSwordsmanPortalGroup = null;
            this.nextUseTick = this.mob.tickCount + 10;
            return false;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && this.selectedAnimationId != null
                && RigAnimationController.getActiveAnimationId(this.mob) == this.selectedAnimationId;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void start() {
        this.animationStarted = false;

        if (!isValidTarget(this.target) || this.selectedAnimationId == null) return;
        if (this.mob.getState() == 0 && !this.mob.beginSecondFormActionWindow()) return;
        if (!this.mob.canUseSecondFormAction()) return;

        this.mob.getNavigation().stop();
        this.mob.setAggressive(false);
        this.faceTarget();

        if (this.forcedSwordsmanPortalUlt && this.forcedSwordsmanPortalGroup != null && this.mob instanceof SwordsmanHerobrineEntity swordsman) DemoniacVoltageReaverItem.setPreferredPortalTarget(swordsman.getMainHandItem(), this.forcedSwordsmanPortalGroup, swordsman.getGregUUID());
        RigAnimationController.play(this.mob, RigAnimationSpecs.get(this.selectedAnimationId), this.target);

        this.animationStarted = RigAnimationController.getActiveAnimationId(this.mob) == this.selectedAnimationId;
    }

    @Override
    public void tick() {
        if (!isValidTarget(this.target)) return;
        this.mob.getNavigation().stop();
        this.faceTarget();
    }

    @Override
    public void stop() {
        int state = this.mob.getState();

        if (isMountedSecondFormReaper()) {
            if (this.animationStarted) {
                scheduleMountedReaperCooldown();
            } else {
                scheduleMountedReaperRetry();
            }
        } else if (state == 2) {
            this.nextUseTick = this.mob.tickCount
                    + STATE_TWO_MIN_COOLDOWN_TICKS
                    + this.mob.getRandom().nextInt(STATE_TWO_RANDOM_COOLDOWN_TICKS);
        } else {
            this.nextUseTick = this.mob.tickCount
                    + this.minCooldownTicks
                    + this.mob.getRandom().nextInt(this.randomCooldownTicks);
        }

        this.target = null;
        this.selectedAnimationId = null;
        this.animationStarted = false;
        this.forcedSwordsmanPortalUlt = false;
        this.forcedSwordsmanPortalGroup = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private void scheduleMountedReaperCooldown() {
        this.nextUseTick = this.mob.tickCount
                + MOUNTED_REAPER_MIN_COOLDOWN_TICKS
                + this.mob.getRandom().nextInt(MOUNTED_REAPER_RANDOM_COOLDOWN_TICKS);
    }

    private void scheduleMountedReaperRetry() {
        this.nextUseTick = this.mob.tickCount + MOUNTED_REAPER_FAILED_RETRY_TICKS;
    }

    @Nullable
    private UUID findSwordsmanSixPortalGroup(int state) {
        if (!(this.mob instanceof SwordsmanHerobrineEntity swordsman)) return null;
        if (state != 2) return null;

        UUID gregUuid = swordsman.getGregUUID();
        if (gregUuid == null) return null;
        return HerobrineUtil.findNearbyPortalGroup(swordsman, gregUuid, 6, 48.0D);
    }

    private boolean isMountedSecondFormReaper() {
        return this.mob instanceof ReaperHerobrineEntity reaper
                && reaper.isSecondFormDragonRider();
    }

    private boolean isMobReady() {
        boolean passengerAllowed = !this.mob.isPassenger()
                || this.mob instanceof ReaperHerobrineEntity reaper
                && reaper.isSecondFormDragonRider();

        return !this.mob.level().isClientSide
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && passengerAllowed
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


