package com.pla.annoyinglib.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class GreatSwordSkillItem extends Item {
    public GreatSwordSkillItem() {
        super((new Properties()).stacksTo(1).rarity(Rarity.UNCOMMON));
    }
}
