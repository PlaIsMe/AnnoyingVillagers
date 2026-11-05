package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteObsidianItem extends Item {

    public EliteObsidianItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
