package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyShieldProperties;
import net.minecraft.world.item.ShieldItem;

public class HeaterShield extends ShieldItem {
    public HeaterShield() {
        super(LegacyShieldProperties.withBlocking(com.pla.annoyingvillagers.util.LegacyItemProperties.create()
                .stacksTo(1)
                .durability(1561)));
    }
}
