package com.pla.annoyingvillagers.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions.MoveCoordSetter;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;

public class MoveCoordFuncUtils {
    public static MoveCoordSetter traceLockedTarget(float maxDistance) {
        return (animation, entityPatch, transformSheet) -> {
            LivingEntity target = entityPatch.getTarget();

            if (target == null || (Boolean) ((StaticAnimation) animation.getRealAnimation().get()).getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false)) {
                transformSheet.readFrom(animation.getCoord().copyAll());
                return;
            }

            TransformSheet dynamicSheet = animation.getCoord().copyAll();
            Keyframe[] keyframes = dynamicSheet.getKeyframes();
            int lastFrame = keyframes.length - 1;
            Vec3f lastTranslation = keyframes[lastFrame].transform().translation();
            LivingEntity owner = entityPatch.getOriginal();
            Vec3 ownerPos = owner.position();
            Vec3 targetPos = target.position();
            Vec3 toTarget = targetPos.subtract(ownerPos);
            Vec3 view = owner.getViewVector(1.0F);
            float targetDistance = Math.max((float) toTarget.horizontalDistance() - (target.getBbWidth() + owner.getBbWidth()) * 0.75F, 0.0F);
            Vec3f destination = new Vec3f(lastTranslation.x, 0.0F, -targetDistance);
            float scale = Math.min(destination.length() / maxDistance, 2.0F);

            if (scale > 1.0F) {
                float facingScale = (float) toTarget.normalize().dot(view.normalize());
                scale = Math.max(scale * facingScale, 1.0F);
            }

            for (Keyframe keyframe : keyframes) {
                Vec3f translation = keyframe.transform().translation();

                if (translation.z < 0.0F) {
                    translation.z *= scale;
                }
            }

            transformSheet.readFrom(dynamicSheet);
        };
    }
}
