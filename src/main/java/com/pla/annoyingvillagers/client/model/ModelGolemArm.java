package com.pla.annoyingvillagers.client.model;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationClientUtil;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationResolver;
import com.pla.annoyingvillagers.client.animation.SpecialClientAnimationState;
import com.pla.annoyingvillagers.client.animation.rig_special_animation.GolemArmsLivingAnimations;
import com.pla.annoyingvillagers.entity.GolemArms;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ModelGolemArm extends HierarchicalModel<GolemArms> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "model_golem_arm"), "main");
	private final ModelPart Root;
	private final ModelPart Torso;
	private final ModelPart Chest;
	private final ModelPart garm_down_1_L;
	private final ModelPart garm_down_2_L;
	private final ModelPart garm_down_3_L;
	private final ModelPart garm_down_4_L;
	private final ModelPart garm_up_1_R;
	private final ModelPart garm_up_2_R;
	private final ModelPart garm_up_3_R;
	private final ModelPart garm_up_4_R;
	private final ModelPart garm_down_1_R;
	private final ModelPart garm_down_2_R;
	private final ModelPart garm_down_3_R;
	private final ModelPart garm_down_4_R;

	public ModelGolemArm(ModelPart root) {
		this.Root = root.getChild("Root");
		this.Torso = this.Root.getChild("Torso");
		this.Chest = this.Torso.getChild("Chest");
		this.garm_down_1_L = this.Chest.getChild("garm_down_1_L");
		this.garm_down_2_L = this.garm_down_1_L.getChild("garm_down_2_L");
		this.garm_down_3_L = this.garm_down_2_L.getChild("garm_down_3_L");
		this.garm_down_4_L = this.garm_down_3_L.getChild("garm_down_4_L");
		this.garm_up_1_R = this.Chest.getChild("garm_up_1_R");
		this.garm_up_2_R = this.garm_up_1_R.getChild("garm_up_2_R");
		this.garm_up_3_R = this.garm_up_2_R.getChild("garm_up_3_R");
		this.garm_up_4_R = this.garm_up_3_R.getChild("garm_up_4_R");
		this.garm_down_1_R = this.Chest.getChild("garm_down_1_R");
		this.garm_down_2_R = this.garm_down_1_R.getChild("garm_down_2_R");
		this.garm_down_3_R = this.garm_down_2_R.getChild("garm_down_3_R");
		this.garm_down_4_R = this.garm_down_3_R.getChild("garm_down_4_R");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0001F, 11.7765F, -0.0151F));

		PartDefinition Torso = Root.addOrReplaceChild("Torso", CubeListBuilder.create(), PartPose.offset(0.0F, -0.8F, 0.0F));

		PartDefinition Chest = Torso.addOrReplaceChild("Chest", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.8F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition garm_down_1_L = Chest.addOrReplaceChild("garm_down_1_L", CubeListBuilder.create().texOffs(-6, -4).addBox(-2.0454F, -4.0071F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-2.0454F, -3.0071F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2346F, 1.6954F, 4.9955F, 0.0F, 0.0F, 2.3562F));

		PartDefinition garm_down_2_L = garm_down_1_L.addOrReplaceChild("garm_down_2_L", CubeListBuilder.create().texOffs(-1, 1).addBox(-2.0454F, 2.5723F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(-0.0454F, 2.5723F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(-2.0454F, 2.5723F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-2.0454F, -3.4277F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(-2.0454F, 2.5723F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.507F, 0.0F));

		PartDefinition garm_down_3_L = garm_down_2_L.addOrReplaceChild("garm_down_3_L", CubeListBuilder.create().texOffs(-6, -4).addBox(-0.0454F, 1.5532F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(-2.0454F, 1.5532F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-2.0454F, -3.4468F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(-2.0454F, 1.5532F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(-2.0454F, 1.5532F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(-2.0454F, 2.5532F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9777F, 0.0F));

		PartDefinition garm_down_4_L = garm_down_3_L.addOrReplaceChild("garm_down_4_L", CubeListBuilder.create().texOffs(60, 80).addBox(-2.0454F, -6.8011F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(-2.0454F, 2.1989F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(-2.0454F, 1.1989F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(-2.0454F, 1.1989F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(-2.0454F, 1.1989F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-0.0454F, 1.1989F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.9475F, 0.0F));

		PartDefinition garm_up_1_R = Chest.addOrReplaceChild("garm_up_1_R", CubeListBuilder.create().texOffs(-6, -4).addBox(0.0454F, -4.0071F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-1.9546F, -3.0071F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0001F, -4.7857F, 4.9953F, 0.0F, 0.0F, 0.0F));

		PartDefinition garm_up_2_R = garm_up_1_R.addOrReplaceChild("garm_up_2_R", CubeListBuilder.create().texOffs(-1, 1).addBox(0.0454F, 2.5723F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(0.0454F, 2.5723F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-1.9546F, 2.5723F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-1.9546F, -3.4277F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(0.0454F, 2.5723F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.507F, 0.0F));

		PartDefinition garm_up_3_R = garm_up_2_R.addOrReplaceChild("garm_up_3_R", CubeListBuilder.create().texOffs(60, 74).addBox(-1.9546F, -3.4468F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(0.0454F, 1.5532F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(0.0454F, 1.5532F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-1.9546F, 1.5532F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(0.0454F, 1.5532F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(0.0454F, 2.5532F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9777F, 0.0F));

		PartDefinition garm_up_4_R = garm_up_3_R.addOrReplaceChild("garm_up_4_R", CubeListBuilder.create().texOffs(60, 80).addBox(-1.9546F, -6.8011F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-1.9546F, 1.1989F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(0.0454F, 1.1989F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(0.0454F, 1.1989F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(0.0454F, 2.1989F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(0.0454F, 1.1989F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.9475F, 0.0F));

		PartDefinition garm_down_1_R = Chest.addOrReplaceChild("garm_down_1_R", CubeListBuilder.create().texOffs(-6, -4).addBox(0.0454F, -4.0071F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-1.9546F, -3.0071F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.235F, 1.6953F, 4.9953F, 0.0F, 0.0F, -2.3562F));

		PartDefinition garm_down_2_R = garm_down_1_R.addOrReplaceChild("garm_down_2_R", CubeListBuilder.create().texOffs(-1, 1).addBox(0.0454F, 2.5723F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(0.0454F, 2.5723F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-1.9546F, 2.5723F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(60, 74).addBox(-1.9546F, -3.4277F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(0.0454F, 2.5723F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.507F, 0.0F));

		PartDefinition garm_down_3_R = garm_down_2_R.addOrReplaceChild("garm_down_3_R", CubeListBuilder.create().texOffs(60, 74).addBox(-1.9546F, -3.4468F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(0.0454F, 1.5532F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(0.0454F, 1.5532F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-1.9546F, 1.5532F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(0.0454F, 1.5532F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(0.0454F, 2.5532F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9777F, 0.0F));

		PartDefinition garm_down_4_R = garm_down_3_R.addOrReplaceChild("garm_down_4_R", CubeListBuilder.create().texOffs(60, 80).addBox(-1.9546F, -6.8011F, -3.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(64, 77).addBox(-1.9546F, 1.1989F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(0.0454F, 1.1989F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-1, 1).addBox(0.0454F, 1.1989F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(-6, -4).addBox(0.0454F, 2.1989F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(-4, -2).addBox(0.0454F, 1.1989F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.9475F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.Root;
	}

	@Override
	public void setupAnim(@NotNull GolemArms entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Root.getAllParts().forEach(ModelPart::resetPose);
		SpecialClientAnimationState.Active active = SpecialClientAnimationState.getActive(entity, ageInTicks);
		if (active != null && active.animationId().family() == SpecialAnimationFamily.GOLEM_ARMS) {
			SpecialAnimationClientUtil.apply(this, SpecialAnimationResolver.resolve(active.animationId()), active.elapsedTicks(ageInTicks));
			return;
		}
		LivingEntity owner = entity.getOwnerLiving();
		AnimationDefinition animation = GolemArmsLivingAnimations.IDLE;
		if (owner != null) {
			boolean moving = isOwnerMoving(owner);
			if (!owner.onGround()) animation = GolemArmsLivingAnimations.IDLE;
			else if (owner.isShiftKeyDown()) animation = moving ? GolemArmsLivingAnimations.SNEAK : GolemArmsLivingAnimations.KNEEL;
			else if (moving && owner.isSprinting()) animation = GolemArmsLivingAnimations.RUN;
			else if (moving) animation = GolemArmsLivingAnimations.WALK;
		}
		SpecialAnimationClientUtil.applyLoop(this, animation, ageInTicks);
	}

	private static boolean isOwnerMoving(LivingEntity owner) {
		double dx = owner.getX() - owner.xo;
		double dz = owner.getZ() - owner.zo;
		return dx * dx + dz * dz > 1.0E-6D || owner.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
	}
}
