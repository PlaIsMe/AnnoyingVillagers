package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.core.component.DataComponents;

/** Keeps the legacy item subclasses while applying the component-backed 26.1 sword definition. */
public class LegacySwordItem extends Item {
    protected LegacySwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Properties properties) {
        super(properties.sword(material, attackDamage, attackSpeed));
    }

    protected LegacySwordItem(LegacyTier material, int attackDamage, float attackSpeed, Item.Properties properties) {
        super(material.applySwordProperties(properties, attackDamage, attackSpeed));
    }

    public static boolean isSword(ItemStack stack) {
        return stack.has(DataComponents.WEAPON) && stack.has(DataComponents.TOOL);
    }

    public static boolean isSword(Item item) {
        return item.components().has(DataComponents.WEAPON) && item.components().has(DataComponents.TOOL);
    }

    public static boolean isTool(Item item) {
        return item.components().has(DataComponents.TOOL);
    }
}
