package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.engine.ThunderRender;
import com.pla.annoyingvillagers.entity.DragonBeamEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class DragonBeamRenderer extends LegacyEntityRenderer<DragonBeamEntity> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/dragon_beam.png");
    private final ThunderRender thunderRender = new ThunderRender();

    public DragonBeamRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    public @NotNull Vec3 getRenderOffset(DragonBeamEntity dragonBeam, float p_114484_) {
        return new Vec3(dragonBeam.level().getRandom().nextGaussian() * 0.03, dragonBeam.level().getRandom().nextGaussian() * 0.03, dragonBeam.level().getRandom().nextGaussian() * 0.03);
    }

    public void submit(LegacyEntityRenderState<DragonBeamEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        super.submit(state, poseStack, collector, camera);
        DragonBeamEntity dragonBeamEntity = state.entity;
        float partialTicks = state.partialTick;
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
            collector.submitCustomGeometry(poseStack,
                    net.minecraft.client.renderer.rendertype.RenderTypes.lightning(),
                    (rootPose, vertices) -> thunderRender.render(partialTicks, rootPose, vertices));
            poseStack.popPose();
        }
    }

    public @NotNull Identifier getTextureLocation(@NotNull DragonBeamEntity dragonBeam) {
        return TEXTURE;
    }
}
