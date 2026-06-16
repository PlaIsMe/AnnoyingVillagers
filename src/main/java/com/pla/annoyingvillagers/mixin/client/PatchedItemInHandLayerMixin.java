package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.HookGunItemRenderer;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import com.pla.annoyingvillagers.item.HookGunItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.model.armature.HumanoidArmature;
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
                || HookGunItem.shouldForceOffhandHookGunRender(livingEntity)
                || annoyingVillagers$shouldForceOffhandBucketRender(livingEntity));
    }

    @WrapOperation(
            method = "renderLayer(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/layers/RenderLayer;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I[Lyesman/epicfight/api/utils/math/OpenMatrix4f;FFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lyesman/epicfight/client/renderer/patched/item/RenderItemBase;renderItemInHand(Lnet/minecraft/world/item/ItemStack;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/world/InteractionHand;Lyesman/epicfight/model/armature/HumanoidArmature;[Lyesman/epicfight/api/utils/math/OpenMatrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;I)V"
            )
    )
    private void annoyingVillagers$renderHookGunWithHandContext(
            RenderItemBase renderer,
            ItemStack stack,
            LivingEntityPatch<?> entityPatch,
            InteractionHand hand,
            HumanoidArmature armature,
            OpenMatrix4f[] poses,
            MultiBufferSource buffer,
            PoseStack poseStack,
            int packedLight,
            Operation<Void> original
    ) {
        Entity entity = entityPatch.getOriginal();
        if (entity instanceof LivingEntity livingEntity) {
            HookGunItemRenderer.setRenderedHandContext(livingEntity, hand);
        }

        try {
            original.call(renderer, stack, entityPatch, hand, armature, poses, buffer, poseStack, packedLight);
        } finally {
            HookGunItemRenderer.clearRenderedHandContext();
        }
    }

    private static boolean annoyingVillagers$shouldForceOffhandBucketRender(LivingEntity livingEntity) {
        ItemStack offhand = livingEntity.getOffhandItem();
        if (offhand.isEmpty()) {
            return false;
        }

        Item item = offhand.getItem();
        String itemPath = BuiltInRegistries.ITEM.getKey(item).getPath();
        return item instanceof BucketItem
                || offhand.is(Items.BUCKET)
                || itemPath.endsWith("_bucket")
                || itemPath.equals("bucket");
    }
}
