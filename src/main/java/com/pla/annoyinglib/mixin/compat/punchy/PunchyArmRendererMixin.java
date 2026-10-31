package com.pla.annoyinglib.mixin.compat.punchy;

import com.mojang.blaze3d.vertex.PoseStack;
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
     // add this in herobrine_vessels
    // @Inject(method = "renderArmArmor", at = @At("HEAD"), cancellable = true, require = 0)
    // private static void av$skipVanillaObsidianSleeve(
    //         PlayerModel<AbstractClientPlayer> model,
    //         AbstractClientPlayer player,
    //         HumanoidArm arm,
    //         PoseStack poseStack,
    //         MultiBufferSource buffer,
    //         int light,
    //         boolean slim,
    //         float partialTick,
    //         CallbackInfo ci
    // ) {
    //     if (player != null && HerobrineObsidianArmorCharge.isChestplate(
    //             player.getItemBySlot(EquipmentSlot.CHEST))) {
    //         ci.cancel();
    //     }
    // }

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
        if (arm != HumanoidArm.RIGHT || ModList.get().isLoaded("epicfight")) return;

        poseStack.pushPose();
        try {
            model.rightArm.translateAndRotate(poseStack);
             // add this in herobrine_vessels
            // ObsidianArmorExtensionRenderer.renderFirstPersonRightArm(player, poseStack, buffer, light);
        } finally {
            poseStack.popPose();
        }
    }
}
