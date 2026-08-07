package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.entity.VacuumSliceEntity;
import net.minecraft.client.model.EntityModel;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * Horizontal X/Z blade plane.
 * Texture U=1 is mapped to local +Z, so the white sharp crescent points forward.
 */
public final class ModelVacuumSlice extends EntityModel<VacuumSliceEntity> {
    private static final float HALF_WIDTH = 4.25F;
    private static final float HALF_LENGTH = 4.25F;
    private static final float HALF_THICKNESS = 0.01F;

    @Override
    public void setupAnim(
            @NotNull VacuumSliceEntity entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
    }

    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            @NotNull VertexConsumer consumer,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha
    ) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        // Top face. U runs from rear (-Z) to sharp front (+Z).
        vertex(consumer, poseMatrix, normalMatrix,
                -HALF_WIDTH, HALF_THICKNESS, -HALF_LENGTH,
                0.0F, 1.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, 1.0F, 0.0F);
        vertex(consumer, poseMatrix, normalMatrix,
                -HALF_WIDTH, HALF_THICKNESS, HALF_LENGTH,
                1.0F, 1.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, 1.0F, 0.0F);
        vertex(consumer, poseMatrix, normalMatrix,
                HALF_WIDTH, HALF_THICKNESS, HALF_LENGTH,
                1.0F, 0.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, 1.0F, 0.0F);
        vertex(consumer, poseMatrix, normalMatrix,
                HALF_WIDTH, HALF_THICKNESS, -HALF_LENGTH,
                0.0F, 0.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, 1.0F, 0.0F);

        // Bottom face.
        vertex(consumer, poseMatrix, normalMatrix,
                HALF_WIDTH, -HALF_THICKNESS, -HALF_LENGTH,
                0.0F, 0.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, -1.0F, 0.0F);
        vertex(consumer, poseMatrix, normalMatrix,
                HALF_WIDTH, -HALF_THICKNESS, HALF_LENGTH,
                1.0F, 0.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, -1.0F, 0.0F);
        vertex(consumer, poseMatrix, normalMatrix,
                -HALF_WIDTH, -HALF_THICKNESS, HALF_LENGTH,
                1.0F, 1.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, -1.0F, 0.0F);
        vertex(consumer, poseMatrix, normalMatrix,
                -HALF_WIDTH, -HALF_THICKNESS, -HALF_LENGTH,
                0.0F, 1.0F,
                red, green, blue, alpha, packedLight, packedOverlay,
                0.0F, -1.0F, 0.0F);
    }

    private static void vertex(
            VertexConsumer consumer,
            Matrix4f poseMatrix,
            Matrix3f normalMatrix,
            float x,
            float y,
            float z,
            float u,
            float v,
            float red,
            float green,
            float blue,
            float alpha,
            int packedLight,
            int packedOverlay,
            float normalX,
            float normalY,
            float normalZ
    ) {
        consumer.vertex(poseMatrix, x, y, z)
                .color(red, green, blue, alpha)
                .uv(u, v)
                .overlayCoords(packedOverlay)
                .uv2(packedLight)
                .normal(normalMatrix, normalX, normalY, normalZ)
                .endVertex();
    }
}
