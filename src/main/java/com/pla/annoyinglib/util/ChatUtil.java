package com.pla.annoyinglib.util;

import com.pla.annoyinglib.config.AnnoyingLibConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import java.util.Objects;

public class ChatUtil {
    public static void joinGame(Entity entity) {
        if (AnnoyingLibConfig.TURN_ON_NPC_CHAT.get() && entity.level().getServer() != null) {
            Objects.requireNonNull(entity.level().getServer()).getPlayerList()
                    .broadcastSystemMessage(Component.literal(entity.getDisplayName().getString() + " has joined the game")
                            .withStyle(ChatFormatting.YELLOW), false);
        }
    }

    public static void joinGame(Entity entity, String string) {
        if (AnnoyingLibConfig.TURN_ON_NPC_CHAT.get() && entity.level().getServer() != null) {
            Objects.requireNonNull(entity.level().getServer()).getPlayerList()
                    .broadcastSystemMessage(Component.literal(string + " has joined the game")
                            .withStyle(ChatFormatting.YELLOW), false);
        }
    }

    public static void leaveGame(Entity entity) {
        if (AnnoyingLibConfig.TURN_ON_NPC_CHAT.get() && entity.level().getServer() != null) {
            Objects.requireNonNull(entity.level().getServer()).getPlayerList()
                    .broadcastSystemMessage(Component.literal(entity.getDisplayName().getString() + " has left the game")
                            .withStyle(ChatFormatting.YELLOW), false);
        }
    }
}
