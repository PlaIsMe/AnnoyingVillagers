package com.pla.annoyingvillagers.mixin.compat.smartnpc;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = com.pla.smart_npc.event.ProgressionEvent.class, remap = false)
public abstract class ProgressionEventMixin {
    @Inject(method = "onServerStarted", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onServerStarted(ServerStartedEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onServerTick", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onServerTick(TickEvent.ServerTickEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onPlayerLoggedIn", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onPlayerChangedDimension", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onLivingDeath", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onLivingDeath(LivingDeathEvent event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "onLivingTick", at = @At("HEAD"), cancellable = true)
    private static void annoyingVillagers$onLivingTick(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
