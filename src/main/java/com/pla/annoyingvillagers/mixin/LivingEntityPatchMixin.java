package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.gameasset.AVAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;

@Mixin(value = LivingEntityPatch.class, remap = false)
public abstract class LivingEntityPatchMixin {
    @Inject(method = "applyStun", at = @At(value = "HEAD"), cancellable = true)
    private void preventStunOveride(StunType stunType, float stunTime,
                                    CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> self = (LivingEntityPatch) (Object)this;
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(self.getAnimator().getPlayerFor(null)).getRealAnimation();
        if ((stunType == StunType.SHORT
                || stunType == StunType.LONG
                || stunType == StunType.HOLD)
                && (dynamicAnimation == AVAnimations.HIT_BACKWARD
                || dynamicAnimation == AVAnimations.HIT_LEFT
                || dynamicAnimation == AVAnimations.HIT_RIGHT)) {
            self.getOriginal().xxa = 0.0F;
            self.getOriginal().yya = 0.0F;
            self.getOriginal().zza = 0.0F;
            self.getOriginal().setDeltaMovement(0.0F, 0.0F, 0.0F);
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}