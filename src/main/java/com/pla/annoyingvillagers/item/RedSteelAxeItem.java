package com.pla.annoyingvillagers.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RedSteelAxeItem extends LegacySwordItem {
    public RedSteelAxeItem() {
        super(new LegacyTier() {
            public int getUses() {
                return 1680;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 3.4F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(Items.LAPIS_LAZULI);
            }
        }, 3, -1.5F, (com.pla.annoyingvillagers.util.LegacyItemProperties.create()));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.future_update"));
    }
}
