package com.pla.annoyingvillagers.mixin.compat.efn;

import com.hm.efn.item.custom.AetherialDuskDualSword_MainHandItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AetherialDuskDualSword_MainHandItem.class, remap = false)
public abstract class AetherialDuskDualSword_MainHandItemMixin {
    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    public void makeWeaponBreakable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}