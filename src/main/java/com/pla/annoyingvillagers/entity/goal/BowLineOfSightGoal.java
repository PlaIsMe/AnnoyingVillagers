package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.util.BowFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class BowLineOfSightGoal extends Goal {
    private static final int ANGLE_STEPS = 16;
    private static final int[] Y_OFFSETS = {1, 0, -1, 2, -2, 3, -3, -4};

    private final Mob mob;
    private final double speedModifier;
    private final double minShootDistance;
    private final double maxShootDistance;
    private int repathDelay;

    public BowLineOfSightGoal(Mob mob, double speedModifier, double minShootDistance, double maxShootDistance) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.minShootDistance = minShootDistance;
        this.maxShootDistance = maxShootDistance;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return shouldReposition();
    }

    @Override
    public boolean canContinueToUse() {
        return shouldReposition();
    }

    @Override
    public void start() {
        this.repathDelay = 0;
        repath();
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }

        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (!needsBetterBowPosition(target)) {
            this.mob.getNavigation().stop();
            return;
        }

        if (this.repathDelay-- <= 0 || this.mob.getNavigation().isDone()) {
            this.repathDelay = 10 + this.mob.getRandom().nextInt(10);
            repath();
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
    }

    private boolean shouldReposition() {
        LivingEntity target = this.mob.getTarget();
        return !this.mob.level().isClientSide
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && !this.mob.isDeadOrDying()
                && !this.mob.isNoAi()
                && !this.mob.isPassenger()
                && this.mob.getMainHandItem().getItem() instanceof BowItem
                && target != null
                && target.isAlive()
                && needsBetterBowPosition(target);
    }

    private boolean needsBetterBowPosition(LivingEntity target) {
        double distance = this.mob.distanceTo(target);
        return distance > getMaxShootDistance(target)
                || !BowFunction.hasClearShot(this.mob, target);
    }

    private void repath() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) {
            return;
        }

        BlockPos clearShotPos = findClearShotPosition(target);
        if (clearShotPos != null) {
            Path path = this.mob.getNavigation().createPath(clearShotPos, 0);
            if (path != null) {
                this.mob.getNavigation().moveTo(path, this.speedModifier);
                return;
            }
        }

        this.mob.getNavigation().moveTo(target, this.speedModifier);
    }

    private BlockPos findClearShotPosition(LivingEntity target) {
        double currentDistance = this.mob.distanceTo(target);
        double effectiveMaxShootDistance = getMaxShootDistance(target);
        double preferredDistance = clamp(currentDistance, this.minShootDistance + 1.0D, effectiveMaxShootDistance - 1.0D);
        double[] distances = {
                preferredDistance,
                effectiveMaxShootDistance - 1.0D,
                (this.minShootDistance + effectiveMaxShootDistance) * 0.5D,
                this.minShootDistance + 1.0D
        };
        double startAngle = this.mob.getRandom().nextDouble() * Math.PI * 2.0D;

        for (double distance : distances) {
            for (int i = 0; i < ANGLE_STEPS; i++) {
                double angle = startAngle + (Math.PI * 2.0D * i / ANGLE_STEPS);
                double x = target.getX() + Math.cos(angle) * distance;
                double z = target.getZ() + Math.sin(angle) * distance;
                BlockPos standPos = findStandPosition(x, z);

                if (standPos == null || !isInBowRange(standPos, target)) {
                    continue;
                }

                Path path = this.mob.getNavigation().createPath(standPos, 0);
                if (path == null) {
                    continue;
                }

                Vec3 eyePos = Vec3.atBottomCenterOf(standPos).add(0.0D, this.mob.getEyeHeight(), 0.0D);
                if (BowFunction.hasClearShotFrom(this.mob.level(), this.mob, eyePos, target)) {
                    return standPos;
                }
            }
        }

        return null;
    }

    private BlockPos findStandPosition(double x, double z) {
        BlockPos base = BlockPos.containing(x, this.mob.getY(), z);
        for (int yOffset : Y_OFFSETS) {
            BlockPos standPos = base.offset(0, yOffset, 0);
            if (canStandAt(standPos)) {
                return standPos;
            }
        }

        Level level = this.mob.level();
        BlockPos surface = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.containing(x, 0.0D, z));
        return canStandAt(surface) ? surface : null;
    }

    private boolean canStandAt(BlockPos standPos) {
        if (this.mob.level().getBlockState(standPos.below()).getCollisionShape(this.mob.level(), standPos.below()).isEmpty()) {
            return false;
        }

        Vec3 feet = Vec3.atBottomCenterOf(standPos);
        AABB box = this.mob.getBoundingBox().move(
                feet.x - this.mob.getX(),
                feet.y - this.mob.getY(),
                feet.z - this.mob.getZ()
        );

        return this.mob.level().noCollision(this.mob, box);
    }

    private boolean isInBowRange(BlockPos standPos, LivingEntity target) {
        double distanceSqr = Vec3.atBottomCenterOf(standPos).distanceToSqr(target.position());
        return distanceSqr >= this.minShootDistance * this.minShootDistance
                && distanceSqr <= getMaxShootDistance(target) * getMaxShootDistance(target);
    }

    private double getMaxShootDistance(LivingEntity target) {
        return target instanceof HerobrineDragonEntity ? 80.0D : this.maxShootDistance;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
