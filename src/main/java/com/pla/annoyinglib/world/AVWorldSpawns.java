package com.pla.annoyinglib.world;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
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
    private static final Map<String, Consumer<ModifiableBiomeInfo.BiomeInfo.Builder>> SOURCES = new LinkedHashMap<>();

    public static synchronized void registerSource(String modId, Consumer<ModifiableBiomeInfo.BiomeInfo.Builder> source) {
        SOURCES.put(modId, source);
    }

    public static synchronized void addBiomeSpawns(ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        SOURCES.values().forEach(source -> source.accept(builder));
    }

    public static void addSpawn(ModifiableBiomeInfo.BiomeInfo.Builder builder, ResourceLocation entityId, int weight, int minCount, int maxCount) {
        if (weight <= 0) return;
        EntityType<?> rawType = ForgeRegistries.ENTITY_TYPES.getValue(entityId);
        if (rawType == null) {
            LOGGER.warn("Spawn config refers to missing entity type: {}", entityId);
            return;
        }

        @SuppressWarnings("unchecked")
        EntityType<? extends Mob> mobType = (EntityType<? extends Mob>) rawType;

        builder.getMobSpawnSettings()
                .getSpawner(mobType.getCategory())
                .add(new MobSpawnSettings.SpawnerData(mobType, weight, minCount, maxCount));
    }
}
