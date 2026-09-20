package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EnderAegisProjectileItem extends Item {

    public EnderAegisProjectileItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).durability(100));
    }

    public @NotNull InteractionResult use(@NotNull Level level, Player player, @NotNull InteractionHand interactionhand) {
        player.startUsingItem(interactionhand);
        return InteractionResult.SUCCESS;
    }

    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack itemstack) {
        return ItemUseAnimation.SPEAR;
    }

    public int getUseDuration(@NotNull ItemStack itemstack, net.minecraft.world.entity.LivingEntity entity) {
        return 72000;
    }

    public boolean releaseUsing(@NotNull ItemStack itemstack, Level level, @NotNull LivingEntity livingentity, int i) {
        if (!level.isClientSide() && livingentity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.isAlive()) {
                EnderAegisProjectile enderAegisProjectile = EnderAegisProjectile.shoot(level, serverPlayer, new Random(), 1.0F, 18.0D, 7);

                itemstack.hurtAndBreak(1, serverPlayer, serverPlayer.getUsedItemHand().asEquipmentSlot());
                enderAegisProjectile.pickup = Pickup.DISALLOWED;
            }
        }
        return true;
    }
}

