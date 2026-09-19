package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.engine.ThunderRender;
import com.pla.annoyingvillagers.entity.DragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DragonBeamRenderer extends EntityRenderer<DragonBeamEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_beam.png");
    private final ThunderRender thunderRender = new ThunderRender();

    public DragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Vec3 getRenderOffset(DragonBeamEntity dragonBeam, float p_114484_) {
        return new Vec3(dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03, dragonBeam.level().random.nextGaussian() * 0.03);
    }

    public void render(@NotNull DragonBeamEntity dragonBeamEntity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(dragonBeamEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if (dragonBeamEntity.isSetUseNoVfxThunder()) {
            poseStack.pushPose();
            Vec3 from = dragonBeamEntity.getThunderStartVec3();
            Vec3 to = dragonBeamEntity.getThunderStopVec3();
            ThunderRender.ThunderData bolt = new ThunderRender.ThunderData(
                    ThunderRender.ThunderData.ThunderRenderInfo.DRAGON_THUNDER, from, to, 15)
                    .size(0.2F)
                    .lifespan(4)
                    .spawn(ThunderRender.ThunderData.SpawnFunction.delay(1F));
            thunderRender.update(null, bolt, partialTicks);
            poseStack.translate(-dragonBeamEntity.getX(), -dragonBeamEntity.getY(), -dragonBeamEntity.getZ());
            thunderRender.render(partialTicks, poseStack, buffer);
            poseStack.popPose();
        }
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull DragonBeamEntity dragonBeam) {
        return TEXTURE;
    }
}