package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NullShovelItem extends ShovelItem {

    public NullShovelItem() {
        super(Tiers.DIAMOND, 1.5F, -3.0F, new Item.Properties());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.null_weapon"));
    }
}
