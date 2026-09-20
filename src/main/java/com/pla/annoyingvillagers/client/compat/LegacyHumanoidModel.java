package com.pla.annoyingvillagers.client.compat;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Map;

/** Humanoid counterpart of {@link LegacyHierarchicalModel}. */
public class LegacyHumanoidModel<T extends LivingEntity> extends HumanoidModel<LegacyEntityRenderState<T>> {
    private LegacyEntityRenderState<T> activeState;

    protected LegacyHumanoidModel(ModelPart root) {
        super(adaptLegacyRoot(root));
    }

    /** Bridges the pre-26.1 root-level hat part to the nested humanoid head layout. */
    public static ModelPart adaptLegacyRoot(ModelPart root) {
        if (!root.hasChild("head")) return root;
        ModelPart head = root.getChild("head");
        if (head.hasChild("hat") || !root.hasChild("hat")) return root;
        ModelPart nestedHead = new ModelPart(List.of(), Map.of(
                "legacy_head", head,
                "hat", root.getChild("hat")));
        return new ModelPart(List.of(), Map.of(
                "head", nestedHead,
                "body", root.getChild("body"),
                "right_arm", root.getChild("right_arm"),
                "left_arm", root.getChild("left_arm"),
                "right_leg", root.getChild("right_leg"),
                "left_leg", root.getChild("left_leg")));
    }

    /** 1.21's model property copier, retained for the generated rig armor models. */
    public void copyPropertiesTo(HumanoidModel<LegacyEntityRenderState<T>> target) {
        target.head.loadPose(this.head.storePose());
        target.hat.loadPose(this.hat.storePose());
        target.body.loadPose(this.body.storePose());
        target.rightArm.loadPose(this.rightArm.storePose());
        target.leftArm.loadPose(this.leftArm.storePose());
        target.rightLeg.loadPose(this.rightLeg.storePose());
        target.leftLeg.loadPose(this.leftLeg.storePose());
    }

    @Override
    public final void setupAnim(LegacyEntityRenderState<T> state) {
        this.activeState = state;
        setupAnim(state.entity, state.walkAnimationPos, state.walkAnimationSpeed,
                state.ageInTicks, state.yRot, state.xRot);
        this.activeState = null;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(activeState);
    }
}
