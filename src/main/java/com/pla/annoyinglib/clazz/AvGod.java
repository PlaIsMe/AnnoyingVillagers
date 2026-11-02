package com.pla.annoyinglib.clazz;

import com.pla.annoyinglib.util.CommonGoals;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/** Shared god NPC base; inherits the library's rig combat, inventory and recovery. */
public abstract class AvGod extends AVNpc {
    protected AvGod(EntityType<? extends AvGod> type, Level level) {
        super(type, level);
        setMaxUpStep(1.0F);
        setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        CommonGoals.registerGoalForNeutralNpc(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
}
