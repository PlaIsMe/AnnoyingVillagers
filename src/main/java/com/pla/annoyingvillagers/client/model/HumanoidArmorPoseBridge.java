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
    private static final Map<HumanoidRenderState, PoseSnapshot> POSES = new WeakHashMap<>();

    private HumanoidArmorPoseBridge() {}

    public static void capture(LivingEntity wearer, HumanoidRenderState state) {
        POSES.remove(state);
        if (wearer == null) return;
        var renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(wearer);
        if (renderer instanceof LivingEntityRenderer<?, ?, ?> livingRenderer
                && livingRenderer.getModel() instanceof HumanoidModel<?> source) {
            ModelPart[] parts = parts(source);
            PartPose[] poses = capture(parts);
            PartPose[] segments = hasRigSegments(source)
                    ? capture(rigSegments(source)) : null;
            POSES.put(state, new PoseSnapshot(poses, segments));
        }
    }

    public static boolean copyWearerPose(LivingEntity wearer, HumanoidRenderState state, HumanoidModel<?> target) {
        PoseSnapshot snapshot = POSES.get(state);
        if (snapshot == null) return false;
        apply(parts(target), snapshot.parts());
        if (snapshot.rigSegments() != null && hasRigSegments(target)) {
            apply(rigSegments(target), snapshot.rigSegments());
        }
        return true;
    }

    private static PartPose[] capture(ModelPart[] parts) {
        PartPose[] poses = new PartPose[parts.length];
        for (int i = 0; i < parts.length; i++) {
            ModelPart part = parts[i];
            // ModelPart.storePose() omits animated scale in 26.1.
            poses[i] = new PartPose(part.x, part.y, part.z, part.xRot, part.yRot, part.zRot,
                    part.xScale, part.yScale, part.zScale);
        }
        return poses;
    }

    private static void apply(ModelPart[] parts, PartPose[] poses) {
        for (int i = 0; i < parts.length; i++) parts[i].loadPose(poses[i]);
    }

    private static ModelPart[] parts(HumanoidModel<?> model) {
        return new ModelPart[] {model.head, model.hat, model.body, model.rightArm,
                model.leftArm, model.rightLeg, model.leftLeg};
    }

    private static boolean hasRigSegments(HumanoidModel<?> model) {
        return model.rightArm.hasChild("right_hand")
                && model.leftArm.hasChild("left_hand")
                && model.rightLeg.hasChild("right_lower_leg")
                && model.leftLeg.hasChild("left_lower_leg");
    }

    private static ModelPart[] rigSegments(HumanoidModel<?> model) {
        return new ModelPart[] {
                model.rightArm.getChild("right_hand"),
                model.leftArm.getChild("left_hand"),
                model.rightLeg.getChild("right_lower_leg"),
                model.leftLeg.getChild("left_lower_leg")
        };
    }

    private record PoseSnapshot(PartPose[] parts, PartPose[] rigSegments) {}
}
