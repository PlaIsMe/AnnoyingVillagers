package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NetheriteSpearItem extends SwordItem implements RigCombatProfileProvider {

    public NetheriteSpearItem() {
        super(new Tier() {
            public int getUses() {
                return 2031;
            }

            public float getSpeed() {
                return 8.0F;
            }

            public float getAttackDamageBonus() {
                return 5.0F;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 20;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(AnnoyingVillagersModItems.DARK_NETHERITE.get()));
            }
        }, 3, -2.2F, (new Properties()));
    }
    
    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.SPEAR;
    }
}