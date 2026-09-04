package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.BlackHoleEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Random;

/**
 * Forge 1.20.1 port of Wizardry's Scroll of Black Hole renderer.
 *
 * Wizardry rendered every ray as a GL triangle strip whose first two vertices shared the centre.
 * A direct conversion of those four vertices to Minecraft's modern QUADS buffer creates a degenerate
 * quad, which may rasterize as nothing. This renderer keeps the same 30 rotating tapered rays, but
 * gives every ray a tiny non-zero inner edge and emits the back face too, making the vortex reliable
 * from every camera angle while retaining the original appearance.
 */
public class BlackHoleRenderer extends EntityRenderer<BlackHoleEntity> {
    private static final ResourceLocation RAY_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/black_hole/ray.png");
    private static final ResourceLocation CENTRE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/black_hole/centre.png");
    private static final RenderType RAY_RENDER_TYPE = RenderType.entityTranslucentEmissive(RAY_TEXTURE);
    private static final RenderType CENTRE_RENDER_TYPE = RenderType.entityTranslucentEmissive(CENTRE_TEXTURE);
    private static final int RAY_COUNT = 30;
    private static final float INNER_RADIUS = 0.035F;

    public BlackHoleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(@NotNull BlackHoleEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        float scale = getSmoothScale(entity, partialTick);
        if (scale <= 0.001F) return;

        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        this.renderRays(entity, partialTick, poseStack, bufferSource);
        this.renderCentre(poseStack, bufferSource);
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void renderRays(BlackHoleEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        VertexConsumer consumer = bufferSource.getBuffer(RAY_RENDER_TYPE);
        float age = entity.tickCount + partialTick;
        float radius = 3.0F * entity.getSizeMultiplier();
        Random random = new Random(entity.getUUID().getMostSignificantBits() ^ entity.getUUID().getLeastSignificantBits());

        for (int ray = 0; ray < RAY_COUNT; ray++) {
            int a = random.nextInt(10);
            int b = random.nextInt(10);
            int sliceAngle = 20 + a;

            double angle1 = Math.toRadians(age + 40.0F * ray);
            double angle2 = Math.toRadians(age + 40.0F * ray - sliceAngle);
            double x1 = radius * Math.sin(angle1);
            double z1 = radius * Math.cos(angle1);
            double x2 = radius * Math.sin(angle2);
            double z2 = radius * Math.cos(angle2);

            // Keep Wizardry's original transformation mathematics exactly.
            double cosB = Mth.cos(31.0F * b);
            double sinB = Mth.sin(31.0F * b);
            double cosA = Mth.cos(31.0F * a);
            double sinA = Mth.sin(31.0F * a);

            float endX1 = (float)(x1 * cosB);
            float endY1 = (float)(z1 * sinA + x1 * cosA * sinB);
            float endZ1 = (float)(z1 * cosA);
            float endX2 = (float)(x2 * cosB);
            float endY2 = (float)(z2 * sinA + x2 * cosA * sinB);
            float endZ2 = (float)(z2 * cosA);

            float length1 = Mth.sqrt(endX1 * endX1 + endY1 * endY1 + endZ1 * endZ1);
            float length2 = Mth.sqrt(endX2 * endX2 + endY2 * endY2 + endZ2 * endZ2);
            if (length1 <= 1.0E-5F || length2 <= 1.0E-5F) continue;

            float innerX1 = endX1 / length1 * INNER_RADIUS;
            float innerY1 = endY1 / length1 * INNER_RADIUS;
            float innerZ1 = endZ1 / length1 * INNER_RADIUS;
            float innerX2 = endX2 / length2 * INNER_RADIUS;
            float innerY2 = endY2 / length2 * INNER_RADIUS;
            float innerZ2 = endZ2 / length2 * INNER_RADIUS;

            // Front face: opaque centre of ray.png at U=0, fading outward toward U=1.
            vertex(consumer, matrix, normalMatrix, innerX1, innerY1, innerZ1, 0.0F, 0.0F);
            vertex(consumer, matrix, normalMatrix, innerX2, innerY2, innerZ2, 0.0F, 1.0F);
            vertex(consumer, matrix, normalMatrix, endX2, endY2, endZ2, 1.0F, 1.0F);
            vertex(consumer, matrix, normalMatrix, endX1, endY1, endZ1, 1.0F, 0.0F);

            // Back face. Wizardry disabled culling globally; emitting the reverse face reproduces that
            // without relying on global OpenGL state in the modern renderer.
            vertex(consumer, matrix, normalMatrix, endX1, endY1, endZ1, 1.0F, 0.0F);
            vertex(consumer, matrix, normalMatrix, endX2, endY2, endZ2, 1.0F, 1.0F);
            vertex(consumer, matrix, normalMatrix, innerX2, innerY2, innerZ2, 0.0F, 1.0F);
            vertex(consumer, matrix, normalMatrix, innerX1, innerY1, innerZ1, 0.0F, 0.0F);
        }
    }

    private void renderCentre(PoseStack poseStack, MultiBufferSource bufferSource) {
        poseStack.pushPose();
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        VertexConsumer consumer = bufferSource.getBuffer(CENTRE_RENDER_TYPE);
        float halfSize = 0.8F;

        vertex(consumer, matrix, normalMatrix, -halfSize, halfSize, 0.0F, 0.0F, 0.0F);
        vertex(consumer, matrix, normalMatrix, halfSize, halfSize, 0.0F, 1.0F, 0.0F);
        vertex(consumer, matrix, normalMatrix, halfSize, -halfSize, 0.0F, 1.0F, 1.0F);
        vertex(consumer, matrix, normalMatrix, -halfSize, -halfSize, 0.0F, 0.0F, 1.0F);

        vertex(consumer, matrix, normalMatrix, -halfSize, -halfSize, 0.0F, 0.0F, 1.0F);
        vertex(consumer, matrix, normalMatrix, halfSize, -halfSize, 0.0F, 1.0F, 1.0F);
        vertex(consumer, matrix, normalMatrix, halfSize, halfSize, 0.0F, 1.0F, 0.0F);
        vertex(consumer, matrix, normalMatrix, -halfSize, halfSize, 0.0F, 0.0F, 0.0F);
        poseStack.popPose();
    }

    private static void vertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix, float x, float y, float z, float u, float v) {
        consumer.vertex(matrix, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(LightTexture.FULL_BRIGHT)
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    private static float getSmoothScale(BlackHoleEntity entity, float partialTick) {
        float age = entity.tickCount + partialTick;
        float lifetime = entity.getLifetime();
        float grow = Mth.clamp(age / 10.0F, 0.0F, 1.0F);
        float shrink = Mth.clamp((lifetime - age) / 10.0F, 0.0F, 1.0F);
        float raw = Math.min(grow, shrink);
        return Mth.sin(raw * (float)(Math.PI * 0.5D));
    }

    @Override
    public boolean shouldRender(@NotNull BlackHoleEntity entity, @NotNull Frustum frustum, double x, double y, double z) {
        if (entity.distanceToSqr(x, y, z) > 192.0D * 192.0D) return false;
        AABB renderBounds = entity.getBoundingBox().inflate(5.0D * entity.getSizeMultiplier());
        return frustum.isVisible(renderBounds);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BlackHoleEntity entity) {
        return RAY_TEXTURE;
    }
}
