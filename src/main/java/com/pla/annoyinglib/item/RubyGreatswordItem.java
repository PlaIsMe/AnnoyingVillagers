package com.pla.annoyinglib.item;
import com.pla.annoyinglib.util.ContentRegistry;

import com.pla.annoyinglib.rig.RigCombatProfileProvider;
import com.pla.annoyinglib.rig.RigCombatStyle;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class RubyGreatswordItem extends SwordItem implements RigCombatProfileProvider {

    public RubyGreatswordItem() {
        super(new Tier() {
            public int getUses() {
                return 2561;
            }

            public float getSpeed() {
                return 6.0F;
            }

            public float getAttackDamageBonus() {
                return 4.5F;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ContentRegistry.item("villager_kingdom:ruby")));
            }
        }, 3, -2.5F, (new Properties()));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.GREATSWORD;
    }
}
