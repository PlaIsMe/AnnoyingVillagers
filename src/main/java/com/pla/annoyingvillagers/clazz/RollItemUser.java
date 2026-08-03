package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.Mob;

public interface RollItemUser {
    boolean canRollItem();

    void rollItem();

    default RigAnimationId selectRollItemAnimation(Mob self) {
        return self.getRandom().nextBoolean()
                ? RigAnimationId.ROLL_BACKWARD
                : RigAnimationId.STEP_BACKWARD;
    }

    default int getRollItemSwitchDelayTicks(RigAnimationId animationId) {
        return 4;
    }
}
