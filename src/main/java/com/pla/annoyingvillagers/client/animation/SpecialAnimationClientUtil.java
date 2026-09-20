package com.pla.annoyingvillagers.client.animation;

import net.minecraft.client.animation.AnimationDefinition;
import com.pla.annoyingvillagers.client.compat.LegacyHierarchicalModel;
import net.minecraft.world.entity.Entity;

public final class SpecialAnimationClientUtil {
    private SpecialAnimationClientUtil() {
    }

    public static void apply(LegacyHierarchicalModel<? extends Entity> model, AnimationDefinition animation, float elapsedTicks) {
        apply(model, animation, elapsedTicks, 1.0F);
    }

    public static void apply(LegacyHierarchicalModel<? extends Entity> model, AnimationDefinition animation, float elapsedTicks, float weight) {
        if (weight <= 0.0F) return;
        model.applyAnimation(animation, (long)(Math.max(0.0F, elapsedTicks) * 50.0F), weight);
    }

    public static void applyLoop(LegacyHierarchicalModel<? extends Entity> model, AnimationDefinition animation, float ageInTicks) {
        applyLoop(model, animation, ageInTicks, 1.0F);
    }

    public static void applyLoop(LegacyHierarchicalModel<? extends Entity> model, AnimationDefinition animation, float ageInTicks, float weight) {
        if (weight <= 0.0F) return;
        model.applyAnimation(animation, (long)(Math.max(0.0F, ageInTicks) * 50.0F), weight);
    }
}
