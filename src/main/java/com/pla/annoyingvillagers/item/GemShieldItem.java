package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ShieldItem;

public class GemShieldItem extends ShieldItem {
    public GemShieldItem() {
        super(com.pla.annoyingvillagers.util.LegacyItemProperties.create()
                .stacksTo(1)
                .durability(1561)
        );
    }
}
