package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HookGunHookEntity;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.util.HookUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import com.pla.annoyingvillagers.client.compat.LegacyEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class HookGunHookRenderer extends LegacyEntityRenderer<HookGunHookEntity> {
    private static final Identifier ROPE_TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/hook_gun_rope.png");
    private static final RenderType ROPE_RENDER = net.minecraft.client.renderer.rendertype.RenderTypes.entitySolid(ROPE_TEXTURE);

    private final EntityRendererProvider.Context context;
    private final ItemModelResolver itemModelResolver;

    public HookGunHookRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.context = context;
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void submit(LegacyEntityRenderState<HookGunHookEntity> state, PoseStack poseStack,
                       SubmitNodeCollector collector, CameraRenderState camera) {
        super.submit(state, poseStack, collector, camera);
        HookGunHookEntity hook = state.entity;
        float partialTicks = state.partialTick;
        LivingEntity owner = hook.getHookOwner();
        if (owner == null || !owner.isAlive()) {
            renderHookItem(state, poseStack, collector, new Vec3(0.0D, 0.0D, 1.0D), 1, partialTicks);
            return;
        }

        int handRight = hook.isRightHand() ? 1 : -1;
        Vec3 handPosition = HookGunItem.getHookStartPosition(owner, hook.isRightHand());
        Vec3 attachDirection = getAttachDirection(hook, handPosition, partialTicks);

        renderHookItem(state, poseStack, collector, attachDirection, handRight, partialTicks);
        renderRope(hook, handPosition, partialTicks, poseStack, collector, state.lightCoords);
    }

    private void renderHookItem(
            LegacyEntityRenderState<HookGunHookEntity> state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            Vec3 attachDirection,
            int handRight,
            float partialTicks
    ) {
        HookGunHookEntity hook = state.entity;
        ItemStack stack = hook.getItem();
        if (stack.isEmpty()) {
            return;
        }

        ItemDisplayContext displayContext = HookItemRenderTransforms.getHookGunProjectileDisplayContext(stack);
        poseStack.pushPose();
        float projectileScale = HookItemRenderTransforms.getHookGunProjectileScale(stack);
        poseStack.scale(projectileScale, projectileScale, projectileScale);

        if (HookUtil.shouldUseShieldFacing(stack)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - getOwnerLookYaw(hook, partialTicks)));
            if (displayContext == ItemDisplayContext.FIXED) {
                HookItemRenderTransforms.applyShieldProjectileTransform(poseStack);
            }
        } else if (!HookUtil.shouldRenderWithoutProjectileSpin(stack)) {
            Vec3 shootDirection = attachDirection.scale(-1.0D);
            double horizontal = Math.sqrt(shootDirection.x * shootDirection.x + shootDirection.z * shootDirection.z);
            float yaw = (float) (Mth.atan2(shootDirection.x, shootDirection.z) * Mth.RAD_TO_DEG);
            float pitch = (float) (Mth.atan2(shootDirection.y, horizontal) * Mth.RAD_TO_DEG);
            HookItemRenderTransforms.applyProjectileFacing(poseStack, stack, yaw, pitch);
            if (!HookUtil.shouldAlignSharpEdge(stack)) {
                poseStack.mulPose(Axis.YP.rotationDegrees(45.0F * handRight));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F));
            }
        }

        state.item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);

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
            SubmitNodeCollector collector,
            int packedLight
    ) {
        poseStack.pushPose();
        Vec3 hookPosition = partialPosition(hook, partialTicks);
        Vec3 finish = handPosition.subtract(hookPosition);
        collector.submitCustomGeometry(poseStack, ROPE_RENDER,
                (pose, vertexBuffer) -> drawSegment(Vec3.ZERO, finish, vertexBuffer, pose, packedLight));
        poseStack.popPose();
    }

    @Override
    public void extractRenderState(HookGunHookEntity hook, LegacyEntityRenderState<HookGunHookEntity> state,
                                   float partialTick) {
        super.extractRenderState(hook, state, partialTick);
        ItemStack stack = hook.getItem();
        ItemDisplayContext display = HookItemRenderTransforms.getHookGunProjectileDisplayContext(stack);
        this.itemModelResolver.updateForTopItem(state.item, stack, display, hook.level(), hook, hook.getId());
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
            PoseStack.Pose pose,
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

            vertex(vertexBuffer, pose, corner1Start, normal1, 0.0F, 0.0F, packedLight);
            vertex(vertexBuffer, pose, corner2Start, normal2, 1.0F, 0.0F, packedLight);
            vertex(vertexBuffer, pose, corner2Finish, normal2, 1.0F, 1.0F, packedLight);
            vertex(vertexBuffer, pose, corner1Finish, normal1, 0.0F, 1.0F, packedLight);
        }
    }

    private static void vertex(
            VertexConsumer vertexBuffer,
            PoseStack.Pose pose,
            Vec3 position,
            Vec3 normal,
            float u,
            float v,
            int packedLight
    ) {
        vertexBuffer.addVertex(pose, (float) position.x, (float) position.y, (float) position.z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, (float) normal.x, (float) normal.y, (float) normal.z)
                ;
    }

    @Override
    public boolean shouldRender(@NotNull HookGunHookEntity entity, @NotNull Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull HookGunHookEntity entity) {
        return ROPE_TEXTURE;
    }
}
