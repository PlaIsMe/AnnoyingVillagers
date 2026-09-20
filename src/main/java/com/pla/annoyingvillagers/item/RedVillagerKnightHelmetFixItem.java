package com.pla.annoyingvillagers.item;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.Random;

public class RedVillagerKnightHelmetFixItem extends Item {
    public RedVillagerKnightHelmetFixItem() {
        super((com.pla.annoyingvillagers.util.LegacyItemProperties.create()).stacksTo(1).rarity(Rarity.COMMON));
    }

    private ItemStack randomDamage(ItemStack itemStack) {
        int maxDamage = itemStack.getMaxDamage();
        itemStack.setDamageValue(new Random().nextInt(maxDamage / 3, maxDamage * 3 / 4));
        return itemStack;
    }

    public void inventoryTick(net.minecraft.world.item.ItemStack stack, net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.Entity entity, @org.jetbrains.annotations.Nullable net.minecraft.world.entity.EquipmentSlot equipmentSlot) {
        int slotId = com.pla.annoyingvillagers.util.LegacyItemTicks.findInventorySlot(entity, stack);
        boolean flag = equipmentSlot == net.minecraft.world.entity.EquipmentSlot.MAINHAND;
        if (level.isClientSide() || !(entity instanceof Player player)) return;
        if (stack.getItem() != this) return;

        ItemStack replacement = randomDamage(new ItemStack(AnnoyingVillagersModItems.RED_VILLAGER_KNIGHT_HELMET.get()));
        player.getInventory().setItem(slotId, replacement);
    }
}
