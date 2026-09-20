package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.NotNull;

public class EnchantedEnderPearlItem extends Item {

    public EnchantedEnderPearlItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).durability(100));
    }

    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return InteractionResult.SUCCESS;
    }

    public void appendHoverText(@NotNull ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, @NotNull net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, display, list, tooltipflag);
        list.accept(Component.translatable("tooltip.annoyingvillagers.enchanted_ender_pearl"));
    }

    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack itemstack) {
        return ItemUseAnimation.BOW;
    }

    public int getUseDuration(@NotNull ItemStack itemstack, net.minecraft.world.entity.LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return true;
    }

    public boolean releaseUsing(@NotNull ItemStack itemstack, Level level, @NotNull LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer serverPlayer) {
            EnchantedEnderPearlEntity enchantedEnderPearl = EnchantedEnderPearlEntity.shoot(level, serverPlayer, RandomSource.create(), 1.3F, 0.0D, 0);
            itemstack.hurtAndBreak(1, serverPlayer, serverPlayer.getUsedItemHand().asEquipmentSlot());
            enchantedEnderPearl.pickup = Pickup.DISALLOWED;
            serverPlayer.getCooldowns().addCooldown(new net.minecraft.world.item.ItemStack(itemstack.getItem()), 20);
        }
        return true;
    }
}
