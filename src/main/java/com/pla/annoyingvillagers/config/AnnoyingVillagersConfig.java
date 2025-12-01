package com.pla.annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> HEROBRINE_POSSESS_RATE;
    public static ForgeConfigSpec.ConfigValue<Boolean> PHYSIC_MOD_COMPAT;
    public static ForgeConfigSpec.ConfigValue<Boolean> HEROBRINE_OBSIDIAN_BREAK_ARMOR;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXECUTION_PLAYER;
    public static ForgeConfigSpec.ConfigValue<Boolean> EXECUTION_NPC;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MIN_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_RECALL_MAX_TIME;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_HEALING_MIN_COOLDOWN;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_HEALING_MAX_COOLDOWN;
    public static ForgeConfigSpec.ConfigValue<Integer> HEROBRINE_HEALING_HEALTH_TRIGGER;

    static {
        HEROBRINE_POSSESS_RATE = BUILDER.comment(
                        "Chance for Herobrine possess another player npc into Herobrine #5 and Herobrine #6")
                .defineInRange("herobrinePossessRate", 0.5, 0, 1);
        PHYSIC_MOD_COMPAT = BUILDER.comment(
                        "Spawn dead body for the mob on killed",
                        "Install Physic Mod to see the effect")
                .define("physicModCompat", false);
        HEROBRINE_OBSIDIAN_BREAK_ARMOR = BUILDER.comment(
                        "Break your armor on Herobrine's obsidian")
                .define("herobrineObsidianBreakArmor", false);
        EXECUTION_PLAYER = BUILDER.comment(
                        "This mod tweaked the Execution from Resurrection",
                        "Set to false if you want to disable Execution for Player")
                .define("executionPlayer", true);
        EXECUTION_NPC = BUILDER.comment(
                        "Set to false if you want to disable Execution for NPC")
                .define("executionNpc", true);
        HEROBRINE_RECALL_MIN_TIME = BUILDER.comment(
                        "The minimum value (in minutes) for Herobrine's random recall time. This value should be lower than or equal the maximum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMinTime", 60, 1, 10080);
        HEROBRINE_RECALL_MAX_TIME = BUILDER.comment(
                        "The maximum value (in minutes) for Herobrine's random recall time. This value should be greater than or equal to the minimum. " +
                                "After a random time between min and max, Herobrine will vanish and return to the Herobrine dimension.")
                .defineInRange("herobrineRecallMaxTime", 300, 1, 10080);
        HEROBRINE_HEALING_MIN_COOLDOWN = BUILDER.comment(
                        "The minimum value (in minutes) for Herobrine's healing cooldown time. This value should be lower than or equal the maximum. " +
                                "After a random time between min and max, Herobrine will heal again.")
                .defineInRange("herobrineHealingCooldownMinTime", 5, 1, 10080);
        HEROBRINE_HEALING_MAX_COOLDOWN = BUILDER.comment(
                        "The maximum value (in minutes) for Herobrine's healing cooldown time. This value should be greater than or equal to the minimum. " +
                                "After a random time between min and max, Herobrine will heal again.")
                .defineInRange("herobrineHealingCooldownMaxTime", 5, 1, 10080);
        HEROBRINE_HEALING_HEALTH_TRIGGER = BUILDER.comment(
                        "The threshold (in percent) that determines when Herobrine starts healing. " +
                                "If Herobrine's current health is less than or equal to this percentage of his max health, he will begin healing. " +
                                "If the calculated value (percentage × max health) is less than 10, the threshold will default to 10 instead. " +
                                "This ensures healing works correctly for low-health Herobrine (herobrine_clone, shadow_herobrine_clone, ...).")
                .defineInRange("herobrineHealingHealthTrigger", 30, 5, 80);
        SPEC = BUILDER.build();
    }
}
