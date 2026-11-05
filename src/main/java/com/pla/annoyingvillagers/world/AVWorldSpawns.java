package com.pla.annoyingvillagers.world;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.config.AnnoyingVillagersSpawnConfig;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AVWorldSpawns {
    private static final Logger LOGGER = LogManager.getLogger();

    private AVWorldSpawns() {}
    public static void addBiomeSpawns(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        for (AnnoyingVillagersSpawnConfig.Entry entry : AnnoyingVillagersSpawnConfig.ENTRIES) {
            AnnoyingVillagersSpawnConfig.SpawnConfig spawnConfig = AnnoyingVillagersSpawnConfig.getSpawnConfig(entry.entityId());
            addSpawn(builder, Identifier.fromNamespaceAndPath(AnnoyingVillagers.MODID, entry.entityId()), spawnConfig);
        }
    }

    private static void addSpawn(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                                 Identifier entityId,
                                 AnnoyingVillagersSpawnConfig.SpawnConfig spawnConfig) {

        if (spawnConfig.weight() <= 0) return;
        // This is a defaulted registry: get() returns minecraft:pig for unknown IDs.
        EntityType<?> rawType = BuiltInRegistries.ENTITY_TYPE.getOptional(entityId).orElse(null);
        if (rawType == null) {
            LOGGER.warn("Spawn config refers to missing entity type: {}", entityId);
            return;
        }

        @SuppressWarnings("unchecked")
        EntityType<? extends Mob> mobType = (EntityType<? extends Mob>) rawType;

        builder.getMobSpawnSettings()
                .getSpawner(mobType.getCategory())
                .add(new MobSpawnSettings.SpawnerData(mobType, spawnConfig.minCount(), spawnConfig.maxCount()), spawnConfig.weight());
    }
}
