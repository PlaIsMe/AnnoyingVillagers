package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.NullSkeletonEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class NullSummonSkeletonGoal extends Goal {
    private static final int SUMMON_TICK = 30;
    private final NullEntity nullEntity;
    private int elapsedTicks;
    private boolean summoned;

    public NullSummonSkeletonGoal(NullEntity nullEntity) {
        this.nullEntity = nullEntity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.nullEntity.level() instanceof ServerLevel
                && this.nullEntity.canSummonNullSkeleton()
                && !RigAnimationController.hasActiveAnimation(this.nullEntity);
    }

    @Override
    public boolean canContinueToUse() {
        return this.elapsedTicks < RigAnimationSpecs.get(RigAnimationId.NULL_EXTRA_ULT).durationTicks();
    }

    @Override
    public void start() {
        this.elapsedTicks = 0;
        this.summoned = false;
        this.nullEntity.getNavigation().stop();
        RigAnimationController.play(this.nullEntity, RigAnimationId.NULL_EXTRA_ULT);
    }

    @Override
    public void tick() {
        this.elapsedTicks++;
        this.nullEntity.getNavigation().stop();
        this.nullEntity.setDeltaMovement(Vec3.ZERO);

        if (!this.summoned && this.elapsedTicks >= SUMMON_TICK) {
            this.summoned = true;
            this.summonSkeleton();
        }
    }

    @Override
    public void stop() {
        this.nullEntity.getNavigation().stop();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    private void summonSkeleton() {
        if (!(this.nullEntity.level() instanceof ServerLevel serverLevel)) return;
        if (!this.nullEntity.isAvailableWitherSkeletonSlot()) return;

        Vec3 forward = new Vec3(this.nullEntity.getLookAngle().x, 0.0D, this.nullEntity.getLookAngle().z);
        if (forward.lengthSqr() < 1.0E-6D) {
            float yawRadians = this.nullEntity.getYRot() * ((float)Math.PI / 180.0F);
            forward = new Vec3(-Mth.sin(yawRadians), 0.0D, Mth.cos(yawRadians));
        }
        Vec3 spawnPosition = this.nullEntity.position().add(forward.normalize().scale(2.0D));

        NullSkeletonEntity skeleton = new NullSkeletonEntity(AnnoyingVillagersModEntities.NULL_SKELETON.get(), serverLevel);
        skeleton.setNullEntity(this.nullEntity);
        skeleton.moveTo(spawnPosition.x, spawnPosition.y, spawnPosition.z, this.nullEntity.getYRot(), this.nullEntity.getXRot());
        skeleton.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(skeleton.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
        if (!serverLevel.addFreshEntity(skeleton)) {
            this.nullEntity.resetNullSkeletonSummonCooldown();
            return;
        }

        this.nullEntity.claimWitherSkeletonSlot(skeleton);
        this.nullEntity.resetNullSkeletonSummonCooldown();

        if (this.nullEntity.getTarget() != null && this.nullEntity.getTarget().isAlive()) {
            skeleton.setTarget(this.nullEntity.getTarget());
        }
    }
}
