package com.pla.annoyingvillagers.item;

import java.util.List;

import com.pla.annoyingvillagers.entity.EnchantedEnderPearlEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class EnchantedEnderPearlItem extends Item {

    public EnchantedEnderPearlItem() {
        super((new Properties()).stacksTo(1).durability(100));
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, @NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(interactionhand));
    }

    public void appendHoverText(@NotNull ItemStack itemstack, Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipflag) {
        super.appendHoverText(itemstack, level, list, tooltipflag);
        list.add(Component.translatable("tooltip.annoyingvillagers.enchanted_ender_pearl"));
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 72000;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return true;
    }

    public void releaseUsing(@NotNull ItemStack itemstack, Level level, @NotNull LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer serverPlayer) {
            EnchantedEnderPearlEntity enchantedEnderPearl = EnchantedEnderPearlEntity.shoot(level, serverPlayer, RandomSource.create(), 1.3F, 0.0D, 0);
            itemstack.hurtAndBreak(1, serverPlayer, (serverplayer1) -> {
                serverplayer1.broadcastBreakEvent(serverPlayer.getUsedItemHand());
            });
            enchantedEnderPearl.pickup = Pickup.DISALLOWED;
            serverPlayer.getCooldowns().addCooldown(itemstack.getItem(), 20);
        }
    }
}
