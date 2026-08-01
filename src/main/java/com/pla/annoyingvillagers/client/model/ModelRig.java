package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.RigAnimationResolver;
import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigDeathAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigIdleAnimations;
import com.pla.annoyingvillagers.client.animation.rig_animation.RigSneakAnimations;
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
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Set;

public class ModelRig<T extends Mob> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "modelrig"), "main");

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

    public ModelRig(ModelPart root) {
        super(root);
        this.modelRoot = root;
        this.animationView = new AnimationView(root);
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
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(40, 22).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 38).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        right_hand.addOrReplaceChild("right_hand_top_face", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -0.001F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.UP)), PartPose.offset(0.0F, 0.0F, 0.0F));

        right_hand.addOrReplaceChild("right_hand_bottom_face", CubeListBuilder.create().texOffs(44, 16).addBox(-3.0F, 6.001F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.DOWN)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_tool = right_hand.addOrReplaceChild("right_tool", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(32, 54).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 54).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        left_hand.addOrReplaceChild("left_hand_top_face", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -0.001F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.UP)), PartPose.offset(0.0F, 0.0F, 0.0F));

        left_hand.addOrReplaceChild("left_hand_bottom_face", CubeListBuilder.create().texOffs(36, 48).addBox(-1.0F, 6.001F, -2.0F, 4.0F, 0.0F, 4.0F, Set.of(Direction.DOWN)), PartPose.offset(0.0F, 0.0F, 0.0F));

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
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.modelRoot.getAllParts().forEach(ModelPart::resetPose);

        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        RigClientAnimationState.Active activeRigAnimation = RigClientAnimationState.getActive(entity, ageInTicks);

        this.body.getAllParts().forEach(ModelPart::resetPose);
        this.rightArm.getAllParts().forEach(ModelPart::resetPose);
        this.leftArm.getAllParts().forEach(ModelPart::resetPose);
        this.rightLeg.getAllParts().forEach(ModelPart::resetPose);
        this.leftLeg.getAllParts().forEach(ModelPart::resetPose);

        if (entity.isDeadOrDying() || entity.deathTime > 0) {
            float partialTick = Math.max(0.0F, Math.min(1.0F, ageInTicks - entity.tickCount));
            float deathElapsedTicks = Math.max(0.0F, entity.deathTime - 1.0F + partialTick);
            this.applyAnimationFromStart(RigDeathAnimations.DEATH, deathElapsedTicks, 1.0F, 1.0F);
        } else if (this.applyActiveRigAnimation(activeRigAnimation, ageInTicks)) {
            // One-shot rig animations are driven by server packets and should override locomotion.
        } else if (entity.isShiftKeyDown()) {
            this.applyLoopingAnimation(RigSneakAnimations.SNEAK, ageInTicks, 1.0F, 1.0F);
        } else if (Math.abs(limbSwingAmount) > MOVEMENT_THRESHOLD && AnimationUtil.shouldUseRunAnimation(entity, limbSwingAmount)) {
            this.applyLoopingAnimation(AnimationUtil.getRunAnimation(entity), ageInTicks, 1.15F, 1.0F);
        } else if (Math.abs(limbSwingAmount) > MOVEMENT_THRESHOLD) {
            this.applyLoopingAnimation(AnimationUtil.getWalkAnimation(entity), ageInTicks, 1.0F, 1.0F);
        } else {
            this.applyLoopingAnimation(AnimationUtil.getIdleAnimation(entity), ageInTicks, 1.0F, 1.0F);
        }

        this.flattenAnimatedRootIntoTopLevelParts();
        this.lockHeadLookDuringAttack(activeRigAnimation, netHeadYaw, headPitch);
        this.hat.copyFrom(this.head);
    }

    private boolean applyActiveRigAnimation(RigClientAnimationState.Active active, float ageInTicks) {
        if (active == null) {
            return false;
        }

        this.applyAnimationFromStart(RigAnimationResolver.get(active.animationId()), active.elapsedTicks(ageInTicks), 1.0F, 1.0F);
        return true;
    }

    private void lockHeadLookDuringAttack(RigClientAnimationState.Active active, float netHeadYaw, float headPitch) {
        if (active == null || !active.animationId().isAttack()) {
            return;
        }

        this.head.xRot = headPitch * ((float) Math.PI / 180.0F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.head.zRot = 0.0F;
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
                minX = -3.0F;
                maxX = 1.0F;
                minY = -2.0F;
                maxY = 5.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 3 -> {
                this.right_arm.translateAndRotate(poseStack);
                this.right_hand.translateAndRotate(poseStack);
                minX = -3.0F;
                maxX = 1.0F;
                minY = 0.0F;
                maxY = 6.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 4 -> {
                this.left_arm.translateAndRotate(poseStack);
                minX = -1.0F;
                maxX = 3.0F;
                minY = -2.0F;
                maxY = 5.0F;
                minZ = -2.0F;
                maxZ = 2.0F;
            }

            case 5 -> {
                this.left_arm.translateAndRotate(poseStack);
                this.left_hand.translateAndRotate(poseStack);
                minX = -1.0F;
                maxX = 3.0F;
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
