package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import com.pla.annoyingvillagers.client.renderer.GlintColorCarrier;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemFeatureRenderer.class)
public abstract class ItemGlintFeatureMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II[ILjava/util/List;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"))
    private void av$drawColor(ItemDisplayContext context, PoseStack pose, MultiBufferSource buffers, int light, int overlay, int[] tints, List<BakedQuad> quads, RenderType type, ItemStackRenderState.FoilType foil, Operation<Void> original, @Local SubmitNodeStorage.ItemSubmit item) {
        int previous = ColoredGlintState.getMode();
        ColoredGlintState.setMode(((GlintColorCarrier) (Object) item).av$getGlintColor());
        try {
            original.call(context, pose, buffers, light, overlay, tints, quads, type, foil);
        } finally {
            ColoredGlintState.setMode(previous);
        }
    }
}
