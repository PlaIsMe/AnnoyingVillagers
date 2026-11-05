package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.engine.SubmitCustomGeometryEvent;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelGeometrySubmissionMixin {
    @Inject(method = "submitBlockEntities", at = @At("RETURN"))
    private void av$submitGeometry(PoseStack poseStack, LevelRenderState state, SubmitNodeStorage collector, CallbackInfo ci) {
        NeoForge.EVENT_BUS.post(new SubmitCustomGeometryEvent(poseStack, collector, state));
    }
}
