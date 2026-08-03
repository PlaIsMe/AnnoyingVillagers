package com.pla.annoyingvillagers.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHookRenderer.class)
public abstract class FishingHookRendererMixin extends EntityRenderer<FishingHook> {
    private static final ResourceLocation AV_NPC_HOOK_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/fishing_hook.png");
    private static final RenderType AV_NPC_HOOK_RENDER_TYPE = RenderType.entityCutout(AV_NPC_HOOK_TEXTURE);

    protected FishingHookRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$renderNpcCombatFishingHook(
            FishingHook hook,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            CallbackInfo ci
    ) {
        Entity ownerEntity = hook.getOwner();
        if (!(ownerEntity instanceof LivingEntity owner)
                || !FishingRodGrappleUtil.isNpcCombatFishingHookOwner(ownerEntity)) {
            return;
        }

        poseStack.pushPose();
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose hookPose = poseStack.last();
        Matrix4f hookMatrix = hookPose.pose();
        Matrix3f hookNormal = hookPose.normal();
        VertexConsumer hookConsumer = buffer.getBuffer(AV_NPC_HOOK_RENDER_TYPE);
        annoyingVillagers$vertex(hookConsumer, hookMatrix, hookNormal, packedLight, 0.0F, 0, 0, 1);
        annoyingVillagers$vertex(hookConsumer, hookMatrix, hookNormal, packedLight, 1.0F, 0, 1, 1);
        annoyingVillagers$vertex(hookConsumer, hookMatrix, hookNormal, packedLight, 1.0F, 1, 1, 0);
        annoyingVillagers$vertex(hookConsumer, hookMatrix, hookNormal, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();

        Vec3 handPosition = annoyingVillagers$getNpcRodHandPosition(owner, partialTicks);
        double hookX = Mth.lerp((double) partialTicks, hook.xo, hook.getX());
        double hookY = Mth.lerp((double) partialTicks, hook.yo, hook.getY()) + 0.25D;
        double hookZ = Mth.lerp((double) partialTicks, hook.zo, hook.getZ());
        float lineX = (float) (handPosition.x - hookX);
        float lineY = (float) (handPosition.y - hookY) + (owner.isCrouching() ? -0.1875F : 0.0F);
        float lineZ = (float) (handPosition.z - hookZ);
        VertexConsumer lineConsumer = buffer.getBuffer(RenderType.lineStrip());
        PoseStack.Pose linePose = poseStack.last();

        for (int segment = 0; segment <= 16; ++segment) {
            annoyingVillagers$stringVertex(
                    lineX,
                    lineY,
                    lineZ,
                    lineConsumer,
                    linePose,
                    annoyingVillagers$fraction(segment, 16),
                    annoyingVillagers$fraction(segment + 1, 16)
            );
        }

        poseStack.popPose();
        super.render(hook, entityYaw, partialTicks, poseStack, buffer, packedLight);
        ci.cancel();
    }

    private static Vec3 annoyingVillagers$getNpcRodHandPosition(LivingEntity owner, float partialTicks) {
        int side = annoyingVillagers$getRodHandSide(owner);
        float bodyYaw = Mth.lerp(partialTicks, owner.yBodyRotO, owner.yBodyRot) * ((float) Math.PI / 180F);
        double sin = Mth.sin(bodyYaw);
        double cos = Mth.cos(bodyYaw);
        double sideOffset = (double) side * 0.35D;
        double forwardOffset = 0.8D;
        double x = Mth.lerp((double) partialTicks, owner.xo, owner.getX()) - cos * sideOffset - sin * forwardOffset;
        double y = owner.yo + (double) owner.getEyeHeight() + (owner.getY() - owner.yo) * (double) partialTicks - 0.45D;
        double z = Mth.lerp((double) partialTicks, owner.zo, owner.getZ()) - sin * sideOffset + cos * forwardOffset;
        return new Vec3(x, y, z);
    }

    private static int annoyingVillagers$getRodHandSide(LivingEntity owner) {
        int mainHandSide = owner.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack mainHand = owner.getMainHandItem();
        if (mainHand.canPerformAction(ToolActions.FISHING_ROD_CAST)) {
            return mainHandSide;
        }
        return -mainHandSide;
    }

    private static float annoyingVillagers$fraction(int numerator, int denominator) {
        return (float) numerator / (float) denominator;
    }

    private static void annoyingVillagers$vertex(VertexConsumer consumer, Matrix4f pose, Matrix3f normal, int lightmapUv, float x, int y, int u, int v) {
        consumer.vertex(pose, x - 0.5F, (float) y - 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv((float) u, (float) v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(lightmapUv)
                .normal(normal, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    private static void annoyingVillagers$stringVertex(
            float x,
            float y,
            float z,
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float start,
            float end
    ) {
        float lineX = x * start;
        float lineY = y * (start * start + start) * 0.5F + 0.25F;
        float lineZ = z * start;
        float normalX = x * end - lineX;
        float normalY = y * (end * end + end) * 0.5F + 0.25F - lineY;
        float normalZ = z * end - lineZ;
        float normalLength = Mth.sqrt(normalX * normalX + normalY * normalY + normalZ * normalZ);
        if (normalLength <= 1.0E-6F) {
            return;
        }
        normalX /= normalLength;
        normalY /= normalLength;
        normalZ /= normalLength;
        consumer.vertex(pose.pose(), lineX, lineY, lineZ)
                .color(0, 0, 0, 255)
                .normal(pose.normal(), normalX, normalY, normalZ)
                .endVertex();
    }
}
