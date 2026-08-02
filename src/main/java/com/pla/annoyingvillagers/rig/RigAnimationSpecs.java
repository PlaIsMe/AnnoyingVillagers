package com.pla.annoyingvillagers.rig;

import java.util.EnumMap;
import java.util.Map;

public final class RigAnimationSpecs {
    private static final Map<RigAnimationId, RigAnimationSpec> SPECS = new EnumMap<>(RigAnimationId.class);

    static {
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO1, 12, 0, 2));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO2, 12, 1, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO3, 12, 1, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO4, 12, 1, 3));
        put(RigAnimationSpec.dashAttack(RigAnimationId.SWORD_DASH, 13, 3, 5, 2.0D));
        put(RigAnimationSpec.jumpAttack(RigAnimationId.SWORD_AIRSLASH, 13, 7, 10, 0.42D));
        put(RigAnimationSpec.ultimateAttack(RigAnimationId.SWEEPING_EDGE,
                20,
                3,
                6,
                RigMovementType.LUNGE,
                6.5D,
                0.0D
        ));

        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO1, 12, 2, 4));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO2, 12, 2, 4));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO3, 15, 5, 7));
        put(RigAnimationSpec.dashAttack(RigAnimationId.SWORD_DUAL_DASH, 15, 1, 6, 4.11D));
        put(RigAnimationSpec.jumpAttack(RigAnimationId.SWORD_DUAL_AIRSLASH, 13, 7, 10, 0.42D));
        put(RigAnimationSpec.ultimateAttack(
                RigAnimationId.DANCING_EDGE,
                25,
                RigMovementType.LUNGE,
                3.6D,
                0.0D,
                RigAttackWindow.of(5, 8),
                RigAttackWindow.of(8, 10),
                RigAttackWindow.of(12, 14)
        ));

        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_FORWARD, 13, RigMovementType.MOVE_FORWARD, 4.2D));
        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_BACKWARD, 13, RigMovementType.MOVE_BACKWARD, 4.2D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_FORWARD, 7, RigMovementType.MOVE_FORWARD, 4.0D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_BACKWARD, 7, RigMovementType.MOVE_BACKWARD, 4.0D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_LEFT, 7, RigMovementType.MOVE_LEFT, 4.0D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_RIGHT, 7, RigMovementType.MOVE_RIGHT, 4.0D));
        put(RigAnimationSpec.movementOnly(RigAnimationId.JUMP, 10, RigMovementType.JUMP, 0.0D, 0.42D));
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
