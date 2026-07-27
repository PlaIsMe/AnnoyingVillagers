package com.pla.annoyingvillagers.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import yesman.epicfight.api.utils.math.OpenMatrix4f;

@Mixin(value = OpenMatrix4f.class, remap = false)
public abstract class OpenMatrix4fInvertFixMixin {

    @Inject(
            method = "invert(Lyesman/epicfight/api/utils/math/OpenMatrix4f;Lyesman/epicfight/api/utils/math/OpenMatrix4f;)Lyesman/epicfight/api/utils/math/OpenMatrix4f;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void fixNullDestWhenDetZero(OpenMatrix4f src, @Nullable OpenMatrix4f dest, CallbackInfoReturnable<OpenMatrix4f> cir) {
        if (dest == null && src.determinant() == 0.0F) {
            OpenMatrix4f out = new OpenMatrix4f();
            out.setIdentity();
            cir.setReturnValue(out);
        }
    }
}
