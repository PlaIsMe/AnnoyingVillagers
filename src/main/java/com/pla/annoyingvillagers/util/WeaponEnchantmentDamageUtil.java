package com.pla.annoyingvillagers.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

public final class WeaponEnchantmentDamageUtil {
    private WeaponEnchantmentDamageUtil() {
    }

    public static float addSharpnessBonus(float baseDamage, LivingEntity owner, Class<? extends Item> weaponClass) {
        return baseDamage + getSharpnessDamageBonus(owner, weaponClass);
    }

    private static float getSharpnessDamageBonus(LivingEntity owner, Class<? extends Item> weaponClass) {
        if (owner == null) {
            return 0.0F;
        }

        ItemStack weaponStack = findWeaponStack(owner, weaponClass);
        if (weaponStack.isEmpty()) {
            return 0.0F;
        }

        int sharpnessLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, weaponStack);
        return sharpnessLevel > 0 ? Enchantments.SHARPNESS.getDamageBonus(sharpnessLevel, MobType.UNDEFINED) : 0.0F;
    }

    private static ItemStack findWeaponStack(LivingEntity owner, Class<? extends Item> weaponClass) {
        ItemStack mainHand = owner.getMainHandItem();
        if (isWeapon(mainHand, weaponClass)) {
            return mainHand;
        }

        ItemStack offhand = owner.getOffhandItem();
        if (isWeapon(offhand, weaponClass)) {
            return offhand;
        }

        if (owner instanceof Player player) {
            for (ItemStack stack : player.getInventory().items) {
                if (isWeapon(stack, weaponClass)) {
                    return stack;
                }
            }
        }

        return ItemStack.EMPTY;
    }

    private static boolean isWeapon(ItemStack stack, Class<? extends Item> weaponClass) {
        return !stack.isEmpty() && weaponClass.isInstance(stack.getItem());
    }
}
