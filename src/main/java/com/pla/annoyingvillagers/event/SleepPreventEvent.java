package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.spawnhandler.GregData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.UUID;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SleepPreventEvent {
    @SubscribeEvent
    public static void onPlayerSleep(CanPlayerSleepEvent event) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;

        ServerLevel serverLevel = server.getLevel(Level.OVERWORLD);
        if (serverLevel == null || !serverLevel.dimension().equals(Level.OVERWORLD)) return;

        GregData gregData = GregData.get(serverLevel);
        UUID gregUUID = gregData.getActiveId();

        if (gregUUID != null) {
            Entity entity = serverLevel.getEntity(gregUUID);
            if (entity instanceof HerobrineGregEntity herobrineGregEntity
                    && herobrineGregEntity.isAlive() && herobrineGregEntity.getSummonTimestamp() >= 0) {
                event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
                event.getEntity().displayClientMessage(
                        Component.literal("Herobrine is preparing to invade near x: " + herobrineGregEntity.getOnPos().getX() +
                                 " y: " + herobrineGregEntity.getOnPos().getY() + " z: " + herobrineGregEntity.getOnPos().getZ() + ". You cannot sleep now!").withStyle(ChatFormatting.RED),
                        false
                );
            }
        }
    }
}
