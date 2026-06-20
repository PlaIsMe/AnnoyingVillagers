package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class AnnoyingVillagersSpawnConfig {
    public record SpawnConfig(int weight, int minCount, int maxCount) {}

    public record Entry(
            String entityId,
            String configKey,
            SpawnConfig defaultConfig,
            boolean groupSizeConfigurable,
            String comment
    ) {}

    private static final int WEIGHT_MIN = 0;
    private static final int WEIGHT_MAX = 1000;
    private static final int COUNT_MIN  = 1;
    private static final int COUNT_MAX  = 64;

    public static final List<Entry> ENTRIES = List.of(
            configurableGroupEntry("player_npc", 6, 2, 4, "Player NPC"),
            configurableGroupEntry("villager_scout", 4, 1, 4, "Villager Scout"),
            configurableGroupEntry("villager_scout_captain", 3, 1, 2, "Villager Scout Captain"),
            configurableGroupEntry("purple_villager_general", 3, 1, 2, "Purple Villager General"),
            configurableGroupEntry("red_villager_general", 3, 1, 2, "Red Villager General"),
            configurableGroupEntry("blue_villager_general", 3, 1, 2, "Blue Villager General"),
            configurableGroupEntry("green_villager_general", 3, 1, 2, "Green Villager General"),

            fixedGroupEntry("steve", 1, "Steve"),
            fixedGroupEntry("alex", 1, "Alex"),
            fixedGroupEntry("chris", 1, "Chris"),
            fixedGroupEntry("blue_demon", 1, "Blue Demon"),

            fixedGroupEntry("low_shadow_herobrine_clone", 1, "Netherite Herobrine"),
            fixedGroupEntry("herobrine_clone", 1, "Herobrine Clone"),
            fixedGroupEntry("shadow_herobrine_clone", 1, "Shadow Herobrine Clone"),
            fixedGroupEntry("transporter_herobrine_clone", 1, "Transporter Herobrine Clone"),
            fixedGroupEntry("armored_herobrine", 1, "Armored Herobrine"),
            fixedGroupEntry("herobrine_7", 1, "Herobrine 7"),
            fixedGroupEntry("herobrine_chris", 1, "Herobrine Chris"),
            fixedGroupEntry("herobrine_greg", 1, "Herobrine Greg")
    );

    public static final ForgeConfigSpec SPEC;

    private static final Map<String, Entry> entryByEntityId = new HashMap<>();
    private static final Map<String, ForgeConfigSpec.IntValue> weightValueByEntityId = new HashMap<>();
    private static final Map<String, ForgeConfigSpec.ConfigValue<List<? extends Number>>> tripleValueByEntityId = new HashMap<>();

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        configBuilder.push("spawning");

        for (Entry entry : ENTRIES) {
            entryByEntityId.put(entry.entityId(), entry);

            if (entry.groupSizeConfigurable()) {
                ForgeConfigSpec.ConfigValue<List<? extends Number>> tripleValue = configBuilder
                        .comment(entry.comment())
                        .defineList(entry.configKey(), toDefaultList(entry.defaultConfig()), element -> element instanceof Number);

                tripleValueByEntityId.put(entry.entityId(), tripleValue);
            } else {
                ForgeConfigSpec.IntValue weightValue = configBuilder
                        .comment(entry.comment())
                        .defineInRange(entry.configKey(), entry.defaultConfig().weight(), WEIGHT_MIN, WEIGHT_MAX);

                weightValueByEntityId.put(entry.entityId(), weightValue);
            }
        }

        configBuilder.pop();
        SPEC = configBuilder.build();
    }

    public static SpawnConfig getSpawnConfig(String entityId) {
        Entry entry = entryByEntityId.get(entityId);
        if (entry == null) {
            return new SpawnConfig(0, 1, 1);
        }

        if (!entry.groupSizeConfigurable()) {
            ForgeConfigSpec.IntValue weightValue = weightValueByEntityId.get(entityId);
            int weight = (weightValue != null) ? weightValue.get() : entry.defaultConfig().weight();
            return new SpawnConfig(weight, 1, 1);
        }

        ForgeConfigSpec.ConfigValue<List<? extends Number>> tripleValue = tripleValueByEntityId.get(entityId);
        if (tripleValue == null) {
            return entry.defaultConfig();
        }

        return parseTripleOrDefault(tripleValue.get(), entry.defaultConfig());
    }

    private static SpawnConfig parseTripleOrDefault(List<? extends Number> rawValues, SpawnConfig defaultConfig) {
        if (rawValues == null || rawValues.size() != 3) {
            return defaultConfig;
        }

        Integer parsedWeight   = toExactInteger(rawValues.get(0));
        Integer parsedMinCount = toExactInteger(rawValues.get(1));
        Integer parsedMaxCount = toExactInteger(rawValues.get(2));

        if (parsedWeight == null || parsedMinCount == null || parsedMaxCount == null) {
            return defaultConfig;
        }

        if (parsedWeight < WEIGHT_MIN || parsedWeight > WEIGHT_MAX) {
            return defaultConfig;
        }
        if (parsedMinCount < COUNT_MIN || parsedMinCount > COUNT_MAX) {
            return defaultConfig;
        }
        if (parsedMaxCount < COUNT_MIN || parsedMaxCount > COUNT_MAX) {
            return defaultConfig;
        }
        if (parsedMaxCount < parsedMinCount) {
            return defaultConfig;
        }

        return new SpawnConfig(parsedWeight, parsedMinCount, parsedMaxCount);
    }

    private static Integer toExactInteger(Number number) {
        if (number == null) return null;

        double valueAsDouble = number.doubleValue();
        long roundedValue = Math.round(valueAsDouble);

        if (Math.abs(valueAsDouble - roundedValue) > 1e-9) return null;
        if (roundedValue < Integer.MIN_VALUE || roundedValue > Integer.MAX_VALUE) return null;

        return (int) roundedValue;
    }

    private static List<? extends Number> toDefaultList(SpawnConfig defaultConfig) {
        return List.of(
                defaultConfig.weight(),
                defaultConfig.minCount(),
                defaultConfig.maxCount()
        );
    }

    private static Entry fixedGroupEntry(String entityId, int defaultWeight, String name) {
        String configKey = "spawn_" + entityId;

        String comment = String.format(
                Locale.ROOT,
                "Spawn config for %s. Format: weight (int). Weight is added to the spawn pool in each biome. Group size is fixed at 1 and is NOT configurable",
                name
        );

        return new Entry(
                entityId,
                configKey,
                new SpawnConfig(defaultWeight, 1, 1),
                false,
                comment
        );
    }

    private static Entry configurableGroupEntry(String entityId, int defaultWeight, int defaultMinCount, int defaultMaxCount, String name) {
        String configKey = "spawn_" + entityId;

        String comment = String.format(
                Locale.ROOT,
                "Spawn config for %s. Format: [weight, minCount, maxCount]. Weight is added to the spawn pool in each biome (higher = more common, 0 = disable). minCount/maxCount control group size",
                name
        );

        return new Entry(
                entityId,
                configKey,
                new SpawnConfig(defaultWeight, defaultMinCount, defaultMaxCount),
                true,
                comment
        );
    }
}
