package com.pla.annoyinglib.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.init.AnnoyingLibModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingLib.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ShieldRendererEvent extends BlockEntityWithoutLevelRenderer {
    public static ShieldRendererEvent instance;

    public ShieldRendererEvent(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new ShieldRendererEvent(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }

    @Override
    public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext itemDisplayContext, PoseStack matrixStack, @NotNull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        matrixStack.scale(1, -1, -1);
        Material renderMaterial = ModelBakery.NO_PATTERN_SHIELD;

        Item shield = stack.getItem();
        if (shield == AnnoyingLibModItems.JESSICA_THE_DARK_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_JESSICA_THE_DARK_SHIELD;
        } else if (shield == AnnoyingLibModItems.HEATER_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_HEATER_SHIELD;
        } else if (shield == AnnoyingLibModItems.GEM_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_GEM_SHIELD;
        } else if (shield == AnnoyingLibModItems.NETHERITE_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_NETHERITE_SHIELD;
        }
        VertexConsumer ivertexBuilder = renderMaterial.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, shieldModel.renderType(renderMaterial.atlasLocation()), true, stack.hasFoil()));
        this.shieldModel.handle().render(matrixStack, ivertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        this.shieldModel.plate().render(matrixStack, ivertexBuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }
}
