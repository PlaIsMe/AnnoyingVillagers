package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteObsidianLongItem extends Item {

    public EliteObsidianLongItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
