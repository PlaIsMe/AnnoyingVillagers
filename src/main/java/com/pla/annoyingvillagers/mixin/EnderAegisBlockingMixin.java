package com.pla.annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlocksAttacks.class)
public abstract class EnderAegisBlockingMixin {
    @Inject(method = "disable", at = @At("HEAD"), cancellable = true)
    private void av$keepAegisUsable(ServerLevel level, LivingEntity entity, float duration, ItemStack stack, CallbackInfo ci) {
        if (VanillaWeaponAbilityUtil.abilitiesEnabled() && stack.getItem() instanceof EnderAegisItem) ci.cancel();
    }
}
