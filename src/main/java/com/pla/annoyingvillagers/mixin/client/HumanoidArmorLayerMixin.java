package com.pla.annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import com.pla.annoyingvillagers.client.model.HumanoidArmorPoseBridge;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintRenderTypes;
import com.pla.annoyingvillagers.client.renderer.ColoredGlintState;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jspecify.annotations.Nullable;

/** Carries armor-stack color selection through the 26.1 equipment submitter. */
@Mixin(EquipmentLayerRenderer.class)
public abstract class HumanoidArmorLayerMixin {
    private static final String RENDER_LAYERS =
            "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;"
                    + "Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;"
                    + "Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V";

    @Inject(method = RENDER_LAYERS, at = @At("HEAD"))
    private <S> void av$setArmorTarget(EquipmentClientInfo.LayerType layerType,
                                       ResourceKey<EquipmentAsset> equipmentAssetId,
                                       Model<? super S> model, S state, ItemStack stack,
                                       PoseStack poseStack, SubmitNodeCollector collector, int light,
                                       @Nullable Identifier playerTexture, int outlineColor, int order,
                                       CallbackInfo ci) {
        LivingEntity wearer = state instanceof LivingEntityRenderState livingState
                ? VanillaOverlayRenderStateCache.getEntity(livingState) : null;
        if (state instanceof HumanoidRenderState humanoidState) {
            HumanoidArmorPoseBridge.capture(wearer, humanoidState);
        }
        ColoredGlintState.setTargetStack(stack, wearer);
    }

    @ModifyExpressionValue(method = RENDER_LAYERS, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;armorEntityGlint()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
    private RenderType av$replaceArmorGlint(RenderType original) {
        return ColoredGlintRenderTypes.getArmorEntityGlint(ColoredGlintState.getMode(), original);
    }

    @Inject(method = RENDER_LAYERS, at = @At("RETURN"))
    private <S> void av$clearArmorTarget(EquipmentClientInfo.LayerType layerType,
                                         ResourceKey<EquipmentAsset> equipmentAssetId,
                                         Model<? super S> model, S state, ItemStack stack,
                                         PoseStack poseStack, SubmitNodeCollector collector, int light,
                                         @Nullable Identifier playerTexture, int outlineColor, int order,
                                         CallbackInfo ci) {
        ColoredGlintState.clear();
    }
}
