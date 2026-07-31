package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HookGunHookEntity;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HookGunHookRenderer extends EntityRenderer<HookGunHookEntity> {
    private static final ResourceLocation ROPE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/hook_gun_rope.png");
    private static final RenderType ROPE_RENDER = RenderType.entitySolid(ROPE_TEXTURE);

    private final EntityRendererProvider.Context context;

    public HookGunHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void render(
            @NotNull HookGunHookEntity hook,
            float entityYaw,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight
    ) {
        LivingEntity owner = hook.getHookOwner();
        if (owner == null || !owner.isAlive()) {
            renderHookItem(hook, poseStack, buffer, packedLight, new Vec3(0.0D, 0.0D, 1.0D), 1, partialTicks);
            super.render(hook, entityYaw, partialTicks, poseStack, buffer, packedLight);
            return;
        }

        int handRight = hook.isRightHand() ? 1 : -1;
        Vec3 handPosition = HookGunItem.getHookStartPosition(owner, hook.isRightHand());
        Vec3 attachDirection = getAttachDirection(hook, handPosition, partialTicks);

        renderHookItem(hook, poseStack, buffer, packedLight, attachDirection, handRight, partialTicks);
        renderRope(hook, handPosition, partialTicks, poseStack, buffer, packedLight);

        super.render(hook, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderHookItem(
            HookGunHookEntity hook,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            Vec3 attachDirection,
            int handRight,
            float partialTicks
    ) {
        ItemStack stack = hook.getItem();
        if (stack.isEmpty()) {
            return;
        }

        BakedModel model = this.context.getItemRenderer().getModel(stack, hook.level(), null, hook.getId());
        ItemDisplayContext displayContext = HookItemRenderTransforms.getHookGunProjectileDisplayContext(stack, model);
        poseStack.pushPose();
        float projectileScale = HookItemRenderTransforms.getHookGunProjectileScale(stack);
        poseStack.scale(projectileScale, projectileScale, projectileScale);

        if (HookUtil.shouldUseShieldFacing(stack)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - getOwnerLookYaw(hook, partialTicks)));
            if (displayContext == ItemDisplayContext.FIXED) {
                HookItemRenderTransforms.applyShieldProjectileTransform(poseStack, model);
            }
        } else if (!HookUtil.shouldRenderWithoutProjectileSpin(stack)) {
            Vec3 shootDirection = attachDirection.scale(-1.0D);
            double horizontal = Math.sqrt(shootDirection.x * shootDirection.x + shootDirection.z * shootDirection.z);
            float yaw = (float) (Mth.atan2(shootDirection.x, shootDirection.z) * Mth.RAD_TO_DEG);
            float pitch = (float) (Mth.atan2(shootDirection.y, horizontal) * Mth.RAD_TO_DEG);
            HookItemRenderTransforms.applyProjectileFacing(poseStack, stack, model, yaw, pitch);
            if (!HookUtil.shouldAlignSharpEdge(stack)) {
                poseStack.mulPose(Axis.YP.rotationDegrees(45.0F * handRight));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F));
            }
        }

        this.context.getItemRenderer().render(
                stack,
                displayContext,
                false,
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                model
        );

        poseStack.popPose();
    }

    private static float getOwnerLookYaw(HookGunHookEntity hook, float partialTick) {
        Entity owner = hook.getHookOwner();
        if (owner != null) {
            return Mth.lerp(partialTick, owner.yRotO, owner.getYRot());
        }

        return Mth.lerp(partialTick, hook.yRotO, hook.getYRot());
    }

    private void renderRope(
            HookGunHookEntity hook,
            Vec3 handPosition,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer vertexBuffer = buffer.getBuffer(ROPE_RENDER);
        Vec3 hookPosition = partialPosition(hook, partialTicks);
        drawSegment(Vec3.ZERO, handPosition.subtract(hookPosition), vertexBuffer, pose.pose(), pose.normal(), packedLight);
        poseStack.popPose();
    }

    private static Vec3 getAttachDirection(HookGunHookEntity hook, Vec3 handPosition, float partialTicks) {
        Vec3 direction = hook.getDeltaMovement().scale(-1.0D);
        if (direction.lengthSqr() <= 1.0E-7D) {
            direction = handPosition.subtract(partialPosition(hook, partialTicks));
        }
        if (direction.lengthSqr() <= 1.0E-7D) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }
        return direction.normalize();
    }

    private static Vec3 partialPosition(Entity entity, float partialTicks) {
        return new Vec3(
                Mth.lerp(partialTicks, entity.xOld, entity.getX()),
                Mth.lerp(partialTicks, entity.yOld, entity.getY()),
                Mth.lerp(partialTicks, entity.zOld, entity.getZ())
        );
    }

    private static void drawSegment(
            Vec3 start,
            Vec3 finish,
            VertexConsumer vertexBuffer,
            Matrix4f matrix,
            Matrix3f normalMatrix,
            int packedLight
    ) {
        if (start.subtract(finish).length() < 0.05D) {
            return;
        }

        Vec3 diff = finish.subtract(start);
        Vec3 forward = diff.normalize();
        Vec3 up = forward.cross(new Vec3(1.0D, 0.0D, 0.0D));
        if (up.lengthSqr() <= 1.0E-7D) {
            up = forward.cross(new Vec3(0.0D, 0.0D, 1.0D));
        }
        up = up.normalize().scale(0.025D);
        Vec3 side = forward.cross(up).normalize().scale(0.025D);

        Vec3[] corners = new Vec3[] {
                up.scale(-1.0D).add(side.scale(-1.0D)),
                up.add(side.scale(-1.0D)),
                up.add(side),
                up.scale(-1.0D).add(side)
        };

        for (int size = 0; size < 4; size++) {
            Vec3 corner1 = corners[size];
            Vec3 corner2 = corners[(size + 1) % 4];
            Vec3 normal1 = corner1.normalize();
            Vec3 normal2 = corner2.normalize();
            Vec3 corner1Start = start.add(corner1);
            Vec3 corner2Start = start.add(corner2);
            Vec3 corner1Finish = finish.add(corner1);
            Vec3 corner2Finish = finish.add(corner2);

            vertex(vertexBuffer, matrix, normalMatrix, corner1Start, normal1, 0.0F, 0.0F, packedLight);
            vertex(vertexBuffer, matrix, normalMatrix, corner2Start, normal2, 1.0F, 0.0F, packedLight);
            vertex(vertexBuffer, matrix, normalMatrix, corner2Finish, normal2, 1.0F, 1.0F, packedLight);
            vertex(vertexBuffer, matrix, normalMatrix, corner1Finish, normal1, 0.0F, 1.0F, packedLight);
        }
    }

    private static void vertex(
            VertexConsumer vertexBuffer,
            Matrix4f matrix,
            Matrix3f normalMatrix,
            Vec3 position,
            Vec3 normal,
            float u,
            float v,
            int packedLight
    ) {
        vertexBuffer.vertex(matrix, (float) position.x, (float) position.y, (float) position.z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(normalMatrix, (float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();
    }

    @Override
    public boolean shouldRender(@NotNull HookGunHookEntity entity, @NotNull Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HookGunHookEntity entity) {
        return ROPE_TEXTURE;
    }
}
