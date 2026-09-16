package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.util.DangerousReactionAnimations;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.UUID;

public class DangerousReactionGoal extends Goal {
    private final Mob mob;
    private final DangerousReaction dangerousReaction;
    private UUID lastTargetUuid;
    private int lastDangerousAnimationStartTick = Integer.MIN_VALUE;
    private Object lastDangerousAnimationId;

    public DangerousReactionGoal(Mob mob, DangerousReaction dangerousReaction) {
        this.mob = mob;
        this.dangerousReaction = dangerousReaction;
    }

    @Override
    public boolean canUse() {
        if (!DangerousReaction.canReact(this.mob)) return false;
        LivingEntity target = this.mob.getTarget();
        if (target == null) return false;

        int startTick = DangerousReactionAnimations.animationStartTick(target);
        Object animationId = DangerousReactionAnimations.animationKey(target);
        return startTick >= 0 && animationId != null && (!target.getUUID().equals(this.lastTargetUuid)
                || startTick != this.lastDangerousAnimationStartTick
                || !animationId.equals(this.lastDangerousAnimationId));
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        LivingEntity target = this.mob.getTarget();
        if (target == null || !DangerousReaction.canReact(this.mob)) return;
        this.lastTargetUuid = target.getUUID();
        this.lastDangerousAnimationStartTick = DangerousReactionAnimations.animationStartTick(target);
        this.lastDangerousAnimationId = DangerousReactionAnimations.animationKey(target);
        this.dangerousReaction.performDangerousReaction(this.mob);
    }
}
