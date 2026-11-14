package com.pla.annoyinglib.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import java.util.List;
import org.jetbrains.annotations.Nullable;

/** A possession escape token. Deliberately supplies no vanilla death-protection hook. */
public final class EnchantedTotemOfUndyingItem extends Item {
    public EnchantedTotemOfUndyingItem() { super(new Properties().stacksTo(1).rarity(Rarity.EPIC)); }
    @Override public boolean isFoil(ItemStack stack) { return true; }
    @Override public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flags) {
        lines.add(Component.translatable("tooltip.annoyinglib.enchanted_totem_of_undying"));
    }
}
