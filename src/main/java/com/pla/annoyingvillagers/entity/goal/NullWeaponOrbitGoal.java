package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.NullWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class NullWeaponOrbitGoal extends Goal {
    private static final int TELEPORT_INTERVAL = 10;
    private static final double HORIZONTAL_RANGE = 4.0D;
    private static final double MIN_Y_OFFSET = -1.25D;
    private static final double MAX_Y_OFFSET = 2.75D;

    private final NullWeapon weapon;

    public NullWeaponOrbitGoal(NullWeapon weapon) {
        this.weapon = weapon;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.weapon.getOrbitOwner();
        return !this.weapon.isReleased() && owner != null && owner.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.teleportAroundOwner();
    }

    @Override
    public void tick() {
        if (this.weapon.tickCount % TELEPORT_INTERVAL == 0) this.teleportAroundOwner();
    }

    @Override
    public void stop() {
        this.weapon.getNavigation().stop();
        this.weapon.setDeltaMovement(Vec3.ZERO);
    }

    private void teleportAroundOwner() {
        LivingEntity owner = this.weapon.getOrbitOwner();
        if (owner == null || !owner.isAlive()) return;

        this.weapon.teleportRandomlyAround(owner, HORIZONTAL_RANGE, MIN_Y_OFFSET, MAX_Y_OFFSET);
    }
}
