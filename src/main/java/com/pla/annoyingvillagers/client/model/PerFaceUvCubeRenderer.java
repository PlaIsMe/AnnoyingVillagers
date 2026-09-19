package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/** Renders one cuboid with an independent UV rectangle and quarter-turn rotation on every face. */
public final class PerFaceUvCubeRenderer {
    private PerFaceUvCubeRenderer() {}

    public static void renderBox(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color, float x0, float y0, float z0, float x1, float y1, float z1, float nU0, float nV0, float nU1, float nV1, int nRot, float eU0, float eV0, float eU1, float eV1, int eRot, float sU0, float sV0, float sU1, float sV1, int sRot, float wU0, float wV0, float wU1, float wV1, int wRot, float uU0, float uV0, float uU1, float uV1, int uRot, float dU0, float dV0, float dU1, float dV1, int dRot, float textureWidth, float textureHeight) {
        x0 /= 16.0F;
        y0 /= 16.0F;
        z0 /= 16.0F;
        x1 /= 16.0F;
        y1 /= 16.0F;
        z1 /= 16.0F;
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        quad(buffer, pose, matrix, packedLight, packedOverlay, color, x1, y0, z0, x0, y0, z0, x0, y1, z0, x1, y1, z0, nU0, nV0, nU1, nV1, nRot, textureWidth, textureHeight, 0.0F, 0.0F, -1.0F);
        quad(buffer, pose, matrix, packedLight, packedOverlay, color, x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1, eU0, eV0, eU1, eV1, eRot, textureWidth, textureHeight, 1.0F, 0.0F, 0.0F);
        quad(buffer, pose, matrix, packedLight, packedOverlay, color, x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1, sU0, sV0, sU1, sV1, sRot, textureWidth, textureHeight, 0.0F, 0.0F, 1.0F);
        quad(buffer, pose, matrix, packedLight, packedOverlay, color, x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0, wU0, wV0, wU1, wV1, wRot, textureWidth, textureHeight, -1.0F, 0.0F, 0.0F);
        quad(buffer, pose, matrix, packedLight, packedOverlay, color, x0, y0, z1, x1, y0, z1, x1, y0, z0, x0, y0, z0, uU0, uV0, uU1, uV1, uRot, textureWidth, textureHeight, 0.0F, -1.0F, 0.0F);
        quad(buffer, pose, matrix, packedLight, packedOverlay, color, x0, y1, z0, x1, y1, z0, x1, y1, z1, x0, y1, z1, dU0, dV0, dU1, dV1, dRot, textureWidth, textureHeight, 0.0F, 1.0F, 0.0F);
    }

    private static void quad(VertexConsumer buffer, PoseStack.Pose pose, Matrix4f matrix, int packedLight, int packedOverlay, int color, float ax, float ay, float az, float bx, float by, float bz, float cx, float cy, float cz, float dx, float dy, float dz, float u0, float v0, float u1, float v1, int rotation, float textureWidth, float textureHeight, float nx, float ny, float nz) {
        float[][] uv = uvCorners(u0 / textureWidth, v0 / textureHeight, u1 / textureWidth, v1 / textureHeight, rotation);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, ax, ay, az, uv[0][0], uv[0][1], nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, bx, by, bz, uv[1][0], uv[1][1], nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, cx, cy, cz, uv[2][0], uv[2][1], nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, dx, dy, dz, uv[3][0], uv[3][1], nx, ny, nz);
    }

    private static float[][] uvCorners(float u0, float v0, float u1, float v1, int rotation) {
        float[][] base = {{u0, v0}, {u1, v0}, {u1, v1}, {u0, v1}};
        int steps = ((rotation % 360) + 360) % 360 / 90;
        if (steps == 0) return base;
        float[][] out = new float[4][2];
        for (int i = 0; i < 4; i++) {
            int src = (i - steps + 4) % 4;
            out[i][0] = base[src][0];
            out[i][1] = base[src][1];
        }
        return out;
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, Matrix4f matrix, int packedLight, int packedOverlay, int color, float x, float y, float z, float u, float v, float nx, float ny, float nz) {
        buffer.addVertex(matrix, x, y, z).setColor(color).setUv(u, v).setOverlay(packedOverlay).setLight(packedLight).setNormal(pose, nx, ny, nz);
    }
}
