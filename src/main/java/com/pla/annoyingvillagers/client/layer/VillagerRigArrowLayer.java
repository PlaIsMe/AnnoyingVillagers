package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.model.ModelRigVillager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Arrow;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class VillagerRigArrowLayer<T extends Mob> extends RenderLayer<T, ModelRigVillager<T>> {
    private final EntityRenderDispatcher dispatcher;
    public VillagerRigArrowLayer(EntityRendererProvider.Context context, RenderLayerParent<T, ModelRigVillager<T>> renderer) {
        super(renderer);
        this.dispatcher = context.getEntityRenderDispatcher();
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, @NotNull T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        int arrowCount = pLivingEntity.getArrowCount();

        if (arrowCount <= 0) {
            return;
        }

        RandomSource random = RandomSource.create(pLivingEntity.getId());
        for (int i = 0; i < arrowCount; i++) {
            pPoseStack.pushPose();

            Vector3f direction = this.getParentModel().translateToRandomArrowPosition(pPoseStack, random);
            this.renderArrow(pPoseStack, pBuffer, pPackedLight, pLivingEntity, direction.x(), direction.y(), direction.z(), pPartialTick);
            pPoseStack.popPose();
        }
    }

    private void renderArrow(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float directionX, float directionY, float directionZ, float partialTick) {
        float horizontalDistance = Mth.sqrt(directionX * directionX + directionZ * directionZ);
        Arrow arrow = new Arrow(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        arrow.setYRot((float) (Math.atan2(directionX, directionZ) * (180.0D / Math.PI)));
        arrow.setXRot((float) (Math.atan2(directionY, horizontalDistance) * (180.0D / Math.PI)));
        arrow.yRotO = arrow.getYRot();
        arrow.xRotO = arrow.getXRot();
        this.dispatcher.render(arrow, 0.0D, 0.0D, 0.0D, 0.0F, partialTick, poseStack, buffer, packedLight);
    }
}