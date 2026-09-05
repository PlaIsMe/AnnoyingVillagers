package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PortalApproachGoal extends Goal {
    private static final String PORTAL_APPROACH_COOLDOWN_TAG = "AnnoyingVillagersPortalApproachCooldown";
    private static final int PORTAL_APPROACH_COOLDOWN_TICKS = 10 * 20;
    private final Mob mob;
    private HerobrineUtil.PortalRoute route;

    public PortalApproachGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof ReaperHerobrineEntity reaper && reaper.isSecondFormDragonRider()) return false;
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (this.mob.getPersistentData().getLong(PORTAL_APPROACH_COOLDOWN_TAG) > this.mob.level().getGameTime()) {
            return false;
        }

        HerobrineUtil.PortalRoute foundRoute = HerobrineUtil.findRouteToTarget(this.mob, target);
        if (foundRoute == null) {
            return false;
        }

        PortalEntity entrance = foundRoute.entrance();
        if (this.mob.distanceToSqr(entrance) < 3.0D) {
            return false;
        }

        this.route = foundRoute;
        return true;
    }

    @Override
    public void start() {
        this.mob.getPersistentData().putLong(PORTAL_APPROACH_COOLDOWN_TAG, this.mob.level().getGameTime() + PORTAL_APPROACH_COOLDOWN_TICKS);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob instanceof ReaperHerobrineEntity reaper && reaper.isSecondFormDragonRider()) return false;
        LivingEntity target = this.mob.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }
        if (this.route == null || this.route.entrance().isRemoved() || this.route.exit().isRemoved()) {
            return false;
        }

        HerobrineUtil.PortalRoute foundRoute = HerobrineUtil.findRouteToTarget(this.mob, target);
        if (foundRoute == null) {
            return false;
        }

        this.route = foundRoute;
        return this.mob.distanceToSqr(this.route.entrance()) > 1.6D;
    }

    @Override
    public void tick() {
        if (this.route == null) {
            return;
        }

        Vec3 center = this.route.entrance().getPortalCenter();
        this.mob.getNavigation().moveTo(center.x, this.route.entrance().getY(), center.z, 1.45D);
        this.mob.getLookControl().setLookAt(center.x, center.y, center.z, 30.0F, 30.0F);
    }

    @Override
    public void stop() {
        this.route = null;
    }
}
