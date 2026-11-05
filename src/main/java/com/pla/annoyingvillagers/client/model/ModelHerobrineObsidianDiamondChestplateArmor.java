package com.pla.annoyingvillagers.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.client.animation.ObsidianArmorClientAnimationState;
import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPart;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseLibrary;
import com.pla.annoyingvillagers.client.compat.LegacyCustomRenderable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ModelHerobrineObsidianDiamondChestplateArmor extends HumanoidModel<HumanoidRenderState> implements LegacyCustomRenderable {
    private final ModelHerobrineObsidianDiamondChestplate<LivingEntity> geometry;
    private LivingEntity wearer;

    public ModelHerobrineObsidianDiamondChestplateArmor(ModelPart bakedRoot) {
        super(createHumanoidRoot(bakedRoot));
        this.geometry = new ModelHerobrineObsidianDiamondChestplate<>(bakedRoot);
        this.geometry.resetAnimationPose();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void prepareForRender(LivingEntity wearer, HumanoidModel<?> original) {
        this.wearer = wearer;
        applyArmorAnimation();
    }

    @Override
    public void setupAnim(HumanoidRenderState state) {
        this.wearer = VanillaOverlayRenderStateCache.getEntity(state);
        if (!HumanoidArmorPoseBridge.copyWearerPose(this.wearer, state, this)) {
            super.setupAnim(state);
        }
        applyArmorAnimation();
    }

    @Override
    public void av$renderLegacy(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.geometry.renderLegacy(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private void applyArmorAnimation() {
        ObsidianArmorClientAnimationState.State state = this.wearer == null ? null : ObsidianArmorClientAnimationState.get(this.wearer, ObsidianArmorPart.CHESTPLATE);
        if (state == null) {
            this.geometry.resetAnimationPose();
            return;
        }
        // Direct part references avoid a recursive model-tree search for every tile.
        this.geometry.applyAnimationPose(ObsidianArmorPoseLibrary.clip(state.animationId()), state.elapsedTicks());
    }

    private static ModelPart createHumanoidRoot(ModelPart bakedRoot) {
        ModelHerobrineObsidianDiamondChestplate<LivingEntity> geometry = new ModelHerobrineObsidianDiamondChestplate<>(bakedRoot);
        ModelPart emptyHead = new ModelPart(List.of(), Map.of("hat", emptyPart()));
        ModelPart emptyRightLeg = emptyPart();
        ModelPart emptyLeftLeg = emptyPart();
        return new ModelPart(List.of(), Map.of("head", emptyHead, "body", geometry.Body, "right_arm", geometry.RightArm, "left_arm", geometry.LeftArm, "right_leg", emptyRightLeg, "left_leg", emptyLeftLeg));
    }

    private static ModelPart emptyPart() {
        return new ModelPart(Collections.emptyList(), Collections.emptyMap());
    }
}
