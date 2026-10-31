package com.pla.annoyinglib.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class PurpleGemItem extends Item {

    public PurpleGemItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.RARE));
    }
}
