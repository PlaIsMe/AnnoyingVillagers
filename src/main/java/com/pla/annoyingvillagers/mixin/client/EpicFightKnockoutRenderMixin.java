package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.engine.GroundStuckKnockoutClient;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.mojang.math.Axis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.RenderEngine;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = RenderEngine.class, remap = false)
public abstract class EpicFightKnockoutRenderMixin {
    @Inject(method = "renderEntityArmatureModel", at = @At("HEAD"))
    private void annoyingVillagers$beginKnockoutSpin(LivingEntity entity, LivingEntityPatch<?> patch, EntityRenderer<? extends Entity> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTick, CallbackInfo ci) {
        float angle = GroundStuckKnockoutClient.getAngle(entity.getId(), partialTick);
        if (Float.isNaN(angle)) return;
        double pivot = entity.getBbHeight() * 0.5D;
        poseStack.pushPose(); poseStack.translate(0.0D, pivot, 0.0D); poseStack.mulPose(Axis.ZP.rotationDegrees(angle)); poseStack.mulPose(Axis.XP.rotationDegrees(angle * 0.35F)); poseStack.translate(0.0D, -pivot, 0.0D);
    }

    @Inject(method = "renderEntityArmatureModel", at = @At("RETURN"))
    private void annoyingVillagers$endKnockoutSpin(LivingEntity entity, LivingEntityPatch<?> patch, EntityRenderer<? extends Entity> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTick, CallbackInfo ci) {
        if (!Float.isNaN(GroundStuckKnockoutClient.getAngle(entity.getId(), partialTick))) poseStack.popPose();
    }
}
