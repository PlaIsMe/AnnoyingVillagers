package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.model.ModelFlyingShockwave;
import com.pla.annoyingvillagers.entity.FlyingShockwaveProjectile;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class FlyingShockwaveRenderer extends EntityRenderer<FlyingShockwaveProjectile>
{
    private final Model model;

    public FlyingShockwaveRenderer(EntityRendererProvider.Context pContext)
    {
        super(pContext);
        this.model = new ModelFlyingShockwave<>(pContext.bakeLayer(ModelFlyingShockwave.LAYER_LOCATION));
    }

    @Override
    public void render(
            FlyingShockwaveProjectile pEntity,
            float pEntityYaw,
            float pPartialTick,
            PoseStack pPoseStack,
            @NotNull MultiBufferSource pBuffer,
            int pPackedLight
    ) {
        pPoseStack.pushPose();

        float yaw = Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot());
        float pitch = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());

        pPoseStack.mulPose(Axis.YP.rotationDegrees(yaw - 90.0F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pitch + 35.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(pitch + 90.0F));

        pPoseStack.translate(0.0D, 0.0D, -2.0D);

        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(
                pBuffer,
                this.model.renderType(this.getTextureLocation(pEntity)),
                false,
                pEntity.isFoil()
        );

        this.model.renderToBuffer(
                pPoseStack,
                vertexConsumer,
                pPackedLight,
                OverlayTexture.NO_OVERLAY,
                -1
        );

        pPoseStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FlyingShockwaveProjectile flyingShockwaveProjectile)
    {
        return ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/flying_shockwave.png");
    }
}
