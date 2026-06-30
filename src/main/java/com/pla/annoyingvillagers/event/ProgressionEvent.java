package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ProgressionEvent {
    private ProgressionEvent() {
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ProgressionUtil.reconcileHistoricalProgression(event.getServer());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.getServer().getTickCount() % 20 == 0) {
            ProgressionUtil.reconcileDragonFightProgression(event.getServer());
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ProgressionUtil.reconcileHistoricalProgression(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ProgressionUtil.increaseDifficulty(player.server, Difficulty.MEDIUM);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon && event.getEntity().level() instanceof ServerLevel level) {
            ProgressionUtil.increaseDifficulty(level.getServer(), Difficulty.HARD);
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof EnderDragon dragon
                && dragon.dragonDeathTime > 0
                && dragon.level() instanceof ServerLevel level) {
            ProgressionUtil.increaseDifficulty(level.getServer(), Difficulty.HARD);
        }
    }
}
