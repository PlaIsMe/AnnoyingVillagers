package com.pla.annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.client.animation.ObsidianArmorClientAnimationState;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondChestplate;
import com.pla.annoyingvillagers.client.model.ModelHerobrineObsidianDiamondHelmet;
import com.pla.annoyingvillagers.item.HerobrineObsidianArmorCharge;
import com.pla.annoyingvillagers.rig.RigColliderAnchor;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPart;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModList;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

/** Shared dynamic geometry for first person and renderers that bake the armor shell. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class ObsidianArmorExtensionRenderer {
    private static final Identifier CHESTPLATE_TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID,
            "textures/models/armor/herobrine_obsidian_armor_layer_1.png");
    private static final Identifier HELMET_TEXTURE = Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID,
            "textures/models/armor/herobrine_obsidian_armor_layer_2.png");
    private static ModelHerobrineObsidianDiamondChestplate<LivingEntity> chestplate;
    private static ModelHerobrineObsidianDiamondHelmet<LivingEntity> helmet;
    private static HumanoidModel<HumanoidRenderState> firstPersonPose;
    private static final HumanoidRenderState FIRST_PERSON_STATE = new HumanoidRenderState();
    private static FirstPersonBackend firstPersonBackend = (entity, stack, buffer, light, partial) -> false;

    private ObsidianArmorExtensionRenderer() {}

    @FunctionalInterface
    public interface FirstPersonBackend {
        /** Return true when the backend has rendered this wearer. */
        boolean render(LivingEntity wearer, PoseStack stack, MultiBufferSource buffer, int light, float partialTick);
    }

    public static void setFirstPersonBackend(FirstPersonBackend backend) {
        firstPersonBackend = backend;
    }

    @EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
    public static final class Models {
        @SubscribeEvent
        public static void onLayersLoaded(EntityRenderersEvent.AddLayers event) {
            chestplate = null;
            helmet = null;
            firstPersonPose = null;
        }
    }

    private static void ensureModels() {
        if (chestplate != null) return;
        var models = Minecraft.getInstance().getEntityModels();
        chestplate = new ModelHerobrineObsidianDiamondChestplate<>(models.bakeLayer(ModelHerobrineObsidianDiamondChestplate.LAYER_LOCATION));
        helmet = new ModelHerobrineObsidianDiamondHelmet<>(models.bakeLayer(ModelHerobrineObsidianDiamondHelmet.LAYER_LOCATION));
        firstPersonPose = new HumanoidModel<>(models.bakeLayer(ModelLayers.PLAYER));
    }

    public static boolean hasExtensions(LivingEntity wearer) {
        for (ObsidianArmorPart part : ObsidianArmorPart.values()) {
            if (isEquipped(wearer, part) && ObsidianArmorClientAnimationState.get(wearer, part) != null) return true;
        }
        return false;
    }

    private static boolean isEquipped(LivingEntity wearer, ObsidianArmorPart part) {
        ItemStack stack = wearer.getItemBySlot(part.slot());
        return part == ObsidianArmorPart.HELMET
                ? HerobrineObsidianArmorCharge.isHelmet(stack) : HerobrineObsidianArmorCharge.isChestplate(stack);
    }

    /** The attachment callback transforms model coordinates into the backend's joint space. */
    public static void render(LivingEntity wearer, PoseStack stack, MultiBufferSource buffer, int light,
                              HumanoidModel<?> humanoidPose, BiConsumer<PoseStack, RigColliderAnchor> attachment) {
        render(wearer, stack, buffer, light, humanoidPose, attachment, anchor -> true);
    }

    private static void render(LivingEntity wearer, PoseStack stack, MultiBufferSource buffer, int light,
                               HumanoidModel<?> humanoidPose, BiConsumer<PoseStack, RigColliderAnchor> attachment,
                               Predicate<RigColliderAnchor> visibleAnchor) {
        ensureModels();
        for (ObsidianArmorPart part : ObsidianArmorPart.values()) {
            if (!isEquipped(wearer, part)) continue;
            var state = ObsidianArmorClientAnimationState.get(wearer, part);
            if (state == null) continue;
            var clip = ObsidianArmorPoseLibrary.clip(state.animationId());
            ItemStack armor = wearer.getItemBySlot(part.slot());
            if (part == ObsidianArmorPart.HELMET) {
                if (!visibleAnchor.test(RigColliderAnchor.HEAD)) continue;
                VertexConsumer vertices = armorBuffer(wearer, armor, HELMET_TEXTURE, buffer);
                helmet.applyAnimationPose(clip, state.elapsedTicks());
                copyPose(helmet.Head, humanoidPose == null ? null : humanoidPose.head);
                stack.pushPose();
                try {
                    attachment.accept(stack, RigColliderAnchor.HEAD);
                    helmet.renderExtensionTiles(stack, vertices, light, OverlayTexture.NO_OVERLAY);
                } finally {
                    stack.popPose();
                }
            } else {
                if (!visibleAnchor.test(RigColliderAnchor.BODY)
                        && !visibleAnchor.test(RigColliderAnchor.RIGHT_ARM)) continue;
                VertexConsumer vertices = armorBuffer(wearer, armor, CHESTPLATE_TEXTURE, buffer);
                chestplate.applyAnimationPose(clip, state.elapsedTicks());
                copyPose(chestplate.Body, humanoidPose == null ? null : humanoidPose.body);
                copyPose(chestplate.RightArm, humanoidPose == null ? null : humanoidPose.rightArm);
                for (RigColliderAnchor anchor : new RigColliderAnchor[]{RigColliderAnchor.BODY, RigColliderAnchor.RIGHT_ARM}) {
                    if (!visibleAnchor.test(anchor)) continue;
                    stack.pushPose();
                    try {
                        attachment.accept(stack, anchor);
                        chestplate.renderExtensionTiles(anchor == RigColliderAnchor.RIGHT_ARM, stack, vertices, light, OverlayTexture.NO_OVERLAY);
                    } finally {
                        stack.popPose();
                    }
                }
            }
        }
    }

    /** Draws the animated right-arm tiles after a first-person renderer has applied its arm transform. */
    public static void renderFirstPersonRightArm(LivingEntity wearer, PoseStack stack,
                                                  MultiBufferSource buffer, int light) {
        ItemStack armor = wearer.getItemBySlot(ObsidianArmorPart.CHESTPLATE.slot());
        if (!HerobrineObsidianArmorCharge.isChestplate(armor)) return;
        var state = ObsidianArmorClientAnimationState.get(wearer, ObsidianArmorPart.CHESTPLATE);
        if (state == null) return;

        ensureModels();
        chestplate.applyAnimationPose(ObsidianArmorPoseLibrary.clip(state.animationId()), state.elapsedTicks());
        PartPose armPose = chestplate.RightArm.storePose();
        chestplate.RightArm.loadPose(PartPose.ZERO);
        try {
            chestplate.renderExtensionTiles(true, stack,
                    armorBuffer(wearer, armor, CHESTPLATE_TEXTURE, buffer), light, OverlayTexture.NO_OVERLAY);
        } finally {
            chestplate.RightArm.loadPose(armPose);
        }
    }

    /** 26.1 submit-node variant used by Punchy's first-person arm renderer. */
    public static void renderFirstPersonRightArm(LivingEntity wearer, PoseStack stack,
                                                  SubmitNodeCollector collector, int light) {
        ItemStack armor = wearer.getItemBySlot(ObsidianArmorPart.CHESTPLATE.slot());
        if (!HerobrineObsidianArmorCharge.isChestplate(armor)) return;
        var state = ObsidianArmorClientAnimationState.get(wearer, ObsidianArmorPart.CHESTPLATE);
        if (state == null) return;

        ensureModels();
        var clip = ObsidianArmorPoseLibrary.clip(state.animationId());
        float elapsedTicks = state.elapsedTicks();
        collector.submitCustomGeometry(stack,
                net.minecraft.client.renderer.rendertype.RenderTypes.armorCutoutNoCull(CHESTPLATE_TEXTURE),
                (rootPose, consumer) -> {
                    PoseStack renderStack = new PoseStack();
                    renderStack.last().set(rootPose);
                    chestplate.applyAnimationPose(clip, elapsedTicks);
                    PartPose armPose = chestplate.RightArm.storePose();
                    chestplate.RightArm.loadPose(PartPose.ZERO);
                    try {
                        chestplate.renderExtensionTiles(true, renderStack, consumer,
                                light, OverlayTexture.NO_OVERLAY);
                    } finally {
                        chestplate.RightArm.loadPose(armPose);
                    }
                });
    }

    private static VertexConsumer armorBuffer(LivingEntity wearer, ItemStack armor,
                                               Identifier texture, MultiBufferSource buffer) {
        ColoredGlintState.setTargetStack(armor, wearer);
        try {
            // Vanilla requests the fixed glint buffer before the shared armor buffer.
            // Reversing that order leaves a stale consumer ("BufferBuilder not started").
            return buffer.getBuffer(net.minecraft.client.renderer.rendertype.RenderTypes.armorCutoutNoCull(texture));
        } finally {
            ColoredGlintState.clear();
        }
    }

    private static void copyPose(ModelPart target, ModelPart source) {
        target.resetPose();
        if (source != null) target.loadPose(source.storePose());
    }

    @SubscribeEvent
    public static void renderFirstPerson(RenderLevelStageEvent.AfterTranslucentFeatures event) {
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null || !mc.options.getCameraType().isFirstPerson() || mc.getCameraEntity() != player
                || player.isSpectator() || player.isSleeping() || player.isInvisible() || !hasExtensions(player)) return;

        float partial = mc.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        PoseStack stack = event.getPoseStack();
        Vec3 camera = event.getLevelRenderState().cameraRenderState.pos;
        var buffer = mc.renderBuffers().bufferSource();
        int light = mc.getEntityRenderDispatcher().getPackedLightCoords(player, partial);
        stack.pushPose();
        try {
            stack.translate(Mth.lerp(partial, player.xo, player.getX()) - camera.x,
                    Mth.lerp(partial, player.yo, player.getY()) - camera.y,
                    Mth.lerp(partial, player.zo, player.getZ()) - camera.z);
            if (firstPersonBackend.render(player, stack, buffer, light, partial)) return;
            ensureModels();
            float bodyYaw = Mth.rotLerp(partial, player.yBodyRotO, player.yBodyRot);
            HumanoidMobRenderer.extractHumanoidRenderState(player, FIRST_PERSON_STATE, partial, mc.getItemModelResolver());
            FIRST_PERSON_STATE.walkAnimationPos = player.walkAnimation.position(partial);
            FIRST_PERSON_STATE.walkAnimationSpeed = player.walkAnimation.speed(partial);
            FIRST_PERSON_STATE.ageInTicks = player.tickCount + partial;
            FIRST_PERSON_STATE.yRot = Mth.rotLerp(partial, player.yHeadRotO, player.yHeadRot) - bodyYaw;
            FIRST_PERSON_STATE.xRot = Mth.lerp(partial, player.xRotO, player.getXRot());
            firstPersonPose.setupAnim(FIRST_PERSON_STATE);
            stack.mulPose(Axis.YP.rotationDegrees(180.0F - bodyYaw));
            stack.scale(-1.0F, -1.0F, 1.0F);
            stack.translate(0.0D, -1.501D, 0.0D);
            // Body-mounted tiles sit between the camera and the hands and can cover
            // the complete first-person arm with the armor's opaque black texture.
            // Punchy supplies a separate animated hand pose, so its arm tiles are
            // rendered by the Punchy mixin in that exact pose instead.
            boolean punchyHands = ModList.get().isLoaded("punchy");
            render(player, stack, buffer, light, firstPersonPose, (pose, anchor) -> {},
                    anchor -> anchor == RigColliderAnchor.HEAD
                            || (!punchyHands && anchor == RigColliderAnchor.RIGHT_ARM));
        } finally {
            stack.popPose();
        }
    }
}
