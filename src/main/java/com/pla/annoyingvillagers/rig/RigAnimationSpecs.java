package com.pla.annoyingvillagers.rig;

import java.util.EnumMap;
import java.util.Map;

public final class RigAnimationSpecs {
    private static final Map<RigAnimationId, RigAnimationSpec> SPECS = new EnumMap<>(RigAnimationId.class);

    static {
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO1, 12, 1));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO2, 12, 2));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO3, 12, 2));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_AUTO4, 12, 2));
        put(RigAnimationSpec.dashAttack(RigAnimationId.SWORD_DASH, 13, 4, 1.5D));
        put(RigAnimationSpec.jumpAttack(RigAnimationId.SWORD_AIRSLASH, 13, 4, 0.42D));
        put(RigAnimationSpec.ultimateAttack(RigAnimationId.SWEEPING_EDGE, 18, 5, RigMovementType.LUNGE, 1.1D, 0.0D));

        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO1, 13, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO2, 13, 3));
        put(RigAnimationSpec.normalAttack(RigAnimationId.SWORD_DUAL_AUTO3, 13, 6));
        put(RigAnimationSpec.dashAttack(RigAnimationId.SWORD_DUAL_DASH, 15, 4, 4.1D));
        put(RigAnimationSpec.jumpAttack(RigAnimationId.SWORD_DUAL_AIRSLASH, 12, 4, 0.42D));
        put(RigAnimationSpec.ultimateAttack(RigAnimationId.DANCING_EDGE, 20, 8, RigMovementType.LUNGE, 1.8D, 0.0D));

        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_FORWARD, 10, RigMovementType.ROLL_FORWARD, 2.8D));
        put(RigAnimationSpec.rolling(RigAnimationId.ROLL_BACKWARD, 10, RigMovementType.ROLL_BACKWARD, 2.8D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_FORWARD, 8, RigMovementType.ROLL_FORWARD, 1.34D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_BACKWARD, 8, RigMovementType.ROLL_BACKWARD, 1.34D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_LEFT, 8, RigMovementType.ROLL_LEFT, 1.5D));
        put(RigAnimationSpec.rolling(RigAnimationId.STEP_RIGHT, 8, RigMovementType.ROLL_RIGHT, 1.4D));
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
