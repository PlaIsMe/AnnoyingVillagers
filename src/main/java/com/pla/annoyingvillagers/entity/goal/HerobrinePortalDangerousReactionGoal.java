package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.clazz.HerobrinePortalSupportCaster;
import com.pla.annoyingvillagers.rig.RigAnimationController;
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
    private RigAnimationId lastDangerousAnimationId;
    private int castAnimationStartTick = -1;
    private boolean reactionPerformed;

    public HerobrinePortalDangerousReactionGoal(Mob mob, DangerousReaction dangerousReaction, HerobrinePortalSupportCaster supportCaster) {
        this.mob = mob;
        this.dangerousReaction = dangerousReaction;
        this.supportCaster = supportCaster;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!DangerousReaction.canReact(this.mob)) return false;
        LivingEntity target = this.mob.getTarget();
        if (!(target instanceof Mob targetMob)) return false;

        int startTick = RigAnimationController.getActiveAnimationStartTick(targetMob);
        RigAnimationId animationId = RigAnimationController.getActiveAnimationId(targetMob);
        return startTick >= 0 && animationId != null && (!target.getUUID().equals(this.lastTargetUuid)
                || startTick != this.lastDangerousAnimationStartTick
                || animationId != this.lastDangerousAnimationId);
    }

    @Override
    public void start() {
        LivingEntity target = this.mob.getTarget();
        if (!(target instanceof Mob targetMob) || !DangerousReaction.canReact(this.mob)) return;

        this.lastTargetUuid = target.getUUID();
        this.lastDangerousAnimationStartTick = RigAnimationController.getActiveAnimationStartTick(targetMob);
        this.lastDangerousAnimationId = RigAnimationController.getActiveAnimationId(targetMob);
        this.reactionPerformed = false;
        this.castAnimationStartTick = -1;

        boolean canUsePortal = this.supportCaster.canUseSupportPortalAction()
                && this.supportCaster.getPortalActionCooldown() <= 0
                && HerobrineUtil.canSpawnPortalPair(this.supportCaster)
                && !RigAnimationController.hasActiveAnimation(this.mob);

        if (!canUsePortal) {
            this.reactionPerformed = true;
            this.dangerousReaction.performDangerousReaction(this.mob);
            return;
        }

        this.mob.getNavigation().stop();
        this.mob.setAggressive(false);
        this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        this.supportCaster.playPortalSupportAnimation(RigAnimationId.POINT_LEFT_HAND_TOWARD, target);
        this.castAnimationStartTick = RigAnimationController.getActiveAnimationStartTick(this.mob);

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
                && RigAnimationController.getActiveAnimationId(this.mob) == RigAnimationId.POINT_LEFT_HAND_TOWARD;
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
}
