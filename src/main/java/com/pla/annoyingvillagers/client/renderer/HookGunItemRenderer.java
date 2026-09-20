package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.HookGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

/** Dynamic hook-gun attachment implemented with the 26.1 special item-model API. */
public final class HookGunItemRenderer implements SpecialModelRenderer<ItemStack> {
    public static final Identifier TYPE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "hook_gun_attachment");
    private final ItemModelResolver itemModelResolver;

    public HookGunItemRenderer() {
        this.itemModelResolver = Minecraft.getInstance().getItemModelResolver();
    }

    @Override
    public @Nullable ItemStack extractArgument(ItemStack hookGun) {
        ItemStack boundItem = HookGunItem.getBoundItem(hookGun);
        if (boundItem.isEmpty() || HookGunItem.isVisualHookOut(hookGun) || isHookingWithRenderedStack(hookGun)) {
            return null;
        }
        return boundItem.copy();
    }

    @Override
    public void submit(@Nullable ItemStack boundItem, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                       int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        if (boundItem == null || boundItem.isEmpty()) return;

        poseStack.pushPose();
        HookItemRenderTransforms.applyHookGunAttachment(poseStack, boundItem,
                net.minecraft.world.item.ItemDisplayContext.GUI);
        ItemStackRenderState boundState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(boundState, boundItem,
                HookItemRenderTransforms.getHookGunAttachmentDisplayContext(
                        boundItem, net.minecraft.world.item.ItemDisplayContext.GUI),
                Minecraft.getInstance().level, null, 0);
        boundState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, outlineColor);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        // The body model supplies the composite model extents; the attachment is dynamic.
    }

    private static boolean isHookingWithRenderedStack(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || minecraft.level == null) return false;

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        if (stack == mainHand) return HookGunItem.hasActiveHook(minecraft.level, player, true);
        if (stack == offHand) return HookGunItem.hasActiveHook(minecraft.level, player, false);
        boolean matchesMainHand = ItemStack.matches(stack, mainHand);
        boolean matchesOffHand = ItemStack.matches(stack, offHand);
        if (matchesMainHand && !matchesOffHand) return HookGunItem.hasActiveHook(minecraft.level, player, true);
        if (matchesOffHand && !matchesMainHand) return HookGunItem.hasActiveHook(minecraft.level, player, false);
        return matchesMainHand && HookGunItem.hasActiveHook(minecraft.level, player);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<ItemStack> {
        public static final Unbaked INSTANCE = new Unbaked();
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public SpecialModelRenderer<ItemStack> bake(SpecialModelRenderer.BakingContext context) {
            return new HookGunItemRenderer();
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked<ItemStack>> type() {
            return MAP_CODEC;
        }
    }
}
