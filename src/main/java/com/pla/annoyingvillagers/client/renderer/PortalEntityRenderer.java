package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.compat.photon.PhotonClientFxUtil;
import com.pla.annoyingvillagers.config.AnnoyingVillagersClientConfig;
import com.pla.annoyingvillagers.entity.PortalEntity;
import com.pla.annoyingvillagers.util.AAAParticlesUtil;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public class PortalEntityRenderer extends LegacyEntityRenderer<PortalEntity> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/portal.png");
    private static final String PHOTON_PORTAL_EFFECT = "snakeportal";
    private static final int PHOTON_PORTAL_LIFETIME_TICKS = 12;
    private static final int AAA_PORTAL_REFRESH_TICKS = 10;
    private static final Map<Integer, Long> LAST_AAA_PORTAL_PLAY_TICK = new HashMap<>();

    public PortalEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(LegacyEntityRenderState<PortalEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        PortalEntity portal = state.entity;
        float partialTicks = state.partialTick;
        super.submit(state, poseStack, collector, camera);
        if (AnnoyingVillagersClientConfig.shouldPreferAaaParticles(AnnoyingVillagersClientConfig.VfxEffect.TELEPORT_PORTAL)
                && playAaaPortalVisual(portal)) {
            return;
        }

        if (shouldRenderWithPhoton() && playPhotonPortalVisual(portal)) {
            return;
        }

        if (!AnnoyingVillagersClientConfig.shouldPreferAaaParticles(AnnoyingVillagersClientConfig.VfxEffect.TELEPORT_PORTAL)
                && shouldRenderWithAaa() && playAaaPortalVisual(portal)) {
            return;
        }

        poseStack.pushPose();

        float yaw = Mth.lerp(partialTicks, portal.yRotO, portal.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));

        collector.submitCustomGeometry(poseStack,
                net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(this.getTextureLocation(portal)),
                (pose, consumer) -> {
                    int light = LightCoordsUtil.FULL_BRIGHT;
                    float halfWidth = PortalEntity.WIDTH * 0.5F;
                    float height = PortalEntity.HEIGHT;
                    drawVertex(consumer, pose, -halfWidth, 0.0F, 0.0F, 0.0F, 1.0F, light, 1.0F);
                    drawVertex(consumer, pose, halfWidth, 0.0F, 0.0F, 1.0F, 1.0F, light, 1.0F);
                    drawVertex(consumer, pose, halfWidth, height, 0.0F, 1.0F, 0.0F, light, 1.0F);
                    drawVertex(consumer, pose, -halfWidth, height, 0.0F, 0.0F, 0.0F, light, 1.0F);
                    drawVertex(consumer, pose, -halfWidth, height, 0.0F, 0.0F, 0.0F, light, -1.0F);
                    drawVertex(consumer, pose, halfWidth, height, 0.0F, 1.0F, 0.0F, light, -1.0F);
                    drawVertex(consumer, pose, halfWidth, 0.0F, 0.0F, 1.0F, 1.0F, light, -1.0F);
                    drawVertex(consumer, pose, -halfWidth, 0.0F, 0.0F, 0.0F, 1.0F, light, -1.0F);
                });

        poseStack.popPose();
    }

    private static boolean shouldRenderWithPhoton() {
        return AnnoyingVillagersClientConfig.shouldUsePhotonWhenAvailable(AnnoyingVillagersClientConfig.VfxEffect.TELEPORT_PORTAL);
    }

    private static boolean playPhotonPortalVisual(PortalEntity portal) {
        if (!portal.level().isClientSide() || portal.isRemoved()) {
            return false;
        }

        return PhotonClientFxUtil.followPortal(
                "teleport_portal:" + portal.getId(),
                portal.level(),
                PHOTON_PORTAL_EFFECT,
                () -> portal.isRemoved() ? null : portal.getPortalCenter(),
                () -> portal.isRemoved() ? null : portal.getNormal(),
                PHOTON_PORTAL_LIFETIME_TICKS
        );
    }

    private static boolean shouldRenderWithAaa() {
        return AnnoyingVillagersClientConfig.shouldUseAaaParticles(AnnoyingVillagersClientConfig.VfxEffect.TELEPORT_PORTAL);
    }

    private static boolean playAaaPortalVisual(PortalEntity portal) {
        if (!portal.level().isClientSide() || portal.isRemoved()) {
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
            PoseStack.Pose pose,
            float x,
            float y,
            float z,
            float u,
            float v,
            int packedLight,
            float normalZ
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(1.0F, 1.0F, 1.0F, 0.9F)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 0.0F, normalZ)
                ;
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull PortalEntity portal) {
        return TEXTURE;
    }
}
