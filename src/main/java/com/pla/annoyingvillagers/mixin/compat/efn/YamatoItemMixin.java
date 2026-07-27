package com.pla.annoyingvillagers.mixin.compat.efn;

import com.hm.efn.item.custom.YamatoItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = YamatoItem.class, remap = false)
public abstract class YamatoItemMixin {
    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    public void makeWeaponBreakable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}