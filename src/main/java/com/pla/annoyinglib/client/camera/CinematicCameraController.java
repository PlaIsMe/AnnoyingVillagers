package com.pla.annoyinglib.client.camera;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.network.ClientboundCinematicCamera;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import java.util.UUID;

/** Short, cancellable local shot; camera options and player position/rotation are never changed. */
@Mod.EventBusSubscriber(modid = AnnoyingLib.MODID, value = Dist.CLIENT)
public final class CinematicCameraController {
    private static CinematicScreen current;

    private CinematicCameraController() {}

    public record CameraPose(Vec3 position, float yaw, float pitch) {}

    public static void start(ClientboundCinematicCamera message) {
        start(message, false, Vec3.ZERO, false);
    }

    /** Opt-in for scripted transformations that place the viewer in spectator mode. */
    public static void startIncludingSpectators(ClientboundCinematicCamera message) {
        start(message, false, Vec3.ZERO, true);
    }

    /** Two local shots across a player teleport. A skipped departure also skips the arrival. */
    public static void startTeleport(ClientboundCinematicCamera message, boolean arrival) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || !client.player.getUUID().equals(message.subjectUuid())) return;
        Vec3 offset = Vec3.ZERO;
        if (arrival) {
            if (!active() || !current.selfTeleport || !current.request.subjectUuid().equals(message.subjectUuid())) return;
            offset = message.focus().subtract(current.request.focus());
            cancel();
        }
        start(message, true, offset, false);
    }

    private static void start(ClientboundCinematicCamera message, boolean selfTeleport, Vec3 cameraOffset, boolean allowSpectator) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null || client.screen != null
                || !client.level.dimension().location().equals(message.dimension())
                || !Double.isFinite(message.focus().x) || !Double.isFinite(message.focus().y)
                || !Double.isFinite(message.focus().z) || !Float.isFinite(message.facingYaw())
                || client.player.distanceToSqr(message.focus()) > 64 * 64
                || (!selfTeleport && !client.level.hasChunkAt(BlockPos.containing(message.focus())))
                || !client.player.isAlive() || !allowSpectator && client.player.isSpectator() || client.player.hurtTime > 0) return;
        CinematicScreen screen = new CinematicScreen(message, selfTeleport, cameraOffset, allowSpectator);
        current = screen;
        client.setScreen(screen);
    }

    public static boolean active() {
        Minecraft client = Minecraft.getInstance();
        return current != null && client.screen == current && client.level == current.level && client.player == current.player;
    }

    public static void cancel() {
        CinematicScreen screen = current;
        current = null;
        Minecraft client = Minecraft.getInstance();
        if (screen != null && client.screen == screen) client.setScreen(null);
    }

    public static void cancelForSubject(UUID subject) {
        if (current != null && current.request.subjectUuid().equals(subject)) cancel();
    }

    @Nullable
    public static CameraPose camera(Vec3 vanillaPosition, float vanillaYaw, float vanillaPitch) {
        return active() ? current.cameraPose(vanillaPosition, vanillaYaw, vanillaPitch) : null;
    }

    @SubscribeEvent
    public static void logout(ClientPlayerNetworkEvent.LoggingOut event) { cancel(); }

    @SubscribeEvent
    public static void hud(RenderGuiEvent.Pre event) { if (active()) event.setCanceled(true); }

    @SubscribeEvent
    public static void hand(RenderHandEvent event) { if (active()) event.setCanceled(true); }

    @SubscribeEvent
    public static void outline(RenderHighlightEvent.Block event) { if (active()) event.setCanceled(true); }

    @SubscribeEvent
    public static void fov(ViewportEvent.ComputeFov event) {
        if (active() && event.usedConfiguredFov()) event.setFOV(event.getFOV() * Mth.lerp(current.weight(), 1.0D, 0.78D));
    }

    private static float smooth(double value) {
        float t = (float) Mth.clamp(value, 0.0D, 1.0D);
        return t * t * (3.0F - 2.0F * t);
    }

    private static final class CinematicScreen extends Screen {
        private final ClientboundCinematicCamera request;
        private final ClientLevel level;
        private final LocalPlayer player;
        private final Vec3 initialPosition;
        private final float initialYaw;
        private final float initialPitch;
        private final long started = Util.getMillis();
        private final long duration;
        private final long approach;
        private final boolean selfTeleport;
        private final boolean allowSpectator;
        private boolean sawSubject;

        private CinematicScreen(ClientboundCinematicCamera request, boolean selfTeleport, Vec3 cameraOffset, boolean allowSpectator) {
            super(Component.translatable("screen.annoyinglib.cinematic"));
            this.request = request;
            this.selfTeleport = selfTeleport;
            this.allowSpectator = allowSpectator;
            Minecraft client = Minecraft.getInstance();
            level = client.level;
            player = client.player;
            var camera = client.gameRenderer.getMainCamera();
            Vec3 start = camera.getPosition().add(cameraOffset);
            initialPosition = selfTeleport ? new Vec3(start.x, Math.max(start.y, request.focus().y), start.z) : start;
            initialYaw = camera.getYRot();
            initialPitch = camera.getXRot();
            duration = Mth.clamp(request.durationTicks(), 30, 200) * 50L;
            approach = Math.max(50L, Math.min(request.approachTicks() * 50L, duration / 2));
        }

        private long elapsed() { return Math.max(0, Util.getMillis() - started); }

        private float entering() { return smooth(elapsed() / (double) approach); }

        private float returning() { return smooth((elapsed() - duration * 0.76D) / (duration * 0.24D)); }

        private float weight() { return entering() * (1.0F - returning()); }

        @Override
        public void tick() {
            if (Minecraft.getInstance().level != level || Minecraft.getInstance().player != player
                    || !player.isAlive() || !allowSpectator && player.isSpectator() || player.hurtTime > 0
                    || player.distanceToSqr(request.focus()) > 72 * 72
                    || (!level.hasChunkAt(BlockPos.containing(request.focus())) && (!selfTeleport || elapsed() > 1500L))
                    || elapsed() >= duration) {
                cancel();
                return;
            }
            // The focus packet may precede entity tracking by a tick. Never follow a reused entity ID.
            Entity subject = level.getEntity(request.subjectId());
            if (subject != null) {
                if (!request.subjectUuid().equals(subject.getUUID()) || !subject.isAlive()) { cancel(); return; }
                sawSubject = true;
            } else if (sawSubject || elapsed() > Math.max(1500L, approach + 1500L)) cancel();
        }

        private CameraPose cameraPose(Vec3 vanillaPosition, float vanillaYaw, float vanillaPitch) {
            double yaw = Math.toRadians(request.facingYaw());
            Vec3 forward = new Vec3(-Math.sin(yaw), 0, Math.cos(yaw));
            Vec3 side = new Vec3(forward.z, 0, -forward.x);
            float drift = smooth((elapsed() - approach) / (duration * 0.76D - approach));
            Vec3 shot = request.focus().add(forward.scale(Mth.lerp(drift, 8.5D, 6.0D)))
                    .add(side.scale(Mth.lerp(drift, -0.8D, 0.8D))).add(0, 0.9D, 0);
            shot = clip(request.focus(), shot);
            Vec3 position = initialPosition.lerp(shot, entering()).lerp(vanillaPosition, returning());
            // A portal transition puts the player's eyes below solid ground. Keep the shot
            // anchored above its portal instead of clipping the camera into that ground.
            position = clip(selfTeleport ? request.focus() : player.getEyePosition(), position);
            Vec3 look = request.focus().subtract(position);
            float targetYaw = (float) (Math.toDegrees(Math.atan2(look.z, look.x)) - 90.0D);
            float targetPitch = (float) -Math.toDegrees(Math.atan2(look.y, look.horizontalDistance()));
            float shotYaw = initialYaw + Mth.wrapDegrees(targetYaw - initialYaw) * entering();
            float shotPitch = Mth.lerp(entering(), initialPitch, targetPitch);
            return new CameraPose(position, shotYaw + Mth.wrapDegrees(vanillaYaw - shotYaw) * returning(),
                    Mth.lerp(returning(), shotPitch, vanillaPitch));
        }

        /** Eight rays preserve a small near-plane volume instead of letting the camera enter a wall. */
        private Vec3 clip(Vec3 anchor, Vec3 desired) {
            Vec3 ray = desired.subtract(anchor);
            double length = ray.length();
            if (length < 0.001D) return anchor;
            double allowed = length;
            for (int corner = 0; corner < 8; corner++) {
                Vec3 offset = new Vec3((corner & 1) == 0 ? -0.15D : 0.15D,
                        (corner & 2) == 0 ? -0.15D : 0.15D, (corner & 4) == 0 ? -0.15D : 0.15D);
                var hit = level.clip(new ClipContext(anchor.add(offset), desired.add(offset),
                        ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, player));
                if (hit.getType() != HitResult.Type.MISS)
                    allowed = Math.min(allowed, Math.max(0.0D, hit.getLocation().distanceTo(anchor.add(offset)) - 0.2D));
            }
            return anchor.add(ray.scale(allowed / length));
        }

        @Override public boolean keyPressed(int key, int scan, int modifiers) {
            if (key == GLFW.GLFW_KEY_ESCAPE || key == GLFW.GLFW_KEY_SPACE || key == GLFW.GLFW_KEY_ENTER) {
                cancel();
                return true;
            }
            return super.keyPressed(key, scan, modifiers);
        }

        @Override public void onClose() { cancel(); }
        @Override public void removed() { if (current == this) current = null; }
        @Override public boolean isPauseScreen() { return false; }

        @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
            int bar = Math.round(height * 0.085F * weight());
            graphics.fill(0, 0, width, bar, 0xFF08070D);
            graphics.fill(0, height - bar, width, height, 0xFF08070D);
            graphics.drawCenteredString(font, Component.translatable("screen.annoyinglib.cinematic.controls"),
                    width / 2, height - 41, 0xD6CEE5);
            super.render(graphics, mouseX, mouseY, partialTick);
        }
    }
}
