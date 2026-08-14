package com.pla.annoyingvillagers.mixin.client;

import com.pla.annoyingvillagers.gameasset.AnimsEnderGlaive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
    private void lockEnderGlaiveInnateCamera(double dy, double dx, CallbackInfoReturnable<Boolean> cir) {
        if (annoyingVillagers$isEnderGlaiveInnate()) cir.setReturnValue(true);
    }

    @Inject(method = "setCameraRotations", at = @At("HEAD"), cancellable = true)
    private void lockEnderGlaiveInnateAutoCamera(float xRot, float yRot, boolean syncOld, CallbackInfo ci) {
        if (annoyingVillagers$isEnderGlaiveInnate()) ci.cancel();
    }

    @Unique
    private static boolean annoyingVillagers$isEnderGlaiveInnate() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return false;
        LocalPlayerPatch patch = EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class);
        if (patch == null) return false;
        AnimationPlayer animationPlayer = patch.getAnimator().getPlayerFor(null);
        return animationPlayer != null && (animationPlayer.getRealAnimation() == AnimsEnderGlaive.ENDER_GLAIVE_INNATE
                || animationPlayer.getRealAnimation() == AnimsEnderGlaive.ENDER_GLAIVE_INNATE_SPECIAL);
    }
}
