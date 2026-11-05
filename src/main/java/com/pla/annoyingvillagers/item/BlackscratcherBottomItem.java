package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class BlackscratcherBottomItem extends Item {
    public BlackscratcherBottomItem() {
            super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).rarity(Rarity.UNCOMMON));
        }
}
