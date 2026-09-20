package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.clazz.Difficulty;
import com.pla.annoyingvillagers.util.ProgressionUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class ProgressionEvent {
    private ProgressionEvent() {
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ProgressionUtil.reconcileHistoricalProgression(event.getServer());
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (true && event.getServer().getTickCount() % 20 == 0) {
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
            ProgressionUtil.increaseDifficulty(player.level().getServer(), Difficulty.MEDIUM);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon && event.getEntity().level() instanceof ServerLevel level) {
            ProgressionUtil.increaseDifficulty(level.getServer(), Difficulty.HARD);
        }
    }

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof EnderDragon dragon
                && dragon.dragonDeathTime > 0
                && dragon.level() instanceof ServerLevel level) {
            ProgressionUtil.increaseDifficulty(level.getServer(), Difficulty.HARD);
        }
    }
}
