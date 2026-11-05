package com.pla.annoyingvillagers.client.renderer;

import net.minecraft.client.renderer.entity.state.ChickenRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public final class BbqRenderState extends ChickenRenderState {
    public final ItemStackRenderState mainHand = new ItemStackRenderState();
    public final ItemStackRenderState offHand = new ItemStackRenderState();
}
