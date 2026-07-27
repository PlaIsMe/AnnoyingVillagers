package com.pla.annoyingvillagers.mixin.compat.efn;

import com.pla.annoyingvillagers.compat.EpicFightNightFall;
import com.pla.annoyingvillagers.config.AnnoyingVillagersConfig;
import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SwordItem.class, remap = false)
public abstract class SwordItemMixin {
    @Inject(method = "hurtEnemy", at = @At("HEAD"), cancellable = true)
    public void damageEfnWeapons(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfoReturnable<Boolean> cir) {
        if (pAttacker instanceof Player) {
            if (EpicFightNightFall.isEfnWeapons(pStack)) {
                if (CommonUtil.isAvDamageableEfnWeaponsMob(pTarget)) {
                    pStack.hurtAndBreak(AnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get(), pAttacker, (livingEntity) -> {
                        livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }
                cir.setReturnValue(true);
            }
        }
    }
}