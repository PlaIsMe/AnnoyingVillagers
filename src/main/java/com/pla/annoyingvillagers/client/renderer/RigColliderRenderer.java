package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.rig.RigAnimationSpec;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigAttackWindow;
import com.pla.annoyingvillagers.rig.RigColliderSystem;
import com.pla.annoyingvillagers.rig.RigOrientedBox;
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

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
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

            for (RigAttackWindow window : spec.attackWindows()) {
                boolean attackTime = window.contains(elapsed);
                float red = 1.0F;
                float green = attackTime ? 0.0F : 1.0F;
                float blue = attackTime ? 0.0F : 1.0F;

                for (RigOrientedBox box : RigColliderSystem.collisionBoxes(mob, spec, window, elapsed, bodyYaw)) {
                    renderBox(poseStack, lines, box, red, green, blue);
                }
            }
        }

        poseStack.popPose();
        buffer.endBatch(RenderType.lines());
    }

    private static void renderBox(PoseStack poseStack, VertexConsumer consumer, RigOrientedBox box, float red, float green, float blue) {
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