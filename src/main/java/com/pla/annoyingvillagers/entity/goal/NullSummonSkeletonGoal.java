package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class NullSummonSkeletonGoal extends AnimatedMobGoal {
    private static final int SUMMON_TICK = 30;
    private final NullEntity nullEntity;
    private int elapsedTicks;
    private boolean summoned;

    public NullSummonSkeletonGoal(NullEntity nullEntity) {
        super(nullEntity);
        this.nullEntity = nullEntity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return canStartSummoning(this.nullEntity)
                && !this.isAnimationBusy();
    }

    /**
     * Shared non-animation eligibility for alternate combat backends. The rig goal
     * adds its own busy-animation check; Epic Fight checks that through its attack goal.
     */
    public static boolean canStartSummoning(NullEntity nullEntity) {
        return nullEntity.level() instanceof ServerLevel
                && nullEntity.isAlive()
                && nullEntity.canSummonNullSkeleton();
    }

    @Override
    public boolean canContinueToUse() {
        return this.nullEntity.isAlive() && this.isGoalAnimationPlaying(RigAnimationId.NULL_EXTRA_ULT);
    }

    @Override
    public void start() {
        this.elapsedTicks = 0;
        this.summoned = false;
        this.nullEntity.getNavigation().stop();
        this.playGoalAnimation(RigAnimationId.NULL_EXTRA_ULT, null);
    }

    @Override
    public void tick() {
        this.elapsedTicks++;
        this.nullEntity.getNavigation().stop();
        this.nullEntity.setDeltaMovement(Vec3.ZERO);

        if (!this.usesAnimationEvents() && !this.summoned && this.elapsedTicks >= SUMMON_TICK) {
            this.summoned = true;
            summonSkeleton(this.nullEntity);
        }
    }

    @Override
    public void stop() {
        this.finishGoalAnimation();
        this.nullEntity.getNavigation().stop();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Nullable
    public static NullSkeletonEntity summonSkeleton(NullEntity nullEntity) {
        if (!(nullEntity.level() instanceof ServerLevel serverLevel)) return null;
        if (!nullEntity.isAlive() || !nullEntity.canSummonNullSkeleton()) return null;

        Vec3 forward = new Vec3(nullEntity.getLookAngle().x, 0.0D, nullEntity.getLookAngle().z);
        if (forward.lengthSqr() < 1.0E-6D) {
            float yawRadians = nullEntity.getYRot() * ((float)Math.PI / 180.0F);
            forward = new Vec3(-Mth.sin(yawRadians), 0.0D, Mth.cos(yawRadians));
        }
        Vec3 spawnPosition = nullEntity.position().add(forward.normalize().scale(2.0D));

        NullSkeletonEntity skeleton = new NullSkeletonEntity(AnnoyingVillagersModEntities.NULL_SKELETON.get(), serverLevel);
        skeleton.setNullEntity(nullEntity);
        skeleton.moveTo(spawnPosition.x, spawnPosition.y, spawnPosition.z, nullEntity.getYRot(), nullEntity.getXRot());
        skeleton.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(skeleton.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        if (!serverLevel.addFreshEntity(skeleton)) {
            nullEntity.resetNullSkeletonSummonCooldown();
            return null;
        }

        nullEntity.claimWitherSkeletonSlot(skeleton);
        nullEntity.resetNullSkeletonSummonCooldown();

        if (nullEntity.getTarget() != null && nullEntity.getTarget().isAlive()) {
            skeleton.setTarget(nullEntity.getTarget());
        }
        return skeleton;
    }
}
