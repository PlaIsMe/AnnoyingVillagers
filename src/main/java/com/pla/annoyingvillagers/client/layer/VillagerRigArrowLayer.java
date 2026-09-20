package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class VillagerRigArrowLayer<T extends Mob> extends RenderLayer<LegacyEntityRenderState<T>, ModelRigVillager<T>> {
    private final ArrowModel arrowModel;
    private final ArrowRenderState arrowState = new ArrowRenderState();
    public VillagerRigArrowLayer(EntityRendererProvider.Context context, RenderLayerParent<LegacyEntityRenderState<T>, ModelRigVillager<T>> renderer) {
        super(renderer);
        this.arrowModel = new ArrowModel(context.bakeLayer(ModelLayers.ARROW));
    }

    @Override
    public void submit(PoseStack pPoseStack, SubmitNodeCollector collector, int pPackedLight,
                       LegacyEntityRenderState<T> state, float yRot, float xRot) {
        T pLivingEntity = state.entity;
        int arrowCount = pLivingEntity.getArrowCount();

        if (arrowCount <= 0) {
            return;
        }

        RandomSource random = RandomSource.create(pLivingEntity.getId());
        for (int i = 0; i < arrowCount; i++) {
            pPoseStack.pushPose();

            Vector3f direction = this.getParentModel().translateToRandomArrowPosition(pPoseStack, random);
            this.submitArrow(pPoseStack, collector, pPackedLight, state, direction.x(), direction.y(), direction.z());
            pPoseStack.popPose();
        }
    }

    private void submitArrow(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                             LegacyEntityRenderState<T> state, float directionX, float directionY, float directionZ) {
        float horizontalDistance = Mth.sqrt(directionX * directionX + directionZ * directionZ);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.atan2(directionX, directionZ) * (180.0D / Math.PI)) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) (Math.atan2(directionY, horizontalDistance) * (180.0D / Math.PI))));
        collector.submitModel(this.arrowModel, this.arrowState, poseStack,
                TippableArrowRenderer.NORMAL_ARROW_LOCATION, packedLight, OverlayTexture.NO_OVERLAY,
                state.outlineColor, null);
    }
}
