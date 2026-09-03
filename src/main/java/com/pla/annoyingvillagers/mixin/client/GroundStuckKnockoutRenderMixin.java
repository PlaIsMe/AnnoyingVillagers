package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.engine.GroundStuckKnockoutClient;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class GroundStuckKnockoutRenderMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Inject(method = "setupRotations", at = @At("TAIL"))
    private void annoyingVillagers$groundStuckKnockoutSpin(
            T entity,
            PoseStack poseStack,
            float ageInTicks,
            float rotationYaw,
            float partialTick,
            CallbackInfo ci
    ) {
        float angle = GroundStuckKnockoutClient.getAngle(entity.getId(), partialTick);
        if (!Float.isNaN(angle)) {
            /*
             * Match the old Epic Fight knockout presentation: spin around the
             * body's center instead of around the feet, with a smaller X-axis
             * tumble layered on top of the main Z-axis rotation.
             */
            double pivot = entity.getBbHeight() * 0.5D;
            poseStack.translate(0.0D, pivot, 0.0D);
            poseStack.mulPose(Axis.ZP.rotationDegrees(angle));
            poseStack.mulPose(Axis.XP.rotationDegrees(angle * 0.35F));
            poseStack.translate(0.0D, -pivot, 0.0D);
        }
    }
}
