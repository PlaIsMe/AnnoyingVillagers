package com.pla.annoyingvillagers.mixin.compat.punchy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.client.renderer.ObsidianArmorExtensionRenderer;
import com.pla.annoyingvillagers.item.HerobrineObsidianArmorCharge;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import punchy.client.render.PunchyArmRenderer;

/** Keeps Obsidian chestplate tiles in the same pose and render pass as Punchy's skin arm. */
@Mixin(value = PunchyArmRenderer.class, remap = false)
public abstract class PunchyArmRendererMixin {
    // Punchy 2.8 adds a vanilla sleeve pass that bypasses Forge's custom armor
    // model/texture hooks. Obsidian's custom UVs turn that sleeve solid black.
    // Its animated arm tiles are already drawn by av$renderObsidianRightArm.
    // Older Punchy versions have no renderArmArmor method.
    @Inject(method = "renderArmArmor", at = @At("HEAD"), cancellable = true, require = 0)
    private static void av$skipVanillaObsidianSleeve(
            PlayerModel<AbstractClientPlayer> model,
            AbstractClientPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            boolean slim,
            float partialTick,
            CallbackInfo ci
    ) {
        if (player != null && HerobrineObsidianArmorCharge.isChestplate(
                player.getItemBySlot(EquipmentSlot.CHEST))) {
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
            PlayerModel<AbstractClientPlayer> model,
            AbstractClientPlayer player,
            HumanoidArm arm,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            ResourceLocation skin,
            boolean slim,
            float partialTick,
            CallbackInfo ci
    ) {
        // Epic Fight owns the whole first-person armor pass when its compatibility
        // add-on is present, so drawing here as well would duplicate the tiles.
        if (arm != HumanoidArm.RIGHT || ModList.get().isLoaded("epicfight")) return;

        poseStack.pushPose();
        try {
            model.rightArm.translateAndRotate(poseStack);
            ObsidianArmorExtensionRenderer.renderFirstPersonRightArm(player, poseStack, buffer, light);
        } finally {
            poseStack.popPose();
        }
    }
}
