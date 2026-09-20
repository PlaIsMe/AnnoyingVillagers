package com.pla.annoyingvillagers.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

/** Adapts the generated numeric tier definitions to 26.1's component-based tools. */
public abstract class LegacyTier {
    public abstract int getUses();
    public abstract float getSpeed();
    public abstract float getAttackDamageBonus();
    public abstract int getLevel();
    public abstract int getEnchantmentValue();
    public abstract @Nullable Ingredient getRepairIngredient();

    public TagKey<Block> getIncorrectBlocksForDrops() {
        return switch (getLevel()) {
            case 0 -> BlockTags.INCORRECT_FOR_WOODEN_TOOL;
            case 1 -> BlockTags.INCORRECT_FOR_STONE_TOOL;
            case 2 -> BlockTags.INCORRECT_FOR_IRON_TOOL;
            case 3 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            default -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        };
    }

    public ToolMaterial toToolMaterial() {
        return new ToolMaterial(
                getIncorrectBlocksForDrops(), getUses(), getSpeed(), getAttackDamageBonus(),
                Math.max(1, getEnchantmentValue()), ItemTags.DIAMOND_TOOL_MATERIALS);
    }

    public Item.Properties applySwordProperties(Item.Properties properties, float attackDamage, float attackSpeed) {
        Item.Properties result = toToolMaterial().applySwordProperties(properties, attackDamage, attackSpeed);
        Ingredient repairIngredient = getRepairIngredient();
        return repairIngredient == null
                ? result
                : result.component(DataComponents.REPAIRABLE, new Repairable(repairIngredient.getValues()));
    }
}
