package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Nullable;

/**
 * Animation backend hooks for shared combat goals. Rig IDs are logical action
 * tokens; the Epic Fight compatibility mod replaces playback and state queries.
 * This class deliberately has no dependency on Epic Fight.
 */
public abstract class AnimatedMobGoal extends Goal {
    public final Mob animationMob;

    protected AnimatedMobGoal(Mob mob) {
        this.animationMob = mob;
    }

    public boolean isAnimationStunned() {
        return RigStunController.isStunned(this.animationMob);
    }

    public boolean isAnimationBusy() {
        return RigAnimationController.hasActiveAnimation(this.animationMob);
    }

    public void playGoalAnimation(RigAnimationId animationId, @Nullable LivingEntity target) {
        RigAnimationController.play(this.animationMob, RigAnimationSpecs.get(animationId), target);
    }

    public boolean isGoalAnimationPlaying(RigAnimationId animationId) {
        return RigAnimationController.getActiveAnimationId(this.animationMob) == animationId;
    }

    /** True when gameplay timing is owned by the replacement animation's events. */
    public boolean usesAnimationEvents() {
        return false;
    }

    /** Releases backend-owned combat locks when the goal ends or is interrupted. */
    public void finishGoalAnimation() {
    }
}
