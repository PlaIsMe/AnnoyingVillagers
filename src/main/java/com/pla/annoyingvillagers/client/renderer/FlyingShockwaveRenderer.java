package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelFlyingShockwave;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import com.pla.annoyingvillagers.entity.FlyingShockwaveProjectile;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class FlyingShockwaveRenderer extends LegacyEntityRenderer<FlyingShockwaveProjectile>
{
    private final ModelFlyingShockwave<FlyingShockwaveProjectile> model;

    public FlyingShockwaveRenderer(EntityRendererProvider.Context pContext)
    {
        super(pContext);
        this.model = new ModelFlyingShockwave<>(pContext.bakeLayer(ModelFlyingShockwave.LAYER_LOCATION));
    }

    @Override
    public void submit(LegacyEntityRenderState<FlyingShockwaveProjectile> state, PoseStack pPoseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        FlyingShockwaveProjectile pEntity = state.entity;
        float pPartialTick = state.partialTick;
        super.submit(state, pPoseStack, collector, camera);
        pPoseStack.pushPose();

        float yaw = Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot());
        float pitch = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());

        pPoseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pitch + 35.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pitch + 90.0F));

        pPoseStack.translate(0.0D, 0.0D, -2.0D);

        collector.submitModel(this.model, state, pPoseStack, this.getTextureLocation(pEntity),
                state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        if (pEntity.isFoil()) {
            collector.submitModel(this.model, state, pPoseStack,
                    net.minecraft.client.renderer.rendertype.RenderTypes.entityGlint(), state.lightCoords,
                    OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
        }

        pPoseStack.popPose();

    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull FlyingShockwaveProjectile flyingShockwaveProjectile)
    {
        return Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/flying_shockwave.png");
    }
}
