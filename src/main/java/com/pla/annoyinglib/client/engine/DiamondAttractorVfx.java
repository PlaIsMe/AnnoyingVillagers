package com.pla.annoyinglib.client.engine;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.client.renderer.AnimatedVfxGeometry;
import com.pla.annoyinglib.client.renderer.AnimatedVfxRenderTypes;
import com.pla.annoyinglib.util.CommonUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import java.util.LinkedHashMap;
import java.util.Map;

/** Circular yellow energy rings follow the wielder and sword for the cast, instead of a one-tick dust burst. */
@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, value = Dist.CLIENT)
public final class DiamondAttractorVfx {
    private static final ResourceLocation AURA = ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID,
            "effeks/diamond_attractor/aurora01.png");
    private static final Map<Integer, Cast> ACTIVE = new LinkedHashMap<>();
    private static ClientLevel currentLevel;
    private static final class Cast {
        final long start;
        Entity entity;
        Cast(long start) { this.start = start; }
    }
    private DiamondAttractorVfx() {}

    public static void start(Level level, int id) {
        if (!(level instanceof ClientLevel client)) return;
        reset(client);
        if (ACTIVE.size() >= 32) ACTIVE.remove(ACTIVE.keySet().iterator().next());
        ACTIVE.put(id, new Cast(level.getGameTime()));
    }

    private static void reset(ClientLevel level) {
        if (currentLevel != level) { ACTIVE.clear(); currentLevel = level; }
    }
    @SubscribeEvent public static void logout(ClientPlayerNetworkEvent.LoggingOut event) { reset(null); }
    @SubscribeEvent public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        reset(Minecraft.getInstance().level);
        if (currentLevel == null) return;
        ACTIVE.entrySet().removeIf(entry -> {
            Cast cast = entry.getValue();
            if (cast.entity == null) cast.entity = currentLevel.getEntity(entry.getKey());
            return currentLevel.getGameTime() - cast.start >= 32
                    || cast.entity != null && (cast.entity.isRemoved() || !cast.entity.isAlive());
        });
    }

    @SubscribeEvent public static void render(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES || currentLevel == null
                || currentLevel != Minecraft.getInstance().level || ACTIVE.isEmpty()) return;
        var pose = event.getPoseStack();
        Vec3 camera = event.getCamera().getPosition();
        var buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        var type = AnimatedVfxRenderTypes.aura(AURA);
        VertexConsumer out = buffers.getBuffer(type);
        pose.pushPose();
        pose.translate(-camera.x, -camera.y, -camera.z);
        Matrix4f matrix = pose.last().pose();
        for (Cast cast : ACTIVE.values()) {
            Entity owner = cast.entity;
            if (owner == null || owner.isRemoved() || owner.distanceToSqr(camera) > 96 * 96) continue;
            float age = (float) (currentLevel.getGameTime() - cast.start) + event.getPartialTick();
            float strength = Mth.clamp(age / 4, 0, 1) * Mth.clamp((32 - age) / 9, 0, 1);
            Vec3 base = owner.getPosition(event.getPartialTick());
            boolean firstPerson = owner == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson();
            // Closed circles; only the texture scrolls and radius pulses. No helical height progression.
            Vec3 bodySide = new Vec3(camera.z - base.z, 0, base.x - camera.x).normalize();
            if (bodySide.lengthSqr() < .001) bodySide = new Vec3(1, 0, 0);
            if (!firstPerson) {
                Vec3 center = base.add(0, owner.getBbHeight() * .52, 0);
                float radius = Math.max(.8F, owner.getBbHeight() * .62F);
                ring(out, matrix, center, bodySide, new Vec3(0, 1, 0), radius, .13F, age, strength * .9F);
            }
            Vec3 sword = CommonUtil.getVanillaSwordOrBodyPosition(owner, event.getPartialTick());
            Vec3 forward = owner.getLookAngle().normalize();
            Vec3 side = new Vec3(-forward.z, 0, forward.x).normalize();
            if (side.lengthSqr() < .001) side = new Vec3(1, 0, 0);
            Vec3 up = forward.cross(side).normalize();
            ring(out, matrix, sword, side, up, .48F, .085F, age, strength);
        }
        buffers.endBatch(type);
        pose.popPose();
    }

    private static void ring(VertexConsumer out, Matrix4f pose, Vec3 center, Vec3 axisX, Vec3 axisY,
                             float radius, float width, float age, float alpha) {
        int segments = 64;
        double pulse = 1 + .025 * Math.sin(age * .18);
        for (int i = 0; i < segments; i++) {
            double angle0 = i * Math.PI * 2 / segments;
            double angle1 = (i + 1) * Math.PI * 2 / segments;
            Vec3 radial0 = axisX.scale(Math.cos(angle0)).add(axisY.scale(Math.sin(angle0)));
            Vec3 radial1 = axisX.scale(Math.cos(angle1)).add(axisY.scale(Math.sin(angle1)));
            Vec3 a = center.add(radial0.scale(radius * pulse));
            Vec3 b = center.add(radial1.scale(radius * pulse));
            Vec3 side = radial0.add(radial1).normalize().scale(width);
            // Each segment spans a quarter texture vertically; sixteen repeats close the texture seam.
            float v = age * .025F + i * .25F;
            AnimatedVfxGeometry.ribbon(out, pose, a, b, side.scale(2.2), 0, v, 1, .9F, .08F, alpha * .22F);
            AnimatedVfxGeometry.ribbon(out, pose, a, b, side, 0, v, 1, .94F, .12F, alpha);
        }
    }
}
