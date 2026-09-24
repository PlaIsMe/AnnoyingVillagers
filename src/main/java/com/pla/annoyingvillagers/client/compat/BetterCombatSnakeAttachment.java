package com.pla.annoyingvillagers.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.renderer.SnakeBladeRenderer;
import com.pla.annoyingvillagers.entity.SnakeBladeEntity;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.mixin.client.SnakeAttachmentGameRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Client-only sockets from the actual held-item pose, including animated arm bends. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class BetterCombatSnakeAttachment {
    private static final Map<LivingEntity, Vec3> SOCKETS = new HashMap<>();
    private static final List<PendingSnake> PENDING = new ArrayList<>();
    private static final Deque<ItemCapture> ITEM_CAPTURES = new ArrayDeque<>();
    private static final Matrix4f WORLD_PROJECTION = new Matrix4f();
    private static final Matrix4f INVERSE_VIEW = new Matrix4f();
    private static final Projection HAND_PROJECTION = new Projection();
    private static boolean drawingSnakes;
    private static boolean samplingHand;
    private static Vec3 cameraPosition = Vec3.ZERO;

    private BetterCombatSnakeAttachment() {}

    public static void beginFrame(Matrix4f view, Matrix4f projection, Vec3 camera) {
        SOCKETS.clear();
        PENDING.clear();
        ITEM_CAPTURES.clear();
        INVERSE_VIEW.set(view).invert();
        WORLD_PROJECTION.set(projection);
        cameraPosition = camera;
    }

    /**
     * Scopes the entity and stack around a 26.1 item-feature submission. The actual
     * socket is sampled later, after the model's display transform has been applied.
     */
    public static void beginItem(LivingEntity owner, ItemStack stack, ItemDisplayContext context) {
        ITEM_CAPTURES.push(new ItemCapture(owner, stack, context));
    }

    public static void endItem() {
        if (!ITEM_CAPTURES.isEmpty()) ITEM_CAPTURES.pop();
    }

    public static void captureTransformed(PoseStack pose) {
        ItemCapture capture = ITEM_CAPTURES.peek();
        if (capture == null) return;
        LivingEntity itemOwner = capture.owner;
        ItemStack stack = capture.stack;
        ItemDisplayContext context = capture.context;
        if (itemOwner == null
                || !(stack.getItem() instanceof DemoniacVoltageReaverItem)
                || !ItemStack.isSameItemSameComponents(stack, itemOwner.getMainHandItem())
                || !DemoniacVoltageReaverItem.hasSnakeAnimation(stack)) return;
        if (context != ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                && context != ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                && !context.firstPerson()) return;
        boolean leftHand = context == ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                || context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        if (leftHand != (itemOwner.getMainArm() == HumanoidArm.LEFT)) return;
        // Display context describes the MODEL, not the render pass. Punchy uses
        // THIRD_PERSON transforms for its first-person hand. Only our sampling
        // pass may publish that hand's socket; the later HUD pass must not replace it.
        if (!samplingHand && (context.firstPerson() || PunchyItemRenderContext.isActive())) return;

        // End of the retained blade in custom/demoniac_voltage_reaver_snake.json.
        // Its tip cubes are rotated -45 degrees about Z around (-2, 7, 2).
        float diagonal = Mth.SQRT_OF_TWO * 0.5F;
        Vector3f socket = new Vector3f((-2.0F + 21.0F * diagonal) / 16.0F,
                (7.0F + 5.0F * diagonal) / 16.0F, 8.0F / 16.0F);
        pose.last().pose().transformPosition(socket);
        if (samplingHand) {
            // The sampling PoseStack starts in view space, even when Punchy
            // selected a THIRD_PERSON model. Match GameRenderer's HUD projection,
            // including the graphics backend's depth convention.
            Minecraft mc = Minecraft.getInstance();
            var renderState = mc.gameRenderer.getGameRenderState();
            HAND_PROJECTION.setupPerspective(0.05F, 100.0F,
                    renderState.levelRenderState.cameraRenderState.hudFov,
                    renderState.windowRenderState.width, renderState.windowRenderState.height);
            Matrix4f handProjection = HAND_PROJECTION.getMatrix(new Matrix4f());
            new Matrix4f(WORLD_PROJECTION).invert().mul(handProjection).transformProject(socket);
            // First-person items are rendered in view space. Third-person
            // entity items are already world-oriented in their local PoseStack.
            INVERSE_VIEW.transformPosition(socket);
        }
        SOCKETS.put(itemOwner, cameraPosition.add(socket.x, socket.y, socket.z));
    }

    public static Vec3 getToolTipPos(LivingEntity entity) {
        return SOCKETS.get(entity);
    }

    public static boolean defer(SnakeBladeRenderer renderer, SnakeBladeEntity snake, float yaw,
                                float partialTick, int light) {
        if (drawingSnakes || !(snake.getRenderFromEntity() instanceof LivingEntity)) return false;
        PENDING.add(new PendingSnake(renderer, snake, yaw, partialTick, light));
        return true;
    }

    @SubscribeEvent
    public static void afterEntities(SubmitCustomGeometryEvent event) {
        if (PENDING.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        float partialTick = mc.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        // Prefer the visible hand over any world-player socket. If Better Combat
        // owns first person and suppresses the hand pass, the world socket remains.
        if (mc.player != null && mc.options.getCameraType().isFirstPerson()
                && !mc.options.hideGui
                && !mc.player.isSpectator() && !mc.player.isSleeping()
                && PENDING.stream().anyMatch(p -> p.snake.getRenderFromEntity() == mc.player)) {
            PoseStack handPose = new PoseStack();
            var accessor = (SnakeAttachmentGameRendererAccessor) mc.gameRenderer;
            var cameraState = mc.gameRenderer.getGameRenderState()
                    .levelRenderState.cameraRenderState;
            accessor.av$bobHurt(cameraState, handPose);
            if (mc.options.bobView().get()) accessor.av$bobView(cameraState, handPose);
            samplingHand = true;
            try {
                mc.gameRenderer.itemInHandRenderer.renderHandsWithItems(partialTick, handPose,
                        new SubmitNodeStorage(), mc.player,
                        mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTick));
            } finally {
                samplingHand = false;
            }
        }
        // Entity order is arbitrary: wait until every player's held-item layer has run.
        drawingSnakes = true;
        PoseStack pose = event.getPoseStack();
        try {
            for (PendingSnake pending : PENDING) {
                SnakeBladeEntity snake = pending.snake;
                pose.pushPose();
                try {
                    pose.translate(Mth.lerp(pending.partialTick, snake.xo, snake.getX()) - cameraPosition.x,
                            Mth.lerp(pending.partialTick, snake.yo, snake.getY()) - cameraPosition.y,
                            Mth.lerp(pending.partialTick, snake.zo, snake.getZ()) - cameraPosition.z);
                    pending.renderer.submitGeometry(snake, pending.partialTick, pose,
                            event.getSubmitNodeCollector());
                } finally {
                    pose.popPose();
                }
            }
        } finally {
            drawingSnakes = false;
            PENDING.clear();
        }
    }

    private record PendingSnake(SnakeBladeRenderer renderer, SnakeBladeEntity snake, float yaw,
                                float partialTick, int light) {}

    private record ItemCapture(LivingEntity owner, ItemStack stack, ItemDisplayContext context) {}

}
