package com.pla.annoyingvillagers.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingVillagers.MODID)
public class ShieldRendererEvent extends BlockEntityWithoutLevelRenderer {
    public static ShieldRendererEvent instance;
    private ShieldModel customShieldModel;

    public ShieldRendererEvent(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        super.onResourceManagerReload(resourceManager);
        this.customShieldModel = new ShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.SHIELD));
    }

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new ShieldRendererEvent(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        IClientItemExtensions extensions = new IClientItemExtensions() {
            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return instance;
            }
        };
        event.registerItem(extensions,
                AnnoyingVillagersModItems.ENDER_AEGIS.get(),
                AnnoyingVillagersModItems.GEM_SHIELD.get(),
                AnnoyingVillagersModItems.HEATER_SHIELD.get(),
                AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get(),
                AnnoyingVillagersModItems.NETHERITE_SHIELD.get());
    }

    @Override
    public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext itemDisplayContext, PoseStack matrixStack, @NotNull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        matrixStack.scale(1, -1, -1);
        Material renderMaterial = ModelBakery.NO_PATTERN_SHIELD;

        Item shield = stack.getItem();
        if (shield == AnnoyingVillagersModItems.JESSICA_THE_DARK_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_JESSICA_THE_DARK_SHIELD;
        } else if (shield == AnnoyingVillagersModItems.HEATER_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_HEATER_SHIELD;
        } else if (shield == AnnoyingVillagersModItems.GEM_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_GEM_SHIELD;
        } else if (shield == AnnoyingVillagersModItems.NETHERITE_SHIELD.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_NETHERITE_SHIELD;
        } else if (shield == AnnoyingVillagersModItems.ENDER_AEGIS.get()) {
            renderMaterial = ModModelPredicateProvider.LOCATION_ENDER_AEGIS;
        }
        VertexConsumer ivertexBuilder = renderMaterial.sprite().wrap(ItemRenderer.getFoilBufferDirect(buffer, customShieldModel.renderType(renderMaterial.atlasLocation()), true, stack.hasFoil()));
        this.customShieldModel.handle().render(matrixStack, ivertexBuilder, combinedLight, combinedOverlay, -1);
        this.customShieldModel.plate().render(matrixStack, ivertexBuilder, combinedLight, combinedOverlay, -1);
        matrixStack.popPose();
    }
}
