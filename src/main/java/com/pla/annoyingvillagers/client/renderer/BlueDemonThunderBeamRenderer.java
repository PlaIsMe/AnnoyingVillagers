package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.engine.ThunderRender;
import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class BlueDemonThunderBeamRenderer extends EntityRenderer<BlueDemonThunderBeamEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_beam.png");
    private final ThunderRender thunderRender = new ThunderRender();

    public BlueDemonThunderBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Vec3 getRenderOffset(BlueDemonThunderBeamEntity dragonBeam, float p_114484_) {
        return new Vec3(dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03);
    }

    public void render(@NotNull BlueDemonThunderBeamEntity blueDemonThunderBeamEntity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(blueDemonThunderBeamEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if (blueDemonThunderBeamEntity.isSetUseNoVfxThunder()) {
            poseStack.pushPose();
            Vec3 from = blueDemonThunderBeamEntity.getStartPos();
            Vec3 to = blueDemonThunderBeamEntity.getEndPos();
            ThunderRender.ThunderData bolt = new ThunderRender.ThunderData(
                    ThunderRender.ThunderData.ThunderRenderInfo.BLUE_DEMON_THUNDER, from, to, 15)
                    .size(0.1F)
                    .lifespan(4)
                    .spawn(ThunderRender.ThunderData.SpawnFunction.delay(1F));
            thunderRender.update(null, bolt, partialTicks);
            poseStack.translate(-blueDemonThunderBeamEntity.getX(), -blueDemonThunderBeamEntity.getY(), -blueDemonThunderBeamEntity.getZ());
            thunderRender.render(partialTicks, poseStack, buffer);
            poseStack.popPose();
        }
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull BlueDemonThunderBeamEntity dragonBeam) {
        return TEXTURE;
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn) {
        vertexBuilder.addVertex(matrix, offsetX, offsetY, offsetZ).setColor(1.0F, 1.0F, 1.0F, alpha).setUv(textureX, textureY).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLightIn).setNormal(0.0F, 1.0F, 0.0F);
    }
}
