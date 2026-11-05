package com.pla.annoyingvillagers.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/** Registry-aware bridge for the mod's established enchantment setup code. */
public final class EnchantmentUtil {
    private EnchantmentUtil() {
    }

    public static void enchant(ItemStack stack, ResourceKey<Enchantment> enchantment, int level) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        Holder<Enchantment> holder = server.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(enchantment);
        stack.enchant(holder, level);
    }

    public static int getLevel(ResourceKey<Enchantment> enchantment, ItemStack stack) {
        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().unwrapKey().filter(enchantment::equals).isPresent()) {
                return entry.getIntValue();
            }
        }
        return 0;
    }

    public static float getDamageBonus(ItemStack stack, LivingEntity target) {
        float bonus = 0.0F;
        int sharpness = getLevel(net.minecraft.world.item.enchantment.Enchantments.SHARPNESS, stack);
        if (sharpness > 0) {
            bonus += 0.5F * sharpness + 0.5F;
        }
        if (target.getType().builtInRegistryHolder().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
            bonus += 2.5F * getLevel(net.minecraft.world.item.enchantment.Enchantments.SMITE, stack);
        }
        if (target.getType().builtInRegistryHolder().is(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS)) {
            bonus += 2.5F * getLevel(net.minecraft.world.item.enchantment.Enchantments.BANE_OF_ARTHROPODS, stack);
        }
        return bonus;
    }
}
