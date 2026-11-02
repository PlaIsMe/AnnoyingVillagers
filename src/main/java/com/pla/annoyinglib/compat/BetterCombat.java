package com.pla.annoyinglib.compat;

import net.bettercombat.logic.PlayerAttackHelper;
import net.minecraft.world.entity.player.Player;

public final class BetterCombat {
    private BetterCombat() {
    }

    public static float getAttackCooldownTicks(Player player) {
        return PlayerAttackHelper.getAttackCooldownTicksCapped(player);
    }
}
