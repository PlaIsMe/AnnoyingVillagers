package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class HerobrineProjectileCounterPortalGoal extends AbstractHerobrinePortalActionGoal {
    @Nullable
    private HerobrineUtil.ProjectileCounterPlan plan;

    public HerobrineProjectileCounterPortalGoal(HerobrinePortalSupportCaster supportCaster) {
        super(supportCaster, RigAnimationId.POINT_LEFT_HAND_TOWARD);
    }

    @Override
    protected boolean canStartAction() {
        if (this.supportCaster.getPortalActionCooldown() > 0 || !HerobrineUtil.canSpawnPortalPair(this.supportCaster)) return false;
        this.plan = HerobrineUtil.findProjectileCounterPlan(this.supportCaster);
        return this.plan != null;
    }

    @Override
    protected void performAction() {
        if (this.plan != null && HerobrineUtil.spawnProjectileCounterPortal(this.supportCaster, this.plan)) {
            this.supportCaster.markPortalSupport();
            this.supportCaster.setPortalActionCooldown();
        }
    }

    @Override
    protected int getActionTick() {
        return this.plan != null && this.plan.arrow() != null ? 1 : 3;
    }

    @Override
    protected LivingEntity getLookTarget() {
        return this.plan == null ? null : this.plan.attacker();
    }

    @Override
    protected void clearActionState() {
        this.plan = null;
    }
}
