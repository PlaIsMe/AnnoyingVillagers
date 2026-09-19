package com.pla.annoyingvillagers.client.trail;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.RigClientAnimationState;
import com.pla.annoyingvillagers.client.animation.SpecialClientAnimationState;
import com.pla.annoyingvillagers.client.renderer.RigItemVisualResolver;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.AvGolem;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.rig.RigAnimationSpec;
import com.pla.annoyingvillagers.rig.RigAnimationSpecs;
import com.pla.annoyingvillagers.rig.RigAttackWindow;
import com.pla.annoyingvillagers.rig.RigCollider;
import com.pla.annoyingvillagers.rig.RigColliderAnchor;
import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import com.pla.annoyingvillagers.rig.pose.RigPoseSampler;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationFamily;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationId;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationSpec;
import com.pla.annoyingvillagers.specialanimation.SpecialAnimationSpecs;
import com.pla.annoyingvillagers.specialanimation.SpecialAttackWindow;
import com.pla.annoyingvillagers.specialanimation.SpecialCollider;
import com.pla.annoyingvillagers.specialanimation.pose.SpecialPoseSampler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class RigSwordTrailManager {
    private static final ResourceLocation SOLID_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "textures/particle/swing_trail.png");
    private static final Map<TrailKey, TrailState> STATES = new HashMap<>();

    // A normal rig movement should be much smaller than this in one client tick. A moveTo/
    // teleport that crosses this threshold resets history instead of connecting old/new space.
    private static final double MAX_CONTINUOUS_MOVE_SQR = 1.75D * 1.75D;
    private static final double NULL_WEAPON_MAX_CONTINUOUS_MOVE_SQR = 0.75D * 0.75D;

    private static final float BODY_ALPHA = 1.0F;
    private static final float[] GLOW_SCALES = {1.08F, 1.20F, 1.38F};
    private static final float[] GLOW_ALPHAS = {0.50F, 0.22F, 0.08F};

    private RigSwordTrailManager() {}

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            STATES.clear();
            return;
        }

        for (TrailState state : STATES.values()) state.ageOneTick();
        Set<TrailKey> touched = new java.util.HashSet<>();

        for (var entry : RigClientAnimationState.snapshot().entrySet()) {
            Entity entity = mc.level.getEntity(entry.getKey());
            if (!(entity instanceof Mob mob) || !mob.isAlive() || mob.isRemoved()) continue;
            RigClientAnimationState.Active active = RigClientAnimationState.getActive(mob, mob.tickCount);
            if (active == null || !active.hasTrailWindow()) continue;

            float elapsed = active.elapsedTicks(mob.tickCount);
            if (elapsed >= active.trailEndTickExclusive()) continue;

            RigAnimationSpec spec = RigAnimationSpecs.get(active.animationId());
            EnumSet<HumanoidArm> arms = emittingArms(mob, spec, active.animationId());
            for (HumanoidArm arm : arms) {
                ItemStack original = stackForArm(mob, arm);
                if (original.isEmpty() || RigClientAnimationState.isToolHidden(mob, arm)) continue;

                ItemStack visual = RigItemVisualResolver.resolve(mob, original, arm == HumanoidArm.LEFT);
                if (visual.isEmpty()) continue;
                RigSwordTrailDefinition definition = RigSwordTrailReloadListener.INSTANCE.get(visual);
                ResourceLocation itemId = RigSwordTrailReloadListener.INSTANCE.getItemId(visual);
                if (definition == null) {
                    definition = RigSwordTrailReloadListener.INSTANCE.get(original);
                    itemId = RigSwordTrailReloadListener.INSTANCE.getItemId(original);
                }
                if (definition == null || itemId == null) continue;

                if (mob instanceof AngrySteveEntity angrySteve
                        && angrySteve.isLegendaryAwakened()
                        && arm == mob.getMainArm()
                        && original.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())) {
                    definition = definition.withColor(253, 255, 118);
                }

                TrailKey key = new TrailKey(mob.getId(), arm, 0);
                TrailState state = STATES.computeIfAbsent(key, ignored -> new TrailState());
                state.update(mob, new RigTrailPlayback(active, elapsed), arm, definition, itemId);
                touched.add(key);
            }
        }

        for (var entry : SpecialClientAnimationState.snapshot().entrySet()) {
            Entity entity = mc.level.getEntity(entry.getKey());
            if (!(entity instanceof AvGolem golem) || !golem.isAlive() || golem.isRemoved()) continue;
            SpecialClientAnimationState.Active active = SpecialClientAnimationState.getActive(golem, golem.tickCount);
            if (active == null || active.animationId().family() != SpecialAnimationFamily.AV_GOLEM) continue;

            float elapsed = active.elapsedTicks(golem.tickCount);
            SpecialAnimationSpec spec = SpecialAnimationSpecs.get(active.animationId());
            for (SpecialTrailWindow trailWindow : specialTrailWindows(spec)) {
                if (elapsed < Math.max(0, trailWindow.startTickInclusive() - 1) || elapsed >= trailWindow.endTickExclusive()) continue;

                HumanoidArm arm = trailWindow.arm();
                ItemStack stack = stackForArm(golem, arm);
                if (stack.isEmpty()) continue;
                RigSwordTrailDefinition definition = RigSwordTrailReloadListener.INSTANCE.get(stack);
                ResourceLocation itemId = RigSwordTrailReloadListener.INSTANCE.getItemId(stack);
                if (definition == null || itemId == null) continue;

                TrailKey key = new TrailKey(golem.getId(), arm, trailWindow.windowIndex() + 1);
                TrailState state = STATES.computeIfAbsent(key, ignored -> new TrailState());
                state.update(golem, new SpecialTrailPlayback(active.animationId(), active.startedAtTick(), elapsed,
                        trailWindow.startTickInclusive(), trailWindow.endTickExclusive()), arm, definition, itemId);
                touched.add(key);
            }
        }

        for (var entry : STATES.entrySet()) {
            if (!touched.contains(entry.getKey())) entry.getValue().beginRemoval();
        }
        STATES.entrySet().removeIf(entry -> entry.getValue().finished());
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES || STATES.isEmpty()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 camera = event.getCamera().getPosition();
        poseStack.pushPose();
        poseStack.translate(-camera.x, -camera.y, -camera.z);
        Matrix4f matrix = poseStack.last().pose();

        try {
            // Solid body first. Every glow pass below reads the exact same TrailEdge list.
            drawBatch(matrix, event.getPartialTick(), false, 1.0F, BODY_ALPHA);
            for (int i = 0; i < GLOW_SCALES.length; i++) {
                drawBatch(matrix, event.getPartialTick(), true, GLOW_SCALES[i], GLOW_ALPHAS[i]);
            }
        } finally {
            restoreRenderState();
            poseStack.popPose();
        }
    }

    private static void drawBatch(Matrix4f matrix, float partialTick, boolean glow, float widthScale, float passAlpha) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        // This renderer runs during the level render stage, outside Minecraft's
        // particle batch. The particle shader samples a lightmap texture which
        // is not guaranteed to be bound here, turning every vertex tint black.
        // The trail is intentionally full-bright, so render its texture and the
        // JSON-defined vertex color directly without a lightmap dependency.
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, SOLID_TEXTURE);

        if (glow) {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        } else {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        for (TrailState state : STATES.values()) {
            if (state.edges.size() < 2 || state.definition == null) continue;
            if (glow && state.definition.isPureBlack()) continue;
            emitState(builder, matrix, state, partialTick, widthScale, passAlpha);
        }
        finish(builder);

        if (glow) {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            for (TrailState state : STATES.values()) {
                if (state.edges.size() < 2 || state.definition == null || !state.definition.isPureBlack()) continue;
                emitState(builder, matrix, state, partialTick, widthScale, passAlpha);
            }
            finish(builder);
        }
    }

    private static boolean emitState(BufferBuilder builder, Matrix4f matrix, TrailState state,
                                     float partialTick, float widthScale, float passAlpha) {
        int edgeCount = state.edges.size() - 1;
        if (edgeCount < 1) return false;

        int interpolateCount = state.definition.interpolations();
        boolean startFade = state.edges.getFirst().lifetime == 1;
        boolean endFade = state.edges.getLast().lifetime >= state.definition.lifetimeTicks();
        float startEdge = (startFade ? interpolateCount * 2.0F * partialTick : 0.0F) + state.startEdgeCorrection;
        float endEdge = endFade
                ? Math.min(edgeCount - (interpolateCount * 2.0F) * (1.0F - partialTick), edgeCount - 1.0F)
                : edgeCount - 1.0F;

        // Very short first/last frames can have no complete quad yet, exactly as a particle trail.
        startEdge = Mth.clamp(startEdge, 0.0F, Math.max(0.0F, edgeCount - 1.0F));
        endEdge = Mth.clamp(endEdge, 0.0F, Math.max(0.0F, edgeCount - 1.0F));
        if (endEdge <= startEdge + 1.0E-4F) return false;

        float interval = 1.0F / (endEdge - startEdge);
        float from = -interval * (startEdge % 1.0F);
        float to = from + interval;
        float stoppingFade = state.removing
                ? Mth.clamp((state.removeTicksRemaining + (1.0F - partialTick)) / Math.max(1.0F, state.definition.lifetimeTicks()), 0.0F, 1.0F)
                : 1.0F;

        int beginIndex = Mth.floor(startEdge);
        int endIndex = Math.min(Mth.floor(endEdge), state.edges.size() - 2);
        boolean wrote = false;
        for (int i = beginIndex; i <= endIndex; i++) {
            TrailEdge e1 = state.edges.get(i);
            TrailEdge e2 = state.edges.get(i + 1);
            EdgePair p1 = scaled(e1, widthScale);
            EdgePair p2 = scaled(e2, widthScale);

            float alphaFrom = Mth.clamp(from, 0.0F, 1.0F) * stoppingFade * passAlpha;
            float alphaTo = Mth.clamp(to, 0.0F, 1.0F) * stoppingFade * passAlpha;
            vertex(builder, matrix, p1.start, from, 1.0F, state.definition, alphaFrom);
            vertex(builder, matrix, p1.end, from, 0.0F, state.definition, alphaFrom);
            vertex(builder, matrix, p2.end, to, 0.0F, state.definition, alphaTo);
            vertex(builder, matrix, p2.start, to, 1.0F, state.definition, alphaTo);
            from += interval;
            to += interval;
            wrote = true;
        }
        return wrote;
    }

    private static void vertex(BufferBuilder builder, Matrix4f matrix, Vec3 p, float u, float v,
                               RigSwordTrailDefinition definition, float alpha) {
        builder.vertex(matrix, (float) p.x, (float) p.y, (float) p.z)
                .uv(u, v)
                .color(definition.redF(), definition.greenF(), definition.blueF(), alpha)
                .endVertex();
    }

    private static EdgePair scaled(TrailEdge edge, float scale) {
        if (Math.abs(scale - 1.0F) < 1.0E-4F) return new EdgePair(edge.start, edge.end);
        Vec3 center = edge.start.add(edge.end).scale(0.5D);
        return new EdgePair(center.add(edge.start.subtract(center).scale(scale)),
                center.add(edge.end.subtract(center).scale(scale)));
    }


    private static void finish(BufferBuilder builder) {
        BufferBuilder.RenderedBuffer rendered = builder.endOrDiscardIfEmpty();
        if (rendered != null) BufferUploader.drawWithShader(rendered);
    }

    private static void restoreRenderState() {
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static EnumSet<HumanoidArm> emittingArms(Mob mob, RigAnimationSpec spec, RigAnimationId animationId) {
        if (animationId == RigAnimationId.SPINNING_WEAPON) return EnumSet.of(mob.getMainArm());
        if (!spec.damagesTarget()) return EnumSet.noneOf(HumanoidArm.class);

        EnumSet<HumanoidArm> result = EnumSet.noneOf(HumanoidArm.class);
        for (RigAttackWindow window : spec.attackWindows()) {
            for (RigCollider collider : window.colliders()) {
                if (collider.anchor() == RigColliderAnchor.RIGHT_TOOL) result.add(HumanoidArm.RIGHT);
                if (collider.anchor() == RigColliderAnchor.LEFT_TOOL) result.add(HumanoidArm.LEFT);
            }
        }
        // Some older attack profiles use a body/hand collider while still visually swinging the
        // held weapon. Keep those on the main arm rather than silently losing their trail.
        if (result.isEmpty()) result.add(mob.getMainArm());
        return result;
    }

    private static List<SpecialTrailWindow> specialTrailWindows(SpecialAnimationSpec spec) {
        List<SpecialTrailWindow> result = new ArrayList<>();
        SpecialAttackWindow[] windows = spec.attackWindows();
        for (int i = 0; i < windows.length; i++) {
            SpecialAttackWindow window = windows[i];
            EnumSet<HumanoidArm> arms = EnumSet.noneOf(HumanoidArm.class);
            for (SpecialCollider collider : window.colliders()) {
                if ("Tool_R".equals(collider.boneName())) arms.add(HumanoidArm.RIGHT);
                if ("Tool_L".equals(collider.boneName())) arms.add(HumanoidArm.LEFT);
            }
            for (HumanoidArm arm : arms) {
                result.add(new SpecialTrailWindow(i, arm, window.startTickInclusive(), window.endTickExclusive()));
            }
        }
        return result;
    }

    private static ItemStack stackForArm(Mob mob, HumanoidArm arm) {
        return arm == mob.getMainArm() ? mob.getMainHandItem() : mob.getOffhandItem();
    }

    private record TrailKey(int entityId, HumanoidArm arm, int track) {}
    private record SpecialTrailWindow(int windowIndex, HumanoidArm arm, int startTickInclusive, int endTickExclusive) {}
    private record SpecialTrailIdentity(SpecialAnimationId animationId, int startedAtTick, float trailStartTick, float trailEndTickExclusive) {}
    private record EdgePair(Vec3 start, Vec3 end) {}

    private interface TrailPlayback {
        Object identity();
        float elapsedTicks();
        float trailStartTick();
        float trailEndTickExclusive();
        TrailEdge sample(Mob mob, HumanoidArm arm, float elapsed, float entityPartial, RigSwordTrailDefinition definition, boolean forceCurrentPosition);
    }

    private record RigTrailPlayback(RigClientAnimationState.Active active, float elapsedTicks) implements TrailPlayback {
        @Override
        public Object identity() {
            return this.active.animationId();
        }

        @Override
        public float trailStartTick() {
            return this.active.trailStartTick();
        }

        @Override
        public float trailEndTickExclusive() {
            return this.active.trailEndTickExclusive();
        }

        @Override
        public TrailEdge sample(Mob mob, HumanoidArm arm, float elapsed, float entityPartial, RigSwordTrailDefinition definition, boolean forceCurrentPosition) {
            return sampleEdge(mob, this.active.animationId(), arm, elapsed, entityPartial, definition, forceCurrentPosition);
        }
    }

    private record SpecialTrailPlayback(SpecialAnimationId animationId, int startedAtTick, float elapsedTicks,
                                        float trailStartTick, float trailEndTickExclusive) implements TrailPlayback {
        @Override
        public Object identity() {
            return new SpecialTrailIdentity(this.animationId, this.startedAtTick, this.trailStartTick, this.trailEndTickExclusive);
        }

        @Override
        public TrailEdge sample(Mob mob, HumanoidArm arm, float elapsed, float entityPartial, RigSwordTrailDefinition definition, boolean forceCurrentPosition) {
            return sampleSpecialEdge(mob, this.animationId, arm, elapsed, entityPartial, definition, forceCurrentPosition);
        }
    }

    private static final class TrailState {
        private final LinkedList<TrailEdge> edges = new LinkedList<>();
        private final LinkedList<TrailEdge> invisibleEdges = new LinkedList<>();
        private RigSwordTrailDefinition definition;
        private ResourceLocation itemId;
        private Object animationIdentity;
        private Vec3 lastOwnerPosition;
        private float startEdgeCorrection;
        private boolean removing;
        private int removeTicksRemaining;

        private void ageOneTick() {
            this.edges.removeIf(edge -> !edge.isAlive());
            this.invisibleEdges.removeIf(edge -> !edge.isAlive());
            if (this.removing && this.removeTicksRemaining > 0) this.removeTicksRemaining--;
        }

        private void update(Mob mob, TrailPlayback playback, HumanoidArm arm,
                            RigSwordTrailDefinition newDefinition, ResourceLocation newItemId) {
            boolean identityChanged = !Objects.equals(this.animationIdentity, playback.identity())
                    || this.itemId == null || !this.itemId.equals(newItemId)
                    || this.definition == null;
            double maxContinuousMoveSqr = mob instanceof com.pla.annoyingvillagers.clazz.NullWeapon
                    ? NULL_WEAPON_MAX_CONTINUOUS_MOVE_SQR : MAX_CONTINUOUS_MOVE_SQR;
            boolean teleported = this.lastOwnerPosition != null
                    && this.lastOwnerPosition.distanceToSqr(mob.position()) > maxContinuousMoveSqr;

            if (identityChanged) {
                this.reset();
                this.animationIdentity = playback.identity();
                this.itemId = newItemId;
                this.definition = newDefinition;
                this.initializeInvisible(mob, playback, arm, false);
            } else {
                this.definition = newDefinition;
            }

            if (teleported) {
                // Never bridge the ribbon between pre-moveTo and post-moveTo positions.
                this.edges.clear();
                this.invisibleEdges.clear();
                this.startEdgeCorrection = 0.0F;
                this.initializeInvisible(mob, playback, arm, true);
            }

            this.lastOwnerPosition = mob.position();
            this.removing = false;
            this.removeTicksRemaining = this.definition.lifetimeTicks();

            float elapsed = playback.elapsedTicks();
            if (elapsed < playback.trailStartTick()) {
                this.updateInvisible(mob, playback, arm, elapsed, teleported);
                return;
            }
            if (elapsed >= playback.trailEndTickExclusive()) {
                this.beginRemoval();
                return;
            }

            this.createNextCurve(mob, playback, arm, elapsed, teleported);
        }

        private void initializeInvisible(Mob mob, TrailPlayback playback, HumanoidArm arm, boolean forceCurrentPosition) {
            float current = playback.elapsedTicks();
            float previous = Math.max(0.0F, current - 1.0F);
            float middle = (previous + current) * 0.5F;
            this.invisibleEdges.add(playback.sample(mob, arm, previous, 0.0F, this.definition, forceCurrentPosition));
            this.invisibleEdges.add(playback.sample(mob, arm, middle, 0.5F, this.definition, forceCurrentPosition));
            this.invisibleEdges.add(playback.sample(mob, arm, current, 1.0F, this.definition, forceCurrentPosition));
        }

        private void updateInvisible(Mob mob, TrailPlayback playback, HumanoidArm arm,
                                     float elapsed, boolean forceCurrentPosition) {
            float previous = Math.max(0.0F, elapsed - 1.0F);
            float middle = (previous + elapsed) * 0.5F;
            this.invisibleEdges.clear();
            this.invisibleEdges.add(playback.sample(mob, arm, previous, 0.0F, this.definition, forceCurrentPosition));
            this.invisibleEdges.add(playback.sample(mob, arm, middle, 0.5F, this.definition, forceCurrentPosition));
        }

        private void createNextCurve(Mob mob, TrailPlayback playback, HumanoidArm arm,
                                     float elapsed, boolean forceCurrentPosition) {
            boolean firstTrail = this.edges.isEmpty();
            // Trail windows arrive as exact server ticks, so there is no fractional start-time
            // correction to apply. The first curve grows in with the normal partial-tick head fade.
            this.startEdgeCorrection = 0.0F;

            float previous = Math.max(0.0F, elapsed - 1.0F);
            float middle = (previous + elapsed) * 0.5F;
            TrailEdge prevEdge = playback.sample(mob, arm, previous, 0.0F, this.definition, forceCurrentPosition);
            TrailEdge middleEdge = playback.sample(mob, arm, middle, 0.5F, this.definition, forceCurrentPosition);
            TrailEdge currentEdge = playback.sample(mob, arm, elapsed, 1.0F, this.definition, forceCurrentPosition);

            TrailEdge edge1;
            TrailEdge edge2;
            if (firstTrail) {
                edge1 = this.invisibleEdges.isEmpty() ? prevEdge : this.invisibleEdges.getLast();
                edge2 = prevEdge;
            } else {
                int back = this.definition.interpolations() / 2 + 1;
                int index = Math.max(0, this.edges.size() - back);
                edge1 = this.edges.get(index);
                edge2 = this.edges.getLast();
                edge2.lifetime++;
            }

            List<Vec3> starts = new ArrayList<>(4);
            List<Vec3> ends = new ArrayList<>(4);
            starts.add(edge1.start); ends.add(edge1.end);
            starts.add(edge2.start); ends.add(edge2.end);
            starts.add(middleEdge.start); ends.add(middleEdge.end);
            starts.add(currentEdge.start); ends.add(currentEdge.end);

            List<Vec3> finalStarts = RigSwordTrailBezier.interpolate(starts, 1, 3, this.definition.interpolations());
            List<Vec3> finalEnds = RigSwordTrailBezier.interpolate(ends, 1, 3, this.definition.interpolations());
            if (!firstTrail && !finalStarts.isEmpty()) {
                finalStarts.remove(0);
                finalEnds.remove(0);
            }
            int count = Math.min(finalStarts.size(), finalEnds.size());
            for (int i = 0; i < count; i++) {
                this.edges.add(new TrailEdge(finalStarts.get(i), finalEnds.get(i), this.definition.lifetimeTicks()));
            }
        }

        private void beginRemoval() {
            if (this.removing) return;
            this.removing = true;
            this.removeTicksRemaining = this.definition == null ? 0 : this.definition.lifetimeTicks();
            this.invisibleEdges.clear();
        }

        private boolean finished() {
            return this.removing && (this.removeTicksRemaining <= 0 || this.edges.isEmpty());
        }

        private void reset() {
            this.edges.clear();
            this.invisibleEdges.clear();
            this.startEdgeCorrection = 0.0F;
            this.animationIdentity = null;
            this.lastOwnerPosition = null;
            this.removing = false;
            this.removeTicksRemaining = 0;
        }
    }

    private static TrailEdge sampleEdge(Mob mob, RigAnimationId animationId, HumanoidArm arm,
                                        float elapsed, float entityPartial, RigSwordTrailDefinition definition,
                                        boolean forceCurrentPosition) {
        Vec3 ownerPosition = forceCurrentPosition ? mob.position() : mob.getPosition(entityPartial);
        float bodyYaw = forceCurrentPosition ? mob.yBodyRot : Mth.rotLerp(entityPartial, mob.yBodyRotO, mob.yBodyRot);
        RigColliderAnchor anchor = arm == HumanoidArm.RIGHT ? RigColliderAnchor.RIGHT_TOOL : RigColliderAnchor.LEFT_TOOL;
        RigPartTransform tool = RigPoseSampler.sample(animationId, elapsed, anchor, ownerPosition, bodyYaw);
        return new TrailEdge(tool.transformPoint(definition.beginPos()), tool.transformPoint(definition.endPos()), definition.lifetimeTicks());
    }

    private static TrailEdge sampleSpecialEdge(Mob mob, SpecialAnimationId animationId, HumanoidArm arm,
                                               float elapsed, float entityPartial, RigSwordTrailDefinition definition,
                                               boolean forceCurrentPosition) {
        Vec3 ownerPosition = forceCurrentPosition ? mob.position() : mob.getPosition(entityPartial);
        float bodyYaw = forceCurrentPosition ? mob.yBodyRot : Mth.rotLerp(entityPartial, mob.yBodyRotO, mob.yBodyRot);
        String boneName = arm == HumanoidArm.RIGHT ? "Tool_R" : "Tool_L";
        RigPartTransform tool = SpecialPoseSampler.sample(animationId, elapsed, boneName, ownerPosition, bodyYaw);
        return new TrailEdge(tool.transformPoint(definition.beginPos()), tool.transformPoint(definition.endPos()), definition.lifetimeTicks());
    }

    private static final class TrailEdge {
        private final Vec3 start;
        private final Vec3 end;
        private int lifetime;

        private TrailEdge(Vec3 start, Vec3 end, int lifetime) {
            this.start = start;
            this.end = end;
            this.lifetime = lifetime;
        }

        private boolean isAlive() {
            return --this.lifetime > 0;
        }
    }
}
