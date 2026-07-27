/*
 * AnnoyingVillagers - Third-Party Derived File Notice
 *
 * SPDX-License-Identifier: MIT
 *
 * Upstream: Sprite Arrows - iliandev
 * Source: https://github.com/justliliandev/arrow-sprites
 *
 * This file contains code adapted from the upstream project.
 * Required upstream notices must be preserved.
 *
 * License texts:
 *   - third_party/licenses/MIT.md
 *
 * Modifications:
 *   Copyright (c) 2026 pla_is_me
 */

package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.renderer.entity.TippableArrowRenderer.NORMAL_ARROW_LOCATION;

public class SpriteArrowRenderer extends EntityRenderer<AbstractArrow> {

    private final ItemRenderer renderer;

    public SpriteArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
        renderer = context.getItemRenderer();
    }

    @Override
    public void render(AbstractArrow abstractArrow, float pEntityYaw, float pPartialTicks, PoseStack poseStack, MultiBufferSource buffer, int pPackedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, abstractArrow.yRotO, abstractArrow.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, abstractArrow.xRotO, abstractArrow.getXRot())));
        poseStack.translate(-0.2, 0, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-45));
        poseStack.scale(1.5f,1.5f,1.5f);
        ItemStack pickupItem = abstractArrow.getPickupItem();
        if (pickupItem.is(Items.ARROW) && abstractArrow instanceof Arrow arrow) {
            int color = arrow.getColor();
            if (color != -1) {
                pickupItem = Items.TIPPED_ARROW.getDefaultInstance();
                pickupItem.getOrCreateTag().putInt("CustomPotionColor", color);
            }
        }
        renderer.renderStatic(pickupItem, ItemDisplayContext.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, abstractArrow.level(), abstractArrow.getId());
        poseStack.popPose();
        super.render(abstractArrow, pEntityYaw, pPartialTicks, poseStack, buffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractArrow entity) {
        return NORMAL_ARROW_LOCATION;
    }
}