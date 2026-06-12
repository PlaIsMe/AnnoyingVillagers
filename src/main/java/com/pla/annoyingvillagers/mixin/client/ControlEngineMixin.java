package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControlEngine;

@Mixin(value = ControlEngine.class, remap = false)
public abstract class ControlEngineMixin {
    @Inject(method = "maybeGuard", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$skipGuardForOffhandFishingRod(CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && FishingRodGrappleUtil.shouldOffhandFishingRodTakeRightClick(player)) {
            ci.cancel();
        }
    }
}
