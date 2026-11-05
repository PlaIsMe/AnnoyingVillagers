package com.pla.annoyingvillagers.client.compat;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.world.entity.Entity;

/** Render-state bridge that lets the existing animation code read its entity during extraction. */
public class LegacyEntityRenderState<T extends Entity> extends HumanoidRenderState {
    public T entity;
    public final ItemStackRenderState item = new ItemStackRenderState();
    public final MovingBlockRenderState movingBlock = new MovingBlockRenderState();
}
