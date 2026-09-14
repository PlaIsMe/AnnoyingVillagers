package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationClientUtil;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationResolver;
import com.pla.annoyingvillagers.client.animation.SpecialClientAnimationState;
import com.pla.annoyingvillagers.client.animation.rig_special_animation.AvWardenLivingAnimations;
import com.pla.annoyingvillagers.client.animation.rig_special_animation.AvWardenSkillAnimations2;
import com.pla.annoyingvillagers.entity.AvWarden;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
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
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModelAvWarden extends HierarchicalModel<AvWarden> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "model_av_warden"), "main");
	private final ModelPart Root;
	private final ModelPart body;
	private final ModelPart chest;
	private final ModelPart head;
	private final ModelPart ear_R;
	private final ModelPart ear_L;
	private final ModelPart shoudler_R;
	private final ModelPart arm_up_R;
	private final ModelPart arm_down_R;
	private final ModelPart bow_R;
	private final ModelPart shoudler_L;
	private final ModelPart arm_up_L;
	private final ModelPart arm_down_L;
	private final ModelPart bow_L;
	private final ModelPart left_ribcage;
	private final ModelPart right_ribcage;
	private final ModelPart leg_up_R;
	private final ModelPart leg_down_R;
	private final ModelPart knee_R;
	private final ModelPart leg_up_L;
	private final ModelPart leg_down_L;
	private final ModelPart knee_L;

	public ModelAvWarden(ModelPart root) {
		this.Root = root.getChild("Root");
		this.body = this.Root.getChild("body");
		this.chest = this.body.getChild("chest");
		this.head = this.chest.getChild("head");
		this.ear_R = this.head.getChild("ear_R");
		this.ear_L = this.head.getChild("ear_L");
		this.shoudler_R = this.chest.getChild("shoudler_R");
		this.arm_up_R = this.shoudler_R.getChild("arm_up_R");
		this.arm_down_R = this.arm_up_R.getChild("arm_down_R");
		this.bow_R = this.arm_up_R.getChild("bow_R");
		this.shoudler_L = this.chest.getChild("shoudler_L");
		this.arm_up_L = this.shoudler_L.getChild("arm_up_L");
		this.arm_down_L = this.arm_up_L.getChild("arm_down_L");
		this.bow_L = this.arm_up_L.getChild("bow_L");
		this.left_ribcage = this.body.getChild("left_ribcage");
		this.right_ribcage = this.body.getChild("right_ribcage");
		this.leg_up_R = this.Root.getChild("leg_up_R");
		this.leg_down_R = this.leg_up_R.getChild("leg_down_R");
		this.knee_R = this.leg_up_R.getChild("knee_R");
		this.leg_up_L = this.Root.getChild("leg_up_L");
		this.leg_down_L = this.leg_up_L.getChild("leg_down_L");
		this.knee_L = this.leg_up_L.getChild("knee_L");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = Root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -9.0F, -4.0F, 18.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, -1.0F));

		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -12.0F, -4.0F, 18.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition head = chest.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition ear_R = head.addOrReplaceChild("ear_R", CubeListBuilder.create().texOffs(52, 32).addBox(-16.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -12.0F, 1.0F));

		PartDefinition ear_L = head.addOrReplaceChild("ear_L", CubeListBuilder.create().texOffs(58, 0).addBox(0.0F, -13.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -12.0F, 1.0F));

		PartDefinition shoudler_R = chest.addOrReplaceChild("shoudler_R", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 1.0F));

		PartDefinition arm_up_R = shoudler_R.addOrReplaceChild("arm_up_R", CubeListBuilder.create().texOffs(44, 50).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 0.0F, 0.0F));

		PartDefinition arm_down_R = arm_up_R.addOrReplaceChild("arm_down_R", CubeListBuilder.create().texOffs(44, 50).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition bow_R = arm_up_R.addOrReplaceChild("bow_R", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition shoudler_L = chest.addOrReplaceChild("shoudler_L", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 1.0F));

		PartDefinition arm_up_L = shoudler_L.addOrReplaceChild("arm_up_L", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 0.0F, 0.0F));

		PartDefinition arm_down_L = arm_up_L.addOrReplaceChild("arm_down_L", CubeListBuilder.create().texOffs(0, 58).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition bow_L = arm_up_L.addOrReplaceChild("bow_L", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));

		PartDefinition left_ribcage = body.addOrReplaceChild("left_ribcage", CubeListBuilder.create(), PartPose.offset(7.0F, -10.0F, -4.0F));

		PartDefinition left_bone_last_r1 = left_ribcage.addOrReplaceChild("left_bone_last_r1", CubeListBuilder.create().texOffs(11, 71).addBox(-7.0F, -15.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 71).addBox(-7.0F, -19.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 71).addBox(-7.0F, -23.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 71).addBox(-7.0F, -27.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.0F, 3.0F, 0.0F, -0.3491F, 0.0F));

		PartDefinition right_ribcage = body.addOrReplaceChild("right_ribcage", CubeListBuilder.create(), PartPose.offset(-7.0F, -10.0F, -4.0F));

		PartDefinition right_bone_last_r1 = right_ribcage.addOrReplaceChild("right_bone_last_r1", CubeListBuilder.create().texOffs(11, 71).addBox(-7.0F, -15.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 71).addBox(-7.0F, -19.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 71).addBox(-7.0F, -23.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(11, 71).addBox(-7.0F, -27.0F, -5.1F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 21.0F, 1.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition leg_up_R = Root.addOrReplaceChild("leg_up_R", CubeListBuilder.create().texOffs(76, 48).addBox(-3.1F, 0.0F, -3.0F, 6.0F, 6.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -13.0F, -1.0F));

		PartDefinition leg_down_R = leg_up_R.addOrReplaceChild("leg_down_R", CubeListBuilder.create().texOffs(76, 48).addBox(-3.1F, 0.0F, -3.0F, 6.0F, 6.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 0.0F));

		PartDefinition knee_R = leg_up_R.addOrReplaceChild("knee_R", CubeListBuilder.create(), PartPose.offset(0.0F, 6.5F, 0.0F));

		PartDefinition leg_up_L = Root.addOrReplaceChild("leg_up_L", CubeListBuilder.create().texOffs(76, 76).addBox(-2.9F, 0.0F, -3.0F, 6.0F, 6.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -13.0F, -1.0F));

		PartDefinition leg_down_L = leg_up_L.addOrReplaceChild("leg_down_L", CubeListBuilder.create().texOffs(76, 76).addBox(-2.9F, 0.0F, -3.0F, 6.0F, 6.5F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 0.0F));

		PartDefinition knee_L = leg_up_L.addOrReplaceChild("knee_L", CubeListBuilder.create(), PartPose.offset(0.0F, 6.5F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.Root;
	}

	@Override
	public void setupAnim(@NotNull AvWarden entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		SpecialClientAnimationState.Active active = SpecialClientAnimationState.getActive(entity, ageInTicks);
		if (active != null && active.animationId().family() == SpecialAnimationFamily.AV_WARDEN) {
			SpecialAnimationClientUtil.apply(this, SpecialAnimationResolver.resolve(active.animationId()), active.elapsedTicks(ageInTicks));
		} else if (entity.isDeadOrDying() || entity.deathTime > 0) {
			float partialTick = Mth.clamp(ageInTicks - entity.tickCount, 0.0F, 1.0F);
			SpecialAnimationClientUtil.apply(this, AvWardenLivingAnimations.DEATH, Math.max(0.0F, entity.deathTime - 1.0F + partialTick));
		} else if (entity.hasPose(net.minecraft.world.entity.Pose.EMERGING)) {
			this.animate(entity.emergeAnimationState, AvWardenSkillAnimations2.EMERGE, ageInTicks);
		} else if (entity.hasPose(net.minecraft.world.entity.Pose.DIGGING)) {
			this.animate(entity.diggingAnimationState, AvWardenLivingAnimations.DIGGING, ageInTicks);
		} else if (entity.sniffAnimationState.isStarted()) {
			this.animate(entity.sniffAnimationState, AvWardenSkillAnimations2.SNIFF, ageInTicks);
		} else if (!entity.onGround()) {
			SpecialAnimationClientUtil.applyLoop(this, AvWardenLivingAnimations.FALL, ageInTicks);
		} else if (entity.getDeltaMovement().horizontalDistanceSqr() > 0.0025D) {
			SpecialAnimationClientUtil.applyLoop(this, entity.isAvChasing() ? AvWardenLivingAnimations.CHASE : AvWardenLivingAnimations.WALK, ageInTicks);
		} else {
			SpecialAnimationClientUtil.applyLoop(this, AvWardenLivingAnimations.IDLE, ageInTicks);
		}
		this.head.yRot += Mth.clamp(netHeadYaw, -60.0F, 60.0F) * ((float)Math.PI / 180.0F);
		this.head.xRot += Mth.clamp(headPitch, -30.0F, 30.0F) * ((float)Math.PI / 180.0F);
	}

	public List<ModelPart> getBioluminescentLayerModelParts() {
		return List.of(this.head, this.body, this.chest, this.arm_up_R, this.arm_down_R, this.arm_up_L, this.arm_down_L, this.leg_up_R, this.leg_down_R, this.leg_up_L, this.leg_down_L);
	}

	public List<ModelPart> getPulsatingSpotsLayerModelParts() {
		return List.of(this.body, this.chest, this.arm_up_R, this.arm_down_R, this.arm_up_L, this.arm_down_L, this.leg_up_R, this.leg_down_R, this.leg_up_L, this.leg_down_L);
	}

	public List<ModelPart> getTendrilsLayerModelParts() {
		return List.of(this.ear_R, this.ear_L);
	}

	public List<ModelPart> getHeartLayerModelParts() {
		return List.of(this.body, this.chest);
	}
	public void renderTendrils(PoseStack poseStack, VertexConsumer consumer, int packedLight, int overlay, float alpha) {
		this.Root.getAllParts().forEach(part -> part.skipDraw = true);
		this.ear_R.skipDraw = false;
		this.ear_L.skipDraw = false;
		this.Root.render(poseStack, consumer, packedLight, overlay, 1.0F, 1.0F, 1.0F, alpha);
		this.Root.getAllParts().forEach(part -> part.skipDraw = false);
	}

}
