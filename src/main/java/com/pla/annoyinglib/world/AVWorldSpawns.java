package com.pla.annoyinglib.world;

import com.pla.annoyinglib.AnnoyingLib;
import com.pla.annoyinglib.config.AnnoyingLibSpawnConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AVWorldSpawns {
    private static final Logger LOGGER = LogManager.getLogger();

    private AVWorldSpawns() {}
    public static void addBiomeSpawns(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        for (AnnoyingLibSpawnConfig.Entry entry : AnnoyingLibSpawnConfig.ENTRIES) {
            AnnoyingLibSpawnConfig.SpawnConfig spawnConfig = AnnoyingLibSpawnConfig.getSpawnConfig(entry.entityId());
            addSpawn(builder, ResourceLocation.fromNamespaceAndPath(AnnoyingLib.MODID, entry.entityId()), spawnConfig);
        }
    }

    private static void addSpawn(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                                 ResourceLocation entityId,
                                 AnnoyingLibSpawnConfig.SpawnConfig spawnConfig) {

        if (spawnConfig.weight() <= 0) return;
        EntityType<?> rawType = ForgeRegistries.ENTITY_TYPES.getValue(entityId);
        if (rawType == null) {
            LOGGER.warn("Spawn config refers to missing entity type: {}", entityId);
            return;
        }

        @SuppressWarnings("unchecked")
        EntityType<? extends Mob> mobType = (EntityType<? extends Mob>) rawType;

        builder.getMobSpawnSettings()
                .getSpawner(mobType.getCategory())
                .add(new MobSpawnSettings.SpawnerData(mobType, spawnConfig.weight(), spawnConfig.minCount(), spawnConfig.maxCount()));
    }
}
