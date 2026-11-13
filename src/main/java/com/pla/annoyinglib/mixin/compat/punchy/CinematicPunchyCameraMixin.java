package com.pla.annoyinglib.mixin.compat.punchy;

import com.pla.annoyinglib.client.camera.CinematicCameraController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Punchy draws before Forge's hand event; suppress its separate pass during the focus shot as well. */
@Pseudo
@Mixin(targets = "punchy.client.render.PunchyArmRenderer", remap = false)
public abstract class CinematicPunchyCameraMixin {
    @Inject(method = "renderFirstPerson", at = @At("HEAD"), cancellable = true, require = 0)
    private static void annoyinglib$hideCinematicHands(CallbackInfo callback) {
        if (CinematicCameraController.active()) callback.cancel();
    }
}
