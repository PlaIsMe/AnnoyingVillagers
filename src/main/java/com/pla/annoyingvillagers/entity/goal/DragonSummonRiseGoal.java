package com.pla.annoyingvillagers.entity.goal;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DragonSummonRiseGoal extends Goal {
    private static final double RISE_SPEED_MODIFIER = 1.0D;
    private static final double TARGET_Y_THRESHOLD = 2.0D;

    private final HerobrineDragonEntity dragon;

    public DragonSummonRiseGoal(HerobrineDragonEntity dragon) {
        this.dragon = dragon;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return dragon.isSummonRising()
                && dragon.getSummonRiseTarget() != null
                && !dragon.isPassenger();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        prepareFlight();
    }

    @Override
    public void tick() {
        Vec3 target = dragon.getSummonRiseTarget();
        if (target == null) {
            dragon.stopSummonRise();
            return;
        }

        prepareFlight();

        dragon.aimBodyAndHeadAt(target, 10.0F, 18.0F);
        dragon.getMoveControl().setWantedPosition(target.x, target.y, target.z, RISE_SPEED_MODIFIER);

        dragon.tickSummonRiseState(dragon.getY() >= target.y - TARGET_Y_THRESHOLD);
    }

    @Override
    public void stop() {
        dragon.getNavigation().stop();
    }

    private void prepareFlight() {
        dragon.getNavigation().stop();
        dragon.setNoGravity(true);
        if (!dragon.isFlying() && dragon.canFly()) {
            dragon.liftOff();
        }
        dragon.setFlying(true);
        dragon.setNavigation(true);
    }
}
