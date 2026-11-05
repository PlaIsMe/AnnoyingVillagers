package com.pla.annoyingvillagers.client.engine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.neoforged.bus.api.Event;

/** Adds mod geometry after entity submissions and before the feature renderer consumes them. */
public final class SubmitCustomGeometryEvent extends Event {
    private final PoseStack poseStack;
    private final SubmitNodeCollector collector;
    private final LevelRenderState state;

    public SubmitCustomGeometryEvent(PoseStack poseStack, SubmitNodeCollector collector, LevelRenderState state) {
        this.poseStack = poseStack;
        this.collector = collector;
        this.state = state;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public SubmitNodeCollector getSubmitNodeCollector() {
        return collector;
    }

    public LevelRenderState getLevelRenderState() {
        return state;
    }
}
