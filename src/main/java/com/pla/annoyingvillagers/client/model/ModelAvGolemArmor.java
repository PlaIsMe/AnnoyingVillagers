package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelAvGolemArmor {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "model_av_golem_armor"), "main");
    private final ModelPart helmet;
    private final ModelPart chestplate;

    public ModelAvGolemArmor(ModelPart root) {
        this.helmet = root.getChild("helmet");
        this.chestplate = root.getChild("chestplate");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.5F, -5.0F, 10.0F, 11.0F, 10.0F, new CubeDeformation(0.35F)), PartPose.ZERO);
        root.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(16, 16).addBox(-9.5F, -12.5F, -6.5F, 19.0F, 13.0F, 13.0F, new CubeDeformation(0.35F)), PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 32);
    }

    public void renderHelmet(PoseStack poseStack, VertexConsumer consumer, int packedLight, int overlay, float red, float green, float blue) {
        this.helmet.render(poseStack, consumer, packedLight, overlay, net.minecraft.util.FastColor.ARGB32.colorFromFloat(1.0F, red, green, blue));
    }

    public void renderChestplate(PoseStack poseStack, VertexConsumer consumer, int packedLight, int overlay, float red, float green, float blue) {
        this.chestplate.render(poseStack, consumer, packedLight, overlay, net.minecraft.util.FastColor.ARGB32.colorFromFloat(1.0F, red, green, blue));
    }
}
