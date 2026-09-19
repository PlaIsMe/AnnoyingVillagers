package com.pla.annoyingvillagers.mixin.compat.smartnpc;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
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
    private static void annoyingVillagers$onServerTick(ServerTickEvent.Post event, CallbackInfo ci) {
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
    private static void annoyingVillagers$onLivingTick(EntityTickEvent.Post event, CallbackInfo ci) {
        ci.cancel();
    }
}
