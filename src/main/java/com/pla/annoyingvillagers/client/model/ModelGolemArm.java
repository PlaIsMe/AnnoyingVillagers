package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

import java.util.Map;
import java.util.WeakHashMap;

public class ModelGolemArm extends HierarchicalModel<GolemArms> {
    private static final float LIVING_TRANSITION_TICKS = 5.0F;
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "model_golem_arm"), "main");

private final ModelPart modelRoot;
    private final ModelPart Root;
    private final ModelPart Torso;
    private final ModelPart Chest;
    private final ModelPart garm_down_1_L;
    private final ModelPart garm_down_2_L;
    private final ModelPart garm_down_3_L;
    private final ModelPart garm_down_4_L;
    private final ModelPart red_core_down_L_3;
    private final ModelPart red_core_down_L_2;
    private final ModelPart red_core_down_L_1;
    private final ModelPart garm_up_1_R;
    private final ModelPart garm_up_2_R;
    private final ModelPart garm_up_3_R;
    private final ModelPart garm_up_4_R;
    private final ModelPart red_core_up_R_3;
    private final ModelPart red_core_up_R_2;
    private final ModelPart red_core_up_R_1;
    private final ModelPart garm_down_1_R;
    private final ModelPart garm_down_2_R;
    private final ModelPart garm_down_3_R;
    private final ModelPart garm_down_4_R;
    private final ModelPart red_core_down_R_3;
    private final ModelPart red_core_down_R_2;
    private final ModelPart red_core_down_R_1;
    private final Map<GolemArms, LivingBlendState> livingBlendStates = new WeakHashMap<>();

    public ModelGolemArm(ModelPart root) {
        this.modelRoot = root;
        this.Root = root.getChild("Root");
        this.Torso = this.Root.getChild("Torso");
        this.Chest = this.Torso.getChild("Chest");
        this.garm_down_1_L = this.Chest.getChild("garm_down_1_L");
        this.garm_down_2_L = this.garm_down_1_L.getChild("garm_down_2_L");
        this.garm_down_3_L = this.garm_down_2_L.getChild("garm_down_3_L");
        this.garm_down_4_L = this.garm_down_3_L.getChild("garm_down_4_L");
        this.red_core_down_L_3 = this.garm_down_3_L.getChild("red_core_down_L_3");
        this.red_core_down_L_2 = this.garm_down_2_L.getChild("red_core_down_L_2");
        this.red_core_down_L_1 = this.garm_down_1_L.getChild("red_core_down_L_1");
        this.garm_up_1_R = this.Chest.getChild("garm_up_1_R");
        this.garm_up_2_R = this.garm_up_1_R.getChild("garm_up_2_R");
        this.garm_up_3_R = this.garm_up_2_R.getChild("garm_up_3_R");
        this.garm_up_4_R = this.garm_up_3_R.getChild("garm_up_4_R");
        this.red_core_up_R_3 = this.garm_up_3_R.getChild("red_core_up_R_3");
        this.red_core_up_R_2 = this.garm_up_2_R.getChild("red_core_up_R_2");
        this.red_core_up_R_1 = this.garm_up_1_R.getChild("red_core_up_R_1");
        this.garm_down_1_R = this.Chest.getChild("garm_down_1_R");
        this.garm_down_2_R = this.garm_down_1_R.getChild("garm_down_2_R");
        this.garm_down_3_R = this.garm_down_2_R.getChild("garm_down_3_R");
        this.garm_down_4_R = this.garm_down_3_R.getChild("garm_down_4_R");
        this.red_core_down_R_3 = this.garm_down_3_R.getChild("red_core_down_R_3");
        this.red_core_down_R_2 = this.garm_down_2_R.getChild("red_core_down_R_2");
        this.red_core_down_R_1 = this.garm_down_1_R.getChild("red_core_down_R_1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.00008F, 11.77646F, -0.01514F));

        PartDefinition Torso = Root.addOrReplaceChild("Torso", CubeListBuilder.create(), PartPose.offset(0.0F, -0.8F, 0.0F));

        PartDefinition Chest = Torso.addOrReplaceChild("Chest", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.8F, 0.0F, 0.0F, 0.000023F, -0.000023F));

        PartDefinition garm_down_1_L = Chest.addOrReplaceChild("garm_down_1_L", CubeListBuilder.create().texOffs(-6, -4).addBox(-2.04541F, -4.00714F, -3.0F, 2.00001F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-2.04541F, -3.00714F, -3.0F, 4.00001F, 5.00001F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.23459F, 1.69543F, 4.99546F, -0.000016F, 0.000016F, 2.356217F));

        PartDefinition garm_down_2_L = garm_down_1_L.addOrReplaceChild("garm_down_2_L", CubeListBuilder.create().texOffs(-1, 1).addBox(-2.04541F, 2.57227F, 2.0F, 2.00001F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(-0.0454F, 2.57227F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.04541F, 2.57227F, -3.0F, 2.00001F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-2.04541F, -3.42773F, -3.0F, 4.00001F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(-2.04541F, 2.57227F, -2.0F, 2.00001F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.50696F, 0.0F));

        PartDefinition garm_down_3_L = garm_down_2_L.addOrReplaceChild("garm_down_3_L", CubeListBuilder.create().texOffs(-6, -4).addBox(-0.0454F, 1.55318F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.04541F, 1.55318F, -3.0F, 2.00001F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-2.04541F, -3.44682F, -3.0F, 4.00001F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.04541F, 1.55318F, 2.0F, 2.00001F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(-2.04541F, 1.55318F, -2.0F, 2.00001F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(-2.04541F, 2.55318F, -3.0F, 2.00001F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9777F, 0.0F));

        PartDefinition garm_down_4_L = garm_down_3_L.addOrReplaceChild("garm_down_4_L", CubeListBuilder.create().texOffs(60, 80).addBox(-2.04541F, -6.80107F, -3.0F, 4.00001F, 8.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(-2.04541F, 2.19894F, -3.0F, 2.00001F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(-2.04541F, 1.19894F, -2.0F, 2.00001F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(-2.04541F, 1.19894F, 2.0F, 2.00001F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(-2.04541F, 1.19894F, -3.0F, 2.00001F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-0.0454F, 1.19894F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.94749F, 0.0F));

        PartDefinition red_core_down_L_3 = garm_down_3_L.addOrReplaceChild("red_core_down_L_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_down_L_2 = garm_down_2_L.addOrReplaceChild("red_core_down_L_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_down_L_1 = garm_down_1_L.addOrReplaceChild("red_core_down_L_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition garm_up_1_R = Chest.addOrReplaceChild("garm_up_1_R", CubeListBuilder.create().texOffs(-6, -4).addBox(0.04541F, -4.00714F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-1.9546F, -3.00714F, -3.0F, 4.00001F, 5.00001F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.00008F, -4.78572F, 4.9953F, 0.000016F, -0.000016F, 0.0F));

        PartDefinition garm_up_2_R = garm_up_1_R.addOrReplaceChild("garm_up_2_R", CubeListBuilder.create().texOffs(-1, 1).addBox(0.04541F, 2.57227F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.04541F, 2.57227F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 2.57227F, -3.0F, 2.00001F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-1.9546F, -3.42774F, -3.0F, 4.00001F, 6.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.04541F, 2.57227F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.50696F, 0.0F));

        PartDefinition garm_up_3_R = garm_up_2_R.addOrReplaceChild("garm_up_3_R", CubeListBuilder.create().texOffs(60, 74).addBox(-1.9546F, -3.44683F, -3.0F, 4.00001F, 5.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(0.04541F, 1.55318F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.04541F, 1.55318F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 1.55318F, -3.0F, 2.00001F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.04541F, 1.55318F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(0.04541F, 2.55318F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9777F, 0.0F));

        PartDefinition garm_up_4_R = garm_up_3_R.addOrReplaceChild("garm_up_4_R", CubeListBuilder.create().texOffs(60, 80).addBox(-1.9546F, -6.80108F, -3.0F, 4.00001F, 8.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 1.19893F, -3.0F, 2.00001F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.04541F, 1.19893F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(0.04541F, 1.19893F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(0.04541F, 2.19893F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.04541F, 1.19893F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.94747F, 0.0F));

        PartDefinition red_core_up_R_3 = garm_up_3_R.addOrReplaceChild("red_core_up_R_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_up_R_2 = garm_up_2_R.addOrReplaceChild("red_core_up_R_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_up_R_1 = garm_up_1_R.addOrReplaceChild("red_core_up_R_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition garm_down_1_R = Chest.addOrReplaceChild("garm_down_1_R", CubeListBuilder.create().texOffs(-6, -4).addBox(0.04541F, -4.00713F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-1.9546F, -3.00713F, -3.0F, 4.00001F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.23504F, 1.69528F, 4.9953F, 0.000016F, 0.000016F, -2.356171F));

        PartDefinition garm_down_2_R = garm_down_1_R.addOrReplaceChild("garm_down_2_R", CubeListBuilder.create().texOffs(-1, 1).addBox(0.04541F, 2.57228F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.04541F, 2.57228F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 2.57228F, -3.0F, 2.00001F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(60, 74).addBox(-1.9546F, -3.42773F, -3.0F, 4.00001F, 6.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.04541F, 2.57228F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.50696F, 0.0F));

        PartDefinition garm_down_3_R = garm_down_2_R.addOrReplaceChild("garm_down_3_R", CubeListBuilder.create().texOffs(60, 74).addBox(-1.9546F, -3.44683F, -3.0F, 4.00001F, 5.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(0.04541F, 1.55318F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.04541F, 1.55318F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 1.55318F, -3.0F, 2.00001F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.04541F, 1.55318F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(0.04541F, 2.55318F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.97769F, 0.0F));

        PartDefinition garm_down_4_R = garm_down_3_R.addOrReplaceChild("garm_down_4_R", CubeListBuilder.create().texOffs(60, 80).addBox(-1.9546F, -6.80108F, -3.0F, 4.00001F, 8.00001F, 6.0F, new CubeDeformation(0.0F)).texOffs(64, 77).addBox(-1.9546F, 1.19893F, -3.0F, 2.00001F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(57, 79).addBox(0.04541F, 1.19893F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-1, 1).addBox(0.04541F, 1.19893F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(-6, -4).addBox(0.04541F, 2.19893F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(-4, -2).addBox(0.04541F, 1.19893F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.94747F, 0.0F));

        PartDefinition red_core_down_R_3 = garm_down_3_R.addOrReplaceChild("red_core_down_R_3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_down_R_2 = garm_down_2_R.addOrReplaceChild("red_core_down_R_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red_core_down_R_1 = garm_down_1_R.addOrReplaceChild("red_core_down_R_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.modelRoot;
    }

	@Override
	public void setupAnim(@NotNull GolemArms entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Root.getAllParts().forEach(ModelPart::resetPose);
		SpecialClientAnimationState.Active active = SpecialClientAnimationState.getActive(entity, ageInTicks);
		if (active != null && active.animationId().family() == SpecialAnimationFamily.GOLEM_ARMS) {
			SpecialAnimationClientUtil.apply(this, SpecialAnimationResolver.resolve(active.animationId()), active.elapsedTicks(ageInTicks));
			return;
		}

		LivingPose desiredPose = resolveLivingPose(entity.getOwnerLiving());
		LivingBlendState state = this.livingBlendStates.computeIfAbsent(entity, ignored -> new LivingBlendState(desiredPose, ageInTicks));
		if (state.currentPose != desiredPose) {
			state.previousPose = state.currentPose;
			state.currentPose = desiredPose;
			state.transitionStartedAt = ageInTicks;
		}

		float progress = Math.max(0.0F, Math.min(1.0F, (ageInTicks - state.transitionStartedAt) / LIVING_TRANSITION_TICKS));
		if (state.previousPose != null && progress < 1.0F) {
			float blend = smoothStep(progress);
			SpecialAnimationClientUtil.applyLoop(this, state.previousPose.animation, ageInTicks, 1.0F - blend);
			SpecialAnimationClientUtil.applyLoop(this, state.currentPose.animation, ageInTicks, blend);
		} else {
			state.previousPose = null;
			SpecialAnimationClientUtil.applyLoop(this, state.currentPose.animation, ageInTicks);
		}
	}

	private static LivingPose resolveLivingPose(LivingEntity owner) {
		if (owner == null || !owner.onGround()) return LivingPose.IDLE;
		boolean moving = isOwnerMoving(owner);
		if (owner.isShiftKeyDown()) return moving ? LivingPose.SNEAK : LivingPose.KNEEL;
		if (moving && owner.isSprinting()) return LivingPose.RUN;
		if (moving) return LivingPose.WALK;
		return LivingPose.IDLE;
	}

	private static boolean isOwnerMoving(LivingEntity owner) {
		double dx = owner.getX() - owner.xo;
		double dz = owner.getZ() - owner.zo;
		return dx * dx + dz * dz > 1.0E-6D || owner.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
	}

	private static float smoothStep(float value) {
		return value * value * (3.0F - 2.0F * value);
	}

	private enum LivingPose {
		IDLE(GolemArmsLivingAnimations.IDLE),
		WALK(GolemArmsLivingAnimations.WALK),
		RUN(GolemArmsLivingAnimations.RUN),
		SNEAK(GolemArmsLivingAnimations.SNEAK),
		KNEEL(GolemArmsLivingAnimations.KNEEL);

		private final AnimationDefinition animation;

		LivingPose(AnimationDefinition animation) {
			this.animation = animation;
		}
	}

	private static final class LivingBlendState {
		private LivingPose previousPose;
		private LivingPose currentPose;
		private float transitionStartedAt;

		private LivingBlendState(LivingPose currentPose, float transitionStartedAt) {
			this.currentPose = currentPose;
			this.transitionStartedAt = transitionStartedAt;
		}
	}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.modelRoot.render(poseStack, buffer, packedLight, packedOverlay, color);
        renderPerFaceCubes(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private void renderPerFaceCubes(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        // red_core_down_L_1_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_down_1_L.translateAndRotate(poseStack);
        this.red_core_down_L_1.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -5.50696F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_down_L_2_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_down_1_L.translateAndRotate(poseStack);
        this.garm_down_2_L.translateAndRotate(poseStack);
        this.red_core_down_L_2.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -7.9777F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_down_L_3_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_down_1_L.translateAndRotate(poseStack);
        this.garm_down_2_L.translateAndRotate(poseStack);
        this.garm_down_3_L.translateAndRotate(poseStack);
        this.red_core_down_L_3.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -6.94749F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_up_R_1_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_up_1_R.translateAndRotate(poseStack);
        this.red_core_up_R_1.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -5.50696F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_up_R_2_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_up_1_R.translateAndRotate(poseStack);
        this.garm_up_2_R.translateAndRotate(poseStack);
        this.red_core_up_R_2.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -7.9777F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_up_R_3_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_up_1_R.translateAndRotate(poseStack);
        this.garm_up_2_R.translateAndRotate(poseStack);
        this.garm_up_3_R.translateAndRotate(poseStack);
        this.red_core_up_R_3.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -6.94747F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_down_R_1_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_down_1_R.translateAndRotate(poseStack);
        this.red_core_down_R_1.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -5.50696F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_down_R_2_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_down_1_R.translateAndRotate(poseStack);
        this.garm_down_2_R.translateAndRotate(poseStack);
        this.red_core_down_R_2.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -7.97769F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
        // red_core_down_R_3_cube
        poseStack.pushPose();
        this.Root.translateAndRotate(poseStack);
        this.Torso.translateAndRotate(poseStack);
        this.Chest.translateAndRotate(poseStack);
        this.garm_down_1_R.translateAndRotate(poseStack);
        this.garm_down_2_R.translateAndRotate(poseStack);
        this.garm_down_3_R.translateAndRotate(poseStack);
        this.red_core_down_R_3.translateAndRotate(poseStack);
        PerFaceCubeRenderer.renderSingleUvBox(poseStack, buffer, packedLight, packedOverlay, color,
                -0.5F, -6.94747F, -1.0F, 0.5F, 0.0F, 1.0F,
                9.0F, 14.0F, 10.0F, 15.0F, 128.0F, 128.0F);
        poseStack.popPose();
    }
}
