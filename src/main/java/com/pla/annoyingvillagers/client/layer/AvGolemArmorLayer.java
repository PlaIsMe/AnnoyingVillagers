package com.pla.annoyingvillagers.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.model.ModelAvGolem;
import com.pla.annoyingvillagers.client.model.ModelAvGolemArmor;
import com.pla.annoyingvillagers.entity.AvGolem;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import com.pla.annoyingvillagers.item.LegacyArmorItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.ItemStack;

public class AvGolemArmorLayer extends RenderLayer<LegacyEntityRenderState<AvGolem>, ModelAvGolem> {
    private final ModelAvGolemArmor armorModel;

    public AvGolemArmorLayer(RenderLayerParent<LegacyEntityRenderState<AvGolem>, ModelAvGolem> parent, ModelAvGolemArmor armorModel) {
        super(parent);
        this.armorModel = armorModel;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                       LegacyEntityRenderState<AvGolem> state, float yRot, float xRot) {
        renderArmorPiece(poseStack, collector, packedLight, state.entity, EquipmentSlot.HEAD);
        renderArmorPiece(poseStack, collector, packedLight, state.entity, EquipmentSlot.CHEST);
    }

    private void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, AvGolem entity, EquipmentSlot slot) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (!(stack.getItem() instanceof LegacyArmorItem armorItem) || armorItem.getEquipmentSlot() != slot) return;
        Identifier texture = armorTexture(armorItem, stack, entity, slot);
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
            float finalRed = red, finalGreen = green, finalBlue = blue;
            collector.submitCustomGeometry(poseStack,
                    net.minecraft.client.renderer.rendertype.RenderTypes.armorCutoutNoCull(texture),
                    (rootPose, consumer) -> {
                        PoseStack stackPose = new PoseStack(); stackPose.last().set(rootPose);
                        this.armorModel.renderHelmet(stackPose, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                                finalRed, finalGreen, finalBlue);
                    });
        } else {
            this.getParentModel().translateToChest(poseStack);
            float finalRed = red, finalGreen = green, finalBlue = blue;
            collector.submitCustomGeometry(poseStack,
                    net.minecraft.client.renderer.rendertype.RenderTypes.armorCutoutNoCull(texture),
                    (rootPose, consumer) -> {
                        PoseStack stackPose = new PoseStack(); stackPose.last().set(rootPose);
                        this.armorModel.renderChestplate(stackPose, consumer, packedLight, OverlayTexture.NO_OVERLAY,
                                finalRed, finalGreen, finalBlue);
                    });
        }
        poseStack.popPose();
    }

    private static Identifier armorTexture(LegacyArmorItem armorItem, ItemStack stack, AvGolem entity, EquipmentSlot slot) {
        String custom = armorItem.getArmorTexture(stack, entity, slot, null);
        return custom == null ? Identifier.withDefaultNamespace("textures/models/armor/leather_layer_1.png")
                : Identifier.parse(custom);
    }
}
