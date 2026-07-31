package com.pla.annoyingvillagers.client.model;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.entity.EquipmentSlot.*;

public class ModelRigArmor<T extends Mob> extends HumanoidModel<T> {

    public static final ModelLayerLocation INNER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelrigarmor_inner"), "main");

    public static final ModelLayerLocation OUTER_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelrigarmor_outer"), "main");

    private final ModelPart right_hand;
    private final ModelPart left_hand;
    private final ModelPart right_lower_leg;
    private final ModelPart left_lower_leg;

    public ModelRigArmor(ModelPart root) {
        super(root);
        this.right_hand = this.rightArm.getChild("right_hand");
        this.left_hand = this.leftArm.getChild("left_hand");
        this.right_lower_leg = this.rightLeg.getChild("right_lower_leg");
        this.left_lower_leg = this.leftLeg.getChild("left_lower_leg");
    }

    public static LayerDefinition createInnerLayer() {
        return createBodyLayer(new CubeDeformation(0.5F));
    }

    public static LayerDefinition createOuterLayer() {
        return createBodyLayer(new CubeDeformation(1.0F));
    }

    public void copySegmentPoses(ModelPart sourceRightHand, ModelPart sourceLeftHand, ModelPart sourceRightLowerLeg, ModelPart sourceLeftLowerLeg) {
        this.right_hand.copyFrom(sourceRightHand);
        this.left_hand.copyFrom(sourceLeftHand);

        this.right_lower_leg.copyFrom(sourceRightLowerLeg);
        this.left_lower_leg.copyFrom(sourceLeftLowerLeg);
    }

    private static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation), PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offset(-5.0F, 2.0F, 0.0F));

        rightArm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(40, 22).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offset(5.0F, 2.0F, 0.0F));

        leftArm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(40, 22).mirror().addBox(-1.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offset(-1.9F, 12.0F, 0.0F));

        rightLeg.addOrReplaceChild("right_lower_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, deformation), PartPose.offset(1.9F, 12.0F, 0.0F));

        leftLeg.addOrReplaceChild("left_lower_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, deformation), PartPose.offset(0.0F, 6.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setVisibleForSlot(EquipmentSlot slot) {
        this.setAllVisible(false);

        this.right_hand.visible = false;
        this.left_hand.visible = false;
        this.right_lower_leg.visible = false;
        this.left_lower_leg.visible = false;

        this.head.skipDraw = false;
        this.hat.skipDraw = false;
        this.body.skipDraw = false;
        this.rightArm.skipDraw = false;
        this.leftArm.skipDraw = false;
        this.rightLeg.skipDraw = false;
        this.leftLeg.skipDraw = false;
        this.right_hand.skipDraw = false;
        this.left_hand.skipDraw = false;
        this.right_lower_leg.skipDraw = false;
        this.left_lower_leg.skipDraw = false;

        if (slot == HEAD) {
            this.head.visible = true;
            this.hat.visible = true;
        } else if (slot == CHEST) {
            this.body.visible = true;

            this.rightArm.visible = true;
            this.leftArm.visible = true;

            this.right_hand.visible = true;
            this.left_hand.visible = true;
        } else if (slot == LEGS) {
            this.body.visible = true;

            this.rightLeg.visible = true;
            this.leftLeg.visible = true;

            this.right_lower_leg.visible = true;
            this.left_lower_leg.visible = true;
        } else if (slot == FEET) {
            this.rightLeg.visible = true;
            this.leftLeg.visible = true;

            this.rightLeg.skipDraw = true;
            this.leftLeg.skipDraw = true;

            this.right_lower_leg.visible = true;
            this.left_lower_leg.visible = true;
        }
    }
}
