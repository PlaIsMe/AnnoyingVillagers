package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class IronGlaiveItem extends LegacySwordItem {

    public IronGlaiveItem() {
        super(new LegacyTier() {
            public int getUses() {
                return 450;
            }

            public float getSpeed() {
                return 6.5F;
            }

            public float getAttackDamageBonus() {
                return 2.0F;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 8;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.IRON_INGOT));
            }
        }, 3, -2.6F, (new Properties()));
    }
}
