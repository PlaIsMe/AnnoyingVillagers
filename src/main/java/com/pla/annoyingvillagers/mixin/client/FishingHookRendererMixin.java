package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.util.EpicfightUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

@Mixin(FishingHookRenderer.class)
public abstract class FishingHookRendererMixin extends EntityRenderer<FishingHook> {
    @Unique
    private static final ResourceLocation annoyingVillagers$textureLocation =
            ResourceLocation.withDefaultNamespace("textures/entity/fishing_hook.png");
    @Unique
    private static final RenderType annoyingVillagers$renderType =
            RenderType.entityCutout(annoyingVillagers$textureLocation);
    @Unique
    private static final double annoyingVillagers$viewBobbingScale = 960.0D;
    @Unique
    private static final Vec3f annoyingVillagers$noTranslation = new Vec3f(0.0F, 0.0F, 0.0F);

    protected FishingHookRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(
            method = "render(Lnet/minecraft/world/entity/projectile/FishingHook;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void annoyingVillagers$renderWithEpicFightRodAnchor(
            FishingHook hook,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            CallbackInfo ci
    ) {
        Player player = hook.getPlayerOwner();
        if (player != null) {
            poseStack.pushPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix = pose.pose();
            Matrix3f normal = pose.normal();
            VertexConsumer hookBuffer = buffer.getBuffer(annoyingVillagers$renderType);
            annoyingVillagers$vertex(hookBuffer, matrix, normal, packedLight, 0.0F, 0, 0, 1);
            annoyingVillagers$vertex(hookBuffer, matrix, normal, packedLight, 1.0F, 0, 1, 1);
            annoyingVillagers$vertex(hookBuffer, matrix, normal, packedLight, 1.0F, 1, 1, 0);
            annoyingVillagers$vertex(hookBuffer, matrix, normal, packedLight, 0.0F, 1, 0, 0);
            poseStack.popPose();

            int armSign = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
            ItemStack mainHand = player.getMainHandItem();
            if (!mainHand.canPerformAction(ToolActions.FISHING_ROD_CAST)) {
                armSign = -armSign;
            }

            float attackAnim = player.getAttackAnim(partialTicks);
            float attackSwing = Mth.sin(Mth.sqrt(attackAnim) * (float) Math.PI);
            float bodyYaw = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * Mth.DEG_TO_RAD;
            double sinYaw = Mth.sin(bodyYaw);
            double cosYaw = Mth.cos(bodyYaw);
            double armOffset = (double) armSign * 0.35D;

            double lineX;
            double lineY;
            double lineZ;
            float lineYOffset;

            if (annoyingVillagers$isFirstPersonOwner(player)) {
                double fovScale = annoyingVillagers$viewBobbingScale
                        / (double) this.entityRenderDispatcher.options.fov().get().intValue();
                Vec3 nearPlane = this.entityRenderDispatcher.camera.getNearPlane()
                        .getPointOnPlane((float) armSign * 0.525F, -0.1F);
                nearPlane = nearPlane.scale(fovScale);
                nearPlane = nearPlane.yRot(attackSwing * 0.5F);
                nearPlane = nearPlane.xRot(-attackSwing * 0.7F);

                lineX = Mth.lerp((double) partialTicks, player.xo, player.getX()) + nearPlane.x;
                lineY = Mth.lerp((double) partialTicks, player.yo, player.getY()) + nearPlane.y;
                lineZ = Mth.lerp((double) partialTicks, player.zo, player.getZ()) + nearPlane.z;
                lineYOffset = player.getEyeHeight();
            } else {
                Vec3 epicFightAnchor = annoyingVillagers$getEpicFightRodAnchor(player, armSign, partialTicks);
                if (epicFightAnchor != null) {
                    lineX = epicFightAnchor.x;
                    lineY = epicFightAnchor.y;
                    lineZ = epicFightAnchor.z;
                    lineYOffset = 0.0F;
                } else {
                    lineX = Mth.lerp((double) partialTicks, player.xo, player.getX()) - cosYaw * armOffset - sinYaw * 0.8D;
                    lineY = player.yo + (double) player.getEyeHeight() + (player.getY() - player.yo) * (double) partialTicks - 0.45D;
                    lineZ = Mth.lerp((double) partialTicks, player.zo, player.getZ()) - sinYaw * armOffset + cosYaw * 0.8D;
                    lineYOffset = player.isCrouching() ? -0.1875F : 0.0F;
                }
            }

            double hookX = Mth.lerp((double) partialTicks, hook.xo, hook.getX());
            double hookY = Mth.lerp((double) partialTicks, hook.yo, hook.getY()) + 0.25D;
            double hookZ = Mth.lerp((double) partialTicks, hook.zo, hook.getZ());
            float stringX = (float) (lineX - hookX);
            float stringY = (float) (lineY - hookY) + lineYOffset;
            float stringZ = (float) (lineZ - hookZ);

            VertexConsumer lineBuffer = buffer.getBuffer(RenderType.lineStrip());
            PoseStack.Pose linePose = poseStack.last();
            for (int segment = 0; segment <= 16; ++segment) {
                annoyingVillagers$stringVertex(
                        stringX,
                        stringY,
                        stringZ,
                        lineBuffer,
                        linePose,
                        annoyingVillagers$fraction(segment, 16),
                        annoyingVillagers$fraction(segment + 1, 16)
                );
            }

            poseStack.popPose();
            super.render(hook, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }

        ci.cancel();
    }

    @Unique
    private boolean annoyingVillagers$isFirstPersonOwner(Player player) {
        return this.entityRenderDispatcher.options != null
                && this.entityRenderDispatcher.options.getCameraType().isFirstPerson()
                && player == Minecraft.getInstance().player;
    }

    @Unique
    private static Vec3 annoyingVillagers$getEpicFightRodAnchor(Player player, int armSign, float partialTicks) {
        if (!player.getMainHandItem().canPerformAction(ToolActions.FISHING_ROD_CAST)
                && !player.getOffhandItem().canPerformAction(ToolActions.FISHING_ROD_CAST)) {
            return null;
        }

        try {
            if (Armatures.BIPED.get() == null) {
                return null;
            }

            Joint joint = armSign > 0 ? Armatures.BIPED.get().toolR : Armatures.BIPED.get().toolL;
            if (joint == null) {
                return null;
            }

            Vec3 anchor = EpicfightUtil.getJointWithTranslation(
                    player,
                    annoyingVillagers$noTranslation,
                    joint,
                    0.5F,
                    0.0D
            );
            if (anchor == null) {
                return null;
            }

            return anchor.add(
                    Mth.lerp((double) partialTicks, player.xo, player.getX()) - player.getX(),
                    Mth.lerp((double) partialTicks, player.yo, player.getY()) - player.getY(),
                    Mth.lerp((double) partialTicks, player.zo, player.getZ()) - player.getZ()
            );
        } catch (Exception ignored) {
            return null;
        }
    }

    @Unique
    private static float annoyingVillagers$fraction(int numerator, int denominator) {
        return (float) numerator / (float) denominator;
    }

    @Unique
    private static void annoyingVillagers$vertex(
            VertexConsumer consumer,
            Matrix4f matrix,
            Matrix3f normal,
            int lightmapUV,
            float x,
            int y,
            int u,
            int v
    ) {
        consumer.vertex(matrix, x - 0.5F, (float) y - 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv((float) u, (float) v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(lightmapUV)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    @Unique
    private static void annoyingVillagers$stringVertex(
            float x,
            float y,
            float z,
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float currentFraction,
            float nextFraction
    ) {
        float currentX = x * currentFraction;
        float currentY = y * (currentFraction * currentFraction + currentFraction) * 0.5F + 0.25F;
        float currentZ = z * currentFraction;
        float normalX = x * nextFraction - currentX;
        float normalY = y * (nextFraction * nextFraction + nextFraction) * 0.5F + 0.25F - currentY;
        float normalZ = z * nextFraction - currentZ;
        float normalLength = Mth.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
        normalX /= normalLength;
        normalY /= normalLength;
        normalZ /= normalLength;
        consumer.vertex(pose.pose(), currentX, currentY, currentZ)
                .color(0, 0, 0, 255)
                .normal(pose.normal(), normalX, normalY, normalZ)
                .endVertex();
    }
}
