package com.pla.annoyinglib.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class AnnoyingLibConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static ForgeConfigSpec.BooleanValue FORCE_TICK_MOBS;
    public static ForgeConfigSpec.BooleanValue NPC_PREFIX;
    public static ForgeConfigSpec.BooleanValue REMOTE_NPC_DEPARTURE_ENABLED;
    public static ForgeConfigSpec.IntValue REMOTE_NPC_DEPARTURE_MIN_MINUTES;
    public static ForgeConfigSpec.IntValue REMOTE_NPC_DEPARTURE_MAX_MINUTES;

    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MIN_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MAX_TIME;
    
    
    
    
    
    
    public static ForgeConfigSpec.ConfigValue<Boolean> TURN_ON_NPC_CHAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> TURN_ON_NPC_VOICE;
    public static ForgeConfigSpec.ConfigValue<Boolean> AV_MOB_CAN_BURN_ITEM;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WEAPON_DISARMS_AFFECTED_ENTITY_TYPES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WEAPON_DISARMS_BLACKLIST;

    static {
        FORCE_TICK_MOBS = BUILDER.comment(
                        "Keep ForceTickEntity mobs ticking remotely (Herobrine mobs, Null weapons, Blue Demon, BBQ and Jev).",
                        "Disabling releases their runtime chunk tickets; player-like Steve/Alex/Chris sessions have separate ownership.")
                .define("forceTickMobs", true);

        NPC_PREFIX = BUILDER.comment(
                        "Show the gray [NPC] prefix for Steve, Alex and Chris in the multiplayer player list.",
                        "Disabled by default so these NPCs look like normal players in the list.")
                .define("npcPrefix", false);

        BUILDER.push("remoteNpcDeparture");
        REMOTE_NPC_DEPARTURE_ENABLED = BUILDER.comment("Allow force-ticked NPCs to leave when no external chunk loader covers them.")
                .define("enabled", true);
        REMOTE_NPC_DEPARTURE_MIN_MINUTES = BUILDER.comment("Minimum unattended online simulation time in minutes. Counts only while this NPC owns a force ticket.")
                .defineInRange("minMinutes", 10, 1, 10080);
        REMOTE_NPC_DEPARTURE_MAX_MINUTES = BUILDER.comment("Maximum unattended time in minutes. External loading resets the timer; reversed bounds are normalized.")
                .defineInRange("maxMinutes", 30, 1, 10080);
        BUILDER.pop();

        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "[ONLY WORK WHEN SmartNpc is installed] Chance for Herobrine possess another player npc into Low Herobrine Clone")
                .defineInRange("herobrinePossessRate", 0.5, 0, 1);

        HEROBRINE_RECALL_MIN_TIME = BUILDER.comment(
                        "The minimum value (in minutes) for Herobrine's random recall time. This value should be lower than or equal the maximum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMinTime", 60, 1, 10080);

        HEROBRINE_RECALL_MAX_TIME = BUILDER.comment(
                        "The maximum value (in minutes) for Herobrine's random recall time. This value should be greater than or equal to the minimum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMaxTime", 300, 1, 10080);

        

        

        

        

        

        

        TURN_ON_NPC_CHAT = BUILDER.comment(
                        "Turn on all chatting for NPC")
                .define("turnOnNpcChat", true);

        TURN_ON_NPC_VOICE = BUILDER.comment(
                        "Turn on all voice for NPC")
                .define("turnOnNpcVoice", true);

        AV_MOB_CAN_BURN_ITEM = BUILDER.comment(
                        "Enable burning items ability for all of Av NPCs and Mobs")
                .define("AvMobCanBurnItem", true);

        WEAPON_DISARMS_AFFECTED_ENTITY_TYPES = BUILDER.comment(
                        "Living entity types whose held weapons can be disarmed")
                .defineListAllowEmpty(
                        "affectedEntityTypes",
                        List.of(
                                "minecraft:player",
                                "minecraft:zombie",
                                "minecraft:skeleton",
                                "smart_npc:player_npc",
                                "herobrine_vessels:low_herobrine_clone",
                                "herobrine_vessels:low_shadow_herobrine_clone",
                                "villager_kingdom:villager_scout",
                                "villager_kingdom:villager_scout_captain",
                                "villager_kingdom:blue_villager_knight",
                                "villager_kingdom:green_villager_knight",
                                "villager_kingdom:red_villager_knight",
                                "villager_kingdom:purple_villager_knight"
                        ),
                        AnnoyingLibConfig::validResourceOrTagOrNamespace
                );

        WEAPON_DISARMS_BLACKLIST = BUILDER.comment(
                        "Weapons/items that cannot be disarmed")
                .defineListAllowEmpty(
                        "weaponBlacklist",
                        List.of(
                                "minecraft:wooden_sword",
                                "minecraft:wooden_shovel",
                                "minecraft:wooden_pickaxe",
                                "minecraft:wooden_axe",
                                "minecraft:wooden_hoe",
                                "minecraft:stone_sword",
                                "minecraft:stone_shovel",
                                "minecraft:stone_pickaxe",
                                "minecraft:stone_axe",
                                "minecraft:stone_hoe",
                                "legendary_npcs:legendary_sword",
                                "herobrine_vessels:demoniac_voltage_reaver",
                                "herobrine_vessels:shadow_obsidian_weapon",
                                "herobrine_vessels:obsidian_weapon",
                                "herobrine_vessels:bedrock_weapon",
                                "legendary_npcs:crafting_table",
                                "legendary_npcs:wooden_door",
                                "legendary_npcs:ladder",
                                "legendary_npcs:trapdoor",
                                "herobrine_vessels:null_weapon",
                                "herobrine_vessels:shadow_obsidian_pillar",
                                "herobrine_vessels:ender_glaive",
                                "herobrine_vessels:ender_slayer_scythe",
                                "herobrine_vessels:demoniac_voltage_reaver",
                                "herobrine_vessels:obsidian_sledgehammer",
                                "herobrine_vessels:shadow_obsidian_sword",
                                "herobrine_vessels:warden_axe",
                                "mob_supremacy:blue_demon_trident",
                                "the_creators:darkness_sword",
                                "mob_supremacy:red_steel_axe",
                                "mob_supremacy:holy_llama_hammer",
                                "legendary_npcs:woopie_the_sword",
                                "annoyinglib:jessica_the_dark_shield"
                        ),
                        AnnoyingLibConfig::validResourceOrTagOrNamespace
                );
        SPEC = BUILDER.build();
    }

    private static boolean validResourceOrTagOrNamespace(Object object) {
        if (!(object instanceof String value)) {
            return false;
        }

        if (value.isBlank()) {
            return false;
        }

        if (value.startsWith("#")) {
            return ResourceLocation.tryParse(value.substring(1)) != null;
        }

        if (value.endsWith(":*")) {
            String namespace = value.substring(0, value.length() - 2);
            return ResourceLocation.isValidNamespace(namespace);
        }

        if (!value.contains(":")) {
            return ResourceLocation.isValidNamespace(value);
        }

        return ResourceLocation.tryParse(value) != null;
    }
}
