package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.FishingRodGrappleUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingHookReturnMixin {
    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V",
                    shift = At.Shift.AFTER
            ),
            cancellable = true
    )
    private void annoyingVillagers$tickTonyReturn(CallbackInfo ci) {
        if (FishingRodGrappleUtil.tickTonyReturningHook((FishingHook)(Object)this)) {
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void annoyingVillagers$afterTonyHookTick(CallbackInfo ci) {
        FishingRodGrappleUtil.afterTonyHookVanillaTick((FishingHook)(Object)this);
    }

    @Inject(method = "canHitEntity", at = @At("HEAD"), cancellable = true)
    private void annoyingVillagers$ignoreEntityHitWhileCarryingItem(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (FishingRodGrappleUtil.shouldIgnoreHookEntityHit((FishingHook)(Object)this, target)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void annoyingVillagers$dropStickyItemWhenHookRemoved(Entity.RemovalReason reason, CallbackInfo ci) {
        FishingRodGrappleUtil.onGrappleHookRemoved((FishingHook)(Object)this);
    }
}
