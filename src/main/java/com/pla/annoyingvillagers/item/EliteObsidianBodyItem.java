package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EliteObsidianBodyItem extends Item {

    public EliteObsidianBodyItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
