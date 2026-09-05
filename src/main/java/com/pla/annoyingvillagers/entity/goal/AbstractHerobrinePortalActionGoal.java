package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public abstract class AbstractHerobrinePortalActionGoal extends Goal {
    protected final HerobrinePortalSupportCaster supportCaster;
    protected final Mob caster;
    private final RigAnimationId animationId;
    private int animationStartTick = -1;
    private boolean actionPerformed;

    protected AbstractHerobrinePortalActionGoal(HerobrinePortalSupportCaster supportCaster, RigAnimationId animationId) {
        this.supportCaster = supportCaster;
        this.caster = supportCaster.getPortalSupportMob();
        this.animationId = animationId;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public final boolean canUse() {
        return this.caster.level() instanceof ServerLevel
                && this.caster.isAlive()
                && !this.caster.isRemoved()
                && !this.caster.isDeadOrDying()
                && !this.caster.isNoAi()
                && !this.caster.isPassenger()
                && !RigStunController.isStunned(this.caster)
                && !RigAnimationController.hasActiveAnimation(this.caster)
                && this.supportCaster.canUseSupportPortalAction()
                && this.canStartAction();
    }

    @Override
    public final void start() {
        this.actionPerformed = false;
        this.caster.getNavigation().stop();
        this.caster.setAggressive(false);
        LivingEntity lookTarget = this.getLookTarget();
        if (lookTarget != null && lookTarget.isAlive()) this.caster.getLookControl().setLookAt(lookTarget, 30.0F, 30.0F);
        this.supportCaster.playPortalSupportAnimation(this.animationId, lookTarget);
        this.animationStartTick = RigAnimationController.getActiveAnimationStartTick(this.caster);
    }

    @Override
    public final boolean canContinueToUse() {
        return this.caster.isAlive() && !this.caster.isRemoved() && !this.caster.isDeadOrDying() && RigAnimationController.getActiveAnimationId(this.caster) == this.animationId;
    }

    @Override
    public final void tick() {
        this.caster.getNavigation().stop();
        LivingEntity lookTarget = this.getLookTarget();
        if (lookTarget != null && lookTarget.isAlive()) this.caster.getLookControl().setLookAt(lookTarget, 30.0F, 30.0F);
        if (this.actionPerformed) return;
        if (this.animationStartTick < 0) return;
        if (this.caster.tickCount - this.animationStartTick >= this.getActionTick()) {
            this.actionPerformed = true;
            this.performAction();
        }
    }

    @Override
    public void stop() {
        this.animationStartTick = -1;
        this.actionPerformed = false;
        this.clearActionState();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected int getActionTick() {
        return 5;
    }

    protected abstract boolean canStartAction();

    protected abstract void performAction();

    @Nullable
    protected LivingEntity getLookTarget() {
        return null;
    }

    protected void clearActionState() {
    }

}
