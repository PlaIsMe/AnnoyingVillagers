package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.entity.goal.VillagerArmyHurtByTargetGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class VillagerArmyEntity extends AVNpc {
    protected VillagerArmyEntity(EntityType<? extends VillagerArmyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(1, new VillagerArmyHurtByTargetGoal(this));
    }
}
