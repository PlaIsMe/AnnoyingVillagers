package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.compat.LegacyHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public class ModelSnakeBladeFragment<T extends Entity> extends LegacyHierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelsnakebladefragment"), "main");
    private final ModelPart bb_main;

    public ModelSnakeBladeFragment(ModelPart root) {
        super(root);
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(2, 23).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -13.0F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(2, 23).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -16.0F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(4, 25).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -15.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(8, 27).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -13.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(10, 27).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -15.0F, -2.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(5, 24).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -14.0F, 1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(5, 25).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -14.0F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(4, 25).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -15.0F, -1.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(10, 27).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -14.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r10 = bb_main.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(4, 27).addBox(-8.7F, 2.0F, -6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -14.0F, -2.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r11 = bb_main.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(4, 0).addBox(-8.8F, 8.0F, 10.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 2).addBox(-8.6F, 9.0F, 9.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-8.6F, 8.0F, 9.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-8.6F, 10.0F, 8.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-8.5F, 9.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.7F, 8.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 10.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 9.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-8.6F, 8.0F, 7.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 13.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 6).addBox(-8.6F, 12.0F, 6.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 10.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-8.5F, 9.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 8.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 12.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.8F, 10.0F, 5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.5F, 9.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 8.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 12.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 10.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.8F, 9.0F, 4.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-8.5F, 8.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 10.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.8F, 9.0F, 3.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 8).addBox(-8.7F, 8.0F, 3.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 12.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 9.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 12).addBox(-8.7F, 8.0F, 2.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 11.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 8.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 6.0F, 10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 4.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 5.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 6.0F, 9.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-8.5F, 3.0F, 8.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-8.7F, 4.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-8.8F, 5.0F, 8.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-8.8F, 6.0F, 8.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 1.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 2.0F, 7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 4).addBox(-8.6F, 3.0F, 7.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 2).addBox(-8.8F, 4.0F, 7.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-8.8F, 5.0F, 7.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-8.7F, 6.0F, 7.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 10).addBox(-8.7F, 1.0F, 6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-8.8F, 2.0F, 6.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-8.8F, 3.0F, 6.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-8.8F, 4.0F, 6.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.6F, 5.0F, 6.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 6).addBox(-8.7F, 6.0F, 6.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-8.8F, 2.0F, 5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-8.8F, 3.0F, 5.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-8.7F, 4.0F, 5.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-8.6F, 5.0F, 5.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.6F, 6.0F, 5.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 3.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 4.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 8).addBox(-8.6F, 5.0F, 4.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 6.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 4.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 5.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 6.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 12).addBox(-8.7F, 5.0F, 2.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 6.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 11.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-8.8F, 7.0F, 10.0F, 1.6F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-8.7F, 7.0F, 9.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 4).addBox(-8.7F, 7.0F, 8.0F, 1.4F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-8.6F, 7.0F, 7.0F, 1.2F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 6.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 7.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 14).addBox(-8.5F, 12.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -11.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 32);
    }


    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
    public void renderLegacy(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
