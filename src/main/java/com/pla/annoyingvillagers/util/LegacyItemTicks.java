package com.pla.annoyingvillagers.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class LegacyItemTicks {
    private LegacyItemTicks() {
    }

    /** Returns the inventory index used by the pre-26.1 item tick callback. */
    public static int findInventorySlot(Entity owner, ItemStack stack) {
        if (!(owner instanceof Player player)) {
            return -1;
        }
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            if (player.getInventory().getItem(slot) == stack) {
                return slot;
            }
        }
        return -1;
    }
}
