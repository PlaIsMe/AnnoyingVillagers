package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NullHoeItem extends HoeItem {

    public NullHoeItem() {
        super(ToolMaterial.DIAMOND, -3.0F, 0.0F, com.pla.annoyingvillagers.util.LegacyItemProperties.create());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.null_weapon"));
    }
}
