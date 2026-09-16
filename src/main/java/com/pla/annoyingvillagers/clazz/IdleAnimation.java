package com.pla.annoyingvillagers.clazz;

import com.pla.annoyingvillagers.rig.RigAnimationId;

public enum IdleAnimation {
    SIT,
    LAY,
    PUSH_UP,
    SLIGHT,
    SLEEP,
    LAY_RELAX_EMOTE,
    ONE_ARM_LAY_EMOTE,
    SIT_NO_WEAPON_EMOTE,
    SORROW_EMOTE,
    FUN_JUMP_EMOTE,
    JUMP_EMOTE,
    PRONE_EMOTE;

    public RigAnimationId rigAnimation() {
        return RigAnimationId.valueOf(name());
    }
}
