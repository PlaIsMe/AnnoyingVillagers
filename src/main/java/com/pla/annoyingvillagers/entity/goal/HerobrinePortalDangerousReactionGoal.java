package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.util.DangerousReactionAnimations;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.UUID;

public class HerobrinePortalDangerousReactionGoal extends Goal {
    private static final int PORTAL_CAST_TICK = 5;

    private final Mob mob;
    private final DangerousReaction dangerousReaction;
    private final HerobrinePortalSupportCaster supportCaster;
    private UUID lastTargetUuid;
    private int lastDangerousAnimationStartTick = Integer.MIN_VALUE;
    private Object lastDangerousAnimationKey;
    private int castAnimationStartTick = -1;
    private boolean reactionPerformed;

    public HerobrinePortalDangerousReactionGoal(Mob mob, DangerousReaction dangerousReaction, HerobrinePortalSupportCaster supportCaster) {
        this.mob = mob;
        this.dangerousReaction = dangerousReaction;
        this.supportCaster = supportCaster;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public Mob getMob() {
        return this.mob;
    }

    @Override
    public boolean canUse() {
        if (!this.canReact()) return false;
        LivingEntity target = this.mob.getTarget();
        if (target == null) return false;

        int startTick = this.getDangerousAnimationStartTick(target);
        Object animationKey = this.getDangerousAnimationKey(target);
        return startTick >= 0 && animationKey != null && (!target.getUUID().equals(this.lastTargetUuid)
                || startTick != this.lastDangerousAnimationStartTick
                || !animationKey.equals(this.lastDangerousAnimationKey));
    }

    @Override
    public void start() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !this.canReact()) return;

        this.lastTargetUuid = target.getUUID();
        this.lastDangerousAnimationStartTick = this.getDangerousAnimationStartTick(target);
        this.lastDangerousAnimationKey = this.getDangerousAnimationKey(target);
        this.reactionPerformed = false;
        this.castAnimationStartTick = -1;

        boolean canUsePortal = this.supportCaster.canUseSupportPortalAction()
                && this.supportCaster.getPortalActionCooldown() <= 0
                && HerobrineUtil.canSpawnPortalPair(this.supportCaster)
                && !DangerousReactionAnimations.isBusy(this.mob);

        if (!canUsePortal) {
            this.reactionPerformed = true;
            this.dangerousReaction.performDangerousReaction(this.mob);
            return;
        }

        this.mob.getNavigation().stop();
        this.mob.setAggressive(false);
        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        this.supportCaster.playPortalSupportAnimation(RigAnimationId.POINT_LEFT_HAND_TOWARD, target);
        this.castAnimationStartTick = DangerousReactionAnimations.animationStartTick(this.mob);

        if (this.castAnimationStartTick < 0) {
            this.reactionPerformed = true;
            this.dangerousReaction.performDangerousReaction(this.mob);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.reactionPerformed
                && this.mob.isAlive()
                && !this.mob.isRemoved()
                && DangerousReactionAnimations.isPlaying(this.mob, RigAnimationId.POINT_LEFT_HAND_TOWARD);
    }

    @Override
    public void tick() {
        this.mob.getNavigation().stop();
        LivingEntity target = this.mob.getTarget();
        if (target != null && target.isAlive()) this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        if (this.reactionPerformed || this.castAnimationStartTick < 0 || this.mob.tickCount - this.castAnimationStartTick < PORTAL_CAST_TICK) return;

        if (HerobrineUtil.spawnSelfDangerousReactionPortal(this.supportCaster)) {
            this.supportCaster.markPortalSupport();
            this.supportCaster.setPortalActionCooldown();
        }
        this.reactionPerformed = true;
        this.dangerousReaction.performCommittedDangerousReaction(this.mob);
    }

    @Override
    public void stop() {
        this.castAnimationStartTick = -1;
        this.reactionPerformed = false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected boolean canReact() {
        return DangerousReaction.canReact(this.mob);
    }

    protected int getDangerousAnimationStartTick(LivingEntity target) {
        return DangerousReactionAnimations.animationStartTick(target);
    }

    protected Object getDangerousAnimationKey(LivingEntity target) {
        return DangerousReactionAnimations.isDangerous(target) ? DangerousReactionAnimations.animationKey(target) : null;
    }
}
