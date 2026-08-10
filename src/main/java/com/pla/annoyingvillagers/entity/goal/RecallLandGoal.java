package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class RecallLandGoal extends Goal {
    private final HerobrineDragonEntity dragon;
    private int stage = 0;

    public RecallLandGoal(HerobrineDragonEntity dragon) {
        this.dragon = dragon;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return dragon.isRecallActive()
                && dragon.getSummoner() != null
                && dragon.getSummoner().isAlive()
                && !dragon.isPassenger()
                && !dragon.hasControllingPassenger();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        stage = 0;

        if (dragon.level() instanceof ServerLevel serverLevel) {
            dragon.setRecallLandPos(findLandingPosNearSummoner(serverLevel, dragon.getSummoner()));
        }

        if (!dragon.isFlying() && dragon.canFly()) dragon.liftOff();
        dragon.setFlying(true);
        dragon.setNavigation(true);
        dragon.getNavigation().stop();
        dragon.setNoGravity(true);
    }

    @Override
    public void stop() {
        dragon.setRecallActive(false);
        dragon.setRecallLandPos(null);
        dragon.setNoGravity(false);
        stage = 0;
    }

    @Override
    public void tick() {
        if (!(dragon.level() instanceof ServerLevel serverLevel)) {
            stop();
            return;
        }

        LivingEntity owner = dragon.getSummoner();
        if (owner == null || !owner.isAlive()) {
            stop();
            return;
        }

        dragon.getNavigation().stop();
        dragon.setNoGravity(true);
        if (!dragon.isFlying() && dragon.canFly()) dragon.liftOff();
        dragon.setFlying(true);
        dragon.setNavigation(true);

        if (dragon.isRecallAutoMount()) {
            Vec3 catchPos = findCatchPosAtSummoner(serverLevel, owner);
            dragon.setRecallLandPos(catchPos);
            dragon.getMoveControl().setWantedPosition(catchPos.x, catchPos.y, catchPos.z, 1.8D);
            dragon.aimBodyAndHeadAt(owner.getEyePosition(), 25.0F, 18.0F);

            if (dragon.distanceToSqr(catchPos) < 16.0D || dragon.distanceToSqr(owner) < 25.0D) {
                dragon.setNoGravity(false);
                dragon.setDeltaMovement(Vec3.ZERO);
                owner.startRiding(dragon, true);
                stop();
            }
            return;
        }

        if (dragon.getRecallLandPos() == null) {
            dragon.setRecallLandPos(findLandingPosNearSummoner(serverLevel, owner));
        }

        Vec3 land = dragon.getRecallLandPos();

        double aboveY = Math.max(owner.getY() + 10.0, land.y + 10.0);
        aboveY = clampYForWorld(serverLevel, land.x, land.z, aboveY);
        aboveY = findNearestFreeY(serverLevel, land.x, land.z, aboveY, serverLevel.dimensionType().hasCeiling());
        Vec3 above = new Vec3(land.x, aboveY, land.z);

        if (stage == 0) {
            dragon.getMoveControl().setWantedPosition(above.x, above.y, above.z, 1.8D);
            dragon.aimBodyAndHeadAt(above, 25.0F, 18.0F);

            if (dragon.distanceToSqr(above) < 16.0D) {
                stage = 1;
            }
            return;
        }


        double landY = clampYForWorld(serverLevel, land.x, land.z, land.y);
        Vec3 landFixed = new Vec3(land.x, landY, land.z);

        dragon.getMoveControl().setWantedPosition(landFixed.x, landFixed.y, landFixed.z, 1.2D);
        dragon.aimBodyAndHeadAt(landFixed, 18.0F, 12.0F);

        if (dragon.distanceToSqr(landFixed) < 9.0D) {
            dragon.setNoGravity(false);
            dragon.setDeltaMovement(Vec3.ZERO);

            stop();
        }
    }

    private Vec3 findCatchPosAtSummoner(ServerLevel level, LivingEntity owner) {
        double y = owner.getY() - dragon.getPassengersRidingOffset();
        y = clampYForWorld(level, owner.getX(), owner.getZ(), y);

        if (canFitAt(level, owner.getX(), y, owner.getZ())) {
            return new Vec3(owner.getX(), y, owner.getZ());
        }

        y = findNearestFreeY(level, owner.getX(), owner.getZ(), y, false);
        return new Vec3(owner.getX(), y, owner.getZ());
    }

    private Vec3 findLandingPosNearSummoner(ServerLevel level, LivingEntity owner) {
        BlockPos base = owner.blockPosition();
        boolean hasCeiling = level.dimensionType().hasCeiling();

        for (int r = 0; r <= 3; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos col = base.offset(dx, 0, dz);
                    double x = col.getX() + 0.5;
                    double z = col.getZ() + 0.5;

                    if (!hasCeiling) {
                        int groundY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
                        double y = groundY + 1.0;

                        if (canFitAt(level, x, y, z)) {
                            return new Vec3(x, y, z);
                        }
                    } else {
                        Vec3 found = findCeilingLandingAtColumn(level, owner, x, z);
                        if (found != null) return found;
                    }
                }
            }
        }

        if (hasCeiling) {
            Vec3 found = findCeilingLandingAtColumn(level, owner, owner.getX(), owner.getZ());
            if (found != null) return found;

            double y = clampYForWorld(level, owner.getX(), owner.getZ(), owner.getY() + 1.0);
            y = findNearestFreeY(level, owner.getX(), owner.getZ(), y, true);
            return new Vec3(owner.getX(), y, owner.getZ());
        } else {
            BlockPos col = BlockPos.containing(owner.getX(), 0.0, owner.getZ());
            int groundY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
            return new Vec3(owner.getX(), groundY + 1.0, owner.getZ());
        }
    }

    @Nullable
    private Vec3 findCeilingLandingAtColumn(ServerLevel level, LivingEntity owner, double x, double z) {
        double minY = level.getMinBuildHeight() + 6.0;
        int yStart = Mth.floor(owner.getY()) + 8;

        BlockPos col = BlockPos.containing(x, 0.0, z);
        int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();

        double maxY = Math.min(level.getMaxBuildHeight() - 2.0, roofAirY - dragon.getBbHeight() - 2.0);
        if (maxY < minY) maxY = minY;

        yStart = Math.min(yStart, Mth.floor(maxY));
        yStart = Math.max(yStart, Mth.floor(minY));

        int yMin = Mth.floor(minY);

        for (int y = yStart; y >= yMin && (yStart - y) <= 96; y--) {

            if (!canFitAt(level, x, y, z)) continue;
            if (!hasGroundBelow(level, x, y, z)) continue;

            return new Vec3(x, y, z);
        }

        return null;
    }

    private double clampYForWorld(ServerLevel level, double x, double z, double y) {
        double min = level.getMinBuildHeight() + 6.0;
        double max = level.getMaxBuildHeight() - 6.0;

        if (level.dimensionType().hasCeiling()) {
            BlockPos col = BlockPos.containing(x, 0.0, z);
            int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
            max = Math.min(max, roofAirY - dragon.getBbHeight() - 2.0);
        }

        if (max < min) max = min;
        return Mth.clamp(y, min, max);
    }

    private boolean canFitAt(ServerLevel level, double x, double y, double z) {
        AABB movedBox = dragon.getBoundingBox().move(
                x - dragon.getX(),
                y - dragon.getY(),
                z - dragon.getZ()
        );
        return level.noCollision(dragon, movedBox) && !level.containsAnyLiquid(movedBox);
    }

    private boolean hasGroundBelow(ServerLevel level, double x, double y, double z) {
        AABB box = dragon.getBoundingBox().move(
                x - dragon.getX(),
                y - dragon.getY(),
                z - dragon.getZ()
        );

        AABB below = box.move(0.0, -0.25, 0.0);
        return !level.noCollision(dragon, below);
    }

    private double findNearestFreeY(ServerLevel level, double x, double z, double desiredY, boolean preferDown) {
        double yClamped = clampYForWorld(level, x, z, desiredY);

        int base = Mth.floor(yClamped);
        int min = Mth.floor(level.getMinBuildHeight() + 6.0);
        int max = Mth.floor(level.getMaxBuildHeight() - 2.0);

        if (level.dimensionType().hasCeiling()) {
            BlockPos col = BlockPos.containing(x, 0.0, z);
            int roofAirY = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, col).getY();
            max = Math.min(max, Mth.floor(roofAirY - 2.0));
        }

        base = Mth.clamp(base, min, max);

        for (int step = 0; step <= 64; step++) {
            int y1 = preferDown ? (base - step) : (base + step);
            int y2 = preferDown ? (base + step) : (base - step);

            if (y1 >= min && y1 <= max && canFitAt(level, x, y1, z)) {
                return y1;
            }
            if (step != 0 && y2 >= min && y2 <= max && canFitAt(level, x, y2, z)) {
                return y2;
            }
        }

        return yClamped;
    }
}
