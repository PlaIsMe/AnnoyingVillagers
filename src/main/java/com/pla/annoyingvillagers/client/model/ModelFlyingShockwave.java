package com.pla.annoyingvillagers.client.model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.FlyingShockwaveProjectile;
import com.pla.annoyingvillagers.client.compat.LegacyHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class ModelFlyingShockwave<T extends FlyingShockwaveProjectile> extends LegacyHierarchicalModel<T>
{
    private final ModelPart bb_main;
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "flying_shockwave"), "main");

    public ModelFlyingShockwave(ModelPart root) {
        super(root);
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Core = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offsetAndRotation(25.375F, 2.0708F, 0.5F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r1 = Core.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(30, 0).addBox(1.0F, -3.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 27).addBox(0.0F, -9.0F, 0.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(24.625F, 9.6792F, -0.5F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r2 = Core.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 20).addBox(-1.0F, -9.0F, 0.0F, 5.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0116F, 3.0054F, -0.5F, 0.0F, 0.0F, -0.9163F));

        PartDefinition cube_r3 = Core.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 10).addBox(-2.0F, -9.0F, 0.0F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.7758F, -1.9514F, -0.5F, 0.0F, 0.0F, -1.1781F));

        PartDefinition cube_r4 = Core.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -9.0F, 0.0F, 7.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.9916F, -5.091F, -0.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r5 = Core.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, 0.0F, 7.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.9916F, -5.091F, -0.5F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r6 = Core.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 0).addBox(-4.0F, -9.0F, 0.0F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.7758F, -1.9514F, -0.5F, 0.0F, 0.0F, 1.1781F));

        PartDefinition cube_r7 = Core.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -9.0F, 0.0F, 5.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-22.0116F, 3.0054F, -0.5F, 0.0F, 0.0F, 0.9163F));

        PartDefinition cube_r8 = Core.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, -3.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 20).addBox(-4.0F, -9.0F, 0.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.625F, 9.6792F, -0.5F, 0.0F, 0.0F, 0.4363F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    public void renderLegacy(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }


    @Override
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4)
    {

    }
}
