package com.pla.annoyingvillagers.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

/** Allows pre-26.1 models to retain custom part rendering despite Model#renderToBuffer being final. */
public interface LegacyCustomRenderable {
    void av$renderLegacy(PoseStack poseStack, VertexConsumer vertices,
                         int packedLight, int packedOverlay, int color);
}
