package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/** 1.20-style sword constructor backed by 1.21 item attribute components. */
public class LegacySwordItem extends SwordItem {
    protected LegacySwordItem(Tier tier, int attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamage, attackSpeed)));
    }
}
