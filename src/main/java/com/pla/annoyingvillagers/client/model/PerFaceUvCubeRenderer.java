package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
        u0 /= textureWidth;
        v0 /= textureHeight;
        u1 /= textureWidth;
        v1 /= textureHeight;
        int steps = ((rotation % 360) + 360) % 360 / 90;
        // Select the rotated corners directly: allocating UV arrays for every face
        // creates thousands of short-lived arrays per extended armor render.
        int a = (4 - steps) & 3;
        int b = (5 - steps) & 3;
        int c = (6 - steps) & 3;
        int d = (7 - steps) & 3;
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, ax, ay, az, cornerU(a, u0, u1), cornerV(a, v0, v1), nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, bx, by, bz, cornerU(b, u0, u1), cornerV(b, v0, v1), nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, cx, cy, cz, cornerU(c, u0, u1), cornerV(c, v0, v1), nx, ny, nz);
        vertex(buffer, pose, matrix, packedLight, packedOverlay, color, dx, dy, dz, cornerU(d, u0, u1), cornerV(d, v0, v1), nx, ny, nz);
    }

    private static float cornerU(int corner, float u0, float u1) {
        return corner == 0 || corner == 3 ? u0 : u1;
    }

    private static float cornerV(int corner, float v0, float v1) {
        return corner < 2 ? v0 : v1;
    }

    private static void vertex(VertexConsumer buffer, PoseStack.Pose pose, Matrix4f matrix, int packedLight, int packedOverlay, int color, float x, float y, float z, float u, float v, float nx, float ny, float nz) {
        buffer.addVertex(matrix, x, y, z).setColor(color).setUv(u, v).setOverlay(packedOverlay).setLight(packedLight).setNormal(pose, nx, ny, nz);
    }
}
