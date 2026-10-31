package com.pla.annoyinglib.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class DarkNetheriteItem extends Item {

    public DarkNetheriteItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.RARE));
    }
}