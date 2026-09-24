package com.pla.annoyingvillagers.mixin.compat.punchy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.compat.PunchyItemRenderContext;
import com.pla.annoyingvillagers.client.renderer.ObsidianArmorExtensionRenderer;
import com.pla.annoyingvillagers.item.HerobrineObsidianArmorCharge;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import punchy.client.access.TransformablePart;
import punchy.client.render.PunchyArmRenderer;

/**
 * Legacy Obsidian armor rendering plus the three 26.1-only weapon bridges:
 * PAL arm attachment, authored hand-display selection, and scoped model resolution.
 * The compatibility pack owns model replacement and animation selection; these
 * hooks only bridge renderer behavior that the pack cannot configure.
 */
@Mixin(value = PunchyArmRenderer.class, remap = false)
public abstract class PunchyArmRendererMixin {
    @WrapOperation(
            method = "renderItemInHand",
            at = @At(value = "INVOKE", target =
                    "Lnet/minecraft/client/model/player/PlayerModel;translateToHand(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;)V"),
            require = 1
    )
    private static void av$followPunchyArm(PlayerModel model, AvatarRenderState state,
                                          HumanoidArm arm, PoseStack poseStack, Operation<Void> original,
                                          @Local(name = "stack") ItemStack stack) {
        // Use Punchy's resolved visual stack, not the player's current slot:
        // equip transitions can still be drawing the previous item.
        if (!PunchyItemRenderContext.isAvItem(stack)) {
            original.call(model, state, arm, poseStack);
            return;
        }
        ModelPart part = arm == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        if ((Object) part instanceof TransformablePart animatedPart
                && animatedPart.punchy$getExplicitTransform() != null) {
            // PAL's PlayerModel.translateToHand wrapper skips translateAndRotate
            // whenever a Better Combat pose is active (notably two-handed weapons).
            // That also skips Punchy's explicit-matrix hook on ModelPart, attaching
            // the weapon to the old vanilla arm instead of the arm actually drawn.
            // Use the same ModelPart path as Punchy's arm rendering. Keep this
            // scoped to Punchy's item pass; normal PAL/third-person poses stay intact.
            // renderArm submits this part directly, without the model root.
            part.translateAndRotate(poseStack);
        } else {
            original.call(model, state, arm, poseStack);
        }
    }

    @WrapOperation(
            method = "renderItemInHand",
            at = @At(value = "INVOKE", target =
                    "Lpunchy/client/render/PunchyArmRenderer;setForceVanillaDisplay(Z)V"),
            require = 1
    )
    private static void av$keepLegacyHandDisplay(boolean force, Operation<Void> original,
                                                  @Local(name = "stack") ItemStack stack) {
        // Punchy's orientation heuristic otherwise switches these models from
        // THIRD_PERSON to FIRST_PERSON inside ItemInHandRenderer.renderItem.
        // That camera-space transform moves the shaft away from the animated
        // hand and tilts the blade inward. Keep the authored hand-space pose for
        // the legacy AV weapon set, including both hands and alternate forms.
        // Preserve Punchy's decision for every item outside that set.
        original.call(force && !PunchyItemRenderContext.keepAuthoredHandDisplay(stack));
    }

    @WrapOperation(
            method = "renderItemInHand",
            at = @At(value = "INVOKE", target =
                    "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V"),
            require = 1
    )
    private static void av$renderWithCurrentModelTransform(ItemInHandRenderer renderer, LivingEntity entity,
                                                           ItemStack stack, ItemDisplayContext context,
                                                           PoseStack poses, SubmitNodeCollector collector,
                                                           int light, Operation<Void> original) {
        if (!PunchyItemRenderContext.isAvItem(stack)) {
            original.call(renderer, entity, stack, context, poses, collector, light);
            return;
        }
        PunchyItemRenderContext.begin();
        try {
            original.call(renderer, entity, stack, context, poses, collector, light);
        } finally {
            PunchyItemRenderContext.end();
        }
    }

    // Punchy 2.8 adds a vanilla sleeve pass that bypasses Forge's custom armor
    // model/texture hooks. Obsidian's custom UVs turn that sleeve solid black.
    // Its animated arm tiles are already drawn by av$renderObsidianRightArm.
    // Older Punchy versions have no renderArmArmor method.
    @Inject(method = "renderArmArmor", at = @At("HEAD"), cancellable = true, require = 0)
    private static void av$skipVanillaObsidianSleeve(
            AvatarRenderer<AbstractClientPlayer> renderer,
            AvatarRenderState renderState,
            PlayerModel model,
            AbstractClientPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light,
            CallbackInfo ci
    ) {
        if (av$hasObsidianChestplate(player)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "renderArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE,
                    remap = true
            ),
            require = 1
    )
    private static void av$renderObsidianRightArm(
            PlayerModel model,
            AbstractClientPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int light,
            Identifier skin,
            boolean slim,
            float partialTick,
            CallbackInfo ci
    ) {
        // Epic Fight owns the whole first-person armor pass when its compatibility
        // add-on is present, so drawing here as well would duplicate the tiles.
        if (arm != HumanoidArm.RIGHT || ModList.get().isLoaded("epicfight")
                || !av$hasObsidianChestplate(player)) return;

        poseStack.pushPose();
        try {
            model.rightArm.translateAndRotate(poseStack);
            ObsidianArmorExtensionRenderer.renderFirstPersonRightArm(player, poseStack, collector, light);
        } finally {
            poseStack.popPose();
        }
    }

    @Unique
    private static boolean av$hasObsidianChestplate(AbstractClientPlayer player) {
        if (player == null) return false;
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        return PunchyItemRenderContext.isAvItem(chest) && HerobrineObsidianArmorCharge.isChestplate(chest);
    }
}
