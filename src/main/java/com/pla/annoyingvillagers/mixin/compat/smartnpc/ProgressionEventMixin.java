package com.pla.annoyingvillagers.mixin.compat.smartnpc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = com.pla.smart_npc.event.ProgressionEvent.class, remap = false)
public abstract class ProgressionEventMixin {
    // Only cancellation is needed: Smart NPC 1.0.3 replaced Forge event arguments with NpcEvents wrappers.
    // Callback-only handlers support both signatures without linking to either event API.
    @Inject(method = "onServerStarted", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onServerStarted(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onServerTick", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onServerTick(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onPlayerLoggedIn", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onPlayerLoggedIn(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onPlayerChangedDimension", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onPlayerChangedDimension(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onLivingDeath", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onLivingDeath(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onLivingTick", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onLivingTick(CallbackInfo ci) {
        ci.cancel();
    }
}
