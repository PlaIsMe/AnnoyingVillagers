package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.PortalEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class PortalEntityRenderer extends LegacyEntityRenderer<PortalEntity> {
    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/portal.png");

    public PortalEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(LegacyEntityRenderState<PortalEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        PortalEntity portal = state.entity;
        float partialTicks = state.partialTick;
        super.submit(state, poseStack, collector, camera);
        poseStack.pushPose();

        float yaw = Mth.lerp(partialTicks, portal.yRotO, portal.getYRot());
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));

        collector.submitCustomGeometry(poseStack,
                net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(this.getTextureLocation(portal)),
                (pose, consumer) -> {
                    int light = LightTexture.FULL_BRIGHT;
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
