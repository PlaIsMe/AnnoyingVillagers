package com.pla.annoyingvillagers.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class NoVfxPortalEvent {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/entities/portal.png");
    private static final RenderType PORTAL_TYPE = RenderTypes.entityTranslucent(TEXTURE);

    private static final int FULL_BRIGHT_LIGHT = LightCoordsUtil.pack(15, 15);
    private static final float PORTAL_HALF_SIZE = 2.5F;

    private static final int GROW_TICKS = 20;
    private static final int SHRINK_TICKS = 20;

    private static final List<PortalInstance> ACTIVE = new ArrayList<>();

    private NoVfxPortalEvent() {}

    public static void spawn(Vec3 pos, int holdTicks) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        ACTIVE.add(new PortalInstance(pos, mc.level.getGameTime(), holdTicks));
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post e) {
        if (false) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            ACTIVE.clear();
            return;
        }

        long nowTick = mc.level.getGameTime();
        Iterator<PortalInstance> it = ACTIVE.iterator();
        while (it.hasNext()) {
            PortalInstance p = it.next();
            if (p.isExpired(nowTick)) it.remove();
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(SubmitCustomGeometryEvent e) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || ACTIVE.isEmpty()) return;

        PoseStack poseStack = e.getPoseStack();
        Vec3 cam = mc.gameRenderer.getMainCamera().position();
        float partial = mc.getDeltaTracker().getGameTimeDeltaPartialTick(false);

        poseStack.pushPose();
        poseStack.translate(-cam.x, -cam.y, -cam.z);

        long nowTick = mc.level.getGameTime();
        for (PortalInstance p : ACTIVE) {
            float time = (nowTick - p.startTick) + partial;
            renderPortal(e.getSubmitNodeCollector(), poseStack, p.pos, time, p.holdTicks);
        }

        poseStack.popPose();
    }

    private static void renderPortal(net.minecraft.client.renderer.SubmitNodeCollector collector,
                                     PoseStack poseStack,
                                     Vec3 basePos,
                                     float animationTime,
                                     int holdTicks) {

        float rotationDegrees = animationTime * Mth.clamp(animationTime / 30F, 1F, 10F);

        float scale = computeScale(animationTime, holdTicks);
        scale = Math.max(0.001F, scale);

        int alpha = Mth.clamp((int) (255F * Mth.clamp(scale * 1.1F, 0F, 1F)), 0, 255);

        poseStack.pushPose();

        poseStack.translate(basePos.x, basePos.y + 0.015, basePos.z);
        poseStack.mulPose(Axis.YP.rotationDegrees(rotationDegrees));
        poseStack.scale(scale, 1.0F, scale);

        int r = 255, g = 255, b = 255, a = alpha;
        collector.submitCustomGeometry(poseStack, PORTAL_TYPE, (pose, vc) -> {
            Matrix4f mat = pose.pose();
            vc.addVertex(mat, -PORTAL_HALF_SIZE, 0, -PORTAL_HALF_SIZE)
                    .setColor(r, g, b, a).setUv(0, 0)
                    .setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT_LIGHT)
                    .setNormal(pose, 0, 1, 0);
            vc.addVertex(mat, PORTAL_HALF_SIZE, 0, -PORTAL_HALF_SIZE)
                    .setColor(r, g, b, a).setUv(1, 0)
                    .setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT_LIGHT)
                    .setNormal(pose, 0, 1, 0);
            vc.addVertex(mat, PORTAL_HALF_SIZE, 0, PORTAL_HALF_SIZE)
                    .setColor(r, g, b, a).setUv(1, 1)
                    .setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT_LIGHT)
                    .setNormal(pose, 0, 1, 0);
            vc.addVertex(mat, -PORTAL_HALF_SIZE, 0, PORTAL_HALF_SIZE)
                    .setColor(r, g, b, a).setUv(0, 1)
                    .setOverlay(OverlayTexture.NO_OVERLAY).setLight(FULL_BRIGHT_LIGHT)
                    .setNormal(pose, 0, 1, 0);
        });

        poseStack.popPose();
    }

    private static float computeScale(float t, int holdTicks) {
        if (t <= GROW_TICKS) {
            float p = t / (float) GROW_TICKS;
            return easeOutCubic(p);
        }
        float shrinkProgress = (t - GROW_TICKS - holdTicks) / (float) SHRINK_TICKS;
        return 1.0F - easeInCubic(Mth.clamp(shrinkProgress, 0F, 1F));
    }

    private static float easeOutCubic(float x) { return 1.0F - (float) Math.pow(1.0F - x, 3); }
    private static float easeInCubic(float x)  { return x * x * x; }

    private static final class PortalInstance {
        final Vec3 pos;
        final long startTick;
        final int holdTicks;
        final int durationTicks;

        PortalInstance(Vec3 pos, long startTick, int holdTicks) {
            this.pos = pos;
            this.startTick = startTick;
            this.holdTicks = holdTicks;
            this.durationTicks = GROW_TICKS + holdTicks + SHRINK_TICKS;
        }

        boolean isExpired(long nowTick) {
            return (nowTick - startTick) > durationTicks;
        }
    }
}
