package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.HookGunItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = PatchedItemInHandLayer.class, remap = false)
public abstract class PatchedItemInHandLayerMixin {
    @WrapOperation(
            method = "renderLayer(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/layers/RenderLayer;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I[Lyesman/epicfight/api/utils/math/OpenMatrix4f;FFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;isOffhandItemValid()Z"
            )
    )
    private boolean annoyingVillagers$forceOffhandUtilityItemRender(
            LivingEntityPatch<?> entityPatch,
            Operation<Boolean> original
    ) {
        if (original.call(entityPatch)) {
            return true;
        }

        Entity entity = entityPatch.getOriginal();
        return entity instanceof LivingEntity livingEntity
                && (FishingRodGrappleUtil.shouldForceOffhandFishingRodRender(livingEntity)
                || HookGunItem.shouldForceOffhandHookGunRender(livingEntity));
    }
}
