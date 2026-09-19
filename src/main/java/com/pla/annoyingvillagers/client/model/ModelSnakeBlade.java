package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelSnakeBlade<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelsnakeblade"), "main");
    private final ModelPart bb_main;

    public ModelSnakeBlade(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 0).addBox(-8.5F, -11.0F, 18.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -11.0F, 17.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -11.0F, 16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -11.0F, 15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -11.0F, 14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -11.0F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 0).addBox(-8.5F, -11.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 0).addBox(-8.5F, -11.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.5F, -11.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -10.0F, 18.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -10.0F, 17.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -10.0F, 16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -10.0F, 15.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-8.6F, -10.0F, 14.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -10.0F, 13.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.8F, -10.0F, 12.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 0).addBox(-8.8F, -10.0F, 11.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.8F, -10.0F, 10.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -9.0F, 17.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -9.0F, 16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -9.0F, 15.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -9.0F, 14.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -9.0F, 13.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 2).addBox(-8.7F, -9.0F, 12.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-8.7F, -9.0F, 11.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -9.0F, 10.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -8.0F, 16.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -8.0F, 15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 4).addBox(-8.7F, -8.0F, 14.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 4).addBox(-8.7F, -8.0F, 13.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -8.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 4).addBox(-8.7F, -8.0F, 11.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 4).addBox(-8.8F, -8.0F, 10.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -7.0F, 15.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -7.0F, 14.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(-8.7F, -7.0F, 13.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-8.7F, -7.0F, 12.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.7F, -7.0F, 11.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.7F, -7.0F, 10.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 8).addBox(-8.5F, -6.0F, 14.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 4).addBox(-8.7F, -6.0F, 13.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-8.8F, -6.0F, 12.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 6).addBox(-8.7F, -6.0F, 11.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 6).addBox(-8.7F, -6.0F, 10.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -5.0F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 8).addBox(-8.5F, -5.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -5.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 8).addBox(-8.8F, -5.0F, 10.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -4.0F, 12.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 8).addBox(-8.5F, -4.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -4.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -3.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -3.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -2.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -10.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -9.0F, 9.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.8F, -8.0F, 9.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.7F, -7.0F, 9.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 6).addBox(-8.7F, -6.0F, 9.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 8).addBox(-8.7F, -5.0F, 9.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 8).addBox(-8.8F, -4.0F, 9.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-8.5F, -3.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 0).addBox(-8.5F, -1.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -2.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-8.5F, -10.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-8.7F, -9.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 2).addBox(-8.5F, -8.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 2).addBox(-8.5F, -9.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-8.7F, -8.0F, 6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(-8.5F, -9.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -8.0F, 7.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.8F, -8.0F, 8.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 4).addBox(-8.5F, -7.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -7.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -7.0F, 5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -7.0F, 6.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.8F, -7.0F, 7.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.7F, -7.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 6).addBox(-8.5F, -6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 6).addBox(-8.7F, -6.0F, 3.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -6.0F, 4.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -6.0F, 5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.7F, -6.0F, 6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 6).addBox(-8.7F, -6.0F, 7.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 6).addBox(-8.7F, -6.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.8F, -5.0F, 3.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 2).addBox(-8.8F, -5.0F, 4.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.7F, -5.0F, 5.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 6).addBox(-8.7F, -5.0F, 6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 6).addBox(-8.7F, -5.0F, 7.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 6).addBox(-8.7F, -5.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 4).addBox(-8.7F, -4.0F, 4.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 8).addBox(-8.7F, -4.0F, 5.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-8.8F, -4.0F, 6.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -4.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-8.8F, -4.0F, 8.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -3.0F, 5.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -2.0F, 6.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.6F, -3.0F, 6.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.5F, -1.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.5F, -2.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -3.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -3.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -1.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.5F, -2.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -7.0F, -13.0F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
