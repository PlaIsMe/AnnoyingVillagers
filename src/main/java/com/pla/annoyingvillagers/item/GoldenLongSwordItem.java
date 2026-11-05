package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class GoldenLongSwordItem extends LegacySwordItem {

    public GoldenLongSwordItem() {
        super(new LegacyTier() {
            public int getUses() {
                return 32;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 2.0F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.GOLD_INGOT);
            }
        }, 3, -2.5F, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
    }
}
