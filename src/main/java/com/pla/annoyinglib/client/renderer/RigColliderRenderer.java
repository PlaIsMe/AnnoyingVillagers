package com.pla.annoyinglib.client.renderer;

import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.client.animation.RigClientAnimationState;
import com.pla.annoyinglib.client.animation.SpecialClientAnimationState;
import com.pla.annoyinglib.rig.RigAnimationSpec;
import com.pla.annoyinglib.rig.RigAnimationSpecs;
import com.pla.annoyinglib.rig.RigAttackWindow;
import com.pla.annoyinglib.rig.RigColliderSystem;
import com.pla.annoyinglib.rig.RigOrientedBox;
import com.pla.annoyinglib.specialanimation.SpecialAnimationSpec;
import com.pla.annoyinglib.specialanimation.SpecialAnimationSpecs;
import com.pla.annoyinglib.specialanimation.SpecialAttackWindow;
import com.pla.annoyinglib.specialanimation.SpecialColliderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, value = Dist.CLIENT)
public final class RigColliderRenderer {
    private static final int[][] EDGES = {{0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 3}, {2, 6}, {3, 7}, {4, 5}, {4, 6}, {5, 7}, {6, 7}};

    private RigColliderRenderer() {}

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || !mc.getEntityRenderDispatcher().shouldRenderHitBoxes()) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 camera = event.getCamera().getPosition();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        VertexConsumer lines = buffer.getBuffer(RenderType.lines());

        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);

        for (var entry : RigClientAnimationState.snapshot().entrySet()) {
            Entity entity = mc.level.getEntity(entry.getKey());
            if (!(entity instanceof Mob mob)) continue;

            float ageInTicks = mob.tickCount + event.getPartialTick();
            RigClientAnimationState.Active active = RigClientAnimationState.getActive(mob, ageInTicks);
            if (active == null) continue;

            RigAnimationSpec spec = RigAnimationSpecs.get(active.animationId());
            if (!spec.damagesTarget()) continue;

            float elapsed = active.sampleTicks(ageInTicks);
            float bodyYaw = Mth.rotLerp(event.getPartialTick(), mob.yBodyRotO, mob.yBodyRot);
            renderRigBoxes(poseStack, lines, mob, spec, elapsed, bodyYaw);
        }

        for (var entry : SpecialClientAnimationState.snapshot().entrySet()) {
            Entity entity = mc.level.getEntity(entry.getKey());
            if (!(entity instanceof Mob mob)) continue;

            float ageInTicks = mob.tickCount + event.getPartialTick();
            SpecialClientAnimationState.Active active = SpecialClientAnimationState.getActive(mob, ageInTicks);
            if (active == null) continue;

            SpecialAnimationSpec spec = SpecialAnimationSpecs.get(active.animationId());
            if (spec.attackWindows().length == 0) continue;

            float elapsed = active.elapsedTicks(ageInTicks);
            float bodyYaw = Mth.rotLerp(event.getPartialTick(), mob.yBodyRotO, mob.yBodyRot);
            renderSpecialBoxes(poseStack, lines, mob, active.animationId(), spec, elapsed, bodyYaw);
        }

        if (ModList.get().isLoaded("herobrine_vessels")) {
            HerobrineVessels.Client.renderArmorColliders(mc, poseStack, lines);
        }

        poseStack.popPose();
        buffer.endBatch(RenderType.lines());
    }

    private static void renderRigBoxes(PoseStack poseStack, VertexConsumer consumer, Mob mob, RigAnimationSpec spec, float elapsed, float bodyYaw) {
        for (RigAttackWindow window : spec.attackWindows()) {
            boolean attackTime = window.contains(elapsed);
            float green = attackTime ? 0.0F : 1.0F;
            float blue = attackTime ? 0.0F : 1.0F;
            for (RigOrientedBox box : RigColliderSystem.collisionBoxes(mob, spec, window, elapsed, bodyYaw)) {
                renderBox(poseStack, consumer, box, 1.0F, green, blue);
            }
        }
    }

    private static void renderSpecialBoxes(PoseStack poseStack, VertexConsumer consumer, Mob mob, com.pla.annoyinglib.specialanimation.SpecialAnimationId animationId, SpecialAnimationSpec spec, float elapsed, float bodyYaw) {
        for (SpecialAttackWindow window : spec.attackWindows()) {
            boolean attackTime = window.contains(elapsed);
            float green = attackTime ? 0.0F : 1.0F;
            float blue = attackTime ? 0.0F : 1.0F;
            for (RigOrientedBox box : SpecialColliderSystem.collisionBoxes(mob, animationId, window, elapsed, bodyYaw)) {
                renderBox(poseStack, consumer, box, 1.0F, green, blue);
            }
        }
    }

    public static void renderBox(PoseStack poseStack, VertexConsumer consumer, RigOrientedBox box, float red, float green, float blue) {
        Vec3[] corners = box.corners();
        PoseStack.Pose pose = poseStack.last();

        for (int[] edge : EDGES) renderLine(pose, consumer, corners[edge[0]], corners[edge[1]], red, green, blue);
    }

    private static void renderLine(PoseStack.Pose pose, VertexConsumer consumer, Vec3 start, Vec3 end, float red, float green, float blue) {
        Vec3 normal = end.subtract(start);
        if (normal.lengthSqr() < 1.0E-8D) return;
        normal = normal.normalize();

        consumer.vertex(pose.pose(), (float) start.x, (float) start.y, (float) start.z)
                .color(red, green, blue, 1.0F)
                .normal(pose.normal(), (float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();

        consumer.vertex(pose.pose(), (float) end.x, (float) end.y, (float) end.z)
                .color(red, green, blue, 1.0F)
                .normal(pose.normal(), (float) normal.x, (float) normal.y, (float) normal.z)
                .endVertex();
    }
}
