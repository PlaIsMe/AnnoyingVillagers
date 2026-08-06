package com.pla.annoyingvillagers.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

public final class ArmatureUtil {
    private ArmatureUtil() {
    }

    public static Vec3 getJointWorldPos(LivingEntityPatch<?> livingEntityPatch, Joint joint, float partialTicks) {
        Pose pose = livingEntityPatch.getAnimator().getPose(partialTicks);
        OpenMatrix4f matrix = getMatrix(livingEntityPatch, joint, partialTicks, pose);
        return OpenMatrix4f.transform(matrix, Vec3.ZERO);
    }

    public static Vec3 getJointWorldPosition(LivingEntityPatch<?> livingEntityPatch, Joint joint, Vec3 offset,
                                             float elapsedTime, float partialTicks) {
        Pose pose = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null))
                .getAnimation().get().getPoseByTime(livingEntityPatch, elapsedTime, partialTicks);
        OpenMatrix4f matrix = getMatrix(livingEntityPatch, joint, partialTicks, pose);
        return OpenMatrix4f.transform(matrix, offset);
    }

    public static Vec3 getJointWorldPosition(LivingEntityPatch<?> livingEntityPatch, Joint joint, Vec3 offset,
                                             float partialTicks) {
        Pose pose = livingEntityPatch.getAnimator().getPose(partialTicks);
        OpenMatrix4f matrix = getMatrix(livingEntityPatch, joint, partialTicks, pose);
        return OpenMatrix4f.transform(matrix, offset);
    }

    public static Vec3 getJointWorldPosition(LivingEntityPatch<?> livingEntityPatch, Joint joint, Vec3 offset) {
        Pose pose = livingEntityPatch.getAnimator().getPose(1.0F);
        OpenMatrix4f matrix = getMatrix(livingEntityPatch, joint, pose);
        return OpenMatrix4f.transform(matrix, offset);
    }

    private static OpenMatrix4f getMatrix(LivingEntityPatch<?> livingEntityPatch, Joint joint, float partialTicks, Pose pose) {
        Vec3 position = livingEntityPatch.getOriginal().getPosition(partialTicks);
        OpenMatrix4f entityMatrix = OpenMatrix4f.createTranslation((float) position.x, (float) position.y, (float) position.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(livingEntityPatch.getModelMatrix(partialTicks)));
        return new OpenMatrix4f(livingEntityPatch.getArmature().getBoundTransformFor(pose, joint)).mulFront(entityMatrix);
    }

    private static OpenMatrix4f getMatrix(LivingEntityPatch<?> livingEntityPatch, Joint joint, Pose pose) {
        Vec3 position = livingEntityPatch.getOriginal().position();
        OpenMatrix4f entityMatrix = OpenMatrix4f.createTranslation((float) position.x, (float) position.y, (float) position.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(livingEntityPatch.getModelMatrix(1.0F)));
        return new OpenMatrix4f(livingEntityPatch.getArmature().getBoundTransformFor(pose, joint)).mulFront(entityMatrix);
    }
}
