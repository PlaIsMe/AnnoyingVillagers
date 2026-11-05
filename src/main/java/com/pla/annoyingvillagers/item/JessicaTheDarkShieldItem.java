package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.LegacyShieldProperties;
import net.minecraft.world.item.ShieldItem;

public class JessicaTheDarkShieldItem extends ShieldItem {
    public JessicaTheDarkShieldItem() {
        super(LegacyShieldProperties.withBlocking(com.pla.annoyingvillagers.util.LegacyItemProperties.create()
                .stacksTo(1)
                .durability(1561)));
    }
}
