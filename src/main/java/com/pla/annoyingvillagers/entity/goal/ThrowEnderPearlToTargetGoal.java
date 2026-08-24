package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ThrowEnderPearlToTargetGoal extends Goal {
    private static final double MIN_THROW_DISTANCE_SQR = 100.0D;
    private static final double MAX_THROW_DISTANCE_SQR = 48.0D * 48.0D;
    private static final int SEARCH_INTERVAL_TICKS = 10;

    private final AVNpc avNpc;
    private Vec3 pearlTarget;
    private long nextSearchTick;

    public ThrowEnderPearlToTargetGoal(AVNpc avNpc) {
        this.avNpc = avNpc;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!(this.avNpc.level() instanceof ServerLevel serverLevel)
                || !this.avNpc.isAlive()
                || this.avNpc.isRemoved()
                || this.avNpc.isDeadOrDying()
                || this.avNpc.isNoAi()
                || this.avNpc.isPassenger()
                || this.avNpc.isHealing()
                || RigAnimationController.hasActiveProfileAttack(this.avNpc)
                || this.avNpc.getEnderPearlCooldown() > 0
                || !InventoryUtils.hasItem(this.avNpc, Items.ENDER_PEARL)) {
            return false;
        }

        long gameTime = serverLevel.getGameTime();
        if (gameTime < this.nextSearchTick) {
            return false;
        }

        this.nextSearchTick = gameTime + SEARCH_INTERVAL_TICKS + this.avNpc.getRandom().nextInt(8);
        LivingEntity target = this.avNpc.getTarget();
        if (target == null || !target.isAlive() || target.isRemoved() || target.isDeadOrDying()) {
            return false;
        }

        double distanceSqr = this.avNpc.distanceToSqr(target);
        if (distanceSqr < MIN_THROW_DISTANCE_SQR || distanceSqr > MAX_THROW_DISTANCE_SQR || !this.avNpc.hasLineOfSight(target)) {
            return false;
        }

        this.pearlTarget = target.position().add(0.0D, target.getBbHeight() * 0.35D, 0.0D);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        if (this.pearlTarget == null) {
            return;
        }

        this.avNpc.getNavigation().stop();
        this.avNpc.getLookControl().setLookAt(this.pearlTarget.x, this.pearlTarget.y, this.pearlTarget.z, 60.0F, 60.0F);
        RigAnimationController.lockProfileAttacksFor(this.avNpc, RigAnimationId.POINT_LEFT_HAND_TOWARD);
        RigAnimationController.play(this.avNpc, RigAnimationId.POINT_LEFT_HAND_TOWARD);
        if (CombatBehaviour.throwEnderPearlAt(this.avNpc, this.pearlTarget)) {
            this.avNpc.setEnderPearlCooldown();
        }
        this.pearlTarget = null;
    }
}
