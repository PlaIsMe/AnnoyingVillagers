package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.rig.RigCombatProfileProvider;
import com.pla.annoyingvillagers.rig.RigCombatStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class WoodenDoorItem extends SwordItem implements RigCombatProfileProvider {

    public WoodenDoorItem() {
        super(new Tier() {
            public int getUses() {
                return 400;
            }

            public float getSpeed() {
                return 4.0F;
            }

            public float getAttackDamageBonus() {
                return 0F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 4;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.OAK_DOOR));
            }
        }, 3, -2.5F, (new Properties()));
    }

    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.wooden_door"));
    }

    @Override
    public RigCombatStyle getRigCombatStyle(ItemStack stack) {
        return RigCombatStyle.GREATAXE;
    }
}

