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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ModelGreenVillagerKnightArmor<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelgreenvillagerknightarmor"), "main");
    public final ModelPart Body;
    public final ModelPart RightArm;
    public final ModelPart LeftArm;
    public final ModelPart RightLeg;
    public final ModelPart LeftLeg;

    public ModelGreenVillagerKnightArmor(ModelPart modelpart) {
        this.Body = modelpart.getChild("Body");
        this.RightArm = modelpart.getChild("RightArm");
        this.LeftArm = modelpart.getChild("LeftArm");
        this.RightLeg = modelpart.getChild("RightLeg");
        this.LeftLeg = modelpart.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(18, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.2F, 3.0F, 10.0F, 19.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(28, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.1745F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(12, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, 0.2094F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(22, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.192F, 0.0F, 0.0349F));
        partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, -0.1745F, 0.0F, -0.0349F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void renderToBuffer(PoseStack posestack, VertexConsumer vertexconsumer, int i, int j, float f, float f1, float f2, float f3) {
        this.Body.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
        this.RightArm.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
        this.LeftArm.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
        this.RightLeg.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
        this.LeftLeg.render(posestack, vertexconsumer, i, j, f, f1, f2, f3);
    }

    public void setupAnim(T t0, float f, float f1, float f2, float f3, float f4) {
        this.RightArm.xRot = Mth.cos(f * 0.6662F + 3.1415927F) * f1;
        this.LeftLeg.xRot = Mth.cos(f * 1.0F) * -1.0F * f1;
        this.LeftArm.xRot = Mth.cos(f * 0.6662F) * f1;
        this.RightLeg.xRot = Mth.cos(f * 1.0F) * 1.0F * f1;
    }
}
