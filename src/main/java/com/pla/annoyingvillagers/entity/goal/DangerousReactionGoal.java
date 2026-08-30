package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.UUID;

public class DangerousReactionGoal extends Goal {
    private final Mob mob;
    private final DangerousReaction dangerousReaction;
    private UUID lastTargetUuid;
    private int lastDangerousAnimationStartTick = Integer.MIN_VALUE;
    private RigAnimationId lastDangerousAnimationId;

    public DangerousReactionGoal(Mob mob, DangerousReaction dangerousReaction) {
        this.mob = mob;
        this.dangerousReaction = dangerousReaction;
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
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        LivingEntity target = this.mob.getTarget();
        if (!(target instanceof Mob targetMob) || !DangerousReaction.canReact(this.mob)) return;
        this.lastTargetUuid = target.getUUID();
        this.lastDangerousAnimationStartTick = RigAnimationController.getActiveAnimationStartTick(targetMob);
        this.lastDangerousAnimationId = RigAnimationController.getActiveAnimationId(targetMob);
        this.dangerousReaction.performDangerousReaction(this.mob);
    }
}
