package com.pla.annoyingvillagers.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.LivingEntity;
import java.util.Map;
import java.util.WeakHashMap;

/** Preserves the already-animated parent pose across deferred equipment rendering. */
public final class HumanoidArmorPoseBridge {
    private static final Map<HumanoidRenderState, PartPose[]> POSES = new WeakHashMap<>();

    private HumanoidArmorPoseBridge() {}

    public static void capture(LivingEntity wearer, HumanoidRenderState state) {
        POSES.remove(state);
        if (wearer == null) return;
        var renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(wearer);
        if (renderer instanceof LivingEntityRenderer<?, ?, ?> livingRenderer
                && livingRenderer.getModel() instanceof HumanoidModel<?> source) {
            ModelPart[] parts = parts(source);
            PartPose[] poses = new PartPose[parts.length];
            for (int i = 0; i < parts.length; i++) {
                ModelPart part = parts[i];
                // ModelPart.storePose() omits animated scale in 26.1.
                poses[i] = new PartPose(part.x, part.y, part.z, part.xRot, part.yRot, part.zRot,
                        part.xScale, part.yScale, part.zScale);
            }
            POSES.put(state, poses);
        }
    }

    static boolean copyWearerPose(LivingEntity wearer, HumanoidRenderState state, HumanoidModel<?> target) {
        PartPose[] poses = POSES.get(state);
        if (poses == null) return false;
        ModelPart[] parts = parts(target);
        for (int i = 0; i < parts.length; i++) parts[i].loadPose(poses[i]);
        return true;
    }

    private static ModelPart[] parts(HumanoidModel<?> model) {
        return new ModelPart[] {model.head, model.hat, model.body, model.rightArm,
                model.leftArm, model.rightLeg, model.leftLeg};
    }
}
