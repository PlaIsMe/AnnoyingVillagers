package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import com.pla.annoyingvillagers.client.renderer.GlintColorCarrier;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SubmitNodeStorage.ItemSubmit.class)
public abstract class ItemGlintSubmitMixin implements GlintColorCarrier {
    @Unique private int av$color;

    @Override public int av$getGlintColor() { return av$color; }
    @Override public void av$setGlintColor(int color) { av$color = color; }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void av$retainColor(PoseStack.Pose pose, ItemDisplayContext context, int light, int overlay, int outline, int[] tints, List<BakedQuad> quads, RenderType type, ItemStackRenderState.FoilType foil, CallbackInfo ci) {
        av$color = ColoredGlintState.getMode();
    }
}
