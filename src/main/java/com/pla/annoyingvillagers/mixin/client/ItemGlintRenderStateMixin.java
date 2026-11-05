package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import com.pla.annoyingvillagers.client.renderer.GlintColorCarrier;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStackRenderState.class)
public abstract class ItemGlintRenderStateMixin implements GlintColorCarrier {
    @Unique private int av$color;

    @Override public int av$getGlintColor() { return av$color; }
    @Override public void av$setGlintColor(int color) { av$color = color; }

    @WrapMethod(method = "submit")
    private void av$submitColor(PoseStack pose, SubmitNodeCollector collector, int light, int overlay, int outline, Operation<Void> original) {
        int previous = ColoredGlintState.getMode();
        ColoredGlintState.setMode(av$color);
        try {
            original.call(pose, collector, light, overlay, outline);
        } finally {
            ColoredGlintState.setMode(previous);
        }
    }
}
