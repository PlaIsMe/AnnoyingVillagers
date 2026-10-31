package com.pla.annoyinglib.entity.goal;

import com.pla.annoyinglib.entity.BbqEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FollowEscapeLeaderGoal extends Goal {
    private final BbqEntity mob;

    public FollowEscapeLeaderGoal(BbqEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.mob.isEscapeMode() && this.mob.getSauceLeader() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isEscapeMode() && this.mob.getSauceLeader() != null;
    }

    @Override
    public void tick() {
        BbqEntity leader = this.mob.getSauceLeader();
        if (leader == null) {
            return;
        }

        if (this.mob.distanceToSqr(leader) > 324.0D) {
            this.mob.teleportTo(leader.getX(), leader.getY(), leader.getZ());
            return;
        }

        Vec3 forward = leader.getDeltaMovement();
        forward = new Vec3(forward.x, 0.0D, forward.z);

        if (forward.lengthSqr() < 1.0E-4D) {
            float yawRad = leader.getYRot() * ((float)Math.PI / 180.0F);
            forward = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
        } else {
            forward = forward.normalize();
        }

        double followDistance = switch (this.mob.getSauceType()) {
            case HONEY_MUSTARD_SAUCE -> 1.2D;
            case SOY_SAUCE -> 2.4D;
            case SWEET_ONION_SAUCE -> 3.6D;
            default -> 1.2D;
        };

        Vec3 desired = leader.position().subtract(forward.scale(followDistance));
        this.mob.getLookControl().setLookAt(leader, 30.0F, 30.0F);

        if (this.mob.isEscapeFlying()) {
            double y = leader.getY() + this.mob.getEscapeFlightHeight();

            if (this.mob.getSauceType() == com.pla.annoyinglib.clazz.SauceType.SWEET_ONION_SAUCE) {
                y += 0.35D;
            }

            this.mob.getNavigation().stop();
            this.mob.setNoGravity(true);
            this.mob.fallDistance = 0.0F;
            this.mob.moveEscapeAerialTowards(desired.x, y, desired.z, 0.20D, 0.88D);
        } else {
            this.mob.setNoGravity(false);
            this.mob.fallDistance = 0.0F;

            if (this.mob.distanceToSqr(desired.x, leader.getY(), desired.z) > 1.0D) {
                this.mob.getNavigation().moveTo(desired.x, leader.getY(), desired.z, 2.0D);
            } else {
                this.mob.getNavigation().stop();
            }
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.mob.setNoGravity(false);
        this.mob.fallDistance = 0.0F;
    }
}