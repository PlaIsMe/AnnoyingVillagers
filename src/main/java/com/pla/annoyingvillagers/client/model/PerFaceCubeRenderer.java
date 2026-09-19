package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/** Renders an axis-aligned model cuboid with one explicit UV rectangle on every face. */
public final class PerFaceCubeRenderer {
    private PerFaceCubeRenderer() {
    }

    public static void renderSingleUvBox(
            PoseStack poseStack, VertexConsumer buffer,
            int packedLight, int packedOverlay,
            int color,
            float x0, float y0, float z0, float x1, float y1, float z1,
            float u0, float v0, float u1, float v1,
            float textureWidth, float textureHeight) {
        x0 /= 16.0F; y0 /= 16.0F; z0 /= 16.0F;
        x1 /= 16.0F; y1 /= 16.0F; z1 /= 16.0F;
        u0 /= textureWidth; u1 /= textureWidth;
        v0 /= textureHeight; v1 /= textureHeight;

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();

        // North (-Z)
        quad(buffer, pose, matrix, packedLight, packedOverlay, color,
                x1, y0, z0, x0, y0, z0, x0, y1, z0, x1, y1, z0,
                u0, v0, u1, v1, 0.0F, 0.0F, -1.0F);
        // South (+Z)
        quad(buffer, pose, matrix, packedLight, packedOverlay, color,
                x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1,
                u0, v0, u1, v1, 0.0F, 0.0F, 1.0F);
        // West (-X)
        quad(buffer, pose, matrix, packedLight, packedOverlay, color,
                x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0,
                u0, v0, u1, v1, -1.0F, 0.0F, 0.0F);
        // East (+X)
        quad(buffer, pose, matrix, packedLight, packedOverlay, color,
                x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1,
                u0, v0, u1, v1, 1.0F, 0.0F, 0.0F);
        // Up (-Y)
        quad(buffer, pose, matrix, packedLight, packedOverlay, color,
                x0, y0, z1, x1, y0, z1, x1, y0, z0, x0, y0, z0,
                u0, v0, u1, v1, 0.0F, -1.0F, 0.0F);
        // Down (+Y)
        quad(buffer, pose, matrix, packedLight, packedOverlay, color,
                x0, y1, z0, x1, y1, z0, x1, y1, z1, x0, y1, z1,
                u0, v0, u1, v1, 0.0F, 1.0F, 0.0F);
    }

    private static void quad(
            VertexConsumer buffer, PoseStack.Pose pose, Matrix4f matrix,
            int packedLight, int packedOverlay,
            int color,
            float ax, float ay, float az,
            float bx, float by, float bz,
            float cx, float cy, float cz,
            float dx, float dy, float dz,
            float u0, float v0, float u1, float v1,
            float nx, float ny, float nz) {
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, ax, ay, az, u0, v0, nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, bx, by, bz, u1, v0, nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, cx, cy, cz, u1, v1, nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, dx, dy, dz, u0, v1, nx, ny, nz);
    }

    private static void vertex(
            VertexConsumer buffer, PoseStack.Pose pose, Matrix4f matrix,
            int packedLight, int packedOverlay,
            int color,
            float x, float y, float z, float u, float v,
            float nx, float ny, float nz) {
        buffer.addVertex(matrix, x, y, z)
                .setColor(color)
                .setUv(u, v)
                .setOverlay(packedOverlay)
                .setLight(packedLight)
                .setNormal(pose, nx, ny, nz)
                ;
    }
}
