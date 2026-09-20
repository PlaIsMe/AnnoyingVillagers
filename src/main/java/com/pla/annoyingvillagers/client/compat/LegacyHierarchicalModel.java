package com.pla.annoyingvillagers.client.compat;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

import java.util.IdentityHashMap;
import java.util.Map;

/** 26.1 model adapter for the generated entity-first animation methods. */
public abstract class LegacyHierarchicalModel<T extends Entity> extends EntityModel<LegacyEntityRenderState<T>> {
    private final Map<AnimationDefinition, KeyframeAnimation> animations = new IdentityHashMap<>();

    protected LegacyHierarchicalModel(ModelPart root) {
        super(root);
    }

    @Override
    public final void setupAnim(LegacyEntityRenderState<T> state) {
        setupAnim(state.entity, state.walkAnimationPos, state.walkAnimationSpeed,
                state.ageInTicks, state.yRot, state.xRot);
    }

    public abstract void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                                   float ageInTicks, float netHeadYaw, float headPitch);

    public final void applyAnimation(AnimationDefinition definition, long milliseconds, float weight) {
        animations.computeIfAbsent(definition, animation -> animation.bake(root())).apply(milliseconds, weight);
    }

    protected final void animate(AnimationState state, AnimationDefinition definition, float ageInTicks) {
        animations.computeIfAbsent(definition, animation -> animation.bake(root())).apply(state, ageInTicks);
    }

    protected final void animate(AnimationState state, AnimationDefinition definition, float ageInTicks, float speed) {
        animations.computeIfAbsent(definition, animation -> animation.bake(root())).apply(state, ageInTicks, speed);
    }

    protected final void animateWalk(AnimationDefinition definition, float position, float speed,
                                     float speedFactor, float scaleFactor) {
        animations.computeIfAbsent(definition, animation -> animation.bake(root()))
                .applyWalk(position, speed, speedFactor, scaleFactor);
    }

    protected final void applyStatic(AnimationDefinition definition) {
        animations.computeIfAbsent(definition, animation -> animation.bake(root())).applyStatic();
    }
}
