package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.animation.ObsidianArmorClientAnimationState;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationClientUtil;
import com.pla.annoyingvillagers.client.animation.SpecialAnimationResolver;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPart;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseClip;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseLibrary;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ModelHerobrineObsidianDiamondHelmetArmor extends HumanoidModel<LivingEntity> {
    private final ModelHerobrineObsidianDiamondHelmet<LivingEntity> geometry;
    private LivingEntity wearer;

    public ModelHerobrineObsidianDiamondHelmetArmor(ModelPart bakedRoot) {
        super(createHumanoidRoot(bakedRoot));
        this.geometry = new ModelHerobrineObsidianDiamondHelmet<>(bakedRoot);
        this.geometry.resetAnimationPose();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void prepareForRender(LivingEntity wearer, HumanoidModel<?> original) {
        ((HumanoidModel)original).copyPropertiesTo(this);
        this.wearer = wearer;
        // Mesh-based armor renderers request the model but do not call renderToBuffer.
        applyArmorAnimation();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        applyArmorAnimation();
        this.geometry.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private void applyArmorAnimation() {
        this.geometry.resetAnimationPose();
        if (this.wearer == null) return;
        ObsidianArmorClientAnimationState.State state = ObsidianArmorClientAnimationState.get(this.wearer, ObsidianArmorPart.HELMET);
        if (state == null) return;

        ObsidianArmorPoseClip poseClip = ObsidianArmorPoseLibrary.clip(state.animationId());
        showTrackedTiles(poseClip);
        SpecialAnimationClientUtil.apply(this.geometry, SpecialAnimationResolver.resolve(state.animationId()), state.elapsedTicks());
    }

    private void showTrackedTiles(ObsidianArmorPoseClip clip) {
        for (String boneName : clip.trackedBones()) {
            if (!boneName.contains("_tile_")) continue;
            ModelPart part = this.geometry.animationPart(boneName);
            if (part != null) part.visible = true;
        }
    }

    private static ModelPart createHumanoidRoot(ModelPart bakedRoot) {
        ModelHerobrineObsidianDiamondHelmet<LivingEntity> geometry = new ModelHerobrineObsidianDiamondHelmet<>(bakedRoot);
        ModelPart emptyHat = emptyPart();
        ModelPart emptyBody = emptyPart();
        ModelPart emptyRightArm = emptyPart();
        ModelPart emptyLeftArm = emptyPart();
        ModelPart emptyRightLeg = emptyPart();
        ModelPart emptyLeftLeg = emptyPart();
        return new ModelPart(List.of(), Map.of("head", geometry.Head, "hat", emptyHat, "body", emptyBody, "right_arm", emptyRightArm, "left_arm", emptyLeftArm, "right_leg", emptyRightLeg, "left_leg", emptyLeftLeg));
    }

    private static ModelPart emptyPart() {
        return new ModelPart(Collections.emptyList(), Collections.emptyMap());
    }
}
