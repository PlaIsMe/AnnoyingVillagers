package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.CombatBehaviour;
import com.pla.annoyingvillagers.util.InventoryUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class RandomEnderPearlEscapeGoal extends Goal {
    private static final int SEARCH_INTERVAL_TICKS = 12;
    private static final double CLOSE_ESCAPE_DISTANCE_SQR = 25.0D;
    private static final double RANDOM_REPOSITION_CHANCE = 0.08D;
    private static final double LOW_HEALTH_RATIO = 0.45D;

    private final AVNpc avNpc;
    private Vec3 pearlTarget;
    private long nextSearchTick;

    public RandomEnderPearlEscapeGoal(AVNpc avNpc) {
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
                || this.avNpc.isInWater()
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

        boolean closeThreat = this.avNpc.distanceToSqr(target) <= CLOSE_ESCAPE_DISTANCE_SQR;
        boolean lowHealth = this.avNpc.getHealth() <= this.avNpc.getMaxHealth() * LOW_HEALTH_RATIO;
        if (!closeThreat && !lowHealth && this.avNpc.getRandom().nextDouble() > RANDOM_REPOSITION_CHANCE) {
            return false;
        }

        this.pearlTarget = this.findAwayTarget(target);
        return this.pearlTarget != null;
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
        RigAnimationController.play(this.avNpc, RigAnimationId.THROW_ENDER_PEARL);
        if (CombatBehaviour.throwEnderPearlAt(this.avNpc, this.pearlTarget)) {
            this.avNpc.setEnderPearlCooldown();
        }
        this.pearlTarget = null;
    }

    private Vec3 findAwayTarget(LivingEntity threat) {
        Vec3 target = DefaultRandomPos.getPosAway(this.avNpc, 14, 6, threat.position());
        if (target != null) {
            return target.add(0.0D, 1.0D, 0.0D);
        }

        Vec3 away = this.avNpc.position().subtract(threat.position());
        if (away.lengthSqr() < 1.0E-4D) {
            away = Vec3.directionFromRotation(0.0F, this.avNpc.getYRot());
        }
        away = new Vec3(away.x, 0.0D, away.z);
        if (away.lengthSqr() < 1.0E-4D) {
            away = new Vec3(0.0D, 0.0D, 1.0D);
        }

        return this.avNpc.position().add(away.normalize().scale(14.0D)).add(0.0D, 1.5D, 0.0D);
    }
}
