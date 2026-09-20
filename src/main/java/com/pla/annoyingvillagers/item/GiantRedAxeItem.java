package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public class GiantRedAxeItem extends Item {
    public GiantRedAxeItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack pStack) {
        return true;
    }
}
