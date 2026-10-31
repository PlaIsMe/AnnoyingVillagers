package com.pla.annoyinglib.item;

import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.rig.RigCombatProfileProvider;
import com.pla.annoyinglib.rig.RigCombatStyle;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class NetheriteFalchionItem extends SwordItem implements RigCombatProfileProvider {

    public NetheriteFalchionItem() {
        super(new Tier() {
            public int getUses() {
                return 2031;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 5.6F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 25;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingLibModItems.DARK_NETHERITE.get()));
            }
        }, 3, -2.0F, (new Properties()));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.TACHI;
    }
}
