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

public class ModelVillagerKnightArmor<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelvillagerknightarmor"), "main");
    public final ModelPart Head;

    public ModelVillagerKnightArmor(ModelPart modelpart) {
        this.Head = modelpart.getChild("Head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(26, 14).addBox(-5.0F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 0).addBox(-5.0F, -10.0F, -5.0F, 10.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 12).addBox(-4.0F, -11.0F, -5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 9).addBox(-5.0F, -6.0F, -5.5F, 10.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).texOffs(0, 1).addBox(-5.0F, -5.0F, -5.5F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).texOffs(5, 11).addBox(-1.0F, -4.0F, -6.0F, 2.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(4.0F, -5.0F, -5.5F, 1.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).texOffs(11, 14).addBox(-4.0F, -4.0F, -5.5F, 8.0F, 1.0F, 0.5F, new CubeDeformation(0.0F)).texOffs(22, 24).addBox(-1.0F, -11.7F, -5.0F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-5.0F, -11.0F, -4.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(11, 10).addBox(-4.0F, -11.0F, 4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 29).addBox(-5.0F, -3.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(33, 12).addBox(-5.0F, -3.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(33, 8).addBox(-5.0F, -3.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(22, 15).addBox(-5.0F, -2.0F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(15, 15).addBox(-5.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 25).addBox(3.0F, -3.0F, -5.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 34).addBox(-5.0F, -2.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(22, 9).addBox(-0.5F, -13.0F, -6.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(33, 8).addBox(-0.5F, -14.0F, -6.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 25).addBox(-0.5F, -15.0F, -6.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(7, 28).addBox(4.0F, -2.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 16).addBox(-5.0F, -10.0F, -4.0F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(5, 13).addBox(4.0F, -1.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 15).addBox(4.0F, -2.0F, 2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 13).addBox(4.0F, -3.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(4.0F, -10.0F, -4.0F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 32).addBox(-4.0F, -10.0F, 4.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 34).addBox(-3.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 6).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(4.0F, -3.0F, -4.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(5, 9).addBox(4.0F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, -3.0F, -6.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void renderToBuffer(PoseStack posestack, VertexConsumer vertexconsumer, int i, int j, float f, float f1, float f2, float f3) {
        this.Head.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
    }

    public void setupAnim(T t0, float f, float f1, float f2, float f3, float f4) {
        this.Head.yRot = f3 / 57.295776F;
        this.Head.xRot = f4 / 57.295776F;
    }
}
