package com.pla.annoyingvillagers.rig;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.rig.RigAnimationSpec.RigTimedAnimationHook;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class RigAnimationSpecs {
    private static final Map<RigAnimationId, RigAnimationSpec> SPECS = new EnumMap<>(RigAnimationId.class);

    static {
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO1, 12, 0, 2));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO2, 12, 1, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO3, 12, 1, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO4, 12, 1, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DASH, 13, 3, 5));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AIRSLASH, 13, 7, 10));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWEEPING_EDGE,
                20,
                3,
                6
        ));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_MOUNT_ATTACK, 12, 2, 6, 2.25D));

        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO1, 12, 2, 4));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO2, 12, 2, 4));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO3, 15, 5, 7));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_DASH, 15, 1, 6));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AIRSLASH, 13, 7, 10));
        put(RigAnimationSpec.ultimateAttack(
                RigAnimationId.DANCING_EDGE,
                25,
                RigAttackWindow.of(5, 8),
                RigAttackWindow.of(8, 10),
                RigAttackWindow.of(12, 14)
        ));

        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_FORWARD, 13));
        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_BACKWARD, 13));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_FORWARD, 7));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_BACKWARD, 7));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_LEFT, 7));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_RIGHT, 7));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.JUMP, 10));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_AIM_DOWN, 14, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_AIM_MID, 14, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_AIM_UP, 14, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_SHOT_DOWN, 2, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_SHOT_MID, 2, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.BOW_SHOT_UP, 2, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_WAKEUP_LEFT, 12));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.KNOCKDOWN_WAKEUP_RIGHT, 12));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EAT_OFFHAND, 32, RigAnimationPlaybackType.LEFT_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.EAT_MAINHAND, 32, RigAnimationPlaybackType.MAIN_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.THROW_ENDER_PEARL, 10, RigAnimationPlaybackType.BOTH_HAND));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.SHIELD_OFFHAND, 56, RigAnimationPlaybackType.UPPER_BODY));
        put(RigAnimationSpec.nonDamaging(RigAnimationId.HIT_SHIELD_OFFHAND, 4, RigAnimationPlaybackType.UPPER_BODY));
    }

    private RigAnimationSpecs() {
    }

    public static RigAnimationSpec get(RigAnimationId animationId) {
        RigAnimationSpec spec = SPECS.get(animationId);
        if (spec == null) {
            throw new IllegalArgumentException("Missing rig animation spec for " + animationId);
        }

        return spec;
    }

    private static void put(RigAnimationSpec spec) {
        SPECS.put(spec.animationId(), spec);
    }
}
