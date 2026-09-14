package com.pla.annoyingvillagers.client.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

public final class SpecialAnimationClientUtil {
    private SpecialAnimationClientUtil() {
    }

    public static void apply(HierarchicalModel<? extends Entity> model, AnimationDefinition animation, float elapsedTicks) {
        apply(model, animation, elapsedTicks, 1.0F);
    }

    public static void apply(HierarchicalModel<? extends Entity> model, AnimationDefinition animation, float elapsedTicks, float weight) {
        if (weight <= 0.0F) return;
        KeyframeAnimations.animate(model, animation, (long)(Math.max(0.0F, elapsedTicks) * 50.0F), weight, new Vector3f());
    }

    public static void applyLoop(HierarchicalModel<? extends Entity> model, AnimationDefinition animation, float ageInTicks) {
        applyLoop(model, animation, ageInTicks, 1.0F);
    }

    public static void applyLoop(HierarchicalModel<? extends Entity> model, AnimationDefinition animation, float ageInTicks, float weight) {
        if (weight <= 0.0F) return;
        KeyframeAnimations.animate(model, animation, (long)(Math.max(0.0F, ageInTicks) * 50.0F), weight, new Vector3f());
    }
}
