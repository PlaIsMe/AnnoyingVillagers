package com.pla.annoyingvillagers.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public final class LegacyPlayerMessages {
    private LegacyPlayerMessages() {
    }

    public static void display(Player player, Component message, boolean overlay) {
        if (overlay) {
            player.sendOverlayMessage(message);
        } else {
            player.sendSystemMessage(message);
        }
    }
}
