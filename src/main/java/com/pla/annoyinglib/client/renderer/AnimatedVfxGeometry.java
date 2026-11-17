package com.pla.annoyinglib.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/** Small shared quad writer. Animation time is supplied by the client tick plus partial tick. */
public final class AnimatedVfxGeometry {
    private AnimatedVfxGeometry() {}

    public static int fireFrame(float age, int offset) {
        return Math.floorMod((int) Math.floor(age * 0.8F) + offset, 16);
    }

    public static void quad(VertexConsumer out, Matrix4f pose, float width, float height,
                            int frame, float red, float green, float blue, float alpha) {
        float u = (frame % 4) / 4F, v = (frame / 4) / 4F;
        float inset = 0.5F / 512;
        vertex(out, pose, -width / 2, 0, u + inset, v + .25F - inset, red, green, blue, alpha);
        vertex(out, pose, width / 2, 0, u + .25F - inset, v + .25F - inset, red, green, blue, alpha);
        vertex(out, pose, width / 2, height, u + .25F - inset, v + inset, red, green, blue, alpha);
        vertex(out, pose, -width / 2, height, u + inset, v + inset, red, green, blue, alpha);
    }

    public static void ribbon(VertexConsumer out, Matrix4f pose, Vec3 a, Vec3 b, Vec3 side,
                              float u, float v, float red, float green, float blue, float alpha) {
        point(out, pose, a.subtract(side), u, v, red, green, blue, alpha);
        point(out, pose, b.subtract(side), u, v + .25F, red, green, blue, alpha);
        point(out, pose, b.add(side), u + 1, v + .25F, red, green, blue, alpha);
        point(out, pose, a.add(side), u + 1, v, red, green, blue, alpha);
    }

    private static void point(VertexConsumer out, Matrix4f pose, Vec3 point, float u, float v,
                              float red, float green, float blue, float alpha) {
        out.vertex(pose, (float) point.x, (float) point.y, (float) point.z).color(red, green, blue, alpha).uv(u, v).endVertex();
    }

    private static void vertex(VertexConsumer out, Matrix4f pose, float x, float y, float u, float v,
                               float red, float green, float blue, float alpha) {
        out.vertex(pose, x, y, 0).color(red, green, blue, alpha).uv(u, v).endVertex();
    }
}
