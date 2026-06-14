package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.HookGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HookGunItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation ROPE_MODEL =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "item/hook_gun_rope");

    public static HookGunItemRenderer instance;

    public HookGunItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = createInstance();
        event.registerReloadListener(instance);
    }

    @SubscribeEvent
    public static void onRegisterAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(ROPE_MODEL);
    }

    public static HookGunItemRenderer getInstance() {
        if (instance == null) {
            instance = createInstance();
        }

        return instance;
    }

    private static HookGunItemRenderer createInstance() {
        return new HookGunItemRenderer(
                Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels()
        );
    }

    @Override
    public void renderByItem(
            ItemStack stack,
            @NotNull ItemDisplayContext itemDisplayContext,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int combinedLight,
            int combinedOverlay
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        BakedModel ropeModel = minecraft.getModelManager().getModel(ROPE_MODEL);

        renderBakedModel(itemRenderer, ropeModel, stack, poseStack, buffer, combinedLight, combinedOverlay);

        ItemStack boundItem = HookGunItem.getBoundItem(stack);
        if (boundItem.isEmpty() || isHookingWithRenderedStack(minecraft, stack)) {
            return;
        }

        poseStack.pushPose();
        HookItemRenderTransforms.applyHookGunAttachment(poseStack, boundItem, itemDisplayContext);
        if (HookItemRenderTransforms.shouldUseDisplayAttachmentRenderer(boundItem, itemDisplayContext)) {
            itemRenderer.renderStatic(
                    boundItem,
                    HookItemRenderTransforms.getHookGunAttachmentDisplayContext(boundItem, itemDisplayContext),
                    combinedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    minecraft.level,
                    0
            );
        } else {
            BakedModel boundModel = itemRenderer.getModel(boundItem, minecraft.level, null, 0);
            renderBakedModel(itemRenderer, boundModel, boundItem, poseStack, buffer, combinedLight, combinedOverlay);
        }
        poseStack.popPose();
    }

    private static boolean isHookingWithRenderedStack(Minecraft minecraft, ItemStack stack) {
        Player player = minecraft.player;
        if (player == null || minecraft.level == null) {
            return false;
        }

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (stack == mainHand) {
            return HookGunItem.hasActiveHook(minecraft.level, player, true);
        }
        if (stack == offHand) {
            return HookGunItem.hasActiveHook(minecraft.level, player, false);
        }

        boolean matchesMainHand = ItemStack.matches(stack, mainHand);
        boolean matchesOffHand = ItemStack.matches(stack, offHand);
        if (matchesMainHand && !matchesOffHand) {
            return HookGunItem.hasActiveHook(minecraft.level, player, true);
        }
        if (matchesOffHand && !matchesMainHand) {
            return HookGunItem.hasActiveHook(minecraft.level, player, false);
        }
        if (matchesMainHand) {
            return HookGunItem.hasActiveHook(minecraft.level, player);
        }

        return false;
    }

    private static void renderBakedModel(
            ItemRenderer itemRenderer,
            BakedModel model,
            ItemStack stack,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int combinedLight,
            int combinedOverlay
    ) {
        for (BakedModel renderPass : model.getRenderPasses(stack, true)) {
            for (RenderType renderType : renderPass.getRenderTypes(stack, true)) {
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, renderType, true, stack.hasFoil());
                itemRenderer.renderModelLists(renderPass, stack, combinedLight, combinedOverlay, poseStack, vertexConsumer);
            }
        }
    }
}
