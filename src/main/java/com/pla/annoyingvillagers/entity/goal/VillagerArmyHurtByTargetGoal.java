package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.VillagerArmyEntity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

public class VillagerArmyHurtByTargetGoal extends HurtByTargetGoal {
    private static final double ALERT_RANGE_Y = 40.0D;

    private final VillagerArmyEntity mob;

    public VillagerArmyHurtByTargetGoal(VillagerArmyEntity mob) {
        super(mob);
        this.mob = mob;
        this.setAlertOthers();
    }

    @Override
    protected void alertOthers() {
        LivingEntity target = this.mob.getLastHurtByMob();
        if (target == null) {
            return;
        }

        double followRange = this.getFollowDistance();
        AABB alertBox = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(followRange, ALERT_RANGE_Y, followRange);
        this.mob.level().getEntitiesOfClass(VillagerArmyEntity.class, alertBox, EntitySelector.NO_SPECTATORS).stream()
                .filter(other -> other != this.mob)
                .filter(VillagerArmyEntity::isAlive)
                .filter(other -> other.getTarget() == null)
                .filter(other -> !other.isAlliedTo(target))
                .forEach(other -> this.alertOther(other, target));
    }
}
