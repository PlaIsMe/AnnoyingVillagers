package com.pla.annoyinglib.item;

import com.pla.annoyinglib.init.AnnoyingLibModItems;
import com.pla.annoyinglib.rig.RigCombatProfileProvider;
import com.pla.annoyinglib.rig.RigCombatStyle;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class DiamondGreatswordItem extends SwordItem implements RigCombatProfileProvider {

    public DiamondGreatswordItem() {
        super(new Tier() {
            public int getUses() {
                return 1561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 3.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 20;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingLibModItems.COMPRESSED_DIAMOND.get()));
            }
        }, 3, -2.5F, (new Properties()));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.GREATSWORD;
    }
}

