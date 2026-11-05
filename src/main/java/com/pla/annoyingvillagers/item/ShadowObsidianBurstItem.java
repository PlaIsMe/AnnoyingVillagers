package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyItemData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ShadowObsidianBurstItem extends Item {

    public ShadowObsidianBurstItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return LegacyItemData.has(stack) && LegacyItemData.get(stack) != null && LegacyItemData.get(stack).getBooleanOr("foil", false);
    }

    public boolean isCorrectToolForDrops(@NotNull BlockState blockstate) {
        return true;
    }
}
