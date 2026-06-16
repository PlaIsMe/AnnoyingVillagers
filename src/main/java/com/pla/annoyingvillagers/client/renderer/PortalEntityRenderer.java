package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig;
import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PortalEntityRenderer extends EntityRenderer<PortalEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/portal.png");
    private static final int AAA_PORTAL_REFRESH_TICKS = 10;
    private static final Map<Integer, Long> LAST_AAA_PORTAL_PLAY_TICK = new HashMap<>();

    public PortalEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(
            @NotNull PortalEntity portal,
            float entityYaw,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight
    ) {
        if (shouldRenderWithAaa() && playAaaPortalVisual(portal)) {
            super.render(portal, entityYaw, partialTicks, poseStack, buffer, packedLight);
            return;
        }

        poseStack.pushPose();

        float yaw = Mth.lerp(partialTicks, portal.yRotO, portal.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(portal)));
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        int light = LightTexture.FULL_BRIGHT;

        float halfWidth = PortalEntity.WIDTH * 0.5F;
        float height = PortalEntity.HEIGHT;
        drawVertex(consumer, matrix, normal, -halfWidth, 0.0F, 0.0F, 0.0F, 1.0F, light, 1.0F);
        drawVertex(consumer, matrix, normal, halfWidth, 0.0F, 0.0F, 1.0F, 1.0F, light, 1.0F);
        drawVertex(consumer, matrix, normal, halfWidth, height, 0.0F, 1.0F, 0.0F, light, 1.0F);
        drawVertex(consumer, matrix, normal, -halfWidth, height, 0.0F, 0.0F, 0.0F, light, 1.0F);

        drawVertex(consumer, matrix, normal, -halfWidth, height, 0.0F, 0.0F, 0.0F, light, -1.0F);
        drawVertex(consumer, matrix, normal, halfWidth, height, 0.0F, 1.0F, 0.0F, light, -1.0F);
        drawVertex(consumer, matrix, normal, halfWidth, 0.0F, 0.0F, 1.0F, 1.0F, light, -1.0F);
        drawVertex(consumer, matrix, normal, -halfWidth, 0.0F, 0.0F, 0.0F, 1.0F, light, -1.0F);

        poseStack.popPose();
        super.render(portal, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static boolean shouldRenderWithAaa() {
        return AnnoyingVillagersClientConfig.shouldUseAaaParticles(AnnoyingVillagersClientConfig.VfxEffect.TELEPORT_PORTAL);
    }

    private static boolean playAaaPortalVisual(PortalEntity portal) {
        if (!portal.level().isClientSide || portal.isRemoved()) {
            LAST_AAA_PORTAL_PLAY_TICK.remove(portal.getId());
            return false;
        }

        long gameTime = portal.level().getGameTime();
        Long lastPlayTick = LAST_AAA_PORTAL_PLAY_TICK.get(portal.getId());
        if (lastPlayTick == null || gameTime < lastPlayTick || gameTime - lastPlayTick >= AAA_PORTAL_REFRESH_TICKS) {
            if (!AAAParticlesUtil.sendTeleportPortal(portal.level(), portal.getPortalCenter(), portal.getNormal())) {
                LAST_AAA_PORTAL_PLAY_TICK.remove(portal.getId());
                return false;
            }
            LAST_AAA_PORTAL_PLAY_TICK.put(portal.getId(), gameTime);
        }
        return true;
    }

    private void drawVertex(
            VertexConsumer consumer,
            Matrix4f matrix,
            Matrix3f normal,
            float x,
            float y,
            float z,
            float u,
            float v,
            int packedLight,
            float normalZ
    ) {
        consumer.vertex(matrix, x, y, z)
                .color(1.0F, 1.0F, 1.0F, 0.9F)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normal, 0.0F, 0.0F, normalZ)
                .endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PortalEntity portal) {
        return TEXTURE;
    }
}
