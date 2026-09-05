package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class HerobrineSupportApproachPortalGoal extends AbstractHerobrinePortalActionGoal {
    @Nullable
    private HerobrineUtil.ApproachPortalPlan plan;

    public HerobrineSupportApproachPortalGoal(HerobrinePortalSupportCaster supportCaster) {
        super(supportCaster, RigAnimationId.POINT_LEFT_HAND_TOWARD);
    }

    @Override
    protected boolean canStartAction() {
        if (this.supportCaster.getPortalActionCooldown() > 0 || !HerobrineUtil.canSpawnPortalPair(this.supportCaster)) return false;
        this.plan = HerobrineUtil.findApproachPortalPlan(this.supportCaster);
        return this.plan != null;
    }

    @Override
    protected void performAction() {
        if (this.plan != null && HerobrineUtil.spawnApproachPortal(this.supportCaster, this.plan)) {
            this.supportCaster.markPortalSupport();
            this.supportCaster.setPortalActionCooldown();
        }
    }

    @Override
    protected LivingEntity getLookTarget() {
        return this.plan == null ? null : this.plan.support();
    }

    @Override
    protected void clearActionState() {
        this.plan = null;
    }
}
