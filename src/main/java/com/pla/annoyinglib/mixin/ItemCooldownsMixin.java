package com.pla.annoyinglib.mixin;

import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.util.VanillaWeaponAbilityUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin {
    @Inject(method = "isOnCooldown", at = @At("HEAD"), cancellable = true)
    private void ignoreCooldownForVanillaAbilityItems(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (!VanillaWeaponAbilityUtil.abilitiesEnabled()) return;
        // add this in herobrine_vessels
        if (
        // item == AnnoyingLibModItems.ENDER_AEGIS.get() || item == AnnoyingLibModItems.ENDER_SLAYER_SCYTHE.get() || item == AnnoyingLibModItems.DEMONIAC_VOLTAGE_REAVER.get() ||
         item == AnnoyingLibModItems.BLUE_DEMON_TRIDENT.get()) cir.setReturnValue(false);
    }
}