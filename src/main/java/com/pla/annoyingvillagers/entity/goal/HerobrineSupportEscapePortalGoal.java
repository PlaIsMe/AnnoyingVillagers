package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class HerobrineSupportEscapePortalGoal extends AbstractHerobrinePortalActionGoal {
    @Nullable
    private LivingEntity support;

    public HerobrineSupportEscapePortalGoal(HerobrinePortalSupportCaster supportCaster) {
        super(supportCaster, RigAnimationId.POINT_LEFT_HAND_TOWARD);
    }

    @Override
    protected boolean canStartAction() {
        if (this.supportCaster.getPortalActionCooldown() > 0 || !HerobrineUtil.canSpawnPortalPair(this.supportCaster)) return false;
        this.support = HerobrineUtil.findDangerousReactionSupport(this.supportCaster);
        return this.support != null;
    }

    @Override
    protected void performAction() {
        if (this.support != null && HerobrineUtil.spawnDangerousReactionSupportPortal(this.supportCaster, this.support)) {
            this.supportCaster.markPortalSupport();
            this.supportCaster.setPortalActionCooldown();
        }
    }

    @Override
    protected int getActionTick() {
        return 2;
    }

    @Override
    protected LivingEntity getLookTarget() {
        return this.support;
    }

    @Override
    protected void clearActionState() {
        this.support = null;
    }
}
