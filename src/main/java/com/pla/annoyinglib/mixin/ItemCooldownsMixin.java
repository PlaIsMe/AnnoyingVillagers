package com.pla.annoyinglib.mixin;
import net.minecraft.world.item.Items;
import com.pla.annoyinglib.compat.MobSupremacy;
import net.minecraftforge.fml.ModList;
import com.pla.annoyinglib.compat.HerobrineVessels;
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
        if (ModList.get().isLoaded("herobrine_vessels") && HerobrineVessels.ignoresItemCooldown(item)) cir.setReturnValue(false);
        if (
         item == (ModList.get().isLoaded("mob_supremacy") ? MobSupremacy.getItems_BLUE_DEMON_TRIDENT() : Items.AIR)) cir.setReturnValue(false);
    }
}
