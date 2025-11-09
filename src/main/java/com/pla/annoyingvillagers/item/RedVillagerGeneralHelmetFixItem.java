package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.Random;

public class RedVillagerGeneralHelmetFixItem extends Item {
    public RedVillagerGeneralHelmetFixItem() {
        super((new Properties()).stacksTo(1).rarity(Rarity.COMMON));
    }

    private ItemStack randomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        itemStack.setDamageValue(new Random().nextInt(maxDamage / 3, maxDamage * 3 / 4));
        return itemStack;
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean flag) {
        if (level.isClientSide || !(entity instanceof Player player)) return;
        if (stack.getItem() != this) return;

        ItemStack replacement = randomDamage(new ItemStack(AnnoyingVillagersModItems.RED_VILLAGER_GENERAL_HELMET.get()));
        player.getInventory().setItem(slotId, replacement);
    }
}
