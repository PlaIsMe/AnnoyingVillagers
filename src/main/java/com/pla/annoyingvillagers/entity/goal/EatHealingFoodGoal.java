package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class EatHealingFoodGoal extends Goal {
    private static final float HEALING_HEALTH_RATIO = 0.7F;

    private final AVNpc avNpc;

    public EatHealingFoodGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.avNpc.level() instanceof ServerLevel
                && this.avNpc.isAlive()
                && !this.avNpc.isNoAi()
                && !this.avNpc.isPassenger()
                && !this.avNpc.isHealing()
                && this.avNpc.getGapCooldown() <= 0
                && this.avNpc.getHealth() < this.avNpc.getMaxHealth() * HEALING_HEALTH_RATIO
                && InventoryUtils.hasHealingFood(this.avNpc);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        this.avNpc.getNavigation().stop();
        if (this.avNpc.eatHealingFoodFromInventory()) {
            this.avNpc.setGapCooldown();
        }
    }
}
