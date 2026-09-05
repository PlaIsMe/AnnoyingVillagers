package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.HerobrineUtil;

public class HerobrineLowCloneSupportGoal extends AbstractHerobrinePortalActionGoal {
    public HerobrineLowCloneSupportGoal(HerobrinePortalSupportCaster supportCaster) {
        super(supportCaster, RigAnimationId.PORTAL_SUMMON);
    }

    @Override
    protected int getActionTick() {
        return 20;
    }

    @Override
    protected boolean canStartAction() {
        return HerobrineUtil.canSummonLowCloneSupport(this.supportCaster);
    }

    @Override
    protected void performAction() {
        if (!HerobrineUtil.summonLowCloneSupport(this.supportCaster)) this.supportCaster.setLowCloneSupportCooldown();
    }
}
