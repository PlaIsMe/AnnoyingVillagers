package com.pla.annoyinglib.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RubyItem extends Item {

    public RubyItem() {
        super((new Properties()).stacksTo(64).rarity(Rarity.RARE));
    }
}