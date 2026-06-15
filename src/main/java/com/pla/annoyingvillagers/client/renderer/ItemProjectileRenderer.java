package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import com.pla.annoyingvillagers.entity.ItemProjectile;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ItemProjectileRenderer extends EntityRenderer<ItemProjectile> {
    private final ItemRenderer itemRenderer;

    public ItemProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15F;
    }

    private static void applyRandomSpin(PoseStack poseStack, float age, int seed) {
        int mixedSeed = mix(seed);

        float direction = (mixedSeed & 1) == 0 ? 1.0F : -1.0F;
        float secondDirection = ((mixedSeed >> 1) & 1) == 0 ? 1.0F : -1.0F;

        float primarySpin = variableSpin(age, mixedSeed, 24.0F, 76.0F) * direction;
        float secondarySpin = variableSpin(age, mixedSeed + 991, 5.0F, 24.0F) * secondDirection;

        float wobble = Mth.sin(
                age * randomRange(mixedSeed + 31, 0.08F, 0.18F)
                        + randomRange(mixedSeed + 57, 0.0F, 6.2831855F)
        ) * randomRange(mixedSeed + 73, 5.0F, 18.0F);

        switch (Math.floorMod(mixedSeed, 6)) {
            case 0 -> {
                poseStack.mulPose(Axis.ZP.rotationDegrees(-primarySpin));
                poseStack.mulPose(Axis.XP.rotationDegrees(wobble));
            }

            case 1 -> {
                poseStack.mulPose(Axis.XP.rotationDegrees(primarySpin));
                poseStack.mulPose(Axis.ZP.rotationDegrees(wobble));
            }

            case 2 -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(primarySpin));
                poseStack.mulPose(Axis.ZP.rotationDegrees(secondarySpin));
            }

            case 3 -> {
                poseStack.mulPose(Axis.XP.rotationDegrees(primarySpin));
                poseStack.mulPose(Axis.YP.rotationDegrees(secondarySpin));
                poseStack.mulPose(Axis.ZP.rotationDegrees(wobble));
            }

            case 4 -> {
                poseStack.mulPose(Axis.ZP.rotationDegrees(primarySpin * 0.75F));
                poseStack.mulPose(Axis.YP.rotationDegrees(primarySpin * 0.35F));
                poseStack.mulPose(Axis.XP.rotationDegrees(secondarySpin));
            }

            default -> {
                poseStack.mulPose(Axis.YP.rotationDegrees(primarySpin * 0.85F));
                poseStack.mulPose(Axis.XP.rotationDegrees(wobble));
                poseStack.mulPose(Axis.ZP.rotationDegrees(secondarySpin));
            }
        }
    }

    private static float variableSpin(float age, int seed, float minSpeed, float maxSpeed) {
        float baseSpeed = randomRange(seed, minSpeed, maxSpeed);
        float pulseFrequency = randomRange(seed + 13, 0.045F, 0.13F);
        float phase = randomRange(seed + 29, 0.0F, 6.2831855F);

        float waveAmount = randomRange(
                seed + 47,
                baseSpeed * 3.0F,
                baseSpeed * 7.0F
        );

        return age * baseSpeed
                + Mth.sin(age * pulseFrequency + phase) * waveAmount
                + Mth.sin(age * pulseFrequency * 0.43F + phase * 1.7F) * waveAmount * 0.45F;
    }

    private static float randomRange(int seed, float min, float max) {
        float value = (mix(seed) & 0x00FFFFFF) / (float) 0x01000000;
        return min + (max - min) * value;
    }

    private static int mix(int value) {
        value ^= value >>> 16;
        value *= 0x7feb352d;
        value ^= value >>> 15;
        value *= 0x846ca68b;
        value ^= value >>> 16;
        return value;
    }

    private static float getOwnerLookYaw(ItemProjectile entity, float partialTick) {
        Entity owner = entity.getOwner();
        if (owner != null) {
            return Mth.lerp(partialTick, owner.yRotO, owner.getYRot());
        }

        return Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
    }

    private static Vec3 getSharpProjectileDirection(ItemProjectile entity, float partialTick) {
        if (entity.isHookAttached()) {
            Entity owner = getRopeOwner(entity.getOwner());
            if (owner != null) {
                Vec3 direction = partialPosition(entity, partialTick).subtract(owner.getEyePosition(partialTick));
                if (direction.lengthSqr() > 1.0E-7D) {
                    return direction;
                }
            }
        }

        Vec3 motion = entity.getDeltaMovement();
        if (motion.lengthSqr() > 1.0E-7D) {
            return motion;
        }

        return Vec3.directionFromRotation(entity.getXRot(), entity.getYRot());
    }

    private static Entity getRopeOwner(Entity owner) {
        if (owner instanceof Projectile projectile && projectile.getOwner() != null) {
            return projectile.getOwner();
        }

        return owner;
    }

    private static Vec3 partialPosition(Entity entity, float partialTick) {
        return new Vec3(
                Mth.lerp(partialTick, entity.xOld, entity.getX()),
                Mth.lerp(partialTick, entity.yOld, entity.getY()),
                Mth.lerp(partialTick, entity.zOld, entity.getZ())
        );
    }

    private static float yawFromDirection(Vec3 direction) {
        return (float) (Mth.atan2(direction.x, direction.z) * Mth.RAD_TO_DEG);
    }

    private static float pitchFromDirection(Vec3 direction) {
        double horizontal = Math.sqrt(direction.x * direction.x + direction.z * direction.z);
        return (float) (Mth.atan2(direction.y, horizontal) * Mth.RAD_TO_DEG);
    }

    @Override
    public void render(
            ItemProjectile entity,
            float entityYaw,
            float partialTick,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight
    ) {
        ItemStack stack = entity.getItem();

        if (!stack.isEmpty()) {
            poseStack.pushPose();
            BakedModel model = this.itemRenderer.getModel(stack, entity.level(), null, entity.getId());

            poseStack.translate(0.0D, 0.15D, 0.0D);
            if (HookUtil.shouldUseShieldFacing(stack)) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - getOwnerLookYaw(entity, partialTick)));
                HookItemRenderTransforms.applyShieldProjectileTransform(poseStack, model);
            } else if (!HookUtil.shouldRenderWithoutProjectileSpin(stack) || !entity.isHookAttached()) {
                boolean sharpItem = HookUtil.shouldAlignSharpEdge(stack);
                float yaw;
                float pitch;

                if (sharpItem) {
                    Vec3 direction = getSharpProjectileDirection(entity, partialTick);
                    yaw = yawFromDirection(direction);
                    pitch = pitchFromDirection(direction);
                } else {
                    yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
                    pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
                }

                HookItemRenderTransforms.applyProjectileFacing(poseStack, stack, model, yaw, pitch);
                if (!sharpItem && !entity.isHookAttached()) {
                    float age = entity.tickCount + partialTick;
                    int spinSeed = entity.getUUID().hashCode();
                    applyRandomSpin(poseStack, age, spinSeed);
                }
            }

            poseStack.scale(0.85F, 0.85F, 0.85F);

            this.itemRenderer.render(
                    stack,
                    HookItemRenderTransforms.getProjectileDisplayContext(stack, model),
                    false,
                    poseStack,
                    buffer,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    model
            );

            poseStack.popPose();
        }

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ItemProjectile entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
