package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteObsidianBigItem extends Item {

    public EliteObsidianBigItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
