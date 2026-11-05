package com.pla.annoyingvillagers.client.model;

import com.pla.annoyingvillagers.client.layer.VanillaOverlayRenderStateCache;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.LivingEntity;

/** Humanoid armor shell that follows the owning model's captured custom pose. */
public final class ModelPoseSyncedArmor extends HumanoidModel<HumanoidRenderState> {
    public ModelPoseSyncedArmor(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(HumanoidRenderState state) {
        LivingEntity wearer = VanillaOverlayRenderStateCache.getEntity(state);
        if (!HumanoidArmorPoseBridge.copyWearerPose(wearer, state, this)) {
            super.setupAnim(state);
        }
    }
}
