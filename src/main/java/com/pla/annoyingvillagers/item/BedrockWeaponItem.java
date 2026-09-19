package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.util.ArmorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class BedrockWeaponItem extends LegacySwordItem {
    public BedrockWeaponItem() {
        super(new LegacyTier() {
            public int getUses() {
                return 3000;
            }

            public float getSpeed() {
                return 50.0F;
            }

            public float getAttackDamageBonus() {
                return 0.0F;
            }

            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 0;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.BEDROCK));
            }
        }, 3, 0.5F, (new Properties()).fireResistant());
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide()) {
            ArmorUtil.damageArmor(pTarget, new Random().nextInt(1, 3));
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.bedrock_weapon"));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return true;
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        return 30.0F;
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility action) {
        return action == ItemAbilities.PICKAXE_DIG
                || action == ItemAbilities.AXE_DIG
                || action == ItemAbilities.SHOVEL_DIG
                || action == ItemAbilities.HOE_DIG
                || action == ItemAbilities.SHEARS_DIG
                || action == ItemAbilities.SWORD_DIG;
    }
}
