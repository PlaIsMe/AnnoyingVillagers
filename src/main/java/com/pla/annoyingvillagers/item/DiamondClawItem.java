package com.pla.annoyingvillagers.item;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class DiamondClawItem extends SwordItem {
    public DiamondClawItem() {
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
                return 5;
            }

            public int getEnchantmentValue() {
                return 21;
            }

            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.DIAMOND));
            }
        }, 3, 0.8F, (new Properties()));
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        if (target.getActiveEffects().isEmpty()) {
            return super.interactLivingEntity(stack, player, target, hand);
        }

        if (!player.level().isClientSide()) {
            target.removeAllEffects();
            stack.hurtAndBreak(1, player, user -> user.broadcastBreakEvent(hand));
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }
}

