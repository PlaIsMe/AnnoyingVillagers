package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModelBlueDemonTrident<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelbluedemontrident"), "main");
    public final ModelPart trident;

    public ModelBlueDemonTrident(ModelPart modelpart) {
        this.trident = modelpart.getChild("trident");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("trident", CubeListBuilder.create().texOffs(0, 0).addBox(-0.51F, -28.01F, -0.51F, 1.02F, 31.02F, 1.02F, new CubeDeformation(0.0F)).texOffs(4, 0).addBox(-1.5F, -24.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 3).addBox(-2.5F, -27.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 3).addBox(1.5F, -27.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, -3.1416F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void renderToBuffer(PoseStack posestack, VertexConsumer vertexconsumer, int i, int j, int color) {
        this.trident.render(posestack, vertexconsumer, i, j, color);
    }

    public void setupAnim(T t0, float f, float f1, float f2, float f3, float f4) {}
}
