package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ShieldItem;

public class NetheriteShield extends ShieldItem {
    public NetheriteShield() {
        super(com.pla.annoyingvillagers.util.LegacyItemProperties.create()
                .stacksTo(1)
                .durability(1561)
        );
    }
}
