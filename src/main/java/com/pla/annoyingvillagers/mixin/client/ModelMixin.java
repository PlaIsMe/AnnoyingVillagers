package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.compat.LegacyCustomRenderable;
import net.minecraft.client.model.Model;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Model.class)
public abstract class ModelMixin {
    @Inject(method = "renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V",
            at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$renderLegacyModel(PoseStack poseStack, VertexConsumer vertices,
                                                      int packedLight, int packedOverlay, int color,
                                                      CallbackInfo ci) {
        if ((Object) this instanceof LegacyCustomRenderable legacyModel) {
            legacyModel.av$renderLegacy(poseStack, vertices, packedLight, packedOverlay, color);
            ci.cancel();
        }
    }
}
