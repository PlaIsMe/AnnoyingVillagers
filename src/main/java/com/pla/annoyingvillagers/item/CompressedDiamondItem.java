package com.pla.annoyingvillagers.item;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CompressedDiamondItem extends Item {

    public CompressedDiamondItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.EPIC));
    }

    public void appendHoverText(@NotNull ItemStack itemStack, net.minecraft.world.item.Item.TooltipContext level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("tooltip.annoyingvillagers.compressessed_diamond"));
    }
}
