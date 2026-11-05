package com.pla.annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public final class BlueDemonTridentOnPlayerHandEvent {
    private static final String TAG_NEXT_STORM_ROLL = "BlueDemonNextStormRoll";
    private static final String TAG_LAST_LIGHTNING_UUID = "BlueDemonLastLightningUUID";

    private static final int STORM_CHECK_INTERVAL = 20;
    private static final double STORM_STRIKE_CHANCE = 0.05D;
    private static final int STRIKE_COOLDOWN_MIN = 60;
    private static final int STRIKE_COOLDOWN_MAX = 120;

    private BlueDemonTridentOnPlayerHandEvent() {
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (false) {
            return;
        }

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ServerLevel serverLevel = player.level();

        if (!isStormAttractorActive(player, serverLevel)) {
            return;
        }

        CompoundTag data = player.getPersistentData();
        long gameTime = serverLevel.getGameTime();
        long nextAllowedRoll = data.getLongOr(TAG_NEXT_STORM_ROLL, 0L);

        if (gameTime < nextAllowedRoll) {
            return;
        }

        data.putLong(TAG_NEXT_STORM_ROLL, gameTime + STORM_CHECK_INTERVAL);

        if (serverLevel.getRandom().nextDouble() > STORM_STRIKE_CHANCE) {
            return;
        }

        summonNaturalLightning(serverLevel, BlockPos.containing(player.getX(), player.getY(), player.getZ()));
        data.putLong(TAG_NEXT_STORM_ROLL, gameTime + Mth.nextInt(serverLevel.getRandom(), STRIKE_COOLDOWN_MIN, STRIKE_COOLDOWN_MAX));
    }

    @SubscribeEvent
    public static void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        LightningBolt lightning = event.getLightning();
        if (alreadyChargedFromThisLightning(player, lightning)) {
            return;
        }

        List<ItemStack> candidates = getChargeCandidates(player);
        if (candidates.isEmpty()) {
            return;
        }

        ItemStack chosen = candidates.get(player.getRandom().nextInt(candidates.size()));
        int gained = Mth.nextInt(player.getRandom(), 2, 4);

        BlueDemonTridentItem.addStormEnergy(chosen, gained);
    }

    private static boolean isStormAttractorActive(ServerPlayer player, ServerLevel serverLevel) {
        if (!player.isAlive() || player.isSpectator()) {
            return false;
        }

        if (!serverLevel.isThundering()) {
            return false;
        }

        BlockPos headPos = BlockPos.containing(player.getX(), player.getY() + player.getBbHeight() + 0.25D, player.getZ());
        if (!serverLevel.canSeeSky(headPos)) {
            return false;
        }

        return BlueDemonTridentItem.isBlueDemonTrident(player.getMainHandItem())
                || BlueDemonTridentItem.isBlueDemonTrident(player.getOffhandItem());
    }

    private static void summonNaturalLightning(ServerLevel serverLevel, BlockPos strikePos) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(serverLevel, net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);
        if (lightning == null) {
            return;
        }

        lightning.snapTo(Vec3.atBottomCenterOf(strikePos));
        lightning.setVisualOnly(false);
        lightning.setDamage(new Random().nextFloat(1.0F, 3.0F));
        serverLevel.addFreshEntity(lightning);
    }

    private static List<ItemStack> getChargeCandidates(ServerPlayer player) {
        List<ItemStack> candidates = new ArrayList<>(2);

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (BlueDemonTridentItem.isBlueDemonTrident(mainHand) && !BlueDemonTridentItem.isFullyCharged(mainHand)) {
            candidates.add(mainHand);
        }

        if (BlueDemonTridentItem.isBlueDemonTrident(offHand) && !BlueDemonTridentItem.isFullyCharged(offHand)) {
            candidates.add(offHand);
        }

        return candidates;
    }

    private static boolean alreadyChargedFromThisLightning(ServerPlayer player, LightningBolt lightning) {
        CompoundTag data = player.getPersistentData();
        UUID lightningUUID = lightning.getUUID();

        if (com.pla.annoyingvillagers.util.LegacyNbt.hasUUID(data, TAG_LAST_LIGHTNING_UUID) && lightningUUID.equals(com.pla.annoyingvillagers.util.LegacyNbt.getUUID(data, TAG_LAST_LIGHTNING_UUID))) {
            return true;
        }

        com.pla.annoyingvillagers.util.LegacyNbt.putUUID(data, TAG_LAST_LIGHTNING_UUID, lightningUUID);
        return false;
    }
}
