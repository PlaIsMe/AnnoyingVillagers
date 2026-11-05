package com.pla.annoyingvillagers.gametest;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.util.LegacyItemData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.List;

/** Opt-in render smoke scene in the copied GameTest world; never included in release jars. */
@EventBusSubscriber(modid = AnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class PortClientSmoke {
    private static int ticks;

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        if (!Boolean.getBoolean("annoyingvillagers.portClientSmoke")) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.getSingleplayerServer() == null) return;
        ticks++;
        if (ticks == 1) {
            mc.setScreen(null);
            mc.options.pauseOnLostFocus = false;
            mc.getSingleplayerServer().execute(() -> {
                var server = mc.getSingleplayerServer();
                var level = server.overworld();
                var player = server.getPlayerList().getPlayers().getFirst();
                player.setGameMode(GameType.CREATIVE);
                for (int x = -18; x <= 18; x++) {
                    for (int z = -4; z <= 36; z++) level.setBlockAndUpdate(new BlockPos(x, 63, z), Blocks.STONE.defaultBlockState());
                }
                level.setDayTime(6000);
                player.connection.teleport(0.5, 64, 0.5, 0, 0);
                List<String> types = List.of("steve", "herobrine_clone", "blue_demon", "armored_herobrine", "reaper_herobrine", "villager_scout", "av_warden", "null", "bbq");
                for (int i = 0; i < types.size(); i++) {
                    var type = BuiltInRegistries.ENTITY_TYPE.getValue(Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, types.get(i)));
                    if (type == null) throw new IllegalStateException("Missing smoke entity " + types.get(i));
                    var entity = type.create(level, EntitySpawnReason.COMMAND);
                    entity.setPos((i % 3 - 1) * 5.0 + 0.5, 64, 8 + (i / 3) * 8.0);
                    if (entity instanceof Mob mob) {
                        mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()), EntitySpawnReason.COMMAND, null);
                        mob.setNoAi(true);
                    }
                    level.addFreshEntity(entity);
                }
                ItemStack weapon = new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get());
                LegacyItemData.update(weapon, tag -> tag.putBoolean("SecondForm", true));
                player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, weapon);
                AnnoyingVillagers.LOGGER.info("Port client smoke scene ready");
            });
        }
        if (ticks == 40 || ticks == 80 || ticks == 120 || ticks == 160) {
            int phase = ticks;
            mc.getSingleplayerServer().execute(() -> {
                var player = mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();
                if (phase == 40) {
                    ItemStack weapon = new ItemStack(AnnoyingVillagersModItems.ENDER_GLAIVE.get());
                    player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, weapon);
                    weapon.getItem().use(player.level(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
                } else if (phase == 80) {
                    ItemStack shield = new ItemStack(AnnoyingVillagersModItems.ENDER_AEGIS.get());
                    player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, shield);
                    shield.getItem().use(player.level(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
                } else if (phase == 120) {
                    player.stopUsingItem();
                    player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, new ItemStack(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get()));
                    com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem.activateVanillaSpecial(player);
                } else {
                    player.getMainHandItem().getItem().use(player.level(), player, net.minecraft.world.InteractionHand.MAIN_HAND);
                }
                AnnoyingVillagers.LOGGER.info("Port client ability smoke phase {}", phase);
            });
        }
        if (ticks == 100) {
            mc.level.addParticle(com.pla.annoyingvillagers.init.AnnoyingVillagersModParticleTypes.GROUND_SLAM.get(), 2.5, 64, 4, 1, 10, 0.5);
            mc.getSingleplayerServer().execute(() -> {
                var level = mc.getSingleplayerServer().overworld();
                var cow = net.minecraft.world.entity.EntityType.COW.create(level, EntitySpawnReason.COMMAND);
                cow.setPos(2.5, 64, 4);
                cow.setNoAi(true);
                cow.igniteForSeconds(10);
                level.addFreshEntity(cow);
                cow.setData(com.pla.annoyingvillagers.init.AnnoyingVillagersModCapabilities.END_FIRE, true);
            });
        }
        if (ticks == 200) {
            Screenshot.grab(mc.gameDirectory, "port-smoke.png", mc.getMainRenderTarget(), 1, message -> AnnoyingVillagers.LOGGER.info("Port client screenshot: {}", message.getString()));
            AnnoyingVillagers.LOGGER.info("Port client smoke completed 200 ticks");
        }
        if (ticks == 240) mc.stop();
    }
}
