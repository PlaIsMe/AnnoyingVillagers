package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.clazz.FakePlayer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class FakePlayerCapeLayer<T extends FakePlayer> extends RenderLayer<T, PlayerModel<T>> {
    public FakePlayerCapeLayer(RenderLayerParent<T, PlayerModel<T>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        Optional<ResourceLocation> cape = FakePlayerTextureUtils.getPlayerCape(entity);
        if (entity.isInvisible() || cape.isEmpty()) {
            return;
        }

        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chest.getItem() instanceof ElytraItem) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.0D, 0.0D, 0.125D);

        double cloakX = Mth.lerp(partialTick, entity.xCloakO, entity.xCloak) - Mth.lerp(partialTick, entity.xo, entity.getX());
        double cloakY = Mth.lerp(partialTick, entity.yCloakO, entity.yCloak) - Mth.lerp(partialTick, entity.yo, entity.getY());
        double cloakZ = Mth.lerp(partialTick, entity.zCloakO, entity.zCloak) - Mth.lerp(partialTick, entity.zo, entity.getZ());
        float bodyRot = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        double bodySin = Mth.sin(bodyRot * ((float)Math.PI / 180.0F));
        double bodyCos = -Mth.cos(bodyRot * ((float)Math.PI / 180.0F));
        float yMotion = (float)cloakY * 10.0F;
        yMotion = Mth.clamp(yMotion, -6.0F, 32.0F);
        float zMotion = (float)(cloakX * bodySin + cloakZ * bodyCos) * 100.0F;
        zMotion = Mth.clamp(zMotion, 0.0F, 150.0F);
        float xMotion = (float)(cloakX * bodyCos - cloakZ * bodySin) * 100.0F;
        xMotion = Mth.clamp(xMotion, -20.0F, 20.0F);

        if (entity.isCrouching()) {
            yMotion += 25.0F;
        }

        poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + zMotion / 2.0F + yMotion));
        poseStack.mulPose(Axis.ZP.rotationDegrees(xMotion / 2.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - xMotion / 2.0F));
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(cape.get()));
        this.getParentModel().renderCloak(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
