package com.pla.annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.Difficulty;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;

public final class ProgressionUtil {
    private ProgressionUtil() {
    }

    public static boolean isDifficulty(Difficulty difficulty) {
        MinecraftServer server = net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        return server != null && isDifficulty(server, difficulty);
    }

    public static boolean isDifficulty(MinecraftServer server, Difficulty difficulty) {
        return getDifficulty(server) == difficulty;
    }

    public static boolean isAtLeastDifficulty(Difficulty difficulty) {
        MinecraftServer server = net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer();
        return server != null && isAtLeastDifficulty(server, difficulty);
    }

    public static boolean isAtLeastDifficulty(MinecraftServer server, Difficulty difficulty) {
        return getDifficulty(server).ordinal() >= difficulty.ordinal();
    }

    public static Difficulty getDifficulty(MinecraftServer server) {
        return ProgressionData.get(server).getDifficulty();
    }

    public static boolean setDifficulty(MinecraftServer server, Difficulty difficulty) {
        return ProgressionData.get(server).setDifficulty(difficulty);
    }

    public static void increaseDifficulty(MinecraftServer server, Difficulty difficulty) {
        ProgressionData.get(server).increaseDifficulty(difficulty);
    }

    private static void increaseHistoricalDifficulty(MinecraftServer server, Difficulty difficulty) {
        ProgressionData data = ProgressionData.get(server);
        if (!data.isManualDifficulty()) {
            data.increaseDifficulty(difficulty);
        }
    }

    public static void reconcileHistoricalProgression(MinecraftServer server) {
        reconcileDragonFightProgression(server);
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            reconcileHistoricalProgression(player);
        }
    }

    public static void reconcileDragonFightProgression(MinecraftServer server) {
        if (isAtLeastDifficulty(server, Difficulty.HARD)) {
            return;
        }

        ServerLevel end = server.getLevel(Level.END);
        if (end != null) {
            EndDragonFight dragonFight = end.getDragonFight();
            if (dragonFight != null && dragonFight.hasPreviouslyKilledDragon()) {
                increaseHistoricalDifficulty(server, Difficulty.HARD);
            }
        }
    }

    public static void reconcileHistoricalProgression(ServerPlayer player) {
        MinecraftServer server = player.server;
        if (player.level().dimension() != Level.OVERWORLD
                || hasAdvancement(player, "minecraft:story/enter_the_nether")
                || hasAdvancement(player, "minecraft:end/root")) {
            increaseHistoricalDifficulty(server, Difficulty.MEDIUM);
        }

        if (hasAdvancement(player, "minecraft:end/kill_dragon")) {
            increaseHistoricalDifficulty(server, Difficulty.HARD);
        }
    }

    private static boolean hasAdvancement(ServerPlayer player, String id) {
        ResourceLocation resourceLocation = ResourceLocation.tryParse(id);
        if (resourceLocation == null) {
            return false;
        }

        Advancement advancement = player.server.getAdvancements().getAdvancement(resourceLocation);
        return advancement != null && player.getAdvancements().getOrStartProgress(advancement).isDone();
    }
}
