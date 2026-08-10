package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DragonOrbitLeaderGoal extends Goal {
    private static final double TWO_PI = Math.PI * 2.0;

    private static final double ORBIT_RING_INNER_FACTOR = 0.80;
    private static final double ORBIT_RING_OUTER_FACTOR = 1.35;

    private final HerobrineDragonEntity dragon;
    private final Level level;

    private final double baseSpeed;
    private final float orbitRadiusMin;
    private final float orbitRadiusMax;
    private final float farCatchUpDistance;

    private LivingEntity leader;

    private int updateCooldownTicks;

    private double orbitAngleRadians;
    private int orbitDirectionSign;

    private double orbitRadiusCurrent;
    private double orbitRadiusDesired;

    private double orbitAngularSpeedCurrent;
    private double orbitAngularSpeedDesired;

    private double orbitBaseHeightCurrent;
    private double orbitBaseHeightDesired;

    private double verticalWaveAmplitude;
    private double verticalWaveSpeed;
    private double verticalWavePhase;

    private int paramsTimeToLiveTicks;

    private double yJitterCurrent;
    private double yJitterDesired;

//    private Vec3 lastPos = Vec3.ZERO;
//    private double lastDistToTarget = Double.NaN;
//    private int stuckTicks = 0;

    public DragonOrbitLeaderGoal(
            HerobrineDragonEntity dragon,
            double baseSpeed,
            float orbitRadiusMin,
            float orbitRadiusMax,
            float farCatchUpDistance
    ) {
        this.dragon = dragon;
        this.level = dragon.level();
        this.baseSpeed = baseSpeed;
        this.orbitRadiusMin = orbitRadiusMin;
        this.orbitRadiusMax = orbitRadiusMax;
        this.farCatchUpDistance = farCatchUpDistance;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

//    private boolean isLineBlocked(Vec3 from, Vec3 to) {
//        if (level instanceof ServerLevel serverLevel) {
//
//            ClipContext clipContext = new ClipContext(
//                    from, to,
//                    ClipContext.Block.COLLIDER,
//                    ClipContext.Fluid.NONE,
//                    dragon
//            );
//
//            HitResult hitResult = serverLevel.clip(clipContext);
//            return hitResult.getType() != HitResult.Type.MISS;
//        }
//        return false;
//    }
//
//    private boolean isStuck(Vec3 target) {
//        double moved = dragon.position().distanceTo(lastPos);
//        double dist = dragon.position().distanceTo(target);
//
//        boolean notMoving = moved < 0.05;
//        boolean stillFar  = dist > 4.0;
//        boolean notClosing = !Double.isNaN(lastDistToTarget) && (dist > lastDistToTarget - 0.02);
//
//        if ((notMoving && stillFar) || notClosing) stuckTicks++;
//        else stuckTicks = Math.max(0, stuckTicks - 1);
//
//        lastPos = dragon.position();
//        lastDistToTarget = dist;
//
//        return stuckTicks > 10;
//    }
//
//    private Vec3 pickDetour(Vec3 target) {
//        Vec3 from = dragon.position().add(0.0, dragon.getBbHeight() * 0.5, 0.0);
//        Vec3 to = target.add(0.0, dragon.getBbHeight() * 0.5, 0.0);
//
//        Vec3 dir = to.subtract(from);
//        double len = dir.length();
//        if (len < 1e-4) return target;
//
//        dir = dir.scale(1.0 / len);
//
//        Vec3 left  = new Vec3(-dir.z, 0.0, dir.x);
//        Vec3 right = new Vec3(dir.z, 0.0, -dir.x);
//
//        boolean preferDown = hasCeiling();
//        double up1 = preferDown ? -6.0 : 6.0;
//        double up2 = preferDown ? -10.0 : 10.0;
//
//        Vec3[] candidates = new Vec3[] {
//                target.add(0.0, up1, 0.0),
//                target.add(0.0, up2, 0.0),
//                target.add(left.scale(8.0)),
//                target.add(right.scale(8.0)),
//                target.add(left.scale(12.0)).add(0.0, up1, 0.0),
//                target.add(right.scale(12.0)).add(0.0, up1, 0.0),
//        };
//
//        for (Vec3 candidate : candidates) {
//            double yClamp = clampYForWorld(candidate.x, candidate.z, candidate.y);
//            Vec3 vec3CandidateClamp = new Vec3(candidate.x, yClamp, candidate.z);
//            if (canMoveTo(vec3CandidateClamp)) {
//                Vec3 vec3CandidateClampMid = vec3CandidateClamp.add(0.0, dragon.getBbHeight() * 0.5, 0.0);
//                if (!isLineBlocked(from, vec3CandidateClampMid)) return vec3CandidateClamp;
//            }
//        }
//
//        Vec3 leaderPos = leader.position();
//        double y = clampYForWorld(leaderPos.x, leaderPos.z, leaderPos.y + 24.0);
//        return new Vec3(leaderPos.x, y, leaderPos.z);
//    }

    @Nullable
    private LivingEntity resolveOrbitCenter(HerobrineDragonEntity herobrineDragonEntity) {
        LivingEntity livingEntity = herobrineDragonEntity.getSummoner();
        if (livingEntity == null) return herobrineDragonEntity.getOwner();

        if (!(livingEntity instanceof Player)
                && (herobrineDragonEntity.hasPassenger(livingEntity)
                || (livingEntity.getVehicle() instanceof HerobrineDragonEntity herobrineDragon
                && !herobrineDragon.getUUID().equals(herobrineDragonEntity.getUUID())))) {
            LivingEntity target = null;

            if (livingEntity instanceof Mob mob) {
                target = mob.getTarget();
            }
            if (target == null || !target.isAlive()) target = livingEntity.getLastHurtMob();
            if (target == null || !target.isAlive()) target = livingEntity.getLastHurtByMob();

            if (target == null) {
                livingEntity.stopRiding();
            }
            return target;
        }

        return livingEntity;
    }

    @Override
    public boolean canUse() {
        LivingEntity resolved = resolveOrbitCenter(dragon);
        if (resolved == null) return false;
        if (!resolved.isAlive()) return false;
        if (resolved.isSpectator()) return false;

        if (dragon.isLeashed()) return false;
        if (dragon.isPassenger()) return false;
        if (dragon.hasControllingPassenger()) return false;
        if (dragon.isSummonRising()) return false;
        if (dragon.isOrderedToSit() && dragon.getSummoner() == null) return false;
        if (dragon.isRecallActive()) return false;

        leader = resolved;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity resolved = resolveOrbitCenter(dragon);
        if (resolved == null) return false;
        if (!resolved.isAlive()) return false;
        if (resolved.isSpectator()) return false;

        if (dragon.isLeashed()) return false;
        if (dragon.isPassenger()) return false;
        if (dragon.hasControllingPassenger()) return false;
        if (dragon.isSummonRising()) return false;
        if (dragon.isOrderedToSit() && dragon.getSummoner() == null) return false;
        if (dragon.isRecallActive()) return false;

        leader = resolved;
        return true;
    }

    @Override
    public void start() {
        updateCooldownTicks = 0;

        orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
        orbitDirectionSign = dragon.getRandom().nextBoolean() ? 1 : -1;

        orbitRadiusCurrent = orbitRadiusDesired = Mth.nextDouble(dragon.getRandom(), orbitRadiusMin, orbitRadiusMax);

        orbitAngularSpeedCurrent = orbitAngularSpeedDesired = Mth.nextDouble(dragon.getRandom(), 0.045, 0.11);

        orbitBaseHeightCurrent = orbitBaseHeightDesired = 14.0 + dragon.getRandom().nextInt(14);

        verticalWaveAmplitude = Mth.nextDouble(dragon.getRandom(), 2.0, 7.0);
        verticalWaveSpeed = Mth.nextDouble(dragon.getRandom(), 0.018, 0.045);
        verticalWavePhase = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);

        paramsTimeToLiveTicks = 80 + dragon.getRandom().nextInt(140);

        dragon.getNavigation().stop();
        yJitterCurrent = yJitterDesired = Mth.nextDouble(dragon.getRandom(), -6.0, 6.0);
    }

    @Override
    public void stop() {
        leader = null;
        dragon.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (leader == null) return;

        dragon.getLookControl().setLookAt(leader, 10.0F, dragon.getMaxHeadXRot());

        orbitAngleRadians = wrapAngle(orbitAngleRadians + orbitDirectionSign * orbitAngularSpeedCurrent);
        verticalWavePhase = wrapAngle(verticalWavePhase + verticalWaveSpeed);

        if (--paramsTimeToLiveTicks <= 0 || dragon.getRandom().nextInt(220) == 0) {
            rerollOrbitParameters();
        }

        orbitRadiusCurrent = Mth.lerp(0.08, orbitRadiusCurrent, orbitRadiusDesired);
        orbitAngularSpeedCurrent = Mth.lerp(0.08, orbitAngularSpeedCurrent, orbitAngularSpeedDesired);
        orbitBaseHeightCurrent = Mth.lerp(0.08, orbitBaseHeightCurrent, orbitBaseHeightDesired);

        if (--updateCooldownTicks > 0) return;
        updateCooldownTicks = adjustedTickDelay(2);

        Vec3 leaderPosition = leader.position();
        Vec3 dragonOffsetFromLeader = dragon.position().subtract(leaderPosition);

        double distanceToLeader = dragonOffsetFromLeader.length();
        double distanceToLeaderSquared = distanceToLeader * distanceToLeader;
        double farCatchUpDistanceSquared = (double) farCatchUpDistance * (double) farCatchUpDistance;

        if (distanceToLeaderSquared >= farCatchUpDistanceSquared) {
            if (!dragon.isFlying() && dragon.canFly()) {
                dragon.liftOff();
            }

            double catchUpY = computeDesiredY(leaderPosition.x, leaderPosition.z, leaderPosition.y) + 6.0;
            catchUpY = clampYForWorld(leaderPosition.x, leaderPosition.z, catchUpY);
            catchUpY = findNearestFreeY(leaderPosition.x, leaderPosition.z, catchUpY, hasCeiling(), 24);

            Vec3 catchUpTarget = new Vec3(leaderPosition.x, catchUpY, leaderPosition.z);
            setMoveTarget(catchUpTarget, baseSpeed * 1.65);
            return;
        }

        if (!dragon.isFlying() && dragon.canFly()) {
            if (dragon.onGround() || (leader.getY() - dragon.getY()) > 2.0 || distanceToLeader > orbitRadiusMin) {
                dragon.liftOff();
            }
        }

        double orbitRingMinDistance = orbitRadiusMin * ORBIT_RING_INNER_FACTOR;
        double orbitRingMaxDistance = orbitRadiusMax * ORBIT_RING_OUTER_FACTOR;

        Vec3 targetPosition;

        if (distanceToLeader < orbitRingMinDistance || distanceToLeader > orbitRingMaxDistance) {
            Vec3 outwardDirection = distanceToLeader > 1.0E-4 ? dragonOffsetFromLeader.scale(1.0 / distanceToLeader) : new Vec3(1.0, 0.0, 0.0);
            Vec3 ringPoint = leaderPosition.add(outwardDirection.scale(orbitRadiusDesired));
            double ringY = computeDesiredY(ringPoint.x, ringPoint.z, leaderPosition.y);
            targetPosition = new Vec3(ringPoint.x, ringY, ringPoint.z);
        } else {
            double orbitX = leaderPosition.x + Math.cos(orbitAngleRadians) * orbitRadiusCurrent;
            double orbitZ = leaderPosition.z + Math.sin(orbitAngleRadians) * orbitRadiusCurrent;
            double orbitY = computeDesiredY(orbitX, orbitZ, leaderPosition.y);
            targetPosition = new Vec3(orbitX, orbitY, orbitZ);
        }
        if (!canMoveTo(targetPosition)) {
            boolean preferDown = hasCeiling();

            double yFixed = findNearestFreeY(targetPosition.x, targetPosition.z, targetPosition.y, preferDown, 32);
            Vec3 fixed = new Vec3(targetPosition.x, yFixed, targetPosition.z);

            if (canMoveTo(fixed)) {
                targetPosition = fixed;
            } else {
                // thử offset dọc (Nether ưu tiên xuống trước)
                double[] offs = preferDown
                        ? new double[]{-6.0, -10.0, -14.0, 6.0, 10.0, 14.0}
                        : new double[]{6.0, 10.0, 14.0, -6.0, -10.0, -14.0};

                boolean found = false;
                for (double off : offs) {
                    double yy = clampYForWorld(targetPosition.x, targetPosition.z, targetPosition.y + off);
                    Vec3 t = new Vec3(targetPosition.x, yy, targetPosition.z);
                    if (canMoveTo(t)) {
                        targetPosition = t;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
                    double fallbackY = clampYForWorld(leaderPosition.x, leaderPosition.z, leaderPosition.y + orbitBaseHeightCurrent + 18.0);
                    fallbackY = findNearestFreeY(leaderPosition.x, leaderPosition.z, fallbackY, preferDown, 32);
                    targetPosition = new Vec3(leaderPosition.x, fallbackY, leaderPosition.z);
                }
            }
        }

//        Vec3 from = dragon.position().add(0.0, dragon.getBbHeight() * 0.5, 0.0);
//        Vec3 to = targetPosition.add(0.0, dragon.getBbHeight() * 0.5, 0.0);
//
//        boolean blocked = isLineBlocked(from, to);
//        boolean stuck = isStuck(targetPosition);
//
//        if (blocked || stuck) {
//            targetPosition = pickDetour(targetPosition);
//            stuckTicks = 0;
//        }

        setMoveTarget(targetPosition, baseSpeed);
        yJitterCurrent = Mth.lerp(0.05, yJitterCurrent, yJitterDesired);
    }

    private void setMoveTarget(Vec3 target, double speed) {
        if (dragon.isFlying()) {
            dragon.getNavigation().stop();
            dragon.getMoveControl().setWantedPosition(target.x, target.y, target.z, speed);
        } else {
            dragon.getNavigation().moveTo(target.x, target.y, target.z, speed);
        }
    }

    private void rerollOrbitParameters() {
        if (dragon.getRandom().nextFloat() < 0.30f) {
            orbitDirectionSign *= -1;
        }

        orbitRadiusDesired = Mth.nextDouble(dragon.getRandom(), orbitRadiusMin, orbitRadiusMax);
        orbitAngularSpeedDesired = Mth.nextDouble(dragon.getRandom(), 0.04, 0.13);

        orbitBaseHeightDesired = 14.0 + dragon.getRandom().nextInt(18);

        verticalWaveAmplitude = Mth.nextDouble(dragon.getRandom(), 2.0, 8.0);
        verticalWaveSpeed = Mth.nextDouble(dragon.getRandom(), 0.016, 0.05);

        if (dragon.getRandom().nextFloat() < 0.35f) {
            orbitAngleRadians = Mth.nextDouble(dragon.getRandom(), 0.0, TWO_PI);
        }

        paramsTimeToLiveTicks = 70 + dragon.getRandom().nextInt(160);
        yJitterDesired = Mth.nextDouble(dragon.getRandom(), -10.0, 10.0);
    }

    private double computeDesiredY(double x, double z, double leaderY) {
        double y = leaderY + orbitBaseHeightCurrent;

        y += Math.sin(verticalWavePhase) * verticalWaveAmplitude;
        y += yJitterCurrent;
        y = clampYForWorld(x, z, y);
        y = findNearestFreeY(x, z, y, hasCeiling(), 24);

        return y;
    }

    private boolean hasCeiling() {
        return level.dimensionType().hasCeiling();
    }

    private double minY() {
        return level.getMinBuildHeight() + 6.0;
    }

    private double maxY(double x, double z) {
        double max = level.getMaxBuildHeight() - 6.0;

        if (hasCeiling()) {
            BlockPos col = BlockPos.containing(x, 0.0, z);
            int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();

            max = Math.min(max, roofAirY - dragon.getBbHeight() - 2.0);
        }

        if (max < minY()) max = minY();
        return max;
    }

    private double clampYForWorld(double x, double z, double y) {
        return Mth.clamp(y, minY(), maxY(x, z));
    }

    private double findNearestFreeY(double x, double z, double desiredY, boolean preferDown, int maxSteps) {
        double yClamped = clampYForWorld(x, z, desiredY);

        int base = Mth.floor(yClamped);
        int min = Mth.floor(minY());
        int max = Mth.floor(maxY(x, z));

        base = Mth.clamp(base, min, max);

        for (int step = 0; step <= maxSteps; step++) {
            int y1 = preferDown ? (base - step) : (base + step);
            int y2 = preferDown ? (base + step) : (base - step);

            if (y1 >= min && y1 <= max && canMoveTo(new Vec3(x, (double) y1, z))) {
                return (double) y1;
            }
            if (step != 0 && y2 >= min && y2 <= max && canMoveTo(new Vec3(x, (double) y2, z))) {
                return (double) y2;
            }
        }

        return yClamped;
    }

    private boolean canMoveTo(Vec3 pos) {
        Vec3 delta = pos.subtract(dragon.position());
        AABB moved = dragon.getBoundingBox().move(delta);
        return level.noCollision(dragon, moved) && !level.containsAnyLiquid(moved);
    }

    private static double wrapAngle(double angle) {
        angle %= TWO_PI;
        return angle < 0.0 ? angle + TWO_PI : angle;
    }
}
