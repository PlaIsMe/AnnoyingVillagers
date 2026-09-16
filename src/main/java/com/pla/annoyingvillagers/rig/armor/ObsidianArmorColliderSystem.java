package com.pla.annoyingvillagers.rig.armor;

import com.pla.annoyingvillagers.rig.RigOrientedBox;
import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public final class ObsidianArmorColliderSystem {
    private ObsidianArmorColliderSystem() {}

    public static List<RigOrientedBox> collisionBoxes(LivingEntity wearer, SpecialAnimationId animationId, float elapsedTicks) {
        ObsidianArmorPart part = ObsidianArmorPart.fromAnimationId(animationId);
        List<RigOrientedBox> boxes = new ArrayList<>();
        ObsidianArmorPoseClip clip = ObsidianArmorPoseLibrary.clip(animationId);
        for (ObsidianArmorColliderSpec collider : ObsidianArmorPoseLibrary.colliders(part)) {
            if (!clip.hasTrack(collider.bone())) continue;
            RigPartTransform transform = ObsidianArmorPoseSampler.sample(wearer, animationId, elapsedTicks, collider.bone());
            boxes.add(RigOrientedBox.from(transform, collider.halfX(), collider.halfY(), collider.halfZ(), collider.center()));
        }
        return boxes.isEmpty() ? List.of() : List.copyOf(boxes);
    }
}
