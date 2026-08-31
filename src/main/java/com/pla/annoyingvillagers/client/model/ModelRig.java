package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.RigAnimationResolver;
import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.client.animation.rig_animation.living.LivingAnimations;
import com.pla.annoyingvillagers.entity.InfectedChrisEntity;
import com.pla.annoyingvillagers.entity.InfectedPlayerNpcEntity;
import com.pla.annoyingvillagers.entity.InfectedTheMostMoistBurrit0Entity;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationPlaybackType;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigBowAnimationSelector;
import com.pla.annoyingvillagers.rig.pose.RigPoseLibrary;
import com.pla.annoyingvillagers.util.AnimationUtil;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class ModelRig<T extends Mob> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelrig"), "main");
    public static final ModelLayerLocation SLIM_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelrig_slim"), "main");

    private static final float TICK_TO_MILLISECONDS = 50.0F;
    private static final float MOVEMENT_THRESHOLD = 0.03F;

    private final ModelPart modelRoot;
    private final AnimationView animationView;
    private final Vector3f animationVectorCache = new Vector3f();

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart right_hand;
    private final ModelPart right_tool;
    private final ModelPart left_arm;
    private final ModelPart left_hand;
    private final ModelPart left_tool;
    private final ModelPart right_leg;
    private final ModelPart right_lower_leg;
    private final ModelPart left_leg;
    private final ModelPart left_lower_leg;
    private final Map<Integer, AnimationKey> activeAnimationKeys = new HashMap<>();
    private final Map<Integer, TransitionPose> transitionPoses = new HashMap<>();
    private final Set<ModelPart> upperBodyPlaybackParts;
    private final Set<ModelPart> rightHandPlaybackParts;
    private final Set<ModelPart> leftHandPlaybackParts;
    private final boolean slim;

    public ModelRig(ModelPart root) {
        this(root, false);
    }

    public ModelRig(ModelPart root, boolean slim) {
        super(root);
        this.modelRoot = root;
        this.animationView = new AnimationView(root);
        this.slim = slim;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_arm = root.getChild("right_arm");
        this.right_hand = this.right_arm.getChild("right_hand");
        this.right_tool = this.right_hand.getChild("right_tool");
        this.left_arm = root.getChild("left_arm");
        this.left_hand = this.left_arm.getChild("left_hand");
        this.left_tool = this.left_hand.getChild("left_tool");
        this.right_leg = root.getChild("right_leg");
        this.right_lower_leg = this.right_leg.getChild("right_lower_leg");
        this.left_leg = root.getChild("left_leg");
        this.left_lower_leg = this.left_leg.getChild("left_lower_leg");
        this.upperBodyPlaybackParts = collectPartTrees(this.head, this.body, this.right_arm, this.left_arm);
        this.rightHandPlaybackParts = collectPartTree(this.right_arm);
        this.leftHandPlaybackParts = collectPartTree(this.left_arm);
    }

    @Override
    public void copyPropertiesTo(@NotNull HumanoidModel<T> pModel) {
        super.copyPropertiesTo(pModel);
        if (pModel instanceof ModelRigArmor<?> armorModel) {
            armorModel.copySegmentPoses(
                    this.right_hand,
                    this.left_hand,
                    this.right_lower_leg,
                    this.left_lower_leg
            );
        }
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer(false);
    }

    public static LayerDefinition createSlimBodyLayer() {
        return createBodyLayer(true);
    }

    private static LayerDefinition createBodyLayer(boolean slim) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float rightArmMinX = slim ? -2.0F : -3.0F;
        float rightArmWidth = slim ? 3.0F : 4.0F;
        float leftArmWidth = slim ? 3.0F : 4.0F;

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(rightArmMinX, -2.0F, -2.0F, rightArmWidth, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 32).addBox(rightArmMinX, -2.0F, -2.0F, rightArmWidth, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(40, 22).addBox(rightArmMinX, 0.0F, -2.0F, rightArmWidth, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 38).addBox(rightArmMinX, 0.0F, -2.0F, rightArmWidth, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        right_hand.addOrReplaceChild("right_hand_top_face", CubeListBuilder.create().texOffs(40, 16).addBox(rightArmMinX, -0.001F, -2.0F, rightArmWidth, 0.0F, 4.0F, Set.of(Direction.UP)), PartPose.offset(0.0F, 0.0F, 0.0F));

        right_hand.addOrReplaceChild("right_hand_bottom_face", CubeListBuilder.create().texOffs(44, 16).addBox(rightArmMinX, 6.001F, -2.0F, rightArmWidth, 0.0F, 4.0F, Set.of(Direction.DOWN)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_tool = right_hand.addOrReplaceChild("right_tool", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, leftArmWidth, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, leftArmWidth, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(32, 54).addBox(-1.0F, 0.0F, -2.0F, leftArmWidth, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 54).addBox(-1.0F, 0.0F, -2.0F, leftArmWidth, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        left_hand.addOrReplaceChild("left_hand_top_face", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -0.001F, -2.0F, leftArmWidth, 0.0F, 4.0F, Set.of(Direction.UP)), PartPose.offset(0.0F, 0.0F, 0.0F));

        left_hand.addOrReplaceChild("left_hand_bottom_face", CubeListBuilder.create().texOffs(36, 48).addBox(-1.0F, 6.001F, -2.0F, leftArmWidth, 0.0F, 4.0F, Set.of(Direction.DOWN)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_tool = left_hand.addOrReplaceChild("left_tool", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition right_lower_leg = right_leg.addOrReplaceChild("right_lower_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        right_lower_leg.addOrReplaceChild("right_lower_leg_top_face", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -0.251F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.UP)), PartPose.offset(0.0F, 0.0F, 0.0F));

        right_lower_leg.addOrReplaceChild("right_lower_leg_bottom_face", CubeListBuilder.create().texOffs(4, 16).addBox(-2.0F, 6.001F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.DOWN)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition left_lower_leg = left_leg.addOrReplaceChild("left_lower_leg", CubeListBuilder.create().texOffs(16, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 6.0F, 0.0F));

        left_lower_leg.addOrReplaceChild("left_lower_leg_top_face", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, -0.251F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.UP)), PartPose.offset(0.0F, 0.0F, 0.0F));

        left_lower_leg.addOrReplaceChild("left_lower_leg_bottom_face", CubeListBuilder.create().texOffs(20, 48).addBox(-2.0F, 6.001F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.DOWN)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void translateToHand(@NotNull HumanoidArm side, @NotNull PoseStack poseStack) {
        if (side == HumanoidArm.RIGHT) {
            this.rightArm.translateAndRotate(poseStack);
            this.right_hand.translateAndRotate(poseStack);
        } else {
            this.leftArm.translateAndRotate(poseStack);
            this.left_hand.translateAndRotate(poseStack);
        }
    }

    public void translateToTool(HumanoidArm side, PoseStack poseStack) {
        this.translateToHand(side, poseStack);
        if (side == HumanoidArm.RIGHT) {
            this.right_tool.translateAndRotate(poseStack);
        } else {
            this.left_tool.translateAndRotate(poseStack);
        }
    }

    public boolean isSlim() {
        return this.slim;
    }

    public void copyPoseTo(ModelRig<?> target) {
        target.modelRoot.copyFrom(this.modelRoot);
        target.head.copyFrom(this.head);
        target.hat.copyFrom(this.hat);
        target.body.copyFrom(this.body);

        target.right_arm.copyFrom(this.right_arm);
        target.right_hand.copyFrom(this.right_hand);
        target.right_tool.copyFrom(this.right_tool);

        target.left_arm.copyFrom(this.left_arm);
        target.left_hand.copyFrom(this.left_hand);
        target.left_tool.copyFrom(this.left_tool);

        target.right_leg.copyFrom(this.right_leg);
        target.right_lower_leg.copyFrom(this.right_lower_leg);

        target.left_leg.copyFrom(this.left_leg);
        target.left_lower_leg.copyFrom(this.left_lower_leg);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        Map<ModelPart, ModelPartPose> previousRenderedPose = this.capturePose();
        this.modelRoot.getAllParts().forEach(ModelPart::resetPose);

        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        RigClientAnimationState.Active activeRigAnimation = RigClientAnimationState.getActive(entity, ageInTicks);
        TransitionPose transitionPose = this.updateTransitionPose(entity, activeRigAnimation, ageInTicks, previousRenderedPose);

        this.body.getAllParts().forEach(ModelPart::resetPose);
        this.rightArm.getAllParts().forEach(ModelPart::resetPose);
        this.leftArm.getAllParts().forEach(ModelPart::resetPose);
        this.rightLeg.getAllParts().forEach(ModelPart::resetPose);
        this.leftLeg.getAllParts().forEach(ModelPart::resetPose);

        float activeWeight = 0.0F;
        if (entity.isDeadOrDying() || entity.deathTime > 0) {
            float partialTick = Math.max(0.0F, Math.min(1.0F, ageInTicks - entity.tickCount));
            float deathElapsedTicks = Math.max(0.0F, entity.deathTime - 1.0F + partialTick);
            if (entity instanceof InfectedPlayerNpcEntity
                    || entity instanceof InfectedChrisEntity
                    || entity instanceof InfectedTheMostMoistBurrit0Entity) {
                this.applyAnimationFromStart(LivingAnimations.LAYING_DEATH_DEAD, deathElapsedTicks, 1.0F, 1.0F);
            } else {
                this.applyAnimationFromStart(LivingAnimations.DEATH, deathElapsedTicks, 1.0F, 1.0F);
            }
        } else {
            activeWeight = activeRigAnimation == null ? 0.0F : activeRigAnimation.weight(ageInTicks);
            float baseWeight = 1.0F - activeWeight;
            if (activeRigAnimation != null) {
                this.applyBlendedRigAnimation(entity, limbSwingAmount, ageInTicks, activeRigAnimation, activeWeight, transitionPose, activeRigAnimation.animationId().isBowAnimation());
            } else if (isUsingBow(entity)) {
                this.applyBowAimAnimation(entity, limbSwingAmount, ageInTicks, headPitch);
            } else if (baseWeight > 0.0F) {
                this.applyBaseRigAnimation(entity, limbSwingAmount, ageInTicks, baseWeight);
            }
        }

        this.flattenAnimatedRootIntoTopLevelParts();
        this.compensateServerMotion(activeRigAnimation, activeWeight, ageInTicks);
        this.hat.copyFrom(this.head);
    }

    private TransitionPose updateTransitionPose(T entity, RigClientAnimationState.Active active, float ageInTicks, Map<ModelPart, ModelPartPose> previousRenderedPose) {
        int entityId = entity.getId();
        if (active == null) {
            this.activeAnimationKeys.remove(entityId);
            this.transitionPoses.remove(entityId);
            return null;
        }

        AnimationKey key = new AnimationKey(active.animationId(), active.startedAtTick());
        AnimationKey previousKey = this.activeAnimationKeys.put(entityId, key);
        if (!key.equals(previousKey)) {
            TransitionPose transitionPose = new TransitionPose(key, previousRenderedPose);
            this.transitionPoses.put(entityId, transitionPose);
            return transitionPose;
        }

        TransitionPose transitionPose = this.transitionPoses.get(entityId);
        if (transitionPose == null || active.elapsedTicks(ageInTicks) > active.blendInTicks()) {
            this.transitionPoses.remove(entityId);
            return null;
        }

        return transitionPose;
    }

    private void applyBlendedRigAnimation(T entity, float limbSwingAmount, float ageInTicks, RigClientAnimationState.Active active, float activeWeight, TransitionPose transitionPose, boolean forceWalkBase) {
        Map<ModelPart, ModelPartPose> baselinePose = this.capturePose();
        RigAnimationPlaybackType playbackType = RigAnimationSpecs.get(active.animationId()).playbackType();
        if (playbackType == RigAnimationPlaybackType.DEFAULT && !forceWalkBase) {
            this.applyLoopingAnimation(AnimationUtil.getIdleAnimation(entity), ageInTicks, 1.0F, 1.0F);
        } else {
            this.applyBaseRigAnimation(entity, limbSwingAmount, ageInTicks, 1.0F, forceWalkBase);
        }

        Map<ModelPart, ModelPartPose> basePose = this.capturePose();
        this.restorePose(baselinePose);
        this.applyActiveRigAnimation(active, ageInTicks, 1.0F);

        Map<ModelPart, ModelPartPose> activePose = this.capturePose();
        this.restorePose(basePose);
        this.blendPose(basePose, activePose, activeWeight, transitionPose, playbackType);
    }

    private void applyBaseRigAnimation(T entity, float limbSwingAmount, float ageInTicks, float weight) {
        this.applyBaseRigAnimation(entity, limbSwingAmount, ageInTicks, weight, false);
    }

    private void applyBaseRigAnimation(T entity, float limbSwingAmount, float ageInTicks, float weight, boolean forceWalk) {
        if (entity.isPassenger()) {
            this.applyLoopingAnimation(LivingAnimations.MOUNT, ageInTicks, 1.0F, weight);
            return;
        }

        if (entity.isNoAi()) {
            this.applyLoopingAnimation(AnimationUtil.getIdleAnimation(entity), ageInTicks, 1.0F, weight);
            return;
        }

        if (forceWalk) {
            this.applyLoopingAnimation(AnimationUtil.getWalkAnimation(entity), ageInTicks, 1.0F, weight);
            return;
        }

        if (entity.isShiftKeyDown()) {
            this.applyLoopingAnimation(LivingAnimations.SNEAK, ageInTicks, 1.0F, weight);
        } else if (Math.abs(limbSwingAmount) > MOVEMENT_THRESHOLD
                && AnimationUtil.isMovingHorizontally(entity)
                && AnimationUtil.shouldUseRunAnimation(entity)) {
            this.applyLoopingAnimation(AnimationUtil.getRunAnimation(entity), ageInTicks, 1.15F, weight);
        } else if (Math.abs(limbSwingAmount) > MOVEMENT_THRESHOLD
                && AnimationUtil.isMovingHorizontally(entity)) {
            this.applyLoopingAnimation(AnimationUtil.getWalkAnimation(entity), ageInTicks, 1.0F, weight);
        } else {
            this.applyLoopingAnimation(AnimationUtil.getIdleAnimation(entity), ageInTicks, 1.0F, weight);
        }
    }

    private void applyActiveRigAnimation(RigClientAnimationState.Active active, float ageInTicks, float weight) {
        this.applyAnimationFromStart(RigAnimationResolver.get(active.animationId()), active.sampleTicks(ageInTicks), 1.0F, weight);
    }

    private void applyBowAimAnimation(T entity, float limbSwingAmount, float ageInTicks, float headPitch) {
        RigAnimationId animationId = RigBowAnimationSelector.aimForPitch(headPitch);
        Map<ModelPart, ModelPartPose> baselinePose = this.capturePose();

        this.applyBaseRigAnimation(entity, limbSwingAmount, ageInTicks, 1.0F, true);
        Map<ModelPart, ModelPartPose> basePose = this.capturePose();

        this.restorePose(baselinePose);
        this.applyAnimationFromStart(
                RigAnimationResolver.get(animationId),
                Math.min((float) entity.getTicksUsingItem(), RigAnimationSpecs.get(animationId).durationTicks()),
                1.0F,
                1.0F
        );
        Map<ModelPart, ModelPartPose> activePose = this.capturePose();

        this.restorePose(basePose);
        this.blendPose(basePose, activePose, bowAimWeight(entity), null, RigAnimationPlaybackType.UPPER_BODY);
    }

    private static boolean isUsingBow(Mob entity) {
        return entity.isUsingItem() && entity.getUseItem().getItem() instanceof BowItem;
    }

    private static float bowAimWeight(Mob entity) {
        return Math.min(1.0F, Math.max(0.0F, entity.getTicksUsingItem() / 2.0F));
    }

    public void applyLoopingAnimation(AnimationDefinition animation, float ageInTicks, float speed, float weight) {
        long elapsedMilliseconds = (long) (ageInTicks * TICK_TO_MILLISECONDS * speed);
        KeyframeAnimations.animate(this.animationView, animation, elapsedMilliseconds, weight, this.animationVectorCache);
    }

    public void applyAnimationFromStart(AnimationDefinition animation, float elapsedTicks, float speed, float weight) {
        float safeElapsedTicks = Math.max(0.0F, elapsedTicks);
        long elapsedMilliseconds = (long) (safeElapsedTicks * TICK_TO_MILLISECONDS * speed);

        KeyframeAnimations.animate(this.animationView, animation, elapsedMilliseconds, weight, this.animationVectorCache);
    }

    private Map<ModelPart, ModelPartPose> capturePose() {
        Map<ModelPart, ModelPartPose> pose = new IdentityHashMap<>();
        this.modelRoot.getAllParts().forEach(part -> pose.put(part, ModelPartPose.from(part)));
        return pose;
    }

    private void restorePose(Map<ModelPart, ModelPartPose> pose) {
        pose.forEach((part, partPose) -> partPose.applyTo(part));
    }

    private void blendPose(Map<ModelPart, ModelPartPose> basePose, Map<ModelPart, ModelPartPose> activePose, float activeWeight, TransitionPose transitionPose, RigAnimationPlaybackType playbackType) {
        basePose.forEach((part, basePartPose) -> {
            if (!shouldBlendPart(playbackType, part)) {
                return;
            }

            ModelPartPose activePartPose = activePose.get(part);
            if (activePartPose != null) {
                ModelPartPose startPartPose = basePartPose.withRotationsFrom(transitionPose == null ? null : transitionPose.pose().get(part));
                startPartPose.blendTo(part, activePartPose, activeWeight);
            }
        });
    }

    private boolean shouldBlendPart(RigAnimationPlaybackType playbackType, ModelPart part) {
        return switch (playbackType) {
            case DEFAULT -> true;
            case UPPER_BODY -> this.upperBodyPlaybackParts.contains(part);
            case MAIN_HAND -> this.rightHandPlaybackParts.contains(part);
            case LEFT_HAND -> this.leftHandPlaybackParts.contains(part);
            case BOTH_HAND -> this.rightHandPlaybackParts.contains(part) || this.leftHandPlaybackParts.contains(part);
        };
    }

    private static Set<ModelPart> collectPartTree(ModelPart root) {
        return collectPartTrees(root);
    }

    private static Set<ModelPart> collectPartTrees(ModelPart... roots) {
        Set<ModelPart> parts = Collections.newSetFromMap(new IdentityHashMap<>());
        for (ModelPart root : roots) {
            root.getAllParts().forEach(parts::add);
        }
        return parts;
    }

    private void flattenAnimatedRootIntoTopLevelParts() {
        if (isIdentityPose(this.modelRoot)) {
            return;
        }

        Matrix4f rootMatrix = createPoseMatrix(this.modelRoot);
        flattenChild(rootMatrix, this.head);
        flattenChild(rootMatrix, this.body);
        flattenChild(rootMatrix, this.rightArm);
        flattenChild(rootMatrix, this.leftArm);
        flattenChild(rootMatrix, this.rightLeg);
        flattenChild(rootMatrix, this.leftLeg);
        this.modelRoot.resetPose();
    }

    private void compensateServerMotion(RigClientAnimationState.Active active, float activeWeight, float ageInTicks) {
        if (active == null || activeWeight <= 0.0F) return;
        Vec3 offset = RigPoseLibrary.modelMotion(active.animationId(), active.sampleTicks(ageInTicks));
        if (offset.lengthSqr() < 1.0E-8D) return;
        float x = (float) (-offset.x * activeWeight);
        float y = RigAnimationSpecs.get(active.animationId()).moveVertical() ? (float) (-offset.y * activeWeight) : 0.0F;
        float z = (float) (-offset.z * activeWeight);
        translateTopLevelPart(this.head, x, y, z);
        translateTopLevelPart(this.body, x, y, z);
        translateTopLevelPart(this.rightArm, x, y, z);
        translateTopLevelPart(this.leftArm, x, y, z);
        translateTopLevelPart(this.rightLeg, x, y, z);
        translateTopLevelPart(this.leftLeg, x, y, z);
    }

    private static void translateTopLevelPart(ModelPart part, float x, float y, float z) {
        part.x += x;
        part.y += y;
        part.z += z;
    }

    public Vector3f translateToRandomArrowPosition(PoseStack poseStack, RandomSource random) {
        float minX;
        float maxX;
        float minY;
        float maxY;
        float minZ;
        float maxZ;

        switch (random.nextInt(10)) {
            case 0 -> {
                this.head.translateAndRotate(poseStack);
                minX = -4.0F;
                maxX = 4.0F;
                minY = -8.0F;
                maxY = 0.0F;
                minZ = -4.0F;
                maxZ = 4.0F;
            }

            case 1 -> {
                this.body.translateAndRotate(poseStack);
                minX = -4.0F;
                maxX = 4.0F;
                minY = 0.0F;
                maxY = 12.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 2 -> {
                this.right_arm.translateAndRotate(poseStack);
                minX = this.slim ? -2.0F : -3.0F;
                maxX = 1.0F;
                minY = -2.0F;
                maxY = 5.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 3 -> {
                this.right_arm.translateAndRotate(poseStack);
                this.right_hand.translateAndRotate(poseStack);
                minX = this.slim ? -2.0F : -3.0F;
                maxX = 1.0F;
                minY = 0.0F;
                maxY = 6.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 4 -> {
                this.left_arm.translateAndRotate(poseStack);
                minX = -1.0F;
                maxX = this.slim ? 2.0F : 3.0F;
                minY = -2.0F;
                maxY = 5.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 5 -> {
                this.left_arm.translateAndRotate(poseStack);
                this.left_hand.translateAndRotate(poseStack);
                minX = -1.0F;
                maxX = this.slim ? 2.0F : 3.0F;
                minY = 0.0F;
                maxY = 6.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 6 -> {
                this.right_leg.translateAndRotate(poseStack);
                minX = -2.0F;
                maxX = 2.0F;
                minY = 0.0F;
                maxY = 7.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 7 -> {
                this.right_leg.translateAndRotate(poseStack);
                this.right_lower_leg.translateAndRotate(poseStack);
                minX = -2.0F;
                maxX = 2.0F;
                minY = 0.0F;
                maxY = 6.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 8 -> {
                this.left_leg.translateAndRotate(poseStack);
                minX = -2.0F;
                maxX = 2.0F;
                minY = 0.0F;
                maxY = 7.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            default -> {
                this.left_leg.translateAndRotate(poseStack);
                this.left_lower_leg.translateAndRotate(poseStack);
                minX = -2.0F;
                maxX = 2.0F;
                minY = 0.0F;
                maxY = 6.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }
        }

        float randomX = random.nextFloat();
        float randomY = random.nextFloat();
        float randomZ = random.nextFloat();
        poseStack.translate(Mth.lerp(randomX, minX, maxX) / 16.0F, Mth.lerp(randomY, minY, maxY) / 16.0F, Mth.lerp(randomZ, minZ, maxZ) / 16.0F
        );
        return new Vector3f(-(randomX * 2.0F - 1.0F), -(randomY * 2.0F - 1.0F), -(randomZ * 2.0F - 1.0F));
    }

    private static boolean isIdentityPose(ModelPart part) {
        return Math.abs(part.x) < 1.0E-6F
                && Math.abs(part.y) < 1.0E-6F
                && Math.abs(part.z) < 1.0E-6F
                && Math.abs(part.xRot) < 1.0E-6F
                && Math.abs(part.yRot) < 1.0E-6F
                && Math.abs(part.zRot) < 1.0E-6F
                && Math.abs(part.xScale - 1.0F) < 1.0E-6F
                && Math.abs(part.yScale - 1.0F) < 1.0E-6F
                && Math.abs(part.zScale - 1.0F) < 1.0E-6F;
    }

    private static Matrix4f createPoseMatrix(ModelPart part) {
        return new Matrix4f()
                .translation(part.x, part.y, part.z)
                .rotateZ(part.zRot)
                .rotateY(part.yRot)
                .rotateX(part.xRot)
                .scale(part.xScale, part.yScale, part.zScale);
    }

    private static void flattenChild(Matrix4f parent, ModelPart child) {
        Matrix4f combined = new Matrix4f(parent).mul(createPoseMatrix(child));
        Vector3f position = combined.getTranslation(new Vector3f());
        Vector3f scale = combined.getScale(new Vector3f());
        Quaternionf rotation = combined.getUnnormalizedRotation(new Quaternionf()).normalize();

        child.x = position.x;
        child.y = position.y;
        child.z = position.z;
        child.xScale = scale.x;
        child.yScale = scale.y;
        child.zScale = scale.z;
        setEulerZYX(child, rotation);
    }

    private static void setEulerZYX(ModelPart part, Quaternionf q) {
        double x = q.x();
        double y = q.y();
        double z = q.z();
        double w = q.w();

        double sinXCosY = 2.0D * (w * x + y * z);
        double cosXCosY = 1.0D - 2.0D * (x * x + y * y);
        double xRot = Math.atan2(sinXCosY, cosXCosY);

        double sinY = 2.0D * (w * y - z * x);
        double yRot = Math.abs(sinY) >= 1.0D ? Math.copySign(Math.PI / 2.0D, sinY) : Math.asin(sinY);

        double sinZCosY = 2.0D * (w * z + x * y);
        double cosZCosY = 1.0D - 2.0D * (y * y + z * z);
        double zRot = Math.atan2(sinZCosY, cosZCosY);

        part.xRot = (float) xRot;
        part.yRot = (float) yRot;
        part.zRot = (float) zRot;
    }

    private record AnimationKey(RigAnimationId animationId, int startedAtTick) {
    }

    private record TransitionPose(AnimationKey activeKey, Map<ModelPart, ModelPartPose> pose) {
    }

    private record ModelPartPose(
            float x,
            float y,
            float z,
            float xRot,
            float yRot,
            float zRot,
            float xScale,
            float yScale,
            float zScale
    ) {
        private static ModelPartPose from(ModelPart part) {
            return new ModelPartPose(part.x, part.y, part.z, part.xRot, part.yRot, part.zRot, part.xScale, part.yScale, part.zScale);
        }

        private void applyTo(ModelPart part) {
            part.x = this.x;
            part.y = this.y;
            part.z = this.z;
            part.xRot = this.xRot;
            part.yRot = this.yRot;
            part.zRot = this.zRot;
            part.xScale = this.xScale;
            part.yScale = this.yScale;
            part.zScale = this.zScale;
        }

        private void blendTo(ModelPart part, ModelPartPose target, float weight) {
            part.x = Mth.lerp(weight, this.x, target.x);
            part.y = Mth.lerp(weight, this.y, target.y);
            part.z = Mth.lerp(weight, this.z, target.z);

            Quaternionf rotation = new Quaternionf().rotationZYX(this.zRot, this.yRot, this.xRot);
            rotation.slerp(new Quaternionf().rotationZYX(target.zRot, target.yRot, target.xRot), weight);
            setEulerZYX(part, rotation);

            part.xScale = Mth.lerp(weight, this.xScale, target.xScale);
            part.yScale = Mth.lerp(weight, this.yScale, target.yScale);
            part.zScale = Mth.lerp(weight, this.zScale, target.zScale);
        }

        private ModelPartPose withRotationsFrom(ModelPartPose rotationPose) {
            if (rotationPose == null) {
                return this;
            }

            return new ModelPartPose(
                    this.x,
                    this.y,
                    this.z,
                    rotationPose.xRot,
                    rotationPose.yRot,
                    rotationPose.zRot,
                    this.xScale,
                    this.yScale,
                    this.zScale
            );
        }

    }

    private static final class AnimationView extends HierarchicalModel<Entity> {
        private final ModelPart root;

        private AnimationView(ModelPart root) {
            this.root = root;
        }

        @Override
        public @NotNull ModelPart root() {
            return this.root;
        }

        @Override
        public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        }
    }
}
