package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelAvGolem;
import com.pla.annoyingvillagers.client.model.ModelAvGolemArmor;
import com.pla.annoyingvillagers.entity.AvGolem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.ItemStack;

public class AvGolemArmorLayer extends RenderLayer<AvGolem, ModelAvGolem> {
    private final ModelAvGolemArmor armorModel;

    public AvGolemArmorLayer(RenderLayerParent<AvGolem, ModelAvGolem> parent, ModelAvGolemArmor armorModel) {
        super(parent);
        this.armorModel = armorModel;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AvGolem entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        renderArmorPiece(poseStack, buffer, packedLight, entity, EquipmentSlot.HEAD);
        renderArmorPiece(poseStack, buffer, packedLight, entity, EquipmentSlot.CHEST);
    }

    private void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AvGolem entity, EquipmentSlot slot) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (!(stack.getItem() instanceof ArmorItem armorItem) || armorItem.getEquipmentSlot() != slot) return;
        ResourceLocation texture = armorTexture(armorItem);
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        float red = 1.0F;
        float green = 1.0F;
        float blue = 1.0F;
        DyedItemColor dyedColor = stack.get(DataComponents.DYED_COLOR);
        if (dyedColor != null) {
            int color = dyedColor.rgb();
            red = (color >> 16 & 255) / 255.0F;
            green = (color >> 8 & 255) / 255.0F;
            blue = (color & 255) / 255.0F;
        }
        poseStack.pushPose();
        if (slot == EquipmentSlot.HEAD) {
            this.getParentModel().translateToHead(poseStack);
            this.armorModel.renderHelmet(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue);
        } else {
            this.getParentModel().translateToChest(poseStack);
            this.armorModel.renderChestplate(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue);
        }
        poseStack.popPose();
    }

    private static ResourceLocation armorTexture(ArmorItem armorItem) {
        ResourceLocation material = armorItem.getMaterial().unwrapKey()
                .map(key -> key.location()).orElse(ResourceLocation.withDefaultNamespace("leather"));
        return ResourceLocation.fromNamespaceAndPath(material.getNamespace(), "textures/models/armor/" + material.getPath() + "_layer_1.png");
    }
}
