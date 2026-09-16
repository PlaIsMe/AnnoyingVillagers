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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.BiConsumer;

/** Shared dynamic geometry for first person and renderers that bake the armor shell. */
@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class ObsidianArmorExtensionRenderer {
    private static final ResourceLocation CHESTPLATE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,
            "textures/models/armor/herobrine_obsidian_armor_layer_1.png");
    private static final ResourceLocation HELMET_TEXTURE = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID,
            "textures/models/armor/herobrine_obsidian_armor_layer_2.png");
    private static ModelHerobrineObsidianDiamondChestplate<LivingEntity> chestplate;
    private static ModelHerobrineObsidianDiamondHelmet<LivingEntity> helmet;
    private static HumanoidModel<LivingEntity> firstPersonPose;
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

    @Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        firstPersonPose = new HumanoidModel<>(models.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
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
        ensureModels();
        for (ObsidianArmorPart part : ObsidianArmorPart.values()) {
            if (!isEquipped(wearer, part)) continue;
            var state = ObsidianArmorClientAnimationState.get(wearer, part);
            if (state == null) continue;
            var clip = ObsidianArmorPoseLibrary.clip(state.animationId());
            RenderType armorType = RenderType.armorCutoutNoCull(
                    part == ObsidianArmorPart.HELMET ? HELMET_TEXTURE : CHESTPLATE_TEXTURE);
            ItemStack armor = wearer.getItemBySlot(part.slot());
            VertexConsumer vertices;
            ColoredGlintState.setTargetStack(armor, wearer);
            try {
                // Vanilla requests the fixed glint buffer BEFORE the shared armor
                // buffer. Reversing that order ends the armor batch and leaves a
                // stale consumer ("BufferBuilder not started"). Our ItemRenderer
                // mixin supplies the colored glint for this same helper.
                vertices = ItemRenderer.getArmorFoilBuffer(buffer, armorType, false, armor.hasFoil());
            } finally {
                ColoredGlintState.clear();
            }
            if (part == ObsidianArmorPart.HELMET) {
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
                chestplate.applyAnimationPose(clip, state.elapsedTicks());
                copyPose(chestplate.Body, humanoidPose == null ? null : humanoidPose.body);
                copyPose(chestplate.RightArm, humanoidPose == null ? null : humanoidPose.rightArm);
                for (RigColliderAnchor anchor : new RigColliderAnchor[]{RigColliderAnchor.BODY, RigColliderAnchor.RIGHT_ARM}) {
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

    private static void copyPose(ModelPart target, ModelPart source) {
        target.resetPose();
        if (source != null) target.copyFrom(source);
    }

    @SubscribeEvent
    public static void renderFirstPerson(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;
        Minecraft mc = Minecraft.getInstance();
        var player = mc.player;
        if (player == null || !mc.options.getCameraType().isFirstPerson() || event.getCamera().getEntity() != player
                || player.isSpectator() || player.isSleeping() || player.isInvisible() || !hasExtensions(player)) return;

        float partial = event.getPartialTick();
        PoseStack stack = event.getPoseStack();
        Vec3 camera = event.getCamera().getPosition();
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
            firstPersonPose.crouching = player.isCrouching();
            firstPersonPose.riding = player.isPassenger();
            firstPersonPose.attackTime = player.getAttackAnim(partial);
            firstPersonPose.setupAnim(player, player.walkAnimation.position(partial), player.walkAnimation.speed(partial),
                    player.tickCount + partial, Mth.rotLerp(partial, player.yHeadRotO, player.yHeadRot) - bodyYaw,
                    Mth.lerp(partial, player.xRotO, player.getXRot()));
            stack.mulPose(Axis.YP.rotationDegrees(180.0F - bodyYaw));
            stack.scale(-1.0F, -1.0F, 1.0F);
            stack.translate(0.0D, -1.501D, 0.0D);
            render(player, stack, buffer, light, firstPersonPose, (pose, anchor) -> {});
        } finally {
            stack.popPose();
        }
    }
}
