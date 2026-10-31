package com.pla.annoyinglib.entity.goal;

import com.pla.annoyinglib.entity.BbqEntity;
import com.pla.annoyinglib.entity.BlueDemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class EscapeAvoidGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
    private final BbqEntity mob;

    public EscapeAvoidGoal(BbqEntity mob, Class<T> avoidClass, float maxDistance, double walkSpeed, double sprintSpeed) {
        super(mob, avoidClass, maxDistance, walkSpeed, sprintSpeed);
        this.mob = mob;
    }

    private boolean isRealEscapeThreat(@Nullable LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (target == this.mob) {
            return false;
        }

        if (target instanceof BlueDemonEntity) {
            return false;
        }

        if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canUse() {
        if (!this.mob.isEscapeMode() || this.mob.getSauceLeader() != null) {
            return false;
        }

        if (!super.canUse()) {
            return false;
        }

        return this.isRealEscapeThreat(this.toAvoid);
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.mob.isEscapeMode() || this.mob.getSauceLeader() != null) {
            return false;
        }

        return super.canContinueToUse() && this.isRealEscapeThreat(this.toAvoid);
    }
}