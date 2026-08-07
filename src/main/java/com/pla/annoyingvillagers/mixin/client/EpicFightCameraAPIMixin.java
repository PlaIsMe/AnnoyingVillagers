package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.client.camera.EpicFightCameraAPI;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mixin(value = EpicFightCameraAPI.class, remap = false)
public abstract class EpicFightCameraAPIMixin {
    @Inject(method = "turnCamera", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$lockVacuumSliceCamera(double dy, double dx, CallbackInfoReturnable<Boolean> cir) {
        if (annoyingVillagers$isVacuumSlicePlaying()) cir.setReturnValue(true);
    }

    @Inject(method = "setCameraRotations", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$lockVacuumSliceAutoCamera(float xRot, float yRot, boolean syncOld, CallbackInfo ci) {
        if (annoyingVillagers$isVacuumSlicePlaying()) ci.cancel();
    }

    private static boolean annoyingVillagers$isVacuumSlicePlaying() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return false;
        LocalPlayerPatch patch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
        if (patch == null) return false;
        AnimationPlayer animationPlayer = patch.getAnimator().getPlayerFor(null);
        return animationPlayer != null && animationPlayer.getRealAnimation() == AnimsEpicFightAwaken.VACUUM_SLICE;
    }
}
