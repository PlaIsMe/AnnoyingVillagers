package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NullPickaxeItem extends PickaxeItem {

    public NullPickaxeItem() {
        super(Tiers.DIAMOND, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F)));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.null_weapon"));
    }
}
