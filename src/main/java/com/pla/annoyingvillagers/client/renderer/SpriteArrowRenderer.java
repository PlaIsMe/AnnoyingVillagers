package com.pla.annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.util.LegacyItemData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.renderer.entity.TippableArrowRenderer.NORMAL_ARROW_LOCATION;

public class SpriteArrowRenderer extends LegacyEntityRenderer<AbstractArrow> {

    private final ItemModelResolver itemModelResolver;

    public SpriteArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void extractRenderState(AbstractArrow arrow, LegacyEntityRenderState<AbstractArrow> state, float partialTick) {
        super.extractRenderState(arrow, state, partialTick);
        this.itemModelResolver.updateForNonLiving(state.item, getPickupItem(arrow), ItemDisplayContext.GROUND, arrow);
    }

    @Override
    public void submit(LegacyEntityRenderState<AbstractArrow> state, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        AbstractArrow abstractArrow = state.entity;
        float pPartialTicks = state.partialTick;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, abstractArrow.yRotO, abstractArrow.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, abstractArrow.xRotO, abstractArrow.getXRot())));
        poseStack.translate(-0.2, 0, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-45));
        poseStack.scale(1.5f,1.5f,1.5f);
        state.item.submit(poseStack, submitNodeCollector, state.lightCoords,
                OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    private static ItemStack getPickupItem(AbstractArrow abstractArrow) {
        ItemStack pickupItem = abstractArrow.getPickupItemStackOrigin().copy();
        if (pickupItem.is(Items.ARROW) && abstractArrow instanceof Arrow arrow) {
            int color = arrow.getColor();
            if (color != -1) {
                pickupItem = Items.TIPPED_ARROW.getDefaultInstance();
                LegacyItemData.update(pickupItem, tag -> tag.putInt("CustomPotionColor", color));
            }
        }
        return pickupItem;
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull AbstractArrow entity) {
        return NORMAL_ARROW_LOCATION;
    }
}
