package com.pla.annoyingvillagers.init;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnnoyingVillagersModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, AnnoyingVillagers.MODID);

    // Spawn egg
    public static final RegistryObject<Item> BLUE_DEMON_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_DEMON, -16737895, -16777216, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> SHADOW_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("shadow_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> TRANSPORTER_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("transporter_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE, -10066330, -103, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_GREG_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_greg_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_GREG, -3355648, -103, (new Properties())));
    public static final RegistryObject<Item> LOW_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("low_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> LOW_SHADOW_HEROBRINE_CLONE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("low_shadow_herobrine_clone_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE, -10066330, -13421773, (new Properties())));
    public static final RegistryObject<Item> HEROBRINE_7_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("herobrine_7_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.HEROBRINE_7, -1, -1, (new Properties())));
    public static final RegistryObject<Item> NULL_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("null_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.NULL, -16777216, -10066330, (new Properties())));
    public static final RegistryObject<Item> ARMORED_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("armored_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ARMORED_HEROBRINE, -16777216, -1, (new Properties())));
    public static final RegistryObject<Item> DARK_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("shadow_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SHADOW_HEROBRINE, -1, -1, (new Properties())));
    public static final RegistryObject<Item> GLAIVE_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("glaive_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE, 0x330099, 0xFFFFFF, (new Properties())));
    public static final RegistryObject<Item> REAPER_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("reaper_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.REAPER_HEROBRINE, 0x330099, 0xFFFFFF, (new Properties())));
    public static final RegistryObject<Item> SWORDSMAN_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("swordsman_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE, 0x330099, 0xFFFFFF, (new Properties())));
    public static final RegistryObject<Item> AEGIS_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("aegis_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.AEGIS_HEROBRINE, 0x330099, 0xFFFFFF, (new Properties())));
    public static final RegistryObject<Item> SLEDGEHAMMER_HEROBRINE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("sledgehammer_herobrine_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE, 0x330099, 0xFFFFFF, (new Properties())));
    public static final RegistryObject<Item> VILLAGER_SCOUT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT, -205, -26317, (new Properties())));
    public static final RegistryObject<Item> VILLAGER_SCOUT_CAPTAIN_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_captain_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN, 0x00FFFF, 0xFFFFFF, (new Properties())));
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_knight_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT, -16711681, -6710887, (new Properties())));
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("green_villager_knight_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT, -16724992, -1, (new Properties())));
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("red_villager_knight_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT, -3407821, -13421773, (new Properties())));
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_knight_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT, -3407668, -6710887, (new Properties())));
    public static final RegistryObject<Item> STEVE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("steve_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.STEVE, -16724788, -13421569, (new Properties())));
    public static final RegistryObject<Item> ANGRY_STEVE_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("angry_steve_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ANGRY_STEVE, -16724788, -13421569, (new Properties())));
    public static final RegistryObject<Item> ALEX_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("alex_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.ALEX, -3342439, -1  , (new Properties())));
    public static final RegistryObject<Item> JEV_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("jev_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.JEV, 0x996C18, -1, (new Properties())));
    public static final RegistryObject<Item> CHRIS_SPAWN_EGG = AnnoyingVillagersModItems.REGISTRY.register("chris_spawn_egg", () -> new ForgeSpawnEggItem(AnnoyingVillagersModEntities.CHRIS, -16737997, -6711040, (new Properties())));

    // ------------------------------

    // Misc item
    public static final RegistryObject<Item> ENCHANT_BED_ITEM = AnnoyingVillagersModItems.REGISTRY.register("enchant_bed_item", EnchantBedItem::new);
    public static final RegistryObject<Item> ENCHANTED_ARROW = AnnoyingVillagersModItems.REGISTRY.register("enchanted_arrow", () -> new EnchantedArrowItem(new Item.Properties()));
    public static final RegistryObject<Item> ADVANCED_FISHING_ROD = AnnoyingVillagersModItems.REGISTRY.register("advanced_fishing_rod", AdvancedFishingRod::new);
    public static final RegistryObject<Item> TONY_THE_FISHING_ROD = AnnoyingVillagersModItems.REGISTRY.register("tony_the_fishing_rod", TonyTheFishingRod::new);
    public static final RegistryObject<Item> HOOK_GUN = AnnoyingVillagersModItems.REGISTRY.register("hook_gun", HookGunItem::new);
    public static final RegistryObject<Item> TRANSPORTER_FRAGMENT = AnnoyingVillagersModItems.REGISTRY.register("transporter_fragment", TransporterFragmentItem::new);
    public static final RegistryObject<Item> INVENTORY_VIEWER = AnnoyingVillagersModItems.REGISTRY.register("inventory_viewer", InventoryViewerItem::new);
    public static final RegistryObject<Item> VILLAGER_HEAD = AnnoyingVillagersModItems.REGISTRY.register("villager_head", VillagerHeadItem::new);
    public static final RegistryObject<Item> JEV_GLASSES = AnnoyingVillagersModItems.REGISTRY.register("jev_glasses", JevGlassesItem::new);
    public static final RegistryObject<Item> ENCHANTED_ENDER_PEARL = AnnoyingVillagersModItems.REGISTRY.register("enchanted_ender_pearl", EnchantedEnderPearlItem::new);
    public static final RegistryObject<Item> JEV_BOOK = AnnoyingVillagersModItems.REGISTRY.register("jev_book", JevBookItem::new);
    public static final RegistryObject<Item> JEV_PENCIL = AnnoyingVillagersModItems.REGISTRY.register("jev_pencil", JevPencilItem::new);
    public static final RegistryObject<Item> POISON_EGG_ITEM = AnnoyingVillagersModItems.REGISTRY.register("poison_egg", PoisonEggItem::new);
    public static final RegistryObject<Item> ENDER_AEGIS_PROJECTILE = AnnoyingVillagersModItems.REGISTRY.register("ender_aegis_projectile", EnderAegisProjectileItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_BURST = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_burst", ShadowObsidianBurstItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_STRAIGHT = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_straight", ShadowObsidianStraightItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER_BLADE = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver_blade", DemoniacVoltageReaverBladeItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER_FRAGMENT = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver_fragment", DemoniacVoltageReaverFragmentItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER_HILT = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver_hilt", DemoniacVoltageReaverHiltItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian", EliteObsidianItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN_LONG = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian_long", EliteObsidianLongItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN_BIG = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian_big", EliteObsidianBigItem::new);
    public static final RegistryObject<Item> ELITE_OBSIDIAN_BODY = AnnoyingVillagersModItems.REGISTRY.register("elite_obsidian_body", EliteObsidianBodyItem::new);
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_helmet_fix", VillagerScoutHelmetFixItem::new);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("red_villager_knight_helmet_fix", RedVillagerKnightHelmetFixItem::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_knight_helmet_fix", BlueVillagerKnightHelmetFixItem::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("green_villager_knight_helmet_fix", GreenVillagerKnightHelmetFixItem::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_HELMET_FIX = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_knight_helmet_fix", PurpleVillagerKnightHelmetFixItem::new);
    // ------------------------------

    // Gems
    public static final RegistryObject<Item> COMPRESSED_DIAMOND = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond", CompressedDiamondItem::new);
    public static final RegistryObject<Item> RUBY = AnnoyingVillagersModItems.REGISTRY.register("ruby", RubyItem::new);
    public static final RegistryObject<Item> DARK_NETHERITE = AnnoyingVillagersModItems.REGISTRY.register("dark_netherite", DarkNetheriteItem::new);
    public static final RegistryObject<Item> PURPLE_GEM = AnnoyingVillagersModItems.REGISTRY.register("purple_gem", PurpleGemItem::new);
    // ------------------------------

    // Weapon
    public static final RegistryObject<Item> LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("legendary_sword", LegendarySwordItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_weapon", ShadowObsidianWeaponItem::new);
    public static final RegistryObject<Item> OBSIDIAN_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("obsidian_weapon", ObsidianWeaponItem::new);
    public static final RegistryObject<Item> BEDROCK_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("bedrock_weapon", BedrockWeaponItem::new);
    public static final RegistryObject<Item> CRAFTING_TABLE = AnnoyingVillagersModItems.REGISTRY.register("crafting_table", CraftingTableItem::new);
    public static final RegistryObject<Item> WOODEN_DOOR = AnnoyingVillagersModItems.REGISTRY.register("wooden_door", WoodenDoorItem::new);
    public static final RegistryObject<Item> LADDER = AnnoyingVillagersModItems.REGISTRY.register("ladder", LadderItem::new);
    public static final RegistryObject<Item> TRAPDOOR = AnnoyingVillagersModItems.REGISTRY.register("trapdoor", TrapdoorItem::new);
    public static final RegistryObject<Item> NULL_WEAPON = AnnoyingVillagersModItems.REGISTRY.register("null_weapon", NullWeaponItem::new);
    public static final RegistryObject<Item> NULL_SWORD = AnnoyingVillagersModItems.REGISTRY.register("null_sword", NullSwordItem::new);
    public static final RegistryObject<Item> NULL_AXE = AnnoyingVillagersModItems.REGISTRY.register("null_axe", NullAxeItem::new);
    public static final RegistryObject<Item> NULL_PICKAXE = AnnoyingVillagersModItems.REGISTRY.register("null_pickaxe", NullPickaxeItem::new);
    public static final RegistryObject<Item> NULL_SHOVEL = AnnoyingVillagersModItems.REGISTRY.register("null_shovel", NullShovelItem::new);
    public static final RegistryObject<Item> NULL_HOE = AnnoyingVillagersModItems.REGISTRY.register("null_hoe", NullHoeItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_PILLAR = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_pillar", ShadowObsidianPillarItem::new);
    public static final RegistryObject<Item> ENDER_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("ender_glaive", EnderGlaiveItem::new);
    public static final RegistryObject<Item> ENDER_SLAYER_SCYTHE = AnnoyingVillagersModItems.REGISTRY.register("ender_slayer_scythe", EnderSlayerScytheItem::new);
    public static final RegistryObject<Item> DEMONIAC_VOLTAGE_REAVER = AnnoyingVillagersModItems.REGISTRY.register("demoniac_voltage_reaver", DemoniacVoltageReaverItem::new);
    public static final RegistryObject<Item> OBSIDIAN_SLEDGEHAMMER = AnnoyingVillagersModItems.REGISTRY.register("obsidian_sledgehammer", ObsidianSledgehammerItem::new);
    public static final RegistryObject<Item> ENDER_AEGIS = AnnoyingVillagersModItems.REGISTRY.register("ender_aegis", EnderAegisItem::new);
    public static final RegistryObject<Item> HEROBRINE_ENDER_EYE = AnnoyingVillagersModItems.REGISTRY.register("herobrine_ender_eye", HerobrineEnderEyeItem::new);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("shadow_obsidian_sword", ShadowObsidianSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_CLAW = AnnoyingVillagersModItems.REGISTRY.register("diamond_claw", DiamondClawItem::new);
    public static final RegistryObject<Item> WARDEN_AXE = AnnoyingVillagersModItems.REGISTRY.register("warden_axe", WardenAxeItem::new);
    public static final RegistryObject<Item> BLUE_DEMON_TRIDENT = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_trident", BlueDemonTridentItem::new);
    public static final RegistryObject<Item> DARKNESS_SWORD = AnnoyingVillagersModItems.REGISTRY.register("darkness_sword", DarknessSwordItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE = AnnoyingVillagersModItems.REGISTRY.register("red_steel_axe", RedSteelAxeItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_FORKED = AnnoyingVillagersModItems.REGISTRY.register("red_steel_axe_forked", RedSteelAxeForkedItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_SPEAR_SHORT = AnnoyingVillagersModItems.REGISTRY.register("red_steel_axe_spear_short", RedSteelAxeSpearShortItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_SPEAR_MIDDLE = AnnoyingVillagersModItems.REGISTRY.register("red_steel_axe_spear_middle", RedSteelAxeSpearMiddleItem::new);
    public static final RegistryObject<Item> RED_STEEL_AXE_SPEAR_LONG = AnnoyingVillagersModItems.REGISTRY.register("red_steel_axe_spear_long", RedSteelAxeSpearLongItem::new);
    public static final RegistryObject<Item> HOLY_LLAMA_HAMMER = AnnoyingVillagersModItems.REGISTRY.register("holy_llama_hammer", HolyLlamaHammerItem::new);
    public static final RegistryObject<Item> BLACK_FIRE_SWORD = AnnoyingVillagersModItems.REGISTRY.register("black_fire_sword", BlackFireSwordItem::new);
    public static final RegistryObject<Item> BLUE_FLAME_SWORD = AnnoyingVillagersModItems.REGISTRY.register("blue_flame_sword", BlueFlameSwordItem::new);
    public static final RegistryObject<Item> CENTRANOS_SWORD = AnnoyingVillagersModItems.REGISTRY.register("centranos_sword", CentranosSwordItem::new);
    public static final RegistryObject<Item> CLOW_SWORD = AnnoyingVillagersModItems.REGISTRY.register("clow_sword", ClowSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_ATTRACTOR_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_attractor_sword", DiamondAttractorSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_BLASTER_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_blaster_sword", DiamondBlasterSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_BLASTER_SWORD_ABILITY = AnnoyingVillagersModItems.REGISTRY.register("diamond_blaster_sword_ability", DiamondBlasterSwordAbilityItem::new);
    public static final RegistryObject<Item> HACKER_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hacker_sword", HackerSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_FALCHION = AnnoyingVillagersModItems.REGISTRY.register("diamond_falchion", DiamondFalchionItem::new);
    public static final RegistryObject<Item> DIAMOND_WARBLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_warblade", DiamondWarbladeItem::new);
    public static final RegistryObject<Item> DIAMOND_GREAT_FALCHION = AnnoyingVillagersModItems.REGISTRY.register("diamond_great_falchion", DiamondGreatFalchionItem::new);
    public static final RegistryObject<Item> DIAMOND_SABRE = AnnoyingVillagersModItems.REGISTRY.register("diamond_sabre", DiamondSabreItem::new);
    public static final RegistryObject<Item> HOOKED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_diamond_sword", HookedDiamondSwordItem::new);
    public static final RegistryObject<Item> HOOKED_IRON_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_iron_sword", HookedIronSwordItem::new);
    public static final RegistryObject<Item> HOOKED_GOLDEN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("hooked_golden_sword", HookedGoldenSwordItem::new);
    public static final RegistryObject<Item> IRON_CLEAVER = AnnoyingVillagersModItems.REGISTRY.register("iron_cleaver", IronCleaverItem::new);
    public static final RegistryObject<Item> DIAMOND_LAEVATEINN = AnnoyingVillagersModItems.REGISTRY.register("diamond_laevateinn", DiamondLaevateinnItem::new);
    public static final RegistryObject<Item> DIAMOND_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_longsword", DiamondLongSwordItem::new);
    public static final RegistryObject<Item> GOLDEN_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("golden_longsword", GoldenLongSwordItem::new);
    public static final RegistryObject<Item> IRON_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("iron_longsword", IronLongSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_CHIPPED_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_chipped_longsword", DiamondChippedLongswordItem::new);
    public static final RegistryObject<Item> DIAMOND_GREATSWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_greatsword", DiamondGreatswordItem::new);
    public static final RegistryObject<Item> DNAX_HOOKED_SWORD = AnnoyingVillagersModItems.REGISTRY.register("dnax_hooked_sword", DNAxHookedSwordItem::new);
    public static final RegistryObject<Item> DNAX_HOOKED_SWORD_ABILITY = AnnoyingVillagersModItems.REGISTRY.register("dnax_hooked_sword_ability", DNAxHookedSwordAbilityItem::new);
    public static final RegistryObject<Item> FLANKER_HOOKED_SWORD = AnnoyingVillagersModItems.REGISTRY.register("flanker_hooked_sword", FlankerHookedSwordItem::new);
    public static final RegistryObject<Item> GREAT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("great_sword", GreatSwordItem::new);
    public static final RegistryObject<Item> GREAT_SWORD_SKILL = AnnoyingVillagersModItems.REGISTRY.register("great_sword_skill", GreatSwordSkillItem::new);
    public static final RegistryObject<Item> IRON_TWIN_BLADE_KATANA = AnnoyingVillagersModItems.REGISTRY.register("iron_twin_blade_katana", IronTwinBladeKatanaItem::new);
    public static final RegistryObject<Item> PALADIN_SWORD = AnnoyingVillagersModItems.REGISTRY.register("paladin_sword", PaladinSwordItem::new);
    public static final RegistryObject<Item> RUBY_GREATSWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_greatsword", RubyGreatswordItem::new);
    public static final RegistryObject<Item> RUBY_LONGSWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_longsword", PurpleGemLongSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_KNIGHT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("diamond_knight_sword", DiamondKnightSwordItem::new);
    public static final RegistryObject<Item> RUBY_KNIGHT_SWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_knight_sword", RubyKnightSwordItem::new);
    public static final RegistryObject<Item> RUBY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("ruby_sword", RubySwordItem::new);
    public static final RegistryObject<Item> THUNDER_DIAMOND_BLADE = AnnoyingVillagersModItems.REGISTRY.register("thunder_diamond_blade", ThunderDiamondBladeItem::new);
    public static final RegistryObject<Item> JADE_SWORD = AnnoyingVillagersModItems.REGISTRY.register("jade_sword", JadeSwordItem::new);
    public static final RegistryObject<Item> RED_DIAMOND_SWORD = AnnoyingVillagersModItems.REGISTRY.register("red_diamond_sword", RedDiamondSwordItem::new);
    public static final RegistryObject<Item> WOOPIE_THE_SWORD = AnnoyingVillagersModItems.REGISTRY.register("woopie_the_sword", WoopieTheSwordItem::new);
    public static final RegistryObject<Item> NETHERITE_SABRE = AnnoyingVillagersModItems.REGISTRY.register("netherite_sabre", NetheriteSabreItem::new);
    public static final RegistryObject<Item> NETHERITE_FALCHION = AnnoyingVillagersModItems.REGISTRY.register("netherite_falchion", NetheriteFalchionItem::new);
    public static final RegistryObject<Item> DIAMOND_HALBERD = AnnoyingVillagersModItems.REGISTRY.register("diamond_halberd", DiamondHalberdItem::new);
    public static final RegistryObject<Item> DIAMOND_GREATAXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_greataxe", DiamondGreataxeItem::new);
    public static final RegistryObject<Item> DIAMOND_BATTLEAXE = AnnoyingVillagersModItems.REGISTRY.register("diamond_battleaxe", DiamondBattleaxeItem::new);
    public static final RegistryObject<Item> EARTH_AXE = AnnoyingVillagersModItems.REGISTRY.register("earth_axe", EarthAxeItem::new);
    public static final RegistryObject<Item> EXTERMINATOR_BATTLEAXE_GREEN = AnnoyingVillagersModItems.REGISTRY.register("exterminator_battleaxe_green", ExterminatorBattleaxeGreenItem::new);
    public static final RegistryObject<Item> EXTERMINATOR_BATTLEAXE = AnnoyingVillagersModItems.REGISTRY.register("exterminator_battleaxe", ExterminatorBattleaxeItem::new);
    public static final RegistryObject<Item> GIANT_NETHERITE_AXE = AnnoyingVillagersModItems.REGISTRY.register("giant_netherite_axe", GiantNetheriteAxeItem::new);
    public static final RegistryObject<Item> RED_AXE = AnnoyingVillagersModItems.REGISTRY.register("red_axe", RedAxeItem::new);
    public static final RegistryObject<Item> GIANT_RED_AXE = AnnoyingVillagersModItems.REGISTRY.register("giant_red_axe", GiantRedAxeItem::new);
    public static final RegistryObject<Item> IRON_DOUBLE_BLADED_HALBERD = AnnoyingVillagersModItems.REGISTRY.register("iron_double_bladed_halberd", IronDoubleBladedHalberdItem::new);
    public static final RegistryObject<Item> IRON_GREATAXE = AnnoyingVillagersModItems.REGISTRY.register("iron_greataxe", IronGreataxeItem::new);
    public static final RegistryObject<Item> IRON_HALBERD = AnnoyingVillagersModItems.REGISTRY.register("iron_halberd", IronHalberdItem::new);
    public static final RegistryObject<Item> NETHERITE_GREATAXE = AnnoyingVillagersModItems.REGISTRY.register("netherite_greataxe", NetheriteGreataxeItem::new);
    public static final RegistryObject<Item> SAMANTHA_THE_KILLER_AXE = AnnoyingVillagersModItems.REGISTRY.register("samantha_the_killer_axe", SamanthaTheKillerAxeItem::new);
    public static final RegistryObject<Item> SPEAR_AXE = AnnoyingVillagersModItems.REGISTRY.register("spear_axe", SpearAxeItem::new);
    public static final RegistryObject<Item> DIAMOND_ARMBLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_armblade", DiamondArmbladeItem::new);
    public static final RegistryObject<Item> DIAMOND_KNIFE = AnnoyingVillagersModItems.REGISTRY.register("diamond_knife", DiamondKnifeItem::new);
    public static final RegistryObject<Item> KNIFE = AnnoyingVillagersModItems.REGISTRY.register("knife", KnifeItem::new);
    public static final RegistryObject<Item> GOLDEN_MOON_BLADE = AnnoyingVillagersModItems.REGISTRY.register("golden_moon_blade", GoldenMoonBladeItem::new);
    public static final RegistryObject<Item> DIAMOND_MOON_BLADE = AnnoyingVillagersModItems.REGISTRY.register("diamond_moon_blade", DiamondMoonBladeItem::new);
    public static final RegistryObject<Item> NETHERITE_KNIFE = AnnoyingVillagersModItems.REGISTRY.register("netherite_knife", NetheriteKnifeItem::new);
    public static final RegistryObject<Item> GEM_SHIELD = AnnoyingVillagersModItems.REGISTRY.register("gem_shield", GemShieldItem::new);
    public static final RegistryObject<Item> HEATER_SHIELD = AnnoyingVillagersModItems.REGISTRY.register("heater_shield", HeaterShield::new);
    public static final RegistryObject<Item> JESSICA_THE_DARK_SHIELD = AnnoyingVillagersModItems.REGISTRY.register("jessica_the_dark_shield", JessicaTheDarkShieldItem::new);
    public static final RegistryObject<Item> NETHERITE_SHIELD = AnnoyingVillagersModItems.REGISTRY.register("netherite_shield", NetheriteShield::new);
    public static final RegistryObject<Item> BLACKSCRATCHER = AnnoyingVillagersModItems.REGISTRY.register("blackscratcher", BlackscratcherItem::new);
    public static final RegistryObject<Item> BLACKSCRATCHER_TOP = AnnoyingVillagersModItems.REGISTRY.register("blackscratcher_top", BlackscratcherTopItem::new);
    public static final RegistryObject<Item> BLACKSCRATCHER_BOTTOM = AnnoyingVillagersModItems.REGISTRY.register("blackscratcher_bottom", BlackscratcherBottomItem::new);
    public static final RegistryObject<Item> DIAMOND_BOLT = AnnoyingVillagersModItems.REGISTRY.register("diamond_bolt", DiamondBoltItem::new);
    public static final RegistryObject<Item> DIAMOND_SICKLE = AnnoyingVillagersModItems.REGISTRY.register("diamond_sickle", DiamondSickleItem::new);
    public static final RegistryObject<Item> DIAMOND_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("diamond_spear", DiamondSpearItem::new);
    public static final RegistryObject<Item> DOUBLE_DIAMOND_GLAIVE = AnnoyingVillagersModItems.REGISTRY.register("double_diamond_glaive", DoubleDiamondGlaiveItem::new);
    public static final RegistryObject<Item> IRON_SICKLE = AnnoyingVillagersModItems.REGISTRY.register("iron_sickle", IronSickleItem::new);
    public static final RegistryObject<Item> NETHERITE_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("netherite_spear", NetheriteSpearItem::new);
    public static final RegistryObject<Item> TWIN_DIAMOND_SPEAR = AnnoyingVillagersModItems.REGISTRY.register("twin_diamond_spear", TwinDiamondSpearItem::new);
    public static final RegistryObject<Item> GOLDEN_MACE = AnnoyingVillagersModItems.REGISTRY.register("golden_mace", GoldenMaceItem::new);
    public static final RegistryObject<Item> DIAMOND_MACE = AnnoyingVillagersModItems.REGISTRY.register("diamond_mace", DiamondMaceItem::new);
    // ------------------------------


    // Item not shown in creative tab
    public static final RegistryObject<Item> ENCHANT_BED = block(AnnoyingVillagersModBlocks.ENCHANT_BED, null);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_SHORT_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_SHORT_PILLAR, null);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_MIDDLE_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_MIDDLE_PILLAR, null);
    public static final RegistryObject<Item> SHADOW_OBSIDIAN_LONG_PILLAR = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_LONG_PILLAR, null);
    public static final RegistryObject<Item> HEAVY_ATTACK_LEGENDARY_SWORD = AnnoyingVillagersModItems.REGISTRY.register("heavy_attack_legendary_sword", HeavyAttackLegendarySwordItem::new);

    public static final RegistryObject<Item> SHADOW_OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.SHADOW_OBSIDIAN_BLOCK, null);
    public static final RegistryObject<Item> OBSIDIAN_ITEM = block(AnnoyingVillagersModBlocks.OBSIDIAN_BLOCK, null);
    // ------------------------------

    // Armor
    public static final RegistryObject<Item> BLUE_DEMON_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_demon_chestplate", BlueDemonChestplateItem.Chestplate::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_helmet", CompressedDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_chestplate", CompressedDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_leggings", CompressedDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> COMPRESSED_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("compressed_diamond_boots", CompressedDiamondArmorItem.Boots::new);
    public static final RegistryObject<Item> RUBY_HELMET = AnnoyingVillagersModItems.REGISTRY.register("ruby_helmet", RubyArmorItem.Helmet::new);
    public static final RegistryObject<Item> RUBY_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("ruby_chestplate", RubyArmorItem.Chestplate::new);
    public static final RegistryObject<Item> RUBY_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("ruby_leggings", RubyArmorItem.Leggings::new);
    public static final RegistryObject<Item> RUBY_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("ruby_boots", RubyArmorItem.Boots::new);
    public static final RegistryObject<Item> EMERALD_HELMET = AnnoyingVillagersModItems.REGISTRY.register("emerald_helmet", EmeraldArmorItem.Helmet::new);
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("emerald_chestplate", EmeraldArmorItem.Chestplate::new);
    public static final RegistryObject<Item> EMERALD_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("emerald_leggings", EmeraldArmorItem.Leggings::new);
    public static final RegistryObject<Item> EMERALD_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("emerald_boots", EmeraldArmorItem.Boots::new);
    public static final RegistryObject<Item> VILLAGER_SCOUT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("villager_scout_helmet", VillagerScoutHelmetItem.Helmet::new);
    public static final RegistryObject<Item> CLASSIC_GOLDEN_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("classic_golden_chestplate", ClassicGoldenSetArmorItem.Chestplate::new);
    public static final RegistryObject<Item> CLASSIC_GOLDEN_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("classic_golden_leggings", ClassicGoldenSetArmorItem.Leggings::new);
    public static final RegistryObject<Item> CLASSIC_GOLDEN_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("classic_golden_boots", ClassicGoldenSetArmorItem.Boots::new);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("red_villager_knight_helmet", RedVillagerKnightArmorItem.Armor::new);
    public static final RegistryObject<Item> RED_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("red_villager_knight_chestplate", RedVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> VILLAGER_KNIGHT_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("villager_knight_leggings", RedVillagerKnightArmorItem.Leggings::new);
    public static final RegistryObject<Item> VILLAGER_KNIGHT_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("villager_knight_boots", RedVillagerKnightArmorItem.Boots::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_knight_helmet", BlueVillagerKnightArmorItem.Helmet::new);
    public static final RegistryObject<Item> BLUE_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("blue_villager_knight_chestplate", BlueVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("green_villager_knight_helmet", GreenVillagerKnightArmorItem.Helmet::new);
    public static final RegistryObject<Item> GREEN_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("green_villager_knight_chestplate", GreenVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_HELMET = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_knight_helmet", PurpleVillagerKnightArmorItem.Helmet::new);
    public static final RegistryObject<Item> PURPLE_VILLAGER_KNIGHT_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("purple_villager_knight_chestplate", PurpleVillagerKnightArmorItem.Chestplate::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_helmet", UnlightDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_chestplate", UnlightDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_leggings", UnlightDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> UNLIGHT_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("unlight_diamond_boots", UnlightDiamondArmorItem.Boots::new);
    public static final RegistryObject<Item> HEROBRINE_OBSIDIAN_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("herobrine_obsidian_diamond_helmet", HerobrineObsidianDiamondArmorHelmetItem.Helmet::new);
    public static final RegistryObject<Item> HEROBRINE_OBSIDIAN_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("herobrine_obsidian_diamond_chestplate", HerobrineObsidianDiamondArmorChestplateItem.Chestplate::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_HELMET = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_helmet", BrokenDiamondArmorItem.Helmet::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_CHESTPLATE = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_chestplate", BrokenDiamondArmorItem.Chestplate::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_LEGGINGS = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_leggings", BrokenDiamondArmorItem.Leggings::new);
    public static final RegistryObject<Item> BROKEN_DIAMOND_BOOTS = AnnoyingVillagersModItems.REGISTRY.register("broken_diamond_boots", BrokenDiamondArmorItem.Boots::new);

    private static RegistryObject<Item> block(RegistryObject<Block> registryobject, CreativeModeTab creativemodetab) {
        return AnnoyingVillagersModItems.REGISTRY.register(registryobject.getId().getPath(), () -> new BlockItem(registryobject.get(), (new Properties())));
    }
}
