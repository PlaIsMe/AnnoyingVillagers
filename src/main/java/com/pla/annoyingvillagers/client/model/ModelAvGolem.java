package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationClientUtil;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationResolver;
import com.pla.annoyingvillagers.client.animation.SpecialClientAnimationState;
import com.pla.annoyingvillagers.client.animation.rig_special_animation.*;
import com.pla.annoyingvillagers.entity.AvGolem;
import com.pla.annoyingvillagers.entity.AvGolemWeaponStyle;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import com.pla.annoyingvillagers.specialanimation.pose.SpecialPoseLibrary;
import net.minecraft.client.animation.AnimationDefinition;
import com.pla.annoyingvillagers.client.compat.LegacyHierarchicalModel;
import com.pla.annoyingvillagers.client.compat.LegacyCustomRenderable;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ModelAvGolem extends LegacyHierarchicalModel<AvGolem> implements LegacyCustomRenderable {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "model_av_golem"), "main");

private final ModelPart modelRoot;
    private final ModelPart Root;
    private final ModelPart body;
    private final ModelPart chest;
    private final ModelPart head;
    private final ModelPart shoudler_L;
    private final ModelPart arm_1_L;
    private final ModelPart arm_2_L;
    private final ModelPart arm_3_L;
    private final ModelPart arm_4_L;
    private final ModelPart arm_5_L;
    private final ModelPart arm_6_L;
    private final ModelPart arm_7_L;
    private final ModelPart Tool_L;
    private final ModelPart red_core_L_3;
    private final ModelPart red_core_L_2;
    private final ModelPart red_core_L_1;
    private final ModelPart shoudler_R;
    private final ModelPart arm_1_R;
    private final ModelPart arm_2_R;
    private final ModelPart arm_3_R;
    private final ModelPart arm_4_R;
    private final ModelPart arm_5_R;
    private final ModelPart arm_6_R;
    private final ModelPart arm_7_R;
    private final ModelPart Tool_R;
    private final ModelPart red_core_R_3;
    private final ModelPart red_core_R_2;
    private final ModelPart red_core_R_1;
    private final ModelPart leg_up_L;
    private final ModelPart leg_down_L;
    private final ModelPart knee_L;
    private final ModelPart leg_up_R;
    private final ModelPart leg_down_R;
    private final ModelPart knee_R;
    private final ModelPart arm_s_L;
    private final ModelPart arm_s_R;

    public ModelAvGolem(ModelPart root) {
        super(root);
        this.modelRoot = root;
        this.Root = root.getChild("Root");
        this.body = this.Root.getChild("body");
        this.chest = this.body.getChild("chest");
        this.head = this.chest.getChild("head");
        this.shoudler_L = this.chest.getChild("shoudler_L");
        this.arm_1_L = this.shoudler_L.getChild("arm_1_L");
        this.arm_2_L = this.arm_1_L.getChild("arm_2_L");
        this.arm_3_L = this.arm_2_L.getChild("arm_3_L");
        this.arm_4_L = this.arm_3_L.getChild("arm_4_L");
        this.arm_5_L = this.arm_4_L.getChild("arm_5_L");
        this.arm_6_L = this.arm_5_L.getChild("arm_6_L");
        this.arm_7_L = this.arm_6_L.getChild("arm_7_L");
        this.Tool_L = this.arm_7_L.getChild("Tool_L");
        this.red_core_L_3 = this.arm_6_L.getChild("red_core_L_3");
        this.red_core_L_2 = this.arm_4_L.getChild("red_core_L_2");
        this.red_core_L_1 = this.arm_2_L.getChild("red_core_L_1");
        this.shoudler_R = this.chest.getChild("shoudler_R");
        this.arm_1_R = this.shoudler_R.getChild("arm_1_R");
        this.arm_2_R = this.arm_1_R.getChild("arm_2_R");
        this.arm_3_R = this.arm_2_R.getChild("arm_3_R");
        this.arm_4_R = this.arm_3_R.getChild("arm_4_R");
        this.arm_5_R = this.arm_4_R.getChild("arm_5_R");
        this.arm_6_R = this.arm_5_R.getChild("arm_6_R");
        this.arm_7_R = this.arm_6_R.getChild("arm_7_R");
        this.Tool_R = this.arm_7_R.getChild("Tool_R");
        this.red_core_R_3 = this.arm_6_R.getChild("red_core_R_3");
        this.red_core_R_2 = this.arm_4_R.getChild("red_core_R_2");
        this.red_core_R_1 = this.arm_2_R.getChild("red_core_R_1");
        this.leg_up_L = this.Root.getChild("leg_up_L");
        this.leg_down_L = this.leg_up_L.getChild("leg_down_L");
        this.knee_L = this.leg_up_L.getChild("knee_L");
        this.leg_up_R = this.Root.getChild("leg_up_R");
        this.leg_down_R = this.leg_up_R.getChild("leg_down_R");
        this.knee_R = this.leg_up_R.getChild("knee_R");
        this.arm_s_L = root.getChild("arm_s_L");
        this.arm_s_R = root.getChild("arm_s_R");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 9.50179F, 0.0F));

        PartDefinition body = Root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 70).addBox(-4.5F, -5.15531F, -3.0F, 9.0F, 5.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.34648F, 0.0F));

        PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 40).addBox(-9.0F, -11.87307F, -6.0F, 18.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.28224F, 0.0F));

        PartDefinition head = chest.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.89019F, -3.9884F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(24, 0).addBox(-1.0F, -2.89019F, -5.9884F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.98288F, -2.5116F));

        PartDefinition shoudler_L = chest.addOrReplaceChild("shoudler_L", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.98288F, 0.0F, 0.0F, 0.0F, 1.731078F));

        PartDefinition arm_1_L = shoudler_L.addOrReplaceChild("arm_1_L", CubeListBuilder.create().texOffs(-6, -4).addBox(-2.0454F, -4.00713F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-2.0454F, -3.00713F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.16618F, -11.06997F, 0.0F, 0.0F, 0.0F, 1.410515F));

        PartDefinition arm_2_L = arm_1_L.addOrReplaceChild("arm_2_L", CubeListBuilder.create(), PartPose.offset(0.0F, -0.4856F, 0.0F));

        PartDefinition arm_3_L = arm_2_L.addOrReplaceChild("arm_3_L", CubeListBuilder.create().texOffs(-1, 1).addBox(-2.0454F, 2.57227F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(-0.0454F, 2.57227F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.0454F, 2.57227F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-2.0454F, -3.42774F, -3.0F, 4.0F, 6.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(-2.0454F, 2.57227F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0938F, 0.0F));

        PartDefinition arm_4_L = arm_3_L.addOrReplaceChild("arm_4_L", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm_5_L = arm_4_L.addOrReplaceChild("arm_5_L", CubeListBuilder.create().texOffs(-6, -4).addBox(-0.0454F, 1.55318F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.0454F, 1.55318F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-2.0454F, -3.44682F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.0454F, 1.55318F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(-2.0454F, 1.55318F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(-2.0454F, 2.55318F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.98092F, 0.0F));

        PartDefinition arm_6_L = arm_5_L.addOrReplaceChild("arm_6_L", CubeListBuilder.create(), PartPose.offset(0.0F, -0.017F, 0.0F));

        PartDefinition arm_7_L = arm_6_L.addOrReplaceChild("arm_7_L", CubeListBuilder.create().texOffs(60, 80).addBox(-2.0454F, -6.80107F, -3.0F, 4.0F, 8.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(-2.0454F, 2.19894F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(-2.0454F, 1.19894F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.0454F, 1.19894F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(-2.0454F, 1.19894F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-0.0454F, 1.19894F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.62876F, 0.0F));

        PartDefinition Tool_L = arm_7_L.addOrReplaceChild("Tool_L", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.41246F, 0.0F, 1.570796F, 0.0F, 0.0F));

        PartDefinition red_core_L_3 = arm_6_L.addOrReplaceChild("red_core_L_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_L_2 = arm_4_L.addOrReplaceChild("red_core_L_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_L_1 = arm_2_L.addOrReplaceChild("red_core_L_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition shoudler_R = chest.addOrReplaceChild("shoudler_R", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.98288F, 0.0F, 0.0F, 0.0F, -1.731078F));

        PartDefinition arm_1_R = shoudler_R.addOrReplaceChild("arm_1_R", CubeListBuilder.create().texOffs(-6, -4).addBox(0.0454F, -4.00713F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-1.9546F, -3.00713F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.16618F, -11.06997F, 0.0F, 0.0F, 0.0F, -1.410515F));

        PartDefinition arm_2_R = arm_1_R.addOrReplaceChild("arm_2_R", CubeListBuilder.create(), PartPose.offset(0.0F, -0.4856F, 0.0F));

        PartDefinition arm_3_R = arm_2_R.addOrReplaceChild("arm_3_R", CubeListBuilder.create().texOffs(-1, 1).addBox(0.0454F, 2.57227F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.0454F, 2.57227F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 2.57227F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-1.9546F, -3.42774F, -3.0F, 4.0F, 6.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.0454F, 2.57227F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0938F, 0.0F));

        PartDefinition arm_4_R = arm_3_R.addOrReplaceChild("arm_4_R", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition arm_5_R = arm_4_R.addOrReplaceChild("arm_5_R", CubeListBuilder.create().texOffs(60, 74).addBox(-1.9546F, -3.44682F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(0.0454F, 1.55318F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.0454F, 1.55318F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 1.55318F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.0454F, 1.55318F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(0.0454F, 2.55318F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.98092F, 0.0F));

        PartDefinition arm_6_R = arm_5_R.addOrReplaceChild("arm_6_R", CubeListBuilder.create(), PartPose.offset(0.0F, -0.017F, 0.0F));

        PartDefinition arm_7_R = arm_6_R.addOrReplaceChild("arm_7_R", CubeListBuilder.create().texOffs(60, 80).addBox(-1.9546F, -6.80107F, -3.0F, 4.0F, 8.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 1.19894F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.0454F, 1.19894F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(0.0454F, 1.19894F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(0.0454F, 2.19894F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.0454F, 1.19894F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.62876F, 0.0F));

        PartDefinition Tool_R = arm_7_R.addOrReplaceChild("Tool_R", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.41246F, 0.0F, 1.570796F, 0.0F, 0.0F));

        PartDefinition red_core_R_3 = arm_6_R.addOrReplaceChild("red_core_R_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_R_2 = arm_4_R.addOrReplaceChild("red_core_R_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_R_1 = arm_2_R.addOrReplaceChild("red_core_R_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_up_L = Root.addOrReplaceChild("leg_up_L", CubeListBuilder.create().texOffs(60, 0).addBox(-2.96799F, -5.86434F, 1.9245F, 5.00649F, 1.03294F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 0).addBox(-3.00096F, -4.86436F, -3.0755F, 5.03286F, 5.03286F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 0).addBox(-2.96799F, -5.86434F, -3.0755F, 5.00649F, 1.03294F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 0).addBox(-2.96799F, -5.86434F, -2.0755F, 5.00649F, 1.03294F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5755F, -1.34648F, 0.0F, 1.570796F, -1.564203F, 1.570796F));

        PartDefinition leg_down_L = leg_up_L.addOrReplaceChild("leg_down_L", CubeListBuilder.create().texOffs(60, 0).addBox(-3.00328F, -8.95244F, -3.0755F, 5.05455F, 10.02717F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.90344F, 0.0F, 0.0F, 0.0F, 0.012055F));

        PartDefinition knee_L = leg_up_L.addOrReplaceChild("knee_L", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.90344F, 0.0F, 0.0F, 0.0F, -1.564203F));

        PartDefinition leg_up_R = Root.addOrReplaceChild("leg_up_R", CubeListBuilder.create().texOffs(37, 0).addBox(-2.0319F, -4.86436F, -3.0755F, 5.03286F, 5.03286F, 6.0F, new CubeDeformation(0.0F)).texOffs(37, 0).addBox(-2.0319F, -4.86436F, -3.0755F, 4.99989F, 0.03296F, 6.0F, new CubeDeformation(0.0F)).texOffs(37, 0).addBox(-2.0385F, -5.86434F, -3.0755F, 5.00649F, 1.03294F, 1.0F, new CubeDeformation(0.0F)).texOffs(37, 0).addBox(-2.0385F, -5.86434F, 1.9245F, 5.00649F, 1.03294F, 1.0F, new CubeDeformation(0.0F)).texOffs(37, 0).addBox(-2.0385F, -5.86434F, -2.0755F, 5.00649F, 1.03294F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5755F, -1.34648F, 0.0F, 1.570796F, 1.564203F, -1.570796F));

        PartDefinition leg_down_R = leg_up_R.addOrReplaceChild("leg_down_R", CubeListBuilder.create().texOffs(37, 0).addBox(-2.05127F, -8.95244F, -3.0755F, 5.05455F, 10.02717F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.90344F, 0.0F, 0.0F, 0.0F, -0.012055F));

        PartDefinition knee_R = leg_up_R.addOrReplaceChild("knee_R", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.90344F, 0.0F, 0.0F, 0.0F, 1.564203F));

        PartDefinition arm_s_L = partdefinition.addOrReplaceChild("arm_s_L", CubeListBuilder.create(), PartPose.offsetAndRotation(10.95461F, -7.50712F, 0.0F, 0.0F, 0.0F, 3.141593F));

        PartDefinition arm_s_R = partdefinition.addOrReplaceChild("arm_s_R", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.95461F, -7.50712F, 0.0F, 0.0F, 0.0F, 3.141593F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

	@Override
	public void setupAnim(@NotNull AvGolem entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Root.getAllParts().forEach(ModelPart::resetPose);
		this.arm_s_L.resetPose();
		this.arm_s_R.resetPose();
		SpecialClientAnimationState.Active active = SpecialClientAnimationState.getActive(entity, ageInTicks);
		if (active != null && active.animationId().family() == SpecialAnimationFamily.AV_GOLEM) {
			float elapsedTicks = active.elapsedTicks(ageInTicks);
			SpecialAnimationClientUtil.apply(this, SpecialAnimationResolver.resolve(active.animationId()), elapsedTicks);
			compensateServerRootMotion(active.animationId(), elapsedTicks);
			return;
		}
		AvGolemWeaponStyle style = entity.getWeaponStyle();
		if (entity.isDeadOrDying() || entity.deathTime > 0) {
			float partialTick = Mth.clamp(ageInTicks - entity.tickCount, 0.0F, 1.0F);
			SpecialAnimationClientUtil.apply(this, deathAnimation(style), Math.max(0.0F, entity.deathTime - 1.0F + partialTick));
		} else if (entity.isSprinting() || isMoving(entity, limbSwingAmount)) {
			AnimationDefinition movement = style == AvGolemWeaponStyle.NORMAL && entity.isSprinting() ? AvGolemLivingAnimations.RUN : walkAnimation(style);
			SpecialAnimationClientUtil.applyLoop(this, movement, ageInTicks);
		} else if (!entity.onGround()) {
			SpecialAnimationClientUtil.applyLoop(this, fallAnimation(style), ageInTicks);
		} else {
			SpecialAnimationClientUtil.applyLoop(this, idleAnimation(style), ageInTicks);
		}
		this.head.yRot += Mth.clamp(netHeadYaw, -45.0F, 45.0F) * ((float)Math.PI / 180.0F);
		this.head.xRot += Mth.clamp(headPitch, -25.0F, 25.0F) * ((float)Math.PI / 180.0F);
	}

	private void compensateServerRootMotion(SpecialAnimationId animationId, float elapsedTicks) {
		Vec3 motion = SpecialPoseLibrary.accumulatedRootMotion(animationId, elapsedTicks);
		this.Root.x -= (float)motion.x;
		this.Root.z -= (float)motion.z;
	}

	private static boolean isMoving(net.minecraft.world.entity.LivingEntity entity, float limbSwingAmount) {
		double dx = entity.getX() - entity.xo;
		double dz = entity.getZ() - entity.zo;
		return limbSwingAmount > 0.001F || dx * dx + dz * dz > 1.0E-8D || entity.getDeltaMovement().horizontalDistanceSqr() > 1.0E-8D;
	}

	private static AnimationDefinition idleAnimation(AvGolemWeaponStyle style) {
		return switch (style) {
			case SWORD -> AvGolemSwordLivingSkillAnimations.SWORD_IDLE;
			case DUAL_SWORD -> AvGolemDualSwordLivingSkillAnimations.DUAL_SWORD_IDLE;
			case AXE -> AvGolemAxeLivingSkillAnimations.AXE_IDLE;
			case DUAL_AXE -> AvGolemDualAxeLivingSkillAnimations.DUAL_AXE_IDLE;
			case SPEAR -> AvGolemSpearLivingSkillAnimations.SPEAR_IDLE;
			case NORMAL -> AvGolemLivingAnimations.IDLE;
		};
	}

	private static AnimationDefinition walkAnimation(AvGolemWeaponStyle style) {
		return switch (style) {
			case SWORD -> AvGolemSwordLivingSkillAnimations.SWORD_WALK;
			case DUAL_SWORD -> AvGolemDualSwordLivingSkillAnimations.DUAL_SWORD_WALK;
			case AXE -> AvGolemAxeLivingSkillAnimations.AXE_WALK;
			case DUAL_AXE -> AvGolemDualAxeLivingSkillAnimations.DUAL_AXE_WALK;
			case SPEAR -> AvGolemSpearLivingSkillAnimations.SPEAR_WALK;
			case NORMAL -> AvGolemLivingAnimations.WALK;
		};
	}

	private static AnimationDefinition fallAnimation(AvGolemWeaponStyle style) {
		return switch (style) {
			case SWORD -> AvGolemSwordLivingSkillAnimations.SWORD_FALL;
			case DUAL_SWORD -> AvGolemDualSwordLivingSkillAnimations.DUAL_SWORD_FALL;
			case AXE -> AvGolemAxeLivingSkillAnimations.AXE_FALL;
			case DUAL_AXE -> AvGolemDualAxeLivingSkillAnimations.DUAL_AXE_FALL;
			case SPEAR -> AvGolemSpearLivingSkillAnimations.SPEAR_FALL;
			case NORMAL -> AvGolemLivingAnimations.FALL;
		};
	}

	private static AnimationDefinition deathAnimation(AvGolemWeaponStyle style) {
		return switch (style) {
			case SWORD -> AvGolemSwordLivingSkillAnimations.SWORD_DEATH;
			case DUAL_SWORD -> AvGolemDualSwordLivingSkillAnimations.DUAL_SWORD_DEATH;
			case AXE -> AvGolemAxeLivingSkillAnimations.AXE_DEATH;
			case DUAL_AXE -> AvGolemDualAxeLivingSkillAnimations.DUAL_AXE_DEATH;
			case SPEAR -> AvGolemSpearLivingSkillAnimations.SPEAR_DEATH;
			case NORMAL -> AvGolemLivingAnimations.DEATH;
		};
	}

	public void translateToTool(HumanoidArm arm, PoseStack poseStack) {
		this.Root.translateAndRotate(poseStack);
		this.body.translateAndRotate(poseStack);
		this.chest.translateAndRotate(poseStack);
		if (arm == HumanoidArm.RIGHT) {
			this.shoudler_R.translateAndRotate(poseStack);
			this.arm_1_R.translateAndRotate(poseStack);
			this.arm_2_R.translateAndRotate(poseStack);
			this.arm_3_R.translateAndRotate(poseStack);
			this.arm_4_R.translateAndRotate(poseStack);
			this.arm_5_R.translateAndRotate(poseStack);
			this.arm_6_R.translateAndRotate(poseStack);
			this.arm_7_R.translateAndRotate(poseStack);
			this.Tool_R.translateAndRotate(poseStack);
		} else {
			this.shoudler_L.translateAndRotate(poseStack);
			this.arm_1_L.translateAndRotate(poseStack);
			this.arm_2_L.translateAndRotate(poseStack);
			this.arm_3_L.translateAndRotate(poseStack);
			this.arm_4_L.translateAndRotate(poseStack);
			this.arm_5_L.translateAndRotate(poseStack);
			this.arm_6_L.translateAndRotate(poseStack);
			this.arm_7_L.translateAndRotate(poseStack);
			this.Tool_L.translateAndRotate(poseStack);
		}
	}

	public void translateToHead(PoseStack poseStack) {
		this.Root.translateAndRotate(poseStack);
		this.body.translateAndRotate(poseStack);
		this.chest.translateAndRotate(poseStack);
		this.head.translateAndRotate(poseStack);
	}

	public void translateToChest(PoseStack poseStack) {
		this.Root.translateAndRotate(poseStack);
		this.body.translateAndRotate(poseStack);
		this.chest.translateAndRotate(poseStack);
	}
    @Override
    public void av$renderLegacy(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        renderLegacy(poseStack, buffer, packedLight, packedOverlay, color);
    }

    public void renderLegacy(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.modelRoot.render(poseStack, buffer, packedLight, packedOverlay, color);
        renderPerFaceCubes(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private void renderPerFaceCubes(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        // cube_arm_6_L
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.chest.translateAndRotate(poseStack);
        this.shoudler_L.translateAndRotate(poseStack);
        this.arm_1_L.translateAndRotate(poseStack);
        this.arm_2_L.translateAndRotate(poseStack);
        this.arm_3_L.translateAndRotate(poseStack);
        this.arm_4_L.translateAndRotate(poseStack);
        this.arm_5_L.translateAndRotate(poseStack);
        this.arm_6_L.translateAndRotate(poseStack);
        this.red_core_L_3.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.6454F, -6.92982F, -1.0F, 0.5546F, 0.07018F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // cube_arm_4_L
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.chest.translateAndRotate(poseStack);
        this.shoudler_L.translateAndRotate(poseStack);
        this.arm_1_L.translateAndRotate(poseStack);
        this.arm_2_L.translateAndRotate(poseStack);
        this.arm_3_L.translateAndRotate(poseStack);
        this.arm_4_L.translateAndRotate(poseStack);
        this.red_core_L_2.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.6454F, -6.92774F, -1.0F, 0.5546F, 0.07227F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // cube_arm_2_L
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.chest.translateAndRotate(poseStack);
        this.shoudler_L.translateAndRotate(poseStack);
        this.arm_1_L.translateAndRotate(poseStack);
        this.arm_2_L.translateAndRotate(poseStack);
        this.red_core_L_1.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.6454F, -7.02153F, -1.0F, 0.5546F, -0.02153F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // cube_arm_6_R
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.chest.translateAndRotate(poseStack);
        this.shoudler_R.translateAndRotate(poseStack);
        this.arm_1_R.translateAndRotate(poseStack);
        this.arm_2_R.translateAndRotate(poseStack);
        this.arm_3_R.translateAndRotate(poseStack);
        this.arm_4_R.translateAndRotate(poseStack);
        this.arm_5_R.translateAndRotate(poseStack);
        this.arm_6_R.translateAndRotate(poseStack);
        this.red_core_R_3.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5546F, -6.92982F, -1.0F, 0.6454F, 0.07018F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // cube_arm_4_R
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.chest.translateAndRotate(poseStack);
        this.shoudler_R.translateAndRotate(poseStack);
        this.arm_1_R.translateAndRotate(poseStack);
        this.arm_2_R.translateAndRotate(poseStack);
        this.arm_3_R.translateAndRotate(poseStack);
        this.arm_4_R.translateAndRotate(poseStack);
        this.red_core_R_2.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5546F, -6.92774F, -1.0F, 0.6454F, 0.07227F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // cube_arm_2_R
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.chest.translateAndRotate(poseStack);
        this.shoudler_R.translateAndRotate(poseStack);
        this.arm_1_R.translateAndRotate(poseStack);
        this.arm_2_R.translateAndRotate(poseStack);
        this.red_core_R_1.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5546F, -7.02153F, -1.0F, 0.6454F, -0.02153F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
    }
}
