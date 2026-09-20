package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ShieldItem;

public class HeaterShield extends ShieldItem {
    public HeaterShield() {
        super(com.pla.annoyingvillagers.util.LegacyItemProperties.create()
                .stacksTo(1)
                .durability(1561)
        );
    }
}
